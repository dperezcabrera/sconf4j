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

import java.util.function.Supplier;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class TypeSupplierBaseTest {
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Test of getPropertyTypeSupplier method, of class TypeSupplierBase.
     */
    @Test
    public void testGetPropertyTypeSupplier() {
        System.out.println("getPropertyTypeSupplier");
                
        String propertyName = "a";
        Supplier<Class<?>> supplier = () -> TypeSupplierBaseTest.class;
        TypeSupplierBase instance = new TypeSupplierBase(supplier);
        thrown.expect(UnsupportedOperationException.class);
        
        instance.getPropertyTypeSupplier(propertyName);
    }

    /**
     * Test of get method, of class TypeSupplierBase.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        
        Class<?> expResult = TypeSupplierBaseTest.class;
        Supplier<Class<?>> supplier = () -> expResult;
        TypeSupplierBase instance = new TypeSupplierBase(supplier);
        
        Class result = instance.get();
        
        assertEquals(expResult, result);
    }

    /**
     * Test of changeType method, of class TypeSupplierBase.
     */
    @Test
    public void testChangeType() {
        System.out.println("changeType");
        
        Supplier<Class<?>> supplier = () -> TypeSupplierBaseTest.class;
        TypeSupplierBase instance = new TypeSupplierBase(supplier);
        Class<?> expResult = String.class;
        TypeSupplier typeSupplier = instance.changeType(expResult);
        
        Class<?> result = typeSupplier.get();
        
        assertEquals(expResult, result);
    }
    
}
