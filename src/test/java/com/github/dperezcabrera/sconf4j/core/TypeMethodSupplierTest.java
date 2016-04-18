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

import static org.hamcrest.core.StringStartsWith.startsWith;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class TypeMethodSupplierTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Test of getPropertyTypeSupplier method, of class TypeMethodSupplier.
     */
    @Test
    public void testGetPropertyTypeSupplierNull() throws NoSuchMethodException {
        System.out.println("getPropertyTypeSupplier propertyName: null");

        String propertyName = null;
        TypeMethodSupplier instance = new TypeMethodSupplier(EmptyProvider.class.getMethod("getEmpty"), 0);

        thrown.expect(NullPointerException.class);
        thrown.expectMessage("PropertyName cant not be null");

        instance.getPropertyTypeSupplier(propertyName);
    }

    /**
     * Test of getPropertyTypeSupplier method, of class TypeMethodSupplier.
     */
    @Test
    public void testGetPropertyTypeSupplier() throws NoSuchMethodException {
        System.out.println("getPropertyTypeSupplier");

        String propertyName = "a";
        TypeMethodSupplier instance = new TypeMethodSupplier(EmptyProvider.class.getMethod("getEmpty"), 0);
        thrown.expect(ConfiguratorException.class);
        thrown.expectMessage(startsWith("No method found for property"));

        instance.getPropertyTypeSupplier(propertyName);
    }

    /**
     * Test of getPropertyTypeSupplier method, of class TypeMethodSupplier.
     */
    @Test
    public void testGetPropertyTypeSupplierEmptyClass() throws NoSuchMethodException {
        System.out.println("getPropertyTypeSupplier propertyName: null");

        String propertyName = "empty";
        TypeMethodSupplier instance = new TypeMethodSupplier(EmptyProvider.class.getMethod("getEmpty"), 0);
        
        thrown.expect(ConfiguratorException.class);
        
        instance.getPropertyTypeSupplier(propertyName);
    }

    /**
     * Test of get method, of class TypeMethodSupplier.
     */
    @Test
    public void testGet() throws NoSuchMethodException {
        System.out.println("get");

        TypeMethodSupplier instance = new TypeMethodSupplier(EmptyProvider.class.getMethod("getEmpty"), 0);
        Class<?> expResult = Empty.class;

        Class<?> result = instance.get();

        assertEquals(expResult, result);
    }

    public interface EmptyProvider {

        Empty getEmpty();
    }

    public interface Empty {
    }

}
