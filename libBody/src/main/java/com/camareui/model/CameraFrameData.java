package com.camareui.model;

import android.hardware.Camera;

public class CameraFrameData {

    private byte[] bytes;
    private int width;
    private int heigh;
    private Camera camera;
    public CameraFrameData(byte[] bytes, int width, int heigh,Camera camera){
        this.bytes = bytes;
        this.width = width;
        this.heigh = heigh;
        this.camera = camera;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeigh(int heigh) {
        this.heigh = heigh;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public Camera getCamera() {
        return camera;
    }

    public int getWidth() {
        return width;
    }

    public int getHeigh() {
        return heigh;
    }
}
