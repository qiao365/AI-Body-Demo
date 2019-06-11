package com.jdjr.risk.face.local.demo.camera;

import android.hardware.Camera;
import android.view.TextureView;

import com.seu.magicfilter.widget.MagicCameraView;

/**
 * Created by michael on 19-5-16.
 */

public interface IFaceCameraManager {
    void initFaceCamera(TextureView textureViewRGB, Camera.PreviewCallback previewCallbackRGB);
    void initFaceCamera(TextureView textureViewRGB, Camera.PreviewCallback previewCallbackRGB, TextureView textureViewNIR, Camera.PreviewCallback previewCallbackNIR);
    
    void initFaceCamera(int cameraIdRGB, MagicCameraView magicCameraViewRGB, Camera.PreviewCallback previewCallbackRGB);
    
    
    
    void releaseFaceCamera();



}
