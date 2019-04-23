package com.cbsp.carolinabeachtours;

import android.content.Intent;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class LocationActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    public static final String LOCATION_INDEX = "li";
    Location location;
    private final StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

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

        TextView textView = findViewById(R.id.info_text);
        textView.setText(location.getAbout());

        ImageView imageView = findViewById(R.id.info_image);
        StorageReference image = mStorageRef.child(location.getImageFile());
        GlideApp.with(this)
                .load(image)
                .into(imageView);
        imageView.setContentDescription(location.getName());
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
            case R.id.nav_about:
                intent = new Intent(this, AboutActivity.class);
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

    }

    public void visitAnimals(android.view.View v) {
        Intent intent = new Intent(this, LocationListActivity.class);
        if (location.getType() == Location.LocationType.ECOSYSTEM) {
            intent.putExtra("InType", location.getName());
        } else {
            intent.putExtra("InType", location.getIsIn());
        }
        intent.putExtra("FindType", Location.LocationType.ANIMAL);
        intent.putExtra("WhoBCallin", location.getName());

        startActivity(intent);
    }

    public void visitPlants(android.view.View v) {
        Intent intent = new Intent(this, LocationListActivity.class);
        if (location.getType() == Location.LocationType.ECOSYSTEM) {
            intent.putExtra("InType", location.getName());
        } else {
            intent.putExtra("InType", location.getIsIn());
        }
        intent.putExtra("FindType", Location.LocationType.PLANT);
        intent.putExtra("WhoBCallin", location.getName());

        startActivity(intent);
    }

}
