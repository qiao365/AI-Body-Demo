package com.seu.magicfilter.utils;

import android.content.Context;
import android.hardware.Camera;
import android.os.Environment;
import android.util.Size;

import com.seu.magicfilter.widget.base.MagicBaseView;

/**
 * Created by why8222 on 2016/2/26.
 */
public class MagicParams {
    public static Context context;
    public static MagicBaseView magicBaseView;
    
    
    
    
    public static int cameraId = 0;
    public static Size cameraPreviewSize = new Size(640, 480);
    public static Size cameraPictureSize = new Size(640, 480);
    
    public static Camera.PreviewCallback previewCallback;
    
    
    
    
    
    

    public static String videoPath = Environment.getExternalStorageDirectory().getPath();
    public static String videoName = "MagicCamera_test.mp4";

    public static int beautyLevel = 5;

    public MagicParams() {

    }
}
