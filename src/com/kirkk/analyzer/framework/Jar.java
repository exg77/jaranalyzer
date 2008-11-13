package com.kirkk.analyzer.framework;

import java.util.List;

public interface Jar
{

    public abstract List getAllContainedPackages();
    public abstract List getAllExternallyReferencedPackages();

    public abstract int getPackageCount();
    public abstract int getAbstractClassCount();
    public abstract int getClassCount();
	public abstract String getJarFileName();
    public abstract void addPackage(JarPackage jarPackage);

    public abstract JarPackage getPackage(String packageName);

    public abstract boolean containsPackage(String packageName);
    public abstract List getOutgoingDependencies();
    public abstract List getIncomingDependencies();
    public abstract List getAllUnidentifiableExternallyReferencedPackages();

    public boolean hasCycles();
	public List getCyclicJars();

	public void addOutgoingDependency(Jar jar);
	public void addIncomingDependency(Jar jar);
	public void addCyclicJar(Jar jar);
	public JarMetrics calculateMetrics();
}
