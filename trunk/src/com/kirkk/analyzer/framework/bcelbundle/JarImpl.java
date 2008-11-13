package com.kirkk.analyzer.framework.bcelbundle;

import com.kirkk.analyzer.framework.*;
import java.util.*;
import java.math.BigDecimal;

public class JarImpl implements Jar {

    public List packages = new ArrayList();
    private List dependentJars = new ArrayList();
    private List incomingDependencies = new ArrayList();
    private List cyclicJars = new ArrayList();
    private boolean containsCycles = false;
    private String jarFileName;

	public JarImpl(String jarFileName) {
		this.jarFileName = jarFileName;
	}

    public List getAllContainedPackages() {
        return packages;
    }

	public void addOutgoingDependency(Jar jar) {
		if (!this.dependentJars.contains(jar)) {
			this.dependentJars.add(jar);
		}

		//Test for cyclic dependencies.
		if (jar.getOutgoingDependencies().contains(this)) {
			//May need to put a conditional here to make sure we only add a cyclic jar if it hasn't already been added.
			if (!this.cyclicJars.contains(jar)) {
				jar.addCyclicJar(this);
				this.addCyclicJar(jar);
			}
		}

		//When adding a dependent jar, setup the relationship so a jar knows of it's incoming dependencies too.
		if (!jar.getIncomingDependencies().contains(this)) {
			jar.addIncomingDependency(this);
		}
	}

	public void addIncomingDependency(Jar jar) {
		this.incomingDependencies.add(jar);
	}

	public void addCyclicJar(Jar jar) {
		if (this.cyclicJars == null) {
			this.cyclicJars = new ArrayList();
		}
		if (!this.cyclicJars.contains(jar)) {
			this.cyclicJars.add(jar);
		}
		this.containsCycles = true;
	}

	public boolean hasCycles() {
		return this.containsCycles;
	}

	public List getCyclicJars() {
		return this.cyclicJars;
	}

	public List getOutgoingDependencies() {
		return this.dependentJars;
	}

	public List getIncomingDependencies() {
		return this.incomingDependencies;
	}

	public List getAllExternallyReferencedPackages() {
		Iterator allPackages = this.getAllContainedPackages().iterator();
		ArrayList externalImports = new ArrayList();
		while (allPackages.hasNext()) {
			JarPackage javaPackage = (JarPackage) allPackages.next();
			Iterator dependencies = javaPackage.getImportedPackageNames().iterator();
			while (dependencies.hasNext()) {
				String dependentPackage = (String) dependencies.next();
				if ( (this.containsPackage(dependentPackage) == false) &&
					 (externalImports.contains(dependentPackage) == false) ) {
					externalImports.add(dependentPackage);
				}

			}
		}
		return externalImports;
	}

	public List getAllUnidentifiableExternallyReferencedPackages() {
		Iterator externalPackages = this.getAllExternallyReferencedPackages().iterator();
		ArrayList unresolvablePackages = new ArrayList();
		while (externalPackages.hasNext()) {
			String packageName = (String) externalPackages.next();
			Iterator dependencies = this.dependentJars.iterator();
			boolean packageFound = false;
			while (dependencies.hasNext()) {
				Jar jar = (Jar) dependencies.next();
				if (jar.containsPackage(packageName)) {
					packageFound = true;
				}
			}
			if ( (!packageFound) && (!unresolvablePackages.contains(packageName)) ) {
				unresolvablePackages.add(packageName);
			}
		}
		return unresolvablePackages;
	}

	public int getPackageCount() {
		return this.packages.size();
	}

	public String getJarFileName() {
		//return this.jarFileName.substring(this.jarFileName.lastIndexOf("\\") + 1,
		//													this.jarFileName.length());
		//return this.jarFileName.substring(this.jarFileName.lastIndexOf("/") + 1,
		//													this.jarFileName.length());
		return this.jarFileName.substring(this.jarFileName.lastIndexOf(System.getProperty("file.separator")) + 1,
															this.jarFileName.length());
	}

	public String getFullyQualifiedJarFileName() {
		return this.jarFileName;
	}

	public int getClassCount() {
		Iterator p = this.packages.iterator();
		int classCount = 0;
		while (p.hasNext()) {
			JarPackage pkg = (JarPackage) p.next();
			classCount += pkg.getClassCount();
		}

		return classCount;
	}

	public int getAbstractClassCount() {
		Iterator p = this.packages.iterator();
		int classCount = 0;
		while (p.hasNext()) {
			JarPackage pkg = (JarPackage) p.next();
			Iterator classes = pkg.getClasses().iterator();
			while (classes.hasNext()) {
				JarClass jarClass = (JarClass) classes.next();
				if (jarClass.isAbstract() || jarClass.isInterface()) {
					classCount ++;
				}
			}
		}
		return classCount;
	}

    public void addPackage(JarPackage jarPackage) {
        packages.add(jarPackage);
    }

    public JarPackage getPackage(String s) {
        JarPackage jarPackage = new JarPackageImpl(s);
        int i = packages.indexOf(jarPackage);
        return (JarPackage)packages.get(i);
    }

    public boolean containsPackage(String s) {
        if(packages == null) {
            return false;
		}
        JarPackage jarPackage = new JarPackageImpl(s);
        return packages.contains(jarPackage);
    }

    public JarMetrics calculateMetrics() {
		JarMetrics metrics = new JarMetricsImpl(this);
		return metrics;
	}

}
