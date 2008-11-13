package com.kirkk.analyzer.framework;

import java.util.List;
import com.kirkk.analyzer.framework.jar.JarFile;

public interface JarBuilder
{

    public abstract Jar buildJar(JarFile jarFile, List ignorePackages) throws Exception;
}
