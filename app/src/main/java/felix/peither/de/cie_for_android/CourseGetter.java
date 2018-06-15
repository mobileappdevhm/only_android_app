package felix.peither.de.cie_for_android;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class CourseGetter implements Runnable {

    private List<Course> courses = new ArrayList<>();
    private List<Thread> allThreads = new ArrayList<>();

    @Override
    public void run() {
        List<String> response = new ArrayList<>();

        try {
            URL url = new URL("https://nine.wi.hm.edu/api/v2/courses/FK%2013/CIE/SoSe%2018");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            InputStream is = conn.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = rd.readLine()) != null) {
                String[] lines = line.split(",");
                for (String newLine: lines) {
                    response.add(newLine);
                    response.add("\r\n");
                }
            }
            rd.close();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Course> courseList = new ArrayList<>();

        String tmpName = "bla";
        String tmpShortName = "bla";
        String tmpProfessor = "bla";
        String tmpCourse_ID = "bla";
        String tmpLocation = "bla";
        String tmpDescription = "bla";

        for (String line: response) {
//            System.out.println(line);
            if (line.contains("\"id\"")) {
                tmpCourse_ID = line; //.substring(6, line.length() - 2);
            } else if (line.contains("\"lecturer\"")) {
                tmpProfessor = line;
            } else if (line.contains("\"description\"")) {
                tmpDescription = line;
            } else if (line.contains("\"name\"")) {
                tmpName = line;
            } else if (line.contains("\"shortName\"")) {
                tmpShortName = line;
                courseList.add(new Course(tmpName, tmpShortName, tmpProfessor, tmpCourse_ID, tmpLocation, tmpDescription));
            } else if (line.contains("\"campus\"")) {
                tmpLocation = line;
            }
//            courseList.add(new Course("bla","bla","bla","bla","bla","bla"));
        }

        courses = courseList;
    }

    public List<Course> getCourses() {
        return Collections.unmodifiableList(courses);
    }
}
