package com.cbsp.carolinabeachtours;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.design.widget.NavigationView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.support.v4.view.GravityCompat;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.nav_about);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                                                                    R.string.nav_open_drawer,
                                                                    R.string.nav_close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.drawData();
    }

    private void drawData() {
        TextView about = findViewById(R.id.about_text);
        about.setText(R.string.about_info);
        TextView aboutUrl = findViewById(R.id.about_archive_url);
        aboutUrl.setText(Html.fromHtml(getResources().getString(R.string.archives_url)));
        aboutUrl.setClickable(true);
        aboutUrl.setMovementMethod(LinkMovementMethod.getInstance());
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
}
