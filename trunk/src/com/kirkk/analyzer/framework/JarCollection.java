package com.kirkk.analyzer.framework;

import java.util.*;
import java.io.*;

public interface JarCollection {
	//public List getJars(File file, List ignorePackages) throws Exception;
	public int getJarCount();
	public boolean hasNext();
	public Jar nextJar();
	public Jar getJarContainingPackage(String packageName);
	public void first();
	public Jar getJar(String jarName);

	public Jar[] toArray();
}