package com.kirkk.analyzer.framework.bcelbundle.test;

import com.kirkk.analyzer.framework.jar.*;
import junit.framework.*;
import com.kirkk.analyzer.framework.bcel.*;
import com.kirkk.analyzer.framework.*;
import com.kirkk.analyzer.framework.bcelbundle.*;
import java.util.*;
import java.io.*;

public class TestJarBuilderImpl extends TestCase {

	public void testBuildJarBundle() throws Exception {
		JarBuilder jarBuilder = new JarBuilderImpl();
		String file = "testdata/acyclictestproject/dist/Test1.jar";
		Jar jar = jarBuilder.buildJar(new JarFile(new File(file)), new ArrayList());
		assertTrue(jar.getJarFileName().equals("Test1.jar"));
	}

	public void testStructureValidity() throws Exception {
		JarBuilder jarBuilder = new JarBuilderImpl();
		String file = "testdata/acyclictestproject/dist/Test1.jar";
		Jar jar = jarBuilder.buildJar(new JarFile(new File(file)), new ArrayList());
		//assertEquals(jar.getLevel(), 1);
		assertEquals(jar.getPackageCount(), 2);
		assertEquals(jar.getClassCount(), 5);
		assertTrue(jar.containsPackage("com.kirkk.analyzer.jar1.test1"));

		/*jar.getAllContainedPackages();
		jar.getAllExternallyReferencedPackages();

		jar.getDependentJars();
		jar.getAllUnidentifiableExternallyReferencedPackages();

		jar.hasCycles();
		jar.getCyclicJars();*/
	}

	public void testIgnorePackages() throws Exception {
		List ignorePackages = new ArrayList();
		ignorePackages.add("java.util");
		JarBuilder jarBuilder = new JarBuilderImpl();
		String file = "testdata/acyclictestproject/dist/Test2.jar";
		Jar jar = jarBuilder.buildJar(new JarFile(new File(file)), ignorePackages);

		assertTrue("Should ignore java.util", !jar.getAllExternallyReferencedPackages().contains("java.util"));
	}

	public void testClassCount() throws Exception {
		JarBuilder jarBuilder = new JarBuilderImpl();
		String file = "testdata/acyclictestproject/dist/Test1.jar";
		Jar jar = jarBuilder.buildJar(new JarFile(new File(file)), new ArrayList());

		assertEquals("Class count should be 5", 5, jar.getClassCount());
	}

	public void testAbstractClassCount() throws Exception {
		JarBuilder jarBuilder = new JarBuilderImpl();
		String file = "testdata/acyclictestproject/dist/Test1.jar";
		Jar jar = jarBuilder.buildJar(new JarFile(new File(file)), new ArrayList());

		assertEquals("Abstract class count should be 2", 2, jar.getAbstractClassCount());
	}
	//public void testInvalidJarFile
	//public void testEmptyJarFile

}