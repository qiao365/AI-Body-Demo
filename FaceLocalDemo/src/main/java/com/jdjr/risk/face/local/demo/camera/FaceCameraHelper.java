package com.jdjr.risk.face.local.demo.camera;

import android.hardware.Camera;
import android.util.Log;
import android.view.TextureView;
import android.view.View;

import com.jdjr.risk.face.local.demo.frame.FrameHelper;
import com.jdjr.risk.face.local.demo.util.UsbCameraUtil;
import com.jdjr.risk.face.local.frame.FaceFrameManager;
import com.seu.magicfilter.widget.MagicCameraView;

import java.util.Arrays;


/**
 * Created by michael on 18-12-21.
 */

public class FaceCameraHelper {

    
    
    private FaceCameraHelper() {
        // do nothing
    }
    
    private static final FaceCameraHelper INSTANCE = new FaceCameraHelper();
    
    public static FaceCameraHelper getInstance() {
        return INSTANCE;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    private volatile boolean isReady = false;
    
    public void initFaceCamera(View RGBTextureView, View NIRTextureView) {
        if (isReady) {
            return;
        }
        
        Log.d("FaceLocalThread", "===================== initDetectVerify thread id = " + Thread.currentThread().getId());
    
        initFaceCameraImpl(RGBTextureView, NIRTextureView);
        
        
        isReady = true;
        
    
    }
    
    
    private void initFaceCameraImpl(View RGBTextureView, View NIRTextureView) {
//        UsbCameraUtil.showAllUsb(RGBTextureView.getContext().getApplicationContext());
        
        final int cameraType = CameraSettings.getCameraType();
        Log.d("FaceLocalCamera", "========================= initFaceCameraImpl type = " + cameraType);
        
        if (cameraType == CameraSettings.TYPE_SYSTEM) {
            SystemCameraManager.getInstance().initFaceCamera((TextureView) RGBTextureView, mRGBCallback);
        }
        
        else if (cameraType == CameraSettings.TYPE_JDY_K2002A_A4) {
            final TextureView textureViewRGB = (TextureView) RGBTextureView;
            final TextureView textureViewNIR = (TextureView) NIRTextureView;
            JDYCameraManager.getInstance().initFaceCamera(textureViewRGB, mRGBCallback, textureViewNIR, mNIRCallback);
        }
        
        else if (cameraType == CameraSettings.TYPE_MAGIC) {
            final int RGBCameraId = CameraType.getRGB();
            final MagicCameraView magicCameraViewRGB = (MagicCameraView) RGBTextureView;
            MagicCameraManager.getInstance().initFaceCamera(RGBCameraId, magicCameraViewRGB, mRGBCallback);
        }
        
        else {
            // do nothing
        
        
        }
        
        
        
        
        
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
//    CameraType:
//    Type_System
//    Type_JDY_K2002A_A4
//
//    CameraSystem:
//    System_Android
//    System_UVC
    
    
    
    /**
     * 释放人脸本地识别
     */
    public void releaseFaceCamera() {
        if (!isReady) {
            return;
        }
    
        Log.d("FaceLocalVerify", "===================== releaseDetectVerify");
        releaseFaceCameraImpl();
    
    
        isReady = false;
        
        
    }
    
    private void releaseFaceCameraImpl() {
        // FaceLocalCameraManager.getInstance().releaseFaceCamera();
        final int cameraType = CameraSettings.getCameraType();
        if (cameraType == CameraSettings.TYPE_SYSTEM) {
            SystemCameraManager.getInstance().releaseFaceCamera();
        }
//        else if (type == CameraDeviceType.UVC_RGBNIR_JDY_K2002A_A4) {
//            FaceUVCCameraManager.getInstance().releaseFaceCamera();
//        }
        else if (cameraType == CameraSettings.TYPE_JDY_K2002A_A4) {
            JDYCameraManager.getInstance().releaseFaceCamera();
        }
//        else if (type == CameraDeviceType.REPLAY_RGBNIR) {
//            ReplayCameraManager.releaseCameraPreview();
//        }
        
        else if (cameraType == CameraSettings.TYPE_MAGIC) {
            MagicCameraManager.getInstance().releaseFaceCamera();
        }
        
        
        
        else {
            // do nothing
        }
    }
    
    
    
    
    
    
//    private byte[] mLastFrameRGB;
    
    
    private Camera.PreviewCallback mRGBCallback = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(final byte[] data, final Camera camera) {
            
    
//            Log.d("FaceLocalMagic", "@@@@@@@@@@@@@@ FaceCameraHelper mRGBCallback onPreviewFrame");
            
            
            final byte[] frameCopy = Arrays.copyOf(data, data.length);
            
            if (camera != null) {
                camera.addCallbackBuffer(data);
            }
            
            
            
    
            // final byte[] frameRotateRGB = FrameHelper.getFrameRotate(frameCopy, CameraSettings.getCameraWidth(), CameraSettings.getCameraHeight());
    
            final byte[] frameRotateRGB = frameCopy;
            
    
//            mLastFrameRGB = frameRotateRGB;
            
            
            FaceFrameManager.handleCameraFrame(frameRotateRGB, mLastFrameNIR, CameraSettings.getCameraWidth(), CameraSettings.getCameraHeight());
            
            
        }
    };
    
    
    
    
    
    
    
    
    private byte[] mLastFrameNIR;
    
    
    private Camera.PreviewCallback mNIRCallback = new Camera.PreviewCallback() {
    
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            
            
    
            final byte[] copy = Arrays.copyOf(data, data.length);
    
            if (camera != null) {
                camera.addCallbackBuffer(data);
            }
            
            
            // final byte[] frameRotate = FrameHelper.getFrameRotate(copy, CameraSettings.getCameraWidth(), CameraSettings.getCameraHeight());
    
            final byte[] frameRotate = copy;
            
            
            mLastFrameNIR = frameRotate;
    
        }
    };
    
    
//    public void takePicture(String path) {
//        if (mLastFrameRGB == null) {return;}
//
//        if (CameraSettings.getCameraType() != CameraSettings.TYPE_MAGIC) {
//            return;
//        }
//
//        final byte[] frameRGBCopy = Arrays.copyOf(mLastFrameRGB, mLastFrameRGB.length);
//
//        MagicCameraManager.getInstance().takePicture(path, frameRGBCopy);
//
//
//
//    }
    
    
}
