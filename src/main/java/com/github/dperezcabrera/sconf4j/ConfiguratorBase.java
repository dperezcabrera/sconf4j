/*
 * Copyright (C) 2016 David Pérez Cabrera <dperezcabrera@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.dperezcabrera.sconf4j;

import com.github.dperezcabrera.sconf4j.core.DataContextBase;
import com.github.dperezcabrera.sconf4j.core.data.PropertiesDataProvider;
import com.github.dperezcabrera.sconf4j.core.TypeSupplierBase;
import java.util.Properties;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class ConfiguratorBase<K> implements Configurator<K> {

    private static final String NOT_SUPPORTED_YET_EXCEPTION_MESSAGE = "Not supported yet.";

    protected final Class<K> type;
    protected volatile ConfiguratorContext context;

    public ConfiguratorBase(Class<K> type, ConfiguratorContext context) {
        this.type = type;
        this.context = context;
    }

    @Override
    public Class<K> getType() {
        return type;
    }

    public ConfiguratorContext getContext() {
        return context;
    }

    public void setContext(ConfiguratorContext context) {
        this.context = context;
    }

    @Override
    public <T> T get(Class<T> target) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET_EXCEPTION_MESSAGE);
    }

    @Override
    public <T> T get(Class<T> target, String param) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET_EXCEPTION_MESSAGE);
    }

    @Override
    public <T> T get(Class<T> target, Properties properties) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET_EXCEPTION_MESSAGE);
    }

    @Override
    public <T> void subscribe(Class<T> target, Subscriber<T> subscriber) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET_EXCEPTION_MESSAGE);
    }

    @Override
    public <T> T get(Class<T> target, Properties properties, String param) {
        ConfiguratorContext cc = context;
        return (T) cc.getBeanContainer().get(new DataContextBase(new PropertiesDataProvider(properties), cc.getBeanContainer()), param, new TypeSupplierBase(() -> target));
    }
}
