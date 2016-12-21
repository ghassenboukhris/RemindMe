package com.example.ghassen.myproject;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapFamilyActivity extends AppCompatActivity {

    MarkerOptions markerOptions;
    LatLng latLng;
    private GoogleMap map;
    //Intent myIntent;
    //String text = myIntent.getStringExtra("key");
    //String text1 = myIntent.getStringExtra("key1");
    //Double d = Double.parseDouble(text);
    //Double d1 = Double.parseDouble(text1);
    private final LatLng Location_Burnaby=new LatLng(35.82115632,10.59279918);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_family);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        map=((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        CameraUpdate update= CameraUpdateFactory.newLatLngZoom(Location_Burnaby, 14);
        map.animateCamera(update);
        //String turl = getIntent().getStringExtra("EXTRA_SESSION_ID");

        //Uri feedUri = Uri.parse(turl);

        //ImageView profilepicture=(ImageView) findViewById(R.id.imageView2);
        //profilepicture.setImageURI(feedUri);



        // Setting a click event handler for the map
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng arg0) {

                // Getting the Latitude and Longitude of the touched location
                latLng = arg0;

                // Clears the previously touched position
                map.clear();

                // Animating to the touched position
                map.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Creating a marker
                markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Placing a marker on the touched position
                map.addMarker(markerOptions);

                // Adding Marker on the touched location with address
                new ReverseGeocodingTask(getBaseContext()).execute(latLng);

            }
        });
    }
    private class ReverseGeocodingTask extends AsyncTask<LatLng, Void, String> {
        Context mContext;

        public ReverseGeocodingTask(Context context){
            super();
            mContext = context;
        }

        // Finding address using reverse geocoding
        @Override
        protected String doInBackground(LatLng... params) {
            Geocoder geocoder = new Geocoder(mContext);
            double latitude = params[0].latitude;
            double longitude = params[0].longitude;

            List<Address> addresses = null;
            String addressText="";

            try {
                addresses = geocoder.getFromLocation(latitude, longitude,1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(addresses != null && addresses.size() > 0 ){
                Address address = addresses.get(0);

                addressText = String.format("%s, %s, %s",
                        address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                        address.getLocality(),
                        address.getCountryName());
            }

            return addressText;
        }

        @Override
        protected void onPostExecute(String addressText) {
            // Setting the title for the marker.
            // This will be displayed on taping the marker
            markerOptions.title(addressText);

            // Placing a marker on the touched position
            map.addMarker(markerOptions);
            Intent toAddBusinessActivity=new Intent(MapFamilyActivity.this,AddFamilyActivity.class);
            toAddBusinessActivity.putExtra("Adress", addressText);
            startActivity(toAddBusinessActivity);



        }
    }


}
