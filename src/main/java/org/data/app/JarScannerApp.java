package org.data.app;

import java.io.File;
import java.util.jar.JarFile;
import java.util.Optional;
import java.io.IOException;
import org.data.service.JarScannerService;
import org.data.domain.Entry;

public class JarScannerApp {

    /**
     * Scans a JAR file for class entries and processes them.
     *
     * @param jarFilePath The path to the JAR file to scan.
     */
    public void scanJarFile(String jarFilePath) {
        System.out.println("Scanning JAR file: " + jarFilePath);
        File jarFile = new File(jarFilePath);

        if (!jarFile.exists() || !jarFile.isFile()) {
            System.err.println("Error: File does not exist or is not a valid file: " + jarFilePath);
            System.exit(1);
        }
        try (JarFile jar = new JarFile(jarFile)) {
            jar.stream().forEach(entry -> {
                JarScannerService service = new JarScannerService();
                try {
                    Optional<Entry> result = service.scanJarEntry(entry, jarFile);
                    if (result.isPresent()) {
                        service.processJarEntry(result.get());
                    }
                }
                catch (IOException e) {
                    System.err.println("Error processing entry " + entry.getName() + ": " + e.getMessage());
                }
            });
        } catch (IOException e) {
            System.err.println("Error reading JAR file: " + e.getMessage());
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java -jar assessment.jar <path-to-jar-file>");
            return;
        }
        String jarFilePath = args[0];
        JarScannerApp scanner = new JarScannerApp();
        scanner.scanJarFile(jarFilePath);
    }
}