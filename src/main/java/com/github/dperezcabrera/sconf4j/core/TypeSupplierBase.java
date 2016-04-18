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

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class TypeSupplierBase implements TypeSupplier {

    Supplier<Class<?>> supplier;

    public TypeSupplierBase(Supplier<Class<?>> supplier) {
        this.supplier = supplier;
    }
    
    @Override
    public TypeSupplier getPropertyTypeSupplier(String propertyName) {
        throw new UnsupportedOperationException("getPropertyTypeSupplier() is not supported");
    }

    @Override
    public Class<?> get() {
        return supplier.get();
    }

    @Override
    public TypeSupplier changeType(Class<?> type) {
        return new TypeSupplierBase(() -> type);
    }
}
