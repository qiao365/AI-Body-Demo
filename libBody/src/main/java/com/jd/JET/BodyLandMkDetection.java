package com.jd.JET;

public class BodyLandMkDetection {
    private static final String TAG = "JD JET";

    static {
        System.loadLibrary("JETdetectBodyLandMark");
    }

    public static native boolean init(byte[] param, byte[] bin);

    public static native boolean initWithPath(String paramPath, String binPath);

    public static native boolean bodyDetectLandMarkRGBA(int h, int w, int[] RGBAFrameData, float[] ResultData);

    public static native boolean bodyDetectLandMarkRGB(int h, int w, int[] RGBFrameData, float[] ResultData);


}
