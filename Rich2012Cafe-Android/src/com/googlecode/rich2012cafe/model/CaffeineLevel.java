package com.googlecode.rich2012cafe.model;

import java.util.Date;

public class CaffeineLevel {

	private Date time;
	private int level;
	
	public CaffeineLevel(Date time, int level) {
		super();
		this.time = time;
		this.level = level;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
}
