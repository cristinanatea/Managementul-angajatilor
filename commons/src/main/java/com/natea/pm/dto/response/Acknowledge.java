package com.natea.pm.dto.response;

import com.natea.pm.dto.base.BaseResponse;

public class Acknowledge extends BaseResponse {
	Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Acknowledge [id=" + id + ", toString()=" + super.toString() + "]";
	}
}
