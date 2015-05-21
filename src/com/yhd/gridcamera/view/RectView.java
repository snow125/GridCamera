package com.yhd.gridcamera.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Think on 2015/4/28.
 */
public class RectView extends View {

    private final static float TOP_RATE = 0.1F;
    private final static float LEFT_RATE = 0.1F;
    private final static float WIDTH_RATE = 0.8F;
    private final static float HEIGHT_RATE = 0.3F;

    private int screenWidth;
    private int screenHeight;
    private Paint p = new Paint();
    public static int viewTop;
    public static int viewLeft;
    public static int viewWidth;
    public static int viewHeight;
    
    private int colNum = 3;
    private int rowNum = 4;
    private int itemWidth;
    private int itemHeight;
    
    private int indexX = -1;
    private int indexY;

    public RectView(Context context) {
        super(context);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeight = wm.getDefaultDisplay().getHeight();
        viewTop = (int)(screenHeight *TOP_RATE);
        viewLeft = (int)(screenWidth * LEFT_RATE);
        viewWidth = (int)(screenWidth * WIDTH_RATE);
        viewHeight = (int)(screenHeight * HEIGHT_RATE);
        itemWidth = viewWidth/(colNum+1);
        itemHeight = viewHeight/(rowNum+1);
    }

    public RectView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        p.setARGB(0xc1, 0xc1, 0xc1, 0xc1);
        canvas.drawRect(0, 0, screenWidth, screenHeight, p);
        p.setARGB(0xff, 0xff, 0xff, 0xff);
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawRect(viewLeft, viewTop,
                viewWidth+viewLeft, viewHeight+viewTop, p);
        p.setARGB(0xff, 0x00, 0x00, 0x00);
        //col
        for (int i = 1; i <= colNum; i++) {
        	canvas.drawLine(viewLeft+i*itemWidth, viewTop, 
        			viewLeft+i*itemWidth, viewHeight+viewTop, p);
		}
        //row
        for (int i = 1; i <= rowNum; i++) {
        	canvas.drawLine(viewLeft, viewTop+i*itemHeight, 
        			viewLeft+viewWidth, viewTop+i*itemHeight, p);
		}
    }
    
    public Point getNextColPoint(){
    	indexX++;
    	if(indexX > colNum){
    		indexX = 0;
    		indexY++;
    	}
    	if(indexY > rowNum){
    		return null;
    	}
		return new Point(viewLeft+indexX*itemWidth, viewTop+indexY*itemHeight, 
				itemWidth, itemHeight,
				indexX, indexY);
    }
    
    public synchronized Point getNextPoint(int x, int y){
		return new Point(viewLeft+x*itemWidth, viewTop+y*itemHeight, 
				itemWidth, itemHeight,
				x, y);
    }
    
    public int getColNum() {
		return colNum;
	}

	public int getRowNum() {
		return rowNum;
	}

	public int getPointCount(){
    	return (colNum+1) * (rowNum+1);
    }
}
