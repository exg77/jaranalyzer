package com.kirkk.analyzer.framework.jar.test;

import com.kirkk.analyzer.framework.jar.*;
import junit.framework.*;
import java.io.*;

public class TestJarFile extends TestCase {

	public TestJarFile(String name) {
		super(name);
	}

	public void testJarFile() throws Exception {
		File f = new File("test/TestFile.zip");
		JarFile jar = new JarFile(f);
		assertTrue(jar.getFileName() != null);
		assertTrue(jar.hasMoreClasses() == true);
		assertTrue(jar.nextClass().equals("Test.class"));
		jar.close();
	}

	public void testIgnoreNonJavaFiles() throws Exception {
		File f = new File("test/TestFile.zip");
		JarFile jar = new JarFile(f);
		int i = 0;
		while (jar.hasMoreClasses()) {
			i++;
		}
		assertTrue(i == 1);
		jar.close();
	}

	public void testShortFileName() throws Exception {
		File f = new File("test/TestFile.zip");
		JarFile jar = new JarFile(f);
		assertTrue(jar.getShortFileName().equals("TestFile.zip"));
		jar.close();
	}
}