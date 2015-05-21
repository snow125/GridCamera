package com.yhd.gridcamera.manage;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.yhd.gridcamera.view.Point;
import com.yhd.gridcamera.view.RectView;

public class DealPictureThread extends Thread{
	
	public native boolean isIdle(String picPath);
	
	private List<Point> points = new ArrayList<>();
    private RectView mRectView;
    private boolean[] freeTime;
    private Bitmap resource;
    
	public DealPictureThread(RectView mRectView, Bitmap resource) {
		super();
		this.mRectView = mRectView;
		this.resource = resource;
		freeTime = new boolean[mRectView.getPointCount()];
		for (int i = 0; i < mRectView.getColNum(); i++) {
			for (int j = 0; j < mRectView.getRowNum(); j++) {
				points.add(mRectView.getNextPoint(i, j));
			}
		}
		this.start();
	}

	@Override
	public void run() {
		super.run();
        for (int i = 0; i < points.size(); i++) {
        	Point p = points.get(i);
        	Bitmap result = Bitmap.createBitmap(resource, p.getX(), p.getY(),
                p.getItemWidth(), p.getItemHeight());
        	try {
                saveFile(result, "opencv_"+Calendar.getInstance().getTimeInMillis()+".png", i);
            } catch (IOException e) {
                e.printStackTrace();
            }
		}
	}
	
	public void saveFile(Bitmap bm, String fileName, int position) throws IOException {
        String path = Environment.getExternalStorageDirectory() +"/GridCamera/";
        File dirFile = new File(path);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
        bos.flush();
        bos.close();
        if(isIdle(myCaptureFile.getAbsolutePath())){
        	freeTime[position] = true;
        }else{
        	freeTime[position] = false;
        }
        myCaptureFile.delete();
    }

	public boolean[] getFreeTime() {
		return freeTime;
	}

	public void setFreeTime(boolean[] freeTime) {
		this.freeTime = freeTime;
	}
	
}
