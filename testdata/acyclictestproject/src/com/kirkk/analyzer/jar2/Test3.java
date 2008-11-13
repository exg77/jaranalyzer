package com.kirkk.analyzer.jar2.test3;

import com.kirkk.analyzer.jar1.test2.*;

public class Test3 {
	public void doNothing() {
		System.out.println(new Test2().nothing());
	}

	public java.util.List getAll() {
		java.util.List list = new java.util.ArrayList();
		return list;
	}
}