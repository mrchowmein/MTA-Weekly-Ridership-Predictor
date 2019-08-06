/*
Move files to Prince
rsync -avz 1803943.csv jwc516@dumbo.es.its.nyu.edu:/scratch/jwc516/weather/

Move files to HDFS
hdfs dfs -mkdir /user/jwc516/weather
hdfs dfs -put /scratch/jwc516/weather/1803943.csv /user/jwc516/weather
*/

//Commands to ETL
val weatherPath: String = "hdfs:///user/rag551/weather"

val weatherData = sc.textFile(weatherPath)

val filteredRDD = weatherData.filter(line=>line.contains("USW00014732"))

val splitData = filteredRDD.map(line=> line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"))

//val outputData = splitData.map(v => v(0).replaceAll("^\"|\"$", "")+','+v(5).split('-')(0).replaceAll("^\"|\"$", "")+','+ v(5).split('-')(1).replaceAll("^\"|\"$", "")+','+v(5).split('-')(2).replaceAll("^\"|\"$", "")+','+v(10).replaceAll("^\"|\"$", "")+','+v(11).replaceAll("^\"|\"$", "")+','+v(13).replaceAll("^\"|\"$", "") )

val outputData = splitData.map(v => v(2).split('-')(0).replaceAll("^\"|\"$", "")+'/'+ v(2).split('-')(1).replaceAll("^\"|\"$", "")+'/'+v(2).split('-')(2).replaceAll("^\"|\"$", "") + ','+v(3).replaceAll("^\"|\"$", "")+','+v(9).replaceAll("^\"|\"$", "")+','+v(10).replaceAll("^\"|\"$", "")+','+v(12).replaceAll("^\"|\"$", "")  )

outputData.saveAsTextFile("etlweather")




System.exit(0)