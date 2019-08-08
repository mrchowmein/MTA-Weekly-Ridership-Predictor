#!/bin/bash
#
#
hdfs dfs -rm -r etlturnstile
hdfs dfs -rm -r etlweather
hdfs dfs -rm -r dailyweatheravgforweek
hdfs dfs -rm -r weeklyfares
hdfs dfs -rm -r finaldataset
hdfs dfs -rm -r dailyandcumu
hdfs dfs -rm -r turnstileDailyRatio

spark-shell -i etlturnstile.scala
spark-shell -i etlweather.scala
spark-shell -i compute.scala
