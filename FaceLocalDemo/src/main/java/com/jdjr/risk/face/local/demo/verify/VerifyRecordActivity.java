package com.jdjr.risk.face.local.demo.verify;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.jdjr.risk.face.local.demo.R;
import com.jdjr.risk.face.local.verify.VerifyRecordManager;
import com.jdjr.risk.face.local.verify.VerifyRecord;

import java.util.List;



/**
 * Created by michael on 18-11-19.
 */

public class VerifyRecordActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_record);
    
    
        showVerifyList();
    
    
    }
    

    /*
    public void showAllRecord(View view) {
        showAllRecord();
    }
    */
    
    
    private void showVerifyList() {
    
        final Context applicationContext = this.getApplicationContext();
    
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<VerifyRecord> verifyList = VerifyRecordManager.getInstance().getAllRecord(applicationContext);
                if (verifyList == null || verifyList.size() == 0) {
                    Log.d("FaceLocal", "@@@@@@ 没有打卡记录");
                    
//                    final String result = "没有打卡记录";
                    showEmptyResult();
                    
                    
                    return;
                }
                
                Log.d("FaceNative", "@@@@@@ allRecord = " + verifyList);
            
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        displayVerifyList(verifyList);
                    
                    }
                });
            
            
            
            }
        }).start();
    
    }
    
    private void showEmptyResult() {
        /*
        final TextView emptyView = new TextView(this);
        emptyView.setBackgroundColor(0xff00ff);
        emptyView.setTextColor(0xff0f0f);
        emptyView.setText(result);
        emptyView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 500));
        
    
        final LinearLayout root = findViewById(R.id.all_record_root);
        root.addView(emptyView, 0);
        */
        
        
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                
                final View view = findViewById(R.id.empty_record_view);
                view.setVisibility(View.VISIBLE);
        
            }
        });
        
        
        
    
    }
    
    
    private void displayVerifyList(List<VerifyRecord> records) {
        Log.d("FaceNative", "@@@@@ displayAllRecord");
        final RecyclerView allRecordView = findViewById(R.id.all_record_view);
        allRecordView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        // recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        allRecordView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        
        final VerifyRecordAdapter adapter = new VerifyRecordAdapter(records);
        allRecordView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
        
    }
    
    public void doSomething(View view) {
        final RecyclerView allRecordView = findViewById(R.id.all_record_view);
        allRecordView.invalidate();
    
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        
        
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final RecyclerView allRecordView = findViewById(R.id.all_record_view);
                allRecordView.invalidate();
            }
        }, 700);
        
    }
    
    
    /*
    public void showLastRecord(View view) {
        showLastRecord();
    }
    
    private void showLastRecord() {
        final Context applicationContext = this.getApplicationContext();
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                final VerifyRecord record = RecordManager.getInstance().getLastRecord(applicationContext);
                Log.d("FaceNative", "@@@@@@ record = " + record);
                
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        displayLastRecord(record);
                    }
                });
            }
        }).start();
    }
    
    
    private void displayLastRecord(VerifyRecord record) {
        final TextView lastRecordView = findViewById(R.id.last_record_view);
        lastRecordView.setText("" + record);
        
        final ImageView faceView = findViewById(R.id.record_face_view);
        final String facePath = record.getFacePath();
        final Bitmap face = BitmapFactory.decodeFile(facePath);
        Log.d("FaceNative", "@@@@@@@@ displayLastRecord record face  = " + face);
        
        faceView.setImageBitmap(face);
        
    }
    */
    
    
    
    
}
