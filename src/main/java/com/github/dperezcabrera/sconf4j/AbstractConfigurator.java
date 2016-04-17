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

import java.util.Properties;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 * @param <K>
 */
public abstract class AbstractConfigurator<K> implements Configurator<K> {

    private static final String NOT_SUPPORTED_GET_METHOD = "Not supported 'get' method.";
    protected final Class<K> type;
    protected volatile ConfiguratorContext context;

    public AbstractConfigurator(Class<K> type) {
        this.type = type;
    }

    @Override
    public Class<K> getType() {
        return type;
    }

    @Override
    public <T> T get(Class<T> target) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_GET_METHOD);
    }

    @Override
    public <T> T get(Class<T> target, String param) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_GET_METHOD);
    }

    @Override
    public <T> T get(Class<T> target, Properties properties) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_GET_METHOD);
    }

    @Override
    public <T> T get(Class<T> target, Properties properties, String param) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_GET_METHOD);
    }

    @Override
    public <T> void subscribe(Class<T> target, Subscriber<T> subscriber) {
        throw new UnsupportedOperationException("Not supported 'subscribe' method.");
    }
}
