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
package com.github.dperezcabrera.sconf4j.factories.loader;

import com.github.dperezcabrera.sconf4j.core.BeanContainer;
import com.github.dperezcabrera.sconf4j.core.BeanFactory;
import com.github.dperezcabrera.sconf4j.core.BeanInitializer;
import com.github.dperezcabrera.sconf4j.core.DataContext;
import com.github.dperezcabrera.sconf4j.core.DataProvider;
import com.github.dperezcabrera.sconf4j.core.TypeSupplier;
import java.util.function.Predicate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class DefaultContructorFactoryLoaderTest {

    DataProvider dataProviderMock = mock(DataProvider.class);
    DataContext dataContextMock = mock(DataContext.class);
    BeanFactory beanFactoryMock = mock(BeanFactory.class);
    TypeSupplier typeSupplierMock = mock(TypeSupplier.class);

    @Before
    public void beforeTest() {
        given(dataContextMock.getBeanFactory()).willReturn(beanFactoryMock);
        given(dataContextMock.getDataProvider()).willReturn(dataProviderMock);
    }

    @Test
    public void testPropertyValueDistintToTypeName() {
        System.out.println("propertyValue distint to type name");

        String propertyName = "name";
        String propertyValue = "java.lang.String";
        Class type = DefaultContructorFactoryLoaderTest.class;
        Object expectedResult = new Object();

        given(dataProviderMock.getProperty(propertyName)).willReturn(propertyValue);
        given(typeSupplierMock.get()).willReturn(type);
        given(beanFactoryMock.get(any(DataContext.class), anyString(), any(TypeSupplier.class))).willReturn(expectedResult);
        BeanContainerTest beanContainer = new BeanContainerTest();
        new DefaultContructorFactoryLoader().load(beanContainer);

        Object result = beanContainer.factory.get(dataContextMock, propertyName, typeSupplierMock);

        assertEquals(expectedResult, result);
    }

    @Test
    public void testPropertyValueNull() {
        System.out.println("propertyValue null");

        String propertyName = "name";
        String propertyValue = null;
        Class type = Object.class;
        Object expectedResult = new Object();

        given(dataProviderMock.getProperty(propertyName)).willReturn(propertyValue);
        given(typeSupplierMock.get()).willReturn(type);
        given(beanFactoryMock.get(any(DataContext.class), anyString(), any(TypeSupplier.class))).willReturn(expectedResult);
        BeanContainerTest beanContainer = new BeanContainerTest();
        new DefaultContructorFactoryLoader().load(beanContainer);

        Object result = beanContainer.factory.get(dataContextMock, propertyName, typeSupplierMock);

        assertNotNull(result);
        assertEquals(Object.class, result.getClass());
    }

    @Test
    public void testPropertyValueEqualsTypeName() {
        System.out.println("propertyValue equals type name");

        String propertyName = "name";
        String propertyValue = "java.lang.Object";
        Class type = Object.class;
        Object expectedResult = new Object();

        given(dataProviderMock.getProperty(propertyName)).willReturn(propertyValue);
        given(typeSupplierMock.get()).willReturn(type);
        given(beanFactoryMock.get(any(DataContext.class), anyString(), any(TypeSupplier.class))).willReturn(expectedResult);
        BeanContainerTest beanContainer = new BeanContainerTest();
        new DefaultContructorFactoryLoader().load(beanContainer);

        Object result = beanContainer.factory.get(dataContextMock, propertyName, typeSupplierMock);

        assertNotNull(result);
        assertEquals(Object.class, result.getClass());
    }

    private class BeanContainerTest implements BeanContainer {

        Predicate<Class<?>> predicate;
        BeanFactory factory;

        @Override
        public void register(Predicate<Class<?>> predicate, BeanFactory factory) {
            this.factory = factory;
            this.predicate = predicate;
        }

        @Override
        public void register(Predicate<Class<?>> predicate, BeanInitializer initializer) {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public Object get(DataContext context, String propertyName, TypeSupplier typeSupplier) {
            throw new UnsupportedOperationException("Not supported.");
        }
    }

}
