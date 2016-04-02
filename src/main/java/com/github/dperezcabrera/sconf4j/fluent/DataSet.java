package com.github.dperezcabrera.sconf4j.fluent;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public interface DataSet {

    public DataProvider getDataProvider();

    public TypeAdapter getAdapter(Class<?> type);

    public <T> T newProxyInstance(String prefix, Class<T> type);
}
