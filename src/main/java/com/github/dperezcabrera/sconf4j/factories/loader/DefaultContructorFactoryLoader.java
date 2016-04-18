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
import com.github.dperezcabrera.sconf4j.core.TypeSupplierBase;
import com.github.dperezcabrera.sconf4j.core.utils.PropertyUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.function.Predicate;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class DefaultContructorFactoryLoader extends AbstractLoader {

    public DefaultContructorFactoryLoader() {
        super(getFactories());
    }

    private static LinkedHashMap<Predicate<Class<?>>, BeanFactory> getFactories() {

        LinkedHashMap<Predicate<Class<?>>, BeanFactory> result = new LinkedHashMap<>();
        result.put(DefaultContructorFactoryLoader::checkPredicate,
                (context, propertyName, typeSupplier) -> {
                    String propertyValue = context.getDataProvider().getProperty(propertyName);
                    Class<?> type = typeSupplier.get();
                    if (propertyValue != null && !propertyValue.equals(type.getName())) {
                        return context.getBeanFactory().get(context, propertyName, new TypeSupplierBase(() -> PropertyUtils.classForName(propertyValue)));
                    } else {
                        return PropertyUtils.newInstance(type);
                    }
                }
        );
        return result;
    }

    private static boolean checkPredicate(Class<?> type) {
        boolean result = false;
        if (PropertyUtils.isCommonObject(type)) {
            for (Constructor constructor : type.getConstructors()) {
                if (constructor.getParameterCount() == 0) {
                    return true;
                }
            }
        }
        return result;
    }
}
