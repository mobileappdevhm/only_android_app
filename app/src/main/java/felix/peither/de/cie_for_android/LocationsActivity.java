package felix.peither.de.cie_for_android;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;

public class LocationsActivity extends NavigationDrawer {

    Toolbar locations_toolbar;
    private DrawerLayout drawer;

    LinearLayout.LayoutParams match_parent_lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_courses:
                Intent intent = new Intent(LocationsActivity.this, CoursesActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_favorites:
                intent = new Intent(LocationsActivity.this, FavoritesActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_locations:
                intent = new Intent(LocationsActivity.this, LocationsActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_schedule:
                intent = new Intent(LocationsActivity.this, ScheduleActivity.class);
                startActivity(intent);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

//      setSupportActionBar(locations_toolbar);
        /*
        For zooming in and out by double tapping or scrolling into the map
         */
        PhotoView photoViewKarlstrasse = (PhotoView) findViewById(R.id.karlstrasse);
        photoViewKarlstrasse.setImageResource(R.drawable.karlstrasse);

        PhotoView photoViewLothstrasse = (PhotoView) findViewById(R.id.lothstrasse);
        photoViewLothstrasse.setImageResource(R.drawable.lothstrasse);

        PhotoView photoViewPasing = (PhotoView) findViewById(R.id.pasing);
        photoViewPasing.setImageResource(R.drawable.pasing);

        Toolbar toolbar = findViewById(R.id.locations_toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer. addDrawerListener(toggle);
        toggle.syncState();

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
