package com.jdjr.risk.face.local.demo.register;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.widget.TextView;

import com.jdjr.risk.face.local.demo.R;
import com.jdjr.risk.face.local.demo.verify.FaceBoxUtil;
import com.jdjr.risk.face.local.demo.view.FaceBoxesView;
import com.jdjr.risk.face.local.detect.DetectFaceInfo;
import com.jdjr.risk.face.local.demo.camera.FaceCameraHelper;
import com.jdjr.risk.face.local.frame.FaceFrameManager;
import com.jdjr.risk.face.local.ndk.FaceDataInfo;
import com.jdjr.risk.face.local.property.FaceProperty;
import com.jdjr.risk.face.local.service.FaceLocalService;
import com.jdjr.risk.face.local.user.LocalUserManager;
import com.jdjr.risk.face.local.verify.VerifyResult;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by michael on 19-1-15.
 */

public class RegisterCaptureActivity extends Activity {
    
    private FaceBoxesView mBoxesView;
    
    
    private TextView mRegisterResult;
    
//    private TextView mResultView;
//    private TextView mDurationView;
    
    
    
    private String mUserId = null;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        setContentView(R.layout.activity_register_camera);
    
    
        mUserId = getIntent().getStringExtra("userId");
    
    
        mBoxesView = findViewById(R.id.face_boxes_view);
    
        mRegisterResult = findViewById(R.id.local_register_result);
        
//        mResultView = findViewById(R.id.search_result_view);
//        mDurationView = findViewById(R.id.search_duration_view);
    
    
    
        // RGB preview
        final TextureView RGBTextureView = findViewById(R.id.camera_preview_rgb);
        // NIR preview
        final TextureView NIRTextureView = findViewById(R.id.camera_preview_nir);
    
    
        // initialize all
//        final boolean isRegister = true;
    
        FaceFrameManager.setBasePropertyCallback(mDetectBoxCallback);
        
        // set search result callback
        FaceFrameManager.setVerifyResultCallback(mVerifyResultCallback);
    
    
        // set face property callback
        FaceFrameManager.setFacePropertyCallback(mFacePropertyCallback);
    
    
//        CameraFrameManager.setRegisterCallback(mRegisterCallback);
    
        FaceFrameManager.setFaceType(FaceFrameManager.TYPE_FACE_DETECT);
        FaceFrameManager.setFaceCallback(mFaceDetectCallback);
        
    
//        CameraFrameManager.setFaceType(FaceDetectManager.TYPE_USER_REGISTER);
        FaceFrameManager.startDetectFace();
        
