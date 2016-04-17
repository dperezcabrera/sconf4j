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
import com.github.dperezcabrera.sconf4j.core.ConfiguratorException;
import java.util.LinkedHashMap;
import java.util.function.Predicate;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class NoContructorFoundLoader extends AbstractLoader {

    public NoContructorFoundLoader() {
        super(getFactories());
    }

    private static LinkedHashMap<Predicate<Class<?>>, BeanFactory> getFactories() {

        LinkedHashMap<Predicate<Class<?>>, BeanFactory> result = new LinkedHashMap<>();
        result.put(type -> true, (dataSet, propertyName, typeSupplier) -> {
            throw new ConfiguratorException("Could not build an Object to property '"+propertyName+"' with type: "+typeSupplier.get());
        });
        return result;
    }
}
