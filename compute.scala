

/*
//usage of tickets
val etlTurnstilePath: String = "hdfs:///user/jwc516/etlturnstile/"

val etlTurnStileRDD = sc.textFile(etlTurnstilePath)

val fixedDateRange = etlTurnStileRDD.filter(line=>line.contains("2015/") || line.contains("2016/") || line.contains("2017/") || line.contains("2018/")).filter(line=>line.contains("RIT-ROOSEVELT@00-05-01"))


val eltTurnstileSplit = fixedDateRange.map(line => line.split(','))

val mappedStationTurnstiles = eltTurnstileSplit.map(line => line(0)).distinct


val dateCountRDD = eltTurnstileSplit.map(v => (v(1), v(4)))


val reducedDateCountRDD = dateCountRDD.map(t =>(t._1, t._2.toLong)).reduceByKey(_+_).sortByKey(true)


val etlweather: String = "hdfs:///user/jwc516/etlweather/"

val weatherOutput = sc.textFile(etlweather)

val weatherMapped = weatherOutput.map(line=>line.split(','))


val weatherTuple = weatherMapped.map(v=>(v(0).split('/')(2)+v(0).split('/')(0)+v(0).split('/')(1), (v(1), v(2), v(3), v(4))))

val joinedData = reducedDateCountRDD.join(weatherTuple)

val joinedOutputData = joinedData.map(t=> t._1 +','+t._2._1+','+t._2._2._1+','+t._2._2._2+','+t._2._2._3+','+t._2._2._4)


joinedOutputData

import java.util.Calendar
import java.text.SimpleDateFormat
import java.util.Date

def getWeek(input:String):String = {
	val format = "yyyyMMdd"

	val df = new SimpleDateFormat(format)
	val date = df.parse(input)

  	val cal = Calendar.getInstance()
    val week = cal.setTime(date)
    val weekNum = cal.get(Calendar.WEEK_OF_YEAR)

    "%02d".format(weekNum)

}

*/

/*
load and compute fare data
*/
val farePath: String = "hdfs:///user/jwc516/Fare"
val fareData = sc.textFile(farePath)
val fareSplit = fareData.map(line=> line.split(','))
val fareMapped = fareSplit.map(line=>(line(0),(line.slice(3,25).map(_.toLong))))
val fareRowSum = fareMapped.map(t => (t._1, t._2.sum))
val correctedDate = fareRowSum.map(t => (t._1.split('/')(0)+t._1.split('/')(1)+t._1.split('/')(2),t._2))
val dateSum = correctedDate.reduceByKey(_+_).sortByKey(true)


/*
load and compute week number data
*/
val datePath: String = "hdfs:///user/jwc516/dateweek.csv"
val dateData = sc.textFile(datePath)
val dateMap = dateData.map(line=>line.split(','))
val fixedDate = dateMap.map(line=>(20+line(0).split('/')(2)+"%02d".format(line(0).split('/')(0).toInt)+"%02d".format(line(0).split('/')(1).toInt), "w" +"%02d".format(line(1).toInt)))
val dateFiltered = fixedDate.filter(t => t._1.contains("2015") || t._1.contains("2016") || t._1.contains("2017") ||t._1.contains("2018") )


/*
load and compute weather data
weather tuple format: average wind, prec, snow, avg temp
*/

val etlweather: String = "hdfs:///user/jwc516/etlweather/"
val weatherOutput = sc.textFile(etlweather)
val weatherMapped = weatherOutput.map(line=>line.split(','))
val weatherTuple = weatherMapped.map(v=>(v(0).split('/')(2)+v(0).split('/')(0)+v(0).split('/')(1), (v(1), v(2), v(3), v(4))))
val weekWeather = weatherTuple.join(dateFiltered)
val weekWeather2 = weekWeather.map(t=>(t._1.substring(0,4)+t._2._2, t._2._1))
val wind = weekWeather2.map(t =>(t._1, t._2._1.toDouble)).reduceByKey(_+_).sortByKey(true)
val prec = weekWeather2.map(t =>(t._1, t._2._2.toDouble)).reduceByKey(_+_).sortByKey(true)
val snow = weekWeather2.map(t =>(t._1, t._2._3.toDouble)).reduceByKey(_+_).sortByKey(true)
val temp = weekWeather2.map(t =>(t._1, t._2._4.toDouble)).reduceByKey(_+_).sortByKey(true)
val daysForWeek = dateFiltered.map(t=>(t._1.substring(0,4)+t._2, 1)).reduceByKey(_+_).sortByKey(true)
//ordering (wind, prec, snow, temp, days recorded per week)
val joinedWeekWeather = wind.join(prec).join(snow).join(temp).join(daysForWeek).sortByKey(true)
//compute daily avg for weather for the week
val dailyWeatherAvg = joinedWeekWeather.map(t=> t._1+','+ (t._2._1._1._1._1/t._2._2) + ',' +(t._2._1._1._1._2/t._2._2) +',' + (t._2._1._1._2/t._2._2) + ',' + (t._2._1._2/t._2._2))

dailyWeatherAvg.saveAsTextFile("dailyweatheravgforweek")


/*
Join weekly fare data with weekly weather data.
*/
val weeklyFares = dateFiltered.join(dateSum).sortByKey(true)
val weeklyFaresWeekNum = weeklyFares.map(t=>(t._1.substring(0,4)+t._2._1, t._2._2))

weeklyFaresWeekNum.saveAsTextFile("weeklyfares")

val dailyWeatherAvgTup = dailyWeatherAvg.map(line=>line.split(',')).map(line => (line(0),(line(1),line(2),line(3), line(4))))
val faresWithWeather = weeklyFaresWeekNum.join(dailyWeatherAvgTup).sortByKey(true)
//((2015w01,(25401607,(12.38,0.22333333333333336,0.0,35.0))))
val outputJoinedWeatherFares = faresWithWeather.map(t=> t._1+','+t._2._1+','+t._2._2._1+','+t._2._2._2+','+t._2._2._3+','+t._2._2._4)

outputJoinedWeatherFares.saveAsTextFile("processedDataSet")
