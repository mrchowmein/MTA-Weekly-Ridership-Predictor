Fare data is located: /scratch/jwc516/Fare
Turnstile data is located: /scratch/jwc516/turnstile
Weather data is located: /scratch/rag551/weather
dateweek.csv is located: /scratch/jwc516/dateweek.csv

In HDFS data is located in the following locations:
hdfs:///user/jwc516/Fare
hdfs:///user/jwc516/turnstile/
hdfs:///user/rag551/weather/
hdfs:///user/jwc516/dateweek.csv

**To run the program: **
1) To run the program linux command line (not Spark-Shell):
Upload the following scala files into prince/linux: etlturnstile.scala, etlweather.scala, compute.scala and run.sh.
Please run the run.sh from commandline.
The script will read in data from rag551 and jwc516's HDFS directories.

OR
To run manually, run the scala scripts in the following order:
etlturnstile.scala
etlweather.scala
compute.scala

2) Final dataset will be saved in: hdfs:///user/jwc516/finaldataset/

3) Rename this finaldataset to a .csv file. Then use this csv file for the KNIME workflow by following these steps:
-double click on the File Reader in the workflow and add the finaldataset.csv file.
-press play or "execute all nodes"
-csv out put will be created with the predicted values
-right click on the numeric scorer to view the model's performance

Optional: intermediary 


Description of Directories
data_injest: This directory contains all the programs and commands we used download and upload data into our hdfs directories.
app_code: This directory contains our primary computation code (compute.scala) we used to process and create our final dataset after ETL. This directory also contains our KNIME workflow used to create our predictive model.
