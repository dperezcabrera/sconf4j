package com.github.dperezcabrera.sconf4j.fluent;

import java.util.Set;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public interface DataProvider {

    public String getProperty(String Key);

    public Set<String> getPropertyNames();
}
