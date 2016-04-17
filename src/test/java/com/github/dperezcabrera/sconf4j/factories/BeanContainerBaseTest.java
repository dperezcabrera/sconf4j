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
package com.github.dperezcabrera.sconf4j.factories;

import com.github.dperezcabrera.sconf4j.core.ConfiguratorException;
import com.github.dperezcabrera.sconf4j.core.DataContext;
import com.github.dperezcabrera.sconf4j.core.DataProvider;
import com.github.dperezcabrera.sconf4j.core.TypeSupplier;
import java.util.Arrays;
import java.util.HashSet;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class BeanContainerBaseTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Test of get method, of class BeanContainerBase.
     */
    @Test
    public void testGet() {
        System.out.println("get");

        String propertyName = null;
        TypeSupplier typeSupplier = mock(TypeSupplier.class);
        DataContext dataSet = mock(DataContext.class);
        DataProvider dataProvider = mock(DataProvider.class);
        given(typeSupplier.get()).willReturn(null);
        given(dataSet.getDataProvider()).willReturn(dataProvider);
        given(dataProvider.getSubProperties(propertyName)).willReturn(new HashSet<>(Arrays.asList("a")));
        
        thrown.expect(ConfiguratorException.class);
        
        new BeanContainerBase().get(dataSet, propertyName, typeSupplier);
    }
}
