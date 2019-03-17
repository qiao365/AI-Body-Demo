package com.small.cjq.mainapp;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.camareui.constant.Constant;
import com.camareui.utils.ThreadUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {
	public static final String TAG = "StartActivity";
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					Toast.makeText(StartActivity.this, "jet文件拷贝完毕", Toast.LENGTH_LONG).show();
					mJetDetectBtn.setClickable(true);
					break;
			}
		}
	};
	private AssetManager mAssetManager;
	private File mBinDir = new File(Constant.JET_BIN_DIR);
	private volatile int mCopiedCount = 0;
	private Button mJetDetectBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		mAssetManager = getAssets();
		mJetDetectBtn = findViewById(R.id.jetBody);
		mJetDetectBtn.setClickable(false);
		if (!mBinDir.exists()) {
			mBinDir.mkdirs();
		}
		ThreadUtil.executeRunnables(Arrays.asList(new Runnable() {
			@Override
			public void run() {
				try {
					copyFile(Constant.JET_INPUT256_C64_BIN);
					copiedCountIncrement();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, new Runnable() {
			@Override
			public void run() {
				try {
					copyFile(Constant.JET_INPUT256_C64_PARAM_BIN);
					copiedCountIncrement();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}));
	}

	private void copyFile(String fileName) throws IOException {
		InputStream inputStream = null;
		BufferedOutputStream outputStream = null;
		try {
			inputStream = mAssetManager.open(fileName);
			byte[] bytes = new byte[1024];
			int len;
			File destFile = new File(mBinDir, fileName);
			destFile.deleteOnExit();
			outputStream = new BufferedOutputStream(new
					FileOutputStream(destFile));
			while ((len = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, len);
				outputStream.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}

	private synchronized void copiedCountIncrement() {
		mCopiedCount++;
		if (mCopiedCount == 2) {
			mHandler.sendEmptyMessage(0);
		}
	}

	@Override
	public void onClick(View v) {
		Constant.AISDKTYPE type = Constant.AISDKTYPE.FACE;
		switch (v.getId()) {
			case R.id.jetBody:
				type = Constant.AISDKTYPE.JETDETECTBODY;
				break;
		}
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("type", type);
		startActivity(intent);
	}

	@Override
	protected void onDestroy() {
		mHandler.removeCallbacksAndMessages(null);
		super.onDestroy();
	}
}
