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

import com.natea.pm.dto.base.LeaveRequestInfo;

@Entity
@Table(name = "LeaveRequests")
public class LeaveRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int requestId;
	private int userId;
	private Date dateStart;
	private Date dateEnd;
	private int nbWorkDays;
	private String comment;
	private int status;

	public LeaveRequest() {
	}

	public LeaveRequest(LeaveRequestInfo details) {
		comment = details.getComment();
		dateEnd = details.getDateEnd();
		dateStart = details.getDateStart();
		userId = details.getUserId();
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId", insertable = false, updatable = false)
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getNbWorkDays() {
		return nbWorkDays;
	}

	public void setNbWorkDays(int nbWorkDays) {
		this.nbWorkDays = nbWorkDays;
	}

	public LeaveRequestInfo toDTO() {
		LeaveRequestInfo leaveRequest = new LeaveRequestInfo();
		leaveRequest.setComment(comment);
		leaveRequest.setDateEnd(dateEnd);
		leaveRequest.setDateStart(dateStart);
		leaveRequest.setStatus(status);
		leaveRequest.setUserId(userId);
		leaveRequest.setRequestId(requestId);

		return leaveRequest;
	}
}