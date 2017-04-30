package myapp.myorganizer;

import android.content.res.Resources;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Amit Desai on 02-02-2016.
 */
public class Timetable {
    int j,dayno;
    String days[] = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    daysOfWeek day[] = new daysOfWeek[7];
    public void addDay()
    {
        for(j=0;j<7;j++)
            day[j] = new daysOfWeek(days[j]);
    }
    void lectures() {

        /*day[0].addLecture("Its a holiday");

        day[1].addLecture("7:00-9:00-   COA PRACTS");
        day[1].addLecture("9:00-9:30-   BREAK");
        day[1].addLecture("9:30-10:30-  DBMS");
        day[1].addLecture("10:30-11:30- CG");
        day[1].addLecture("11:30-12:00- BREAK");
        day[1].addLecture("12:30-1:30-  TCS");
        day[1].addLecture("1:30-2:30-   COA");

        day[2].addLecture("7:00-8:00-   TCS");
        day[2].addLecture("8:00-9:00-   DBMS");
        day[2].addLecture("9:00-9:30-   BREAK");
        day[2].addLecture("9:30-10:30-  CG");
        day[2].addLecture("10:30-11:30- COA");
        day[2].addLecture("11:00-11:30- AOA");


        day[3].addLecture("7:00-8:00-   AOA");
        day[3].addLecture("8:00-9:00-   TCS");
        day[3].addLecture("9:00-9:30-   BREAK");
        day[3].addLecture("9:30-10:30-  DBMS");
        day[3].addLecture("10:30-11:30- COA");
        day[3].addLecture("11:00-11:30- MATHS");


        day[4].addLecture("9:30-11:30-  DBMS PRACTS");
        day[4].addLecture("11:30-12:30- MATHS TUTS");
        day[4].addLecture("12:30-1:30-  BREAK");
        day[4].addLecture("1:30-2:30-   MATHS");
        day[4].addLecture("2:30-3:30-   CG");


        day[5].addLecture("9:30-11:30-  AOA PRACTS");
        day[5].addLecture("11:30-1:30-  CG PRACTS");
        day[5].addLecture("1:30-2:00-   BREAK");
        day[5].addLecture("2:00-3:00-   MATHS");
        day[5].addLecture("3:00-4:00-   AOA");
        day[5].addLecture("3:00-5:00-   TCS");


        day[6].addLecture("11:00-12:00- MATHS");
        day[6].addLecture("12:00-12:30- BREAK");
        day[6].addLecture("12:30-1:30-  COA");
        day[6].addLecture("1:30-2:30-   DBMS");
        day[6].addLecture("2:30-3:30-   AOA");*/


        //String fileName = R.raw.timetable.txt;

        // This will reference one line at a time
        //String line = null;

        //try {
            //FileReader reads text files in the default encoding.
          //  FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            //BufferedReader bufferedReader = new BufferedReader(fileReader);

            //while((line = bufferedReader.readLine()) != null) {
              //   dayno = Integer.parseInt(line.substring(0,1));
                // day[dayno].addLecture(line.substring(2));
           // }

            //Always close files.
            //bufferedReader.close();
             //      }

            //catch(FileNotFoundException ex) {
      //    System.out.println("Unable to open file " );
       //}
       //catch(IOException ex) {
         //System.out.println("Error reading file ");
            // Or we could just do this:
        // ex.printStackTrace();
        //}

            String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
            //String fileName = baseDir + File.separator + "timetable.txt";
            String fileName = "/sdcard/timetable.txt";
            //String fileName2 = "/sdcard/myhellotest.txt";
            //InputStream inputStream = getResources().openRawResource(R.raw.timetable);

            //text.setText("Reading File '" + fileName + "'");
            String line;
            int dayno;

            try {
                // line = br.readLine();
                FileReader fileReader = new FileReader(fileName);
                BufferedReader br  = new BufferedReader(fileReader);
                //text.setText("File Found " + fileName);
                //System.out.println (line);
                while((line = br.readLine()) != null) {
                    dayno = Integer.parseInt(line.substring(0,1));
                    System.out.println("Day " + dayno);
                    dayno--;
                    System.out.println(line.substring(2));
                    day[dayno].addLecture(line.substring(2));
                }

                br.close();
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                //text.setText("Error Reading File " + fileName);
                e.printStackTrace();
            }
            //System.out.println("Reading file over");


        }





}
