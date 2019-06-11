package com.jdjr.risk.face.local.demo.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jdjr.risk.face.local.demo.business.QinlinVerifyActivity;
import com.jdjr.risk.face.local.demo.camera.CameraIdHelper;
import com.jdjr.risk.face.local.demo.camera.CameraSettings;
import com.jdjr.risk.face.local.demo.camera.UsbCameraManager;
import com.jdjr.risk.face.local.demo.R;
import com.jdjr.risk.face.local.demo.capture.CaptureFaceActivity;
import com.jdjr.risk.face.local.demo.compare.CompareFolderActivity;
import com.jdjr.risk.face.local.demo.extract.ExtractPropertyActivity;
import com.jdjr.risk.face.local.demo.register.RegisterFolderActivity;
import com.jdjr.risk.face.local.demo.register.RegisterIdActivity;
import com.jdjr.risk.face.local.demo.register.RegisterRecordActivity;
import com.jdjr.risk.face.local.demo.remote.RemoteRegisterIdActivity;
import com.jdjr.risk.face.local.demo.verify.VerifyRGBNIRActivity;
import com.jdjr.risk.face.local.demo.verify.VerifyRecordActivity;
import com.jdjr.risk.face.local.service.FaceLocalService;
import com.jdjr.risk.face.local.settings.FaceSettings;
import com.jdjr.risk.face.local.user.LocalUserManager;

import java.io.File;

public class FeatureListActivity extends Activity {
    private ProgressDialog mProgressDialog;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("FaceLocalDemo", "...........UI thread id = " + Thread.currentThread().getId());
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        
        
        
        showProgressDialog();
    
    
        
        checkCameraType();
        
        initCameraId();
        
        
    
    
        
        
    }
    
    
    private void checkCameraType() {
        if (CameraSettings.getCameraType() == CameraSettings.TYPE_JDY_K2002A_A4) {
            final boolean checkResult = UsbCameraManager.checkJDY_K2002A_A4(this.getApplicationContext());
            Log.d("FaceLocalInit", "----------- checkResult = " + checkResult);
        }
        
    }
    
    
    
    private void initCameraId() {
        if (CameraSettings.getCameraType() == CameraSettings.TYPE_JDY_K2002A_A4) {
            CameraIdHelper.initCameraRGBNIRIdAsync(this.getApplicationContext(), new CameraIdHelper.CameraIdCallback() {
                @Override
                public void onCameraIdResult() {
                    startFaceService();
                }
            });
        } else {
            startFaceService();
        }
    }

