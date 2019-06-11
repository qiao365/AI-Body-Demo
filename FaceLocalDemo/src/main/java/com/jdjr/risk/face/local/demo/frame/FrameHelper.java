package com.jdjr.risk.face.local.demo.frame;

import com.jdjr.risk.face.local.demo.camera.CameraSettings;
import com.jdjr.risk.face.local.frame.NV21Util;

/**
 * Created by michael on 19-5-6.
 */

public class FrameHelper {
    public static byte[] getFrameRotate(byte[] frame, int width, int height) {
        
        final int rotation = CameraSettings.getCameraImageRotation();
        
        final byte[] frameRotate = FrameHelper.rotateFrame(rotation, frame, width, height);
        if (rotation == 90 || rotation == 180) {
            CameraSettings.setCameraWidth(height);
            CameraSettings.setCameraHeight(width);
        }
        
        
        
        return frameRotate;
        
        
    }
    
    
    private static byte[] rotateFrame(int rotation, byte[] frame, int width, int height) {
        
        
        byte[] frameRotate = frame;
        
        if (rotation == 0) {
            return frame;
        }
        else if (rotation == 90) {
            frameRotate = NV21Util.NV21_rotate_to_90(frame, width, height);
        }
        else if (rotation == 180) {
            frameRotate = NV21Util.NV21_rotate_to_180(frame, width, height);
        }
        else if (rotation == 270) {
            frameRotate = NV21Util.NV21_rotate_to_270(frame, width, height);
        }
        else {
            // do nothing
        }
        
        return frameRotate;
        
        
    }
    


}
