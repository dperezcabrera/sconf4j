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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class ReadWriteLockCache<K, V> implements ConcurrentCache<K, V> {

    Map<K, V> cache = new HashMap<>();

    private final Lock readLock;
    private final Lock writeLock;

    public ReadWriteLockCache() {
        this(new ReentrantReadWriteLock());
    }

    public ReadWriteLockCache(ReadWriteLock lock) {
        readLock = lock.readLock();
        writeLock = lock.writeLock();
    }

    private V checkAndWrite(K key, V value) {
        V result;
        writeLock.lock();
        try {
            if (cache.containsKey(key)) {
                result = cache.get(key);
            } else {
                result = value;
                cache.put(key, result);
            }
            return result;
        } finally {
            writeLock.unlock();
        }
    }

    private V write(K key, V value) {
        writeLock.lock();
        try {
            cache.put(key, value);
            return value;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public V get(K key, Supplier<V> supplier) {
        V result = null;
        readLock.lock();
        try {
            if (cache.containsKey(key)) {
                result = cache.get(key);
            } else {
                readLock.unlock();
                try {
                    result = checkAndWrite(key, supplier.get());
                } finally {
                    readLock.lock();
                }
            }
        } finally {
            readLock.unlock();
        }
        return result;
    }

    @Override
    public void put(K key, V value) {
        write(key, value);
    }
}
