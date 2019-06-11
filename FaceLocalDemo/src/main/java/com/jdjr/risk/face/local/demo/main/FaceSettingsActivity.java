package com.jdjr.risk.face.local.demo.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.params.Face;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Switch;

import com.jdjr.risk.face.local.demo.R;
import com.jdjr.risk.face.local.demo.camera.CameraSettings;
import com.jdjr.risk.face.local.settings.FaceSettings;

/**
 * Created by michael on 19-5-5.
 */

public class FaceSettingsActivity extends Activity {
//    private static final boolean MULTIPLE_FACE = true;
//    private static final boolean CAPTURE_FACE = true;
    
    
//    private static final int FACE_TYPE = FaceSettings.TYPE_VERIFY_FACE;
//    private static final int CHECK_TYPE = FaceSettings.CHECK_NIR;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_settings);
        
        configFaceService();
        // configFaceServiceYoupingyinxiang();
        
        initView();
        
    }
    
    
    
    public void startFaceService(View view) {
        startActivity(new Intent(this, FeatureListActivity.class));
        
    }
    
    
    
    
    public void configFaceService() {
    
        CameraSettings.setCameraType(CameraSettings.TYPE_JDY_K2002A_A4);
    

        
        
            
        // 输入相机输出图像旋转角度
        CameraSettings.setCameraImageRotation(CameraSettings.ROTATION_0);
        
        // 输入相机显示预览旋转角度
        CameraSettings.setCameraDisplayRotation(CameraSettings.ROTATION_0);
        
        
        CameraSettings.MIRROR_RGB = true;
        CameraSettings.MIRROR_NIR = true;
        
        
    
    
    
    
    
        FaceSettings.setMultipleFace(false);
        FaceSettings.setMinFaceArea(30000);
    
        FaceSettings.setCaptureFace(false);
        final String captureDir = this.getApplicationContext().getDir("CaptureImageDir", Context.MODE_PRIVATE).getAbsolutePath();
        FaceSettings.setCaptureDir(captureDir);
    
        // FaceSettings.setFaceType(FaceSettings.TYPE_VERIFY_FACE);
        
        
        
        
        FaceSettings.setCheckFaceArea(true);
        
        FaceSettings.setVerifyFaceArea(30000);
        
        
        FaceSettings.setVerifyFace(true);
        
        FaceSettings.setExtractProperty(false);
        FaceSettings.setOnlyEmotion(false);
        
        
        
        FaceSettings.setCheckType(FaceSettings.CHECK_NIR);
    
        FaceSettings.setPersistentVerifyFrame(true);
    
    
        FaceSettings.setPullAuto(false);
        
    }
    
    
    public void configFaceServiceYoupingyinxiang() {
        
        CameraSettings.setCameraType(CameraSettings.TYPE_SYSTEM);
        
        
        
        
        
        // 输入相机输出图像旋转角度
        CameraSettings.setCameraImageRotation(CameraSettings.ROTATION_0);
        
        // 输入相机显示预览旋转角度
        CameraSettings.setCameraDisplayRotation(CameraSettings.ROTATION_0);
        
        
        CameraSettings.MIRROR_RGB = true;
        CameraSettings.MIRROR_NIR = true;
        
        
        CameraSettings.IMAGE_MIRROR_RGB = false;
        
        
        
        
        
        
        FaceSettings.setMultipleFace(true);
        FaceSettings.setMinFaceArea(30000);
        
        FaceSettings.setCaptureFace(false);
//        final String captureDir = this.getApplicationContext().getDir("CaptureImageDir", Context.MODE_PRIVATE).getAbsolutePath();
//        FaceSettings.setCaptureDir(captureDir);
        
        // FaceSettings.setFaceType(FaceSettings.TYPE_VERIFY_FACE);
        FaceSettings.setVerifyFace(false);
        FaceSettings.setExtractProperty(true);
        FaceSettings.setOnlyEmotion(true);
        
        
        
//        FaceSettings.setCheckType(FaceSettings.CHECK_NIR);
        
//        FaceSettings.setPersistentVerifyFrame(true);
        
        
        FaceSettings.setPullAuto(false);
        
    }
    
    
    
    
    private void initView() {
        final int cameraType = CameraSettings.getCameraType();
        Log.d("FaceLocalSettings", "============= cameraType = " + cameraType);
        
        if (cameraType == CameraSettings.TYPE_SYSTEM) {
            final RadioButton systemButton = findViewById(R.id.camera_system_radio);
            systemButton.setChecked(false);
        } else if (cameraType == CameraSettings.TYPE_JDY_K2002A_A4) {
            final RadioButton jdyButton = findViewById(R.id.camera_jdy_k2002a_a4_radio);
            jdyButton.setChecked(true);
        } else {
            // do nothing
        }
        
        
        final Switch mirrorRGBSwitch = findViewById(R.id.rgb_mirror_switch);
        mirrorRGBSwitch.setChecked(CameraSettings.MIRROR_RGB);
        
        final Switch mirrorNIRSwitch = findViewById(R.id.nir_mirror_switch);
        mirrorNIRSwitch.setChecked(CameraSettings.MIRROR_NIR);
        
        
        
        
        
        
        
        
        final Switch multipleFaceSwitch = findViewById(R.id.multiple_face_switch);
        multipleFaceSwitch.setChecked(FaceSettings.isMultipleFace());
        
        final Switch captureFaceSwitch = findViewById(R.id.capture_face_switch);
        captureFaceSwitch.setChecked(FaceSettings.isCaptureFace());
        
        
        
        
        final Switch checkFaceSwitch = findViewById(R.id.check_area_switch);
        checkFaceSwitch.setChecked(FaceSettings.isCheckFaceArea());
        
        
        final Switch verifyFaceSwitch = findViewById(R.id.verify_face_switch);
        verifyFaceSwitch.setChecked(FaceSettings.isVerifyFace());
        
        final Switch extractPropertySwitch = findViewById(R.id.extract_property_switch);
        extractPropertySwitch.setChecked(FaceSettings.isExtractProperty());
        
        final Switch onlyEmotionSwitch = findViewById(R.id.only_emotion_switch);
        onlyEmotionSwitch.setChecked(FaceSettings.isOnlyEmotion());
        
        
    
    
        final int checkType = FaceSettings.getCheckType();
        
        if (checkType == FaceSettings.CHECK_NONE) {
            final RadioButton checkNoneButton = findViewById(R.id.check_none_radio);
            checkNoneButton.setChecked(true);
        } else if (checkType == FaceSettings.CHECK_RGB) {
            final RadioButton checkRGBButton = findViewById(R.id.check_rgb_radio);
            checkRGBButton.setChecked(true);
        } else if (checkType == FaceSettings.CHECK_NIR) {
            final RadioButton checkNIRButton = findViewById(R.id.check_nir_radio);
            checkNIRButton.setChecked(true);
        }else {
            // do nothing
        }
        
        final Switch pullAutoSwitch =findViewById(R.id.pull_auto_switch);
        pullAutoSwitch.setChecked(FaceSettings.isPullAuto());
        
    }
    
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void captureFace(View view) {
        Log.d("FaceLocalSettings", "=========== SettingsActivity captureFace");
        
        final Switch aSwitch = (Switch) view;
        if (aSwitch.isChecked()) {
            FaceSettings.setCaptureFace(true);
        } else {
            FaceSettings.setCaptureFace(false);
        }
    
    
    }
    
    
    public void enableMultiple(View view) {
        Log.d("FaceLocalSettings", "=========== SettingsActivity enableMultiple");
        final Switch aSwitch = (Switch) view;
        if (aSwitch.isChecked()) {
            FaceSettings.setMultipleFace(true);
        } else {
            FaceSettings.setMultipleFace(false);
        }
    }
    
    
    
    


    
    
