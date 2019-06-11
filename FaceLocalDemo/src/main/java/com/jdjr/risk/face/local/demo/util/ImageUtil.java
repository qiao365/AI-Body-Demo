package com.jdjr.risk.face.local.demo.util;

/**
 * Created by michael on 19-4-25.
 */

public class ImageUtil {
    
    public static boolean isImage(String name) {
        
        if (name.endsWith(".jpg")) {
            return true;
        }
        
        if (name.endsWith(".JPG")) {
            return true;
        }
        
        if (name.endsWith(".jpeg")) {
            return true;
        }
        
        if (name.endsWith(".JPEG")) {
            return true;
        }
        
        if (name.endsWith(".png")) {
            return true;
        }
        if (name.endsWith(".PNG")) {
            return true;
        }
        
        if (name.endsWith(".bmp")) {
            return true;
        }
        
        if (name.endsWith(".BMP")) {
            return true;
        }
        
        
        return false;
        
    }
    
}
