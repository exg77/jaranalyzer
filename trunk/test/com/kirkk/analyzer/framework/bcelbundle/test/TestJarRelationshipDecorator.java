package com.kirkk.analyzer.framework.bcelbundle.test;

import junit.framework.*;
import java.io.*;
import com.kirkk.analyzer.framework.bcelbundle.*;
import com.kirkk.analyzer.framework.*;
import com.kirkk.analyzer.framework.JarCollection;
import java.util.*;

public class TestJarRelationshipDecorator extends TestCase {
	public void testOutgoingJarRelationships() throws Exception {
		JarCollection jarCollection = new JarRelationshipDecorator(new JarCollectionImpl(new File("testdata/acyclictestproject/dist"), new ArrayList()));
		assertEquals(jarCollection.getJarCount(),2);
		jarCollection.first();

		Jar jar1 = jarCollection.getJar("Test1.jar");
		Jar jar2 = jarCollection.getJar("Test2.jar");
		assertNotNull("Jar1 is null", jar1);
		assertNotNull("Jar2 is null", jar2);

		List jar2OutgoingDependencies = jar2.getOutgoingDependencies();
		List jar1OutgoingDependencis = jar1.getOutgoingDependencies();
		assertTrue("jar2 should be dependent on jar1", jar2OutgoingDependencies.contains(jar1));
		assertTrue("jar1 should not be dependent on jar2", !jar1OutgoingDependencis.contains(jar2));

	}

	public void testIncomingJarRelationships() throws Exception {
		JarCollection jarCollection = new JarRelationshipDecorator(new JarCollectionImpl(new File("testdata/acyclictestproject/dist"), new ArrayList()));
		assertEquals(jarCollection.getJarCount(),2);
		jarCollection.first();

		Jar jar1 = jarCollection.getJar("Test1.jar");
		Jar jar2 = jarCollection.getJar("Test2.jar");
		assertNotNull("Jar1 is null", jar1);
		assertNotNull("Jar2 is null", jar2);

		List jar2IncomingDependencies = jar2.getIncomingDependencies();
		List jar1IncomingDependencis = jar1.getIncomingDependencies();
		assertTrue("jar2 should not have any incoming dependncies", !jar2IncomingDependencies.contains(jar1));
		assertTrue("jar1 should have jar2 as an incoming dependency", jar1IncomingDependencis.contains(jar2));

	}

	public void testCylicJars() throws Exception {
		JarCollection jarCollection = new JarRelationshipDecorator(new JarCollectionImpl(new File("testdata/cyclictestproject/dist"), new ArrayList()));
		assertEquals(jarCollection.getJarCount(),2);
		jarCollection.first();

		Jar jar1 = jarCollection.getJar("Test1.jar");
		Jar jar2 = jarCollection.getJar("Test2.jar");
		assertNotNull("Jar1 is null", jar1);
		assertNotNull("Jar2 is null", jar2);

		assertTrue("Jar1 should have a cycle", jar1.hasCycles());
		assertTrue("Jar2 should have a cycle", jar2.hasCycles());

		List jar1Cycles = jar1.getCyclicJars();
		assertTrue("Jar1 should have a cycle with Jar2", jar1Cycles.contains(jar2));

		List jar2Cycles = jar2.getCyclicJars();
		assertTrue("Jar2 should have a cycle with Jar1", jar2Cycles.contains(jar1));
	}

	public void testDecoratorToArray() throws Exception {
		JarCollection jarCollection = new JarRelationshipDecorator(new JarCollectionImpl(new File("testdata/acyclictestproject/dist")));
		Jar[] jar = jarCollection.toArray();
		assertEquals("Array size should be 2", 2, jar.length);

		assertTrue( (jar[0].getJarFileName().equals("Test1.jar")) || (jar[0].getJarFileName().equals("Test2.jar")));
	}

	/*public void testDupPackageSpanningJars() throws Exception {
		JarCollection jarCollection = new JarRelationshipDecorator(new JarCollectionImpl(new File("testdata/samepackagespansjars/dist")));

		Jar jar1 = jarCollection.getJar("Test1.jar");
		Jar jar2 = jarCollection.getJar("Test2.jar");

		System.out.println(jar1.hasCycles());
		System.out.println(jar2.hasCycles());
	}*/
}