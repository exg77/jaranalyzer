package com.kirkk.analyzer.jar1.test1;

import com.kirkk.analyzer.jar1.test2.*;

public abstract class Test1 {
	public void nothing() {
		Test2 t = new Test2();
		System.out.println(t.nothing());

		Interface1 i = new Interface1() {
			public void nothing() { ; }
		};
	}
}