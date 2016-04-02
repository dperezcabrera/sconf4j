package com.github.dperezcabrera.sconf4j;

import java.util.Properties;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 * @param <K>
 */
public interface Configurator<K> {

    public <T> T get(Class<T> target);
    public <T> T get(Class<T> target, String param);
    public <T> T get(Class<T> target, Properties properties);
    public <T> T get(Class<T> target, Properties properties, String param);
    public <T> void subscribe(Class<T> target, Subscriber<T> subscriber);
}
