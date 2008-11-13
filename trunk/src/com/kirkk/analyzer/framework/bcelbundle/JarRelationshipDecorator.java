package com.kirkk.analyzer.framework.bcelbundle;

import java.util.*;
import java.io.*;
import com.kirkk.analyzer.framework.*;

public class JarRelationshipDecorator extends JarCollectionDecorator {

	public JarRelationshipDecorator(JarCollection jarCollection) {
		super(jarCollection);
		this.buildJarRelationships();
	}

	private void buildJarRelationships() {
		while (this.jarCollection.hasNext()) {
			Jar jar = this.jarCollection.nextJar();
			Iterator externalImports = jar.getAllExternallyReferencedPackages().iterator();
			while (externalImports.hasNext()) {
				String packageName = (String) externalImports.next();
				Jar dependentJar = this.jarCollection.getJarContainingPackage(packageName);
				if (dependentJar != null) {
					jar.addOutgoingDependency(dependentJar);
				}
			}
		}
	}

}