package com.jdjr.risk.face.local.demo.verify;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jdjr.risk.face.local.demo.camera.CameraSettings;
import com.jdjr.risk.face.local.demo.R;
import com.jdjr.risk.face.local.search.LocalSearchResult;
import com.jdjr.risk.face.local.verify.VerifyRecord;
import com.jdjr.risk.face.local.util.BitmapUtil;
import com.jdjr.risk.face.local.util.DisplayRunnable;

import java.io.File;
import java.util.List;



/**
 * Created by michael on 18-11-20.
 */

public class VerifyRecordAdapter extends RecyclerView.Adapter<VerifyRecordAdapter.RecordViewHolder> {
    // private Context applicationContext;
    private List<VerifyRecord> recordList;
    
    public VerifyRecordAdapter(List<VerifyRecord> recordList) {
        // this.applicationContext = applicationContext;
        this.recordList = recordList;
    }
    
    @Override
    public RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false)
        Log.d("FaceLocal", "@@@@@@@@@ VerifyRecordAdapter onCreateViewHolder");
        
        final Context applicationContexttt =  parent.getContext().getApplicationContext();
        final View view = LayoutInflater.from(applicationContexttt).inflate(R.layout.item_verify, parent, false);
        
        final RecordViewHolder holder = new RecordViewHolder(view);
        return holder;
    }
    
    @Override
    public void onBindViewHolder(RecordViewHolder holder, int position) {
        final VerifyRecord record = recordList.get(position);
        Log.d("FaceLocalRecord", "@@@@@@ RecordAdapter onBindViewHolder record = " + record);
        
        final int verifyResult = record.getVerifyResult();
        
//        if (verifyResult == LocalSearchResult.SUCCESS) {
//            holder.recordLayout.setBackgroundColor(0x1F0000FF);
//        } else {
//            holder.recordLayout.setBackgroundColor(0x1FFF0000);
//        }
        
        holder.recordIdView.setText("" + record.getId());
        
        final long verifyRecordTimestamp = record.getTimestamp();
        Log.d("FaceLocalRecord", ".............. verifyRecordTimestamp = " + verifyRecordTimestamp);
        
        holder.recordTimeView.setText("" + VerifyRecord.getTimeLabel(verifyRecordTimestamp));
        
        if (verifyResult == LocalSearchResult.SUCCESS) {
            holder.verifyResultView.setTextColor(0xFF006400);
        } else {
            holder.verifyResultView.setTextColor(0xFFCD0000);
        }
        holder.verifyResultView.setText("" + LocalSearchResult.getResultLabel(verifyResult));
        
        holder.recordUserView.setText("" + record.getUniqueId());
        holder.checkScoreView.setText(record.getCheckScoreString());
        holder.recordScoreView.setText(record.getScoreString());
        
        // clear old bitmap
        holder.recordFaceView.setImageBitmap(null);
        // terminated display runnable
        if (holder.recordFaceView.getTag() != null) {
            final DisplayRunnable displayRunnable = (DisplayRunnable) holder.recordFaceView.getTag(R.id.tag_display_runnable);
            displayRunnable.setTerminated(true);
        }
        
        // final String facePath = record.getNirPath();
        final String pathRGB = record.getFacePath();
        // Log.d("FaceLocal", "@@@@@@@@@ VerifyRecordAdapter facePath = " + facePath);
        final File fileRGB = new File(pathRGB);
        if (fileRGB.exists()) {
            // get reuse bitmap
            final Bitmap reuseBitmap = (Bitmap) holder.recordFaceView.getTag(R.id.tag_reuse_bitmap);
            // display bitmap with reuse memory
            BitmapUtil.postDisplayBitmap(holder.recordFaceView, pathRGB, reuseBitmap);
        } else {
            // TODO decode in worker thread ???
            // holder.recordFaceView.setImageResource(R.drawable.image_not_exist);
            holder.recordFaceView.setImageResource(R.drawable.no_imagee);
        }
        
        
        
        
        
        holder.cloneFaceImage.setTag(record.getFacePath());
        // TODO clone face image
        
        
        
    
    }
    
    @Override
    public int getItemCount() {
        return recordList.size();
    }
    

    
    
    
    /*
    public static final int TAG_DISPLAY_RUNNABLE = 101010;
    private static final int TAG_REUSE_BITMAP = 12345;
    */
    
    public class RecordViewHolder extends RecyclerView.ViewHolder {
        LinearLayout recordLayout;
        TextView recordIdView;
        TextView recordTimeView;
        TextView verifyResultView;
        TextView recordUserView;
        TextView checkScoreView;
        TextView recordScoreView;
        ImageView recordFaceView;
        Button cloneFaceImage;
    
        public RecordViewHolder(View itemView) {
            super(itemView);
    
            recordLayout = itemView.findViewById(R.id.verify_record_layout);
            
            recordIdView = itemView.findViewById(R.id.record_id_view);
            
            recordTimeView = itemView.findViewById(R.id.record_time_view);
    
            verifyResultView = itemView.findViewById(R.id.verify_result_view);
            
            recordUserView = itemView.findViewById(R.id.record_user_view);
            recordFaceView = itemView.findViewById(R.id.record_face_view);
    
            checkScoreView = itemView.findViewById(R.id.check_score_view);
            recordScoreView = itemView.findViewById(R.id.record_score_view);
            
            final Bitmap emptyBitmap = BitmapUtil.generateEmptyBitmap(CameraSettings.getCameraWidth(), CameraSettings.getCameraHeight());
            recordFaceView.setTag(R.id.tag_reuse_bitmap, emptyBitmap);
            
            cloneFaceImage = itemView.findViewById(R.id.bt_clone_face);
            
            
            
        }
        
        
        
    }
    
    

    
    
    
    
    
    
    
    
}
