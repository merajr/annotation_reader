package org.data.domain;

import java.net.URL;
import java.util.jar.JarEntry;

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