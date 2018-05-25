package felix.peither.de.cie_for_android;

import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.gson.Gson;

import java.util.List;

import velez2210.j.logindemo.R;

public class CoursesActivity extends AppCompatActivity {

    Toolbar courses_toolbar;
    ScrollView sv;

    private List<Course> course_list;

    private CourseGetter courseGetter;

    SharedPreferences favorites;
    SharedPreferences.Editor favorites_editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        sv = (ScrollView) findViewById(R.id.courses_scroll_view);
        courses_toolbar = (Toolbar) findViewById(R.id.courses_toolbar);

        favorites = getSharedPreferences("FAVORITES", MODE_PRIVATE);
        favorites_editor = favorites.edit();

        courseGetter = new CourseGetter();
        course_list = courseGetter.getCourses();

        LinearLayout.LayoutParams match_parent_ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout inner_layout = new LinearLayout(this);
        inner_layout.setOrientation(LinearLayout.VERTICAL);

        for (final Course course: course_list) {
            final Toolbar course_bar = new Toolbar(this);
            course_bar.setTitle(course.getName());
//            if (favorites.contains(Integer.toString(course.getCourse_ID()))) {
//                course_bar.setNavigationIcon(R.drawable.ic_favorite_full_red);
//            } else {
//                course_bar.setNavigationIcon(R.drawable.ic_favorite_red);
//            }
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
//                    if (favorites.contains(Integer.toString(course.getCourse_ID()))) {
//                        course_bar.setNavigationIcon(R.drawable.ic_favorite_red);
//                        editFavorites.remove(Integer.toString(course.getCourse_ID()));
//                        editFavorites.commit();
//                    } else {
//                        course_bar.setNavigationIcon(R.drawable.ic_favorite_full_red);
//                        Gson gson = new Gson();
//                        String json = gson.toJson(course);
//                        editFavorites.putString(Integer.toString(course.getCourse_ID()), json);
//                        editFavorites.commit();
//                    }
                }
            });

            inner_layout.addView(course_bar, match_parent_ll);
        }

        // add the layout with all courses inside into the scroll view
        sv.addView(inner_layout);
    }
}
