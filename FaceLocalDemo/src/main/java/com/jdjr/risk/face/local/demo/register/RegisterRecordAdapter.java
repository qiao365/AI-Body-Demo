package com.jdjr.risk.face.local.demo.register;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdjr.risk.face.local.demo.R;
import com.jdjr.risk.face.local.user.LocalUserManager;
import com.jdjr.risk.face.local.user.UserFeature;
import com.jdjr.risk.face.local.util.TimeUtil;

import java.util.List;

/**
 * Created by michael on 18-12-7.
 */

public class RegisterRecordAdapter extends RecyclerView.Adapter<RegisterRecordAdapter.RegisterViewHolder> {
//    public List<UserFeature> getRegisterList() {
//        return registerList;
//    }
    
    public void setRegisterList(List<UserFeature> registerList) {
        this.registerList = registerList;
    }
    
    private List<UserFeature> registerList;
    
    public RegisterRecordAdapter(List<UserFeature> registerList) {
        this.registerList = registerList;
    }
    
    
    
    @Override
    public RegisterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context applicationContext = parent.getContext().getApplicationContext();
        final View view = LayoutInflater.from(applicationContext).inflate(R.layout.item_register, parent, false);
        final RegisterViewHolder holder = new RegisterViewHolder(view);
        return holder;
    }
    
    @Override
    public void onBindViewHolder(RegisterViewHolder holder, final int position) {
        final UserFeature registerItem = registerList.get(position);
        
        holder.registerIdView.setText("" + registerItem.getId());
        holder.registerTimeView.setText(getTimeLabel(registerItem.getTimestamp()));
        holder.registerUserView.setText("" + registerItem.getUserUniqueId());
        holder.registerGenderView.setText("" + registerItem.getGenderLabel());
        // holder.registerAgeView.setText("" + registerItem.getAge());
        // TODO debug
        holder.registerAgeView.setText("" + registerItem.getGroupId());
        
        final Bitmap face = BitmapFactory.decodeFile(registerItem.getFacePath());
        if (face != null) {
            holder.registerFaceView.setImageBitmap(face);
        }
        
        holder.deleteRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context applicationContext = v.getContext().getApplicationContext();
                handleDelete(applicationContext, position);
            }
    
            private void handleDelete(Context applicationContext, int position) {
                final UserFeature faceUser = registerList.get(position);
                final String userUniqueId = faceUser.getUserUniqueId();
                final long groupId = faceUser.getGroupId();
                
                int result = LocalUserManager.removeUser(applicationContext, groupId, userUniqueId);
                if (result == 0) {
                    registerList.remove(position);
                    notifyDataSetChanged();
                }
            }
            
        });
        
        
    }
    
    
    private static String getTimeLabel(String timeMillisLongString) {
    
        final long timeMillis = Long.valueOf(timeMillisLongString).longValue();
        final String timeLabel = TimeUtil.getDateTimeText(timeMillis);
        return timeLabel;
    
    }
    

    
    @Override
    public int getItemCount() {
        return registerList.size();
    }
    
    
    
    
    
    
    public class RegisterViewHolder extends RecyclerView.ViewHolder {
        // private LinearLayout registerRecordLayout;
        private TextView registerIdView;
        private TextView registerTimeView;
        private TextView registerUserView;
        private TextView registerGenderView;
        private TextView registerAgeView;
        
        private ImageView registerFaceView;
        private Button deleteRecordButton;
        
    
        public RegisterViewHolder(View itemView) {
            super(itemView);
            
            // registerRecordLayout = itemView.findViewById(R.id.register_record_layout);
            registerIdView = itemView.findViewById(R.id.register_id_view);
            registerTimeView = itemView.findViewById(R.id.register_time_view);
            registerUserView = itemView.findViewById(R.id.register_user_view);
            registerGenderView = itemView.findViewById(R.id.register_gender_view);
            registerAgeView = itemView.findViewById(R.id.register_age_view);
            
            registerFaceView = itemView.findViewById(R.id.register_face_view);
            deleteRecordButton = itemView.findViewById(R.id.register_record_delete_button);
            
        }
    }
    
    
    
    
    
//    private static float deleteButtonWidth;
//
//    private float beginX;
//    private float lastX;
//
//
//    private void handleMotionEvent(View view, MotionEvent event) {
//
//
//        final int action = event.getAction();
//        Log.d("FaceLocalDelete", "...... handleMotionEvent action = " + action);
//
//
//
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                beginX = event.getX();
//                lastX = event.getX();
//                break;
//            case MotionEvent.ACTION_UP:
//                beginX = 0;
//                lastX = 0;
//
//
//
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                beginX = 0;
//                lastX = 0;
//
//                break;
//
//
//            case MotionEvent.ACTION_MOVE:
//
//                final float currentX = event.getX();
//                final float deltaX = currentX - beginX;
//                lastX = currentX;
//                Log.d("FaceLocalDelete", "................. deltaX = " + deltaX);
//
//                if (Math.abs(lastX - beginX) < deleteButtonWidth) {
//                    view.setTranslationX(50);
//                }
//
//                break;
//
//
//            default:
//                break;
//
//        }
//
//
//    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
