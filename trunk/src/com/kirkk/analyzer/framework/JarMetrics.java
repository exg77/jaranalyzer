package com.kirkk.analyzer.framework;

import java.math.BigDecimal;

public interface JarMetrics {
	public BigDecimal calculateAbstractness();
	public BigDecimal calculateInstability();
	public int calculateAfferentCoupling();
	public int calculateEfferentCoupling();
	public BigDecimal calculateDistance();
}