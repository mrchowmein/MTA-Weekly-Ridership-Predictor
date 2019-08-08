# README

## To run the program:
FYI: You will not need to upload any data as the scala scripts already point to our HDFS directories.
However, intermediary and final output will be saved into your relative HDFS directory.

The application includes the following: etlturnstile.scala, etlweather.scala, compute.scala, run.sh, mtadectree.knwf, Fares vs Weather.twbx, MTA Fares Prediction.twbx, Turnstile Bad Data?.twbx.

1) Run from HPC's linux command line (not Spark-Shell) with script:  
Upload the following scala files into prince/linux: etlturnstile.scala, etlweather.scala, compute.scala and run.sh.  
Please run the run.sh from commandline.  
The script will read in data from rag551 and jwc516's HDFS directories.  
The script will delete any intermediary directories. 
The script will then run the 3 scala files.

 OR
 To run manually, run the scala files in the following order:  
 etlturnstile.scala  
 etlweather.scala  
 compute.scala  

2) Final dataset will be saved in: hdfs:///user/nyuID/finaldataset/

__Optional:__ intermediary directories will also be created if you are interested in other forms of data besides the final dataset. These directories are: etlturnstile, etlweather, dailyweatheravgforweek, weeklyfares, dailyandcumu, turnstileDailyRatio.

3) Download and Rename this finaldataset to a .csv file. Then use this csv file for the KNIME workflow by following these steps:  
-You may need to add column headers to the csv file. the headers are from left to right: year,    week,    total fares,    wind,    prec,    snow,    temp  
-double click on the File Reader in the workflow and add the finaldataset.csv file.  
-press play or "execute all nodes"  
-gbtpredicted.csv will be created with the predicted values.  The test data used was 20% data partitioned from our finaldataset. More info below in the test_code directory description.
-right-click on the numeric scorer to view the model's performance  

4. Open the Tableau files to view the dashboards. Tableau used the following data files to create the visuals: gbtpredicted.csv, dailyandcumu, turnstileDailyRatio and finaldataset.csv.

## Data Locations:
__In Scratch, the data is located in:__  
Fare data is located: /scratch/jwc516/Fare  
Turnstile data is located: /scratch/jwc516/turnstile  
Weather data is located: /scratch/rag551/weather  
dateweek.csv is located: /scratch/jwc516/dateweek.csv  

__In HDFS data is located in the following locations:__   
hdfs:///user/jwc516/Fare  
hdfs:///user/jwc516/turnstile/  
hdfs:///user/rag551/weather/  
hdfs:///user/jwc516/dateweek.csv  


## Description of Submission (zip file) Directories:
-data_injest: This directory contains all the programs and commands we used download and upload data into our hdfs directories.  
-app_code: This directory contains our primary computation code (compute.scala) we used to process and create our final dataset after ETL. This directory also contains our KNIME workflow used to create our predictive model. This directory also includes are Tableau files created with some intermediary datasets and predicted datasets.  
-profiling_code: This directory contains scala scripts used to profile our data.  
-etl_code: This directory contains scripts used to ETL our turnstile and weather data.  
-screenshots: This directory includes sample screen shots of our program running.  
-test_code: This is the exact data used to test AND evaluate our trained model. The test data was 20% data partitioned from our finaldataset. The seed for this partition is set in the KNIME workflow file. You will not need to input this data manually as the workflow takes care of it. 
