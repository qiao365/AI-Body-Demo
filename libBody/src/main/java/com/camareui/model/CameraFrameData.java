package com.camareui.model;

public class CameraFrameData {

    private byte[] bytes;
    private int width;
    private int heigh;
    public CameraFrameData(byte[] bytes, int width, int heigh){
        this.bytes = bytes;
        this.width = width;
        this.heigh = heigh;
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

    public int getWidth() {
        return width;
    }

    public int getHeigh() {
        return heigh;
    }
}
