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

import com.github.dperezcabrera.sconf4j.core.ConfiguratorException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static org.hamcrest.core.StringStartsWith.startsWith;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class MappingFactoryLoaderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testWrongMappingToInterface() {
        System.out.println("wrongMapping: interface to interface");
        
        Map<Class<?>, Class<?>> interfacesMapping = new HashMap<>();
        interfacesMapping.put(List.class, List.class);
        thrown.expect(ConfiguratorException.class);
        thrown.expectMessage(startsWith("The default class"));
        
        new MappingFactoryLoader(interfacesMapping);
    }

    @Test
    public void testWrongMappingNotAssignable() {
        System.out.println("wrongMapping: not assignable");
        
        Map<Class<?>, Class<?>> interfacesMapping = new HashMap<>();
        interfacesMapping.put(Set.class, HashMap.class);
        thrown.expect(ConfiguratorException.class);
        thrown.expectMessage(startsWith("The default class"));
        
        new MappingFactoryLoader(interfacesMapping);
    }
}
