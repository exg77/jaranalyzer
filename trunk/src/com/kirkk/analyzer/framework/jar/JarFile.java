package com.kirkk.analyzer.framework.jar;

import java.io.*;
import java.util.zip.*;
import java.util.*;

public class JarFile {
	private HashMap jarContents;
	private Enumeration jarEntries;
    private ZipFile zipFile;
	private String nextClass;
	private File file;

	public JarFile(File file)  throws ZipException, IOException {
		this.file = file;
        this.zipFile =  new ZipFile(file);
		this.jarEntries = zipFile.entries();
	}

	public boolean hasMoreClasses() {
		boolean classFileFound = false;
		ZipEntry nextEntry = null;
		while (jarEntries.hasMoreElements() && (classFileFound == false) ) {
			nextEntry = (ZipEntry) jarEntries.nextElement();
			if ( (nextEntry.getName().endsWith(".class")) ) {
				classFileFound = true;
			}
		}
		if (classFileFound) {
			this.nextClass = nextEntry.getName();
			return true;
		} else {
			this.nextClass = null;
			return false;
		}
	}

	public String nextClass() {
		return this.nextClass;
	}

	public String getFileName() {
		return this.file.getAbsolutePath();
	}

	public String getShortFileName() {
		return this.file.getName();
	}

    public void close() {
        if(null != zipFile) {
            try {
                zipFile.close();
            }
            catch (IOException exc) {
                exc.printStackTrace();
            }
        }
    }
}