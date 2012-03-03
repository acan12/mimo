package com.mimo.app.model.pojo;

import com.mimo.app.interfaces.IBusiness;
import com.mimo.app.model.BaseModel;

public class Business extends BaseModel implements IBusiness {

	private String bizname;
	private String category;
	private String created;
	private String description;
	private String event;
	private String expire;
	private int follower;
	private double lat;
	private double lng;

	private String id;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the bizname
	 */
	public String getBizname() {
		return bizname;
	}

	/**
	 * @param bizname
	 *            the bizname to set
	 */
	public void setBizname(String bizname) {
		this.bizname = bizname;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the created
	 */
	public String getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(String created) {
		this.created = created;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the event
	 */
	public String getEvent() {
		return event;
	}

	/**
	 * @param event
	 *            the event to set
	 */
	public void setEvent(String event) {
		this.event = event;
	}

	/**
	 * @return the expire
	 */
	public String getExpire() {
		return expire;
	}

	/**
	 * @param expire
	 *            the expire to set
	 */
	public void setExpire(String expire) {
		this.expire = expire;
	}

	/**
	 * @return the follower
	 */
	public int getFollower() {
		return follower;
	}

	/**
	 * @param follower
	 *            the follower to set
	 */
	public void setFollower(int follower) {
		this.follower = follower;
	}

	/**
	 * @return the lat
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * @param lat
	 *            the lat to set
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}

	/**
	 * @return the lng
	 */
	public double getLng() {
		return lng;
	}

	/**
	 * @param lng
	 *            the lng to set
	 */
	public void setLng(double lng) {
		this.lng = lng;
	}

}
