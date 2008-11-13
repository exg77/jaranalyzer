package com.kirkk.analyzer.framework.bcelbundle;

import com.kirkk.analyzer.framework.*;
import java.math.BigDecimal;

public class JarMetricsImpl implements JarMetrics {
	private Jar jar;

	public JarMetricsImpl(Jar jar) {
		this.jar = jar;
	}

	public BigDecimal calculateAbstractness() {
		int classCount = jar.getClassCount();
		if (classCount == 0) {
			return new BigDecimal("-1.0");
		}
		BigDecimal totalClassCount = new BigDecimal(new Integer(classCount).toString());
		BigDecimal abstractClassCount = new BigDecimal(new Integer(jar.getAbstractClassCount()).toString());
		return abstractClassCount.divide(totalClassCount,2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal calculateInstability() {
		int afferent = this.calculateAfferentCoupling();
		int efferent = this.calculateEfferentCoupling();

		if (afferent + efferent == 0) {
			return new BigDecimal("-1.0");
		}

		BigDecimal top = new BigDecimal(new Integer(efferent).toString());
		BigDecimal bottom = new BigDecimal(new Integer(afferent + efferent).toString());

		return top.divide(bottom, 2, BigDecimal.ROUND_HALF_UP);
	}

	public int calculateEfferentCoupling() {
		return jar.getOutgoingDependencies().size();
	}

	public int calculateAfferentCoupling() {
		return jar.getIncomingDependencies().size();
	}

	public BigDecimal calculateDistance() {
		BigDecimal abstractness = this.calculateAbstractness();
		BigDecimal instability = this.calculateInstability();
		if ( abstractness.equals(new BigDecimal("-1.0")) || instability.equals(new BigDecimal("-1.0")) ) {
			return new BigDecimal("-1.0");
		}
		return abstractness.add(instability).subtract(new BigDecimal("1.00")).abs();
	}
}