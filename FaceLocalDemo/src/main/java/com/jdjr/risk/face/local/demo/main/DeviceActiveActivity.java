package com.jdjr.risk.face.local.demo.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jdjr.risk.face.local.active.DeviceActiveManager;
import com.jdjr.risk.face.local.demo.camera.CameraIdHelper;
import com.jdjr.risk.face.local.demo.camera.CameraSettings;
import com.jdjr.risk.face.local.demo.R;
import com.jdjr.risk.face.local.demo.permission.FacePermissionHelper;
import com.jdjr.risk.face.local.service.FaceLocalService;
import com.yzq.testzxing.zxing.android.CaptureActivity;

/**
 * Created by michael on 19-3-15.
 */

public class DeviceActiveActivity extends Activity {
    private static final int PERMISSIONS_REQUEST_CODE = 1;
    
    public static final String USER_INFO = "QjFDOTM2MkFDMkU1MzNGQzI2MkVBOTdDOTZDRjJGMjhCOUY2MEZCNzNCOTMwRTZBQjBEOTU5QjBFNkRCNzg2NXxNSUlCSURBTkJna3Foa2lHOXcwQkFRRUZBQU9DQVEwQU1JSUJDQUtDQVFFQTQ0ZElEQnVLclQ4U1JpaDMvT3hLVjhQdE1LUTBsNlU3YnpBTGNYeDBPQldSVWJLWS9IVkVOVUxFUVhBTGs4Wm5sdFFpWk9nSFdHSjRnRUVJME0xQ1N0Z2RRWFdLM0xjY3B1UHVlR00vTGsrdXVHb2JGanUyVlUwckZTeEh4cmxaZHpuSW9nZ1VNSjdmWTRlc2VKbXJZWWJoQmtib0J2b0RIbm5QWTFOZ3pqVVVSUkpITjUxdFRmNWRhNnZ5TTJFVU1iTUQ5bE8vSXdoOWxzb2oxelRGYk42V1ppZmdaK043aHprUHVuV2tuTmZwVmJCamdvRndZQk85aGhOMHZZakVOYzVnM1VoZU1QTFVFL21xbE9OYlNRSmxTdE8zZ2ozUy9oTUs1V1ByaTRQNkxYQkZPSkdZZnJwYzJ3ckwvd0hjNkc2cjFHdTh3enpuc3JSeFdBTTZGd0lCQXc9PXxNSUlCTXpDQjdBWUhLb1pJemowQ0FUQ0I0QUlCQVRBc0JnY3Foa2pPUFFFQkFpRUEvLy8vL3YvLy8vLy8vLy8vLy8vLy8vLy8vLzhBQUFBQS8vLy8vLy8vLy84d1JBUWcvLy8vL3YvLy8vLy8vLy8vLy8vLy8vLy8vLzhBQUFBQS8vLy8vLy8vLy93RUlDanArcDZkbjE0MFRWcWVTODlsQ2Fmemw0bjFGYXVQa3QyOHZVRk5sQTZUQkVFRU1zU3VMQjhaZ1JsZm1RUkdham5KbEkvakM3L3laZ3ZoY1ZwRmlUTk1kTWU4TnphaTlQWjNuRm05enVOcmFTRlQwS21IZk1ZcVIwQUMzekxsSVRud29BSWhBUC8vLy83Ly8vLy8vLy8vLy8vLy8vOXlBOTlySWNZRksxTzc5QWs1MVVFakFnRUJBMElBQlBmT0FVOEp1UHVyOW1VWCtkSnVHWTJJRkpqNE1hTGpoTXNoUXUwYlI2Sld0TXJoSlpOcXhWYXRPZDN4ZGhBNGd1YStReEZxejM0VFhwdC9jbWluT1E0PQ==";
    
    /*getActiveFile resultCode
    e321f910558e41f29660c37b918c1d34
    a528fe540388401086bf597fad0103ab
    29c1c378e05c43058d27c4392b1926b6
    6b180a363dff464092c88d7e6580c56b
    7263215404e64561ad882a300ec1a539
    */
    
