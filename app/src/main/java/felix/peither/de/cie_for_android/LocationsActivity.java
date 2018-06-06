package felix.peither.de.cie_for_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;

public class LocationsActivity extends AppCompatActivity {

//    ScrollView sv;
    Toolbar locations_toolbar;

//    ImageView karlstrasse;
//    ImageView lothstrasse;
//    ImageView pasing;
//
//    LinearLayout campus_karlstrasse;
//    LinearLayout campus_lothstrasse;
//    LinearLayout campus_pasing;
//
//    TextView text_karlstrasse;
//    TextView text_lothstrasse;
//    TextView text_pasing;

    LinearLayout.LayoutParams match_parent_lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

//        sv = (ScrollView) findViewById(R.id.locations_scroll_view)
//        locations_toolbar = (Toolbar) findViewById(R.id.locations_toolbar);

//        setSupportActionBar(locations_toolbar);



        /*
        For zooming in and out by double tapping or scrolling into the map
         */
        PhotoView photoViewKarlstrasse = (PhotoView) findViewById(R.id.karlstrasse);
        photoViewKarlstrasse.setImageResource(R.drawable.karlstrasse);

        PhotoView photoViewLothstrasse = (PhotoView) findViewById(R.id.lothstrasse);
        photoViewLothstrasse.setImageResource(R.drawable.lothstrasse);

        PhotoView photoViewPasing = (PhotoView) findViewById(R.id.pasing);
        photoViewPasing.setImageResource(R.drawable.pasing);

//        make_campus_layouts();
//        make_images();
//        make_texts();
//        add_images_and_texts();

//        sv.addView(campus_karlstrasse);
//        sv.addView(campus_lothstrasse);
//        sv.addView(campus_pasing);

    }

//    private void add_images_and_texts() {
//        campus_karlstrasse.addView(karlstrasse, match_parent_lp);
//        campus_karlstrasse.addView(text_karlstrasse, match_parent_lp);
//
//        campus_lothstrasse.addView(lothstrasse, match_parent_lp);
//        campus_lothstrasse.addView(text_lothstrasse, match_parent_lp);
//
//        campus_pasing.addView(pasing, match_parent_lp);
//        campus_pasing.addView(text_pasing, match_parent_lp);
//    }
//
//    private void make_texts() {
//        text_karlstrasse = new TextView(this);
//        text_lothstrasse = new TextView(this);
//        text_pasing = new TextView(this);
//
//        text_karlstrasse.setText("Campus Karlstrasse");
//        text_lothstrasse.setText("Campus Lothstrasse");
//        text_pasing.setText("Campus Pasing");
//    }
//
//    private void make_images() {
//        karlstrasse = new ImageView(this);
//        lothstrasse = new ImageView(this);
//        pasing = new ImageView(this);

//        karlstrasse.setImageDrawable(getResources().getDrawable(R.drawable.karlstrasse));
//        lothstrasse.setImageDrawable(getResources().getDrawable(R.drawable.lothstrasse));
//        pasing.setImageDrawable(getResources().getDrawable(R.drawable.pasing));
//    }
//
//    private void make_campus_layouts() {
//        campus_karlstrasse = new LinearLayout(this);
//        campus_lothstrasse = new LinearLayout(this);
//        campus_pasing = new LinearLayout(this);
//
//        campus_karlstrasse.setOrientation(LinearLayout.VERTICAL);
//        campus_lothstrasse.setOrientation(LinearLayout.VERTICAL);
//        campus_lothstrasse.setOrientation(LinearLayout.VERTICAL);
//    }
}
