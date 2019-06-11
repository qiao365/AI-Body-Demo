package com.jdjr.risk.face.local.demo.register;

import java.io.File;

/**
 * Created by michael on 19-3-8.
 */

public class RegisterImage {
    private File image;
    private boolean checked;
    
    
    public RegisterImage(File image, boolean checked) {
        this.image = image;
        this.checked = checked;
    }
    
    
    
    
    
    public File getImage() {
        return image;
    }
    
//    public void setImage(File image) {
//        this.image = image;
//    }
    
    
    
    public boolean isChecked() {
        return checked;
    }
    
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    
    
    
    
    
    
    
    
    
}
