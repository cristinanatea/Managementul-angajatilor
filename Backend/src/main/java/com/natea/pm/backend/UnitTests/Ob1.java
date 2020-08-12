package com.natea.pm.backend.UnitTests;

import org.codehaus.jackson.annotate.JsonCreator;

public class Ob1 {
	private int i;
	private String b;

	@JsonCreator
	public Ob1() {

	}

	public Ob1(int i, String b) {
		this.setI(i);
		this.setB(b);
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	@Override
	public String toString() {
		return "Ob1 [i=" + i + ", b=" + b + "]";
	}

}
