package com.kirkk.analyzer;

import java.util.*;

public class Configuration {

	public static List ignorePackages;
	public static List ignoreJars;

	static {
		ResourceBundle filters = ResourceBundle.getBundle("com.kirkk.analyzer.framework.Filter");

		String packageFilters = filters.getString("filter.packages");
		ignorePackages = new ArrayList();
		StringTokenizer packageTokenizer = new StringTokenizer(packageFilters, ";");
		while (packageTokenizer.hasMoreTokens()) {
			String token = packageTokenizer.nextToken();
			//System.out.println(token.substring(0, token.length() - 1));
			ignorePackages.add(token.substring(0, token.length() - 1));
		}

		String jarFilters = filters.getString("filter.jars");
		ignoreJars = new ArrayList();
		StringTokenizer jarTokenizer = new StringTokenizer(jarFilters, ";");
		while (jarTokenizer.hasMoreTokens()) {
			String token = jarTokenizer.nextToken();
			ignoreJars.add(token.substring(0, token.length()));
		}
	}

	public static void initialize(String packageFilter, String jarFilter) {
		if (packageFilter != null) {
			ignorePackages = new ArrayList();
			StringTokenizer packageTokenizer = new StringTokenizer(packageFilter, ";");
			while (packageTokenizer.hasMoreTokens()) {
				String token = packageTokenizer.nextToken();
				//System.out.println(token.substring(0, token.length() - 1));
				ignorePackages.add(token.substring(0, token.length() - 1));
			}
		}

		if (jarFilter != null) {
			ignoreJars = new ArrayList();
			StringTokenizer jarTokenizer = new StringTokenizer(jarFilter, ";");
			while (jarTokenizer.hasMoreTokens()) {
				String token = jarTokenizer.nextToken();
				ignoreJars.add(token.substring(0, token.length()));
			}
		}
	}

}