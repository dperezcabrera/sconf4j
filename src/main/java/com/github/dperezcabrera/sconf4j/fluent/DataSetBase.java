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
package com.github.dperezcabrera.sconf4j.fluent;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class DataSetBase implements DataSet {

    private static final Set<Class<?>> CACHEABLE_TYPES = new HashSet<>(
            Arrays.asList(String.class, Long.class, Character.class, Byte.class,
                    Short.class, Integer.class, Boolean.class, Float.class, Double.class));

    DataProvider dataProvider;
    InstanceBuilder instanceBuilder;
    TypeAdapter typeAdapter;
    ReadWriteLockUtility lockUtility;
    Map<String, Object> cache = new HashMap<>();

    public DataSetBase(DataProvider dataProvider, InstanceBuilder instanceBuilder, TypeAdapter typeAdapter) {
        this.dataProvider = dataProvider;
        this.instanceBuilder = instanceBuilder;
        this.typeAdapter = new TypeAdapterCache(typeAdapter);
        this.lockUtility = new ReadWriteLockUtility();
    }

    @Override
    public DataProvider getDataProvider() {
        return dataProvider;
    }

    @Override
    public TypeAdapter getAdapter(Class<?> type) {
        return typeAdapter;
    }

    @Override
    public <T> T newProxyInstance(String propertyName, Class<T> type) {
        T result = instanceBuilder.build(this, propertyName, type);
        lockUtility.write(() -> cache.put(propertyName, result));
        return result;
    }

    private class TypeAdapterCache implements TypeAdapter {

        private TypeAdapter typeAdapter;

        public TypeAdapterCache(TypeAdapter typeAdapter) {
            this.typeAdapter = typeAdapter;
        }

        @Override
        public Object adapt(DataSet dataSet, String propertyName, Method invokeMethod, int dimension) {
            Object result;
            Object[] obj = lockUtility.read(() -> cache.containsKey(propertyName), () -> new Object[]{cache.get(propertyName)});
            if (obj != null) {
                result = obj[0];
            } else {
                result = typeAdapter.adapt(dataSet, propertyName, invokeMethod, dimension);
                if (result != null && isCacheable(result.getClass())) {
                    lockUtility.write(() -> cache.put(propertyName, result));
                }
            }
            return result;
        }

        private boolean isCacheable(Class<?> type) {
            return type.isPrimitive() || CACHEABLE_TYPES.contains(type);
        }
    }
}
