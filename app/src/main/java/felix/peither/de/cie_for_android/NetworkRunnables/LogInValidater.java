package felix.peither.de.cie_for_android.NetworkRunnables;

import android.content.Intent;

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

import felix.peither.de.cie_for_android.LoginActivity;
import felix.peither.de.cie_for_android.SecondActivity;

public class LogInValidater implements Runnable {

    List<String> response = new ArrayList<>();

    private boolean isValid = false;

    private final String mail;
    private final String password;

    public LogInValidater(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    @Override
    public void run() {
        URL url = null;

        try {
            url = new URL("https://nine.wi.hm.edu/api/v2/login");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            String body =   "{\n" +
                    "   \"username\": " + "\"" + mail + "\"," +
                    "   \"password\": " + "\"" + password + "\"\n" +
                    "}";

            InputStream is = conn.getInputStream();
            conn.setRequestProperty("Content-Length", Integer.toString(body.length()));
            conn.getOutputStream().write(body.getBytes("UTF8"));
            conn.getOutputStream().flush();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = rd.readLine()) != null) {
                response.add(line);
            }

            if (!response.isEmpty()) {
                isValid = true;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getResponse() {
        return Collections.unmodifiableList(response);
    }

    public boolean isValid() {
        return isValid;
    }
}
