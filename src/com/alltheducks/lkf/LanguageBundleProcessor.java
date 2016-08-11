package com.alltheducks.lkf;

import java.io.*;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
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

    private boolean isBinaryFile(Path path) {
        String name = path.toString();
        return name.endsWith(".jar")
                || name.endsWith(".war")
                || name.endsWith(".zip")
                || name.endsWith(".png")
                || name.endsWith(".jpg")
                || name.endsWith(".gif");
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
            // create the list of files once per language pack
            List<Path> files = new ArrayList<>();
            try {
                files = Files.walk(directory, FileVisitOption.FOLLOW_LINKS)
                        // no directories
                        .filter(path -> !Files.isDirectory(path))
                        // don't look at itself
                        .filter(path -> !path.getFileName().equals(filename.getFileName()))
                        // don't look at dotfiles and dotdirs
                        .filter(path -> !path.toString().startsWith("."))
                        // don't care about binary files
                        .filter(path -> !isBinaryFile(path))
                        .collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // iterate over each of the property keys
            for (Object o : properties.keySet()) {
                String languageKey = o.toString();

                    // represents the presence of a language key
                    boolean found = false;

                    // loop over the files we now have in memory
                    for (Path file : files) {
                        String content = "";
                        try {
                            content = new String(Files.readAllBytes(file));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        // check if the language pack key is contained inside a file
                        if (content.contains(languageKey)) {
                            found = true;
                        }
                    }
                    // notify the user of any keys that were not found
                    if (!found) {
                        System.out.println(String.format("%s: %s", filename, languageKey));
                    }

            }
        }
    }
}
