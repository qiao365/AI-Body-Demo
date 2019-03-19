package com.camareui.fragment;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import com.camareui.constant.Constant;
import com.camareui.view.AutoFaceFitTextureView;
import com.camareui.base.R;

import java.io.IOException;

import static android.graphics.ImageFormat.NV21;

@SuppressLint("ValidFragment")
public class LegacyCameraConnectionFragment extends Fragment {
    private Camera camera;
    private Camera.PreviewCallback imageListener;
    private Size desiredSize;

    /**
     * The layout identifier to inflate for this Fragment.
     */
    private int layout;

    public LegacyCameraConnectionFragment(
            final Camera.PreviewCallback imageListener, final int layout, final Size desiredSize) {
        this.imageListener = imageListener;
        this.layout = layout;
        this.desiredSize = desiredSize;
    }


    /**
     * Conversion from screen rotation to JPEG orientation.
     */
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    /**
     * {@link android.view.TextureView.SurfaceTextureListener} handles several lifecycle events on a
     * {@link TextureView}.
     */
    private final TextureView.SurfaceTextureListener surfaceTextureListener =
            new TextureView.SurfaceTextureListener() {
                @Override
                public void onSurfaceTextureAvailable(
                        final SurfaceTexture texture, final int width, final int height) {

                    int index = getCameraId();
                    camera = Camera.open(index);
                    try {
                        Camera.Parameters parameters = camera.getParameters();
                        parameters.setPreviewSize(desiredSize.getWidth(), desiredSize.getHeight());
                        camera.setDisplayOrientation(0);
                        camera.setParameters(parameters);
                        camera.setPreviewTexture(texture);
                    } catch (IOException exception) {
                        camera.release();
                    }

                    camera.setPreviewCallback(imageListener);
                    Camera.Size s = camera.getParameters().getPreviewSize();
                    textureView.setAspectRatio(s.height, s.width);
                    camera.startPreview();
                }

                @Override
                public void onSurfaceTextureSizeChanged(
                        final SurfaceTexture texture, final int width, final int height) {
                }

                @Override
                public boolean onSurfaceTextureDestroyed(final SurfaceTexture texture) {
                    return true;
                }

                @Override
                public void onSurfaceTextureUpdated(final SurfaceTexture texture) {
                }
            };

    /**
     * An {@link AutoFaceFitTextureView} for camera preview.
     */
    private AutoFaceFitTextureView textureView;

    public AutoFaceFitTextureView getTextureView(){
        return textureView;
    }

    /**
     * An additional thread for running tasks that shouldn't block the UI.
     */
    @Override
    public View onCreateView(
            final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(layout, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        textureView = view.findViewById(R.id.texture);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (textureView.isAvailable() && camera != null) {
            camera.startPreview();
        } else {
            textureView.setSurfaceTextureListener(surfaceTextureListener);
        }
    }

    @Override
    public void onPause() {
        stopCamera();
        super.onPause();
    }

    protected void stopCamera() {
        if (camera != null) {
            camera.stopPreview();
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        }
    }

    private int getCameraId() {
        CameraInfo ci = new CameraInfo();
        int size = Camera.getNumberOfCameras();
        if (size == 1) {
            return 0;
        } else if (size >= 2) {
            for (int i = 0; i < size; i++) {
                Camera.getCameraInfo(i, ci);
                if (ci.facing == CameraInfo.CAMERA_FACING_FRONT)
                    return i;
            }
        }
        return -1; // 没有摄像头

    }

    /**
     * 获取屏幕宽度和高度，单位为px
     *
     * @param context
     * @return
     */
    private static Point getScreenMetrics(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;
        return new Point(w_screen, h_screen);
    }
}
