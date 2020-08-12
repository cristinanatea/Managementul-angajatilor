package com.natea.pm.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.natea.pm.dto.base.BaseResponse;
import com.natea.pm.dto.base.ContractInfo;
import com.natea.pm.dto.base.RoleInfo;

public class Organization extends BaseResponse {
	List<RoleInfo> roles = new ArrayList<RoleInfo>();
	List<ContractInfo> contracts = new ArrayList<ContractInfo>();

	public List<RoleInfo> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleInfo> roles) {
		this.roles = roles;
	}

	public void addRole(RoleInfo role) {
		this.roles.add(role);
	}

	public List<ContractInfo> getContracts() {
		return contracts;
	}

	public void setContracts(List<ContractInfo> contracts) {
		this.contracts = contracts;
	}

	public void addContract(ContractInfo contract) {
		this.contracts.add(contract);
	}

	@Override
	public String toString() {
		return "Organization [roles=" + roles + ", contracts=" + contracts + ", toString()=" + super.toString() + "]";
	}
}
