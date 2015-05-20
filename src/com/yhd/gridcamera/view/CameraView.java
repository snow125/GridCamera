package com.yhd.gridcamera.view;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Think on 2015/4/22.
 */
public class CameraView extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback{

	public native boolean isIdle(String picPath);
	
    private Camera mCamera;
    private SurfaceHolder mHolder;
    private Context context;
    private int previewSizeCount;

    public CameraView(Context context, Camera camera) {
        super(context);
        this.context = context;
        mCamera = camera;
        mHolder = getHolder();
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mHolder.addCallback(this);
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CameraView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.setDisplayOrientation(90);
            mCamera.setPreviewCallback(this);
            setProperty();
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }

    private void setProperty(){
        Camera.Parameters parameters = mCamera.getParameters();

        List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();
        previewSizeCount = sizeList.size();
        parameters.setPreviewSize(sizeList.get(previewSizeCount-1).width, sizeList.get(previewSizeCount-1).height);

        parameters.setAutoWhiteBalanceLock(true);

        List<String> out = mCamera.getParameters().getSupportedFocusModes();
        if(mCamera.getParameters().getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)){
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }

        if(parameters.getSupportedFlashModes().contains(Camera.Parameters.FLASH_MODE_AUTO)){
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
        }
        if(parameters.getSupportedPictureFormats().contains(PixelFormat.JPEG)){
            parameters.setPictureFormat(PixelFormat.JPEG);
        }
        parameters.set("jpeg-quality", 100);

        if(parameters.getMaxNumFocusAreas() > 0){
            List<Camera.Area> areas = new ArrayList<Camera.Area>();
            areas.add(new Camera.Area(new Rect(-200, -200, 400, 400), 1000));
            parameters.setFocusAreas(areas);
        }

        if(parameters.getSupportedSceneModes().contains(Camera.Parameters.SCENE_MODE_AUTO)){
            parameters.setSceneMode(Camera.Parameters.SCENE_MODE_AUTO);
            //Toast.makeText(context, "scene", Toast.LENGTH_SHORT).show();
        }
        if(parameters.getSupportedWhiteBalance().contains(Camera.Parameters.WHITE_BALANCE_AUTO)){
            parameters.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);
            //Toast.makeText(context, "white", Toast.LENGTH_SHORT).show();
        }
        if(parameters.getSupportedColorEffects().contains(Camera.Parameters.EFFECT_NONE)){
            parameters.setColorEffect(Camera.Parameters.EFFECT_NONE);
            //Toast.makeText(context, "color", Toast.LENGTH_SHORT).show();
        }

        mCamera.setParameters(parameters);

    }

    private void releaseCamera(){
        if (mCamera != null){
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Camera.Size size = camera.getParameters().getPreviewSize();
        YuvImage yuv = new YuvImage(data, ImageFormat.NV21, size.width, size.height, null);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        yuv.compressToJpeg(new Rect(0, 0, size.width, size.height), 100, bos);
        byte[] rgbImage = bos.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(rgbImage, 0, rgbImage.length);
        Matrix matrix = new Matrix();
        matrix.setRotate(90);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        
//        Bitmap result = Bitmap.createBitmap(bitmap, RectView.viewLeft, RectView.viewTop,
//                RectView.viewWidth, RectView.viewHeight);
        try {
            saveFile(bitmap, "opencv.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        /*  使用 setPreviewCallbackWithBuffer 需要在两个地方调用  addCallbackBuffer
        byte[] dataa = new byte[38016];
        mCamera.addCallbackBuffer(dataa);*/
    }

    public void saveFile(Bitmap bm, String fileName) throws IOException {
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
        	Log.e("123", "233333");
        }else{
        	Log.e("123", ".......");
        }
    }

}