    /*
    // ACTIVE_ID
    HT	f862ce35ddc14a1ca36debc53a38276b	2019-03-18 19:11:25	0
    HT	f9ea6f92f0294323b35359009d86302e	2019-03-18 19:11:25	0
    HT	000fc6bb9b434557ab02321b42ec3a5f	2019-03-18 19:11:25	0
    HT	2a0fd6e7292e4b6db838e0951dc58ff0	2019-03-18 19:11:25	0
    HT	275ec0aabd154ac7975290ba05b44410	2019-03-18 19:11:25	0
    HT	c41f843b496b423aa0fdd7d2c5416f39	2019-03-18 19:11:25	0
    
    // TODO qinlin device
    HT	3a8572630c9c43a5a278a3f623c7bdad	2019-03-18 19:11:25	0
    HT	a98e828b17434c8ebbce01666f1dbb3a	2019-03-18 19:11:25	0
    HT	2289d306d527414fb3bd8abcd6f04437	2019-03-18 19:11:25	0
    HT	9b315af1a4d94a3ba0accfd1640bc7f7	2019-03-18 19:11:25	0
    */
    
    
    
//    private static final String ACTIVE_ID = "f862ce35ddc14a1ca36debc53a38276b";
    
    private static final String DECODED_CONTENT_KEY = "codedContent";
//    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    
    private static final int REQUEST_CODE_SCAN = 0x0000;
    
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
//        setContentView(R.layout.activity_active);
    
        
        // TODO must invoke this before use FaceLocalSDK protected
        // new com.load.sdk.LoadJarSo();
        
        // FaceLocalService.getInstance().requestPermissions(this, PERMISSIONS_REQUEST_CODE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            FacePermissionHelper.requestPermissions(this, PERMISSIONS_REQUEST_CODE);
        } else {
            isDeviceActive();
        }
        
        // startQRCaptureActivity();
    }
    
    private void startQRCaptureActivity() {
    
        final Intent intent = new Intent(DeviceActiveActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    
    }
    

    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        Log.d("FaceLocalPermission", ".............. requestCode = " + requestCode);
        Log.d("FaceLocalPermission", ".............. resultCode = " + resultCode);
        
        
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            handlePermissionsRequestResult(resultCode);
        } else if (requestCode == REQUEST_CODE_SCAN) {
            handleQRCodeResult(resultCode, data);
        } else {
        
        }
        
    }
    
    
    private void handleQRCodeResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (data != null) {
                
                final String QRCode = data.getStringExtra(DECODED_CONTENT_KEY);
                // final Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);
                
                Log.d("FaceLocalActive", "=============== BActiveActivity handleQRCodeResult QRCode = " + QRCode);
                
                mActiveIdInput.setText(QRCode);
                
                activeDeviceImpl(QRCode);
                
            }
        }
        
        
    }
    
    
    
    
    private void handlePermissionsRequestResult(int resultCode) {
        if (resultCode == 1) {
            // hasAllPermissions = true;
            Toast.makeText(this, "已经获得所有必需权限..............", Toast.LENGTH_SHORT).show();
            // launchFaceLocalService();
            
            // showAuthButtons();
            isDeviceActive();
            
            
        } else {
            Toast.makeText(this, "获得所有必需权限失败!!!!!!!!!!!!!!", Toast.LENGTH_SHORT).show();
            
        }
    }

    
    
    
    
