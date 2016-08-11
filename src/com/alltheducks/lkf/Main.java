package com.alltheducks.lkf;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        String projectDir = ".";
        // possibly scan an alternative directory
        if (args.length == 1) {
            projectDir = args[0];
        }
        Path projectDirectory = Paths.get(projectDir);

        // get a list of all the property files
        Files.walk(projectDirectory, FileVisitOption.FOLLOW_LINKS)
                // don't care about any files in the ./build/ directory if a build.gradle file exists
                .filter(path -> {
                    if (Files.exists(Paths.get("build.gradle"))) {
                        if (path.toString().startsWith("./build/")) {
                            return false;
                        }
                    }
                    return true;
                })
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
