package com.github.dperezcabrera.sconf4j.fluent;

import java.util.Properties;
import java.util.Set;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class PropertiesDataProvider implements DataProvider {

    Properties properties;

    public PropertiesDataProvider(Properties properties) {
        this.properties = properties;
    }
    
    @Override
    public String getProperty(String Key) {
        return properties.getProperty(Key);
    }

    @Override
    public Set<String> getPropertyNames() {
        return properties.stringPropertyNames();
    }
}
