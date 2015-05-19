package com.yhd.gridcamera.activity;


import com.yhd.gridcamera.R;
import com.yhd.gridcamera.R.drawable;
import com.yhd.gridcamera.R.id;
import com.yhd.gridcamera.R.layout;
import com.yhd.gridcamera.instance.CameraInstance;
import com.yhd.gridcamera.view.CameraView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends Activity {

	private Camera mCamera;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		mCamera = CameraInstance.getCameraInstance();
		FrameLayout f = (FrameLayout) findViewById(R.id.camera);
        if(mCamera != null){
            CameraView cv = new CameraView(this, mCamera);
            f.addView(cv);
            //f.addView(mRectView);
        }
		
//		Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
//		Button button = (Button) findViewById(R.id.jnitest);
//		button.setText(ImgFun());
	}
}
