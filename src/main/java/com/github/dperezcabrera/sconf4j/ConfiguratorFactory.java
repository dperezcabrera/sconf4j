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
import com.github.dperezcabrera.sconf4j.core.utils.ConcurrentCache;
import com.github.dperezcabrera.sconf4j.core.utils.ReadWriteLockCache;
import com.github.dperezcabrera.sconf4j.factories.BeanContainerBase;
import com.github.dperezcabrera.sconf4j.factories.loader.BasicFactoryLoader;
import com.github.dperezcabrera.sconf4j.factories.loader.CollectionsLoader;
import com.github.dperezcabrera.sconf4j.factories.loader.DefaultContructorFactoryLoader;
import com.github.dperezcabrera.sconf4j.factories.loader.DefaultInterfaceFactoryLoader;
import com.github.dperezcabrera.sconf4j.factories.loader.MappingFactoryLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public final class ConfiguratorFactory {

    private static final ConfiguratorFactory INSTANCE = new ConfiguratorFactory();

    private ConcurrentCache<Class<?>, Configurator> configurators = new ReadWriteLockCache<>();

    private ConfiguratorFactory() {
    }

    public static <K> Configurator<K> configurator(Class<K> typeOwner) {
        return INSTANCE.getConfigurator(typeOwner);
    }

    public static <K> Configurator<K> configurator(Class<K> typeOwner, ConfiguratorContext context) {
        return new ConfiguratorBase<>(typeOwner, context);
    }

    private <K> Configurator<K> getConfigurator(Class<K> typeOwner) {
        return configurators.get(typeOwner, () -> new ConfiguratorBase(typeOwner, getConfiguration(typeOwner)));
    }
    
    private <K> ConfiguratorContext getConfiguration(Class<K> typeOwner){
        return getDefaultConfiguratorContext();
    }

    private static ConfiguratorContext getDefaultConfiguratorContext() {
        Map<Class<?>, Class<?>> mapping = new HashMap<>();
        mapping.put(List.class, ArrayList.class);
        mapping.put(Set.class, HashSet.class);
        mapping.put(Map.class, HashMap.class);
        ContainerLoader[] defaultLoaders = new ContainerLoader[]{new BasicFactoryLoader(), new CollectionsLoader(), new MappingFactoryLoader(mapping), new DefaultContructorFactoryLoader(), new DefaultInterfaceFactoryLoader()};
        BeanContainer container = new BeanContainerBase();
        for (ContainerLoader defaultLoader : defaultLoaders) {
            defaultLoader.load(container);
        }
        return new ConfiguratorContext(container);
    }
}
