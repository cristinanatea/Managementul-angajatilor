package com.natea.pm.backend.UnitTests;

public class Ob<SPECIAL_TYPE> {
	private int num;
	private SPECIAL_TYPE data;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public SPECIAL_TYPE getData() {
		return data;
	}

	public void setData(SPECIAL_TYPE data) {
		this.data = data;
	}

	public Ob() {
	}

	@Override
	public String toString() {
		return "Ob [num=" + num + ", data=" + data + "]";
	}

}