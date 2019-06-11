package com.jdjr.risk.face.local.demo.register;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.jdjr.risk.face.local.service.FaceLocalService;
import com.jdjr.risk.face.local.user.LocalUserManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michael on 19-1-18.
 */

public class ImageRegisterHelper {


    // private static ExecutorService resultService = Executors.newSingleThreadExecutor();
    
    
    
    
    
    
//    public static void registerMultipleBean(final Activity activity, final ArrayList<ImageBean> imageBeans, Runnable completeRunnable) {
//
//        final List<FaceUserImage> userImages = new ArrayList<>();
//
//        for (int i = 0; i < imageBeans.size(); i++) {
//
//            final String path = imageBeans.get(i).getPath();
//            final String userId = getUserId(path);
//            final FaceUserImage image = new FaceUserImage(userId, path);
//            userImages.add(image);
//        }
//
//        registerImageAtIndex(activity, userImages, 0, completeRunnable);
//
//    }
    
    
    public static void registerImageMultiple(Context applicationContext, final List<File> images, Runnable completeRunnable) {
    
        final List<FaceUserImage> registerImages = new ArrayList<>();
    
    
        for (File file : images) {
            final String path = file.getPath();
            final String uid = getUserId(path);
            final FaceUserImage userImage = new FaceUserImage(uid, path);
            registerImages.add(userImage);
        }
        
        registerImageAtIndex(applicationContext, registerImages, 0, completeRunnable);
        
    }
    
    
    
    
    
    
    
    
    
    // TODO debug
//    public static void registerMultipleImage(final Activity activity, final ArrayList<ImageBean> imageBeans, Runnable runnable) {
//        successCount = 0;
//        failureCount = 0;
//
//        // final int number = 5;
//        // final int number = 100;
//        // final int number = 11000;
//        final int number = 50000;
//
//
//        final List<FaceUserImage> userImages = new ArrayList<>();
//        for (int i = 0; i < number; i++) {
//            final FaceUserImage image = new FaceUserImage();
//            final String path = imageBeans.get(0).getPath();
//            image.setPath(path);
//
//            userImages.add(image);
//        }
//
//        registerImageAtIndex(activity, userImages, 0, runnable);
//
//    }
    
    
    
//    private static void publishResult(final Activity activity, final String detail) {
//        resultService.submit(new Runnable() {
//            @Override
//            public void run() {
//
//
//                sleepAMoment();
//
//                activity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(activity, detail, Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//
//            }
//        });
//
//
//    }
    
    
    
    
    
    private static int successCount = 0;
    private static int failureCount = 0;
    
    
    
    private static void registerImageAtIndex(final Context applicationContext, final List<FaceUserImage> images, int index, final Runnable completeRunnable) {
        final long beginTime = System.currentTimeMillis();
        
        if (index == images.size()) {
            Toast.makeText(applicationContext, "全部图片注册成功", Toast.LENGTH_LONG).show();
            
            Log.d("FaceLocalRegister", "======= registerMultipleImage successCount = " + successCount);
            Log.d("FaceLocalRegister", "======= registerMultipleImage failureCount = " + failureCount);
            
            if (completeRunnable != null) {
                completeRunnable.run();
            }
            return;
        }
        
        
        // sleepAMoment();
        
    
        final FaceUserImage image = images.get(index++);
        // register everyone
        final String userId = image.getUserId();
        // TODO debug
        // final String userId = TimeUtil.getDayTimeLabel(System.currentTimeMillis());
        final String imagePath = image.getPath();
        Log.d("FaceLocalRegister", "======= registerImageAtIndex =========================================== ");
        Log.d("FaceLocalRegister", "======= registerImageAtIndex userId = " + userId);
        Log.d("FaceLocalRegister", "======= registerImageAtIndex imagePath = " + imagePath);
    
    
        final int finalIndex = index;
        final LocalUserManager.UserManageCallback addCallback = new LocalUserManager.UserManageCallback() {
            @Override
            public void onUserResult(boolean result, String message) {
//                Log.d("FaceLocal", userId + " @@@@@@@@ face local demo register by image result = " + result);
//                Log.d("FaceLocal", userId + " @@@@@@@@ face local demo register by image message = " + message);
                
                if (result) {
                    successCount++;
                } else {
                    failureCount++;
                }
                
                
                
                
                final String detail =
                        "userId = " + userId + "\n" +
                        "imagePath = " + imagePath + "\n" +
                        "result = " + result + "\n" +
                        "message = " + message + "\n"
                        ;
                    
                Log.d("FaceLocalRegister", "======= registerImageAtIndex detail = " + detail);
                
                final long consumeTime = System.currentTimeMillis() - beginTime;
                Log.d("FaceLocalRegister", "======= registerImageAtIndex consumeTime = " + consumeTime);
                Log.d("FaceLocalRegister", "======= registerImageAtIndex successCount = " + successCount);
                Log.d("FaceLocalRegister", "======= registerImageAtIndex failureCount = " + failureCount);
                
                // publishResult(activity, detail);
                
                
                // Toast.makeText(activity, detail, Toast.LENGTH_SHORT).show();
    
    
                // register next
                registerImageAtIndex(applicationContext, images, finalIndex, completeRunnable);
            }
        };
    
    
    
    
        LocalUserManager.getInstance().registerByImage(applicationContext, userId, imagePath, null, addCallback);
    
    
    }
    
    
    
    
    
    
    
    private static void sleepAMoment() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    private static String getUserId(String path) {
        // final String addPath = "/sdcard/zhaohongtao1.jpg";
//        final String beginLabel = "sdcard/";
//        final int indexSdcard = path.indexOf(beginLabel);
//        final int beginIndex = indexSdcard + beginLabel.length();
        
        // /storage/emulated/0/images/hhjkk_66788.jpeg
    
//        YQ01151赵良议.jpg

//        YQ01764钟元香.JPG
//        YQ11853 熊雨婕.JPG
//        YQ00363 廖才英.jpg
//        YQ01232 杨月梅 Anny.JPG
    
    
        final int lastIndexSlash = path.lastIndexOf("/");
        final int beginIndex = lastIndexSlash + 1;
        Log.d("FaceLocalRegister", "@@@@@@ beginIndex = " + beginIndex);
        
        int endIndex = path.indexOf(".jpg");
    
        if (endIndex == -1) {
            endIndex = path.indexOf(".jpg");
        }
        
        if (endIndex == -1) {
            endIndex = path.indexOf(".JPG");
        }
        
        if (endIndex == -1) {
            endIndex = path.indexOf(".jpeg");
        }
    
        if (endIndex == -1) {
            endIndex = path.indexOf(".JPEG");
        }
    
        if (endIndex == -1) {
            endIndex = path.indexOf(".bmp");
        }
    
        if (endIndex == -1) {
            endIndex = path.indexOf(".BMP");
        }
        
        if (endIndex == -1) {
            endIndex = path.indexOf(".png");
        }
    
        if (endIndex == -1) {
            endIndex = path.indexOf(".PNG");
        }
        
    
        Log.d("FaceLocalRegister", "@@@@@@ endIndex = " + endIndex);
        
        
        
        final String userId = path.substring(beginIndex, endIndex);
        return userId;
    }
    
    
    
    
    
    






}
