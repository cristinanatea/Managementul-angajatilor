package com.natea.pm.persistence.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.natea.pm.dto.base.TimeSheetInfo;

@Entity
@Table(name = "TimeSheets")
public class TimeSheet implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int entryId;
	private Date date;
	private int timeSpent;
	private int userId;
	private int projectId;
	private String comment;

	public TimeSheet() {
	}

	public TimeSheet(int userId, TimeSheetInfo entry) {
		this.date = entry.getDate();
		this.timeSpent = entry.getTimeSpent();
		this.userId = userId;
		this.projectId = entry.getProjectId();
		this.comment = "";
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "projectId", insertable = false, updatable = false)
	private Project project;

	public Project getProject() {
		return project;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId", insertable = false, updatable = false)
	private User user;

	public User getUser() {
		return user;
	}

	public int getEntryId() {
		return entryId;
	}

	public void setEntryId(int entryId) {
		this.entryId = entryId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getTimeSpent() {
		return timeSpent;
	}

	public void setTimeSpent(int timeSpent) {
		this.timeSpent = timeSpent;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public TimeSheetInfo toDTO() {
		TimeSheetInfo timeSheetInfo = new TimeSheetInfo();

		timeSheetInfo.setDate(date);
		timeSheetInfo.setTimeSpent(timeSpent);
		timeSheetInfo.setUserId(userId);
		timeSheetInfo.setProjectId(projectId);
		timeSheetInfo.setComment(comment);

		return timeSheetInfo;
	}
}