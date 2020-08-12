package com.natea.pm.dto.response;

import java.util.HashMap;
import java.util.Map;

import com.natea.pm.dto.base.BaseResponse;
import com.natea.pm.dto.base.LeaveRequestInfo;
import com.natea.pm.dto.base.UserInfo;

public class Requests extends BaseResponse {
	Map<Integer, LeaveRequestInfo> leaveRequests = new HashMap<Integer, LeaveRequestInfo>();
	Map<Integer, UserInfo> users = new HashMap<Integer, UserInfo>();

	public Map<Integer, LeaveRequestInfo> getLeaveRequests() {
		return leaveRequests;
	}

	public void addLeaveRequest(Integer requestId, LeaveRequestInfo leaveRequest) {
		this.leaveRequests.put(requestId, leaveRequest);
	}

	public Map<Integer, UserInfo> getUsers() {
		return users;
	}

	public void addUser(Integer userId, UserInfo user) {
		this.users.put(userId, user);
	}

	@Override
	public String toString() {
		return "Requests [leaveRequests=" + leaveRequests + ", users=" + users + ", toString()=" + super.toString()
				+ "]";
	}
}
