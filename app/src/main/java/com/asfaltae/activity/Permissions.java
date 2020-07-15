package com.asfaltae.activity;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissions {

    public static boolean validatePermissions(String[] permissions, Activity activity, int requestCode){

        if (Build.VERSION.SDK_INT >= 23 ){

            List<String> whitelist = new ArrayList<>();

            // Cycles through past permissions checking one by one if permission is already released
            for ( String permission : permissions ){
                boolean havePermission = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
                if ( !havePermission ) whitelist.add(permission);
            }

            // If the list is empty, it is not necessary to request permission
            if ( whitelist.isEmpty() ) return true;
            String[] newPermissions = new String[ whitelist.size() ];
            whitelist.toArray( newPermissions );

            // Request permission
            ActivityCompat.requestPermissions(activity, newPermissions, requestCode );


        }

        return true;

    }

}
