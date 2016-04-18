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
import com.github.dperezcabrera.sconf4j.factories.CacheableBeanFactory;
import com.github.dperezcabrera.sconf4j.factories.SimpleStringBeanFactory;
import com.github.dperezcabrera.sconf4j.core.utils.PropertyUtils;
import com.github.dperezcabrera.sconf4j.core.utils.Utilities;
import java.util.LinkedHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class BasicFactoryLoader extends AbstractLoader {

    public BasicFactoryLoader() {
        super(getFactories());
    }

    private static BeanFactory build(BeanFactory factory) {
        return new CacheableBeanFactory(factory);
    }

    private static BeanFactory build(Function<String, Object> function) {
        return build(new SimpleStringBeanFactory(function));
    }

    private static LinkedHashMap<Predicate<Class<?>>, BeanFactory> getFactories() {
        LinkedHashMap<Predicate<Class<?>>, BeanFactory> result = new LinkedHashMap<>();
        result.put(type -> type == Class.class, build(PropertyUtils::classForName));
        result.put(Class::isEnum, build((context, propertyName, typeSupplier) -> Enum.valueOf((Class<? extends Enum>) typeSupplier.get(), context.getDataProvider().getProperty(propertyName))));
        result.put(type -> type == String.class, build((context, propertyName, typeSupplier) -> context.getDataProvider().getProperty(propertyName)));
        result.put(Utilities::isBoolean, build(Boolean::valueOf));
        result.put(Utilities::isByte, build(Byte::valueOf));
        result.put(Utilities::isDouble, build(Double::valueOf));
        result.put(Utilities::isFloat, build(Float::valueOf));
        result.put(Utilities::isInteger, build(Integer::valueOf));
        result.put(Utilities::isLong, build(Long::valueOf));
        result.put(Utilities::isShort, build(Short::valueOf));
        result.put(Utilities::isCharacter, build((context, propertyName, typeSupplier) -> {
            Object obj = null;
            String value = context.getDataProvider().getProperty(propertyName);
            if (value.length() == 1) {
                obj = value.charAt(0);
            } else {
                throw new ClassCastException("\"" + value + "\" cannot be cast to char");
            }
            return obj;
        }));
        return result;
    }
}
