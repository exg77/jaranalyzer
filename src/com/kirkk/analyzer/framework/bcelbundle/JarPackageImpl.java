package com.kirkk.analyzer.framework.bcelbundle;

import com.kirkk.analyzer.framework.JarPackage;
import com.kirkk.analyzer.framework.JarClass;
import java.util.*;

public class JarPackageImpl implements JarPackage {

    private String packageName;
    private List classes;

    public JarPackageImpl(String packageName) {
        if(packageName== null) {
            throw new NullPointerException("Package name cannot be null");
        } else {
            this.packageName = packageName;
        }
    }

    public void addClass(JarClass jarClass) {
        if(classes == null) {
            classes = new ArrayList();
		}
        classes.add(jarClass);
    }

    public List getClasses() {
		return this.classes;
		/*Iterator classIterator = this.publicClasses.iterator();
		List publicList = new ArrayList();
		while (classIterator.hasNext()) {
			PublicClass cls = (PublicClass) classIterator.next();
			if (cls.isPublic()) {
				publicList.add(cls);
			}
		}
        return publicList;*/
    }

    public int getClassCount() {
		return this.classes.size();
	}

    public String getLongName() {
        return this.packageName;
    }

    public boolean equals(Object obj) {
        if(obj instanceof JarPackage) {
            JarPackage javapackage = (JarPackage)obj;
            if(getLongName().equals(javapackage.getLongName())) {
                return true;
			}
        }
        return false;
    }

    public int hashCode() {
        return packageName.hashCode();
    }

    public List getImportedPackageNames() {
		List importedPackages = new ArrayList();
		Iterator classes = this.classes.iterator();
		while (classes.hasNext()) {
			JarClass cls = (JarClass) classes.next();
			Iterator packages = cls.getImportedPackageNames().iterator();
			while (packages.hasNext()) {
				String packageName = (String) packages.next();
				if (!importedPackages.contains(packageName)) {
					importedPackages.add(packageName);
				}
			}
		}
		return importedPackages;
	}
}
