package com.small.cjq.mainapp;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.camareui.base.BaseUI;
import com.camareui.constant.Constant;

public class MainActivity extends Activity implements BaseUI.DataSettingAndListener {
    private BaseUI baseUI;
    private WebView webView;
    private ImageView img;
    private LinearLayout mainlayout;
    private TextView width, height, xiaolv, textZhen, time_textZhen;
    private Constant.AISDKTYPE sdkType = Constant.AISDKTYPE.JETDETECTBODY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initView();
        initBaseUI();
        getOritation();
    }

    private void initBaseUI() {
        baseUI = new BaseUI(this, R.id.container);
        baseUI.setDataSettingAndListener(this);
        baseUI.setTextViews(width, height, xiaolv, textZhen, time_textZhen, img);
    }

    private void getOritation() {
        Configuration mConfiguration = getResources().getConfiguration();
        if(mConfiguration.orientation == mConfiguration.ORIENTATION_PORTRAIT){
            Toast.makeText(this,"竖屏",Toast.LENGTH_SHORT).show();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        if(mConfiguration.orientation == mConfiguration.ORIENTATION_LANDSCAPE){
            Toast.makeText(this,"横屏",Toast.LENGTH_SHORT).show();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    /**
     * 初始化view
     * webview、mainlayout、fragment（R.id.container）必须有
     */
    @SuppressLint("JavascriptInterface")
    private void initView() {
        webView = findViewById(R.id.webview);//cocos、h5
        mainlayout = findViewById(R.id.mainlayout);//unity
        width = findViewById(R.id.width);
        height = findViewById(R.id.height);
        xiaolv = findViewById(R.id.xiaolv);
        textZhen = findViewById(R.id.textZhen);
        time_textZhen = findViewById(R.id.time_textZhen);
        img = findViewById(R.id.img);
        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (baseUI != null) baseUI.savePicture();
            }
        });
    }


    /**
     * ai算法类型
     *
     * @return
     */
    @Override
    public Constant.AISDKTYPE getSdkAiType() {
        return sdkType;
    }

    /**
     * 是否显示预览
     *
     * @return
     */
    @Override
    public boolean getPreViewStatus() {
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
        super.onBackPressed();
    }
}