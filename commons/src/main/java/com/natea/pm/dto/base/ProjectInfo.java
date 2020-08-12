package com.natea.pm.dto.base;

public class ProjectInfo {
	private Integer projectId;
	private String projectName;
	private String description;

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ProjectInfo [projectId=" + projectId + ", projectName=" + projectName + ", description=" + description
				+ "]";
	}
}
