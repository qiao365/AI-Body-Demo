package com.jdjr.risk.face.local.demo.compare;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jdjr.risk.face.local.demo.R;
import com.jdjr.risk.face.local.demo.register.RegisterImage;
import com.jdjr.risk.face.local.util.BitmapUtil;
import com.jdjr.risk.face.local.util.DisplayRunnable;

import java.util.ArrayList;

/**
 * Created by michael on 19-3-8.
 */

public class CompareImageAdapter extends RecyclerView.Adapter<CompareImageAdapter.ImageHolder> {
    public interface SelectListener {
        void onSelectChanged(int count);
    }
    
    
    public ArrayList<RegisterImage> registerImages;
//    public LinkedList<File> selectedImages = new LinkedList<>();
    private SelectListener listener;
    
    
//    private void selectImage(File image) {
//        selectedImages.add(image);
//        listener.onSelectChanged(selectedImages.size());
//    }
//
//    private void unselectedImage(File image) {
//        selectedImages.remove(image);
//        listener.onSelectChanged(selectedImages.size());
//
//    }
    
    private void notifyImageChanged() {
        int count = 0;
    
        for (RegisterImage registerImage : registerImages) {
            if (registerImage.isChecked()) {
                count = count + 1;
            }
        }
        
        listener.onSelectChanged(count);
        
    
    }
    
    
    
    
    
    public CompareImageAdapter(ArrayList<RegisterImage> registerImages, SelectListener listener) {
        this.registerImages = registerImages;
        this.listener = listener;
    }
    
    
    @Override
    public CompareImageAdapter.ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context applicationContext  = parent.getContext().getApplicationContext();
        final View view = LayoutInflater.from(applicationContext).inflate(R.layout.item_image, parent, false);
        final ImageHolder holder = new ImageHolder(view);
        return holder;
        
        
    }
    
    @Override
    public void onBindViewHolder(final CompareImageAdapter.ImageHolder holder, int position) {
        final RegisterImage registerImage = registerImages.get(position);
        
        holder.nameView.setText(registerImage.getImage().getName());
        
        
        
        
        
        
        
        
        
        // TODO display in manager
        // holder.contentView.setImageBitmap(BitmapFactory.decodeFile(registerImage.getImage().getAbsolutePath()));
    
    
//        // clear old bitmap
//        holder.recordFaceView.setImageBitmap(null);
//        // terminated display runnable
//        if (holder.recordFaceView.getTag() != null) {
//            final DisplayRunnable displayRunnable = (DisplayRunnable) holder.recordFaceView.getTag(R.id.tag_display_runnable);
//            displayRunnable.setTerminated(true);
//        }
//
//        final String facePath = record.getFacePath();
//        // final String facePath = record.getNirPath();
//
//        // Log.d("FaceLocal", "@@@@@@@@@ VerifyRecordAdapter facePath = " + facePath);
//        // get reuse bitmap
//        final Bitmap reuseBitmap = (Bitmap) holder.recordFaceView.getTag(R.id.tag_reuse_bitmap);
//        // display bitmap with reuse memory
//        BitmapUtil.postDisplayBitmap(holder.recordFaceView, facePath, reuseBitmap);
        
        final Object pathTag = holder.contentView.getTag();
        if (pathTag == null || !pathTag.equals(registerImage.getImage().getAbsolutePath())) {
            holder.contentView.setImageBitmap(null);
            if (holder.contentView.getTag(R.id.tag_display_runnable) != null) {
                final DisplayRunnable displayRunnable = (DisplayRunnable) holder.contentView.getTag(R.id.tag_display_runnable);
                displayRunnable.setTerminated(true);
            }
    
            final String imagePath = registerImage.getImage().getAbsolutePath();
            final Bitmap reuseBitmap = (Bitmap) holder.contentView.getTag(R.id.tag_reuse_bitmap);
            BitmapUtil.postDisplayBitmap(holder.contentView, imagePath, reuseBitmap);
            
        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        holder.checkView.setImageResource((registerImage.isChecked() ? R.drawable.checked : R.drawable.unchecked));
        
        
        holder.imageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                
                final boolean checked = registerImage.isChecked();
                Log.d("FaceLocalImage", "............. image checked = " + checked);
                
                registerImage.setChecked(!checked);
                notifyImageChanged();
                
                if (checked) {
                    holder.checkView.setImageResource(R.drawable.unchecked);
//                    unselectedImage(registerImage.getImage());
                } else {
                    holder.checkView.setImageResource(R.drawable.checked);
//                    selectImage(registerImage.getImage());
                }
            }
        });
        
        
    
    
    }
    
    @Override
    public int getItemCount() {
        return registerImages.size();
    }
    
    
    
    
    public class ImageHolder extends RecyclerView.ViewHolder {
        public LinearLayout imageLayout;
        
        public TextView nameView;
        public ImageView contentView;
        public ImageView checkView;
        
        public ImageHolder(View itemView) {
            super(itemView);
            
            imageLayout = itemView.findViewById(R.id.image_layout);
            
            nameView = itemView.findViewById(R.id.image_name_view);
            
            contentView = itemView.findViewById(R.id.image_content_view);
            final Bitmap emptyBitmap = BitmapUtil.generate10801920Bitmap();
            contentView.setTag(R.id.tag_reuse_bitmap, emptyBitmap);
            
            checkView = itemView.findViewById(R.id.image_checker);
            
        }
        
        
        
        
    }
    
    
    
    
    
    
    
}
