package com.jdjr.risk.face.local.demo.business;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.jdjr.risk.face.local.demo.R;
import com.jdjr.risk.face.local.demo.camera.CameraSettings;
import com.jdjr.risk.face.local.demo.camera.FaceCameraHelper;
import com.jdjr.risk.face.local.demo.check.CheckFaceHelper;
import com.jdjr.risk.face.local.demo.verify.DisplaySize;
import com.jdjr.risk.face.local.demo.verify.FaceBoxUtil;
import com.jdjr.risk.face.local.demo.view.FaceBoxesView;
import com.jdjr.risk.face.local.detect.DetectFaceInfo;
import com.jdjr.risk.face.local.frame.FaceFrameManager;
import com.jdjr.risk.face.local.property.FaceProperty;
import com.jdjr.risk.face.local.register.RegisterResult;
import com.jdjr.risk.face.local.search.LocalSearchResult;
import com.jdjr.risk.face.local.settings.FaceSettings;
import com.jdjr.risk.face.local.user.UserFeature;
import com.jdjr.risk.face.local.verify.VerifyResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by michael on 18-12-21.
 */

public class QinlinVerifyActivity extends Activity {
    private FaceBoxesView mBoxesView;
    
    // show search user result view
    private TextView mResultView;
    
    // show check verify score
    private TextView mScoreView;
    
    // show search user duration view
    private TextView mDurationView;
    
    
//    private TextView mFaceAreaView;
    
    
    private float mRGBScaleY = 1;
    
    
    private ImageView mFaceView;
    
//    private ImageView mCameraImageView;
    
    private TextView mFinalResultView;
    
    private ViewFlipper mSearchResultFlipper;
    
    private ViewFlipper mFaceGuideFlipper;
    
    private ViewFlipper mFinalResultFlipper;
    
    private TextView faceGuideView;
    
    private ImageView mADView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.d("FaceLocalThread", "============= VerifyRGBNIRActivity UI thread id = " + Thread.currentThread().getId());
    
        Log.d("FaceLocal", "########## getResources().getDisplayMetrics().widthPixels = " + getResources().getDisplayMetrics().widthPixels);
        Log.d("FaceLocal", "########## getResources().getDisplayMetrics().heightPixels = " + getResources().getDisplayMetrics().heightPixels);
        
        
        setContentView(R.layout.activity_verify_qinlin);
        
        mADView =findViewById(R.id.ad_view);
        
        faceGuideView = findViewById(R.id.face_guide_view);
    
//        mCameraImageView = findViewById(R.id.camera_image_view);
        
        mBoxesView = findViewById(R.id.face_boxes_view);
        
        
        mResultView = findViewById(R.id.search_result_view);
        mScoreView = findViewById(R.id.check_verify_score_view);
        mDurationView = findViewById(R.id.search_duration_view);
    
    
        mFinalResultView = findViewById(R.id.final_result_view);
    
        mSearchResultFlipper = findViewById(R.id.search_result_flipper);
        mFaceGuideFlipper = findViewById(R.id.face_guide_flipper);
        
        
        
        mFinalResultFlipper = findViewById(R.id.final_result_flipper);
        
        
        
        
        
    
    
    
//        final TextView featureStateView = findViewById(R.id.feature_state_view);
//        final String label = "是否开启人脸识别:" + (FaceLocalSettings.isEnableLarge() ? "是" : "否") +
//                        "     是否开启获取属性:" + (FaceLocalSettings.isEnableSmall() ? "是" : "否");
//        featureStateView.setText(label);
        
//        mFaceAreaView = findViewById(R.id.face_area_view);
        
        mFaceView = findViewById(R.id.selected_face_view);
        
        
        
        // RGB preview
        RGBTextureView = findViewById(R.id.camera_preview_rgb);
        if (!CameraSettings.MIRROR_RGB) {
            RGBTextureView.setScaleX(-1);
        }
        
        
        // NIR preview
        NIRTextureView = findViewById(R.id.camera_preview_nir);
        if (!CameraSettings.MIRROR_NIR) {
            RGBTextureView.setScaleX(-1);
        }