//    private void printResolution() {
//        final DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
//        final int widthPixels = metrics.widthPixels;
//        final int heightPixels = metrics.heightPixels;
//        Log.d("FaceLocalResolution", ".......... widthPixels = " + widthPixels);
//        Log.d("FaceLocalResolution", ".......... heightPixels = " + heightPixels);
//    }
    
    private void showProgressDialog() {
    
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setTitle("正在启动人脸本地服务...........");
        mProgressDialog.show();
     
    }
    
    
    
    
    
    
    
    
    
    public void startFaceService() {

        
        final String userInfo = DeviceActiveActivity.USER_INFO;
        
        FaceLocalService.getInstance().initSDK(this.getApplicationContext(), userInfo, new FaceLocalService.InitCallback() {
            @Override
            public void onInitResult(boolean result, int resultCode, String resultMessage) {
                Log.d("FaceLocalInit", "........... InitActivity initFaceLocal result = " + result);
                Log.d("FaceLocalInit", "........... InitActivity initFaceLocal resultCode = " + resultCode);
                Log.d("FaceLocalInit", "........... InitActivity initFaceLocal resultMessage = " + resultMessage);
                
                
                mProgressDialog.dismiss();
                
                
                if (result) {
                    Toast.makeText(FeatureListActivity.this, "启动人脸服务成功.........", Toast.LENGTH_SHORT).show();
                    
                    if (FaceSettings.isPullAuto()) {
                        FaceLocalService.getInstance().startPullService();
                    }
                    
                    
                } else {
                    Toast.makeText(FeatureListActivity.this, "启动人脸服务失败!!!!!!!!!!", Toast.LENGTH_SHORT).show();
                    
                }
                
                
            }
            
            
            
        });
        
        
        
    }
    
    
    
    
    
    
    
    public void registerSingle(View view) {
        final Intent intent = new Intent(this, RegisterIdActivity.class);
        startActivity(intent);
    }
    
    
    
    
    
    
    
    
    public void registerImageSingle(View view) {
        
        final String imagePath = "/sdcard/FaceLocalRegisterImages/zhaohongtao04191055.jpg";
        
        if (imagePath == null || imagePath.length() == 0 || !new File(imagePath).exists()) {
            Toast.makeText(this, "请在demo中HardCode图片路径...", Toast.LENGTH_SHORT).show();
            return;
        }
        
        
        Log.d("FaceLocalRegister", "register imagePath　＝　" + imagePath);
        
        final File file = new File(imagePath);
        if (!file.exists()) {
            Toast.makeText(this, "注册图片不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        
        
        final String addUserId = getUserId(file.getName());
        Log.d("FaceLocalRegister", "@@@@@@@@ add userId = " + addUserId);
        
        final LocalUserManager.UserManageCallback addCallback = new LocalUserManager.UserManageCallback() {
            @Override
            public void onUserResult(boolean result, String message) {
                Log.d("FaceLocalRegister", addUserId + " @@@@@@@@ face local demo register by image result = " + result);
                Log.d("FaceLocalRegister", addUserId + " @@@@@@@@ face local demo register by image message = " + message);
                
                final String detail = "imagePath = " + imagePath + "\n"
                        + "userId = " + addUserId + "\n"
                        + "result = " + result + "\n"
                        + "message = " + message + "\n"
                        ;
                showRegisterResult(detail);
                
                
            }
        };
        LocalUserManager.getInstance().registerByImage(this.getApplicationContext(), addUserId, imagePath, null, addCallback);




    
    }
    
    private static String getUserId(String path) {
        final String endLabel = ".jpg";
        
        final int endIndex = path.indexOf(endLabel);
        
        final String userId = path.substring(0, endIndex);
        Log.d("FaceLocalRegister", "............ getUserId userId = " + userId);
        return userId;
    }
    
    private void showRegisterResult(String detail) {
        Toast.makeText(this, detail, Toast.LENGTH_SHORT).show();;
        
    }
    
    
    
    public void registerImageMultiple(View view) {
        final Intent intent = new Intent(this, RegisterFolderActivity.class);
        startActivity(intent);
    }
    
    
    
    
    
    
    
    public void showRegisterList(View view) {
        final Intent intent = new Intent(this, RegisterRecordActivity.class);
        startActivity(intent);
    }
    
    
    
    
    
    public void compare2Images(View view) {
    
    
    
        startActivity(new Intent(this, CompareFolderActivity.class));
    
    
    }
    

    
    
    
    
    public void startVerifyActivity(View view) {
        final Intent intent = new Intent(this, VerifyRGBNIRActivity.class);
        this.startActivity(intent);
    }
    
    
    public void showVerifyList(View view) {
        final Intent intent = new Intent(this, VerifyRecordActivity.class);
        startActivity(intent);
    }
    
    
    
    
    
    
    
    public void registerRemote(View view) {
        startActivity(new Intent(this, RemoteRegisterIdActivity.class));
    }
    
    
    
    
    
    
    public void showCaptureFace(View view) {
        startActivity(new Intent(this, CaptureFaceActivity.class));
    }
    
    public void startRemoteActivity(View view) {
        startActivity(new Intent(this, RemotePullActivity.class));
        
    }
    
    public void startExtractActivity(View view) {
        final Intent intent = new Intent(this, ExtractPropertyActivity.class);
        this.startActivity(intent);
    
    
    
    }
    
    
    public void startVerifyQinlinActivity(View view) {
        final Intent intent = new Intent(this, QinlinVerifyActivity.class);
        this.startActivity(intent);
    }
}
