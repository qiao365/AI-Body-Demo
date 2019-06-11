package com.jdjr.risk.face.local.demo.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.TextureView;

import com.jdjr.risk.face.local.service.FaceLocalService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by michael on 19-1-29.
 *
 * 通过算法分别识别RGBCameraId NIRCameraId
 */

public class CameraIdHelper {
//    private CameraIdHelper() {
//        // do nothing
//    }
//
//    private static final CameraIdHelper INSTANCE = new CameraIdHelper();
//
//    public static CameraIdHelper getInstance() {
//        return INSTANCE;
//    }
    
    
//    /**
//     * 分辨黑白彩色相机类型
//     * @return 方法是否执行成功
//     */
//    public boolean initCameraTypeProxy() {
//        final boolean result = initCameraRGBNIRId();
//        return result;
//
//    }
    
    
    /**
     * 初始化RGBCameraId NIRCameraId
     *
     * @return cameraId 是否初始化成功
     */
    public static void initCameraRGBNIRIdAsync(final Context applicationContext, final CameraIdCallback cameraIdCallback) {
        Log.d("FaceLocalCamera", "@@@@@@@@@@@@@@@@@@@@ initCameraRGBNIRIdAsync");
        
        final Looper looper = Looper.myLooper();
    
        new Thread(new Runnable() {
            @Override
            public void run() {
            
                CameraIdHelper.initCameraRGBNIRId(applicationContext, new CameraIdCallback() {
                    @Override
                    public void onCameraIdResult() {
                        if (looper != null) {
                            new Handler(looper).post(new Runnable() {
                                @Override
                                public void run() {
                                    cameraIdCallback.onCameraIdResult();
                                }
                            });
                        } else {
                            cameraIdCallback.onCameraIdResult();
                        }
                    }
                });
                
            }
        }).start();
        
    
    
    }
    
    
    public interface CameraIdCallback {
        void onCameraIdResult();
    }
    
    
    
    private static void initCameraRGBNIRId(Context applicationContext, final CameraIdCallback cameraIdCallback) {
        Log.d("FaceLocalSystemRGBNIR", "=================== SystemCameraManager initCameraRGBNIRId");
    
        final int number = Camera.getNumberOfCameras();
        Log.d("FaceLocalSystemRGBNIR", "=================== initCameraRGBNIRId getNumberOfCameras = " + number);
        
        
        
        if (number < 2) {
            Log.d("FaceLocalSystemRGBNIR", "............ initCameraRGBNIRId SYSTEM_RGBNIR_JDY_K2002A_A4 system number of cameras less than 2 !!!!!!!!!!");
            return;
        }
        
        
        
        
        
        
        try {
            // open camera 0
    
            Log.d("FaceLocalSystemRGBNIR", "........... initCameraRGBNIRId Camera.open(0)");
            final Camera camera = Camera.open(0);
    
            camera.setDisplayOrientation(0);
    
            final Camera.Parameters params = camera.getParameters();
            Log.d("FaceLocalSystemRGBNIR", ".......... initCameraRGBNIRId params = " + params);

    
            params.setPreviewSize(CameraSettings.getCameraWidth(),  CameraSettings.getCameraHeight());
    
    
            camera.setParameters(params);
    
            final int length = CameraSettings.getCameraWidth() * CameraSettings.getCameraHeight() * 3 / 2;
            camera.addCallbackBuffer(new byte[length]);
    
    
            camera.setPreviewCallbackWithBuffer(new Camera.PreviewCallback() {
                                                    @Override
                                                    public void onPreviewFrame(byte[] data, Camera camera) {
                                                        Log.d("FaceLocalSystemRGBNIR", "............ initCameraRGBNIRId onPreviewFrame");
            
                                                        initCameraTypeImpl(data);
            
                                                        camera.addCallbackBuffer(data);
            
            
                                                        // release camera
                                                        camera.setPreviewCallback(null);
                                                        camera.stopPreview();
                                                        camera.release();
    
                                                        cameraIdCallback.onCameraIdResult();
                                                        
                                                    }
                                                }
            );
    
            final TextureView textureView = new TextureView(applicationContext);
    
            final SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();
    
            Log.d("FaceLocalSystemRGBNIR", "................. initCameraRGBNIRId camera.setPreviewTexture(surfaceTexture) surfaceTexture = " + surfaceTexture);
            camera.setPreviewTexture(surfaceTexture);
            Log.d("FaceLocalSystemRGBNIR", ".................initCameraRGBNIRId camera.startPreview()");
            camera.startPreview();
    
    
    
            // TODO open camera 1
            Log.d("FaceLocalSystemRGBNIR", "........... initCameraRGBNIRId Camera.open(1)");
            final Camera camera1 = Camera.open(1);
            camera1.release();
            
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
            final String detail = e.toString();
            Log.d("FaceLocalSystemRGBNIR", "====================== initCameraRGBNIRId exception e = " + detail);
            return;
            
        
        }
        
        
        
        
        return;
    
    }
    
    
    
    
    
    
    
    
    private static void initCameraTypeImpl(byte[] NIRFrame) {
        Log.d("FaceLocalSystemRGBNIR", "............ SystemCameraManager initCameraTypeImpl");
        
        final YuvImage yuvImage = new YuvImage(NIRFrame, ImageFormat.NV21, CameraSettings.getCameraWidth(), CameraSettings.getCameraHeight(), null);
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new Rect(0, 0, CameraSettings.getCameraWidth(), CameraSettings.getCameraHeight()), 100, outputStream);
        final byte[] bytes = outputStream.toByteArray();
        try {
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        
        
        boolean isAllGray = true;
        final int width = CameraSettings.getCameraWidth();
        final int height = CameraSettings.getCameraHeight();
        for (int i = 0; i < width; i+=40) {
            for (int j = 0; j < height; j+=40) {
                final int colorXY = bitmap.getPixel(i, j);
                isAllGray = isGray(colorXY);
                if (!isAllGray) {
                    isAllGray = false;
                    break;
                }
            }
        }
        
        
        Log.d("FaceLocalRGBNIR", "=============== isAllGray = " + isAllGray);
        
        
        if (isAllGray) {
            CameraType.setRGB(1);
            CameraType.setNIR(0);
        } else {
            CameraType.setRGB(0);
            CameraType.setNIR(1);
        }
    }
    
    
    
    
    
    private static boolean isGray(int color) {
    
        final String colorHex = Integer.toHexString(color);
//        Log.d("FaceLocalColor", ".............. colorHex = " + colorHex);
    
        final int red = Color.red(color);
        final int green = Color.green(color);
        final int blue = Color.blue(color);
    
//        Log.d("FaceLocalColor", ".............. red = " + red);
//        Log.d("FaceLocalColor", ".............. green = " + green);
//        Log.d("FaceLocalColor", ".............. blue = " + blue);
    
        if (red == green && green == blue) {
            return true;
        } else {
            return false;
        }
    }
    
    
    
    
    
    
    
    
    
    
    
}
