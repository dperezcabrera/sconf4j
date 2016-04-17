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

import com.github.dperezcabrera.sconf4j.core.utils.ReadWriteLockCache;
import java.util.function.Supplier;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class ReadWriteLockCacheTest {

    ReadWriteLockCache instance = new ReadWriteLockCache();

    /**
     * Test of get method, of class ReadWriteLockCache.
     */
    @Test
    public void testGet() {
        System.out.println("get");

        String key = "key";

        Object expResult = new Object();
        Supplier<Object> s = () -> expResult;

        Object result = instance.get(key, s);

        assertEquals(expResult, result);
    }

    @Test
    public void testGetAlready() {
        System.out.println("get");

        String key = "key";
        Object expResult = new Object();
        Supplier<Object> s = () -> expResult;
        instance.get(key, s);
        s = mock(Supplier.class);

        Object result = instance.get(key, s);

        verify(s, times(0)).get();
        assertEquals(expResult, result);
    }

    /**
     * Test of put method, of class ReadWriteLockCache.
     */
    @Test
    public void testPut() {
        System.out.println("put");

        String key = "key";
        Object firstValue = new Object();
        Supplier<Object> s = () -> firstValue;
        instance.get(key, s);
        Object expResult = new Object();

        instance.put(key, expResult);

        s = mock(Supplier.class);
        assertEquals(expResult, instance.get(key, s));
        verify(s, times(0)).get();
    }

    /**
     * Test of put method, of class ReadWriteLockCache.
     */
    @Test
    public void testSynchronizedPut() {
        System.out.println("putSynchronized");

        String key = "key";

        Object firstValue = new Object();
        Object expResult = new Object();

        Supplier<Object> s = () -> {
            instance.put(key, expResult);
            return firstValue;
        };
        
        Object result = instance.get(key, s);

        assertEquals(expResult, result);
    }
}
