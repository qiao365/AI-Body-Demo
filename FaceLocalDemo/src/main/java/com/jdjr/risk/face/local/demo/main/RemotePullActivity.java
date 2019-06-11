package com.jdjr.risk.face.local.demo.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.jdjr.risk.face.local.demo.R;
import com.jdjr.risk.face.local.demo.remote.RemoteFeaturesCache;
import com.jdjr.risk.face.local.remote.RemoteOffsetHelper;
import com.jdjr.risk.face.local.remote.RemoteRegisterHelper;
import com.jdjr.risk.face.local.demo.remote.RemoteRegisterIdActivity;
import com.jdjr.risk.face.local.remote.RemoteFeatureService;
import com.jdjr.risk.face.local.user.RemoteUserFeature;

import java.util.List;

/**
 * Created by michael on 19-4-26.
 */

public class RemotePullActivity extends Activity {
    private ProgressDialog mProgressDialog;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_remote);
        
        
        initProgressDialog();
    }
    
    private void initProgressDialog() {
        
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setTitle("正在加班加点工作...........");
        
        
    }
    
    private void showProgressDialog() {
        
        mProgressDialog.show();
        
    }
    
    private void dismissProgressDialog() {
        
        mProgressDialog.dismiss();
        
    }
    
    
    
    
    
    
    
    
    public void registerRemote(View view) {
        startActivity(new Intent(this, RemoteRegisterIdActivity.class));
    }
    
    
    
    public void showPullResult(boolean result) {
        final String text = (result ? "拉取成功....." : "拉取失败!!!!!");
        
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RemotePullActivity.this, text, Toast.LENGTH_SHORT).show();
        
            }
        });
    
    }
    
    public void showRegisterResult(boolean result) {
        final String text = (result ? "注册成功....." : "注册失败!!!!!");
        
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RemotePullActivity.this, text, Toast.LENGTH_SHORT).show();
                
            }
        });
        
    }
    
    
    
    
    
    
    
    
    public void pullFeaturesFromRemoteAll(View view) {
        showProgressDialog();
        RemoteFeatureService.pullRemoteFaceAll(new RemoteFeatureService.RemoteFeatureCallback() {
            @Override
            public void onFeatureResult(List<RemoteUserFeature> featureList, String offset) {
                RemoteFeaturesCache.remoteFeatureAll = featureList;
                RemoteOffsetHelper.setOffset(offset);
    
                dismissProgressDialog();
                showPullResult(true);
                
            }
    
            @Override
            public void onFeatureFailure() {
                dismissProgressDialog();
                showPullResult(false);
                
            }
        });
        
    }
    
    
    public void pullFeaturesFromRemoteNew(View view) {
        RemoteFeatureService.pullRemoteFaceNew(new RemoteFeatureService.RemoteFeatureCallback() {
            @Override
            public void onFeatureResult(List<RemoteUserFeature> featureList, String offset) {
                RemoteFeaturesCache.remoteFeatureNew = featureList;
                RemoteOffsetHelper.setOffset(offset);
    
                dismissProgressDialog();
                showPullResult(true);
                
            }
    
            @Override
            public void onFeatureFailure() {
                dismissProgressDialog();
                showPullResult(false);
        
            }
        });
    
    
    }
    
    
    
    
    
    
    
    
    public void registerRemoteFeaturesAll(View view) {
        showProgressDialog();
        if (RemoteFeaturesCache.remoteFeatureAll != null && RemoteFeaturesCache.remoteFeatureAll.size() > 0) {
            RemoteRegisterHelper.registerFeature(this.getApplicationContext(), RemoteFeaturesCache.remoteFeatureAll, 0, new Runnable() {
                @Override
                public void run() {
                    dismissProgressDialog();
    
                    showRegisterResult(true);
                }
            });
        
        
        }
    
    
    
    }
    
    public void registerRemoteFeaturesNew(View view) {
        showProgressDialog();
        
        if (RemoteFeaturesCache.remoteFeatureNew != null && RemoteFeaturesCache.remoteFeatureNew.size() > 0) {
            RemoteRegisterHelper.registerFeature(this.getApplicationContext(), RemoteFeaturesCache.remoteFeatureNew, 0, new Runnable() {
                @Override
                public void run() {
                    dismissProgressDialog();
                
                    showRegisterResult(true);
                    
                }
            });
        
        
        }
    
    
    
    }
    
    

    
    
    


//    public void registerRemoteFeatures(View view) {
//
//
//        FaceLocalService.getInstance().registerFeaturesLocal(this.getApplicationContext(), RemoteHttpService.REMOTE_USER_FEATURE
//                , new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.d("FaceLocalRemoteRegister", "......... registerFeaturesLocal success complete!!!!!!!");
//
//
//                    }
//                }, new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                });
//
//
//
//
//
//    }
    

    
    // TOD register process UI
    
    
    
    



    
    
    
    
}
