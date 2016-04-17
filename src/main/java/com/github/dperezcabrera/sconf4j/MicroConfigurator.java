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
package com.github.dperezcabrera.sconf4j;

import com.github.dperezcabrera.sconf4j.core.BeanContainer;
import com.github.dperezcabrera.sconf4j.core.ContainerLoader;
import com.github.dperezcabrera.sconf4j.core.DataContextBase;
import com.github.dperezcabrera.sconf4j.core.data.PropertiesDataProvider;
import com.github.dperezcabrera.sconf4j.core.TypeSupplierBase;
import com.github.dperezcabrera.sconf4j.factories.BeanContainerBase;
import com.github.dperezcabrera.sconf4j.factories.loader.BasicFactoryLoader;
import com.github.dperezcabrera.sconf4j.factories.loader.CollectionsLoader;
import com.github.dperezcabrera.sconf4j.factories.loader.DefaultContructorFactoryLoader;
import com.github.dperezcabrera.sconf4j.factories.loader.DefaultInterfaceFactoryLoader;
import com.github.dperezcabrera.sconf4j.factories.loader.MappingFactoryLoader;
import com.github.dperezcabrera.sconf4j.factories.loader.NoContructorFoundLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class MicroConfigurator<K> extends AbstractConfigurator<K> {

    private static final ContainerLoader[] DEFAULT_LOADERS = new ContainerLoader[]{new BasicFactoryLoader(), new CollectionsLoader(), new MappingFactoryLoader(defaultMapping()), new DefaultContructorFactoryLoader(), new DefaultInterfaceFactoryLoader(), new NoContructorFoundLoader()};

    public MicroConfigurator(Class<K> type) {
        super(type);
        BeanContainer container = new BeanContainerBase();
        for (ContainerLoader defaultLoader : DEFAULT_LOADERS) {
            defaultLoader.load(container);
        }
        this.context = new ConfiguratorContext(container);
    }

    private static Map<Class<?>, Class<?>> defaultMapping() {
        Map<Class<?>, Class<?>> result = new HashMap<>();
        result.put(List.class, ArrayList.class);
        result.put(Set.class, HashSet.class);
        result.put(Map.class, HashMap.class);
        return result;
    }

    @Override
    public <T> T get(Class<T> target) {
        return (T) context.getBeanContainer().get(new DataContextBase(new PropertiesDataProvider(new Properties()), context.getBeanContainer()), "", new TypeSupplierBase(() -> target));
    }

    @Override
    public <T> T get(Class<T> target, String param) {
        return (T) context.getBeanContainer().get(new DataContextBase(new PropertiesDataProvider(new Properties()), context.getBeanContainer()), param, new TypeSupplierBase(() -> target));
    }

    @Override
    public <T> T get(Class<T> target, Properties properties) {
        return (T) context.getBeanContainer().get(new DataContextBase(new PropertiesDataProvider(properties), context.getBeanContainer()), "", new TypeSupplierBase(() -> target));
    }

    @Override
    public <T> T get(Class<T> target, Properties properties, String param) {
        return (T) context.getBeanContainer().get(new DataContextBase(new PropertiesDataProvider(properties), context.getBeanContainer()), param, new TypeSupplierBase(() -> target));
    }
}
