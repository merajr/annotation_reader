package org.data.service;

import org.data.domain.Entry;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Optional;
import java.util.jar.JarEntry;
import java.lang.annotation.Annotation;

/**
 * JarScannerService is responsible for scanning JAR files and processing their entries.
 * It loads classes from the JAR file and prints their annotations.
 */
public class JarScannerService {

    /**
     * Scans a JAR file for class entries and processes them.
     *
     * @param jarFilePath The path to the JAR file to scan.
     */
    public Optional<Entry> scanJarEntry(JarEntry entry, File jarFile) {
        if (entry.isDirectory() || !entry.getName().endsWith(".class")) {
            return Optional.empty();
        }
        try {
            URL url = new URL("jar:file:" + jarFile.getPath() + "!/");
            return Optional.of(new Entry(url, entry));
        } catch (MalformedURLException e) {
            System.err.println("Invalid JAR file URL: " + e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Processes a JAR entry to load its class and print annotations.
     *
     * @param entry The JAR entry to process.
     * @throws IOException If an I/O error occurs while processing the entry.
     */
    public void processJarEntry(Entry entry) throws IOException {
        URL[] urls = { entry.url() };

        String className = entry.jarEntry().getName().replace(".class", "").replace('/', '.');

        // Use a URLClassLoader to load classes from the specified JAR file.
        // This is crucial for dynamically loading classes that are not on the default classpath.
        try (URLClassLoader classLoader = URLClassLoader.newInstance(urls)) {
            // Load the class using the class loader.
            Class myClass = Class.forName(className, false, classLoader);
            System.out.println("\nScanning class: " + myClass.getName());
            Annotation[] annotations = myClass.getAnnotations();

            if (annotations.length > 0) {
                System.out.println("\nAnnotations found in class: " + myClass.getName());
                int counter = 1;
                for (Annotation annotation : annotations) {
                    System.out.println("  " + counter++ + ": " + annotation.toString());
                }
            }

        } catch (ClassNotFoundException | NoClassDefFoundError e) {
            System.err.println("Could not load class " + className + ": " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred while inspecting class " + className + ": " + e.getMessage());
        }
    }
}
