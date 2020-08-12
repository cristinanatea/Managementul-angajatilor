package com.natea.pm.dto.response;

import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.natea.pm.dto.base.BaseResponse;
import com.natea.pm.dto.base.HolidayInfo;
import com.natea.pm.dto.base.LeaveRequestInfo;
import com.natea.pm.dto.base.ProjectInfo;
import com.natea.pm.dto.base.TimeSheetInfo;
import com.natea.pm.dto.base.UserInfo;

public class TimeSheets extends BaseResponse {
	Date startDate;
	Date endDate;

	Map<Integer, ProjectInfo> projects = new HashMap<Integer, ProjectInfo>();
	Map<Integer, UserInfo> users = new HashMap<Integer, UserInfo>();
	Map<Integer, TimeSheetInfo> timesheets = new HashMap<Integer, TimeSheetInfo>();
	Set<HolidayInfo> freeDates = new HashSet<HolidayInfo>();
	Set<LeaveRequestInfo> leaveRequests = new HashSet<LeaveRequestInfo>();

	public Set<LeaveRequestInfo> getLeaveRequests() {
		return leaveRequests;
	}

	public void addLeaveRequest(LeaveRequestInfo leaveRequest) {
		this.leaveRequests.add(leaveRequest);
	}

	public Map<Integer, ProjectInfo> getProjects() {
		return projects;
	}

	public void addProject(Integer projectId, ProjectInfo project) {
		this.projects.put(projectId, project);
	}

	public Set<HolidayInfo> getHolidays() {
		return freeDates;
	}

	public void setFreeDates(Set<HolidayInfo> freeDates) {
		this.freeDates = freeDates;
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

	public Map<Integer, UserInfo> getUsers() {
		return users;
	}

	public void addUser(Integer userId, UserInfo user) {
		this.users.put(userId, user);
	}

	public Map<Integer, TimeSheetInfo> getTimeSheets() {
		return timesheets;
	}

	public void addTimeSheet(Integer entryId, TimeSheetInfo timesheet) {
		this.timesheets.put(entryId, timesheet);
	}

	@Override
	public String toString() {
		return "TimeSheets [startDate=" + startDate + ", endDate=" + endDate + ", projects=" + projects + ", users="
				+ users + ", timesheets=" + timesheets + ", freeDates=" + freeDates + ", leaveRequests=" + leaveRequests
				+ ", toString()=" + super.toString() + "]";
	}
}
