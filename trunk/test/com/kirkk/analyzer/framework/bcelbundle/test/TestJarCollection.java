package com.kirkk.analyzer.framework.bcelbundle.test;

import junit.framework.*;
import java.io.*;
import com.kirkk.analyzer.framework.bcelbundle.*;
import com.kirkk.analyzer.framework.*;
import com.kirkk.analyzer.framework.JarCollection;
import java.util.*;

public class TestJarCollection extends TestCase {
	public void testBuildJarCollection() throws Exception {
		JarCollection jarCollection = new JarCollectionImpl(new File("testdata/acyclictestproject/dist"));
		//List jars = jarCollection.getJars(new File("test/acyclictestproject/dist"), new ArrayList());
		assertEquals(jarCollection.getJarCount(),2);

	}

	public void testFindPackage() throws Exception {
		JarCollection jarCollection = new JarCollectionImpl(new File("testdata/acyclictestproject/dist"));
		Jar jar = jarCollection.getJarContainingPackage("com.kirkk.analyzer.jar1.test1");
		assertEquals(jar.getJarFileName(), "Test1.jar");
	}


	/*public void testNoIgnoredJars() throws Exception {

	}

	public void testIgnoredPackages() throws Exception {

	}*/

	public void testIgnoredJars() throws Exception {
		List ignoredJars = new ArrayList();
		ignoredJars.add("Test2.jar");
		JarCollection jarCollection = new JarCollectionImpl(new File("testdata/acyclictestproject/dist"), new ArrayList(), ignoredJars);
		//List jars = jarCollection.getJars(new File("test/acyclictestproject/dist"), new ArrayList());
		assertEquals("Should ignore Jar2 - count should be 1", 1, jarCollection.getJarCount());

		assertNull("Collection should not contain Test2.jar", jarCollection.getJar("Test2.jar"));
	}

	public void testToArray() throws Exception {
		JarCollection jarCollection = new JarCollectionImpl(new File("testdata/acyclictestproject/dist"));
		Jar[] jar = jarCollection.toArray();
		assertEquals("Array size should be 2", 2, jar.length);

		assertTrue( (jar[0].getJarFileName().equals("Test1.jar")) || (jar[0].getJarFileName().equals("Test2.jar")));
	}
}