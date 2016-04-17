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
package com.github.dperezcabrera.sconf4j.factories.loader;

import com.github.dperezcabrera.sconf4j.core.BeanFactory;
import com.github.dperezcabrera.sconf4j.core.utils.PropertyUtils;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class MappingFactoryLoader extends AbstractLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(MappingFactoryLoader.class);

    public MappingFactoryLoader(Map<Class<?>, Class<?>> interfacesMapping) {
        super(getFactories(interfacesMapping));
    }

    private static LinkedHashMap<Predicate<Class<?>>, BeanFactory> getFactories(Map<Class<?>, Class<?>> interfacesMapping) {
        LinkedHashMap<Predicate<Class<?>>, BeanFactory> result = new LinkedHashMap<>();
        Map<Class<?>, Class<?>> mapping = new HashMap<>();
        for (Entry<Class<?>, Class<?>> e : interfacesMapping.entrySet()) {
            if (!PropertyUtils.isCommonObject(e.getValue())) {
                LOGGER.warn("The default class {} for {} isn't a valid class. It will be ignored.", e.getKey(), e.getValue());
            } else if (!e.getKey().isAssignableFrom(e.getValue())) {
                LOGGER.warn("The default class {} isn't assignable {}. It will be ignored.", e.getKey(), e.getValue());
            } else {
                mapping.put(e.getKey(), e.getValue());
            }
        }
        result.put(interfacesMapping::containsKey, (dataSet, propertyName, typeSupplier) -> dataSet.getBeanFactory().get(dataSet, propertyName, typeSupplier.changeType(mapping.get(typeSupplier.get()))));
        return result;
    }
}
