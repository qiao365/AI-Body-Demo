package com.jdjr.risk.face.local.demo.register;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jdjr.risk.face.local.demo.R;
import com.jdjr.risk.face.local.user.LocalUserManager;
import com.jdjr.risk.face.local.user.UserFeature;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michael on 18-12-7.
 */

public class RegisterRecordActivity extends Activity {
    private EditText search_edt;                                  //搜索输入框
    private String lastTextString;                                //上一次的输入结果
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_record);
    
    
        search_edt = findViewById(R.id.search_edt);
        search_edt.addTextChangedListener(mSearchWatcher);
        
        
        showRegisterList();
        
    }
    
    
    
    
    private void showRegisterList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // get register list
                final Context applicationContext = RegisterRecordActivity.this.getApplicationContext();
                final List<UserFeature> registerList = LocalUserManager.getRegAll(applicationContext);
                
                if (registerList == null || registerList.size() == 0) {
                    showEmptyResult();
                    
                    
                    return;
                }
                mRegisterList = registerList;
                
                
                // show on UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        displayRegisterList(mRegisterList);
                    }
                });
                
            }
        }).start();
    }
    
    private void showEmptyResult() {
        
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                
                final View view = findViewById(R.id.empty_record_view);
                view.setVisibility(View.VISIBLE);
                
            }
        });
        
        
        
        
    }
    
    
    
    
    // private RecyclerTouchListener onTouchListener;
    List<UserFeature> mRegisterList;
    RegisterRecordAdapter mAdapter;
    
    private void displayRegisterList(final List<UserFeature> registerList) {
        final RecyclerView registerListView = findViewById(R.id.register_list_view);
        registerListView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        registerListView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        
        final RegisterRecordAdapter adapter = new RegisterRecordAdapter(registerList);
        mAdapter = adapter;
        
        registerListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    
    
        //添加触摸监听
//        onTouchListener = new RecyclerTouchListener(this, registerListView);
//        onTouchListener
//                .setSwipeOptionViews(R.id.delete_rl)
//                .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
//                    @Override
//                    public void onSwipeOptionClicked(int viewID, int position) {//侧拉出现的三个按钮监听事件
//                        Log.d("FaceLocal", "@@@@@@@@@@ onTouchListener onSwipeOptionClicked position = " + position);
//                        if (viewID == R.id.delete_rl) {
//                            // deleteFromDatabases(position);
//
//
//                            handleDelete(position);
//
//
//
//
//                        }
//                    }
//
//                    private void handleDelete(int position) {
//                        final String userUniqueId = registerList.get(position).getUserUniqueId();
//                        Log.d("FaceLocal", "@@@@@@@@@@@ userUniqueId = " + userUniqueId);
//                        int result = PersonalManagement.removeUser(RegisterRecordActivity.this.getApplicationContext(), userUniqueId);
//                        if(result == 0){
//                            registerList.remove(position);
//                            if (adapter != null) {
//                                // callSetNumber();
//                                adapter.notifyDataSetChanged();
//                            }
//                        }
//                    }
//
//
//
//                });
        
        
        
        
        
        
        
        
        
        
        
        
        // registerListView.addOnItemTouchListener(onTouchListener);
    
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private TextWatcher mSearchWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        
        }
        
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        
        }
        
        @Override
        public void afterTextChanged(Editable s) {
            String content = s.toString().trim();
            if (!content.equals(lastTextString)) {
                addResult(content);
                lastTextString = content;
            }
        }
    };
    
    
    private void addResult(String content) {
        Log.d("FaceLocal", "@@@@@@ addResult content = " + content);
    
        final List<UserFeature> searchFeatures = new ArrayList<>();
        
    
        for (UserFeature feature : mRegisterList) {
            if (feature.getUserUniqueId().contains(content)) {
                Log.d("FaceLocal", "@@@@@@@ add search result = " + feature.getUserUniqueId());
                searchFeatures.add(feature);
            }
            
        }
//        mAdapter.setDataList(mSearchResults);
//        callSetNumber();
    
        mAdapter.setRegisterList(searchFeatures);
        mAdapter.notifyDataSetChanged();
    }
    
    public void deleteAllUsers(View view) {
        deleteAllUsersImpl();
    }
    
    private void deleteAllUsersImpl() {
        LocalUserManager.getInstance().removeAllUsers(RegisterRecordActivity.this.getApplicationContext(),
                new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("FaceLocalFeatures", "@@@@@@@@ removeAllUsers success");
                                Toast.makeText(RegisterRecordActivity.this, "全部用户删除成功.....", Toast.LENGTH_SHORT).show();
                                notifyUserList();
                                
                            
                            }
                        });
                    
                    
                    }
                }, new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("FaceLocalFeatures", "@@@@@@@@ removeAllUsers failure");
                                Toast.makeText(RegisterRecordActivity.this, "全部用户删除失败!!!!!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    
    }
    
    
    
    private void notifyUserList() {
        mRegisterList.clear();
        mAdapter.notifyDataSetChanged();
        
    
    }
    
    
    
    
    
    /*
    private void deleteFromDatabases(int position) {
        final String userUniqueId = mSearchResults.get(position).getUserUniqueId();
        Log.d("FaceLocal", "@@@@@@@@@@@ userUniqueId = " + userUniqueId);
        int result = PersonalManagement.removeUser(this, userUniqueId);
        if(result == 0){
            mSearchResults.remove(position);
            if (mAdapter != null) {
                // callSetNumber();
                mAdapter.notifyDataSetChanged();
            }
        }
    }
    */
    
    
    
    
    
    
    
    
    
}




