package com.kirkk.analyzer.framework.bcel.test;

import com.kirkk.analyzer.framework.jar.*;
import junit.framework.*;
import org.apache.bcel.classfile.*;
import com.kirkk.analyzer.framework.bcel.*;
import com.kirkk.analyzer.framework.bcelbundle.*;

public class TestClassParsing extends TestCase {

	public TestClassParsing (String name) {
		super(name);
	}

	public void testGetClassParsing() throws Exception {
		String file = "testdata/acyclictestproject/dist/Test2.jar";
		String className = "com/kirkk/analyzer/jar2/test3/Test3.class";
		JavaClass javaClass = BCELClassFactory.getClass(file, className);
		assertTrue(javaClass.getClassName().equals("com.kirkk.analyzer.jar2.test3.Test3"));
    }


}