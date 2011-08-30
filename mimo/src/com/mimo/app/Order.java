package com.mimo.app;

public class Order {
	   
    private String orderName;
    private String orderStatus;
    private int drawableImage;
   
    public int getDrawableImage() {
		return drawableImage;
	}
	public void setDrawableImage(int drawableImage) {
		this.drawableImage = drawableImage;
	}
	public String getOrderName() {
        return orderName;
    }
    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }
    public String getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}