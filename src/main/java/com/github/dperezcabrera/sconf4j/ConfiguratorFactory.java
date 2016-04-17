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

import com.github.dperezcabrera.sconf4j.core.utils.ConcurrentCache;
import com.github.dperezcabrera.sconf4j.core.utils.ReadWriteLockCache;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public final class ConfiguratorFactory {

    private static final ConfiguratorFactory INSTANCE = new ConfiguratorFactory();

    private ConcurrentCache<Class<?>, Configurator> configurators = new ReadWriteLockCache<>();
    private ConfiguratorBuilder builder = type -> new MicroConfigurator(type);

    private ConfiguratorFactory() {
    }

    public static <K> Configurator<K> configurator(Class<K> typeOwner) {
        return INSTANCE.getConfigurator(typeOwner);
    }

    private <K> Configurator<K> getConfigurator(Class<K> typeOwner) {
        return configurators.get(typeOwner, () -> builder.build(typeOwner));
    }

    @FunctionalInterface
    private interface ConfiguratorBuilder<T> {

        Configurator<T> build(Class<T> type);
    }
}
