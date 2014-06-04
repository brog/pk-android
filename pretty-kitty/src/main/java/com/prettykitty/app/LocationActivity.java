package com.prettykitty.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.util.Log;


public class LocationActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener{

    private static final String TAG = "LocationActivity";

    private String userLocation;
    private Context context;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        context = (Context) this;
        sharedPref = context.getSharedPreferences(
                getString(R.string.global_prefs), Context.MODE_PRIVATE);

        final String savedLocation = sharedPref.getString(getString(R.string.key_location), null);

        final String locationsArr[] = (String[]) PrettyData.SPA_LOCATIONS.keySet().toArray(new String[0]);
        final Spinner spinner = (Spinner) findViewById(R.id.location_spinner);
        spinner.setOnItemSelectedListener(this);


        // Create an ArrayAdapter using the string array and a default spinner layout
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                locationsArr);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        Button btn = (Button) findViewById(R.id.submit_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "userLocation: " + userLocation);

                saveSelection(userLocation);
                finish();
            }
        });

        Log.i(TAG, "saved: " + savedLocation);

        if (null != savedLocation) {
            int spinnerPosition = adapter.getPosition(savedLocation);
            //set the default according to value
            spinner.setSelection(spinnerPosition);
        }
    }
    private void saveSelection(final String location){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.key_location), location);
        editor.commit();
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

        userLocation = parent.getItemAtPosition(pos).toString();
        Log.i(TAG, "item: "+userLocation);
    }

    // Another interface callback
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        sharedPref = null;
        userLocation = null;
    }
}
