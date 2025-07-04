package org.data.service;

import org.data.domain.Entry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.net.URL;
import java.util.Optional;
import java.util.jar.JarEntry;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JarScannerServiceTest {

    private JarScannerService jarScannerService;

    @BeforeEach
    void setUp() {
        jarScannerService = new JarScannerService();
    }

    @Test
    void processJarEntry_shouldPrintAnnotationsForAnnotatedClass() throws Exception {
    
        // Create a dummy class with an annotation for testing
        @SuppressWarnings("unused")
        @Deprecated
        class AnnotatedClass {}

        // Prepare mocks
        JarEntry mockJarEntry = mock(JarEntry.class);
        when(mockJarEntry.getName()).thenReturn(AnnotatedClass.class.getName().replace('.', '/') + ".class");
        URL url = AnnotatedClass.class.getProtectionDomain().getCodeSource().getLocation();
        Entry entry = new Entry(url, mockJarEntry);

        // No exception should be thrown, but the exception will be logged.
        assertDoesNotThrow(() -> jarScannerService.processJarEntry(entry));
    }

    @Test
    void scanJarEntry_processEntryForValidClassFile() {
        JarEntry mockEntry = mock(JarEntry.class);
        when(mockEntry.isDirectory()).thenReturn(false);
        when(mockEntry.getName()).thenReturn("org/example/MyClass.class");

        File mockJarFile = mock(File.class);
        when(mockJarFile.getPath()).thenReturn("/path/to/jarfile.jar");

        Optional<Entry> result = jarScannerService.scanJarEntry(mockEntry, mockJarFile);
        assertTrue(result.isPresent());
        assertEquals("org/example/MyClass.class", result.get().jarEntry().getName());
        assertTrue(result.get().url().toString().startsWith("jar:file:/path/to/jarfile.jar!/"));

        // No exception should be thrown
        assertDoesNotThrow(() -> jarScannerService.processJarEntry(result.get()));
    }

    @Test
    void processJarEntry_shouldHandleClassNotFoundException() {
        // Create a mock Entry with a class that does not exist
        JarEntry mockJarEntry = mock(JarEntry.class);
        when(mockJarEntry.getName()).thenReturn("org/example/NonExistentClass.class");
        URL mockUrl = mock(URL.class);
        Entry entry = new Entry(mockUrl, mockJarEntry);

        // No exception should be thrown, but the exception will be logged.
        assertDoesNotThrow(() -> jarScannerService.processJarEntry(entry));
    }

    @Test
    void scanJarEntry_returnsEmptyForDirectoryEntry() {
        JarEntry mockEntry = mock(JarEntry.class);
        when(mockEntry.isDirectory()).thenReturn(true);
        when(mockEntry.getName()).thenReturn("org/example/");

        File mockJarFile = mock(File.class);

        Optional<Entry> result = jarScannerService.scanJarEntry(mockEntry, mockJarFile);
        assertTrue(result.isEmpty());
    }

    @Test
    void scanJarEntry_returnsEmptyForNonClassFile() {
        JarEntry mockEntry = mock(JarEntry.class);
        when(mockEntry.isDirectory()).thenReturn(false);
        when(mockEntry.getName()).thenReturn("org/example/resource.txt");

        File mockJarFile = mock(File.class);

        Optional<Entry> result = jarScannerService.scanJarEntry(mockEntry, mockJarFile);
        assertTrue(result.isEmpty());
    }

    @Test
    void scanJarEntry_returnsEntryForValidClassFile() {
        JarEntry mockEntry = mock(JarEntry.class);
        when(mockEntry.isDirectory()).thenReturn(false);
        when(mockEntry.getName()).thenReturn("org/example/MyClass.class");

        File mockJarFile = mock(File.class);
        when(mockJarFile.getPath()).thenReturn("/path/to/jarfile.jar");

        Optional<Entry> result = jarScannerService.scanJarEntry(mockEntry, mockJarFile);
        assertTrue(result.isPresent());
        assertEquals(mockEntry, result.get().jarEntry());
        assertTrue(result.get().url().toString().startsWith("jar:file:/path/to/jarfile.jar!/"));
    }
}
