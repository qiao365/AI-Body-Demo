package com.small.cjq.mainapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.camareui.constant.Constant;
import com.tuzhenlei.crashhandler.CrashHandler;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends Activity {

    private Button enterBtn;
    private static final String[] requestPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
    private List<String> mPermissionList = new ArrayList<>();
    private final int mRequestCode = 100;
    private AlertDialog mPermissionDialog;
    private SharedPreferences mSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashHandler.getInstance().init(getApplication(), true);
        mSp = getSharedPreferences(Constant.PREFERENCE_NAME, MODE_PRIVATE);
        boolean isFirstComeIn = mSp.getBoolean(Constant.PREFERENCE_KEY_IS_FIRST_COME_IN, true);
        if(!isFirstComeIn) {
            enterMainpage();
        } else {
            setContentView(com.camareui.base.R.layout.activity_face_splash);
            initView();
            initEvent();
            initPermission();
        }
    }

    private void initPermission() {
        mPermissionList.clear();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : requestPermissions) {
                if(ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permission);
                }
            }
            if(mPermissionList.size() > 0) {
                ActivityCompat.requestPermissions(this, requestPermissions, mRequestCode);
            }else {
                // 进入首页
                enterMainpage();
            }
        }else {
            // 进入首页
            enterMainpage();
        }
    }

    private void enterMainpage() {
        mSp.edit().putBoolean(Constant.PREFERENCE_KEY_IS_FIRST_COME_IN, false).apply();
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermissionDismiss = false;
        if(mRequestCode == requestCode) {
            for (int grantResult : grantResults) {
                if(grantResult == PackageManager.PERMISSION_DENIED) {
                    hasPermissionDismiss = true;
                    break;
                }
            }
            if(hasPermissionDismiss) {
                showPermissionDialog();
            }else {
                // 进入首页
                enterMainpage();
            }
        }
    }

    private void showPermissionDialog() {
        if(mPermissionDialog == null) {
            mPermissionDialog = new AlertDialog.Builder(this)
                    .setMessage("已禁止权限")
                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cancelPermissionDialog();
                            Uri packageUri = Uri.parse("package:" + Constant.PACKAGE_NAME);
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageUri);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cancelPermissionDialog();
                            Toast.makeText(getApplication(), "您必须开启权限，才能使用服务", Toast.LENGTH_LONG).show();
                            // 延迟结束程序
                            delayCloseAiad();
                        }
                    })
                    .create();
        }
        mPermissionDialog.show();
    }

    private void delayCloseAiad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1000);
    }

    private void cancelPermissionDialog() {
        mPermissionDialog.cancel();
    }

    private void initEvent() {
        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPermission();
            }
        });
    }

    private void initView() {
        enterBtn = findViewById(com.camareui.base.R.id.enterBtn);
    }

}
