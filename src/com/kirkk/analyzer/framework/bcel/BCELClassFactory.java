package com.kirkk.analyzer.framework.bcel;

import org.apache.bcel.classfile.*;
import com.kirkk.analyzer.framework.bcelbundle.*;
import java.io.*;

public class BCELClassFactory {

	public static JavaClass getClass(String file, String className) throws IOException {
		ClassParser classParser = new ClassParser(file, className);
		JavaClass javaClass = classParser.parse();
		//BCELPublicClass theClass = new BCELPublicClass(javaClass, null);
		return javaClass;
	}

}