        initFaceVerify();
        
        
        Log.d("FaceLocalBox", "....................... VerifyRGBNIRActivity UI thread id = " + Thread.currentThread().getId());
        
        
//        initTTS();
    
        // ResolutionUtil.printResolution(this.getApplicationContext());
    

    
        resizeLayout();
    
        
    }
    
    private void resizeLayout() {
    
//        05-13 02:55:49.054 12309-12309/com.jdjr.risk.face.local.demoooooooo D/FaceLocalResolution: .......... widthPixels = 1208
//        05-13 02:55:49.054 12309-12309/com.jdjr.risk.face.local.demoooooooo D/FaceLocalResolution: .......... heightPixels = 720
        final DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        final int widthPixels = metrics.widthPixels;
        final int heightPixels = metrics.heightPixels;
        
        if (widthPixels == 1208 && heightPixels == 720) {
            
            final TextureView previewRGB = findViewById(R.id.camera_preview_rgb);
            final RelativeLayout.LayoutParams RGBParams = (RelativeLayout.LayoutParams) previewRGB.getLayoutParams();
            RGBParams.removeRule(RelativeLayout.BELOW);
            previewRGB.setLayoutParams(RGBParams);
    
            final FaceBoxesView boxView = findViewById(R.id.face_boxes_view);
            final RelativeLayout.LayoutParams boxParams = (RelativeLayout.LayoutParams) previewRGB.getLayoutParams();
            boxParams.removeRule(RelativeLayout.BELOW);
            boxView.setLayoutParams(RGBParams);
    
        }
        
    
    
    }
    
    
    

