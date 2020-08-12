package com.natea.pm.backend.UnitTests;

import org.codehaus.jackson.annotate.JsonCreator;

public class Ob2 {
	private double a;

	@JsonCreator
	public Ob2() {

	}

	public Ob2(double i) {
		this.setA(i);
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	@Override
	public String toString() {
		return "Ob2 [a=" + a + "]";
	}
}