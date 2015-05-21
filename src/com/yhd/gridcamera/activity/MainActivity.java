package com.yhd.gridcamera.activity;


import java.util.Random;

import com.yhd.gridcamera.R;
import com.yhd.gridcamera.R.drawable;
import com.yhd.gridcamera.R.id;
import com.yhd.gridcamera.R.layout;
import com.yhd.gridcamera.manage.CameraInstance;
import com.yhd.gridcamera.view.CameraView;
import com.yhd.gridcamera.view.Point;
import com.yhd.gridcamera.view.RectView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends Activity {

	private Camera mCamera;
	private RectView mRectView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		mCamera = CameraInstance.getCameraInstance();
		mRectView = new RectView(this);
		FrameLayout f = (FrameLayout) findViewById(R.id.camera);
        if(mCamera != null){
            CameraView cv = new CameraView(this, mCamera, mRectView);
            f.addView(cv);
            f.addView(mRectView);
        }
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		mCamera.release();
		this.finish();
	}
	
}
