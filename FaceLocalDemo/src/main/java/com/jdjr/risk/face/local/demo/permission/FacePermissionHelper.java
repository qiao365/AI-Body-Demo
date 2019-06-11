package com.jdjr.risk.face.local.demo.permission;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

/**
 * Created by michael on 19-3-18.
 */

public class FacePermissionHelper {
    
    /**
     * 检查并请求必须权限
     * @param activity
     * @param requestCode
     */
    public static void requestPermissions(Activity activity, int requestCode) {
        Log.d("FaceLocal", "@@@@@@@ FaceLocalServiceImpl requestPermissions");
        final Intent intent = new Intent(activity.getApplicationContext(), PermissionsActivity.class);
        activity.startActivityForResult(intent, requestCode);
        
    }




}