//    private TextToSpeech mTTS;
//
//    private void initTTS() {
//        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int status) {
//                Log.d("FaceLocalTTS", "============== TextToSpeech onInit status = " + status);
//
//                final int result = mTTS.setLanguage(Locale.ENGLISH);
//
//                Log.d("FaceLocalTTS", "============== TextToSpeech setLanguage result = " + result);
//
//            }
//        });
//
//    }
//
//
//    private void speak(String text) {
////        mTTS.speak(text, TextToSpeech.QUEUE_ADD, null);
////        mTTS.speak(text, TextToSpeech.QUEUE_ADD, null, null);
//        // TODO replace with a new great TTS
//    }
    
    
    
    private TextureView RGBTextureView;
    private TextureView NIRTextureView;
    
    
    
    
    
    
    private void initFaceVerify() {
        
        
        FaceFrameManager.setBasePropertyCallback(mBasePropertyCallback);
    
    
    
        FaceFrameManager.setFaceType(FaceFrameManager.TYPE_FACE_PROCESS);
    
        FaceFrameManager.setVerifyResultCallback(mVerifyResultCallback);
    
        FaceFrameManager.setFacePropertyCallback(mFacePropertyCallback);
        
        FaceFrameManager.startDetectFace();
        
        
    
        FaceCameraHelper.getInstance().initFaceCamera(RGBTextureView, NIRTextureView);
        
        
        
        
    }
    
    
    private final FaceFrameManager.BasePropertyCallback mBasePropertyCallback = new FaceFrameManager.BasePropertyCallback() {
    
        
        
        @Override
        public void onBasePropertyResult(List<DetectFaceInfo> faceBoxList) {
            if (faceBoxList != null && faceBoxList.size() > 0) {
                QinlinVerifyActivity.this.onFaceResult(faceBoxList);
            } else {
                onFaceLost();
            }
            
            
        }
    
    
    };
    
    
    private void onFaceResult(List<DetectFaceInfo> faceBoxList) {
        drawFaceBoxes(faceBoxList);
        checkBox(faceBoxList);
        
        checkFaceArea(faceBoxList);
        
    }
    
    private void checkFaceArea(List<DetectFaceInfo> faceBoxList) {
        
        boolean hasLargeFace = false;
        
        for (DetectFaceInfo faceBox : faceBoxList) {
            final int faceArea = faceBox.getFaceArea();
//            Log.d("FaceLocalArea", "============== checkFaceArea faceArea = " + faceArea);
            
            if (faceArea >= FaceSettings.getVerifyFaceArea()) {
                hasLargeFace = true;
                break;
            }else {
                // do nothing
            }
        
        }
        
        
        
        final boolean isPreviewVisible = isRGBVisible();
        
        
//        Log.d("FaceLocalArea", "=============== hasLargeFace = " + hasLargeFace);
//        Log.d("FaceLocalArea", "=============== isPreviewVisible = " + isPreviewVisible);
        
        
        
        
        
        if (hasLargeFace) {
            if (!isPreviewVisible) {
                setRGBPreviewVisible(true);
            
            } else {
                // do nothing
            
            }
            
        
        } else {
            
            if (isPreviewVisible) {
                setRGBPreviewVisible(false);
            } else {
                // do nothing
            }
        
        }
        
        
        
    }
    
    private boolean isRGBVisible() {
        if (RGBTextureView != null) {
            final int width = RGBTextureView.getLayoutParams().width;
            final int height = RGBTextureView.getLayoutParams().height;
            if (width > 1 && height > 1) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    
    
    
    }
    
    
    private void setRGBPreviewVisible(final boolean visible) {
        Log.d("FaceLocalArea", "@@@@@@@@@@ setRGBPreviewVisible isVisible = " + visible);
        
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
        
//                Log.d("FaceLocalArea", "@@@@@@@@@@@ setRGBPreviewVisible");
                
                final RelativeLayout.LayoutParams paramsRGB = (RelativeLayout.LayoutParams) RGBTextureView.getLayoutParams();
                if (visible) {
                    paramsRGB.width = 640;
                    paramsRGB.height = 480;
                } else {
                    paramsRGB.width = 0;
                    paramsRGB.height = 0;
                }
                
                RGBTextureView.setLayoutParams(paramsRGB);
                
                
                
                
                
                final RelativeLayout.LayoutParams paramsAD = (RelativeLayout.LayoutParams) mADView.getLayoutParams();
                if (visible) {
                    paramsAD.height = 480;
                } else {
                    paramsAD.height = RelativeLayout.LayoutParams.MATCH_PARENT;
                }

                mADView.setLayoutParams(paramsAD);
                
                
                
                
            }
        });
    
    }
    
    
    public void onFaceLost() {
        mBoxesView.clearFaces();
    }
    
    
    
    
    private void checkBox(List<DetectFaceInfo> faceBoxList) {
        if (faceBoxList != null && faceBoxList.size() == 1) {
        
            checkBox(faceBoxList.get(0));
            
        
        
        
        }
    }
    
    private void checkBox(DetectFaceInfo faceProperty) {
        // check face yaw
        final int checkYawResult = CheckFaceHelper.checkFaceYaw(faceProperty);
        if (checkYawResult == 1) {
            displayFaceGuide(FACE_LEFT);
            return;
        }
        else if (checkYawResult == 2) {
            displayFaceGuide(FACE_RIGHT);
            return;
        }
        else {
        
        }
    
    
        // check face pitch
        final int checkPitchResult = CheckFaceHelper.checkFacePitch(faceProperty);
        if (checkPitchResult == 1) {
            displayFaceGuide(FACE_UP);
            return;
        }
        else if (checkPitchResult == 2) {
            displayFaceGuide(FACE_DOWN);
            return;
        }
        else {
        
        }
        
        // check face area
        if (!CheckFaceHelper.checkFaceArea(faceProperty)) {
            displayFaceGuide(CLOSE_SCREEN);
            return;
        } else {
        
        }
        
        
        // check face position
        if (!CheckFaceHelper.checkFacePosition(faceProperty)) {
            displayFaceGuide(CLOSE_CENTER);
            return;
        } else {
        
        }
        
        

    
    }
    
    
    private static final int CLOSE_SCREEN = 1;
    private static final int CLOSE_CENTER = 11;
    private static final int FACE_UP = 21;
    private static final int FACE_DOWN = 22;
    private static final int FACE_LEFT = 31;
    private static final int FACE_RIGHT = 32;
    
    
    
    
    private static String getGuideText(int action) {
        if (action == CLOSE_SCREEN) {
            return "请靠近屏幕";
        }
        else if (action == CLOSE_CENTER) {
            return "请把脸放在屏幕中央";
        }
        else if (action == FACE_UP) {
            return "请抬头";
        }
        else if (action == FACE_DOWN) {
            return "请低头";
        }
        else if (action == FACE_LEFT) {
            return "请左转";
        }
        else if (action == FACE_RIGHT) {
            return "请右转";
        }
        else {
            return "";
        }
    
    
    
    }
    
    
    private String mGuideText = "";
    
    private void displayFaceGuide(int action) {
    
        final String guideText = getGuideText(action);
        
        
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                faceGuideView.setText(getDayTimeLabel(System.currentTimeMillis()) + guideText);
                
                flipGuideText();
            }
        });
        
        
        
