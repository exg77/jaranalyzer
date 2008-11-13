package com.kirkk.analyzer.framework.bcelbundle.test;

import junit.framework.*;
import com.kirkk.analyzer.framework.bcel.*;
import com.kirkk.analyzer.framework.*;
import com.kirkk.analyzer.framework.bcelbundle.*;
import java.util.*;
import java.math.*;


public class TestJarMetricsImpl extends TestCase {

	private Jar jar;
	private Jar emptyJar;

	public void setUp() {
		this.jar = new Jar() {
			public int getPackageCount() { return 5; }
			public int getAbstractClassCount() {return 2; }
			public int getClassCount() { return 10; }
			//public int getLevel() { return 1; }
			public String getJarFileName() { return "THIS IS A TEST JAR INNER CLASS IN TestJarMetricsImpl"; }

			public void addPackage(JarPackage jarPackage) { ; }
			public boolean containsPackage(String packageName) {return false; }
			public JarPackage getPackage(String packageName) { return null; }
			public List getAllUnidentifiableExternallyReferencedPackages() { return null; }
			public List getAllContainedPackages() { return null; }
			public List getAllExternallyReferencedPackages() { return null; }


			public List getOutgoingDependencies() {
				ArrayList list = new ArrayList();
				list.add("1");
				list.add("2");
				return list;
			}

			public List getIncomingDependencies() {
				ArrayList list = new ArrayList();
				return list;
			}

			public void addIncomingDependency(Jar jar) { ; }
			public void addOutgoingDependency(Jar jar) { ; }

			public boolean hasCycles() { return false; }
			public List getCyclicJars() { return null; }
			public void addCyclicJar(Jar jar) { ; }
			public JarMetrics calculateMetrics() { return null; }
		};

		this.emptyJar = new Jar() {
			public int getPackageCount() { return 0; }
			public int getAbstractClassCount() {return 0; }
			public int getClassCount() { return 0; }
			//public int getLevel() { return 0; }
			public String getJarFileName() { return "THIS IS AN EMPTY TEST JAR INNER CLASS IN TestJarMetricsImpl"; }

			public void addPackage(JarPackage jarPackage) { ; }
			public boolean containsPackage(String packageName) {return false; }
			public JarPackage getPackage(String packageName) { return null; }
			public List getAllUnidentifiableExternallyReferencedPackages() { return null; }
			public List getAllContainedPackages() { return null; }
			public List getAllExternallyReferencedPackages() { return null; }


			public List getOutgoingDependencies() {
				ArrayList list = new ArrayList();
				return list;
			}

			public List getIncomingDependencies() {
				ArrayList list = new ArrayList();
				return list;
			}

			public void addIncomingDependency(Jar jar) { ; }
			public void addOutgoingDependency(Jar jar) { ; }

			public boolean hasCycles() { return false; }
			public List getCyclicJars() { return null; }
			public void addCyclicJar(Jar jar) { ; }
			public JarMetrics calculateMetrics() { return null; }
		};
	}

	public void testAbstractness() {
		JarMetrics jarMetrics = new JarMetricsImpl(this.jar);
		assertEquals("Abstractness should be 0.20", new BigDecimal("0.20"), jarMetrics.calculateAbstractness());
	}

	//Incoming
	public void testInstability() throws Exception {
		JarMetrics jarMetrics = new JarMetricsImpl(this.jar);
		assertEquals("Instability should be 1", new BigDecimal("1.00"), jarMetrics.calculateInstability());
	}

	public void testEfferent() {
		JarMetrics jarMetrics = new JarMetricsImpl(this.jar);
		assertEquals("Efferent coupling should be 2", 2, jarMetrics.calculateEfferentCoupling());

	}

	public void testAfferent() {
		JarMetrics jarMetrics = new JarMetricsImpl(this.jar);
		assertEquals("Afferent coupling should be 0", 0, jarMetrics.calculateAfferentCoupling());
	}

	public void testDistance() {
		JarMetrics jarMetrics = new JarMetricsImpl(this.jar);
		assertEquals("Distance from main sequence should be 0.20", new BigDecimal("0.20"), jarMetrics.calculateDistance());
	}

	public void testAllZeros() {
		JarMetrics jarMetrics = new JarMetricsImpl(this.emptyJar);
		assertEquals("Efferent coupling should be 0", 0, jarMetrics.calculateEfferentCoupling());
		assertEquals("Afferent coupling should be 0", 0, jarMetrics.calculateAfferentCoupling());
		assertEquals("Abstractness should be -1 (undefined)", new BigDecimal("-1.0"), jarMetrics.calculateAbstractness());
		assertEquals("Instability should be -1.0 (undefined)", new BigDecimal("-1.0"), jarMetrics.calculateInstability());
		assertEquals("Distance should be -1.0 (undefined)", new BigDecimal("-1.0"), jarMetrics.calculateDistance());

	}

}