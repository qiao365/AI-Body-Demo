package com.jdjr.risk.face.local.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.jdjr.risk.face.local.detect.DetectFaceInfo;

import java.util.List;

/**
 * Created by michael on 19-1-11.
 */

public class FaceBoxesView extends View {
    
    
    public FaceBoxesView(Context context) {
        super(context);
        initPaint();
    }
    
    public FaceBoxesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }
    
    private Paint mPaint;
    private Paint mPaintFill;
    
    private void initPaint() {
        Log.d("FaceLocalBox", "@@@@@@@@@ FaceBoxesView initPaint");
        mPaint = new Paint();
        mPaint.setAntiAlias(false);
        mPaint.setColor(Color.GREEN);
        mPaint.setAlpha(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setTextSize(25);
    
        mPaintFill = new Paint();
        
        mPaintFill.setColor(Color.WHITE);
        mPaintFill.setStyle(Paint.Style.FILL);
    
//        mPaintFill.setColor(Color.WHITE);
//        mPaintFill.setStyle(Paint.Style.STROKE);
//        mPaintFill.setStrokeWidth(5);
        
    }
    
    
    
    
    private List<DetectFaceInfo> mFaces;
    
    public void updateFaceBoxes(List<DetectFaceInfo> faces) {
//        Log.d("FaceLocalBox", "@@@@@@ FaceBoxesView updateFaceBoxes");
        mFaces = faces;
        invalidate();
    }
    
    public void clearFaces() {
//        Log.d("FaceLocalBox", "@@@@@@ FaceBoxesView clearFaces");
        mFaces = null;
        invalidate();
    }
    
    
    
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    
        if (mFaces == null) {
            return;
        }
        
        for (DetectFaceInfo face : mFaces) {
            
            // final Rect previewBox = getPreviewBox(face.getFaceRect());
//            Log.d("FaceLocalBox", "@@@@@@@@@@@ canvas.drawRect rect = " + previewBox);
            
            
            mPaint.setColor(Color.GREEN);
            final Rect previewBox = face.getFaceRect();
            canvas.drawRect(previewBox, mPaint);
            
    
            
            
            
            mPaint.setTextSize(23);
            mPaint.setColor(Color.GREEN);
            final String faceIdText = face.getFaceIdText();
            canvas.drawText(faceIdText , previewBox.left, previewBox.top - 5, mPaint);
            
            
//            mPaint.setTextSize(23);
//            mPaint.setColor(Color.YELLOW);
//            final String yawText = face.getYawText();
//            canvas.drawText(yawText , previewBox.left, previewBox.top + 15, mPaint);
//            final String pitchText = face.getPitchText();
//            canvas.drawText(pitchText , previewBox.left, previewBox.top + 35, mPaint);
    
            
            
            
            
            
            for (Point landmark : face.getLandmarks()) {
                canvas.drawCircle(landmark.x, landmark.y, 5, mPaintFill);
            }
            
            
            
            
            
            
            mPaint.setTextSize(23);
            mPaint.setColor(Color.RED);
            final String userInfo = face.getUserText();
            canvas.drawText(userInfo, previewBox.left, (previewBox.top + previewBox.bottom) / 2, mPaint);
            
            
            
            mPaint.setTextSize(23);
            mPaint.setColor(Color.WHITE);
            final String humanProperty = face.getPropertyText();
            canvas.drawText(humanProperty, previewBox.left, previewBox.bottom - 9, mPaint);
            
            mPaint.setTextSize(33);
            mPaint.setColor(Color.WHITE);
            final String emotion = face.getEmotionText();
            canvas.drawText(emotion, previewBox.left, previewBox.bottom - 57, mPaint);
        
        
        
        
            
        }
        
    }
    
    
    
//    public static Rect getPreviewBox(Rect cameraBox) {
//
//        final float scaleX = ((float) PreviewSize.WIDTH) / CameraSize.WIDTH;
//        final float scaleY = ((float) PreviewSize.HEIGHT) / CameraSize.HEIGHT;
//
//        final int scaleLeft = (int) (cameraBox.left * scaleX);
//        final int scaleTop = (int) (cameraBox.top * scaleY);
//        final int scaleRight = (int) (cameraBox.right * scaleX);
//        final int scaleBottom = (int) (cameraBox.bottom * scaleY);
//
//        final Rect previewBox = new Rect(scaleLeft, scaleTop, scaleRight, scaleBottom);
//
//        return previewBox;
//    }
    
    
    
}
