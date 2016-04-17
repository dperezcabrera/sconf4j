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

import com.github.dperezcabrera.sconf4j.core.BeanInitializer;
import com.github.dperezcabrera.sconf4j.core.ConfiguratorException;
import com.github.dperezcabrera.sconf4j.core.DataContext;
import com.github.dperezcabrera.sconf4j.core.TypeSupplier;
import com.github.dperezcabrera.sconf4j.core.utils.PropertyUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class BeanInitializerBase implements BeanInitializer {

    @FunctionalInterface
    public static interface Adder {

        void add(Object target, String name, Supplier<Object> childSupplier, Class<?> childType);
    }

    private Adder adder;

    public BeanInitializerBase(Adder adder) {
        this.adder = adder;
    }

    private void check(boolean throwException, String message) {
        if (throwException) {
            throw new ConfiguratorException(message);
        }
    }

    private List<String> sortProperties(Set<String> names) {
        List<String> result = new ArrayList<>(names.size());
        names.remove("");
        if (!names.isEmpty()) {
            int min = Integer.MAX_VALUE;
            int max = -1;
            int indexCounter = 0;
            List<String> properties = new ArrayList<>(names.size());
            for (String name : names) {
                if (PropertyUtils.isIndex(name)) {
                    int value = Integer.parseInt(name);
                    max = Math.max(max, value);
                    min = Math.min(min, value);
                    indexCounter++;
                } else {
                    properties.add(name);
                }
            }

            check(max >= 0 && max - min != indexCounter - 1, "Wrong indexes, some index is missing");
            IntStream.range(0, indexCounter).mapToObj(Integer::toString).forEach(result::add);
            properties.stream().sorted().forEach(result::add);
        }
        return result;
    }

    @Override
    public Object init(Object obj, DataContext dataSet, String propertyName, TypeSupplier typeSupplier) {
        List<String> names = sortProperties(dataSet.getDataProvider().getSubProperties(propertyName));
        if (!names.isEmpty()) {
            for (String childProperty : names) {
                TypeSupplier childTypeSupplier = typeSupplier.getPropertyTypeSupplier(childProperty);
                Supplier<Object> childSupplier = () -> dataSet.getBeanFactory().get(dataSet, String.join(PropertyUtils.PROPERTY_DELIMITER, propertyName, childProperty), childTypeSupplier);
                adder.add(obj, childProperty, childSupplier, childTypeSupplier.get());
            }
        }
        return obj;
    }
}
