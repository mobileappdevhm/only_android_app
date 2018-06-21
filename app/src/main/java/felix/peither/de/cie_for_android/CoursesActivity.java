package felix.peither.de.cie_for_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

public class CoursesActivity extends AppCompatActivity {

    Toolbar courses_toolbar;
    ScrollView sv;

    private static final String SHARED_PREFS_NAME = "FAVORITES";

    private List<Course> course_list;

    private CourseGetter courseGetter;

    SharedPreferences favorites;
    SharedPreferences.Editor favorites_editor;

    List<Thread> allThreads = new ArrayList<>();

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



//        sv = (ScrollView) findViewById(R.id.courses_scroll_view);
        courses_toolbar = (Toolbar) findViewById(R.id.courses_toolbar);
        courses_toolbar.setTitleTextColor(Color.WHITE);
        courses_toolbar.setNavigationIcon(R.drawable.ic_arrow_backward_white);
        courses_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        favorites = getSharedPreferences("FAVORITES", MODE_PRIVATE);
        favorites_editor = favorites.edit();
//
//        courseGetter = new CourseGetter();
//        course_list = courseGetter.doInBackground();

//        LinearLayout.LayoutParams match_parent_ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        LinearLayout inner_layout = new LinearLayout(this);
//        inner_layout.setOrientation(LinearLayout.VERTICAL);
//
//        for (final Course course: course_list) {
//            final Toolbar course_bar = new Toolbar(this);
//            course_bar.setTitle(course.getName());
//            if (favorites.contains(course.getCourse_ID())) {
//                course_bar.setNavigationIcon(R.drawable.ic_favorite_full_red);
//            } else {
//                course_bar.setNavigationIcon(R.drawable.ic_favorite_red);
//            }
//            course_bar.setPadding(5,5,5,5);
//            course_bar.setClickable(true);
//            final AlertDialog.Builder info_dialog = new AlertDialog.Builder(this)
//                    .setTitle("Course Description")
//                    .setMessage("This is a description of a course :)")
//                    .setNeutralButton("Close", null);
//            course_bar.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    info_dialog.create().show();
//                }
//            });
//            course_bar.setNavigationOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) { // save or delete the courses from
//                    // favorites and change the icon accordingly
//                    if (favorites.contains(course.getCourse_ID())) {
//                        course_bar.setNavigationIcon(R.drawable.ic_favorite_red);
//                        favorites_editor.remove(course.getCourse_ID());
//                        favorites_editor.commit();
//                    } else {
//                        course_bar.setNavigationIcon(R.drawable.ic_favorite_full_red);
//                        Gson gson = new Gson();
//                        String json = gson.toJson(course);
//                        favorites_editor.putString(course.getCourse_ID(), json);
//                        favorites_editor.commit();
//                    }
//                }
//            });
//
//            inner_layout.addView(course_bar, match_parent_ll);
//        }
//
//        // add the layout with all courses inside into the scroll view
//        sv.addView(inner_layout);
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
}
