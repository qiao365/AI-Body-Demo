package com.jdjr.risk.face.local.demo.camera;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by michael on 18-12-20.
 */

public class DeviceHelper {
    
    
    // TODO JDY 3002A
    public static boolean isJDYK2002ARGB(UsbDevice device) {
        final boolean isJDYK2002ARGB = (device.getVendorId() == 49512);
        
//        if (isJDYK2002ARGB) {
//            Log.d("FaceLocalUVC", "############## RGB RGB RGB RGB RGB RGB RGB ");
//        }
        Log.e("DeviceHelper","RGB device.getVendorId()" + device.getVendorId());
        Log.d("FaceLocalDevice", ".............. isJDYK2002ARGB = " + isJDYK2002ARGB);
        
        return isJDYK2002ARGB;
    }
    
    public static boolean isJDYK2002ANIR(UsbDevice device) {
        final boolean isJDYK2002ANIR = (device.getVendorId() == 53608);
//        if (isJDYK2002ANIR) {
//            Log.d("FaceLocalUVC", "############## NIR NIR NIR NIR NIR NIR NIR ");
//        }
        Log.e("DeviceHelper","NIR device.getVendorId()" + device.getVendorId());
    
        Log.d("FaceLocalDevice", ".............. isJDYK2002ANIR = " + isJDYK2002ANIR);
        return isJDYK2002ANIR;
    }
    
    
    
    
    
    
    
    
    
//    // TODO SONIX
//    public static boolean isRGBSonix(UsbDevice device) {
//        // TODO test
//        final boolean isRGBSonix = (device.getVendorId() == 3141 && device.getDeviceName().equals(DeviceName.getRgbSonix()));
//        return isRGBSonix;
//    }
//
//
//    public static boolean isNIRSonix(UsbDevice device) {
//        // TODO test
//        final boolean isNIRSonix = (device.getVendorId() == 3141 && device.getDeviceName().equals(DeviceName.getNirSonix()));
//        return isNIRSonix;
//    }
    
    
    
    
    
    
    
    
    
    
    public static String getType(UsbDevice device) {
        if (isJDYK2002ANIR(device)) {
            return "NIR";
        } else if (isJDYK2002ARGB(device)) {
            return "RGB";
        } else {
            return "others";
        }
    
    
    }
    
//
//    public static UsbDevice getRGBDevice(Context applicationContext) {
//        final UsbManager manager = (UsbManager) applicationContext.getSystemService(Context.USB_SERVICE);
//        final HashMap<String, UsbDevice> deviceMap = manager.getDeviceList();
//
//
//
//        for (Map.Entry<String, UsbDevice> entry : deviceMap.entrySet()) {
//            final UsbDevice device = entry.getValue();
//
//            if (DeviceHelper.isJDYK2002ARGB(device)) {
//                return device;
//            }
//
//        }
//
//        return null;
//    }


    public static UsbDevice getNIRDevice(Context applicationContext) {
        final UsbManager manager = (UsbManager) applicationContext.getSystemService(Context.USB_SERVICE);
        final HashMap<String, UsbDevice> deviceMap = manager.getDeviceList();

        for (Map.Entry<String, UsbDevice> entry : deviceMap.entrySet()) {
            final UsbDevice device = entry.getValue();

            if (DeviceHelper.isJDYK2002ANIR(device)) {
                return device;
            }
        }



        return null;

    }
    

}
