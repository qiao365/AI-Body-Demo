package com.jdjr.risk.face.local.demo.camera;

/**
 * Created by michael on 19-5-7.
 */

public class CameraSettings {
    
    
    public static final int TYPE_SYSTEM = 1;
    public static final int TYPE_JDY_K2002A_A4 = 2;
    public static final int TYPE_MAGIC = 3;
    
    
    private static int cameraType = TYPE_JDY_K2002A_A4;
    
    
    public static int getCameraType() {
        return cameraType;
    }
    
    /**
     * 设置相机类型
     * @param cameraType
     */
    public static void setCameraType(int cameraType) {
        CameraSettings.cameraType = cameraType;
    }
    
    
    
    
    
    
    
    public static final int RESOLUTION_640_480 = 1;
    public static final int RESOLUTION_1280_720 = 2;
    
    public static void setCameraResolution(int resolution) {
        if (resolution == RESOLUTION_640_480) {
            cameraWidth = 640;
            cameraHeight = 480;
        }
        else if (resolution == RESOLUTION_1280_720) {
            cameraWidth = 1280;
            cameraHeight = 720;
        }
        else {
            // do nothing
        }
    }
    
    private static int cameraWidth = 640;
    private static int cameraHeight = 480;
    
    public static int getCameraWidth() {
        return cameraWidth;
    }
    
    public static int getCameraHeight() {
        return cameraHeight;
    }
    
    public static void setCameraWidth(int cameraWidth) {
        CameraSettings.cameraWidth = cameraWidth;
    }
    
    public static void setCameraHeight(int cameraHeight) {
        CameraSettings.cameraHeight = cameraHeight;
    }
    
    
    
    
    
    
    
    
    /**
     * 逆时针0度
     */
    public static final int ROTATION_0 = 0;
    /**
     * 逆时针90度
     */
    public static final int ROTATION_90 = 90;
    /**
     * 逆时针180度
     */
    public static final int ROTATION_180 = 180;
    /**
     * 逆时针270度
     */
    public static final int ROTATION_270 = 270;
    
    
    
    
    private static int cameraImageRotation = ROTATION_0;
    
    public static int getCameraImageRotation() {
        return cameraImageRotation;
    }
    
    /**
     * 输入相机输出图像预览角度
     * @param rotation
     */
    public static void setCameraImageRotation(int rotation) {
        cameraImageRotation = rotation;
    }
    
    
    
    
    
    
    
    private static int cameraDisplayRotation = ROTATION_0;
    
    public static int getCameraDisplayRotation() {
        return cameraDisplayRotation;
    }
    
    /**
     * 输入相机预览图像旋转角度
     * @param rotation
     */
    public static void setCameraDisplayRotation(int rotation) {
        cameraDisplayRotation = rotation;
    }
    
    
    
    
    
    
    
    
    public static boolean MIRROR_RGB = true;
    public static boolean MIRROR_NIR = true;
    
    
    
    public static boolean IMAGE_MIRROR_RGB = true;
    
}