//    public void noneFace(View view) {
//        FaceSettings.setFaceType(FaceSettings.TYPE_NONE);
//    }
//
//    public void verifyFace(View view) {
//        FaceSettings.setFaceType(FaceSettings.TYPE_VERIFY_FACE);
//    }
//
//    public void extractProperty(View view) {
//        FaceSettings.setFaceType(FaceSettings.TYPE_EXTRACT_PROPERTY);
//    }
//
//    public void extractEmotion(View view) {
//        FaceSettings.setFaceType(FaceSettings.TYPE_EXTRACT_EMOTION);
//    }
    
    
    
    
    
    public void checkNone(View view) {
        FaceSettings.setCheckType(FaceSettings.CHECK_NONE);
    }
    
    public void checkRGB(View view) {
        FaceSettings.setCheckType(FaceSettings.CHECK_RGB);
    }
    
    public void checkNIR(View view) {
        FaceSettings.setCheckType(FaceSettings.CHECK_NIR);
        
    }
    
    
    public void setCameraSystem(View view) {
        CameraSettings.setCameraType(CameraSettings.TYPE_SYSTEM);
    }
    
    
    public void setCameraJDY(View view) {
        CameraSettings.setCameraType(CameraSettings.TYPE_JDY_K2002A_A4);
    }
    
    public void setMirrorRGB(View view) {
        final Switch aSwitch = (Switch) view;
        if (aSwitch.isChecked()) {
            CameraSettings.MIRROR_RGB = true;
        } else {
            CameraSettings.MIRROR_RGB = false;
        }
    }
    
    public void setMirrorNIR(View view) {
        final Switch aSwitch = (Switch) view;
        if (aSwitch.isChecked()) {
            CameraSettings.MIRROR_NIR = true;
        } else {
            CameraSettings.MIRROR_NIR = false;
        }
    }
    
    public void enableVerifyFace(View view) {
        final Switch aSwitch = (Switch) view;
        if (aSwitch.isChecked()) {
            FaceSettings.setVerifyFace(true);
        } else {
            FaceSettings.setVerifyFace(false);
        }
        
    }
    
    
    public void enableExtractProperty(View view) {
        final Switch aSwitch = (Switch) view;
        if (aSwitch.isChecked()) {
            FaceSettings.setExtractProperty(true);
        } else {
            FaceSettings.setExtractProperty(false);
        }
    }
    
    public void enableOnlyEmotion(View view) {
        final Switch aSwitch = (Switch) view;
        if (aSwitch.isChecked()) {
            FaceSettings.setOnlyEmotion(true);
        } else {
            FaceSettings.setOnlyEmotion(false);
        }
        
    }
    
    public void enablePullAuto(View view) {
        final Switch aSwitch = (Switch) view;
        if (aSwitch.isChecked()) {
            FaceSettings.setPullAuto(true);
        } else {
            FaceSettings.setPullAuto(false);
        }
        
    
    
    }
    
    
    public void configureEmotionSettings(View view) {
        configFaceServiceEmotion();
    
    }
    
    
    public void configFaceServiceEmotion() {
        
        CameraSettings.setCameraType(CameraSettings.TYPE_MAGIC);
        
        
        
        // 输入相机输出图像旋转角度
        CameraSettings.setCameraImageRotation(CameraSettings.ROTATION_0);
        
        // 输入相机显示预览旋转角度
        CameraSettings.setCameraDisplayRotation(CameraSettings.ROTATION_0);
        
        
        CameraSettings.MIRROR_RGB = false;
        CameraSettings.MIRROR_NIR = true;
        
        
        
        
        
        
        
        FaceSettings.setMultipleFace(false);
        FaceSettings.setMinFaceArea(30000);
        
        FaceSettings.setCaptureFace(false);
        final String captureDir = this.getApplicationContext().getDir("CaptureImageDir", Context.MODE_PRIVATE).getAbsolutePath();
        FaceSettings.setCaptureDir(captureDir);
        
        // FaceSettings.setFaceType(FaceSettings.TYPE_VERIFY_FACE);
        FaceSettings.setVerifyFace(false);
        
        FaceSettings.setExtractProperty(true);
        FaceSettings.setOnlyEmotion(true);
        
        
        
        FaceSettings.setCheckType(FaceSettings.CHECK_NIR);
        
        FaceSettings.setPersistentVerifyFrame(false);
        
        
        FaceSettings.setPullAuto(false);
        
    }
    
    
    public void checkFaceArea(View view) {
        FaceSettings.setCheckFaceArea(true);
    }
    
    
    
    
}
