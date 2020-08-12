package com.natea.pm.business;

import com.natea.pm.dto.base.AssignmentDetails;
import com.natea.pm.dto.base.ContractInfo;
import com.natea.pm.dto.base.GenericQuery;
import com.natea.pm.dto.base.HolidayInfo;
import com.natea.pm.dto.base.LeaveRequestInfo;
import com.natea.pm.dto.base.LoginDetails;
import com.natea.pm.dto.base.OrganizationDetails;
import com.natea.pm.dto.base.PassUpdateDetails;
import com.natea.pm.dto.base.ProjectInfo;
import com.natea.pm.dto.base.RoleInfo;
import com.natea.pm.dto.base.TimeSheetDetails;
import com.natea.pm.dto.base.UserInfo;
import com.natea.pm.dto.request.Request;
import com.natea.pm.dto.response.Acknowledge;
import com.natea.pm.dto.response.Credentials;
import com.natea.pm.dto.response.Holidays;
import com.natea.pm.dto.response.Organization;
import com.natea.pm.dto.response.Projects;
import com.natea.pm.dto.response.Requests;
import com.natea.pm.dto.response.TimeSheets;
import com.natea.pm.dto.response.Users;

public interface UsersManagerItf {
	Credentials login(Request<LoginDetails> request);

	Acknowledge logout(Request<Integer> loginInfo);

	Users getUsers(Request<GenericQuery> request);

	TimeSheets getLoggedTime(Request<GenericQuery> request);

	Projects getProjects(Request<GenericQuery> request);

	Requests getLeaveRequests(Request<GenericQuery> request);

	Acknowledge approveLeaveRequest(Request<Integer> id);

	Acknowledge rejectLeaveRequest(Request<Integer> request);

	Acknowledge cancelLeaveRequest(Request<Integer> request);

	Acknowledge registerLeaveRequest(Request<LeaveRequestInfo> request);

	Acknowledge registerTimeSheet(Request<TimeSheetDetails> request);

	Acknowledge updateUserPassword(Request<PassUpdateDetails> details);

	Acknowledge resetUserPassword(Request<Integer> request);

	Acknowledge removeUser(Request<Integer> id);

	Acknowledge removeProject(Request<Integer> id);

	Acknowledge createProject(Request<ProjectInfo> details);

	Acknowledge createUser(Request<UserInfo> details);

	Acknowledge updateUser(Request<UserInfo> request);

	Acknowledge updateProject(Request<ProjectInfo> request);

	Acknowledge assignUserToProject(Request<OrganizationDetails> details);

	Acknowledge removeUserFromProject(Request<OrganizationDetails> details);

	Holidays getHolidays(Request<GenericQuery> request);

	Acknowledge removeHoliday(Request<HolidayInfo> request);

	Acknowledge addHoliday(Request<HolidayInfo> request);

	Organization getOrganizationInfo(Request<Integer> request);

	Acknowledge addRole(Request<RoleInfo> request);

	Acknowledge addContract(Request<ContractInfo> request);

	Acknowledge removeRole(Request<Integer> request);

	Acknowledge removeContract(Request<Integer> request);

	Acknowledge assignUsersToProject(Request<AssignmentDetails> request);

	Acknowledge assignProjectsToUser(Request<AssignmentDetails> request);

}
