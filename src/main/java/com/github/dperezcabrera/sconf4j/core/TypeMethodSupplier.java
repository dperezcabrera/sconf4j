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
package com.github.dperezcabrera.sconf4j.core;

import com.github.dperezcabrera.sconf4j.core.utils.PropertyUtils;
import java.lang.reflect.Method;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class TypeMethodSupplier implements TypeSupplier {

    private Method invokedMethod;
    private int dimensions;
    private Class<?> type;

    
    private TypeMethodSupplier(Method invokedMethod, int dimensions, Class<?> type) {
        this.invokedMethod = invokedMethod;
        this.dimensions = dimensions;
        this.type = type;
    }
    
    public TypeMethodSupplier(Method invokedMethod, int dimensions) {
        this(invokedMethod, dimensions, getSubtype(dimensions, invokedMethod));
    }
    
    @Override
    public TypeSupplier changeType(Class<?> type) {
        return new TypeMethodSupplier(invokedMethod, dimensions, type);
    }

    @Override
    public TypeSupplier getPropertyTypeSupplier(String propertyName) {
        if (propertyName == null){
            throw new NullPointerException("PropertyName cant not be null");
        }
        TypeSupplier result;
        if (PropertyUtils.isIndex(propertyName)) {
            result = new TypeMethodSupplier(invokedMethod, dimensions + 1);
        } else {
            result = new TypeMethodSupplier(getMethod(propertyName), 0);
        }
        return result;
    }

    private Method getMethod(String property) {
        for (Method method : type.getMethods()) {
            if (property.equals(PropertyUtils.getPropertyFromMethod(method))){
                return method;
            }
        }
        throw new ConfiguratorException("No method found for property '" + property + "'");
    }

    @Override
    public Class<?> get() {
        return type;
    }

    private static Class<?> arraySubtypeGetterHelper(int dimensions, Class<?> c) {
        if (dimensions <= 0) {
            return c;
        } else {
            return arraySubtypeGetterHelper(dimensions - 1, c.getComponentType());
        }
    }

    private static Class<?> getSubtype(int dimensions, Method m) {
        Class<?> result = m.getReturnType();
        if (dimensions > 0) {
            if (result.isArray()) {
                result = arraySubtypeGetterHelper(dimensions, result);
            } else {
                result = m.getAnnotation(Mapping.class).value()[dimensions - 1];
            }
        }
        return result;
    }
}
