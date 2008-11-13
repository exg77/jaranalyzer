package com.kirkk.analyzer.framework.bcel;

import org.apache.bcel.classfile.*;
import java.util.*;

public class PackageVisitor extends EmptyVisitor {

	private JavaClass javaClass;
	private ArrayList imports;
	private ArrayList strings;

	public PackageVisitor(JavaClass javaClass) {
		this.javaClass = javaClass;
		//System.out.println("PackageVisitor constructor: " + javaClass.getClassName());
		this.imports = new ArrayList();
		this.strings = new ArrayList();
	}
	public void visitConstantClass(ConstantClass cls) {
		//System.out.println("CONSTANTCLASS: " + cls.getBytes(this.pool));
		String sCls = cls.getBytes(this.javaClass.getConstantPool());
		if (sCls.indexOf("/") != -1) {
			//sCls = sCls.substring(0, sCls.lastIndexOf("."));
			sCls = this.stripClassName(sCls);
			sCls = sCls.replace('/', '.');
			sCls = this.cleanClass(sCls);
			if (!this.imports.contains(sCls) && (!this.javaClass.getPackageName().equals(sCls))) {
				this.imports.add(sCls);
			}
		}
	}

	public void visitConstantUtf8(ConstantUtf8 utf) {
		//System.out.println("UTF8: " + utf.getBytes());
		//System.out.println("UTF8 Strng: " + utf.toString());
		String utfString = utf.toString().substring(utf.toString().indexOf('"') + 1,utf.toString().lastIndexOf('"'));
		//System.out.println("FIXED: " + utfString);
		if (isValidJavaClass(utfString)) {
			if (!this.strings.contains(utfString)) {
				String[] classes = this.separateClasses(utfString);
				for (int i = 0; i < classes.length; i++) {
					if (classes[i] != null) {
						String cls = classes[i];
						String packageName = this.stripClassName(cls);
						packageName = packageName.replace('/', '.');
						String cleanedPackage = this.cleanClass(packageName);
						//System.out.println("UTF " + cls);
						if (!this.imports.contains(cleanedPackage) && (!this.javaClass.getPackageName().equals(cleanedPackage))) {
							this.imports.add(cleanedPackage);
						}
					}
				}
			}
		}

	}

	public void visitConstantString(ConstantString str) {
		//System.out.println("CONSTANTSTRING: " + str.getBytes(this.pool));
		this.strings.add(str.getBytes(this.javaClass.getConstantPool()).toString());
	}

	private boolean isValidJavaClass(String cls) {
		if (cls.indexOf("/") == -1) {
			return false;
		}
		if ( (!cls.startsWith("(")) && (!cls.startsWith("L")) ) {
			return false;
		}

		if ( (!cls.endsWith("V")) && (!cls.endsWith(";")) && (!cls.endsWith(")")) ) {
			return false;
		}

		return true;
	}

	private String[] separateClasses(String utfString) {
		StringTokenizer tokenizer = new StringTokenizer(utfString, ";");
		String classes[] = new String[tokenizer.countTokens()];
		int i = 0;
		while (tokenizer.hasMoreTokens()) {
			String cls = tokenizer.nextToken();
			if (cls.indexOf('/') != -1) {
				classes[i] = cls;
				i++;
			}
		}
		return classes;
	}

	private String cleanClass(String cls) {
		int index = cls.lastIndexOf('L');
		//int index = cls.indexOf('L');
		String newCls = cls;
		if (index != -1) {
			newCls = cls.substring(index + 1);
		}
		return newCls;
	}

	private String stripClassName(String cls) {
		//String strippedName = cls.substring(0, cls.lastIndexOf("."));
		String strippedName = cls.substring(0, cls.lastIndexOf("/"));
		return strippedName;
	}

	public List getAllImports() {
		return this.imports;
	}

	public List getImports(List ignorePackages) {
		Iterator i = this.imports.iterator();
		ArrayList imports = new ArrayList();
		while (i.hasNext()) {
			String pkg = (String) i.next();
			//System.out.println("IMPORT: " + pkg);
			if (ignorePackages.isEmpty() == false) {
				Iterator filter = ignorePackages.iterator();
				boolean filterPackage = false;
				while (filter.hasNext()) {
					String packageFilter = (String) filter.next();
					if (pkg.startsWith(packageFilter)) {
						filterPackage = true;
					}
				}
				if (!filterPackage) {
					imports.add(pkg);
				}
			} else {
				imports.add(pkg);
			}
		}
		return imports;

	}
}