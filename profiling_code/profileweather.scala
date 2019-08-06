val weatherPath: String = "hdfs:///user/rag551/weather/1818263.csv"

val weatherData = sc.textFile(weatherPath)

val filteredRDD = weatherData.filter(line=>line.contains("USW00014732"))

val splitData = filteredRDD.map(line=> line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"))


//Check if specific values can be parsed to numeric
//Check if Date values can be parsed to int
splitData.take(1)(0)(2).split('-')(0).replaceAll("^\"|\"$", "").toInt
splitData.take(1)(0)(2).split('-')(1).replaceAll("^\"|\"$", "").toInt
splitData.take(1)(0)(2).split('-')(2).replaceAll("^\"|\"$", "").toInt

//Check if precipitation can be parsed to double
splitData.take(1)(0)(9).replaceAll("^\"|\"$", "").toDouble

//Check if snow values can be parsed to double
splitData.take(1)(0)(10).replaceAll("^\"|\"$", "").toDouble

//Check if avg temp value can be parsed to int
splitData.take(1)(0)(12).replaceAll("^\"|\"$", "").toInt

val outputData = splitData.map(v => v(10).replaceAll("^\"|\"$", "")+','+v(11).replaceAll("^\"|\"$", "")+','+v(12).replaceAll("^\"|\"$", "") )

//find max temp
val maxTemp = outputData.map(line => line.split(',')(2).toInt).max()

//find max snow
val maxSnow = outputData.map(line => line.split(',')(1).toDouble).max()

//find max precipitation
val maxPrec = outputData.map(line => line.split(',')(0).toDouble).max()