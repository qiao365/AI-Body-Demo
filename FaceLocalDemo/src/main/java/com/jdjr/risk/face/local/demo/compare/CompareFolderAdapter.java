package com.jdjr.risk.face.local.demo.compare;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jdjr.risk.face.local.demo.R;

import java.io.File;
import java.util.List;

/**
 * Created by michael on 19-3-7.
 */

public class CompareFolderAdapter extends RecyclerView.Adapter<CompareFolderAdapter.DirHolder> {
    
    private List<File> dirs;
    
    
    public CompareFolderAdapter(List<File> dirs) {
        this.dirs = dirs;
    }
    
    
    @Override
    public DirHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        
        final Context applicationContext  = parent.getContext().getApplicationContext();
        final View view = LayoutInflater.from(applicationContext).inflate(R.layout.item_dir, parent, false);
        final DirHolder holder = new DirHolder(view);
        return holder;
    }
    
    @Override
    public void onBindViewHolder(DirHolder holder, int position) {
    
        final File folder = dirs.get(position);
        
        holder.nameView.setText(folder.getName());
        
        holder.dirLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
    
                final Context applicationContext  = view.getContext().getApplicationContext();
                final Intent intent = new Intent(applicationContext, CompareImageActivity.class);
                intent.putExtra("path", folder.getAbsolutePath());
                applicationContext.startActivity(intent);
        
            }
        });
    
    
    }
    
    @Override
    public int getItemCount() {
        return dirs.size();
    }
    
    
    public class DirHolder extends RecyclerView.ViewHolder {
        public ViewGroup dirLayout;
        public TextView nameView;
    
        public DirHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.dir_name_view);
            dirLayout = itemView.findViewById(R.id.dir_layout);
            
        }
    }
    
    
    
    
    
    
    
}
