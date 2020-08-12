package com.natea.pm.dto.base;

import java.sql.Date;

public class GenericQuery {
	Integer roleId;
	Integer projectId;
	Integer contractId;
	Integer userId;
	Integer requestTypeId;
	Date startDate;
	Date endDate;
	Boolean status;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRequestTypeId() {
		return requestTypeId;
	}

	public void setRequestTypeId(Integer requestTypeId) {
		this.requestTypeId = requestTypeId;
	}

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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "GenericQuery [roleId=" + roleId + ", projectId=" + projectId + ", contractId=" + contractId
				+ ", userId=" + userId + ", requestTypeId=" + requestTypeId + ", startDate=" + startDate + ", endDate="
				+ endDate + ", status=" + status + "]";
	}
}
