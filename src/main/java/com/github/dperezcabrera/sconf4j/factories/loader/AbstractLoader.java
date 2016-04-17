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

import com.github.dperezcabrera.sconf4j.core.BeanFactory;
import com.github.dperezcabrera.sconf4j.core.BeanInitializer;
import com.github.dperezcabrera.sconf4j.core.BeanContainer;
import com.github.dperezcabrera.sconf4j.core.ContainerLoader;
import java.util.Map;
import java.util.function.Predicate;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public abstract class AbstractLoader implements ContainerLoader {

    Map<Predicate<Class<?>>, BeanFactory> factories;
    Map<Predicate<Class<?>>, BeanInitializer> initializers = null;

    public AbstractLoader(Map<Predicate<Class<?>>, BeanFactory> factories) {
        this.factories = factories;
    }

    public AbstractLoader(Map<Predicate<Class<?>>, BeanFactory> factories, Map<Predicate<Class<?>>, BeanInitializer> initializers) {
        this.factories = factories;
        this.initializers = initializers;
    }

    @Override
    public void load(BeanContainer registry) {
        if (factories != null) {
            for (Map.Entry<Predicate<Class<?>>, BeanFactory> e : factories.entrySet()) {
                registry.register(e.getKey(), e.getValue());
            }
        }
        if (initializers != null) {
            for (Map.Entry<Predicate<Class<?>>, BeanInitializer> e : initializers.entrySet()) {
                registry.register(e.getKey(), e.getValue());
            }
        }
    }
}
