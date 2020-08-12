package com.natea.pm.dto.request;

import com.natea.pm.dto.base.CredentialsInfo;

public class Request<ENTRY> implements RequestItf {
	CredentialsInfo credentials = new CredentialsInfo();

	private ENTRY data;

	public Request() {
	}

	public Request(CredentialsInfo credentials) {
		this.credentials = credentials;
	}

	public Request(CredentialsInfo credentials, ENTRY data) {
		this.credentials = credentials;
		this.data = data;
	}

	public CredentialsInfo getCredentials() {
		return credentials;
	}

	public void setCredentials(CredentialsInfo credentials) {
		this.credentials = credentials;
	}

	public ENTRY getData() {
		return data;
	}

	public void setData(ENTRY data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Request [credentials=" + credentials + ", data=" + data + "]";
	}
}
