package org.data.domain;

import java.net.URL;
import java.util.jar.JarEntry;

/**
 * Represents an entry in a JAR file, containing the URL of the JAR file and the JAR entry itself.
 * This class is used to encapsulate information about a class file within a JAR file.
 */
public record Entry(URL url, JarEntry jarEntry) {

    /**
     * Constructs a new Entry instance.
     *
     * @param url      The URL of the JAR file.
     * @param jarEntry The JAR entry representing a class file.
     */
    public Entry(URL url, JarEntry jarEntry) {
        this.url = url;
        this.jarEntry = jarEntry;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "url=" + url +
                ", jarEntry=" + jarEntry.getName() +
                '}';
    }
}