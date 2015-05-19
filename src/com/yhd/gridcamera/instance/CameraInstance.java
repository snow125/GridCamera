package com.yhd.gridcamera.instance;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;

/**
 * Created by Think on 2015/4/22.
 */
public class CameraInstance {

	static {  
        System.loadLibrary("ImgFun");  
    }
	
	public static native String ImgFun();
	
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open();
        }
        catch (Exception e){
        }
        return c;
    }
    
    private static boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            return true;
        } else {
            return false;
        }
    }

}
