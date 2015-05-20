package com.yhd.gridcamera.view;

public class Point {

	private int x;
	private int y;
	private int itemWidth;
	private int itemHeight;
	private int indexX;
	private int indexY;
	
	public Point(int x, int y, int itemWidth, int itemHeight, int indexX, int indexY) {
		super();
		this.x = x;
		this.y = y;
		this.itemWidth = itemWidth;
		this.itemHeight = itemHeight;
		this.indexX = indexX;
		this.indexY = indexY;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getItemWidth() {
		return itemWidth;
	}
	public void setItemWidth(int itemWidth) {
		this.itemWidth = itemWidth;
	}
	public int getItemHeight() {
		return itemHeight;
	}
	public void setItemHeight(int itemHeight) {
		this.itemHeight = itemHeight;
	}
	
}
