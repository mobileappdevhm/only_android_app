package felix.peither.de.cie_for_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import felix.peither.de.cie_for_android.CourseData.Course;
import felix.peither.de.cie_for_android.NetworkRunnables.CourseGetter;

public class CoursesActivity extends NavigationDrawer {

    Toolbar courses_toolbar;
    private DrawerLayout drawer;
    ScrollView sv;

    private static final String SHARED_PREFS_NAME = "FAVORITES";

    private List<Course> course_list;

    private CourseGetter courseGetter;

    SharedPreferences favorites;
    SharedPreferences.Editor favorites_editor;

    List<Thread> allThreads = new ArrayList<>();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_courses:
                Intent intent = new Intent(CoursesActivity.this, CoursesActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_favorites:
                intent = new Intent(CoursesActivity.this, FavoritesActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_locations:
                intent = new Intent(CoursesActivity.this, LocationsActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_schedule:
                intent = new Intent(CoursesActivity.this, ScheduleActivity.class);
                startActivity(intent);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        CourseGetter getter = new CourseGetter();

        final Context ctx = this;

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

        ListAdapter adapter = new ListAdapter();
        ListView listView = (ListView) findViewById(R.id.courses_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final AlertDialog.Builder info_dialog = new AlertDialog.Builder(ctx)
                        .setTitle(course_list.get(position).getShortName())
                        .setMessage(course_list.get(position).getDescription())
                        .setNeutralButton("Close", null);

                info_dialog.create().show();
            }
        });

        Toolbar toolbar = findViewById(R.id.courses_toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer. addDrawerListener(toggle);
        toggle.syncState();

//        sv = (ScrollView) findViewById(R.id.courses_scroll_view);
        courses_toolbar = (Toolbar) findViewById(R.id.courses_toolbar);
        courses_toolbar.setTitleTextColor(Color.WHITE);
        /*
        courses_toolbar.setNavigationIcon(R.drawable.ic_arrow_backward_white);
        courses_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });*/

        favorites = getSharedPreferences("FAVORITES", MODE_PRIVATE);
        favorites_editor = favorites.edit();
    }

    class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return course_list.size();
        }

        @Override
        public Object getItem(int position) {
            return course_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.courses_layout, null);

            final Course course = course_list.get(position);

            final ImageView fav_icon = (ImageView) convertView.findViewById(R.id.fav_icon);
            TextView name = (TextView) convertView.findViewById(R.id.text_name);
            TextView shortName = (TextView) convertView.findViewById(R.id.text_descr);

            if (favorites.contains(course.getCourse_ID())) {
                fav_icon.setImageResource(R.drawable.ic_favorite_full_red);
            } else {
                fav_icon.setImageResource(R.drawable.ic_favorite_red);
            }

            fav_icon.setClickable(true);
            fav_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (favorites.contains(course.getCourse_ID())) {
                        fav_icon.setImageResource(R.drawable.ic_favorite_red);
                        favorites_editor.remove(course.getCourse_ID());
                        favorites_editor.commit();
                    } else {
                        fav_icon.setImageResource(R.drawable.ic_favorite_full_red);
                        Gson gson = new Gson();
                        String json = gson.toJson(course);
                        favorites_editor.putString(course.getCourse_ID(), json);
                        favorites_editor.commit();
                    }
                }
            });

            name.setText(course.getName());
            name.setTextSize(14);
            shortName.setText(course.getShortName());
            shortName.setTextSize(10);

            return convertView;
        }
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
