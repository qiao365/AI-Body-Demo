package com.jdjr.risk.face.local.demo.capture;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jdjr.risk.face.local.demo.R;
import com.jdjr.risk.face.local.demo.compare.CompareImageAdapter;
import com.jdjr.risk.face.local.demo.register.RegisterImage;
import com.jdjr.risk.face.local.demo.util.ImageUtil;
import com.jdjr.risk.face.local.settings.FaceSettings;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by michael on 19-3-7.
 */

public class CaptureFaceActivity extends Activity {
    
//    private TextView countView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_face_capture);
    
//        countView = findViewById(R.id.select_count);
//        countView.setText("已选图片：" + 0);
        
        // final String path = getIntent().getStringExtra("path");
        final String path = FaceSettings.getCaptureDir();
        if (path != null && path.length() > 0 && new File(path).exists() && new File(path).isDirectory()) {
            showImageList(path);
        }
        
    }
    
    
    
    
    private CompareImageAdapter adapter;
    
    
    
    
    
    private void showImageList(String path) {
        // get images
        final File dir = new File(path);
        final File[] images = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return ImageUtil.isImage(name);
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
                final int nameOrder = dir2.getImage().getName().compareTo(dir1.getImage().getName());
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
//            countView.setText("已选图片：" + count);
        }
        
        
    };
    
    
    
    
    
    
    
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
