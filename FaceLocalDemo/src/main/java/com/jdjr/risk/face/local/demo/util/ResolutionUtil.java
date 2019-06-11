package com.jdjr.risk.face.local.demo.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by michael on 19-5-13.
 */

public class ResolutionUtil {
    public static  void printResolution(Context applicationContext) {
        final DisplayMetrics metrics = applicationContext.getResources().getDisplayMetrics();
        final int widthPixels = metrics.widthPixels;
        final int heightPixels = metrics.heightPixels;
        Log.d("FaceLocalResolution", ".......... widthPixels = " + widthPixels);
        Log.d("FaceLocalResolution", ".......... heightPixels = " + heightPixels);
    }



}
