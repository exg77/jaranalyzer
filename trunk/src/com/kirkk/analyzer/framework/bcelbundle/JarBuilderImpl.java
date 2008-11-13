package com.kirkk.analyzer.framework.bcelbundle;

import java.util.*;
import com.kirkk.analyzer.framework.*;
import com.kirkk.analyzer.framework.jar.JarFile;
import com.kirkk.analyzer.framework.bcel.*;
import java.io.*;
import java.util.zip.ZipException;

public class JarBuilderImpl implements JarBuilder {

    public Jar buildJar(JarFile jarFile, List ignorePackages) throws Exception {
        Jar jar = new JarImpl(jarFile.getFileName());
        while (jarFile.hasMoreClasses()) {
            String className = jarFile.nextClass();
            try {
                BCELPublicClass javaClass = new BCELPublicClass(jarFile.getFileName(), className, ignorePackages);
                String packageName = javaClass.getPackageName();
                JarPackage javaPackage = null;
                if(jar.containsPackage(packageName)) {
                    javaPackage = jar.getPackage(packageName);
                } else {
                    javaPackage = new JarPackageImpl(packageName);
                    jar.addPackage(javaPackage);
                }
                javaPackage.addClass(javaClass);
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
        jarFile.close();
        return jar;
    }

}
