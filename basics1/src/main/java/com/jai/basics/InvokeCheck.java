package com.jai.basics;

public class InvokeCheck {

	public void meth1() {
		System.out.println("simple meth1");
	}

	public String returnString() {
		return "returing....";
	}

	public int add(String x, String y) {
		System.out.println(x + "," + y);
		System.out.println(Integer.parseInt(x) + Integer.parseInt(y));
		int sum = Integer.parseInt(x) + Integer.parseInt(y);
		return sum;
	}
	
	public void exc() throws Exception {
		throw new Exception("test exception");
	}
}
