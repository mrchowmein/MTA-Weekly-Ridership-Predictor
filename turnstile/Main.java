
/* program was used to download turnstile data using MTA's website's link source code saved as a text file.
    program will parse out the urls from the html, then save the files to your local drive.
*/
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        String inputFile = "turnstileurl.txt";
        BufferedReader br = null;
        String line = "";
        long rowCount = 0;
        ArrayList <String> urlList = new ArrayList<>();

        String lineIn ="";
        try {
            br = new BufferedReader(new FileReader(inputFile));
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

        for(int i = 0; i < arrOfStr.length; i++){
            arrOfStr[i] = "http://web.mta.info/developers/"+arrOfStr[i].substring(9,49);
            //System.out.println(arrOfStr[i].substring(51));
        }

        generateOuputFile("urlList.txt", arrOfStr);

        for(int j = 0; j<arrOfStr.length; j++){

            //305
            downloadMTA(arrOfStr[j]);
            System.out.println("File: "+j + " of "+ arrOfStr.length);
            TimeUnit.SECONDS.sleep(1);
        }

    }

    public static void generateOuputFile(String reportOutputPath, String[] dataArray) throws IOException {

        File f = new File(reportOutputPath);
        FileWriter fileWriter = new FileWriter(f);
        PrintWriter printWriter = new PrintWriter(fileWriter);


        for(int i = 0; i < dataArray.length; i++){

            printWriter.printf("%s\n", dataArray[i]);

        }


        printWriter.close();

    }

    public static void downloadMTA(String urlString){



        try (BufferedInputStream inputStream = new BufferedInputStream(new URL(urlString).openStream());
             FileOutputStream fileOS = new FileOutputStream(urlString.substring(51))) {
            byte data[] = new byte[1024];
            int byteContent;
            while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
                fileOS.write(data, 0, byteContent);
            }
        } catch (IOException e) {
            // handles IO exceptions
        }
    }



    //http://web.mta.info/developers/data/nyct/turnstile/turnstile_190713.txt
}
