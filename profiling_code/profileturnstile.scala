val turnStilePath: String = "hdfs:///user/jwc516/turnstile/turnstile_190713.txt"

val turnStileData = sc.textFile(turnStilePath)

val filteredRDD = turnStileData.filter(line=>line.contains("01:00:00") || line.contains("00:00:00"))

val splitData = filteredRDD.map(line=> line.split(','))

//Check if specific values can be parsed to numeric
//Check if Date values can be parsed to int
splitData.take(1)(0)(6).split('/')(0).toInt
splitData.take(1)(0)(6).split('/')(1).toInt
splitData.take(1)(0)(6).split('/')(2).toInt


//Check if turnstile exits value can be parsed to int
splitData.take(1)(0)(10).trim().toInt

val outputData = splitData.map(v => v(0)+','+v(6).split('/')(0)+','+ v(6).split('/')
(1)+','+v(6).split('/')(2)+','+v(7)+','+v(8)+','+v(10).trim() )

//Check for max exits
val maxExits = outputData.map(line => line.split(',')(6).toLong).max()

