package com.natea.pm.persistence.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.natea.pm.dto.base.ProjectInfo;

@Entity
@Table(name = "Projects")
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private int projectId;
	@Column(unique = true, nullable = false)
	private String projectName;
	private String description;

	public Project() {
	}

	public Project(String projectName, String description, User supervisor) {
		this.projectName = projectName;
		this.description = description;
	}

	public Project(ProjectInfo details) {
		this.projectName = details.getProjectName();
		this.description = details.getDescription();
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "project")
	private Set<TimeSheet> timesheets = new HashSet<TimeSheet>();

	public Set<TimeSheet> getTimesheets() {
		return timesheets;
	}

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "projects")
	private Set<User> users = new HashSet<>();

	public Set<User> getUsers() {
		return users;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
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

	public ProjectInfo toDTO() {
		ProjectInfo projectInfo = new ProjectInfo();

		projectInfo.setProjectName(projectName);
		projectInfo.setDescription(description);

		return projectInfo;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public void clearUsers() {
		this.users.clear();
	}
}
