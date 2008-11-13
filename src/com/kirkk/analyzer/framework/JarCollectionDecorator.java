package com.kirkk.analyzer.framework;

public abstract class JarCollectionDecorator implements JarCollection {
	protected JarCollection jarCollection;

	public JarCollectionDecorator(JarCollection jarCollection) {
		this.jarCollection = jarCollection;
	}

	public int getJarCount() {
		return this.jarCollection.getJarCount();
	}

	public boolean hasNext() {
		return this.jarCollection.hasNext();
	}

	public Jar nextJar() {
		return this.jarCollection.nextJar();
	}

	public Jar getJarContainingPackage(String packageName) {
		return this.jarCollection.getJarContainingPackage(packageName);
	}

	public void first() {
		this.jarCollection.first();
	}

	public Jar getJar(String jarName) {
		return this.jarCollection.getJar(jarName);
	}

	public Jar[] toArray() {
		return this.jarCollection.toArray();
	}

}