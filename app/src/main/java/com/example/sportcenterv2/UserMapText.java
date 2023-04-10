package com.example.sportcenterv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;

public class UserMapText extends AppCompatActivity {

    private TextView Coordinates, Address;
    private LocationCallback locationCallback;
    LocationRequest locationRequest;

    String[] perms = {"android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.INTERNET",
            "android.permission.ACCESS_NETWORK_STATE"};

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_map_text);

        getSupportActionBar().setTitle("View Map Text");

        Coordinates = findViewById(R.id.Coordinates);
        Address = findViewById(R.id.Address);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //check permission/ permission request
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, perms, 200);
            return;
        }

        locationRequest = LocationRequest.create();

        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000); //in millisecond, 1000 = 1 second
        locationRequest.setFastestInterval(2000); //2 second

        locationCallback = new LocationCallback() {
            public void onLocationResult(LocationResult locationResult) {
                if(locationResult == null) {
                    Toast.makeText(getApplicationContext(), "Unable to detect location", Toast.LENGTH_SHORT).show();
                    return;
                }
                for(Location location : locationResult.getLocations()) {
                    //Update UI with location data
                    double lat = location.getLatitude();
                    double lon = location.getLongitude();

                    Coordinates.setText("" + lat + ", " + lon);
                    Geocoder geocoder = new Geocoder(getApplicationContext());

                    List<android.location.Address> addressList = null;
                    try {
                        addressList = geocoder.getFromLocation(lat, lon, 1);
                        Address address = addressList.get(0);
                        String line = address.getAddressLine(0);
                        String area = address.getAdminArea();
                        String locality = address.getLocality();
                        String country = address.getCountryName();
                        String postcode = address.getPostalCode();
                        Address.setText(line + "\n" + area + "\n" + locality + "\n" + country + "\n" + postcode);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();

                Coordinates.setText("" + lat + ", " + lon);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    public void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, perms, 200);
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }
}