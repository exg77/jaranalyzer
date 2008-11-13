package com.kirkk.analyzer.jar1.test1;

import com.kirkk.analyzer.jar1.test2.*;
import com.kirkk.analyzer.jar2.service.*;

public class Test1 {
	public void nothing() {
		Test2 t = new Test2();
		System.out.println(t.nothing());

		Jar2Service service = new Jar2Service();
		System.out.println(service.nothing());

	}
}