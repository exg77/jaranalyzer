package com.kirkk.analyzer.framework;

import java.util.List;

public interface JarClass
{

	public boolean isPublic();
	public List getImportedPackageNames();
    public abstract String getLongName();
    //public abstract List getPublicMethods();
    public boolean isInterface();
    public boolean isAbstract();
}
