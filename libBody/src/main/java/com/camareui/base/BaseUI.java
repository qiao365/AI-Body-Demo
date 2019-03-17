package com.camareui.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Size;
import android.widget.ImageView;
import android.widget.TextView;

import com.camareui.constant.Constant;
import com.camareui.fragment.LegacyCameraConnectionFragment;
import com.camareui.model.CameraFrameData;
import com.camareui.utils.YuvToRGB;
import com.camareui.view.OverlayView;
import com.camareui.utils.ImageUtils;
import com.jd.JET.BodyLandMkDetection;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;


public class BaseUI implements Camera.PreviewCallback {

    private final String TAG = BaseUI.this.getClass().getSimpleName();
    private Bitmap textureCopyBitmap;
    // camera自定义帧率
    public static int frameCountMax = 1;
    // 读取图片数据间隔时间 ms
    public static int intervalTime = 1;
    private int[] rgbBytes = null;
    public int previewWidth = 0;
    public int previewHeight = 0;
    public CameraFrameData cameraFrameData;
    private long previousTime = 0;
    private final Timer mTimer = new Timer();
    private OverlayView overlay;
    private Bitmap rgbFrameBitmap, croppedBitmap;

    public interface DataSettingAndListener {
        Constant.AISDKTYPE getSdkAiType();

        boolean getPreViewStatus();
    }

    private DataSettingAndListener mDataSettingAndListener = null;
    private Activity activity;
    private boolean canHandler = true;//是否循环
    private int fragmentId;
    private TextView width, height, xiaolv, textZhen, time_textZhen;

    private int getLayoutId() {
        return R.layout.camera_base_fragment_connect;
    }

