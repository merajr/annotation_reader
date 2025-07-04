package org.data.sample;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A custom annotation for testing purposes.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface TestSuite {
    String name();
}

/**
 * A simple annotation to mark a class as a component.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Component {}


/**
 * A sample class that uses a mix of standard and custom annotations.
 * This class is designed to be a test subject for the AnnotationScanner.
 */
@Deprecated
@TestSuite(name = "SampleTests")
@Component
public class SampleAnnotatedClass {

    private String message = "Hello, Annotations!";

    public void doSomething() {
        System.out.println(message);
    }
}

/**
 * A second class with no annotations to ensure the scanner can handle both cases.
 */
class NonAnnotatedClass {
    public void doNothing() {
        // This class is intentionally left blank.
    }
}
