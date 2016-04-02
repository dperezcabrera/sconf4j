package com.github.dperezcabrera.sconf4j;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public interface Subscriber<T> {
    public void onChange(T obj);
}
