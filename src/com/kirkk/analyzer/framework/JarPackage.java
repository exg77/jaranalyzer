package com.kirkk.analyzer.framework;

import java.util.List;

public interface JarPackage
{

	public abstract List getImportedPackageNames();
    public abstract void addClass(JarClass jarClass);
    public abstract List getClasses();
    public abstract int getClassCount();
    public abstract String getLongName();
}
