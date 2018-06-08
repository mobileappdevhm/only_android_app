package felix.peither.de.cie_for_android;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private SharedPreferences favorites;
    private SharedPreferences.Editor favorites_editor;

    private static final String SHARED_PREFS_NAME = "FAVORITES";

    private CourseGetter courseGetter;
    private List<Course> course_list;

    ScrollView sv;
    Toolbar favorites_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        favorites = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
        favorites_editor = favorites.edit();

        favorites_toolbar = (Toolbar) findViewById(R.id.favorites_toolbar);
        favorites_toolbar.setTitleTextColor(Color.WHITE);
        favorites_toolbar.setNavigationIcon(R.drawable.ic_arrow_backward_white);
        favorites_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sv = (ScrollView) findViewById(R.id.favorites_scroll_view);

        courseGetter = new CourseGetter();
        course_list = courseGetter.getCourses();

        LinearLayout.LayoutParams match_parent_ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout inner_layout = new LinearLayout(this);
        inner_layout.setOrientation(LinearLayout.VERTICAL);

        final AlertDialog.Builder info_dialog = new AlertDialog.Builder(this)
                .setTitle("Course Description")
                .setMessage("This is a description of a course :)")
                .setNeutralButton("Close", null);

        int fav_counter = 0;

        // this loop adds all favorites to the layout
        for (final Course course: course_list) {
            if (favorites.contains(course.getCourse_ID())) {
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
                        favorites_editor.remove(course.getCourse_ID());
                        favorites_editor.commit();
                        recreate();
                    }
                });
                inner_layout.addView(fav_bar, match_parent_ll);
            }
        }

        // show a text if there aren't any courses selected for favorites yet
        if (fav_counter == 0) {
            TextView no_favs_yet = new TextView(this);
            no_favs_yet.setText("No Favorites selected yet!");
            no_favs_yet.setTextSize((float)20.0);
            no_favs_yet.setPadding(0, 30, 0, 0);
            inner_layout.addView(no_favs_yet, match_parent_ll);
        }

        sv.addView(inner_layout);
    }
}
