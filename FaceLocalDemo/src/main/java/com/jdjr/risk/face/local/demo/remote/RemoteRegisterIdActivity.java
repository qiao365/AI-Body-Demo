package com.jdjr.risk.face.local.demo.remote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jdjr.risk.face.local.demo.R;


/**
 * Created by michael on 19-4-8.
 */

public class RemoteRegisterIdActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_remote_register_id);
        
        
        
    }
    
    
    
    public void inputId(View view) {
        final TextView textView = (TextView) view;
        final String id = (String) textView.getText();
        
        final TextView idView = findViewById(R.id.remote_id_view);
        idView.setText(id);
    }
    
    
    
    
    public void clearId(View view) {
        final TextView idView = findViewById(R.id.remote_id_view);
        idView.setText("");
    
    }
    
    
    
    public void registerRemote(View view) {
        final TextView idView = findViewById(R.id.remote_id_view);
        final String userId = idView.getText().toString();
        final String userName = "用户名" + System.currentTimeMillis();
        
        final Intent intent = new Intent(this, RemoteRegisterCaptureActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("userName", userName);
        
        startActivity(intent);
        
    }
    
    
    
    
}
