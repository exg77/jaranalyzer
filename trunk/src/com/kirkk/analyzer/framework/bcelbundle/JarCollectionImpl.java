package com.kirkk.analyzer.framework.bcelbundle;

import com.kirkk.analyzer.framework.*;
import com.kirkk.analyzer.framework.jar.*;
import java.util.*;
import java.io.*;

public class JarCollectionImpl implements JarCollection {
	private List jars;
	private Iterator jarIterator;

	public JarCollectionImpl(File file) throws Exception {
		this(file, new ArrayList());
	}

	public JarCollectionImpl(File file, List ignorePackages) throws Exception {
		this(file, ignorePackages, new ArrayList());
	}

	public JarCollectionImpl(File file, List ignorePackages, List ignoreJars) throws Exception {
		this.jars = this.getJars(file, ignorePackages, ignoreJars);
		this.jarIterator = this.jars.iterator();
	}

	public int getJarCount() {
		return this.jars.size();
	}

	public boolean hasNext() {
		return this.jarIterator.hasNext();
	}

	public Jar nextJar() {
		return (Jar)this.jarIterator.next();
	}

	public void first() {
		this.jarIterator = jars.iterator();
	}

	public Jar getJar(String jarName) {
		Iterator jarIterator = this.jars.iterator();
		while (jarIterator.hasNext()) {
			Jar jar = (Jar) jarIterator.next();
			if (jar.getJarFileName().equals(jarName)) {
				return jar;
			}
		}
		return null;
	}

	public Jar getJarContainingPackage(String packageName) {
		Iterator jarIterator = this.jars.iterator();
		while (jarIterator.hasNext()) {
			Jar jar = (Jar) jarIterator.next();
			if (jar.containsPackage(packageName)) {
				return jar;
			}
		}
		return null;
	}

	public Jar[] toArray() {
		Jar[] jar = new Jar[jars.size()];
		Iterator jarIterator = jars.iterator();
		int i = 0;
		while (jarIterator.hasNext()) {
			jar[i] = (Jar) jarIterator.next();
			i++;
		}
		return jar;
	}

	private List getJars(File file, List ignorePackages, List ignoreJars) throws Exception {
		if (file.isDirectory()) {
			File files[] = this.getJarFiles(file);
			ArrayList jars = new ArrayList();
			for (int i = 0; i < files.length; i++) {
				JarBuilder jarBuilder = new JarBuilderImpl();
				JarFile jarFile = new JarFile(files[i]);
				Jar jar = jarBuilder.buildJar(jarFile, ignorePackages);
				if ( (jar.getClassCount() > 0) && (ignoreJars.contains(jar.getJarFileName()) == false) ) {
					jars.add(jar);
				}
			}
			return jars;

		} else {
			throw new IOException("File must be a directory");
		}

	}


	private File[] getJarFiles(File file) {
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File file, String fileName) {
				if (fileName.endsWith(".jar")) {
					return true;
				} else {
					return false;
				}
			}
		};

		return file.listFiles(filter);

	}



}