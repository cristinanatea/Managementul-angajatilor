package com.natea.pm.dto.base;

import java.util.HashSet;
import java.util.Set;

public class TimeSheetDetails {
	int userId;
	Set<TimeSheetInfo> timeEntries = new HashSet<TimeSheetInfo>();

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Set<TimeSheetInfo> getTimeEntries() {
		return timeEntries;
	}

	public void setTimeEntries(Set<TimeSheetInfo> timeEntries) {
		this.timeEntries = timeEntries;
	}

	public void addTimeEntry(TimeSheetInfo entry) {
		this.timeEntries.add(entry);
	}

	@Override
	public String toString() {
		return "TimeSheetDetails [userId=" + userId + ", timeEntries=" + timeEntries + "]";
	}
}
