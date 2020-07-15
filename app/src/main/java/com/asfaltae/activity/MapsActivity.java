package com.asfaltae.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.NoCopySpan;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.asfaltae.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String[] permissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Validate permissions
        Permissions.validatePermissions(permissions, this, 1);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
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
        public void onMapReady (GoogleMap googleMap){
            mMap = googleMap;

            // Object responsible for managing the user's location
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Log.d("Localização", "onLocationChanged" + location.toString());

                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    LatLng userLocation = new LatLng(latitude, longitude);
                    mMap.addMarker(new MarkerOptions()
                            .position(userLocation)
                            .title("Meu local")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.car))
                    );
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 20));

                    Toast.makeText(MapsActivity.this,
                            "Latitude: " + latitude + " \nLongitude: " + longitude,
                            Toast.LENGTH_LONG).show();

                    CircleOptions circleOptions = new CircleOptions();
                    circleOptions.center(userLocation);
                    circleOptions.radius(25);
                    circleOptions.strokeWidth(5);
                    circleOptions.strokeColor(Color.argb(80, 255, 165, 0));
                    circleOptions.fillColor(Color.argb(80, 255, 165, 0));
                    mMap.addCircle(circleOptions);

                    //beginning camera




                    //end camera

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };

            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        10000,
                        10,
                        locationListener
                );
            }




        /*
        // Circle
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(saoluis);
        circleOptions.radius(25);
        circleOptions.strokeWidth(5);
        circleOptions.strokeColor(Color.argb(128, 255, 165, 0));
        circleOptions.fillColor(Color.argb(128, 255, 165, 0));
        mMap.addCircle(circleOptions);
        */


        // Add Click Event
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                double latitude = latLng.latitude;
                double longitude = latLng.longitude;

                Toast.makeText(MapsActivity.this,
                        "Latitude: " + latitude + "\nLongitude: " + longitude,
                        Toast.LENGTH_LONG).show();

                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("ATENÇÃO!")
                        .snippet("Descrição do defeito da via")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.caution))
                );

            }

            // Camera

            static final int REQUEST_IMAGE_CAPTURE = 1;

            private void dispatchTakePictureIntent() {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }

            // Fim camera
        });

        }

        @Override
        public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            for (int permissionResult : grantResults) {

                if (permissionResult == PackageManager.PERMISSION_DENIED) {

                    alertValidationPermission();
                } else if (permissionResult == PackageManager.PERMISSION_GRANTED) {

                    // Retrieve the user's location
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                10000,
                                10,
                                locationListener
                        );

                    }

                }

            }

        }

        private void alertValidationPermission () {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Permissões negadas!");
            builder.setMessage("Para utilizar o app é necessário aceitar as permissões!");
            builder.setCancelable(false);
            builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

        }
}
