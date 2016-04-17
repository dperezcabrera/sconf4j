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
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.mockito.Mockito.mock;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class PropertyUtilsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Test of subproperties method, of class PropertyUtils.
     */
    @Test
    public void testSubproperties() {
        System.out.println("subproperties");

        Set<String> properties = new HashSet<>(Arrays.asList("a.1.m", "a.2.tr.ta", "a.3.yt"));
        String prefix = "a";
        Set<String> expResult = new HashSet<>(Arrays.asList("1", "2", "3"));

        Set<String> result = PropertyUtils.subproperties(properties, prefix);

        assertEquals(expResult, result);
    }

    /**
     * Test of subproperties method, of class PropertyUtils.
     */
    @Test
    public void testSubpropertiesGroup() {
        System.out.println("subproperties");

        Set<String> properties = new HashSet<>(Arrays.asList("a", "a.1.34", "a.2.555", "a.3"));
        String prefix = "a";
        Set<String> expResult = new HashSet<>(Arrays.asList("", "1", "2", "3"));

        Set<String> result = PropertyUtils.subproperties(properties, prefix);

        assertEquals(expResult, result);
    }

    /**
     * Test of subproperties method, of class PropertyUtils.
     */
    @Test
    public void testSubpropertiesGroupf() {
        System.out.println("subproperties");

        Set<String> properties = new HashSet<>(Arrays.asList("aa", "a.1.34", "a.2.555", "a.3", "b.a"));
        String prefix = "a";
        Set<String> expResult = new HashSet<>(Arrays.asList("1", "2", "3"));

        Set<String> result = PropertyUtils.subproperties(properties, prefix);

        assertEquals(expResult, result);
    }

    @Test
    public void testSubpropertiesWrongPrefix() {
        System.out.println("subproperties");

        Set<String> properties = new HashSet<>(Arrays.asList("a.1", "a.2", "a.3"));
        String prefix = "b";
        Set<String> expResult = new HashSet<>();

        Set<String> result = PropertyUtils.subproperties(properties, prefix);

        assertEquals(expResult, result);
    }

    @Test
    public void testSubpropertiesEmpty() {
        System.out.println("subproperties");

        Set<String> properties = new HashSet<>();
        String prefix = "a";
        Set<String> expResult = new HashSet<>();

        Set<String> result = PropertyUtils.subproperties(properties, prefix);

        assertEquals(expResult, result);
    }

    /**
     * Test of isIndex method, of class PropertyUtils.
     */
    @Test
    public void testIsIndex() {
        System.out.println("isIndex");
        String value = "1";
        boolean expResult = true;

        boolean result = PropertyUtils.isIndex(value);

        assertEquals(expResult, result);
    }

    /**
     * Test of isIndex method, of class PropertyUtils.
     */
    @Test
    public void testIsIndex_1() {
        System.out.println("isIndex");
        String value = "-1";
        boolean expResult = false;

        boolean result = PropertyUtils.isIndex(value);

        assertEquals(expResult, result);
    }

    /**
     * Test of isIndex method, of class PropertyUtils.
     */
    @Test
    public void testIsIndexSpace() {
        System.out.println("isIndex");
        String value = " ";
        boolean expResult = false;

        boolean result = PropertyUtils.isIndex(value);

        assertEquals(expResult, result);
    }

    @Test
    public void testIsIndexEmpty() {
        System.out.println("isIndex");
        String value = "";
        boolean expResult = false;

        boolean result = PropertyUtils.isIndex(value);

        assertEquals(expResult, result);
    }

    /**
     * Test of classForName method, of class PropertyUtils.
     */
    @Test
    public void testClassForName() {
        System.out.println("classForName");

        Class expResult = ObjectClass.class;

        Class result = PropertyUtils.classForName(expResult.getName());

        assertEquals(expResult, result);
    }

    @Test
    public void testClassForNameException() {
        System.out.println("classForNameException");

        String name = "adsfadsf.adsf";

        thrown.expect(ConfiguratorException.class);
        thrown.expectMessage(name + " isn't a known class");

        PropertyUtils.classForName(name);
    }

    /**
     * Test of newInstance method, of class PropertyUtils.
     */
    @Test
    public void testNewInstance() throws Exception {
        System.out.println("newInstance");

        Class<?> type = ObjectClass.class;
        Object expResult = new ObjectClass();

        Object result = PropertyUtils.newInstance(type);

        assertEquals(expResult, result);
    }

    @Test
    public void testNewInstanceException() throws Exception {
        System.out.println("newInstanceException");

        Class<?> type = ObjectClassException.class;

        thrown.expect(ConfiguratorException.class);
        thrown.expectMessage(type.toString() + " couldn't be instanciated");

        PropertyUtils.newInstance(type);
    }

    public static class ObjectClass {

        @Override
        public boolean equals(Object obj) {
            return obj != null && obj.getClass() == getClass();
        }
    }

    public static class ObjectClassException {

        public ObjectClassException() {
            throw new RuntimeException("Exception");
        }
    }

    /**
     * Test of isCommonObject method, of class PropertyUtils.
     */
    @Test
    public void testIsCommonObject() {
        System.out.println("isCommonObject");
        Class type = Object.class;
        boolean expResult = true;
        boolean result = PropertyUtils.isCommonObject(type);

        assertEquals(expResult, result);
    }

    /**
     * Test of isCommonObject method, of class PropertyUtils.
     */
    @Test
    public void testIsCommonObjectPrimitive() {
        System.out.println("isCommonObjectInt");
        Class type = int.class;
        boolean expResult = false;
        boolean result = PropertyUtils.isCommonObject(type);

        assertEquals(expResult, result);
    }

    /**
     * Test of isCommonObject method, of class PropertyUtils.
     */
    @Test
    public void testIsCommonObjectPrimitiveWrapper() {
        System.out.println("isCommonObjectInteger");
        Class type = Integer.class;
        boolean expResult = false;
        boolean result = PropertyUtils.isCommonObject(type);

        assertEquals(expResult, result);
    }

    /**
     * Test of isCommonObject method, of class PropertyUtils.
     */
    @Test
    public void testIsCommonObjectArray() {
        System.out.println("isCommonObjectArray");
        Class type = Object[].class;
        boolean expResult = false;
        boolean result = PropertyUtils.isCommonObject(type);

        assertEquals(expResult, result);
    }

    /**
     * Test of isCommonObject method, of class PropertyUtils.
     */
    @Test
    public void testIsCommonObjectEnum() {
        System.out.println("isCommonObjectEnum");
        Class type = TypeEnum.class;
        boolean expResult = false;
        boolean result = PropertyUtils.isCommonObject(type);

        assertEquals(expResult, result);
    }

    public static enum TypeEnum {
        L
    }

    @Test
    public void testIsCommonObjectInterface() {
        System.out.println("isCommonObjectInterface");
        Class type = List.class;
        boolean expResult = false;
        boolean result = PropertyUtils.isCommonObject(type);

        assertEquals(expResult, result);
    }

    /**
     * Test of typeDistance method, of class PropertyUtils.
     */
    @Test
    public void testTypeDistanceNotAsignable() {
        System.out.println("typeDistanceNotAsignable");

        Class type = int.class;
        Class valueType = short.class;
        
        int expResult = -1;
        int result = PropertyUtils.typeDistance(type, valueType);

        assertEquals(expResult, result);
    }

    /**
     * Test of typeDistance method, of class PropertyUtils.
     */
    @Test
    public void testTypeDistanceSameType() {
        System.out.println("typeDistanceSameType");

        Class type = Integer.class;
        Class valueType = Integer.class;
        int expResult = 0;
        int result = PropertyUtils.typeDistance(type, valueType);

        assertEquals(expResult, result);
    }

    /**
     * Test of typeDistance method, of class PropertyUtils.
     */
    @Test
    public void testTypeDistanceInterface() {
        System.out.println("typeDistanceInterface");

        Class type = List.class;
        Class valueType = ArrayList.class;
        int expResult = 1;
        int result = PropertyUtils.typeDistance(type, valueType);

        assertEquals(expResult, result);
    }

    /**
     * Test of typeDistance method, of class PropertyUtils.
     */
    @Test
    public void testTypeDistanceAsignableInterface() {
        System.out.println("typeDistanceAsignableInterface");

        Class type = Collection.class;
        Class valueType = List.class;
        
        int expResult = 1;
        int result = PropertyUtils.typeDistance(type, valueType);

        assertEquals(expResult, result);
    }

    /**
     * Test of typeDistance method, of class PropertyUtils.
     */
    @Test
    public void testTypeDistanceIntegerObject() {
        System.out.println("typeDistanceIntegerObject");

        Class type = Object.class;
        Class valueType = Integer.class;
        int expResult = 2;
        int result = PropertyUtils.typeDistance(type, valueType);

        assertEquals(expResult, result);
    }

    /**
     * Test of typeDistance method, of class PropertyUtils.
     */
    @Test
    public void testTypeDistancePrimitiveWrapper() {
        System.out.println("typeDistancePrimitiveWrapper");
        Class type = Integer.class;
        Class valueType = int.class;
        int expResult = 1;
        int result = PropertyUtils.typeDistance(type, valueType);

        assertEquals(expResult, result);
    }

    /**
     * Test of getPropertyFromMethod method, of class PropertyUtils.
     */
    @Test
    public void testGetPropertyFromMethod_Method_String() throws NoSuchMethodException {
        System.out.println("getPropertyFromMethod");
        Method method = MethodTestClass.class.getDeclaredMethod("getSomething");
        String prefix = "a";
        String expResult = "a.something";

        String result = PropertyUtils.getPropertyFromMethod(method, prefix);

        assertEquals(expResult, result);
    }
    
    /**
     * Test of getPropertyFromMethod method, of class PropertyUtils.
     */
    @Test
    public void testGetPropertyFromMethod_Method_String2() throws NoSuchMethodException {
        System.out.println("getPropertyFromMethod emptyPrefix");
        Method method = MethodTestClass.class.getDeclaredMethod("getSomething");
        String prefix = "";
        String expResult = "something";

        String result = PropertyUtils.getPropertyFromMethod(method, prefix);

        assertEquals(expResult, result);
    }
    
        /**
     * Test of getPropertyFromMethod method, of class PropertyUtils.
     */
    @Test
    public void testGetPropertyFromMethod_Method_StringNothing() throws NoSuchMethodException {
        System.out.println("getPropertyFromMethod nothing");
        Method method = MethodTestClass.class.getDeclaredMethod("nothing");
        String prefix = "";
        String expResult = null;

        String result = PropertyUtils.getPropertyFromMethod(method, prefix);

        assertEquals(expResult, result);
    }
    
    /**
     * Test of getPropertyFromMethod method, of class PropertyUtils.
     */
    @Test
    public void testGetPropertyFromMethod_Method_StringNothing2() throws NoSuchMethodException {
        System.out.println("getPropertyFromMethod nothing");
        Method method = MethodTestClass.class.getDeclaredMethod("nothing");
        String prefix = "a";
        String expResult = null;

        String result = PropertyUtils.getPropertyFromMethod(method, prefix);

        assertEquals(expResult, result);
    }

    private static class MethodTestClass {

        public int getSomething() {
            return 0;
        }
        
        public void nothing(){
        }
    }

}
