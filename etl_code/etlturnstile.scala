/*
Command to move turnstile data to HPC:
 rsync -avz turnstile/ jwc516@dumbo.es.its.nyu.edu:/scratch/jwc516/turnstile/

Command to move turnstile data to HDFS:
hdfs dfs -put turnstile

*/

val turnStilePath: String = "hdfs:///user/jwc516/turnstile"

val turnStileData = sc.textFile(turnStilePath)

val filteredRDD = turnStileData.filter(line=>line.contains(",01:") || line.contains(",00:") || line.contains(",02:") || line.contains(",03:"))

val splitData = filteredRDD.map(line=> line.split(','))

val outputData = splitData.map(v => v(3)+'@'+v(2)+','+v(6).split('/')(2)+'/'+v(6).split('/')(0)+'/'+v(6).split('/')(1)+','+v(7)+','+v(8)+','+v(9).trim() )

outputData.saveAsTextFile("etlturnstile")


System.exit(0)

