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

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class DataContextBase implements DataContext {

    DataProvider dataProvider;
    BeanFactory beanFactory;

    public DataContextBase(DataProvider dataProvider, BeanFactory beanFactory) {
        this.dataProvider = dataProvider;
        this.beanFactory = beanFactory;
    }

    @Override
    public DataProvider getDataProvider() {
        return dataProvider;
    }

    @Override
    public BeanFactory getBeanFactory() {
        return beanFactory;
    }
}
