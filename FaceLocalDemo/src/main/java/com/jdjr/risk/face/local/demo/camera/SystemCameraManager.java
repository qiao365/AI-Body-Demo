package com.jdjr.risk.face.local.demo.camera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;
import android.view.TextureView;


import com.seu.magicfilter.widget.MagicCameraView;

import java.io.IOException;
import java.util.List;

/**
 * Created by michael on 18-12-29.
 */

public class SystemCameraManager implements IFaceCameraManager {
    private SystemCameraManager() {
        // do nothing
    }
    
    private static final SystemCameraManager INSTANCE = new SystemCameraManager();
    
    public static SystemCameraManager getInstance() {
        return INSTANCE;
    }
    
    
    
    
    
    
    
    @Override
    public void initFaceCamera(TextureView previewTextureView, Camera.PreviewCallback mPreviewCallback) {
    
        previewTextureView.setSurfaceTextureListener(surfaceTextureListener);
        
        
        
        try {
            
            
//            final Activity activity = (Activity) previewTextureView.getContext();
//            final int displayRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
//            Log.d("FaceLocalCamera", "........... displayRotation = " + displayRotation);
            
            
            
            
//            final Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
//            Camera.getCameraInfo(0, cameraInfo);
//            final int cameraOrientation = cameraInfo.orientation;
//            Log.d("FaceLocalCamera", "........... cameraOrientation = " + cameraOrientation);
            
            
            
            
            
            
            
            
            final int cameras = Camera.getNumberOfCameras();
            Log.d("FacePropertyCamera", "####### cameras = " + cameras);
            
            final Camera camera = Camera.open(0);
        
//            final int cameraOrientation = 0;
//            final int cameraOrientation = FaceLocalSettings.getCameraImageRotation();
//            camera.setCameraDisplayRotation(cameraOrientation);
        
            
            
            
            Camera.Parameters params = camera.getParameters();
            
//            final List<Camera.Size> sizes =  params.getSupportedPreviewSizes();
//            for (Camera.Size size : sizes) {
//                Log.d("FaceLocalCamera", "............ size width = " + size.width);
//                Log.d("FaceLocalCamera", "............ size height = " + size.height);
//
//            }
            
            
            
            
            params.setPreviewSize(CameraSettings.getCameraWidth(), CameraSettings.getCameraHeight());
            camera.setParameters(params);
        
            for (int i = 0; i < 3; i++) {
                int length = CameraSettings.getCameraWidth() * CameraSettings.getCameraHeight() * 3 / 2;
                camera.addCallbackBuffer(new byte[length]);
            }
        
        
        
        
        
            camera.setPreviewCallbackWithBuffer(mPreviewCallback);
        
            setCamera(camera);
        
        
        
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("FaceInfoException", "########## initFaceCamera  Camera.open exception");
        
        }
        
    
    }
    
    @Override
    public void initFaceCamera(TextureView textureViewRGB, Camera.PreviewCallback previewCallbackRGB, TextureView textureViewNIR, Camera.PreviewCallback previewCallbackNIR) {
        // do nothing
    }
    
    @Override
    public void initFaceCamera(int cameraIdRGB, MagicCameraView magicCameraViewRGB, Camera.PreviewCallback previewCallbackRGB) {
        // do nothing
    
    }
    
    
    private final TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            Log.d("FaceLocalArea", "@@@@@@@@@@@@ onSurfaceTextureAvailable width = " + width);
            Log.d("FaceLocalArea", "@@@@@@@@@@@@ onSurfaceTextureAvailable height = " + height);
            setSurfaceTexture(surface);
        }
        
        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            Log.d("FaceLocalArea", "@@@@@@@@@@@@ onSurfaceTextureDestroyed");
            setSurfaceTexture(null);
            return false;
        }
        
        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        }
        
        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }
        
    };
    
    
    
    
    
    
    
    
    private Camera mCamera;
    private SurfaceTexture mSurfaceTexture = null;
    
    
    
    private void setCamera(Camera camera) {
        mCamera = camera;
        triggerCameraPreview();
    }
    
    private void setSurfaceTexture(SurfaceTexture surfaceTexture) {
        mSurfaceTexture = surfaceTexture;
        triggerCameraPreview();
    }
    
    private void triggerCameraPreview() {
        if (mCamera != null && mSurfaceTexture != null) {
            doCameraPreview();
        }
    }
    
    
    private void doCameraPreview() {
        try {
            mCamera.stopPreview();
            mCamera.setPreviewTexture(mSurfaceTexture);
            mCamera.startPreview();
            
            
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("FaceInfoConfig", "########## doCameraPreview setPreviewTexture exception");
        }
    }
    
    
    
    @Override
    public void releaseFaceCamera() {
    
        try {
            if (mCamera != null) {
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }
    
            mSurfaceTexture = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    
    
    }
    
    



}
