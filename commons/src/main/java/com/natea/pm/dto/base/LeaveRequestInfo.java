package com.natea.pm.dto.base;

import java.sql.Date;

public class LeaveRequestInfo {
	private Integer requestId;
	private Integer userId;
	private Date dateStart;
	private Date dateEnd;
	private Integer nbWorkDays;
	private String comment;
	private Integer status;

	public Integer getRequestId() {
		return requestId;
	}

	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public Integer getNbWorkDays() {
		return nbWorkDays;
	}

	public void setNbWorkDays(Integer nbWorkDays) {
		this.nbWorkDays = nbWorkDays;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "LeaveRequestInfo [requestId=" + requestId + ", userId=" + userId + ", dateStart=" + dateStart
				+ ", dateEnd=" + dateEnd + ", nbWorkDays=" + nbWorkDays + ", comment=" + comment + ", status=" + status
				+ "]";
	}
}