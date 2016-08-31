package com.example.admin.itsmygang.Services;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.itsmygang.Activity.SignupActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MyService extends Service {

    FirebaseAuth auth;
    DatabaseReference rootref;
 public   DatabaseReference userlocref;
    LocationManager lmanager;
public static final String TAG="logs";


    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        auth = FirebaseAuth.getInstance();
        rootref = FirebaseDatabase.getInstance().getReference();
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(this, SignupActivity.class));
            stopSelf();
        }

        userlocref = rootref.child("users").child(auth.getCurrentUser().getUid()).child("location");
        lmanager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!lmanager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(MyService.this, "Please turn on gps", Toast.LENGTH_SHORT).show();
        }

        LocationListener locationListener = new MyLocationListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return super.onStartCommand(intent,flags,startId);
        }
        lmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
       return null;
    }


    public class MyLocationListener extends MyService implements android.location.LocationListener{

        @Override
        public void onLocationChanged(Location location) {

         userlocref.setValue(location);

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }

}
