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
package com.github.dperezcabrera.sconf4j.factories;

import com.github.dperezcabrera.sconf4j.core.BeanFactory;
import com.github.dperezcabrera.sconf4j.core.BeanInitializer;
import com.github.dperezcabrera.sconf4j.core.Cacheable;
import com.github.dperezcabrera.sconf4j.core.DataContext;
import com.github.dperezcabrera.sconf4j.core.TypeSupplier;
import com.github.dperezcabrera.sconf4j.core.utils.ReadWriteLockUtility;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.dperezcabrera.sconf4j.core.BeanContainer;
import com.github.dperezcabrera.sconf4j.core.ConfiguratorException;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class BeanContainerBase implements BeanContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanContainerBase.class);

    Map<Predicate<Class<?>>, BeanFactory> beanFactories = new LinkedHashMap<>();
    Map<Predicate<Class<?>>, BeanInitializer> beanInitializers = new LinkedHashMap<>();

    ReadWriteLockUtility lockUtility = new ReadWriteLockUtility();
    Map<String, Object> cache = new HashMap<>();

    @Override
    public void register(Predicate<Class<?>> predicate, BeanInitializer initializer) {
        beanInitializers.put(predicate, initializer);
    }

    @Override
    public void register(Predicate<Class<?>> predicate, BeanFactory factory) {
        beanFactories.put(predicate, factory);
    }

    private Object get(BeanFactory factory, DataContext dataSet, String propertyName, TypeSupplier typeSupplier) {
        Object result;
        Object[] obj = lockUtility.read(() -> cache.containsKey(propertyName), () -> new Object[]{cache.get(propertyName)});
        if (obj != null) {
            result = obj[0];
        } else {
            result = process(factory.get(dataSet, propertyName, typeSupplier), dataSet, propertyName, typeSupplier);
        }
        return result;
    }

    private Object process(final Object obj, DataContext dataSet, String propertyName, TypeSupplier typeSupplier) {
        Object result;
        if (Cacheable.class.isInstance(obj)) {
            result = init(((Cacheable) obj).get(), dataSet, propertyName, typeSupplier);
            lockUtility.write(() -> cache.put(propertyName, result));
        } else {
            result = init(obj, dataSet, propertyName, typeSupplier);
        }
        return result;
    }

    private Object init(Object obj, DataContext dataSet, String propertyName, TypeSupplier typeSupplier) {
        Object target = obj;
        Class<?> type = typeSupplier.get();
        for (Entry<Predicate<Class<?>>, BeanInitializer> e : beanInitializers.entrySet()) {
            if (e.getKey().test(type)) {
                return e.getValue().init(target, dataSet, propertyName, typeSupplier);
            }
        }
        return obj;
    }

    @Override
    public Object get(DataContext dataSet, String propertyName, TypeSupplier typeSupplier) {
        if (!dataSet.getDataProvider().getSubProperties(propertyName).isEmpty()) {
            Class<?> type = typeSupplier.get();
            for (Entry<Predicate<Class<?>>, BeanFactory> e : beanFactories.entrySet()) {
                if (e.getKey().test(type)) {
                    return get(e.getValue(), dataSet, propertyName, typeSupplier);
                }
            }
            throw new ConfiguratorException("There isn't a valid factory for "+type);
        }
        return null;
    }
}
