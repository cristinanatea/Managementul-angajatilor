package com.natea.pm.dto.base;

public class OrganizationDetails {
	int userId;
	int projectId;

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

	@Override
	public String toString() {
		return "OrganizationDetails [userId=" + userId + ", projectId=" + projectId + "]";
	}
}
