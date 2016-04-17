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

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class ReadWriteLockUtilityTest {

    ReadWriteLock mainLock = mock(ReadWriteLock.class);
    Lock readLock = mock(Lock.class);
    Lock writeLock = mock(Lock.class);
    
    ReadWriteLockUtility instance;

    @Before
    public void beforeTest() {
        given(mainLock.readLock()).willReturn(readLock);
        given(mainLock.writeLock()).willReturn(writeLock);
        instance = new ReadWriteLockUtility(mainLock);
    }

    /**
     * Test of read method, of class ReadWriteLockUtility.
     */
    @Test
    public void testRead_ReadWriteLockUtilityAction() {
        System.out.println("read");

        ReadWriteLockUtility.Action action = mock(ReadWriteLockUtility.Action.class);

        instance.read(action);

        verify(action, times(1)).execute();
        verify(mainLock, times(1)).readLock();
        verify(mainLock, times(1)).writeLock();
        verify(readLock, times(1)).lock();
        verify(readLock, times(1)).unlock();
        verify(writeLock, times(0)).lock();
        verify(writeLock, times(0)).unlock();
    }

    /**
     * Test of write method, of class ReadWriteLockUtility.
     */
    @Test
    public void testWrite_ReadWriteLockUtilityAction() {
        System.out.println("write");

        ReadWriteLockUtility.Action action = mock(ReadWriteLockUtility.Action.class);

        instance.write(action);

        verify(action, times(1)).execute();
        verify(mainLock, times(1)).readLock();
        verify(mainLock, times(1)).writeLock();
        verify(readLock, times(0)).lock();
        verify(readLock, times(0)).unlock();
        verify(writeLock, times(1)).lock();
        verify(writeLock, times(1)).unlock();
    }

    /**
     * Test of read method, of class ReadWriteLockUtility.
     */
    @Test
    public void testRead_Supplier() {
        System.out.println("read-supplier");

        Object expectedResult = "null";
        Supplier supplier = mock(Supplier.class);
        given(supplier.get()).willReturn(expectedResult);

        Object result = instance.read(supplier);

        assertEquals(expectedResult, result);
        verify(supplier, times(1)).get();
        verify(mainLock, times(1)).readLock();
        verify(mainLock, times(1)).writeLock();
        verify(readLock, times(1)).lock();
        verify(readLock, times(1)).unlock();
        verify(writeLock, times(0)).lock();
        verify(writeLock, times(0)).unlock();
    }

    /**
     * Test of write method, of class ReadWriteLockUtility.
     */
    @Test
    public void testWrite_Supplier() {
        System.out.println("write-supplier");

        Object expectedResult = "null";
        Supplier supplier = mock(Supplier.class);
        given(supplier.get()).willReturn(expectedResult);

        Object result = instance.write(supplier);

        assertEquals(expectedResult, result);
        verify(supplier, times(1)).get();
        verify(mainLock, times(1)).readLock();
        verify(mainLock, times(1)).writeLock();
        verify(readLock, times(0)).lock();
        verify(readLock, times(0)).unlock();
        verify(writeLock, times(1)).lock();
        verify(writeLock, times(1)).unlock();
    }

    /**
     * Test of read method, of class ReadWriteLockUtility.
     */
    @Test
    public void testRead_BooleanSupplier_ReadWriteLockUtilityAction_true() {
        System.out.println("read-boolean-action:true");

        ReadWriteLockUtility.Action action = mock(ReadWriteLockUtility.Action.class);
        BooleanSupplier booleanSupplier = mock(BooleanSupplier.class);

        given(booleanSupplier.getAsBoolean()).willReturn(true);

        instance.read(booleanSupplier, action);

        verify(action, times(1)).execute();
        verify(mainLock, times(1)).readLock();
        verify(mainLock, times(1)).writeLock();
        verify(readLock, times(1)).lock();
        verify(readLock, times(1)).unlock();
        verify(writeLock, times(0)).lock();
        verify(writeLock, times(0)).unlock();
    }

    @Test
    public void testRead_BooleanSupplier_ReadWriteLockUtilityAction_false() {
        System.out.println("read-boolean-action:false");

        ReadWriteLockUtility.Action action = mock(ReadWriteLockUtility.Action.class);
        BooleanSupplier booleanSupplier = mock(BooleanSupplier.class);

        given(booleanSupplier.getAsBoolean()).willReturn(false);

        instance.read(booleanSupplier, action);

        verify(action, times(0)).execute();
        verify(mainLock, times(1)).readLock();
        verify(mainLock, times(1)).writeLock();
        verify(readLock, times(1)).lock();
        verify(readLock, times(1)).unlock();
        verify(writeLock, times(0)).lock();
        verify(writeLock, times(0)).unlock();
    }

    /**
     * Test of write method, of class ReadWriteLockUtility.
     */
    @Test
    public void testWrite_BooleanSupplier_ReadWriteLockUtilityAction_true() {
        System.out.println("write-boolean-action:true");

        ReadWriteLockUtility.Action action = mock(ReadWriteLockUtility.Action.class);
        BooleanSupplier booleanSupplier = mock(BooleanSupplier.class);

        given(booleanSupplier.getAsBoolean()).willReturn(true);

        instance.write(booleanSupplier, action);

        verify(action, times(1)).execute();
        verify(mainLock, times(1)).readLock();
        verify(mainLock, times(1)).writeLock();
        verify(readLock, times(0)).lock();
        verify(readLock, times(0)).unlock();
        verify(writeLock, times(1)).lock();
        verify(writeLock, times(1)).unlock();
    }

    @Test
    public void testWrite_BooleanSupplier_ReadWriteLockUtilityAction_false() {
        System.out.println("write-boolean-action:false");

        ReadWriteLockUtility.Action action = mock(ReadWriteLockUtility.Action.class);
        BooleanSupplier booleanSupplier = mock(BooleanSupplier.class);

        given(booleanSupplier.getAsBoolean()).willReturn(false);

        instance.write(booleanSupplier, action);

        verify(action, times(0)).execute();
        verify(mainLock, times(1)).readLock();
        verify(mainLock, times(1)).writeLock();
        verify(readLock, times(0)).lock();
        verify(readLock, times(0)).unlock();
        verify(writeLock, times(1)).lock();
        verify(writeLock, times(1)).unlock();
    }

    /**
     * Test of read method, of class ReadWriteLockUtility.
     */
    @Test
    public void testRead_BooleanSupplier_Supplier_true() {
        System.out.println("read-boolean-supplier:true");
        Object expectedResult = "null";
        Supplier supplier = mock(Supplier.class);
        BooleanSupplier booleanSupplier = mock(BooleanSupplier.class);

        given(supplier.get()).willReturn(expectedResult);
        given(booleanSupplier.getAsBoolean()).willReturn(true);

        Object result = instance.read(booleanSupplier, supplier);

        assertEquals(expectedResult, result);
        verify(supplier, times(1)).get();
        verify(mainLock, times(1)).readLock();
        verify(mainLock, times(1)).writeLock();
        verify(readLock, times(1)).lock();
        verify(readLock, times(1)).unlock();
        verify(writeLock, times(0)).lock();
        verify(writeLock, times(0)).unlock();
    }

    @Test
    public void testRead_BooleanSupplier_Supplier_false() {
        System.out.println("read-boolean-supplier:false");
        Object expectedResult = null;
        Supplier supplier = mock(Supplier.class);
        BooleanSupplier booleanSupplier = mock(BooleanSupplier.class);

        given(booleanSupplier.getAsBoolean()).willReturn(false);

        Object result = instance.read(booleanSupplier, supplier);

        assertEquals(expectedResult, result);
        verify(supplier, times(0)).get();
        verify(mainLock, times(1)).readLock();
        verify(mainLock, times(1)).writeLock();
        verify(readLock, times(1)).lock();
        verify(readLock, times(1)).unlock();
        verify(writeLock, times(0)).lock();
        verify(writeLock, times(0)).unlock();
    }

    /**
     * Test of write method, of class ReadWriteLockUtility.
     */
    @Test
    public void testWrite_BooleanSupplier_Supplier_true() {
        System.out.println("write-boolean-supplier:true");
        Object expectedResult = "null";
        Supplier supplier = mock(Supplier.class);
        BooleanSupplier booleanSupplier = mock(BooleanSupplier.class);

        given(supplier.get()).willReturn(expectedResult);
        given(booleanSupplier.getAsBoolean()).willReturn(true);

        Object result = instance.write(booleanSupplier, supplier);

        assertEquals(expectedResult, result);
        verify(supplier, times(1)).get();
        verify(mainLock, times(1)).readLock();
        verify(mainLock, times(1)).writeLock();
        verify(readLock, times(0)).lock();
        verify(readLock, times(0)).unlock();
        verify(writeLock, times(1)).lock();
        verify(writeLock, times(1)).unlock();
    }

    @Test
    public void testWrite_BooleanSupplier_Supplier_false() {
        System.out.println("write-boolean-supplier:false");
        Object expectedResult = null;
        Supplier supplier = mock(Supplier.class);
        BooleanSupplier booleanSupplier = mock(BooleanSupplier.class);

        given(booleanSupplier.getAsBoolean()).willReturn(false);

        Object result = instance.write(booleanSupplier, supplier);

        assertEquals(expectedResult, result);
        verify(supplier, times(0)).get();
        verify(mainLock, times(1)).readLock();
        verify(mainLock, times(1)).writeLock();
        verify(readLock, times(0)).lock();
        verify(readLock, times(0)).unlock();
        verify(writeLock, times(1)).lock();
        verify(writeLock, times(1)).unlock();
    }
}
