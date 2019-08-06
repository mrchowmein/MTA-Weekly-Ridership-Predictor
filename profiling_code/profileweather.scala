val weatherPath: String = "hdfs:///user/jwc516/weather/1803943.csv"

val weatherData = sc.textFile(weatherPath)

val filteredRDD = weatherData.filter(line=>line.contains("USW00014732"))

val splitData = filteredRDD.map(line=> line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"))


//Check if specific values can be parsed to numeric
//Check if Date values can be parsed to int
splitData.take(1)(0)(5).split('-')(0).replaceAll("^\"|\"$", "").toInt
splitData.take(1)(0)(5).split('-')(1).replaceAll("^\"|\"$", "").toInt
splitData.take(1)(0)(5).split('-')(2).replaceAll("^\"|\"$", "").toInt

//Check if precipitation can be parsed to double
splitData.take(1)(0)(10).replaceAll("^\"|\"$", "").toDouble

//Check if snow values can be parsed to double
splitData.take(1)(0)(11).replaceAll("^\"|\"$", "").toDouble

//Check if avg temp value can be parsed to int
splitData.take(1)(0)(13).replaceAll("^\"|\"$", "").toInt

val outputData = splitData.map(v => v(0).replaceAll("^\"|\"$", "")+','+v(5).split('-')(0).replaceAll("^\"|\"$", "")+','+ v(5).split('-')(1).replaceAll("^\"|\"$", "")+','+v(5).split('-')(2).replaceAll("^\"|\"$", "")+','+v(10).replaceAll("^\"|\"$", "")+','+v(11).replaceAll("^\"|\"$", "")+','+v(13).replaceAll("^\"|\"$", "") )

//find max temp
val maxTemp = outputData.map(line => line.split(',')(6).toInt).max()

//find max snow
val maxSnow = outputData.map(line => line.split(',')(5).toDouble).max()

//find max precipitation
val maxSnow = outputData.map(line => line.split(',')(4).toDouble).max()