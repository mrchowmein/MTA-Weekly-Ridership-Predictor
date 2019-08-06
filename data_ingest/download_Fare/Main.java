package com.company;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        String csvFile = "fareURL.txt";
        BufferedReader br = null;
        String line = "";
        long rowCount = 0;
        ArrayList <String> urlList = new ArrayList<>();

        String lineIn ="";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                lineIn = line;
                rowCount++;

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String [] arrOfStr = lineIn.split("<br/>");

    System.out.println(rowCount);
        File f = new File("fileList.txt");
        FileWriter fileWriter = new FileWriter(f);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        for(int i = 0; i < arrOfStr.length; i++){
            System.out.println(arrOfStr[i]);
            //arrOfStr[i] = "http://web.mta.info/developers/"+arrOfStr[i].substring(9,49);
            printWriter.printf("%s\n", arrOfStr[i].substring(25,41));
            arrOfStr[i] = "http://web.mta.info/developers/"+arrOfStr[i].substring(9,41);
            //System.out.println(arrOfStr[i].substring(51));
        }

        printWriter.close();

        generateOuputFile("urlList.txt", arrOfStr);


        for(int j = 0; j<arrOfStr.length; j++){

            //305
            downloadMTA(arrOfStr[j]);
            System.out.println("File: "+j + " of "+ arrOfStr.length);
            TimeUnit.SECONDS.sleep(1);
        }

        updateCSV();

    }

    public static void generateOuputFile(String reportOutputPath, String[] dataArray) throws IOException {

        File f = new File(reportOutputPath);
        FileWriter fileWriter = new FileWriter(f);
        PrintWriter printWriter = new PrintWriter(fileWriter);


        for(int i = 0; i < dataArray.length; i++){

            printWriter.printf("%s\n", dataArray[i]);

        }

        printWriter.close();



        //generatefilelist
    }

    public static void downloadMTA(String urlString){



        try (BufferedInputStream inputStream = new BufferedInputStream(new URL(urlString).openStream());
             FileOutputStream fileOS = new FileOutputStream(urlString.substring(47))) {
            byte data[] = new byte[1024];
            int byteContent;
            while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
                fileOS.write(data, 0, byteContent);
            }
        } catch (IOException e) {
            // handles IO exceptions
        }
    }


    public static void updateCSV() throws IOException {
        String csvFile = "fileList.txt";

        BufferedReader br = null;
        String line = "";
        long rowCount = 0;
        ArrayList <String> fileList = new ArrayList<>();

        String lineIn ="";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                fileList.add(line);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println(fileList);

        System.out.println("total files: " + fileList.size());

        for(int i = 0; i<fileList.size(); i++){


            String fareFile = fileList.get(i);
            String date = "20"+fareFile.substring(6,12);
            String date2 = date.substring(0,4)+"/"+date.substring(4,6)+"/"+date.substring(6,8);


            br = null;
            line = "";
            rowCount = 0;
            ArrayList <String> rowData = new ArrayList<>();


            try {
                br = new BufferedReader(new FileReader(fareFile));

                while ((line = br.readLine()) != null) {

                   if(rowCount>2){
                       line = date2+","+line;
                       rowData.add(line);
                   }

                    rowCount++;

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


            File f = new File("fares_"+date+".csv");
            FileWriter fileWriter = new FileWriter(f);
            PrintWriter printWriter = new PrintWriter(fileWriter);


            for(int j = 0; j < rowData.size(); j++){

                printWriter.printf("%s\n", rowData.get(j));

            }

            printWriter.close();





        }

    }

}
