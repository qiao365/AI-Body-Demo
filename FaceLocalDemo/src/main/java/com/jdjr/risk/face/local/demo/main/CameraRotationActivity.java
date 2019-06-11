package com.jdjr.risk.face.local.demo.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jdjr.risk.face.local.demo.R;

/**
 * Created by michael on 19-3-14.
 */

public class CameraRotationActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
    }
    
    public void launchDemo(View view) {
    
        final Intent intent = new Intent(this, DeviceActiveActivity.class);
        // final Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        
    }
}
