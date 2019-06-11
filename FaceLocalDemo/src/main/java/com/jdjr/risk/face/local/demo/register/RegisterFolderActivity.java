package com.jdjr.risk.face.local.demo.register;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jdjr.risk.face.local.demo.R;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by michael on 19-3-7.
 */

public class RegisterFolderActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder);
        
        showFolderList();
    }
    
    
    private void showFolderList() {
        final File sdcard = Environment.getExternalStorageDirectory();
        final File[] dirs = sdcard.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    
        final List<File> dirList = Arrays.asList(dirs);
    
        Collections.sort(dirList, new Comparator<File>() {
            @Override
            public int compare(File dir1, File dir2) {
                final int nameOrder = dir1.getName().compareTo(dir2.getName());
                return nameOrder;
            }
        });
        
        
        
        final RegisterFolderAdapter folderAdapter = new RegisterFolderAdapter(dirList);
        final RecyclerView folderList = findViewById(R.id.folder_list);
        folderList.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        folderList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    
    
        folderList.setAdapter(folderAdapter);
        // folderAdapter.notifyDataSetChanged();
        
        
        
        
        
        
        
        
        
    }
    
    
    
    
    
    
    
    
    
    
    
}
