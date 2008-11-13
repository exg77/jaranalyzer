package com.kirkk.analyzer.textui;


import java.io.*;
import java.util.*;
import com.kirkk.analyzer.framework.*;
import com.kirkk.analyzer.*;

/*
 * Modified version of XMLUISummary
 * http://www.kirkk.com/wiki/wiki.php/Main/JarAnalyzer
 *
 * This class takes the input directory and output file
 * from the command line or from System.in. It is also
 * usable directly from Java and can be called from
 * an ant task
 */
public class XMLUISummary implements Summary {
	private PrintWriter writer;

	public static void main(String args[]) throws Exception {
		new XMLUISummary().instanceMain(args);
	}

	public void instanceMain(String args[]) throws Exception {
        File scrDir = (args.length > 0) ? new File(args[0]) : null;
        File destFile = (args.length > 1) ? new File(args[1]) : null;
        if(scrDir == null)
        {
            System.out.print("Please enter input directory name: ");
            scrDir = getFile();
        }
        if(destFile == null)
        {
            System.out.print("Please enter output file name: ");
            destFile = getFile();
        }
        createSummary(scrDir, destFile);
	}

    public void createSummary(File srcDir, File destFile) throws Exception
    {
        Analyzer analyzer = new Analyzer();
        Jar jarBundle[] = analyzer.analyze(srcDir);
        outputAll(jarBundle, destFile);
    }

    public void createSummary(File srcDir, File destFile, String packageFilter, String jarFilter) throws Exception {
		Analyzer analyzer = new Analyzer();
		Configuration.initialize(packageFilter, jarFilter);
		Jar jarBundle[] = analyzer.analyze(srcDir);
        outputAll(jarBundle, destFile);
	}

    private File getFile() throws IOException {
	    try {
		    InputStreamReader streamReader = new InputStreamReader(System.in);
			BufferedReader reader = new BufferedReader(streamReader);
			String fileName = reader.readLine();
			File file = new File(fileName);
			return file;
		} catch (IOException e) {
			throw e;
		}
	}

	private void outputAll(Jar[] jarBundle, File file) {
		try {
			FileWriter fileWriter = new FileWriter(file);
			writer = new PrintWriter(fileWriter);
		} catch (IOException e) {
			System.out.println("IOException - Redirecting to System.out");
			System.out.println(e);
			OutputStreamWriter outputWriter = new OutputStreamWriter(System.out);
			writer = new PrintWriter(outputWriter);
		}
		this.printHeader();
		this.output(jarBundle);
		this.printFooter();
		writer.flush();
		writer.close();
	}

	private void printHeader() {
		writer.println("<?xml version=\"1.0\"?>");
		writer.println("<JarAnalyzer>");
	}

	private void printFooter() {
		writer.println("</JarAnalyzer>");
	}

	private void output(Jar[] jarBundles) {
		writer.println(tab()+"<Jars>");writer.println();
		for (int i = 0; i < jarBundles.length; i++) {
			String jar = jarBundles[i].getJarFileName().substring(jarBundles[i].getJarFileName().lastIndexOf("\\") + 1,
									jarBundles[i].getJarFileName().length());
			writer.println(tab(2)+"<Jar name=\"" + jar + "\">");
			writer.println(tab(3)+"<Summary>");
			this.statistics(jarBundles[i]);writer.println();
			this.metrics(jarBundles[i].calculateMetrics());writer.println();
			this.jarPackages(jarBundles[i]);writer.println();
			this.outgoingDependencies(jarBundles[i]);
			this.incomingDependencies(jarBundles[i]);
			this.cycles(jarBundles[i]);
			this.unresolveableDependencies(jarBundles[i]);
			writer.println(tab(3)+"</Summary>");writer.println();
			writer.println(tab(2)+"</Jar>");writer.println();
		}
		writer.println(tab()+"</Jars>");
	}

	private void statistics(Jar jarBundle) {
		writer.println(tab(4)+"<Statistics>");
		writer.println(tab(5)+"<ClassCount>" + jarBundle.getClassCount() + "</ClassCount>");
		writer.println(tab(5)+"<AbstractClassCount>" + jarBundle.getAbstractClassCount() + "</AbstractClassCount>");
		writer.println(tab(5)+"<PackageCount>" + jarBundle.getPackageCount() + "</PackageCount>");
		//writer.println(tab(5)+"<Level>" + jarBundle.getLevel() + "</Level>");
		writer.println(tab(4)+"</Statistics>");
	}

