package com.natea.pm.dto.response;

import com.natea.pm.dto.base.BaseResponse;
import com.natea.pm.dto.base.CredentialsInfo;
import com.natea.pm.dto.base.RoleInfo;
import com.natea.pm.dto.base.UserInfo;

public class Credentials extends BaseResponse {
	CredentialsInfo crededentials;

	RoleInfo rights;
	UserInfo user;

	public CredentialsInfo getCrededentials() {
		return crededentials;
	}

	public void setCrededentials(CredentialsInfo crededentials) {
		this.crededentials = crededentials;
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	public RoleInfo getRights() {
		return rights;
	}

	public void setRights(RoleInfo rights) {
		this.rights = rights;
	}

	public Credentials() {
	}

	@Override
	public String toString() {
		return "Credentials [crededentials=" + crededentials + ", rights=" + rights + ", user=" + user + ", toString()="
				+ super.toString() + "]";
	}
}
