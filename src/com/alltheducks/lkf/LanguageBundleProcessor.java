package com.alltheducks.lkf;

import java.io.*;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Created by Harry Scells on 11/08/2016.
 */
public class LanguageBundleProcessor {

    private Properties properties;
    private Path filename;

    public LanguageBundleProcessor(Path filename) {
        properties = new Properties();
        try (FileInputStream f = new FileInputStream(filename.toString())) {
            properties.load(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.filename = filename;
    }

    /**
     * Process the language pack for a given directory. This method loops over each
     * key in the language pack and then over each file in the directory tree.
     * @param directory
     */
    public void process(Path directory) {
        // the directory needs to exist
        if (!Files.exists(directory)) {
            return;
        }

        // the properties file should have at least one property
        if (properties.keySet().size() > 0) {
            for (Object o : properties.keySet()) {
                boolean found = false;
                String languageKey = o.toString();
                try {
                    // walk the directory
                    for (Path path : Files.walk(directory, FileVisitOption.FOLLOW_LINKS).collect(Collectors.toList())) {
                        // ignore directories and the current properties file
                        if (!Files.isDirectory(path) && !path.getFileName().equals(filename.getFileName())) {
                            // test if the language pack key occurs in the file
                            String content = new String(Files.readAllBytes(path));
                            if (content.contains(languageKey)) {
                                found = true;
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // notify the user of any keys that were not found
                if (!found) {
                    System.out.println(String.format("%s: %s", filename, languageKey));
                }
            }
        }
    }
}