	private void metrics(JarMetrics jarMetrics) {
		writer.println(tab(4)+"<Metrics>");
		writer.println(tab(5)+"<Abstractness>" + jarMetrics.calculateAbstractness().toString() + "</Abstractness>");
		writer.println(tab(5)+"<Efferent>" + jarMetrics.calculateEfferentCoupling() + "</Efferent>");
		writer.println(tab(5)+"<Afferent>" + jarMetrics.calculateAfferentCoupling() + "</Afferent>");
		writer.println(tab(5)+"<Instability>" + jarMetrics.calculateInstability().toString() + "</Instability>");
		writer.println(tab(5)+"<Distance>" + jarMetrics.calculateDistance().toString() + "</Distance>");
		writer.println(tab(4)+"</Metrics>");
	}

	private void cycles(Jar jarBundle) {
		writer.println(tab(4) + "<Cycles>");
		if (jarBundle.hasCycles()) {
			Iterator cyclicJars = jarBundle.getCyclicJars().iterator();
			while (cyclicJars.hasNext()) {
				Jar cyclicBundle = (Jar) cyclicJars.next();
				String jarName = cyclicBundle.getJarFileName();
				writer.println(tab(5) + "<Cycle>" + jarName + "</Cycle>");
			}
		}
		writer.println(tab(4) + "</Cycles>");writer.println();
	}

	private void outgoingDependencies(Jar jarBundle) {
		writer.println(tab(4)+"<OutgoingDependencies>");
		Iterator jarDependencies = jarBundle.getOutgoingDependencies().iterator();
		while (jarDependencies.hasNext()) {
			Jar dependentBundle = (Jar) jarDependencies.next();
			//String jar2 = dependentBundle.getJarFileName().substring(dependentBundle.getJarFileName().lastIndexOf("\\") + 1,
			//												dependentBundle.getJarFileName().length());
			String jar2= dependentBundle.getJarFileName();
			writer.println(tab(5)+"<Jar>"+jar2+"</Jar>");
		}
		writer.println(tab(4)+"</OutgoingDependencies>");writer.println();
	}

	private void incomingDependencies(Jar jarBundle) {
		writer.println(tab(4)+"<IncomingDependencies>");
		Iterator jarDependencies = jarBundle.getIncomingDependencies().iterator();
		while (jarDependencies.hasNext()) {
			Jar dependentBundle = (Jar) jarDependencies.next();
			//String jar2 = dependentBundle.getJarFileName().substring(dependentBundle.getJarFileName().lastIndexOf("\\") + 1,
			//												dependentBundle.getJarFileName().length());
			String jar2= dependentBundle.getJarFileName();
			writer.println(tab(5)+"<Jar>"+jar2+"</Jar>");
		}
		writer.println(tab(4)+"</IncomingDependencies>");writer.println();
	}


	private void externalDependencies(Jar jarBundle) {
		Iterator allPackages = jarBundle.getAllExternallyReferencedPackages().iterator();
		while (allPackages.hasNext()) {
			String javaPackage = (String) allPackages.next();
			writer.println(tab(5)+"<Package>"+javaPackage+"</Package>");
		}
	}

	private void unresolveableDependencies(Jar jarBundle) {
		writer.println(tab(4)+"<UnresolvedDependencies>");
		Iterator unresolvedPackages = jarBundle.getAllUnidentifiableExternallyReferencedPackages().iterator();
		while (unresolvedPackages.hasNext()) {
			String packageName = (String) unresolvedPackages.next();
			writer.println(tab(5)+"<Package>"+packageName+"</Package>");
		}
		writer.println(tab(4)+"</UnresolvedDependencies>");
	}

	private void jarPackages(Jar jarBundle) {
		writer.println(tab(4)+"<Packages>");
		Iterator allPackages = jarBundle.getAllContainedPackages().iterator();
		while (allPackages.hasNext()) {
			JarPackage javaPackage = (JarPackage) allPackages.next();
			writer.println(tab(5)+"<Package>" + javaPackage.getLongName() + "</Package>");
		}
		writer.println(tab(4)+"</Packages>");
	}

	private String tab() {
		return "    ";
	}

	private String tab(int i) {
		String tab = tab();
		for (int j = 0; j < i - 1; j++) {
			tab = tab + tab();
		}
		return tab;
	}

}