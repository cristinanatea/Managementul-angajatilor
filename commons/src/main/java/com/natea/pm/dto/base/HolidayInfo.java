package com.natea.pm.dto.base;

import java.sql.Date;

public class HolidayInfo {
	private Date date;
	private String name;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "HolidayInfo [date=" + date + ", name=" + name + "]";
	}
}