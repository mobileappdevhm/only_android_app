package felix.peither.de.cie_for_android;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class HomePage extends AppCompatActivity {

    BottomNavigationView nav_bar;
    CoordinatorLayout cl;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        cl = (CoordinatorLayout) findViewById(R.id.home_layout);
        nav_bar = (BottomNavigationView) findViewById(R.id.nav_bar);

        toolbar = (Toolbar) findViewById(R.id.courses_toolbar);
        toolbar.setTitle("Courses");
        setSupportActionBar(toolbar);
    }
}
