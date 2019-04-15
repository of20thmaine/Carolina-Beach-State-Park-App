package com.cbsp.carolinabeachtours;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.support.v4.view.GravityCompat;
import android.widget.RelativeLayout;
import java.util.List;

public class LocationListActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    public static List<Location> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        Intent intent = getIntent();
        Location.LocationType locationType = (Location.LocationType)
                intent.getSerializableExtra("LocationType");

        FirestoreConnector fp = new FirestoreConnector();
        fp.getAllLocationsOfType(locationType, this);

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
    }

    void drawData(List<Location> locations) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Visit " + locations.get(0).typeAsString() + "s");
        LocationListActivity.locations = locations;

        RecyclerView locationRecycler = findViewById(R.id.list_recycler);

        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(locations);
        locationRecycler.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        locationRecycler.setLayoutManager(layoutManager);

        adapter.setListener(new CaptionedImagesAdapter.Listener() {
            public void onClick(int position) {
            Intent intent = new Intent(LocationListActivity.this, LocationActivity.class);
            intent.putExtra(LocationActivity.LOCATION_INDEX, position);
            startActivity(intent);
            }
        });
    }

    void dataLoadFailed() {
        RelativeLayout bigPapa = findViewById(R.id.top_parent);
        Snackbar snackbar = Snackbar.make(bigPapa, "There was a connection failure.",
                Snackbar.LENGTH_LONG);
        snackbar.show();
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
}
