package com.github.dperezcabrera.sconf4j;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class InitializeConfiguratorException extends RuntimeException {

    public InitializeConfiguratorException(String message) {
        super(message);
    }

    public InitializeConfiguratorException(String message, Throwable cause) {
        super(message, cause);
    }
}
