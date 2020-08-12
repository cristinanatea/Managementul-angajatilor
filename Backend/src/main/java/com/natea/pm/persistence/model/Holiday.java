package com.natea.pm.persistence.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.natea.pm.dto.base.HolidayInfo;

@Entity
@Table(name = "Holidays")
public class Holiday implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Date date;
	private String name;

	public Holiday() {
	}

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

	public HolidayInfo toDTO() {
		HolidayInfo holiday = new HolidayInfo();

		holiday.setDate(date);
		holiday.setName(name);

		return holiday;
	}
}