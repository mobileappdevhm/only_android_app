package felix.peither.de.cie_for_android;

import android.support.v4.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.icu.text.MeasureFormat;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {

    BottomNavigationView nav_bar;
    ScrollView sv;
    //Toolbar toolbar;

    private final CourseGetter courseGetter = new CourseGetter();

    private ArrayList<Course> courseList;

    private Map map = new Map();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //toolbar = (Toolbar) findViewById(R.id.courses_toolbar);

        courseList = courseGetter.getCourses();


        sv = (ScrollView) findViewById(R.id.scroll_view);

        nav_bar = (BottomNavigationView) findViewById(R.id.nav_bar);
        nav_bar.setBackgroundColor(Color.LTGRAY);

        nav_bar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_button:
                        make_home_page();
                        break;
                    case R.id.courses_button:
                        make_course_page();
                        break;
                    case R.id.favorites_button:
                        make_favorite_page();
                        break;
                    case R.id.map_button:
                        make_map_page(map);
                        break;
                }
                return true;
            }
        });
        make_home_page();
    }

    private void make_course_page() {

        sv.removeAllViews();

        Toolbar toolbar = new Toolbar(this);
        toolbar.setTitle("Courses");
        toolbar.setBackgroundColor(Color.LTGRAY);
        LinearLayout.LayoutParams match_parent_ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout inner_layout = new LinearLayout(this);
        inner_layout.setOrientation(LinearLayout.VERTICAL);
        inner_layout.addView(toolbar, match_parent_ll);

        LinearLayout.LayoutParams wrap_content_ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        for (Course course: courseList) {
            Button btn = new Button(this);
            btn.setText(course.getName());

            inner_layout.addView(btn, wrap_content_ll);
        }

        sv.addView(inner_layout);
    }

    private void make_home_page() {

        sv.removeAllViews();

        Toolbar toolbar = new Toolbar(this);
        toolbar.setTitle("Home");
        toolbar.setBackgroundColor(Color.LTGRAY);
        LinearLayout inner_layout = new LinearLayout(this);

        LinearLayout.LayoutParams clp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        inner_layout.addView(toolbar, clp);

        sv.addView(inner_layout, clp);

    }

    private void make_favorite_page() {

        sv.removeAllViews();

        Toolbar toolbar = new Toolbar(this);
        toolbar.setTitle("Favorites");
        toolbar.setBackgroundColor(Color.LTGRAY);
        LinearLayout inner_layout = new LinearLayout(this);

        LinearLayout.LayoutParams clp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        inner_layout.addView(toolbar, clp);

        sv.addView(inner_layout, clp);

    }

    private void make_map_page(Fragment fragment) {

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_map, fragment).commit();
        //sv.removeAllViews();

        //Toolbar toolbar = new Toolbar(this);
        //toolbar.setTitle("Map");
        //toolbar.setBackgroundColor(Color.LTGRAY);
        //LinearLayout inner_layout = new LinearLayout(this);

        //LinearLayout.LayoutParams clp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //inner_layout.addView(toolbar, clp);

        //sv.addView(inner_layout, clp);

    }
}
