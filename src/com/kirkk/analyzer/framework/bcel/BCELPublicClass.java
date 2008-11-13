package com.kirkk.analyzer.framework.bcel;

import com.kirkk.analyzer.framework.JarClass;
import java.util.*;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.ConstantUtf8;
import org.apache.bcel.classfile.DescendingVisitor;

public class BCELPublicClass implements JarClass {

    //private JavaClass javaClass;
    private static List ignorePackages;
    private List importedPackages;
    private List publicMethods;
    private String packageName;
    private String longName;
    private boolean isAbstract;
    private boolean isPublic;
    private boolean isInterface;

	public BCELPublicClass(String fileName, String className) throws Exception {
		this(fileName, className, new ArrayList());
	}

	public BCELPublicClass(String fileName, String className, List ignorePackages) throws Exception {
		this(BCELClassFactory.getClass(fileName, className), ignorePackages);
	}


    private BCELPublicClass(JavaClass javaClass, List ignorePackages) {
		this.importedPackages = this.getImportedPackages(javaClass, ignorePackages);
		//this.publicMethods = this.getPublicMethods(javaClass);
		this.packageName = javaClass.getPackageName();
		this.isAbstract = javaClass.isAbstract();
		this.isPublic = javaClass.isPublic();
		this.longName = javaClass.getClassName();
		this.isInterface = javaClass.isInterface();
        //this.javaClass = javaClass;
    }

	private List getImportedPackages(JavaClass javaClass, List ignorePackages) {
		PackageVisitor pVisitor = new PackageVisitor(javaClass);
		DescendingVisitor dVisitor = new DescendingVisitor(javaClass, pVisitor);
		javaClass.accept(dVisitor);
		return pVisitor.getImports(ignorePackages);

		//return pVisitor.getNonJavaImports();
	}

	/*private List getPublicMethods(JavaClass javaClass) {
		ArrayList arraylist = new ArrayList();
		Method methods[] = javaClass.getMethods();
		for(int i = 0; i < methods.length; i++) {
		    if (methods[i].isPublic()) {
		        arraylist.add(new BCELPublicMethod(methods[i]));
			}
		}
        return arraylist;
	}*/

	public boolean isPublic() {
		return this.isPublic;
	}

	public boolean isAbstract() {
		return this.isAbstract;
	}

	public List getImportedPackageNames() {
		return this.importedPackages;
	}

    public String getPackageName() {
        return this.packageName;
    }

    public String getLongName() {
        return this.longName;
    }

   /*public List getPublicMethods() {
		return this.publicMethods;
    }*/

    public boolean isInterface() {
		return this.isInterface;
	}

}
