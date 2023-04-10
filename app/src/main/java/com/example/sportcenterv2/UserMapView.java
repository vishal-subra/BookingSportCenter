package com.example.sportcenterv2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

public class UserMapView extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMarkerDragListener  {

        private GoogleMap mMap;
        private GoogleApiClient client;
        private LocationRequest locationRequest;
        private Location lastLocation;
        private Marker currentLocationMarker;
        double latitude, longitude;
        double end_latitude, end_longitude;
        public static final int REQUEST_LOCATION_CODE = 99;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_user_map_view);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                checkLocationPermission();
            }

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
    }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            switch (requestCode) {
                case REQUEST_LOCATION_CODE:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        //permission is granted
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            if (client == null) {
                                buildGoogleApiClient();
                            }
                            mMap.setMyLocationEnabled(true);
                        }
                    } else //permission is denied
                    {
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();

                    }
                    return;
            }
        }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            } else {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }

            mMap.setOnMarkerDragListener(this);
            mMap.setOnMarkerClickListener(this);

        }

        public void onClick(View v) {
            Object dataTransfer[] = new Object[2];
            switch (v.getId()) {
                case R.id.B_search: {
                    EditText tf_location = (EditText) findViewById(R.id.TF_location);
                    String location = tf_location.getText().toString();
                    List<Address> addressList = null;
                    MarkerOptions mo = new MarkerOptions();

                    if (!location.equals("")) {
                        Geocoder geocoder = new Geocoder(this);
                        try {
                            addressList = geocoder.getFromLocationName(location, 5);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        for (int i = 0; i < addressList.size(); i++) {
                            Address myAddress = addressList.get(i);
                            LatLng latLng = new LatLng(myAddress.getLatitude(), myAddress.getLongitude());
                            mo.position(latLng);
                            mo.title("Your search result");
                            mMap.addMarker(mo);
                            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                        }
                    }
                }
                break;
                case R.id.B_to: mMap.clear();
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(end_latitude, end_longitude));
                    markerOptions.title("Destination");

                    float results[] = new float[10];
                    Location.distanceBetween(latitude, longitude, end_latitude, end_longitude, results);
                    markerOptions.snippet("Distance =" + results[0]);
                    mMap.addMarker(markerOptions);
                    //dataTransfer = new  Object[3];
                    //String url = getDirectionsUrl();
                    //GetDirectionsData getDirectionsData = new GetDirectionsData();
                    //dataTransfer[0] = mMap;
                    //dataTransfer[1] = url;
                    //dataTransfer[2] = new LatLng(end_latitude, end_longitude);
                    //getDirectionsData.execute(dataTransfer);

                    break;
                case R.id.B_direction:
                    String map = "Playground no 6, Lot 89, Jalan Industri Semambu 7, Kawasan Perindustrian Semambu, 25350 Kuantan, Pahang";
                    Uri uri = Uri.parse("http://maps.google.co.in/maps?q="+map);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setPackage("com.google.android.apps.maps");
                    startActivity(intent);

                    break;


            }

        }

        //private  String getDirectionsUrl()
        //{
        //StringBuilder googleDirectionsUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        //googleDirectionsUrl.append("origin="+latitude+","+longitude);
        //googleDirectionsUrl.append("&destination="+end_latitude+","+end_longitude);
        //googleDirectionsUrl.append("&key"+"AIzaSyDfHzHhYGsKF1ytS9TLup6DmsHyrA517Bw");

        //return googleDirectionsUrl.toString();
        //}

        protected synchronized void buildGoogleApiClient()
        {
            client = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            client.connect();
        }

        @Override
        public void onLocationChanged(@NonNull Location location) {
            lastLocation = location;

            if (currentLocationMarker != null)
            {
                currentLocationMarker.remove();
            }

            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Location");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

            currentLocationMarker = mMap.addMarker(markerOptions);

            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomBy(10));

            if (client != null)
            {
                LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
            }

        }


        @Override
        public void onConnected(@Nullable Bundle bundle) {
            locationRequest =  new LocationRequest();

            locationRequest.setInterval(1000);
            locationRequest.setFastestInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
            }
        }

        public  boolean checkLocationPermission(){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED )
            {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
                {
                    ActivityCompat.requestPermissions(this, new  String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE );
                }
                else
                {
                    ActivityCompat.requestPermissions(this, new  String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE );
                }
                return false;
            }
            else
                return true;
        }

        @Override
        public void onConnectionSuspended(int i) {

        }

        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        }

        @Override
        public boolean onMarkerClick(Marker marker) {
            marker.setDraggable(true);
            return false;
        }

        @Override
        public void onMarkerDragStart(Marker marker) {

        }

        @Override
        public void onMarkerDrag(Marker marker) {

        }

        @Override
        public void onMarkerDragEnd(Marker marker) {
            end_latitude = marker.getPosition().latitude;
            end_longitude =  marker.getPosition().longitude;

        }
    }