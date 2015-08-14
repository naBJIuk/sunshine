package com.example.android.sunshine.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String FORECASTFRAGMENT_TAG = "forecastFragment";

    private String mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment(), FORECASTFRAGMENT_TAG)
                    .commit();
        }

        mLocation = Utility.getPreferredLocation(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mLocation != Utility.getPreferredLocation(this)) {
            ForecastFragment ff = (ForecastFragment) getSupportFragmentManager().findFragmentByTag(FORECASTFRAGMENT_TAG);
            ff.onLocationChanged();
            mLocation = Utility.getPreferredLocation(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ac_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.action_map) {
            openPreferredLocationMap();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openPreferredLocationMap() {
        String location = Utility.getPreferredLocation(this);

        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q", location)
                .build();

        Intent intent = new Intent(Intent.ACTION_VIEW, geoLocation);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } Log.d(LOG_TAG, "No activity for intent");
    }

}
