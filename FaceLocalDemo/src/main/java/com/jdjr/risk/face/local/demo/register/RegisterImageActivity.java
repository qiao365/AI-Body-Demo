package com.jdjr.risk.face.local.demo.register;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jdjr.risk.face.local.demo.R;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Created by michael on 19-3-7.
 */

public class RegisterImageActivity extends Activity {
    
    private TextView countView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_image);
    
        countView = findViewById(R.id.select_count);
        countView.setText("已选图片：" + 0);
        
        final String path = getIntent().getStringExtra("path");
        showImageList(path);
        
    }
    
    private RegisterImageAdapter adapter;
    
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
        
        
        
        adapter = new RegisterImageAdapter(registerImages, listener);
        
        
        
        
        // show images
        final RecyclerView imageList = findViewById(R.id.image_list);
        imageList.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        imageList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        
        imageList.setAdapter(adapter);
        
    }
    
    private RegisterImageAdapter.SelectListener listener = new RegisterImageAdapter.SelectListener() {
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
    
    
    
    
    
    public void selectAll(View view) {
        
        
        boolean checked = false;
        if (view.getTag() == null) {
            checked = true;
            view.setTag(new Object());
        } else {
            checked = false;
            view.setTag(null);
        }
        
        
        
        
        final ArrayList<RegisterImage> registerImages = adapter.registerImages;
        for (RegisterImage registerImage : registerImages) {
            registerImage.setChecked(checked);
        }
        
        adapter.notifyDataSetChanged();
    
    
    
    
    
    
    
    
    }
    
    
    public void registerAll(View view) {
        
        
        final LinkedList<File> registerImages = new LinkedList<>();
        
        for (RegisterImage registerImage : adapter.registerImages) {
            if (registerImage.isChecked()) {
                registerImages.add(registerImage.getImage());
            }
        
            
        }
        
        ImageRegisterHelper.registerImageMultiple(this.getApplicationContext(), registerImages, null);
    
    
    
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
