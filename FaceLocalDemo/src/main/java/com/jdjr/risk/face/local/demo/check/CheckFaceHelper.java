package com.jdjr.risk.face.local.demo.check;

import android.graphics.Rect;
import android.util.Log;

import com.jdjr.risk.face.local.demo.verify.FaceBoxUtil;
import com.jdjr.risk.face.local.detect.DetectFaceInfo;

/**
 * Created by michael on 19-4-29.
 */

public class CheckFaceHelper {
    private static final int MIN_AREA = 30000;
    
    public static boolean checkFaceArea(DetectFaceInfo faceProperty) {
        final Rect cameraRect = faceProperty.getFaceRect();
        final Rect previewRect = FaceBoxUtil.getPreviewBox(cameraRect);
        final int width = Math.abs(previewRect.right - previewRect.left);
        final int height = Math.abs(previewRect.bottom - previewRect.top);
        final int area = width * height;
//        Log.d("FaceLocalCheck", "============ checkFaceArea area = " + area);
        
        
        if (area < MIN_AREA) {
            return false;
        } else {
            return true;
        
        }
    }
    
    public static boolean checkFacePosition(DetectFaceInfo faceProperty) {
        final Rect cameraRect = faceProperty.getFaceRect();
        final Rect previewRect = FaceBoxUtil.getPreviewBox(cameraRect);
        
        if (previewRect.left < 10) {
            return false;
        }
        
        if (previewRect.right < 10) {
            return false;
        }
        
        if (previewRect.top < 5) {
            return false;
        }
        
        if (previewRect.bottom < 5) {
            return false;
        }
        
        return true;
        
    
    }
    
    
    private static final int MAX_YAW = 17;
    
    public static int checkFaceYaw(DetectFaceInfo faceProperty) {
        final float yaw = faceProperty.getYaw();
//        Log.d("FaceLocalCheck", "============ checkFaceYaw yaw = " + yaw);
        
        if (Math.abs(yaw) > MAX_YAW) {
            if (yaw < 0) {
                return 2;
            } else {
                return 1;
            }
        } else {
            return 0;
        }
    }
    
    
    
    private static final int MAX_PITCH = 15;
    
    public static int checkFacePitch(DetectFaceInfo faceProperty) {
        final float pitch = faceProperty.getPitch();
//        Log.d("FaceLocalCheck", "============ checkFaceYaw pitch = " + pitch);
        
        if (Math.abs(pitch) > MAX_PITCH) {
            if (pitch < 0) {
                return 2;
            } else {
                return 1;
            }
        } else {
            return 0;
        }
    }
    
    
    
}
