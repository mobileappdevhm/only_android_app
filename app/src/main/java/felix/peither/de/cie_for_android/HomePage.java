package felix.peither.de.cie_for_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {

    /**
     * The nav bar at the bottom of the screen
     */
    BottomNavigationView nav_bar;

    /**
     * A scroll view, which is the most outer layout of the app
     */
    ScrollView sv;
    //Toolbar toolbar;

    /**
     * gets the courses
     */
    private final CourseGetter courseGetter = new CourseGetter();

//    private final Drawable fav_full = getResources().getDrawable(R.drawable.ic_favorite_full_red);
//    private final Drawable fav_empty = getResources().getDrawable(R.drawable.ic_favorite_red);

    /**
     *  SharedPrefs to save the favorites
     */
    private SharedPreferences favorites;

    /**
     * SharedPrefs Editor to edit saved favorites, delete them or put new ones in
     */
    private SharedPreferences.Editor editFavorites;

    /**
     * all courses
     */
    private ArrayList<Course> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        //toolbar = (Toolbar) findViewById(R.id.courses_toolbar);

        favorites = getSharedPreferences("", Context.MODE_PRIVATE);
        editFavorites = favorites.edit();

        courseList = courseGetter.getCourses();

        sv = (ScrollView) findViewById(R.id.scroll_view);

        nav_bar = (BottomNavigationView) findViewById(R.id.nav_bar);
        nav_bar.setBackgroundColor(Color.LTGRAY);

        // tell the buttons on the navigation bar what to du when they are clicked
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
                }
                return true;
            }
        });
        make_home_page();
        nav_bar.setSelectedItemId(R.id.home_button);
    }


    /**
     * creating the menu in the toolbars of each page.
     * @param menu a Menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    /**
     * builds the course page.
     */
    private void make_course_page() {

        sv.removeAllViews();

        // The Toolbar for this page
        Toolbar toolbar = new Toolbar(this);
        toolbar.setTitle("Courses");
        toolbar.setBackgroundColor(Color.LTGRAY);
        setSupportActionBar(toolbar);

        // the outer layout for the courses and inner layout to the scroll view
        LinearLayout.LayoutParams match_parent_ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout inner_layout = new LinearLayout(this);
        inner_layout.setOrientation(LinearLayout.VERTICAL);
        inner_layout.addView(toolbar, match_parent_ll);

        // this loop creates a tile for each course and adds it to the layout
        for (final Course course: courseList) {
            final Toolbar course_bar = new Toolbar(this);
            course_bar.setTitle(course.getName());
            if (favorites.contains(Integer.toString(course.getCourse_ID()))) {
                course_bar.setNavigationIcon(R.drawable.ic_favorite_full_red);
            } else {
                course_bar.setNavigationIcon(R.drawable.ic_favorite_red);
            }
            course_bar.setPadding(5,5,5,5);
            course_bar.setClickable(true);
            final AlertDialog.Builder info_dialog = new AlertDialog.Builder(this)
                    .setTitle("Course Description")
                    .setMessage("This is a description of a course :)")
                    .setNeutralButton("Close", null);
            course_bar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    info_dialog.create().show();
                }
            });
            course_bar.setSubtitle("by Prof. : " + course.getProfessor());
            course_bar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { // save or delete the courses from
                                                            // favorites and change the icon accordingly
                    if (favorites.contains(Integer.toString(course.getCourse_ID()))) {
                        course_bar.setNavigationIcon(R.drawable.ic_favorite_red);
                        editFavorites.remove(Integer.toString(course.getCourse_ID()));
                        editFavorites.commit();
                    } else {
                        course_bar.setNavigationIcon(R.drawable.ic_favorite_full_red);
                        Gson gson = new Gson();
                        String json = gson.toJson(course);
                        editFavorites.putString(Integer.toString(course.getCourse_ID()), json);
                        editFavorites.commit();
                    }
                }
            });

            inner_layout.addView(course_bar, match_parent_ll);
        }

        // add the layout with all courses inside into the scroll view
        sv.addView(inner_layout);
    }

    /**
     * build the home page. (has nothing on it yet)
     */
    private void make_home_page() {

        sv.removeAllViews();

        Toolbar toolbar = new Toolbar(this);
        toolbar.setTitle("Home");
        toolbar.setBackgroundColor(Color.LTGRAY);
        setSupportActionBar(toolbar);
        LinearLayout inner_layout = new LinearLayout(this);

        LinearLayout.LayoutParams clp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        inner_layout.addView(toolbar, clp);

        sv.addView(inner_layout, clp);

    }

    /**
     * builds the favorites page.
     */
    private void make_favorite_page() {

        // remove the previous page
        sv.removeAllViews();

        int fav_counter = 0;

        // Toolbar in the favorites page
        Toolbar toolbar = new Toolbar(this);
        toolbar.setTitle("Favorites");
        toolbar.setBackgroundColor(Color.LTGRAY);
        setSupportActionBar(toolbar);

        // outer Layout for the favorite tiles
        // and inner layout for the scroll view
        LinearLayout inner_layout = new LinearLayout(this);
        inner_layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams clp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        inner_layout.addView(toolbar, clp);

        // Info pop up for the course
        final AlertDialog.Builder info_dialog = new AlertDialog.Builder(this)
                .setTitle("Course Description")
                .setMessage("This is a description of a course :)")
                .setNeutralButton("Close", null);

        // this loop adds all favorites to the layout
        for (final Course course: courseList) {
            if (favorites.contains(Integer.toString(course.getCourse_ID()))) {
                fav_counter++;

                // The Course
                Toolbar fav_bar = new Toolbar(this);
                fav_bar.setTitle(course.getName());
                fav_bar.setClickable(true);
                fav_bar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        info_dialog.create().show();
                    }
                });
                fav_bar.setSubtitle("by Prof. : " + course.getProfessor());
                fav_bar.setNavigationIcon(R.drawable.ic_delete);
                fav_bar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { // delete the course from favorites if the delete button is pressed
                        editFavorites.remove(Integer.toString(course.getCourse_ID()));
                        editFavorites.commit();
                        make_favorite_page();
                    }
                });
                inner_layout.addView(fav_bar, clp);
            }
        }

        // show a text if there aren't any courses selected for favorites yet
        if (fav_counter == 0) {
            TextView no_favs_yet = new TextView(this);
            no_favs_yet.setText("No Favorites selected yet!");
            no_favs_yet.setTextSize((float)20.0);
            no_favs_yet.setPadding(0, 30, 0, 0);
            no_favs_yet.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            inner_layout.addView(no_favs_yet, clp);
        }

        sv.addView(inner_layout);

    }
}
