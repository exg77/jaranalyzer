package com.kirkk.analyzer;

import com.kirkk.analyzer.framework.jar.*;
import com.kirkk.analyzer.framework.bcelbundle.*;
import com.kirkk.analyzer.framework.*;
import java.io.*;
import java.util.*;
import java.util.zip.ZipException;


public class Analyzer {
    public Jar[] analyze(File file) throws Exception {
		JarCollection jarCollection = new JarRelationshipDecorator(new JarCollectionImpl(file, Configuration.ignorePackages, Configuration.ignoreJars));
        return jarCollection.toArray();
    }

}