        FaceCameraHelper.getInstance().initFaceCamera(RGBTextureView, NIRTextureView);
    
    }
    
    
    
    private FaceFrameManager.FaceDetectCallback mFaceDetectCallback = new FaceFrameManager.FaceDetectCallback() {
        @Override
        public void onFaceDetect(byte[] faceImageJPGBytes, FaceDataInfo faceDataInfo) {
            Log.d("FaceLocalRegisterRemote", "..............onFaceDetect");
            
            
            
            // persistent face image
            final String imagePath = persistentFaceImage(faceImageJPGBytes);
            Log.d("FaceLocalRegisterImage", "====================== RegisterCaptureActivity onFaceDetect imagePath = " + imagePath);
            if (imagePath == null) {
                return;
            }
    
    
    
            final LocalUserManager.UserManageCallback registerCallback = new LocalUserManager.UserManageCallback() {
                @Override
                public void onUserResult(boolean result, String message) {
                    handleRegisterResult(result, message, imagePath);
            
                }
            };
            
            // register image
            LocalUserManager.getInstance().registerByImage(RegisterCaptureActivity.this.getApplicationContext(), mUserId, imagePath, faceDataInfo, registerCallback);
            
            
            
        }
    };
    
    
    
    private void handleRegisterResult(boolean result, String message, String imagePath) {
        Log.d("FaceLocalRegister", mUserId + " @@@@@@@@ register camera image imagePath = " + imagePath);
        Log.d("FaceLocalRegister", mUserId + " @@@@@@@@ register camera image mUserId = " + mUserId);
        Log.d("FaceLocalRegister", mUserId + " @@@@@@@@ register camera image result = " + result);
        Log.d("FaceLocalRegister", mUserId + " @@@@@@@@ register camera image message = " + message);
        
//        final String detail = "imagePath = " + imagePath + "\n"
//                + "userId = " + mUserId + "\n"
//                + "result = " + result + "\n"
//                + "message = " + message + "\n"
//                ;
    
        final String registerResult = mUserId + " 本地注册成功!!!!!!!";
        showRegisterResult(registerResult);
    
    }
    
    
    
    
    private static String persistentFaceImage(byte[] faceImageJPGBytes) {
        final String imageDir = FaceLocalService.getInstance().mApplicationContext.getDir("TempFaceImageDir", Context.MODE_PRIVATE).getAbsolutePath();
        final String imagePath = imageDir + "/" + "TempRegisterFaceImage" + "_Timestamp" + System.currentTimeMillis() + ".jpg";
        Log.d("FaceLocalRegister", "=============== local image register persistentFaceImage imagePath = " + imagePath);
        
        try {
            final FileOutputStream outputStream = new FileOutputStream(imagePath);
            outputStream.write(faceImageJPGBytes);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    
    
        return imagePath;
    }
    
    
    
    
    
    public static String getDayTimeLabel(long timestamp) {
        final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss:SSS");
        final String timeLabel = format.format(new Date(timestamp));
        return timeLabel;
    }
    
    private FaceFrameManager.VerifyResultCallback mVerifyResultCallback = new FaceFrameManager.VerifyResultCallback() {
    
        @Override
        public void onDetectPause() {
            Log.d("FaceLocalRegister", ".......... FaceLocalCameraRegisterActivity onDetectPause!!!!!");
            FaceFrameManager.resumeDetect();
        }
    
//        @Override
//        public void onSearchBegin() {
//
//        }
    
    
        @Override
        public void onVerifyResult(VerifyResult verifyResult) {
        
        
        }
        
        
        
        

//        @Override
//        public void onSearchResult(String faceJpgBase64, long faceId, int resultCode, float checkScore, float score, UserFeature userFeature, String resultMessage, long checkConsumeTime, long searchConsumeTime) {
//
//        }
    
    
//        @Override
//        public void onSearchResult(int resultCode, String result) {
//            Log.d("FaceLocalClient", "########### onSearchResult result = " + result);
//            final String resultLabel = "结果:" + getTimeText(System.currentTimeMillis()) + " 用户:" + result.toString();
//
//            mResultView.setText(resultLabel);
//
//
//
//        }
        
//        @Override
//        public void onSearchDuration(long checkConsumeTime, long searchConsumeTime) {
////            Log.d("FaceLocalClient", "########### onSearchResult onSearchDuration = " + duration);
//            mDurationView.setText("耗时:" + (checkConsumeTime + searchConsumeTime));
//        }
        
//        @Override
//        public void onFaceBox(Rect rect) {
//            Log.d("FaceLocalClient", "########### onFaceBox rect = " + rect);
//
////            final ArrayList<Rect> boxes = new ArrayList<>();
////            boxes.add(rect);
////            mBoxesView.reDraw(boxes, 0, 0);
//        }
    
//        @Override
//        public void onFaceChanged() {
//
//        }
//
//        @Override
//        public void onFaceAreaChanged(int faceArea) {
//
//        }
//
//        @Override
//        public void onRGBDisconnect() {
//
//        }
//
//        @Override
//        public void onNIRDisconnect() {
//
//        }
//
//        @Override
//        public void onRGBNull() {
//
//        }
//
//        @Override
//        public void onNIRNull() {
//
//        }
//
//        @Override
//        public void onRGBPreviewFail() {
//
//        }
//
//        @Override
//        public void onNIRPreviewFail() {
//
//        }
//
//        @Override
//        public void onNoCamera() {
//
//        }
    };
    
    
    
    
    
    private FaceFrameManager.BasePropertyCallback mDetectBoxCallback = new FaceFrameManager.BasePropertyCallback() {
//        @Override
//        public void onBoxDetect(List<DetectBox> detectBoxes) {
//
//        }
//
//        @Override
//        public void onFaceDetect(String imageData, String faceID) {
//
//        }
    
        @Override
        public void onBasePropertyResult(List<DetectFaceInfo> detectFaceInfos) {
            drawFaceBoxes(detectFaceInfos);
        }
    
//        @Override
//        public void onFaceLost() {
//
//        }

//        @Override
//        public void onFaceLost() {
//            mBoxesView.clearFaces();
//        }
//
//        @Override
//        public void onFaceNew(long faceId) {
//
//        }
    
    
    };
    
    
    private FaceFrameManager.FacePropertyCallback mFacePropertyCallback = new FaceFrameManager.FacePropertyCallback() {
    
        @Override
        public void onFacePropertyResult(FaceProperty faceProperty) {
        
        }
        
        
//        @Override
//        public void onFaceDetect(String imageData, String faceID) {
//            Log.d("FacePropertyClient", "############## onFaceDetect faceID = " + faceID);
//        }
//
//        @Override
//        public void onFaceResult(List<DetectFaceInfo> faces) {
//            drawFaceBoxes(faces);
//        }
//
//
//
//        @Override
//        public void onFaceLost() {
//            Log.d("FacePropertyClient", "############## onFaceLost");
//            mBoxesView.clearFaces();
//        }
//
//
//        @Override
//        public void onFaceNew(long faceId) {
//            Log.d("FacePropertyClient", "############## onFaceNew faceId = " + faceId);
//        }
        
    };
    
    
    
    
    
    
    
    private boolean noDrawBox = false;
    
//    private LocalRegisterTask.LocalRegisterCallback mRegisterCallback = new LocalRegisterTask.LocalRegisterCallback() {
//        @Override
//        public void onRegisterComplete(boolean result) {
//
//            if (result) {
//                noDrawBox = true;
//                mBoxesView.clearFaces();
//                Log.d("FaceLocalRegister", "FaceLocalRegister result = " + result + "userId = " + mUserIdString);
//
//                final String detail = mUserIdString + " 本地注册成功!!!!!!!";
//                showFaceVerifyResult(detail);
//            }
//
//
//
//
//        }
//    };
    
    
    
    
    
    
    
    
    
    public void showRegisterResult(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRegisterResult.setText(result);
            }
        });
    }
    
    
    
    
    
//    private void drawFaceBoxes(List<DetectFaceInfo> faces) {
//        if (!noDrawBox) {
//            mBoxesView.updateFaceBoxes(faces);
//        }
//    }
    
    
    
    private void drawFaceBoxes(List<DetectFaceInfo> faces) {
        if (noDrawBox) {
            return;
        }
        
        // TODO draw face boxes on UVCCamera reconnect
        
        
        if (faces != null && faces.size() > 0) {
            for (DetectFaceInfo face : faces) {
                final Rect cameraRect = face.getFaceRect();
                final Rect previewRect = FaceBoxUtil.getPreviewBox(cameraRect);
                face.setFaceRect(previewRect);
            }
        }
        
        
        mBoxesView.updateFaceBoxes(faces);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    protected void onDestroy() {
        // release all
        FaceCameraHelper.getInstance().releaseFaceCamera();
        super.onDestroy();
    }
    
    
    
    
}
