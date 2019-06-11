package com.jdjr.risk.face.local.demo.permission;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by michael on 18-12-10.
 */

public class PermissionsActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermissions(this);
        }
    }
    
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermissions(PermissionsActivity activity) {
        
        final ArrayList<String> needPermissionList = new ArrayList<>();
        
        final int cameraPermissionGranted = activity.checkSelfPermission(Manifest.permission.CAMERA);
        Log.d("FaceLocalPermission", "PermissionsActivity cameraPermissionGranted = " + cameraPermissionGranted);
        if (cameraPermissionGranted != PackageManager.PERMISSION_GRANTED) {
            needPermissionList.add(Manifest.permission.CAMERA);
        }
        
        final int readExternalGranted =  activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        Log.d("FaceLocalPermission", "PermissionsActivity readExternalGranted = " + readExternalGranted);
        if (readExternalGranted != PackageManager.PERMISSION_GRANTED) {
            needPermissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        
        final int writeExternalGranted = activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        Log.d("FaceLocalPermission", "PermissionsActivity writeDiskPermissionGranted = " + writeExternalGranted);
        if (writeExternalGranted != PackageManager.PERMISSION_GRANTED) {
            needPermissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        
        
        
        
        /*
        // Device auth permissions
        <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
        <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
        <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
        <uses-permission android:name="android.permission.INTERNET"/>
        <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
        <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
        */
        
        
        final int readPhoneGranted = activity.checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
        Log.d("FaceLocalPermission", "PermissionsActivity readPhoneGranted = " + readPhoneGranted);
        if (readPhoneGranted != PackageManager.PERMISSION_GRANTED) {
            needPermissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
    
    
//        final int accessWifiGranted = activity.checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE);
//        Log.d("FaceLocalPermission", "PermissionsActivity accessWifiGranted = " + accessWifiGranted);
//        if (accessWifiGranted != PackageManager.PERMISSION_GRANTED) {
//            needPermissionList.add(Manifest.permission.ACCESS_WIFI_STATE);
//        }
    
    
//        final int internetPermissionGranted = activity.checkSelfPermission(Manifest.permission.INTERNET);
//        Log.d("FaceLocalPermission", "PermissionsActivity internetPermissionGranted = " + internetPermissionGranted);
//        if (internetPermissionGranted != PackageManager.PERMISSION_GRANTED) {
//            needPermissionList.add(Manifest.permission.INTERNET);
//        }
    
        final int accessCoarseGranted = activity.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
        Log.d("FaceLocalPermission", "PermissionsActivity accessCoarseGranted = " + accessCoarseGranted);
        if (accessCoarseGranted != PackageManager.PERMISSION_GRANTED) {
            needPermissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
    
        final int accessFineGranted = activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        Log.d("FaceLocalPermission", "PermissionsActivity accessFineGranted = " + accessFineGranted);
        if (accessFineGranted != PackageManager.PERMISSION_GRANTED) {
            needPermissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        
        
        
        
        
        // final boolean hasAllPermissions = hasCameraPermission && hasWriteDiskPermission && hasReadDiskPermission;
    
        // TODO debug remove
    
    
        final boolean hasAllPermissions = (
                cameraPermissionGranted == PackageManager.PERMISSION_GRANTED
                && readExternalGranted == PackageManager.PERMISSION_GRANTED
                && writeExternalGranted == PackageManager.PERMISSION_GRANTED
                
                && readPhoneGranted == PackageManager.PERMISSION_GRANTED
//                && accessWifiGranted == PackageManager.PERMISSION_GRANTED
//                && internetPermissionGranted == PackageManager.PERMISSION_GRANTED
                && accessCoarseGranted == PackageManager.PERMISSION_GRANTED
                && accessFineGranted == PackageManager.PERMISSION_GRANTED
                
                
        );
        
        
        
        
        if (hasAllPermissions) {
//            activity.hasRequestPermissions = true;
            
            
            // initJNI();
            onAllPermissionsGranted();
            activity.finish();
            return;
        } else {
            final int size = needPermissionList.size();
            if (size > 0) {
                String[] requestPermissions = new String[size];
                needPermissionList.toArray(requestPermissions);
                requestNecessaryPermissions(activity, requestPermissions);
                
            }
            
        }
        
    }
    
    private static void requestNecessaryPermissions(Activity activity, String[] requestPermissions) {
        Log.d("FaceLocalPermission", "@@@@@@@ PermissionsActivity requestNecessaryPermissions");
        activity.requestPermissions(requestPermissions, 1);
    
    }
    
    
    
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        for (int grantResult : grantResults) {
            Log.d("FaceLocalPermission", "@@@@@@ onRequestPermissionsResult grantResult = " + grantResult);
        }
        
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if (requestCode == 1) {
            final ArrayList<String> needPermissionList = new ArrayList<>();
    
            if (grantResults != null && grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        if (permissions[i] != null && permissions[i].length() > 0) {
                            needPermissionList.add(permissions[i]);
                        }
                    }
                }
            }
            
            if (needPermissionList.size() == 0) {
//                hasRequestPermissions = true;
                
                // initJNI();
                onAllPermissionsGranted();
                
                this.finish();
            } else {
                Log.d("FaceLocalPermission", "@@@@@ requestNecessaryPermissions continuous");
                String[] requestPermissions = new String[needPermissionList.size()];
                needPermissionList.toArray(requestPermissions);
                requestNecessaryPermissions(this, requestPermissions);
            }
        } else {
            // do nothing
        }
    }
    
    
    
//    private static void initJNI() {
//        FaceLocalServiceImpl.initJNI();
//    }
    
    private void onAllPermissionsGranted() {
        Log.d("FaceLocalPermission", ".................. onAllPermissionsGranted");
        setResult(1);
    }
    
    
    
    
    
//    private boolean hasRequestPermissions = false;
    
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        
//        Log.d("FaceLocal", "@@@@@@@ hasRequestPermissions = " + hasRequestPermissions);
//        if (!hasRequestPermissions) {
//            FaceLocalService.getInstance().mInitCallback.onInitResult(false);
//
//        }
    
    
    
    
    
    
    
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
