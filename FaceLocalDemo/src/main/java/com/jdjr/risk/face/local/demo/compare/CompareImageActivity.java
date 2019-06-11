package com.jdjr.risk.face.local.demo.compare;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jdjr.risk.face.local.compare.CompareImageManager;
import com.jdjr.risk.face.local.demo.R;
import com.jdjr.risk.face.local.demo.register.RegisterImage;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Created by michael on 19-3-7.
 */

public class CompareImageActivity extends Activity {
    
    private TextView countView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_image_compare);
    
        countView = findViewById(R.id.select_count);
        countView.setText("已选图片：" + 0);
        
        final String path = getIntent().getStringExtra("path");
        showImageList(path);
        
    }
    
    private CompareImageAdapter adapter;
    
    private void showImageList(String path) {
        // get images
        final File dir = new File(path);
        final File[] images = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return isImage(name);
            }
        });
        
        
        final ArrayList<RegisterImage> registerImages = new ArrayList<>();
        for (File file : images) {
            final RegisterImage registerImage = new RegisterImage(file, false);
            registerImages.add(registerImage);
        }
    
        Collections.sort(registerImages, new Comparator<RegisterImage>() {
            @Override
            public int compare(RegisterImage dir1, RegisterImage dir2) {
                final int nameOrder = dir1.getImage().getName().compareTo(dir2.getImage().getName());
                return nameOrder;
            }
        });
        
        
        
        adapter = new CompareImageAdapter(registerImages, listener);
        
        
        
        
        // show images
        final RecyclerView imageList = findViewById(R.id.image_list);
        imageList.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        imageList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        
        imageList.setAdapter(adapter);
        
    }
    
    private CompareImageAdapter.SelectListener listener = new CompareImageAdapter.SelectListener() {
        @Override
        public void onSelectChanged(int count) {
            countView.setText("已选图片：" + count);
        }
        
        
    };
    
    
    
    private static boolean isImage(String name) {
        
        if (name.endsWith(".jpg")) {
            return true;
        }
        
        if (name.endsWith(".JPG")) {
            return true;
        }
    
        if (name.endsWith(".jpeg")) {
            return true;
        }
    
        if (name.endsWith(".JPEG")) {
            return true;
        }
    
        if (name.endsWith(".png")) {
            return true;
        }
        if (name.endsWith(".PNG")) {
            return true;
        }
    
        if (name.endsWith(".bmp")) {
            return true;
        }
    
        if (name.endsWith(".BMP")) {
            return true;
        }
    
    
        return false;
    
    }
    
    
    
    
    
//    public void selectAll(View view) {
//
//
//        boolean checked = false;
//        if (view.getTag() == null) {
//            checked = true;
//            view.setTag(new Object());
//        } else {
//            checked = false;
//            view.setTag(null);
//        }
//
//
//
//
//        final ArrayList<RegisterImage> registerImages = adapter.registerImages;
//        for (RegisterImage registerImage : registerImages) {
//            registerImage.setChecked(checked);
//        }
//
//        adapter.notifyDataSetChanged();
//
//    }
    
    
//    public void registerAll(View view) {
//
//
//        final LinkedList<File> registerImages = new LinkedList<>();
//
//        for (RegisterImage registerImage : adapter.registerImages) {
//            if (registerImage.isChecked()) {
//                registerImages.add(registerImage.getImage());
//            }
//
//
//        }
//
//        ImageRegisterHelper.registerImageMultiple(this.getApplicationContext(), registerImages, null);
//
//
//
//    }
    
    
    public void compare2Images(View view) {
    
        final LinkedList<File> registerImages = new LinkedList<>();
    
        for (RegisterImage registerImage : adapter.registerImages) {
            if (registerImage.isChecked()) {
                registerImages.add(registerImage.getImage());
            }
        
        }
        
        
        if (registerImages.size() == 2) {
            compare2ImageImpl(registerImages.get(0).getPath(), registerImages.get(1).getPath());
        
        
        } else {
            Toast.makeText(CompareImageActivity.this, "请选择2张照片!!!!!!", Toast.LENGTH_SHORT).show();
        }
        
        
    
    
    }
    
    
    private void compare2ImageImpl(String imagePath1, String imagePath2) {
        Log.d("FaceLocalCompare", "@@@@@@@@@@@@@@ compare2ImageImpl before thread id = " + Thread.currentThread().getId());
        CompareImageManager.getInstance().compare2Images(imagePath1, imagePath2, new CompareImageManager.CompareCallback() {
            @Override
            public void onCompareResult(final boolean isSame, float compareScore) {
                Log.d("FaceLocalCompare", "============== onCompareResult isSame = " + isSame);
                
                Log.d("FaceLocalCompare", "@@@@@@@@@@@@@@ compare2ImageImpl after thread id = " + Thread.currentThread().getId());
                displayCompareResult(isSame, compareScore);
                
            }
        });
    }
    
    private void displayCompareResult(boolean isSame, float compareScore) {
        // Toast.makeText(CompareImageActivity.this, "compare2Images isSame = " + isSame, Toast.LENGTH_SHORT).show();
//        mProgressDialog = new ProgressDialog(this);
//        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        mProgressDialog.setIndeterminate(true);
//        mProgressDialog.setCancelable(true);
//        mProgressDialog.setCanceledOnTouchOutside(false);
//        mProgressDialog.setTitle("正在启动人脸本地服务...........");
//        mProgressDialog.show();
        final String label = "比对结果:" + (isSame ? "相同" : "不同") + "----------------比对分数:" + compareScore;
        
        
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        
//        final TextView resultView = new TextView(this);
//        resultView.setWidth(700);
//        resultView.setHeight(300);
//        resultView.setGravity(Gravity.CENTER_VERTICAL);
//        resultView.setTextColor(0xFF0000);
//        resultView.setPadding(90, 0, 0, 0);
//        resultView.setTextSize(1.0f);
//        resultView.setText(label);
//        builder.setCustomTitle(resultView);
        builder.setTitle(label);
//        builder.setNeutralButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//
//            }
//        });
        builder.show();
        
        
        
        
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
