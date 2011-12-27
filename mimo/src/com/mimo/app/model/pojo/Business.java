package com.mimo.app.model.pojo;

import com.mimo.app.interfaces.IBusiness;
import com.mimo.app.model.BaseModel;

public class Business extends BaseModel implements IBusiness {

	private String bizname;
	private int count;
	private String description;
	private String created_at;
	private String icon;
	
	
	/**
	 * @return the bizname
	 */
	public String getBizname() {
		return bizname;
	}
	/**
	 * @param bizname the bizname to set
	 */
	public void setBizname(String bizname) {
		this.bizname = bizname;
	}
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the created_at
	 */
	public String getCreated_at() {
		return created_at;
	}
	/**
	 * @param created_at the created_at to set
	 */
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}
	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
}
