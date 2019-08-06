Fare data is located: /scratch/jwc516/Fare
Turnstile data is located: /scratch/jwc516/turnstile
Weather data is located: /scratch/rag551/weather
dateweek.csv is located: /scratch/jwc516/dateweek.csv

In HDFS data is located in the following locations:
hdfs:///user/jwc516/Fare
hdfs:///user/jwc516/turnstile/
hdfs:///user/jwc516/weather/

To run the program linux command line (not Spark-Shell):
Please run the run.sh.

OR

To run manually, run the scala scripts in the following order:
etlturnstile.scala
etlweather.scala
compute.scala

Final dataset will be saved in: hdfs:///user/jwc516/finaldataset/

Use this finaldataset as a csv file for the KNIME workflow.
-double click on the File Reader in the workflow and add the finaldataset.csv file.
-press play or "execute all nodes"
-csv out put will be created with the predicted values
-right click on the numeric scorer to view the model's performance
