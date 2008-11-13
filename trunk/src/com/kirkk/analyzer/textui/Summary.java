package com.kirkk.analyzer.textui;

import java.io.File;

public interface Summary
{
    public void createSummary(File srcDir, File destFile) throws Exception;
    public void createSummary(File srcDir, File destFile, String packageFilter, String jarFilter) throws Exception;
}