//    private void showAuthButtons() {
//        final Button generateButton = findViewById(R.id.generate_auth_button);
//        generateButton.setVisibility(View.VISIBLE);
//
//        final Button checkButton = findViewById(R.id.check_auth_button);
//        checkButton.setVisibility(View.VISIBLE);
//
//        final Button startButton = findViewById(R.id.start_service_button);
//        startButton.setVisibility(View.VISIBLE);
//
//    }
    
    
    
    
    private void isDeviceActive() {
        
        FaceLocalService.getInstance().registerUserOnce(this.getApplicationContext(), USER_INFO);
        final boolean isDeviceActive = FaceLocalService.getInstance().isDeviceActive(this.getApplicationContext(), USER_INFO);
        Log.d("FaceLocalActive", ".............. AuthActivity isDeviceActive = " + isDeviceActive);
        
        if (isDeviceActive) {
            showActiveResult(isDeviceActive);
            startFaceService();
        } else {
            // FaceLocalService.getInstance().activeDevice(this.getApplicationContext(), USER_INFO, );
            
            // show auth UI
            
            showActiveUI();
            
            
    
//            FaceLocalService.getInstance().setmApplicationContext(this.getApplication());
//            FaceLocalSettings.setCameraType(CameraDeviceType.SYSTEM_RGBNIR_JDY_K2002A_A4);
//            final boolean initCameraTypeResult = CameraIdHelper.initCameraRGBNIRId();
//
//            Log.d("FaceLocalActive", "=============== isDeviceActive initCameraTypeResult = " + initCameraTypeResult);
//
//            if (initCameraTypeResult) {
//                new Handler(Looper.myLooper()).postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        startQRCaptureActivity();
//                    }
//                }, 1000);
//            }
            
            
        }
        
        
        
        
        
        
    
    
    
    
    }
    
    private EditText mActiveIdInput;
    
    
    private void showActiveUI() {
        
        setContentView(R.layout.activity_active);
        
        
        mActiveIdInput = findViewById(R.id.active_id_input);
        
        
        
        


    
    

    }
    
    
    
    
    public void getQRCode(View view) {
//        startQRCaptureActivity();

        // TODO
        CameraSettings.setCameraType(CameraSettings.TYPE_JDY_K2002A_A4);
        
        if (CameraSettings.getCameraType() == CameraSettings.TYPE_JDY_K2002A_A4) {
            CameraIdHelper.initCameraRGBNIRIdAsync(this.getApplicationContext(), new CameraIdHelper.CameraIdCallback() {
                @Override
                public void onCameraIdResult() {
                    startQRCaptureActivity();
                }
            });
        } else {
            startQRCaptureActivity();
            
        }
        
        
        
    }
    
    
    
    
//    public void generateAuthFile(View view) {
//
//
//
//
//    }
//
//
//    public void checkAuthFile(View view) {
//
//    }
    
    
    private void showNoActiveCodeToast() {
        Toast.makeText(this, "请输入激活码!!!", Toast.LENGTH_SHORT).show();
    }
    
    public void activeDevice(View view) {
        final String activeId = mActiveIdInput.getText().toString();
        
        if (activeId == null) {
            showNoActiveCodeToast();
            return;
        }
        
        final String activeCodeTrim = activeId.trim();
        if (activeCodeTrim == null || activeCodeTrim.length() == 0) {
            showNoActiveCodeToast();
            return;
        }
        
    
        activeDeviceImpl(activeCodeTrim);
    
    
    }
    
    private void activeDeviceImpl(String activeId) {
        if (activeId == null || activeId.length() == 0) {return;}
        Log.d("FaceLocalActive", ".......... AuthActivity activeDevice activeId = " + activeId);
        Log.d("FaceLocalActive", ".......... AuthActivity activeDevice USER_INFO = " + USER_INFO);
    
        DeviceActiveManager.getInstance().activeDevice(this.getApplicationContext(), USER_INFO, activeId, new DeviceActiveManager.ActiveCallback() {
            @Override
            public void onActiveResult(boolean enable) {
                Log.d("FaceLocalActive", ".............. AuthActivity onActiveResult enable = " + enable);
                
                
                showActiveResult(enable);
                
                if (enable) {
                    startFaceService();
                
                }
        
            }
        });
    }
    
    
    private void showActiveResult(final boolean enable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                
                if (enable) {
                    Toast.makeText(DeviceActiveActivity.this, ".....设备已经激活成功.....", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DeviceActiveActivity.this, "!!!!!设备激活失败失败!!!!!", Toast.LENGTH_SHORT).show();
                }
            
            }
        });
    
    }
    
    
    
    public void startFaceService() {
    
        // final Intent intent = new Intent(this, CDemoActivity.class);
        final Intent intent = new Intent(this, FaceSettingsActivity.class);
        startActivity(intent);
    
    
    }
    
    
    public void startFaceService(View view) {
        startFaceService();
    }
    
    
    public void inputActiveCode(View view) {
        final TextView activeCodeView = (TextView) view;
        final String activeCode = (String) activeCodeView.getText();
        mActiveIdInput.setText(activeCode);
        
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
