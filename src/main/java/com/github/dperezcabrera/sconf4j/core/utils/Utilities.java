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

import java.util.Collection;
import java.util.Map;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public final class Utilities {

    public static final String EMPTY_STRING = "";
    
    private Utilities() {
    }

    public static boolean isEmpty(int[] s) {
        return s == null || s.length == 0;
    }

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static boolean isEmpty(Collection<?> s) {
        return s == null || s.isEmpty();
    }
    
    public static boolean isEmpty(Map<?, ?> s) {
        return s == null || s.isEmpty();
    }

    public static boolean isBoolean(Class<?> type) {
        return type == Boolean.class || type == boolean.class;
    }

    public static boolean isByte(Class<?> type) {
        return type == Byte.class || type == byte.class;
    }

    public static boolean isCharacter(Class<?> type) {
        return type == Character.class || type == char.class;
    }

    public static boolean isDouble(Class<?> type) {
        return type == Double.class || type == double.class;
    }

    public static boolean isFloat(Class<?> type) {
        return type == Float.class || type == float.class;
    }

    public static boolean isInteger(Class<?> type) {
        return type == Integer.class || type == int.class;
    }

    public static boolean isLong(Class<?> type) {
        return type == Long.class || type == long.class;
    }

    public static boolean isShort(Class<?> type) {
        return type == Short.class || type == short.class;
    }
}
