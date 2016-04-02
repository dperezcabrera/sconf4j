package com.github.dperezcabrera.sconf4j.fluent;

import java.lang.reflect.Method;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public interface TypeAdapter {

    Object adapt(DataSet dataSet, String propertyName, Method invokeMethod, int dimension);
}
