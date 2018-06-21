package felix.peither.de.cie_for_android;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import felix.peither.de.cie_for_android.NetworkRunnables.LogInValidater;

public class LoginActivity extends AppCompatActivity {

    Toolbar toolbar;
    MenuItem skipText;
    LinearLayout linearLayout;
    ScrollView scrollView;
    ScrollView sv;

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private int counter = 5;

    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ctx = this;

        sv = (ScrollView) findViewById(R.id.log_in_scroll_view);
        Name = (EditText) findViewById(R.id.etName);
        Password = (EditText) findViewById(R.id.etPassword);
        Info = (TextView) findViewById(R.id.tvInfo);
        Login = (Button) findViewById(R.id.btnLogin);

        skipText = (MenuItem) findViewById(R.id.skip_login);

        toolbar = (Toolbar) findViewById(R.id.log_in_toolbar);
        setSupportActionBar(toolbar);

        //toolbar.setTitle(R.string.toolbar_title);

        Info.setText("Nº of attempts remaining: 5");

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    validate(Name.getText().toString(), Password.getText().toString());
                } catch (InterruptedException e) {
                    Toast.makeText(ctx, "Log in failed, please try again!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.skip_login || item.getItemId() == R.id.skip_login_icon){
            startActivity(new Intent(this, SkippedLoginActivity.class));
        }

        return true;
    }

    private void validate (String userName, String userPassword) throws InterruptedException {

        List<Thread> allThreads = new ArrayList<>();

        LogInValidater validater = new LogInValidater(userName, userPassword);
        Thread validate = new Thread(validater);

        try {
            validate.start();
        } catch (Exception e) {
            Toast.makeText(ctx, "Log in failed, please try again", Toast.LENGTH_LONG).show();
        }
        allThreads.add(validate);
        for (int i = 0; i < allThreads.size(); i++) {
            try {
                allThreads.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if((userName.equals("admin")) && (userPassword.equals("123456"))) {
            Intent intent = new Intent(LoginActivity.this, SecondActivity.class);
            startActivity(intent);
        } else if (!validater.isValid()) {
            Toast.makeText(ctx, "Log in failed, please try again", Toast.LENGTH_LONG).show();
            counter--;

            Info.setText("Nº of Attempts remaining: " + String.valueOf(counter));

            if(counter == 0){
                Login.setEnabled(false);
            }
        } else {
            Intent intent = new Intent(LoginActivity.this, SecondActivity.class);
            startActivity(intent);
        }
    }
}
