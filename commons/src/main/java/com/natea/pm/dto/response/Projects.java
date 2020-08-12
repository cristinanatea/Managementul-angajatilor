package com.natea.pm.dto.response;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.natea.pm.dto.base.BaseResponse;
import com.natea.pm.dto.base.ProjectInfo;
import com.natea.pm.dto.base.UserInfo;

@JsonIgnoreProperties
public class Projects extends BaseResponse {
	Map<Integer, UserInfo> users = new HashMap<Integer, UserInfo>();
	Map<Integer, ProjectInfo> projects = new HashMap<Integer, ProjectInfo>();
	Map<Integer, Set<Integer>> usersAssignedToProject = new HashMap<Integer, Set<Integer>>();

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

	public Map<Integer, Set<Integer>> getUsersAssignedToProject() {
		return usersAssignedToProject;
	}

	public void addUsersMapping(Integer projectId, Set<Integer> userIds) {
		this.usersAssignedToProject.put(projectId, userIds);
	}

	@Override
	public String toString() {
		return "Projects [users=" + users + ", projects=" + projects + ", usersAssignedToProject="
				+ usersAssignedToProject + ", toString()=" + super.toString() + "]";
	}
}
