package com.github.dperezcabrera.sconf4j.fluent;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public interface InstanceBuilder {

    <T> T build(DataSet dataSet, String prefix, Class<T> type);
}
