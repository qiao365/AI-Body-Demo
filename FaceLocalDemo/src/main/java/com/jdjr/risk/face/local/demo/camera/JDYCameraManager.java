package com.jdjr.risk.face.local.demo.camera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;
import android.view.TextureView;

import com.seu.magicfilter.widget.MagicCameraView;

import java.io.IOException;

/**
 * Created by michael on 18-12-29.
 */

public class JDYCameraManager implements IFaceCameraManager {
    private JDYCameraManager() {
        // do nothing
    }
    
    private static final JDYCameraManager INSTANCE = new JDYCameraManager();
    
    public static JDYCameraManager getInstance() {
        return INSTANCE;
    }
    
    
    
    
    

    
    // private boolean saveNIR = false;
    
    
    @Override
    public void initFaceCamera(TextureView textureViewRGB, Camera.PreviewCallback previewCallbackRGB) {
        // do nothing
    }
    
    @Override
    public void initFaceCamera(TextureView previewTextureView, Camera.PreviewCallback mPreviewCallback, TextureView nirView, Camera.PreviewCallback nirCallback) {
        
        previewTextureView.setSurfaceTextureListener(surfaceTextureListener);
        nirView.setSurfaceTextureListener(mNIRListener);
    
        final int rgb = CameraType.getRGB();
        final int nir = CameraType.getNIR();
    
        Log.d("FaceLocalSystemRGBNIR", "====================== initFaceCamera camera rgb = " + rgb);
        Log.d("FaceLocalSystemRGBNIR", "====================== initFaceCamera camera nir = " + nir);
        
        int cameraType = rgb;
        
        try {
            final int cameras = Camera.getNumberOfCameras();
            Log.d("FaceLocalSystemRGBNIR", "====================== cameras = " + cameras);
            // Log.d("FaceLocalSystemRGBNIR", "###### liveFaceConfig.cameraOrient = " + FaceSettings.cameraOrient);
            
            if (cameras < 2) {
//                if (noCameraRUnnable != null) {
//                    noCameraRUnnable.run();
//                }
                
                // TODO check camera count
                // return;
            }
            
            
            
            
            
            
            
            
            // open RGB camera
            cameraType = rgb;
            Log.d("FaceLocalSystemRGBNIR", "..................... initFaceCamera open rgb = " + cameraType);
            final Camera camera = Camera.open(cameraType);
        
            // camera.setCameraDisplayRotation(FaceSettings.cameraOrient);
            
            // TODO optimize
            camera.setDisplayOrientation(CameraSettings.getCameraDisplayRotation());
            
        
            Camera.Parameters params = camera.getParameters();
            
            params.setPreviewSize(CameraSettings.getCameraWidth(), CameraSettings.getCameraHeight());
            camera.setParameters(params);
        
            for (int i = 0; i < 3; i++) {
                int length = CameraSettings.getCameraWidth() * CameraSettings.getCameraHeight() * 3 / 2;
                camera.addCallbackBuffer(new byte[length]);
            }
        
            camera.setPreviewCallbackWithBuffer(mPreviewCallback);
        
            setCamera(camera);
//            FaceLogHelper.persistentCameraOpenComplete(cameraType);
            
            
            
            
            
            
            
            
            
            // open NIR camera
            cameraType = nir;
            Log.d("FaceLocalSystemRGBNIR", "..................... initFaceCamera open nir = " + cameraType);
            final Camera nirCamera = Camera.open(cameraType);
            
            // TODO remove
            nirCamera.setDisplayOrientation(CameraSettings.getCameraDisplayRotation());
            
            // nirCamera.setCameraDisplayRotation(FaceSettings.cameraOrient);
            
            final Camera.Parameters nirParams = nirCamera.getParameters();
            
            
            nirParams.setPreviewSize(CameraSettings.getCameraWidth(),  CameraSettings.getCameraHeight());
            nirCamera.setParameters(nirParams);
    
            for (int i = 0; i < 3; i++) {
                int length = CameraSettings.getCameraWidth() * CameraSettings.getCameraHeight() * 3 / 2;
                nirCamera.addCallbackBuffer(new byte[length]);
            }
    
    
            nirCamera.setPreviewCallbackWithBuffer(nirCallback);
            
            setNIRCamera(nirCamera);
//            FaceLogHelper.persistentCameraOpenComplete(cameraType);
    
    
    
    
    
    
    
    
    
        } catch (Exception e) {
            e.printStackTrace();
            final String detail = e.toString();
            
            // System.err: java.lang.RuntimeException: getParameters failed (empty parameters)
            Log.d("FaceLocalSystemRGBNIR", "====================== initFaceCamera  Camera.open exception detail = " + detail);
            Log.d("FaceLocalSystemRGBNIR", "====================== initFaceCamera  Camera.open exception cameraType = " + (cameraType == rgb ? "RGB" : "NIR"));
            
            
            final String typeLabel = (cameraType == rgb ? "RGB" : "NIR");
//            FaceLogHelper.persistentCameraOpenExcetion(typeLabel, detail);
    
    
//            if (noCameraRUnnable != null) {
//                noCameraRUnnable.run();
//            }
            
        }
        
    
    }
    
    @Override
    public void initFaceCamera(int cameraIdRGB, MagicCameraView magicCameraViewRGB, Camera.PreviewCallback previewCallbackRGB) {
    
    }
    
    
    private final TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            setSurfaceTexture(surface);
            
            
            
            
        }
        
        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
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
            mCamera.setPreviewTexture(mSurfaceTexture);
            mCamera.startPreview();
            
            
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("FaceLocalSystemRGBNIR", "########## doCameraPreview RGB exception");
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    private final TextureView.SurfaceTextureListener mNIRListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            setNIRPreview(surface);
        }
        
        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            setNIRPreview(null);
            return false;
        }
        
        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        }
        
        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }
        
    };
    
    
    
    
    
    
    
    
    
    public Camera mNIRCamera;
    private SurfaceTexture mNIRPreview;
    
    
    private void setNIRCamera(Camera nirCamera) {
        mNIRCamera = nirCamera;
        triggerNIRPreview();
    }
    
    private void setNIRPreview(SurfaceTexture nirPreview) {
        mNIRPreview = nirPreview;
        triggerNIRPreview();
    }
    
    private void triggerNIRPreview() {
        if (mNIRCamera != null && mNIRPreview != null) {
            doNIRPreview();
        }
        
        
    }
    
    
    private void doNIRPreview() {
    
        try {
            mNIRCamera.setPreviewTexture(mNIRPreview);
            mNIRCamera.startPreview();
        
        
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("FaceLocalSystemRGBNIR", "########## doCameraPreview NIR exception");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        try {
            if (mNIRCamera != null) {
                mNIRCamera.setPreviewCallback(null);
                mNIRCamera.stopPreview();
                mNIRCamera.release();
                mNIRCamera = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    
    
    
    
    }
    
    



}
