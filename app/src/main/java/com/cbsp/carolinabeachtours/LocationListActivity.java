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

import java.util.ArrayList;
import java.util.List;

public class LocationListActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    public static List<Location> locations;
    Toolbar toolbar;
    RecyclerView locationRecycler;
    CaptionedImagesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        Intent intent = getIntent();
        Location.LocationType locationType = (Location.LocationType)
                intent.getSerializableExtra("LocationType");

        FirestoreConnector fp = new FirestoreConnector();
        fp.getAllLocationsOfType(locationType, this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                                                                    R.string.nav_open_drawer,
                                                                    R.string.nav_close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /* So, when the firestore connection thread completes it updates the data used in the
         * recycler view BUT recycler view needs an adapter in onCreate otw you get horrible errors.
         * So I bind it to an empty adapter here, and when the db thread finishes it calls drawData()
         * which refreshes the data inside the adapter.
         */
        LocationListActivity.locations = new ArrayList<>();
        locationRecycler = findViewById(R.id.list_recycler);
        adapter = new CaptionedImagesAdapter(locations, this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        locationRecycler.setLayoutManager(layoutManager);
        locationRecycler.setAdapter(adapter);

        adapter.setListener(new CaptionedImagesAdapter.Listener() {
            public void onClick(int position) {
                Intent intent = new Intent(LocationListActivity.this, LocationActivity.class);
                intent.putExtra(LocationActivity.LOCATION_INDEX, position);
                startActivity(intent);
            }
        });
    }

    /**
     * Called by firestore-connector when it finishes data retrieval from its own thread, refreshes
     * the recycler view adapter data.
     * @param locations: List of locations.
     */
    void drawData(List<Location> locations) {
        LocationListActivity.locations = locations;
        toolbar.setTitle("Visit " + locations.get(0).typeAsString() + "s");

        adapter.swapDataSet(LocationListActivity.locations);
    }

    /**
     * Called by fire-store connector is db call fails. Displays error message in toast.
     */
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
}
