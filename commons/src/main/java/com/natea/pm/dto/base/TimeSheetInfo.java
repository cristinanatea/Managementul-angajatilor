package com.natea.pm.dto.base;

import java.sql.Date;

public class TimeSheetInfo {
	private Integer entryId;
	private Date date;
	private Integer timeSpent;
	private Integer userId;
	private Integer projectId;
	private String comment;

	public Integer getEntryId() {
		return entryId;
	}

	public void setEntryId(Integer entryId) {
		this.entryId = entryId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getTimeSpent() {
		return timeSpent;
	}

	public void setTimeSpent(Integer timeSpent) {
		this.timeSpent = timeSpent;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "TimeSheetInfo [entryId=" + entryId + ", date=" + date + ", timeSpent=" + timeSpent + ", userId="
				+ userId + ", projectId=" + projectId + ", comment=" + comment + "]";
	}
}