package com.jdjr.risk.face.local.demo.verify;

import android.graphics.Rect;

import com.jdjr.risk.face.local.demo.camera.CameraSettings;

/**
 * Created by michael on 19-3-15.
 */

public class FaceBoxUtil {
    public static Rect getPreviewBox(Rect cameraBox) {
        
        final int cameraImageWidth = CameraSettings.getCameraWidth();
        final int cameraImageHeight = CameraSettings.getCameraHeight();
        
        final float scaleX = ((float) DisplaySize.WIDTH) / cameraImageWidth;
        final float scaleY = ((float) DisplaySize.HEIGHT) / cameraImageHeight;
        
        int scaleLeft = (int) (cameraBox.left * scaleX);
        int scaleTop = (int) (cameraBox.top * scaleY);
        int scaleRight = (int) (cameraBox.right * scaleX);
        int scaleBottom = (int) (cameraBox.bottom * scaleY);
        
        int finalLeft = scaleLeft;
        int finalRight = scaleRight;
        
        if (!CameraSettings.MIRROR_RGB) {
            finalLeft = cameraImageWidth - scaleRight;
            finalRight = cameraImageWidth - scaleLeft;
        }
        
        if (!CameraSettings.IMAGE_MIRROR_RGB) {
            finalLeft = cameraImageWidth - scaleRight;
            finalRight = cameraImageWidth - scaleLeft;
        }
        
        int finalTop = scaleTop;
        int finalBottom = scaleBottom;
//        if (!CameraDisplayMirror.MIRROR_PORTRAIT) {
//            finalTop = cameraImageHeight - scaleBottom;
//            finalBottom = cameraImageHeight - scaleTop;
//        }



//        Log.d("FaceLocalScale", "@@@@@@@@@@@@@@@@@@@@@@@@ scaleLeft = " + scaleLeft);
//        Log.d("FaceLocalScale", "@@@@@@@@@@@@@@@@@@@@@@@@ scaleTop = " + scaleTop);
//        Log.d("FaceLocalScale", "@@@@@@@@@@@@@@@@@@@@@@@@ scaleRight = " + scaleRight);
//        Log.d("FaceLocalScale", "@@@@@@@@@@@@@@@@@@@@@@@@ scaleBottom = " + scaleBottom);


//        if (mRGBScaleY == -1) {
//            scaleTop = DisplaySize.HEIGHT - scaleTop;
//            scaleBottom = DisplaySize.HEIGHT - scaleBottom;
//        }
        
        
        
        final Rect previewBox = new Rect(finalLeft, finalTop, finalRight, finalBottom);
        
        return previewBox;
    }




}
