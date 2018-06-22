package felix.peither.de.cie_for_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import felix.peither.de.cie_for_android.CourseData.Course;
import felix.peither.de.cie_for_android.NetworkRunnables.CourseGetter;

public class ScheduleActivity extends NavigationDrawer {

    private SharedPreferences favorites;
    private SharedPreferences.Editor favorites_editor;


    Toolbar schedule_toolbar;
    private DrawerLayout drawer;

    private static final String SHARED_PREFS_NAME = "FAVORITES";

    private List<Course> course_list;
    private List<Thread> allThreads = new ArrayList<>();

    ScrollView sv;
    RadioGroup rg;
    RadioButton monday;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_courses:
                Intent intent = new Intent(ScheduleActivity.this, CoursesActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_favorites:
                intent = new Intent(ScheduleActivity.this, FavoritesActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_locations:
                intent = new Intent(ScheduleActivity.this, LocationsActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_schedule:
                intent = new Intent(ScheduleActivity.this, ScheduleActivity.class);
                startActivity(intent);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        favorites = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
        favorites_editor = favorites.edit();

        monday = (RadioButton) findViewById(R.id.monday);

        sv = (ScrollView) findViewById(R.id.schedule_scroll_view);


        rg = (RadioGroup) findViewById(R.id.week);

        Toolbar toolbar = findViewById(R.id.schedule_toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer. addDrawerListener(toggle);
        toggle.syncState();


        CourseGetter getter = new CourseGetter();
        Thread request = new Thread(getter);
        request.start();
        allThreads.add(request);

        for (int i = 0; i < allThreads.size(); i++) {
            try {
                allThreads.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        course_list = getter.getCourses();

        final LinearLayout.LayoutParams match_parent_ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        final LinearLayout inner_layout = new LinearLayout(this);
        inner_layout.setOrientation(LinearLayout.VERTICAL);



        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.monday:
                        coursesRefresh("Monday",course_list,match_parent_ll,inner_layout);
                        break;
                    case R.id.tuesday:
                        coursesRefresh("Tuesday",course_list,match_parent_ll,inner_layout);
                        break;
                    case R.id.wednesday:
                        coursesRefresh("Wednesday",course_list,match_parent_ll,inner_layout);
                        break;
                    case R.id.thursday:
                        coursesRefresh("Thursday",course_list,match_parent_ll,inner_layout);
                        break;
                    case R.id.friday:
                        coursesRefresh("Friday",course_list,match_parent_ll,inner_layout);
                        break;
                }
            }
        });



    }

    private void coursesRefresh(String day, List<Course> course_list, LinearLayout.LayoutParams match_parent_ll,  LinearLayout inner_layout){
        boolean check = false;
        sv.removeAllViews();
        inner_layout.removeAllViews();
        for (final Course course: course_list) {
            if(favorites.contains(course.getCourse_ID())) {
                if(course.getDay().equals(day)) {
                    Toolbar schedule_bar = new Toolbar(this);
                    schedule_bar.setTitle(course.getName());
                    inner_layout.addView(schedule_bar, match_parent_ll);
                }else{
                    check = true;
                }
            }
        }
        if(check){
            TextView no_schedule_yet = new TextView(this);
            no_schedule_yet.setText("No Course selected yet!");
            no_schedule_yet.setTextSize((float)20.0);
            no_schedule_yet.setPadding(0, 30, 0, 0);
            inner_layout.addView(no_schedule_yet, match_parent_ll);
        }


        sv.addView(inner_layout);
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
