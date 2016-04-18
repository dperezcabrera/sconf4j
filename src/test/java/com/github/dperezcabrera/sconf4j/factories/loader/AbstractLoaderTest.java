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
import java.util.function.Predicate;
import org.junit.Test;
import static org.mockito.Matchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class AbstractLoaderTest {
    
    public AbstractLoaderTest() {
    }

    /**
     * Test of load method, of class AbstractLoader.
     */
    @Test
    public void testLoad() {
        System.out.println("load");
        BeanContainer registryMock = mock(BeanContainer.class);
        AbstractLoader instance = mock(AbstractLoader.class, Mockito.CALLS_REAL_METHODS);
       
        instance.load(registryMock);
        
        verify(registryMock, times(0)).register(any(Predicate.class), any(BeanFactory.class));
        verify(registryMock, times(0)).register(any(Predicate.class), any(BeanInitializer.class));
    }    
}
