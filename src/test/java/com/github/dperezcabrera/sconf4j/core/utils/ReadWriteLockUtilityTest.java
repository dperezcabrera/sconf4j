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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class ReadWriteLockUtilityTest {

    ReadWriteLock mainLockMock = mock(ReadWriteLock.class);
    Lock readLockMock = mock(Lock.class);
    Lock writeLockMock = mock(Lock.class);

    ReadWriteLockUtility.Action actionMock = mock(ReadWriteLockUtility.Action.class);
    Supplier supplierMock = mock(Supplier.class);
    BooleanSupplier booleanSupplierMock = mock(BooleanSupplier.class);

    ReadWriteLockUtility instance;

    @Before
    public void beforeTest() {
        given(mainLockMock.readLock()).willReturn(readLockMock);
        given(mainLockMock.writeLock()).willReturn(writeLockMock);
        instance = new ReadWriteLockUtility(mainLockMock);
    }

    /**
     * Test of read method, of class ReadWriteLockUtility.
     */
    @Test
    public void testRead_ReadWriteLockUtilityAction() {
        System.out.println("read");

        instance.read(actionMock);

        verify(actionMock, times(1)).execute();
        verify(mainLockMock, times(1)).readLock();
        verify(mainLockMock, times(1)).writeLock();
        verify(readLockMock, times(1)).lock();
        verify(readLockMock, times(1)).unlock();
        verify(writeLockMock, times(0)).lock();
        verify(writeLockMock, times(0)).unlock();
    }

    /**
     * Test of write method, of class ReadWriteLockUtility.
     */
    @Test
    public void testWrite_ReadWriteLockUtilityAction() {
        System.out.println("write");

        instance.write(actionMock);

        verify(actionMock, times(1)).execute();
        verify(mainLockMock, times(1)).readLock();
        verify(mainLockMock, times(1)).writeLock();
        verify(readLockMock, times(0)).lock();
        verify(readLockMock, times(0)).unlock();
        verify(writeLockMock, times(1)).lock();
        verify(writeLockMock, times(1)).unlock();
    }

    /**
     * Test of read method, of class ReadWriteLockUtility.
     */
    @Test
    public void testRead_Supplier() {
        System.out.println("read-supplier");

        Object expectedResult = "null";
        given(supplierMock.get()).willReturn(expectedResult);

        Object result = instance.read(supplierMock);

        assertEquals(expectedResult, result);
        verify(supplierMock, times(1)).get();
        verify(mainLockMock, times(1)).readLock();
        verify(mainLockMock, times(1)).writeLock();
        verify(readLockMock, times(1)).lock();
        verify(readLockMock, times(1)).unlock();
        verify(writeLockMock, times(0)).lock();
        verify(writeLockMock, times(0)).unlock();
    }

    /**
     * Test of write method, of class ReadWriteLockUtility.
     */
    @Test
    public void testWrite_Supplier() {
        System.out.println("write-supplier");

        Object expectedResult = "null";
        given(supplierMock.get()).willReturn(expectedResult);

        Object result = instance.write(supplierMock);

        assertEquals(expectedResult, result);
        verify(supplierMock, times(1)).get();
        verify(mainLockMock, times(1)).readLock();
        verify(mainLockMock, times(1)).writeLock();
        verify(readLockMock, times(0)).lock();
        verify(readLockMock, times(0)).unlock();
        verify(writeLockMock, times(1)).lock();
        verify(writeLockMock, times(1)).unlock();
    }

    /**
     * Test of read method, of class ReadWriteLockUtility.
     */
    @Test
    public void testRead_BooleanSupplier_ReadWriteLockUtilityAction_true() {
        System.out.println("read-boolean-action:true");

        given(booleanSupplierMock.getAsBoolean()).willReturn(true);

        instance.read(booleanSupplierMock, actionMock);

        verify(actionMock, times(1)).execute();
        verify(mainLockMock, times(1)).readLock();
        verify(mainLockMock, times(1)).writeLock();
        verify(readLockMock, times(1)).lock();
        verify(readLockMock, times(1)).unlock();
        verify(writeLockMock, times(0)).lock();
        verify(writeLockMock, times(0)).unlock();
    }

    @Test
    public void testRead_BooleanSupplier_ReadWriteLockUtilityAction_false() {
        System.out.println("read-boolean-action:false");

        given(booleanSupplierMock.getAsBoolean()).willReturn(false);

        instance.read(booleanSupplierMock, actionMock);

        verify(actionMock, times(0)).execute();
        verify(mainLockMock, times(1)).readLock();
        verify(mainLockMock, times(1)).writeLock();
        verify(readLockMock, times(1)).lock();
        verify(readLockMock, times(1)).unlock();
        verify(writeLockMock, times(0)).lock();
        verify(writeLockMock, times(0)).unlock();
    }

    /**
     * Test of write method, of class ReadWriteLockUtility.
     */
    @Test
    public void testWrite_BooleanSupplier_ReadWriteLockUtilityAction_true() {
        System.out.println("write-boolean-action:true");

        given(booleanSupplierMock.getAsBoolean()).willReturn(true);

        instance.write(booleanSupplierMock, actionMock);

        verify(actionMock, times(1)).execute();
        verify(mainLockMock, times(1)).readLock();
        verify(mainLockMock, times(1)).writeLock();
        verify(readLockMock, times(0)).lock();
        verify(readLockMock, times(0)).unlock();
        verify(writeLockMock, times(1)).lock();
        verify(writeLockMock, times(1)).unlock();
    }

    @Test
    public void testWrite_BooleanSupplier_ReadWriteLockUtilityAction_false() {
        System.out.println("write-boolean-action:false");

        given(booleanSupplierMock.getAsBoolean()).willReturn(false);

        instance.write(booleanSupplierMock, actionMock);

        verify(actionMock, times(0)).execute();
        verify(mainLockMock, times(1)).readLock();
        verify(mainLockMock, times(1)).writeLock();
        verify(readLockMock, times(0)).lock();
        verify(readLockMock, times(0)).unlock();
        verify(writeLockMock, times(1)).lock();
        verify(writeLockMock, times(1)).unlock();
    }

    /**
     * Test of read method, of class ReadWriteLockUtility.
     */
    @Test
    public void testRead_BooleanSupplier_Supplier_true() {
        System.out.println("read-boolean-supplier:true");
        
        Object expectedResult = "null";
        given(supplierMock.get()).willReturn(expectedResult);
        given(booleanSupplierMock.getAsBoolean()).willReturn(true);

        Object result = instance.read(booleanSupplierMock, supplierMock);

        assertEquals(expectedResult, result);
        verify(supplierMock, times(1)).get();
        verify(mainLockMock, times(1)).readLock();
        verify(mainLockMock, times(1)).writeLock();
        verify(readLockMock, times(1)).lock();
        verify(readLockMock, times(1)).unlock();
        verify(writeLockMock, times(0)).lock();
        verify(writeLockMock, times(0)).unlock();
    }

    @Test
    public void testRead_BooleanSupplier_Supplier_false() {
        System.out.println("read-boolean-supplier:false");
        
        Object expectedResult = null;

        given(booleanSupplierMock.getAsBoolean()).willReturn(false);

        Object result = instance.read(booleanSupplierMock, supplierMock);

        assertEquals(expectedResult, result);
        verify(supplierMock, times(0)).get();
        verify(mainLockMock, times(1)).readLock();
        verify(mainLockMock, times(1)).writeLock();
        verify(readLockMock, times(1)).lock();
        verify(readLockMock, times(1)).unlock();
        verify(writeLockMock, times(0)).lock();
        verify(writeLockMock, times(0)).unlock();
    }

    /**
     * Test of write method, of class ReadWriteLockUtility.
     */
    @Test
    public void testWrite_BooleanSupplier_Supplier_true() {
        System.out.println("write-boolean-supplier:true");
        
        Object expectedResult = "null";

        given(supplierMock.get()).willReturn(expectedResult);
        given(booleanSupplierMock.getAsBoolean()).willReturn(true);

        Object result = instance.write(booleanSupplierMock, supplierMock);

        assertEquals(expectedResult, result);
        verify(supplierMock, times(1)).get();
        verify(mainLockMock, times(1)).readLock();
        verify(mainLockMock, times(1)).writeLock();
        verify(readLockMock, times(0)).lock();
        verify(readLockMock, times(0)).unlock();
        verify(writeLockMock, times(1)).lock();
        verify(writeLockMock, times(1)).unlock();
    }

    @Test
    public void testWrite_BooleanSupplier_Supplier_false() {
        System.out.println("write-boolean-supplier:false");
        Object expectedResult = null;

        given(booleanSupplierMock.getAsBoolean()).willReturn(false);

        Object result = instance.write(booleanSupplierMock, supplierMock);

        assertEquals(expectedResult, result);
        verify(supplierMock, times(0)).get();
        verify(mainLockMock, times(1)).readLock();
        verify(mainLockMock, times(1)).writeLock();
        verify(readLockMock, times(0)).lock();
        verify(readLockMock, times(0)).unlock();
        verify(writeLockMock, times(1)).lock();
        verify(writeLockMock, times(1)).unlock();
    }
}
