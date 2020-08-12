package com.natea.pm.dto.response;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.natea.pm.dto.base.BaseResponse;
import com.natea.pm.dto.base.ProjectInfo;
import com.natea.pm.dto.base.UserInfo;

public class Users extends BaseResponse {
	Map<Integer, UserInfo> users = new HashMap<Integer, UserInfo>();
	Map<Integer, ProjectInfo> projects = new HashMap<Integer, ProjectInfo>();
	Map<Integer, Set<Integer>> projectsAssignedToUser = new HashMap<Integer, Set<Integer>>();

	public Map<Integer, UserInfo> getUsers() {
		return users;
	}

	public void addUser(Integer userId, UserInfo user) {
		this.users.put(userId, user);
	}

	public Map<Integer, ProjectInfo> getProjects() {
		return projects;
	}

	public void addProject(Integer projectId, ProjectInfo project) {
		this.projects.put(projectId, project);
	}

	public Map<Integer, Set<Integer>> getProjectsAssignedToUser() {
		return projectsAssignedToUser;
	}

	public void addProjectsMapping(Integer userId, Set<Integer> projectsIds) {
		this.projectsAssignedToUser.put(userId, projectsIds);
	}

	@Override
	public String toString() {
		return "Users [users=" + users + ", projects=" + projects + ", projectsAssignedToUser=" + projectsAssignedToUser
				+ ", toString()=" + super.toString() + "]";
	}
}
