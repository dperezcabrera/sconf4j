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
package com.github.dperezcabrera.sconf4j.core.utils;

import com.github.dperezcabrera.sconf4j.core.ConfiguratorException;
import com.github.dperezcabrera.sconf4j.core.Mapping;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public final class PropertyUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyUtils.class);

    public  static final String PROPERTY_DELIMITER = ".";
    private static final Pattern INDEX_PATTERN = Pattern.compile("^\\d+$");
    private static final List<String> ALLOW_PREFIX_LIST = Arrays.asList("get", "is");

    private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_MAP = primitiveWrapperMap();

    private PropertyUtils() {
    }

    public static Set<String> subproperties(Set<String> properties, String prefix) {
        Set<String> result = new HashSet<>();
        String currentName;
        String currentPrefix = prefix.concat(PROPERTY_DELIMITER);
        for (String name : properties) {
            if (name.startsWith(currentPrefix)) {
                int newNameIndex = name.indexOf(PROPERTY_DELIMITER, prefix.length() + 1);
                if (newNameIndex < 0) {
                    newNameIndex = name.length();
                }
                currentName = name.substring(prefix.length() + 1, newNameIndex);
                result.add(currentName);
            }
        }
        if (properties.contains(prefix)) {
            result.add(Utilities.EMPTY_STRING);
        }
        return result;
    }

    public static boolean isIndex(String value) {
        return INDEX_PATTERN.matcher(value).matches();
    }

    public static String getPropertyFromMethod(Method method, String prefix) {
        String result = getPropertyFromMethod(method);
        if (!Utilities.isEmpty(result) && !Utilities.isEmpty(prefix)) {
            result = String.join(PROPERTY_DELIMITER, prefix, result);
        }
        return result;
    }

    public static String getPropertyFromMethod(Method method) {
        for (String methodPrefix : ALLOW_PREFIX_LIST) {
            if (method.getName().startsWith(methodPrefix)) {
                Mapping mapping = method.getAnnotation(Mapping.class);
                String tail;
                if (mapping != null && !mapping.property().isEmpty()) {
                    tail = mapping.property();
                } else {
                    tail = method.getName().substring(methodPrefix.length());
                    tail = tail.substring(0, 1).toLowerCase().concat(tail.substring(1));
                }
                return tail;
            }
        }
        return null;
    }

    public static boolean isCommonObject(Class<?> type) {
        return !type.isInterface() && !type.isEnum() && !PRIMITIVE_WRAPPER_MAP.containsKey(type) && !type.isArray();
    }

    public static Class<?> classForName(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException ex) {
            String message = name + " isn't a known class";
            LOGGER.error(message, ex);
            throw new ConfiguratorException(message, ex);
        }
    }

    public static <T> T newInstance(Class<T> type) {
        try {
            return type.newInstance();
        } catch (Exception ex) {
            String message = type + " couldn't be instanciated";
            LOGGER.error(message, ex);
            throw new ConfiguratorException(message, ex);
        }
    }

    private static int recursiveTypeDistance(Class<?> type, Class<?> valueType) {
        int result = 0;
        if (type != valueType) {
            result = 1 + recursiveTypeDistance(type, valueType.getSuperclass());
        }
        return result;
    }

    public static int typeDistance(Class<?> type, Class<?> valueType) {
        int result;
        if (type == valueType) {
            result = 0;
        } else if (PRIMITIVE_WRAPPER_MAP.get(type) == valueType) {
            result = 1;
        } else if (type.isAssignableFrom(valueType)) {
            result = 1;
            if (!type.isInterface()) {
                result = recursiveTypeDistance(type, valueType);
            }
        } else {
            result = -1;
        }

        return result;
    }

    private static Map<Class<?>, Class<?>> primitiveWrapperMap() {
        Map<Class<?>, Class<?>> result = new HashMap<>();
        result.put(boolean.class, Boolean.class);
        result.put(byte.class, Byte.class);
        result.put(char.class, Character.class);
        result.put(double.class, Double.class);
        result.put(float.class, Float.class);
        result.put(int.class, Integer.class);
        result.put(long.class, Long.class);
        result.put(short.class, Short.class);
        Set<Class<?>> keys = new HashSet<>(result.keySet());
        for (Class<?> key : keys) {
            result.put(result.get(key), key);
        }
        return result;
    }
}
