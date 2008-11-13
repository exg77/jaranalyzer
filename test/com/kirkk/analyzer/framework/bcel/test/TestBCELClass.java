package com.kirkk.analyzer.framework.bcel.test;

import com.kirkk.analyzer.framework.jar.*;
import junit.framework.*;
import com.kirkk.analyzer.framework.bcel.*;
import com.kirkk.analyzer.framework.bcelbundle.*;
import java.util.*;

public class TestBCELClass extends TestCase {

	public TestBCELClass (String name) {
		super(name);
	}

	public void testGetClassParsing() throws Exception {
		String file = "testdata/acyclictestproject/dist/Test2.jar";
		String className = "com/kirkk/analyzer/jar2/test3/Test3.class";
		BCELPublicClass javaClass = new BCELPublicClass(file, className);
		assertTrue(javaClass.getLongName().equals("com.kirkk.analyzer.jar2.test3.Test3"));
    }

    public void testWithNoIgnoredPackages() throws Exception {
		String file = "testdata/acyclictestproject/dist/Test2.jar";
		String className = "com/kirkk/analyzer/jar2/test3/Test3.class";
		BCELPublicClass javaClass = new BCELPublicClass(file, className);
		List list = javaClass.getImportedPackageNames();
		assertTrue("Should contain java.lang", list.contains("java.lang"));
		assertTrue("Should contain com.kirkk.analyzer.jar1.test2", list.contains("com.kirkk.analyzer.jar1.test2"));

	}

	public void testWithIgnoredPackages() throws Exception {
		List ignore = new ArrayList();
		ignore.add("java.lang");

		String file = "testdata/acyclictestproject/dist/Test2.jar";
		String className = "com/kirkk/analyzer/jar2/test3/Test3.class";
		BCELPublicClass javaClass = new BCELPublicClass(file, className, ignore);
		List list = javaClass.getImportedPackageNames();
		assertTrue("Should ignore java.lang", !list.contains("java.lang"));
		assertTrue("Should contain com.kirkk.analyzer.jar1.test2", list.contains("com.kirkk.analyzer.jar1.test2"));
	}

    public void testClassWithInlineImports() throws Exception {
		String file = "testdata/acyclictestproject/dist/Test2.jar";
		String className = "com/kirkk/analyzer/jar2/test3/Test3.class";
		BCELPublicClass javaClass = new BCELPublicClass(file, className);
		List list = javaClass.getImportedPackageNames();
		assertTrue("Should contain java.util", list.contains("java.util"));
	}

    //public void testClassWithNoImports



}