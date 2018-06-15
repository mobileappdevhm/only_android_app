package felix.peither.de.cie_for_android;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;

public class LocationsActivity extends AppCompatActivity {

    Toolbar locations_toolbar;

    LinearLayout.LayoutParams match_parent_lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

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

    }

}
