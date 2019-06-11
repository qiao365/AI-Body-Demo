package com.jdjr.risk.face.local.demo.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jdjr.risk.face.local.demo.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by michael on 19-4-2.
 */

public class ReadMeActivity extends Activity {
    private Button mLaunchButton;
    // private long mTimestamp = System.currentTimeMillis();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_first);
        
        mLaunchButton = findViewById(R.id.launch_guide_button);
        
        // mTimestamp = System.currentTimeMillis();
        
        initContentTimer();
    }
    
    private volatile int mContentSeconds = 9;
    
    private void initContentTimer() {
        final Timer timer = new Timer();
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                
                
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mContentSeconds <= 0) {
                            final String remainText = "我全都看懂啦" + "(" + 0 + ")";
                            mLaunchButton.setText(remainText);
                            timer.cancel();
                            return;
                        } else {
                            final String remainText = "我全都看懂啦" + "(" + mContentSeconds-- + ")";
                            mLaunchButton.setText(remainText);
                        }
                    }
                });
            }
        };
        
        timer.schedule(task, 1000, 1000);
    
    }
    
    private int mClickNumber;
    
    public void launchGuideActivity(View view) {
        mClickNumber++;
        if (mClickNumber >= 3) {
            startActivity(new Intent(this, CameraRotationActivity.class));
            return;
        }
        
    
        if (mContentSeconds <= 0) {
            startActivity(new Intent(this, CameraRotationActivity.class));
        } else {
            Toast.makeText(this, "请耐心阅读完...", Toast.LENGTH_SHORT).show();
            return;
        }
    
    
    
    
    
    
    
    }
    
    
    
    
    
    public void startPerformanceActivity(View view) {
        startActivity(new Intent(this, AlgorithmPerformanceActivity.class));
    }
    
}
