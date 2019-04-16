package com.cbsp.carolinabeachtours;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.support.v4.view.GravityCompat;
import android.widget.ImageView;
import android.widget.TextView;

public class LocationActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    public static final String LOCATION_INDEX = "li";
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                                                                    R.string.nav_open_drawer,
                                                                    R.string.nav_close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        int i = intent.getIntExtra(LocationActivity.LOCATION_INDEX, 0);
        location = LocationListActivity.locations.get(i);

        getSupportActionBar().setTitle(location.getName());

        ImageView imageView = findViewById(R.id.info_image);
        Drawable drawable = ContextCompat.getDrawable(this,
                location.getImageId());
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(location.getName());

        TextView textView = findViewById(R.id.info_text);
        textView.setText(location.getAbout());
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;

        switch (id) {
            case R.id.nav_home:
                intent = new Intent(this, MainActivity.class);
                break;
            case R.id.nav_ecosystems:
                intent = new Intent(this, LocationListActivity.class);
                intent.putExtra("LocationType", Location.LocationType.ECOSYSTEM);
                break;
            case R.id.nav_plants:
                intent = new Intent(this, LocationListActivity.class);
                intent.putExtra("LocationType", Location.LocationType.PLANT);
                break;
            case R.id.nav_animals:
                intent = new Intent(this, LocationListActivity.class);
                intent.putExtra("LocationType", Location.LocationType.ANIMAL);
                break;
            default:
                intent = new Intent(this, MainActivity.class);
        }

        startActivity(intent);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void visitHere(android.view.View v) {
        // Will deal with later, opens map.
    }
}
