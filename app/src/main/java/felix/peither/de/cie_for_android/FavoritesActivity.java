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

import java.util.ArrayList;
import java.util.List;

import felix.peither.de.cie_for_android.CourseData.Correlation;
import felix.peither.de.cie_for_android.CourseData.Course;
import felix.peither.de.cie_for_android.CourseData.Date;
import felix.peither.de.cie_for_android.NetworkRunnables.CourseGetter;

public class FavoritesActivity extends NavigationDrawer {

    private SharedPreferences favorites;
    private SharedPreferences.Editor favorites_editor;

    private static final String SHARED_PREFS_NAME = "FAVORITES";

    private List<Course> course_list;
    private List<Course> favorite_courses = new ArrayList<>();
    private List<Thread> allThreads = new ArrayList<>();

    ScrollView sv;
    Toolbar favorites_toolbar;
    private DrawerLayout drawer;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_courses:
                Intent intent = new Intent(FavoritesActivity.this, CoursesActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_favorites:
                intent = new Intent(FavoritesActivity.this, FavoritesActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_locations:
                intent = new Intent(FavoritesActivity.this, LocationsActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_schedule:
                intent = new Intent(FavoritesActivity.this, ScheduleActivity.class);
                startActivity(intent);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        favorites = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
        favorites_editor = favorites.edit();

        favorites_toolbar = (Toolbar) findViewById(R.id.favorites_toolbar);
        favorites_toolbar.setTitleTextColor(Color.WHITE);
        /*
        favorites_toolbar.setNavigationIcon(R.drawable.ic_arrow_backward_white);
        favorites_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });*/

        Toolbar toolbar = findViewById(R.id.favorites_toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer. addDrawerListener(toggle);
        toggle.syncState();

        //sv = (ScrollView) findViewById(R.id.favorites_scroll_view);

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

        for (Course course: course_list) {
            if (favorites.contains(course.getCourse_ID())) {
                favorite_courses.add(course);
            }
        }

        if (favorite_courses.isEmpty()) {
            favorite_courses.add(new Course("No Favorites yet.","No Favorites yet.","","",new ArrayList<Date>(), new ArrayList<Correlation>()));
        }

        final Context ctx = this;

        ListView listView = (ListView) findViewById(R.id.favorites_list_view);
        ListAdapter adapter = new ListAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final AlertDialog.Builder info_dialog = new AlertDialog.Builder(ctx)
                        .setTitle(favorite_courses.get(position).getShortName())
                        .setMessage(favorite_courses.get(position).getDescription())
                        .setNeutralButton("Close", null);

                info_dialog.create().show();
            }
        });



//        LinearLayout.LayoutParams match_parent_ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        LinearLayout inner_layout = new LinearLayout(this);
//        inner_layout.setOrientation(LinearLayout.VERTICAL);
//
//        final AlertDialog.Builder info_dialog = new AlertDialog.Builder(this)
//                .setTitle("Course Description")
//                .setMessage("This is a description of a course :)")
//                .setNeutralButton("Close", null);
//
//        int fav_counter = 0;
//
//        // this loop adds all favorites to the layout
//        for (final Course course: course_list) {
//            if (favorites.contains(course.getCourse_ID())) {
//                fav_counter++;
//
//                // The Course
//                Toolbar fav_bar = new Toolbar(this);
//                fav_bar.setTitle(course.getName());
//                fav_bar.setClickable(true);
//                fav_bar.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        info_dialog.create().show();
//                    }
//                });
//                fav_bar.setNavigationIcon(R.drawable.ic_delete);
//                fav_bar.setNavigationOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) { // delete the course from favorites if the delete button is pressed
//                        favorites_editor.remove(course.getCourse_ID());
//                        favorites_editor.commit();
//                        recreate();
//                    }
//                });
//                inner_layout.addView(fav_bar, match_parent_ll);
//            }
//        }
//
//        // show a text if there aren't any courses selected for favorites yet
//        if (fav_counter == 0) {
//            TextView no_favs_yet = new TextView(this);
//            no_favs_yet.setText("No Favorites selected yet!");
//            no_favs_yet.setTextSize((float)20.0);
//            no_favs_yet.setPadding(0, 30, 0, 0);
//            inner_layout.addView(no_favs_yet, match_parent_ll);
//        }
//
//        sv.addView(inner_layout);
    }

    class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return favorite_courses.size();
        }

        @Override
        public Object getItem(int position) {
            return favorite_courses.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.courses_layout, null);

            final ImageView fav_icon = (ImageView) convertView.findViewById(R.id.fav_icon);
            final TextView name = (TextView) convertView.findViewById(R.id.text_name);
            final TextView shortName = (TextView) convertView.findViewById(R.id.text_descr);

            final Course course = favorite_courses.get(position);


            if (!favorite_courses.get(0).getName().equals("No Favorites yet.")) {

                fav_icon.setImageResource(R.drawable.ic_delete);

                fav_icon.setClickable(true);
                fav_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        favorites_editor.remove(course.getCourse_ID());
                        favorites_editor.commit();
                        recreate();
                    }
                });

                name.setText(course.getName());
                name.setTextSize(14);
                shortName.setText(course.getShortName());
                shortName.setTextSize(10);
            } else {
                fav_icon.setImageResource(R.drawable.ic_nothind_selected);
                name.setText(course.getName());
                shortName.setText("");
            }

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
