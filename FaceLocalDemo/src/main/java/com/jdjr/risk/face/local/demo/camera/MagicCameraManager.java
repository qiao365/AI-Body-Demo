package com.jdjr.risk.face.local.demo.camera;

import android.hardware.Camera;
import android.util.Log;
import android.util.Size;
import android.view.TextureView;

import com.seu.magicfilter.camera.CameraEngine;
import com.seu.magicfilter.filter.helper.MagicFilterType;
import com.seu.magicfilter.utils.MagicParams;
import com.seu.magicfilter.widget.MagicCameraView;

public class MagicCameraManager implements IFaceCameraManager {
    
    private MagicCameraManager() {
        // do nothing
    }
    
    private static final MagicCameraManager INSTANCE = new MagicCameraManager();
    
    public static MagicCameraManager getInstance() {
        return INSTANCE;
    }
    
    
    
    
    
    
    
    
    
    
    @Override
    public void initFaceCamera(TextureView textureViewRGB, Camera.PreviewCallback previewCallbackRGB) {
        // do nothing
    
    }
    
    @Override
    public void initFaceCamera(TextureView textureViewRGB, Camera.PreviewCallback previewCallbackRGB, TextureView textureViewNIR, Camera.PreviewCallback previewCallbackNIR) {
        // do nothing
    
    
    }
    
    @Override
    public void initFaceCamera(int cameraIdRGB, MagicCameraView magicCameraViewRGB, Camera.PreviewCallback previewCallbackRGB) {
        MagicParams.context = magicCameraViewRGB.getContext();
        
        MagicParams.cameraId = cameraIdRGB;
        MagicParams.cameraPreviewSize = new Size(640, 480);
        MagicParams.cameraPictureSize = new Size(640, 480);
        
        MagicParams.magicBaseView = magicCameraViewRGB;
        MagicParams.previewCallback = previewCallbackRGB;
    }
    
    
    
    
    public void releaseFaceCamera() {
        MagicParams.context = null;
        
        MagicParams.cameraId = 0;
        MagicParams.cameraPreviewSize = new Size(640, 480);
        MagicParams.cameraPictureSize = new Size(640, 480);
        
        MagicParams.magicBaseView = null;
        MagicParams.previewCallback = null;
    
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void switchCamera(){
        CameraEngine.switchCamera();
    }
    
    private MagicFilterType magicFilterType = MagicFilterType.NONE;
    
    public void setFilter(MagicFilterType type){
        // MagicParams.magicBaseView.setFilter(type);
        magicFilterType = type;
        ((MagicCameraView) MagicParams.magicBaseView).setFilter(type);
    }
    
    
    public void setBeautyLevel(int level){
        if(MagicParams.magicBaseView instanceof MagicCameraView && MagicParams.beautyLevel != level) {
            MagicParams.beautyLevel = level;
            ((MagicCameraView) MagicParams.magicBaseView).onBeautyLevelChanged();
        }
        
        
        setFilter(magicFilterType);
        
    }
    
    
    
    public void takePicture(String picturePath, byte[] frameRGBCopy) {
        Log.d("FaceLocalMagic", "=========== picturePath = " + picturePath);
        
        final MagicCameraView magicCameraView = (MagicCameraView) MagicParams.magicBaseView;
        magicCameraView.takePictureFrame(picturePath, frameRGBCopy);
    
    }

    
    
    
    

    
    
    
    
    
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
