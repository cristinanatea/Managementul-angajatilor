package com.natea.pm.dto.response;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.natea.pm.dto.base.BaseResponse;
import com.natea.pm.dto.base.HolidayInfo;

public class Holidays extends BaseResponse {
	Date startDate;
	Date endDate;

	Set<HolidayInfo> holidays = new HashSet<HolidayInfo>();

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Set<HolidayInfo> getHolidays() {
		return holidays;
	}

	public void setHolidays(Set<HolidayInfo> holidays) {
		this.holidays = holidays;
	}

	public void addHoliday(HolidayInfo dto) {
		holidays.add(dto);
	}

	@Override
	public String toString() {
		return "Holidays [startDate=" + startDate + ", endDate=" + endDate + ", holidays=" + holidays + ", toString()="
				+ super.toString() + "]";
	}
}
