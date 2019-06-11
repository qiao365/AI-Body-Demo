package com.jdjr.risk.face.local.demo.camera;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by michael on 19-3-7.
 */

public class UsbCameraManager {
    
    
    public static boolean checkJDY_K2002A_A4(Context applicationContext) {
        final UsbManager usbManager = (UsbManager) applicationContext.getSystemService(Context.USB_SERVICE);
        final HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
        
        boolean hasRGB = false;
        boolean hasNIR = false;
    
    
        for (Map.Entry<String, UsbDevice> entry : deviceList.entrySet()) {
            final UsbDevice device = entry.getValue();
            if (DeviceHelper.isJDYK2002ARGB(device)) {
                hasRGB = true;
            }
            if (DeviceHelper.isJDYK2002ANIR(device)) {
                hasNIR = true;
            }
            
        
        }
        
        
        
        if (hasRGB && hasNIR) {
            return true;
        } else {
            return false;
        }
        
    
    }
    
    
    
    
    
    
    




}
