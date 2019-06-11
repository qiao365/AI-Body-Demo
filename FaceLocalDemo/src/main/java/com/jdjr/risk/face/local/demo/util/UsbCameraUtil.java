package com.jdjr.risk.face.local.demo.util;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by michael on 19-3-7.
 */

public class UsbCameraUtil {
    
    public static void showAllUsb(Context applicationContext) {
        final UsbManager usbManager = (UsbManager) applicationContext.getSystemService(Context.USB_SERVICE);
        final HashMap<String, UsbDevice> deviceMap = usbManager.getDeviceList();
        
        for (Map.Entry<String, UsbDevice> entry : deviceMap.entrySet()) {
            final UsbDevice device = entry.getValue();
            Log.d("FaceLocalUVC", "................. showAllUsb getDeviceList device = " + device);
            
            
        }
        
    }
    
    
    
    
    
}