//        Log.d("FaceLocalGuide", "================ guideText = " + guideText);
//        Log.d("FaceLocalGuide", "================ mGuideText = " + mGuideText);
        
        if (!guideText.equals(mGuideText)) {
//            speak(guideText);
        }
        
        
        
        mGuideText = guideText;
        
        
    }
    
    
    
    private void flipGuideText() {
        mFaceGuideFlipper.startFlipping();
        mFaceGuideFlipper.stopFlipping();
    }
    
    
    
    
    
    
    
    
    
    
    
    private FaceFrameManager.VerifyResultCallback mVerifyResultCallback = new FaceFrameManager.VerifyResultCallback() {
    
        /**
         * 选帧结束
         */
        @Override
        public void onDetectPause() {
            /**
             * 继续选帧
             */
            Log.d("FaceLocalUVC", "........................ VerifyRGBNIRActivity onDetectPause");
            FaceFrameManager.resumeDetect();
        }
    
    
        
    
        @Override
        public void onVerifyResult(VerifyResult result) {
    
    
            final long faceId = result.getFaceId();
            final int resultCode = result.getVerifyResult();
            final float checkScore = result.getCheckScore();
            final float verifyScore = result.getVerifyScore();
            final UserFeature userFeature = result.getUser();
            
            final long checkConsumeTime = result.getCheckConsumeTime();
            final long extractConsumeTime = result.getExtractConsumeTime();
            final long verifyConsumeTime = result.getVerifyConsumeTime();
            
            Log.d("FaceLocalVerify", "================ checkConsumeTime = " + checkConsumeTime);
            Log.d("FaceLocalVerify", "================ extractConsumeTime = " + extractConsumeTime);
            Log.d("FaceLocalVerify", "================ verifyConsumeTime = " + verifyConsumeTime);
            
            
    
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
            
                    handleSearchResult(faceId, resultCode, checkScore, verifyScore, userFeature, checkConsumeTime, extractConsumeTime, verifyConsumeTime);

//                    if (faceImage != null) {
//                        mFaceView.setImageBitmap(faceImage);
//                    }
            
            
                }
            });
            
            
            
            
            
            
            final byte[] faceImageBytes = result.getFaceImageBytes();
    
            if (faceImageBytes != null && faceImageBytes.length != 0) {
                displayFace(faceImageBytes);
            }
            
            
        
        }
        
        
        
    

    };
    
    
    
    
    
    
    public static String getDayTimeLabel(long timestamp) {
        final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss:SSS");
        final String timeLabel = format.format(new Date(timestamp));
        return "[" + timeLabel + "]";
    }
    
    
    private void handleSearchResult(long faceId, int resultCode, float checkScore, float verifyScore, UserFeature userFeature, long checkConsumeTime, long extractConsumeTime, long searchConsumeTime) {
        final String timeLabel = getDayTimeLabel(System.currentTimeMillis());
        
        final FaceVerifyResult verifyResult = new FaceVerifyResult(faceId, resultCode);
        if (userFeature != null) {
            verifyResult.setUserId(userFeature.getUserUniqueId());
        }
        cacheVerifyResult(verifyResult);
        
        String verifyResultMessage = null;
        if (resultCode == VerifyResult.NOT_FACE) {
            verifyResultMessage = "不是真实人脸";
        }
        else if (resultCode == VerifyResult.NEW_FACE) {
            verifyResultMessage = "无法识别此人";
        }
        else if (resultCode == VerifyResult.USER_FACE) {
            verifyResultMessage = "人脸识别成功";
        }
        else {
        
        }
        
        if (resultCode == LocalSearchResult.SUCCESS) {
    
            cacheSuccessVerifyResult(faceId, userFeature.getUserUniqueId());
            
            final String userJsonStr = userFeature.getFacePath();
            Log.d("FaceLocalVerifyResult", "...............handleSearchResult userJsonStr = " + userJsonStr);
            Log.d("FaceLocalVerifyResult", "...............handleSearchResult userId = " + userFeature.getUserUniqueId());
            
            final String userIdString = userFeature.getUserUniqueId();
            final String genderLabel = userFeature.getGenderLabel();
            final String age = "" + userFeature.getAge();
            Log.d("FaceLocalLarge", "@@@@@@@@@ client onSearchResult userIdString = " + userIdString);
            
            final String resultLabel = timeLabel + " faceId:" + faceId + "成功"
                                        + " (用户:" + userIdString
                                        + " 性别:" + genderLabel
                                        + " 年龄:" + age
                                        + " 分数:" + verifyScore
                                        + ")"
                                        ;
            mResultView.setText(resultLabel);
            // mResultView.setBackgroundColor(Color.BLUE);
            
            final String scoreDetail = "防伪分数:" + checkScore + "\n" + "识别分数:" + verifyScore + "\n";
            mScoreView.setText(scoreDetail);
            
            final String searchDetail = "防伪耗时: " + checkConsumeTime + "\n"
                    + "特征耗时: " + extractConsumeTime + "\n"
                    + "识别耗时: " + searchConsumeTime + "\n"
                    ;
            mDurationView.setText(searchDetail);
            
            
            flipSearchResult();
            
            
        } else {
            
            mResultView.setText(timeLabel + " faceId:" + faceId + verifyResultMessage);
            
            String scoreDetail = "";
            if (checkScore != 0 && checkScore != -1.0 && checkScore != -22.0) {
                scoreDetail = scoreDetail + "防伪耗时: " + checkConsumeTime + "\n";
            }
            if (verifyScore != 0 && verifyScore != -33.0 && verifyScore != -1.0) {
                scoreDetail = scoreDetail + "识别分数:" + verifyScore + "\n";
            }
            mScoreView.setText(scoreDetail);
            
            mDurationView.setText("");
            // mResultView.setBackgroundColor(Color.RED);
    
            flipSearchResult();
        }
    }
    
    
    private void flipSearchResult() {
        mSearchResultFlipper.startFlipping();
        mSearchResultFlipper.stopFlipping();
    }
    
    public void inputId(View view) {
        final EditText idText = findViewById(R.id.card_id_view);
        final String cardId = idText.getText().toString().trim();
        if (cardId != null && cardId.length() > 0) {
            FaceFrameManager.setCardId(cardId, new FaceFrameManager.FaceRegisterCallback() {
                @Override
                public void onRegisterResult(RegisterResult result) {
                    if (result != null) {
                        Log.d("FaceLocalRegister", "=============== VerifyRGBNIRActivity onRegisterResult = " + result.isResult());
                    } else {
                        Log.d("FaceLocalRegister", "=============== VerifyRGBNIRActivity onRegisterResult null !!!");
                    }
                }
                
            });
        }
        
    
    
    }
    
    
    private class FaceVerifyResultTemp {
        public long faceId;
        public String userId;
    
        public FaceVerifyResultTemp(long faceId, String userId) {
            this.faceId = faceId;
            this.userId = userId;
        }
    }
    
    
    
    
    
    
    
    
    
    // TODO =============================================================================================================================
    private LinkedList<FaceVerifyResultTemp> mResultCache = new LinkedList<>();
    private static final int CAPACITY_RESULT_MAP = 13;
    
    private void cacheSuccessVerifyResult(long faceId, String userId) {
//        Log.d("FaceLocalVerifyDebug", "............ cacheVerifyResult faceId = " + faceId);
//        Log.d("FaceLocalVerifyDebug", "............ cacheVerifyResult userId = " + userId);
        
        if (mResultCache.size() >= CAPACITY_RESULT_MAP) {
            mResultCache.removeFirst();
        }
        
        final FaceVerifyResultTemp verifyResult = new FaceVerifyResultTemp(faceId, userId);
        mResultCache.add(verifyResult);
    }
    
    private String getVerifyResult(long faceId) {
        String userId = null;
    
        for (int i = mResultCache.size() - 1; i >= 0; i--) {
            final FaceVerifyResultTemp resultTemp = mResultCache.get(i);
            if (resultTemp.faceId == faceId && resultTemp.userId != null && resultTemp.userId.length() > 0) {
                userId = resultTemp.userId;
            }
        }
        
//        Log.d("FaceLocalVerifyDebug", "........... from cache getVerifyResult faceId = " + faceId);
//        Log.d("FaceLocalVerifyDebug", "........... from cache getVerifyResult userId = " + userId);
        return userId;
    
    }
    // TODO =============================================================================================================================
    
    
    
    
    
    
    
    
    
    
    
    
    private LinkedList<FaceVerifyResult> mVerifyResults = new LinkedList<>();
    
    private static final int TIMES_FAILURE = 3;
    
    
    private void cacheVerifyResult(FaceVerifyResult result) {
        if (mVerifyResults.size() >= TIMES_FAILURE) {
            mVerifyResults.pollFirst();
        }
        
        mVerifyResults.add(result);
        
        checkFailureResult(result);
    
    
    
    }
    
    private void checkFailureResult(FaceVerifyResult result) {
        final int resultCode = result.getResultCode();
        if (resultCode == LocalSearchResult.SUCCESS) {
            displayFinalResultSuccess(result);
            
        } else {
            
            
            
            // if one face failure max time, display failure result.
            if (mVerifyResults != null && mVerifyResults.size() >= TIMES_FAILURE) {
                boolean isFailureMax = true;
    
                for (FaceVerifyResult verifyResult : mVerifyResults) {
                    if (verifyResult.getFaceId() != result.getFaceId() || verifyResult.getResultCode() == LocalSearchResult.SUCCESS) {
                        isFailureMax = false;
                        break;
                    }
                    
                }
            
                if (isFailureMax) {
                    mVerifyResults.clear();
                    displayFinalResultFailure(result);
                }
            
            
            } else {
                // do nothing
            
            }
            
            
            
            
            
            
        }
        
        
    
    
    
    
    }
    
    
    
    private void displayFinalResultSuccess(FaceVerifyResult result) {
    
        final String timeLabel = getDayTimeLabel(System.currentTimeMillis());
        
        final String resultLabel = timeLabel + " FaceId:" + result.getFaceId() + " 成功 " + "\n" + result.getUserId();
        
        mFinalResultView.setText(resultLabel);
    
        mFinalResultView.setBackgroundColor(Color.BLUE);
        
        flipFinalResult();
        
        
    }
    
    private void displayFinalResultFailure(FaceVerifyResult result) {
        final String timeLabel = getDayTimeLabel(System.currentTimeMillis());
    
        String failureLabel = " 最终失败!!!";
        
        if (result.getResultCode() == LocalSearchResult.NOT_HUMAN_FACE) {
            mFinalResultView.setBackgroundColor(Color.RED);
            failureLabel = " 防伪失败!!!";
        }
        else if (result.getResultCode() == LocalSearchResult.FACE_UNKNOWN) {
            mFinalResultView.setBackgroundColor(Color.YELLOW);
            failureLabel = " 识别失败!!!";
        }
        else {
        
        }
        
        final String resultLabel = timeLabel + " FaceId:" + result.getFaceId() + failureLabel ;
    
        mFinalResultView.setText(resultLabel);
        

    
        flipFinalResult();
    
    
    
    
    }
    
    private void flipFinalResult() {
        mFinalResultFlipper.startFlipping();
        mFinalResultFlipper.stopFlipping();
    }
    
    // TODO =============================================================================================================================
    
    
    
    
    
    
    
    
    
    
    
    
    private class FaceVerifyResult {
        private long faceId;
        private int resultCode;
    
    
        public FaceVerifyResult(long faceId, int resultCode) {
            this.faceId = faceId;
            this.resultCode = resultCode;
        }
    
    
        public long getFaceId() {
            return faceId;
        }
    
        public void setFaceId(long faceId) {
            this.faceId = faceId;
        }
    
        public int getResultCode() {
            return resultCode;
        }
    
        public void setResultCode(int resultCode) {
            this.resultCode = resultCode;
        }
        
        
        private String userId;
    
        
        public String getUserId() {
            return userId;
        }
    
        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
    
    
    
    
    
    
    
    
    
    private final ExecutorService displayService = Executors.newSingleThreadExecutor();
    
//    private static final Bitmap FACE_IMAGE = Bitmap.createBitmap(CameraSize.getWidthRotate(), CameraSize.getHeightRotate(), Bitmap.Config.ARGB_8888);
    
    private void displayFace(final byte[] faceImageBytes) {
        displayService.submit(new Runnable() {
            @Override
            public void run() {
                final BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inBitmap = FACE_IMAGE;
                final Bitmap faceImage = BitmapFactory.decodeByteArray(faceImageBytes, 0, faceImageBytes.length, options);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        mFaceView.setImageBitmap(faceImage);
                    }
                });

            }
        });

    }
    
    
    
    
    
    

    
    private FaceFrameManager.FacePropertyCallback mFacePropertyCallback = new FaceFrameManager.FacePropertyCallback() {
        @Override
        public void onFacePropertyResult(FaceProperty faceProperty) {
            Log.d("FaceLocalProperty", "######################################################################################");
            Log.d("FaceLocalProperty", "############# onFaceProperty getFaceId = " + faceProperty.getFaceId());
            Log.d("FaceLocalProperty", "############# onFaceProperty getGenderText = " + faceProperty.getGenderText());
            Log.d("FaceLocalProperty", "############# onFaceProperty getAge = " + faceProperty.getAge());
            Log.d("FaceLocalProperty", "############# onFaceProperty getEmotionText = " + faceProperty.getEmotionText());
            Log.d("FaceLocalProperty", "######################################################################################");
            
            cacheFaceProperty(faceProperty);
    
            getExtractConsumeTime();
            
        }
    
    
    
    
    
    };
    
    private long onPropertyTime = System.currentTimeMillis();
    
    private void getExtractConsumeTime() {
        final long consumeTime = System.currentTimeMillis() - onPropertyTime;
        onPropertyTime = System.currentTimeMillis();
        Log.d("FaceLocalPerformance", "================== extract property consumeTime = " + consumeTime);
    }
    
    
    // TODO ============================================================================================
    private static final int MAX_PROPERTY_SIZE = 3;
    private LinkedList<FaceProperty> mFacePropertyCache = new LinkedList<FaceProperty>();
    
    private void cacheFaceProperty(FaceProperty faceProperty) {
        
        if (mFacePropertyCache.size() >= MAX_PROPERTY_SIZE) {
            mFacePropertyCache.removeFirst();
        }
        
        mFacePropertyCache.add(faceProperty);
    }
    
    private FaceProperty getFaceProperty(long faceId) {
        
        // desc order
        for (int i = mFacePropertyCache.size() - 1; i >= 0; i--) {
            final FaceProperty faceProperty = mFacePropertyCache.get(i);
            if (faceProperty.getFaceId() == faceId) {
                return faceProperty;
            }
        }
        
        return null;
    }
    
    
    
    
    // TODO ============================================================================================
    
    
    
    
    
    
    
    
    
    
    
    
    private long allNeutralTime = System.currentTimeMillis();
    
    private void drawFaceBoxes(List<DetectFaceInfo> faces) {
        
        // TODO draw face boxes on UVCCamera reconnect
        
        
        if (faces != null && faces.size() > 0) {
            for (DetectFaceInfo face : faces) {
    
                transformFaceBox(face);
    
                transformLandmarks(face);
    
                appendVerifyResult(face);
                
                appendFaceProperty(face);
            }
        }
        
        
        
        
        
        
        
        
        // TODO check 3 user has emotion
        // if all Neutral
        boolean allNeutral = true;
        final HashMap<String, Integer> faceNumber = new HashMap();
        for (DetectFaceInfo faceBox : faces) {
            if (faceBox.getEmotionText().equals("厌恶")) {
                allNeutral = false;
            }
            
            faceNumber.put(faceBox.getFaceId() + "", 1);
        }
        
        if (allNeutral && faceNumber.entrySet().size() >= 3) {
            final long consumeTime = System.currentTimeMillis() - allNeutralTime;
            allNeutralTime = System.currentTimeMillis();
            Log.d("FaceLocalPerformance", "===================== onAllNeutralEmotion consumeTime = " + consumeTime);
            
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mFinalResultView.setText("茄子");
                    flipFinalResult();
        
                }
            });
            
        }
        
        
        
        
        
        
        
        
        mBoxesView.updateFaceBoxes(faces);
        
    }
    
    
    
    
    
    
    private void transformFaceBox(DetectFaceInfo face) {
        final Rect cameraRect = face.getFaceRect();
        final Rect previewRect = FaceBoxUtil.getPreviewBox(cameraRect);
        face.setFaceRect(previewRect);
        
        
    }
    
    private void transformLandmarks(DetectFaceInfo face) {
//        final boolean mirrorLandscape = CameraDisplayMirror.MIRROR_LANDSCAPE;
//        final boolean mirrorPortrait = CameraDisplayMirror.MIRROR_PORTRAIT;
        
        final ArrayList<Point> landmarkList = face.getLandmarks();
        for (Point point : landmarkList) {
//            if (!CameraSettings.MIRROR_RGB) {
//                point.x = DisplaySize.WIDTH - point.x;
//            }
//            if (!mirrorPortrait) {
//                point.y = DisplaySize.HEIGHT - point.y;
//            }
        
            if (!CameraSettings.IMAGE_MIRROR_RGB) {
                point.x = DisplaySize.WIDTH - point.x;
            }
            
        }
        face.setLandmarks(landmarkList);
    }
    
    
    
    private void appendVerifyResult(DetectFaceInfo face) {
        final long faceId = face.getFaceId();
//        Log.d("FaceLocalVerifyDebug", "......... appendVerifyResult getVerifyResult faceId = " + faceId);
        final String userId = getVerifyResult(faceId);
//        Log.d("FaceLocalVerifyDebug", "......... appendVerifyResult getVerifyResult userId = " + userId);
        face.setUserId(userId);
    }
    
    
    private void appendFaceProperty(DetectFaceInfo face) {
        if (face == null) {
            return;
        }
        
        final FaceProperty faceProperty = getFaceProperty(face.getFaceId());
        
        if (faceProperty != null) {
            final int gender = faceProperty.getGender();
            final int age = faceProperty.getAge();
            final float[] emotionScores = faceProperty.getEmotionScore();
            
            
            face.setGender(gender);
            face.setAge(age);
            face.setEmotionScores(emotionScores);
            
        }
        
    }
    
    




    
    
    
    
    
    
    
    
    
    
    
    @Override
    protected void onDestroy() {
        // release all
        FaceCameraHelper.getInstance().releaseFaceCamera();
        super.onDestroy();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
