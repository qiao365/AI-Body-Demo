package com.jdjr.risk.face.local.demo.remote;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.TextureView;
import android.widget.TextView;
import android.widget.Toast;

import com.jdjr.risk.face.local.demo.R;
import com.jdjr.risk.face.local.demo.verify.FaceBoxUtil;
import com.jdjr.risk.face.local.demo.view.FaceBoxesView;
import com.jdjr.risk.face.local.detect.DetectFaceInfo;
import com.jdjr.risk.face.local.demo.camera.FaceCameraHelper;
import com.jdjr.risk.face.local.frame.FaceFrameManager;
import com.jdjr.risk.face.local.ndk.FaceDataInfo;
import com.jdjr.risk.face.local.property.FaceProperty;
import com.jdjr.risk.face.local.remote.RemoteHttpService;
import com.jdjr.risk.face.local.verify.VerifyResult;

import java.util.List;

/**
 * Created by michael on 19-4-8.
 */

public class RemoteRegisterCaptureActivity extends Activity {
    
    
    private FaceBoxesView mBoxesView;
    
    
//    private TextView mRegisterResult;
    
    private TextView mResultView;
    private TextView mDurationView;
    
    
    
    private  static String mUserIdString = null;
    
    private String mUserName;
    
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_remote_register_capture);
    
    
        mUserIdString = getIntent().getStringExtra("userId");
        
        mUserName = getIntent().getStringExtra("userName");
    
    
        mBoxesView = findViewById(R.id.face_boxes_view);
    
//        mRegisterResult = findViewById(R.id.local_register_result);
    
        mResultView = findViewById(R.id.search_result_view);
        mDurationView = findViewById(R.id.search_duration_view);
    
    
    
        // RGB preview
        final TextureView RGBTextureView = findViewById(R.id.camera_preview_rgb);
        // NIR preview
        final TextureView NIRTextureView = findViewById(R.id.camera_preview_nir);
    
    
        // initialize all
//        final boolean isRegister = false;
    
        
        FaceFrameManager.setBasePropertyCallback(mDetectBoxCallback);
        
        // set search result callback
        FaceFrameManager.setVerifyResultCallback(mSearchResultCallback);
    
    
        // set face property callback
        FaceFrameManager.setFacePropertyCallback(mFacePropertyInfoCallback);
    
    
        // FaceLocalVerifyManager.getInstance().setRegisterCallback(mRegisterCallback);
    
        FaceFrameManager.setFaceType(FaceFrameManager.TYPE_FACE_DETECT);
        FaceFrameManager.setFaceCallback(mDetectCallback);
    
        FaceFrameManager.startDetectFace();
        
        FaceCameraHelper.getInstance().initFaceCamera(RGBTextureView, NIRTextureView);
    
    
    }
    
    private FaceFrameManager.FaceDetectCallback mDetectCallback = new FaceFrameManager.FaceDetectCallback() {
        @Override
        public void onFaceDetect(byte[] faceImageJPGBytes, FaceDataInfo faceDataInfo) {
            Log.d("FaceLocalRegisterRemote", "..............onFaceDetect");
            
            final String faceImageJPGBase64Encoded = Base64.encodeToString(faceImageJPGBytes, Base64.DEFAULT);;
            
            
            
            
            RemoteHttpService.registerRemote(mUserIdString, mUserName, faceImageJPGBase64Encoded, new Runnable() {
                @Override
                public void run() {
                    Log.d("FaceLocalRegisterRemote", "..............registerRemote success!!!!!!!!");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
    
                            Toast.makeText(RemoteRegisterCaptureActivity.this, "云端注册成功！！！" + mUserIdString, Toast.LENGTH_LONG).show();
                            
                            RemoteRegisterCaptureActivity.this.finish();
                        
                        }
                    });
                    
                }
            });
            
            
        
        }
    };
    
    
    
//    public static String getDayTimeLabel(long timestamp) {
//        final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss:SSS");
//        final String timeLabel = format.format(new Date(timestamp));
//        return timeLabel;
//    }
    
    private FaceFrameManager.VerifyResultCallback mSearchResultCallback = new FaceFrameManager.VerifyResultCallback() {
        
        @Override
        public void onDetectPause() {
            Log.d("FaceLocalRegister", ".......... FaceLocalCameraRegisterActivity onDetectPause!!!!!");
            // FaceLocalVerifyManager.getInstance().resumeDetect();
            
            
            
        }
        
//        @Override
//        public void onSearchBegin() {
//
//        }
        
    
        @Override
        public void onVerifyResult(VerifyResult verifyResult) {
        
        
        
        
        }


//        @Override
//        public void onSearchResult(int resultCode, String result) {
//            Log.d("FaceLocalClient", "########### onSearchResult result = " + result);
//            final String resultLabel = "结果:" + getDayTimeLabel(System.currentTimeMillis()) + " 用户:" + result.toString();
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
//
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
//
//        }
    
//        @Override
//        public void onFaceNew(long faceId) {
//
//        }
    
    
    };
    
    
    private FaceFrameManager.FacePropertyCallback mFacePropertyInfoCallback = new FaceFrameManager.FacePropertyCallback() {
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
//        @Override
//        public void onFaceLost() {
//            Log.d("FacePropertyClient", "############## onFaceLost");
//            mBoxesView.clearFaces();
//        }
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
//                // mBoxesView.clearFaces();
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
    
    
    
    
    
    
    
    
    
//    public void showFaceVerifyResult(final String result) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                mRegisterResult.setText(result);
//                // mRegisterResult.animateText(result);
//            }
//        });
//    }





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
