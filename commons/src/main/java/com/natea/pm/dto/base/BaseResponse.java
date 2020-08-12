package com.natea.pm.dto.base;

public class BaseResponse {
	String errorMessage;
	int errorCode;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String toString() {
		return " BaseResponse{ errorCode [" + errorCode + "] errorMessage [" + errorMessage + "] } ";
	}
}
