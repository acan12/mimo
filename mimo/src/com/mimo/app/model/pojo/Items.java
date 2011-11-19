package com.mimo.app.model.pojo;

import java.util.ArrayList;

public class Items {
	
	private String itemId;
	private String itemName;
    private String itemStatus;
    private int drawableImage;
	private ArrayList<Items> items = null;
   
    
    public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
    public int getDrawableImage() {
		return drawableImage;
	}
	public void setDrawableImage(int drawableImage) {
		this.drawableImage = drawableImage;
	}
	public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public String getItemStatus() {
        return itemStatus;
    }
    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }
}