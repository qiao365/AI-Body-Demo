package com.jdjr.risk.face.local.demo.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jdjr.risk.face.local.demo.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by michael on 18-11-27.
 */

public class RegisterIdActivity extends Activity {
    private EditText mIdView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_id);
    
        mIdView = findViewById(R.id.id_edit_view);
    }
    
    
    public void registerUser(View view) {
    
        final EditText idView = findViewById(R.id.id_edit_view);
        final String id = idView.getText().toString();
        final String userId = id.trim();
        if (userId == null || userId.length() == 0) {
            Toast.makeText(this, "用户ID不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
    
    
        launchRegisterSingleActivity(userId);
        
        
        
    }
    
//    public static final String EXTRA_NAME_LAYOUT       = "layout";
//    public static final String EXTRA_NAME_ORIGINW      = "originW";
//    public static final String EXTRA_NAME_IS_REGISTER  = "isRegister";
    
    
    
    private void launchRegisterSingleActivity(String userId) {
        /*
        final Intent intent = new Intent(this, RegisterSingleActivity.class);
        intent.putExtra("faceId", idTrim);
        startActivity(intent);
        */
        
    
    
        // final Intent intent = new Intent(this, FaceRegisterSingleActivity.class);
        final Intent intent = new Intent(this, RegisterCaptureActivity.class);
//        intent.putExtra("layout",  R.layout.activity_register_single_face_local);   // 设置layout
//        intent.putExtra("originW", 0.75f);                        // 设置宽高比例
//        intent.putExtra("isRegister", true);                     // 是否是注册
        
        intent.putExtra("userId", userId);
        
        startActivity(intent);
        
        
        
    }
    
    
    public void clearId(View view) {
        final EditText idView = findViewById(R.id.id_edit_view);
        idView.setText("");
    
    }
    
    
    public void inputIdTimestamp(View view) {
        
        final String id = getTimestampId();
    
    
        mIdView.setText(id);
    
    }
    
    
    
    
    
    
    

    
    public void inputIdzhaohongtao1(View view) {
        mIdView.setText("zhaohongtao1");
    }
    
    
    public void inputId(View view) {
        final TextView idView = (TextView) view;
        final String id = (String) idView.getText();
        mIdView.setText(id);
    }
    
    
    
    public void inputIdliuyuguang1(View view) {
        mIdView.setText("liuyuguang1");
    }
    
    public void inputIdfuhaijing(View view) {
        mIdView.setText("fuhaijing");
    }
    
    public void inputIdwangxiaoguang1(View view) {
        mIdView.setText("wangxiaoguang1");
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private static String getTimestampId() {
        
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        final String timeLabel = format.format(new Date(System.currentTimeMillis()));
        final String id = "ID-" + timeLabel;
        return id;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
