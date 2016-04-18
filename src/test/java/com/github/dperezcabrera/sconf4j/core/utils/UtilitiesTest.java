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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class UtilitiesTest {

    /**
     * Test of isBoolean method, of class Utilities.
     */
    @Test
    public void testIsBoolean() {
        System.out.println("isBoolean");
        Class type = boolean.class;
        boolean expResult = true;
        boolean result = Utilities.isBoolean(type);
        assertEquals(expResult, result);
    }

    /**
     * Test of isBoolean method, of class Utilities.
     */
    @Test
    public void testIsBooleanWrapper() {
        System.out.println("isBooleanWrapper");
        Class type = Boolean.class;
        boolean expResult = true;
        boolean result = Utilities.isBoolean(type);
        assertEquals(expResult, result);
    }

    /**
     * Test of isBoolean method, of class Utilities.
     */
    @Test
    public void testIsBooleanNull() {
        System.out.println("isBooleanNull");
        Class type = null;
        boolean expResult = false;
        boolean result = Utilities.isBoolean(type);
        assertEquals(expResult, result);
    }

    /**
     * Test of isByte method, of class Utilities.
     */
    @Test
    public void testIsByte() {
        System.out.println("isByte");
        Class type = byte.class;
        boolean expResult = true;
        boolean result = Utilities.isByte(type);
        assertEquals(expResult, result);
    }

    /**
     * Test of isByte method, of class Utilities.
     */
    @Test
    public void testIsByteWrapper() {
        System.out.println("isByteWrapper");
        Class type = Byte.class;
        boolean expResult = true;
        boolean result = Utilities.isByte(type);
        assertEquals(expResult, result);
    }

    /**
     * Test of isByte method, of class Utilities.
     */
    @Test
    public void testIsByteString() {
        System.out.println("isByte");
        Class type = String.class;
        boolean expResult = false;
        boolean result = Utilities.isByte(type);
        assertEquals(expResult, result);
    }

    /**
     * Test of isCharacter method, of class Utilities.
     */
    @Test
    public void testIsCharacter() {
        System.out.println("isCharacter");
        Class type = char.class;
        boolean expResult = true;
        boolean result = Utilities.isCharacter(type);

        assertEquals(expResult, result);
    }

    /**
     * Test of isCharacter method, of class Utilities.
     */
    @Test
    public void testIsCharacterWrapper() {
        System.out.println("isCharacterWrapper");
        Class type = Character.class;
        boolean expResult = true;
        boolean result = Utilities.isCharacter(type);

        assertEquals(expResult, result);
    }

    /**
     * Test of isCharacter method, of class Utilities.
     */
    @Test
    public void testIsCharacterString() {
        System.out.println("isCharacterString");
        Class type = String.class;
        boolean expResult = false;
        boolean result = Utilities.isCharacter(type);

        assertEquals(expResult, result);
    }

    /**
     * Test of isEmpty method, of class Utilities.
     */
    @Test
    public void testIsEmpty_intArrNull() {
        System.out.println("isEmpty(int[]): null");
        int[] s = null;
        boolean expResult = true;
        boolean result = Utilities.isEmpty(s);

        assertEquals(expResult, result);
    }

    /**
     * Test of isEmpty method, of class Utilities.
     */
    @Test
    public void testIsEmpty_intArrEmpty() {
        System.out.println("isEmpty(int[]): empty");
        int[] s = new int[0];
        boolean expResult = true;

        boolean result = Utilities.isEmpty(s);

        assertEquals(expResult, result);
    }

    /**
     * Test of isEmpty method, of class Utilities.
     */
    @Test
    public void testIsEmpty_intArr() {
        System.out.println("isEmpty(int[])");
        int[] s = new int[1];
        boolean expResult = false;

        boolean result = Utilities.isEmpty(s);

        assertEquals(expResult, result);
    }

    /**
     * Test of isEmpty method, of class Utilities.
     */
    @Test
    public void testIsEmpty_StringEmpty() {
        System.out.println("isEmpty(String): Empty");
        String s = "";
        boolean expResult = true;

        boolean result = Utilities.isEmpty(s);

        assertEquals(expResult, result);
    }

    /**
     * Test of isEmpty method, of class Utilities.
     */
    @Test
    public void testIsEmpty_StringNull() {
        System.out.println("isEmpty(String): Null");
        String s = null;
        boolean expResult = true;

        boolean result = Utilities.isEmpty(s);

        assertEquals(expResult, result);
    }

    /**
     * Test of isEmpty method, of class Utilities.
     */
    @Test
    public void testIsEmpty_String() {
        System.out.println("isEmpty(String)");
        String s = "asd";
        boolean expResult = false;

        boolean result = Utilities.isEmpty(s);

        assertEquals(expResult, result);
    }

    /**
     * Test of isEmpty method, of class Utilities.
     */
    @Test
    public void testIsEmpty_Collection() {
        System.out.println("isEmpty(Collection)");
        Collection<String> s = Arrays.asList("a");

        boolean expResult = false;

        boolean result = Utilities.isEmpty(s);

        assertEquals(expResult, result);
    }

    /**
     * Test of isEmpty method, of class Utilities.
     */
    @Test
    public void testIsEmpty_CollectionEmpty() {
        System.out.println("isEmpty(Collection): Empty");
        Collection<String> s = Arrays.asList();

        boolean expResult = true;

        boolean result = Utilities.isEmpty(s);

        assertEquals(expResult, result);
    }

    /**
     * Test of isEmpty method, of class Utilities.
     */
    @Test
    public void testIsEmpty_CollectionNull() {
        System.out.println("isEmpty(Collection): Null");
        Collection<String> s = null;

        boolean expResult = true;

        boolean result = Utilities.isEmpty(s);

        assertEquals(expResult, result);
    }

    /**
     * Test of isEmpty method, of class Utilities.
     */
    @Test
    public void testIsEmpty_Map() {
        System.out.println("isEmpty(Map)");
        Map<Integer, Integer> s = new HashMap<>();
        s.put(1, 1);
        boolean expResult = false;

        boolean result = Utilities.isEmpty(s);

        assertEquals(expResult, result);
    }

    /**
     * Test of isEmpty method, of class Utilities.
     */
    @Test
    public void testIsEmpty_MapEmpty() {
        System.out.println("isEmpty(Map): Empty");
        Map<Integer, Integer> s = new HashMap<>();
        boolean expResult = true;

        boolean result = Utilities.isEmpty(s);

        assertEquals(expResult, result);
    }

    /**
     * Test of isEmpty method, of class Utilities.
     */
    @Test
    public void testIsEmpty_MapNull() {
        System.out.println("isEmpty(Map): Null");
        Map<Integer, Integer> s = null;
        boolean expResult = true;

        boolean result = Utilities.isEmpty(s);

        assertEquals(expResult, result);
    }

    /**
     * Test of isDouble method, of class Utilities.
     */
    @Test
    public void testIsDouble() {
        System.out.println("isDouble");
        Class<?> type = double.class;
        boolean expResult = true;

        boolean result = Utilities.isDouble(type);

        assertEquals(expResult, result);
    }

    /**
     * Test of isDouble method, of class Utilities.
     */
    @Test
    public void testIsDoubleWrapper() {
        System.out.println("isDouble: Wrapper");
        Class<?> type = Double.class;
        boolean expResult = true;

        boolean result = Utilities.isDouble(type);

        assertEquals(expResult, result);
    }

    /**
     * Test of isDouble method, of class Utilities.
     */
    @Test
    public void testIsDoubleOther() {
        System.out.println("isDouble: String");
        Class<?> type = String.class;
        boolean expResult = false;

        boolean result = Utilities.isDouble(type);

        assertEquals(expResult, result);
    }

    /**
     * Test of isFloat method, of class Utilities.
     */
    @Test
    public void testIsFloat() {
        System.out.println("isFloat");
        Class<?> type = float.class;

        boolean expResult = true;
        boolean result = Utilities.isFloat(type);

        assertEquals(expResult, result);
    }

    /**
     * Test of isFloat method, of class Utilities.
     */
    @Test
    public void testIsFloatWrapper() {
        System.out.println("isFloat: Wrapper");
        Class<?> type = Float.class;

        boolean expResult = true;
        boolean result = Utilities.isFloat(type);

        assertEquals(expResult, result);
    }

    /**
     * Test of isFloat method, of class Utilities.
     */
    @Test
    public void testIsFloatNull() {
        System.out.println("isFloat: Null");
        Class<?> type = null;

        boolean expResult = false;
        boolean result = Utilities.isFloat(type);

        assertEquals(expResult, result);
    }

    /**
     * Test of isInteger method, of class Utilities.
     */
    @Test
    public void testIsInteger() {
        System.out.println("isInteger");
        Class<?> type = int.class;
        boolean expResult = true;

        boolean result = Utilities.isInteger(type);

        assertEquals(expResult, result);
    }

    /**
     * Test of isInteger method, of class Utilities.
     */
    @Test
    public void testIsIntegerWrapper() {
        System.out.println("isInteger: Wrapper");
        Class<?> type = Integer.class;
        boolean expResult = true;

        boolean result = Utilities.isInteger(type);

        assertEquals(expResult, result);
    }

    /**
     * Test of isInteger method, of class Utilities.
     */
    @Test
    public void testIsIntegerNull() {
        System.out.println("isInteger: Null");
        Class<?> type = null;
        boolean expResult = false;

        boolean result = Utilities.isInteger(type);

        assertEquals(expResult, result);
    }

    /**
     * Test of isLong method, of class Utilities.
     */
    @Test
    public void testIsLong() {
        System.out.println("isLong");

        Class<?> type = long.class;
        boolean expResult = true;

        boolean result = Utilities.isLong(type);

        assertEquals(expResult, result);
    }

    /**
     * Test of isLong method, of class Utilities.
     */
    @Test
    public void testIsLongWrapper() {
        System.out.println("isLong: Wrapper");

        Class<?> type = Long.class;
        boolean expResult = true;

        boolean result = Utilities.isLong(type);

        assertEquals(expResult, result);
    }

    /**
     * Test of isLong method, of class Utilities.
     */
    @Test
    public void testIsLongNull() {
        System.out.println("isLong: Null");

        Class<?> type = null;
        boolean expResult = false;

        boolean result = Utilities.isLong(type);

        assertEquals(expResult, result);
    }

    /**
     * Test of isShort method, of class Utilities.
     */
    @Test
    public void testIsShort() {
        System.out.println("isShort");
        Class<?> type = short.class;

        boolean expResult = true;

        boolean result = Utilities.isShort(type);

        assertEquals(expResult, result);
    }

    /**
     * Test of isShort method, of class Utilities.
     */
    @Test
    public void testIsShortWrapper() {
        System.out.println("isShort: Wrapper");
        Class<?> type = Short.class;

        boolean expResult = true;

        boolean result = Utilities.isShort(type);

        assertEquals(expResult, result);
    }

    /**
     * Test of isShort method, of class Utilities.
     */
    @Test
    public void testIsShortOther() {
        System.out.println("isShort: String");
        Class<?> type = String.class;

        boolean expResult = false;

        boolean result = Utilities.isShort(type);

        assertEquals(expResult, result);
    }
}