    @SuppressLint("HandlerLeak")
    private Handler hand = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            switch (msg.what) {
                case 0:
                    startFaceRecognizationTask();
                    break;
            }
        }
    };

    private void startFaceRecognizationTask() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                if (currentTime - previousTime >= intervalTime) {
                    previousTime = currentTime;
                    final long a = System.currentTimeMillis();
                    execTask();
                    final long b = System.currentTimeMillis();
                    Log.i(TAG, "run:run: " + (b - a));
                    Log.i(TAG, "run:run: " + previewWidth + "---" + previewHeight);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (width != null) width.setText(previewHeight + "");
                            if (height != null) height.setText(previewWidth + "");
                            if (xiaolv != null) xiaolv.setText((b - a) + "");
                            if (textZhen != null) textZhen.setText(frameCountMax + "");
                            if (time_textZhen != null) time_textZhen.setText(intervalTime + "");
                        }
                    });
                }
                if (canHandler) hand.sendEmptyMessage(0);
            }
        };
        mTimer.schedule(task, 0);
    }

    //可设置预览
    public BaseUI(Activity activity, int fragmentId) {
        this.activity = activity;
        this.fragmentId = fragmentId;
        setFragment();
    }

    //设置是否执行 handler循环
    public void stopTimerCameraFrameData() {
        this.canHandler = false;
    }

    private void startTimerCameraFrameData() {
        this.canHandler = true;
        hand.sendEmptyMessage(0);
    }

    public void setDataSettingAndListener(DataSettingAndListener mmDataSettingAndListener) {
        this.mDataSettingAndListener = mmDataSettingAndListener;
        initJetDetect();
        // 开启定时器去进行数据处理
        startTimerCameraFrameData();
    }

    private void initJetDetect() {
        BodyLandMkDetection.initWithPath(Constant.JET_BIN_DIR + File.separator + Constant
                .JET_INPUT256_C64_PARAM_BIN, Constant.JET_BIN_DIR + File.separator + Constant
                .JET_INPUT256_C64_BIN);
    }


    public void setTextViews(TextView width, TextView height, TextView xiaolv, TextView textZhen, TextView time_textZhen) {
        this.width = width;
        this.height = height;
        this.xiaolv = xiaolv;
        this.textZhen = textZhen;
        this.time_textZhen = time_textZhen;
    }


    protected void jetBodyRecognition() {
        rgbFrameBitmap = Bitmap.createBitmap(previewWidth, previewHeight, Bitmap.Config.ARGB_8888);
        croppedBitmap = Bitmap.createBitmap(previewWidth, previewHeight, Bitmap.Config.ARGB_8888);
        long startTime = System.currentTimeMillis();
        rgbFrameBitmap.setPixels(getRgbBytes(), 0, previewWidth, 0, 0, previewWidth, previewHeight);
        long endTime = System.currentTimeMillis();
        Log.i(TAG, "jet检测，bitmap.setPixels耗时: " + (endTime - startTime));
        Matrix frameToCropMatrix = ImageUtils.getTransformationMatrix(
                previewWidth, previewHeight,
                previewHeight, previewWidth,
                270, false, false);
        final Canvas croppedCanvas = new Canvas(croppedBitmap);
        croppedCanvas.drawBitmap(rgbFrameBitmap, frameToCropMatrix, new Paint());

        //关闭预览
        if (mDataSettingAndListener != null && !mDataSettingAndListener.getPreViewStatus()) {
            return;
        }
        endTime = System.currentTimeMillis();
        Log.i(TAG, "jet检测，获取bitmap耗时: " + (endTime - startTime));
        //根据data保存的数据画框

        int width = croppedBitmap.getWidth();
        int height = croppedBitmap.getHeight();

//        int[] image_input = new int[width * height];
//        croppedBitmap.getPixels(image_input, 0, width, 0, 0, width, height); bitmap方式可行，但是效率较慢，最好直接转换
        byte[] data = rotateYUV420Degree270(cameraFrameData.getBytes(), previewWidth, previewHeight);//前置摄像头 数据 旋转 270为正
//        int[] dataNormal = ImageUtils.convertYUV420_NV21toARGB8888(data, width, height);
        //        ImageUtils.ARGBtoRGBA(dataNormal);
        int[] dataNormal = YuvToRGB.NV21ToRGB(data, width, height);

        float[] testResultData = new float[14 * 3];

        BodyLandMkDetection.bodyDetectLandMarkRGB(height, width, dataNormal, testResultData);

        Paint p = new Paint();
        p.setColor(Color.RED);
        p.setStrokeWidth(4.0f);
        float xScale = width / 256f;
        float ySclae = height / 256f;
        for (int i = 0; i < 14; i++) {
            float xPos = testResultData[3 * i] * xScale;
            float yPos = testResultData[3 * i + 1] * ySclae;
            croppedCanvas.drawCircle(xPos, yPos, 2, p);
        }

        endTime = System.currentTimeMillis();
        Log.i(TAG, "jet检测，总耗时： " + (endTime - startTime));
        textureCopyBitmap = Bitmap.createBitmap(croppedBitmap);
        croppedBitmap.recycle();
        croppedBitmap = null;
        rgbFrameBitmap.recycle();
        rgbFrameBitmap = null;
        requestRender();
    }


    protected int[] getRgbBytes() {
        ImageUtils.convertYUV420SPToARGB8888(cameraFrameData.getBytes(), previewWidth, previewHeight, rgbBytes);
        return rgbBytes;
    }

    /**
     * Callback for android.hardware.Camera API
     */
    @Override
    public void onPreviewFrame(final byte[] bytes, final Camera camera) {
        Camera.Size previewSize = camera.getParameters().getPreviewSize();
        previewWidth = previewSize.width;
        previewHeight = previewSize.height;
        if (cameraFrameData == null) {
            cameraFrameData = new CameraFrameData(bytes, previewWidth, previewHeight);
        } else {
            cameraFrameData.setBytes(bytes);
            cameraFrameData.setWidth(previewWidth);
            cameraFrameData.setHeigh(previewHeight);
        }
    }

    protected void setFragment() {
        Size DESIRED_PREVIEW_SIZE = new Size(640, 480);
        Fragment fragment =
                new LegacyCameraConnectionFragment(this, getLayoutId(), DESIRED_PREVIEW_SIZE);
        activity.getFragmentManager()
                .beginTransaction()
                .replace(fragmentId, fragment)
                .commit();
    }

    //顺时针旋转270度
    private byte[] rotateYUV420Degree270(byte[] data, int imageWidth, int imageHeight) {
        byte[] yuv = new byte[imageWidth * imageHeight * 3 / 2];
        // Rotate the Y luma
        int i = 0;
        for (int x = imageWidth - 1; x >= 0; x--) {

            for (int y = 0; y < imageHeight; y++) {

                yuv[i] = data[y * imageWidth + x];
                i++;

            }
        }// Rotate the U and V color components  	i = imageWidth*imageHeight;

        for (int x = imageWidth - 1; x > 0; x = x - 2) {

            for (int y = 0; y < imageHeight / 2; y++) {
                yuv[i] = data[(imageWidth * imageHeight) + (y * imageWidth) + (x - 1)];
                i++;
                yuv[i] = data[(imageWidth * imageHeight) + (y * imageWidth) + x];
                i++;

            }

        }
        return yuv;
    }


    private byte[] rotateYUV420Degree90(byte[] data, int imageWidth, int imageHeight) {
        byte[] yuv = new byte[imageWidth * imageHeight * 3 / 2];
        // Rotate the Y luma
        int i = 0;
        for (int x = 0; x < imageWidth; x++) {
            for (int y = imageHeight - 1; y >= 0; y--) {
                yuv[i] = data[y * imageWidth + x];
                i++;
            }
        }
        // Rotate the U and V color components
        i = imageWidth * imageHeight * 3 / 2 - 1;
        for (int x = imageWidth - 1; x > 0; x = x - 2) {
            for (int y = 0; y < imageHeight / 2; y++) {
                yuv[i] = data[(imageWidth * imageHeight) + (y * imageWidth) + x];
                i--;
                yuv[i] = data[(imageWidth * imageHeight) + (y * imageWidth) + (x - 1)];
                i--;
            }
        }
        return yuv;
    }

    private void execTask() {
        if (cameraFrameData != null) {
            try {
                if (rgbBytes == null) {
                    rgbBytes = new int[previewWidth * previewHeight];
                }
            } catch (final Exception e) {
                return;
            }
            //人体识别
            jetBodyRecognition();
        }
    }

    private void renderDebug(final Canvas canvas) {
        Bitmap textureBitmap = textureCopyBitmap;
        if (textureBitmap == null) {
            return;
        }
        // 绘制具有红色矩形框的图像
        canvas.drawBitmap(textureBitmap, new Rect(0, 0, textureBitmap.getWidth(), textureBitmap.getHeight()),
                new Rect(0, 0, textureBitmap.getWidth(), textureBitmap.getHeight()), new Paint());
        textureBitmap.recycle();
        textureBitmap = null;
        textureCopyBitmap.recycle();
        textureCopyBitmap = null;
    }

    public void requestRender() {
        overlay = activity.findViewById(R.id.debug_overlay);
        addOverlayViewCallback(
                new OverlayView.DrawCallback() {
                    @Override
                    public void drawCallback(final Canvas canvas) {
                        renderDebug(canvas);
                    }
                });
        if (overlay != null) {
            overlay.postInvalidate();
        }
    }

    public void addOverlayViewCallback(final OverlayView.DrawCallback callback) {
        if (overlay != null) {
            overlay.addCallback(callback);
        }
    }
}
