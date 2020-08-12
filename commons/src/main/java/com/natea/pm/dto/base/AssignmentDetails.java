package com.natea.pm.dto.base;

import java.util.HashSet;
import java.util.Set;

public class AssignmentDetails {
	private int id;
	private Set<Integer> referenceIds = new HashSet<Integer>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<Integer> getReferenceIds() {
		return referenceIds;
	}

	public void addReferenceIds(Integer referenceId) {
		this.referenceIds.add(referenceId);
	}

	@Override
	public String toString() {
		return "AssignmentDetails [id=" + id + ", referenceIds=" + referenceIds + "]";
	}
}
