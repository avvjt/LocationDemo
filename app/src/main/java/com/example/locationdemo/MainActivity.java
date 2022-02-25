package com.example.locationdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button getLocation;
    TextView textView1, textView2, textView3, textView4, textView5;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLocation = findViewById(R.id.get_location);
        textView1 = findViewById(R.id.tv_1);
        textView2 = findViewById(R.id.tv_2);
        textView3 = findViewById(R.id.tv_3);
        textView4 = findViewById(R.id.tv_4);
        textView5 = findViewById(R.id.tv_5);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    getLocations();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });
    }

    private void getLocations() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(MainActivity.this,
                                Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );
                        textView1.setText(Html.fromHtml("<font color='#6200EE'><b>Latitude :</b><br></font>"
                                + addresses.get(0).getLatitude()));

                        textView2.setText(Html.fromHtml("<font color='#6200EE'><b>Longitude : </b><br></font>"
                                + addresses.get(0).getLongitude()));

                        textView3.setText(Html.fromHtml("<font color='#6200EE'><b>Country name : </b><br></font>"
                                + addresses.get(0).getCountryName()));

                        textView4.setText(Html.fromHtml("<font color='#6200EE'><b>Locality : </b><br></font>"
                                + addresses.get(0).getLocality()));

                        textView5.setText(Html.fromHtml("<font color='#6200EE'><b>Address : </b><br></font>"
                                + addresses.get(0).getAddressLine(0)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}