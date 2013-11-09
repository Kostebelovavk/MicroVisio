package com.cscentr.microvisio.draw;

public class Straight {
	private double a, b, c;

	public double getC() {
		return c;
	}

	public void setC(double c) {
		this.c = c;
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public Straight(double a, double b, double c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

}