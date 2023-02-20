package by.smirnov.chequeprintproject.util;

import lombok.experimental.UtilityClass;

import java.io.File;

@UtilityClass
public class PathGetter {

    private static final String DEFAULT_FILE_NAME = "cheque.txt";
    private static final String DIR = "user.dir";
    private static final String EXTENSION = ".txt";
    private static final String PATH = "src" +
            File.separator + "main" +
            File.separator + "resources" +
            File.separator;

    public static String getPath(String filename) {
        if(filename == null || filename.isBlank()) filename = DEFAULT_FILE_NAME;
        if(!filename.endsWith(EXTENSION)) filename = filename + EXTENSION;
        String root = System.getProperty(DIR);
        return root + File.separator + PATH + filename;
    }
}
