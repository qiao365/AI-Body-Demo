package com.jdjr.risk.face.local.demo.register;

/**
 * Created by michael on 19-1-21.
 */

public class FaceUserImage {
    private String userId;
    private String path;
    
    
    
    public FaceUserImage() {
    }
    
    
    public FaceUserImage(String userId, String path) {
        this.userId = userId;
        this.path = path;
    }
    
    
    
    
    
    
    
    
    
    
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    
    
    
    
}
