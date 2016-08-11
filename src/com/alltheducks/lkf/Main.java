package com.alltheducks.lkf;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        Path projectDirectory = Paths.get(".");

        // get a list of all the property files
        Files.walk(projectDirectory, FileVisitOption.FOLLOW_LINKS)
                // only care about property files
                .filter(path -> path.toString().endsWith(".properties"))
                // we want the parent directory to be bundles
                .filter(path -> path.getParent().getFileName().toString().equals("bundles"))
                // the parent directory of bundles should be WEB-INF
                .filter(path -> path.getParent().getParent().getFileName().toString().equals("WEB-INF"))
                // process the project directory
                .forEach(propertiesFile -> new LanguageBundleProcessor(propertiesFile).process(projectDirectory));
    }
}
