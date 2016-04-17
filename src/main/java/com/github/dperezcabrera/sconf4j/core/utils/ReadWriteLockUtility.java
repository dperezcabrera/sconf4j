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
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class ReadWriteLockUtility {

    private final Lock readLock;
    private final Lock writeLock;

    public ReadWriteLockUtility() {
        this(new ReentrantReadWriteLock());
    }

    public ReadWriteLockUtility(ReadWriteLock lock) {
        readLock = lock.readLock();
        writeLock = lock.writeLock();
    }

    public void read(Action action) {
        lock(readLock, action);
    }

    public void write(Action action) {
        lock(writeLock, action);
    }

    public <T> T read(Supplier<T> supplier) {
        return lock(readLock, supplier);
    }

    public <T> T write(Supplier<T> supplier) {
        return lock(writeLock, supplier);
    }

    public void read(BooleanSupplier condition,Action action) {
        lock(readLock, condition, action);
    }

    public void write(BooleanSupplier condition,Action action) {
        lock(writeLock, condition, action);
    }

    public <T> T read(BooleanSupplier condition, Supplier<T> supplier) {
        return lock(readLock, condition, supplier);
    }

    public <T> T write(BooleanSupplier condition, Supplier<T> supplier) {
        return lock(writeLock, condition, supplier);
    }

    private void lock(Lock lock,Action action) {
        lock.lock();
        try {
            action.execute();
        } finally {
            lock.unlock();
        }
    }

    private void lock(Lock lock, BooleanSupplier condition,Action action) {
        lock.lock();
        try {
            if (condition.getAsBoolean()) {
                action.execute();
            }
        } finally {
            lock.unlock();
        }
    }

    private <T> T lock(Lock lock, Supplier<T> supplier) {
        T result = null;
        lock.lock();
        try {
            result = supplier.get();
        } finally {
            lock.unlock();
        }
        return result;
    }

    private <T> T lock(Lock lock, BooleanSupplier condition, Supplier<T> supplier) {
        T result = null;
        lock.lock();
        try {
            if (condition.getAsBoolean()) {
                result = supplier.get();
            }
        } finally {
            lock.unlock();
        }
        return result;
    }

    @FunctionalInterface
    public interface Action {

        void execute();
    }
}
