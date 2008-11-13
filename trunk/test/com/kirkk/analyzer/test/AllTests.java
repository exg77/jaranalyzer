package com.kirkk.analyzer.test;

import junit.framework.*;

public class AllTests extends TestCase {
	public AllTests(String name) {
		super(name);
	}

	public static Test suite() {
	  TestSuite suite= new TestSuite(AllTests.class.getName());
	  suite.addTestSuite(com.kirkk.analyzer.framework.jar.test.TestJarFile.class);
	  suite.addTestSuite(com.kirkk.analyzer.framework.bcel.test.TestClassParsing.class);
	  suite.addTestSuite(com.kirkk.analyzer.framework.bcel.test.TestBCELClass.class);
	  suite.addTestSuite(com.kirkk.analyzer.framework.bcelbundle.test.TestJarBuilderImpl.class);
	  suite.addTestSuite(com.kirkk.analyzer.framework.bcelbundle.test.TestJarCollection.class);
	  suite.addTestSuite(com.kirkk.analyzer.framework.bcelbundle.test.TestJarRelationshipDecorator.class);
	  suite.addTestSuite(com.kirkk.analyzer.framework.bcelbundle.test.TestJarMetricsImpl.class);
	  return suite;
	 }
 }