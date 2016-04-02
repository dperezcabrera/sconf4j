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

import com.github.dperezcabrera.sconf4j.fluent.DataSetBase;
import com.github.dperezcabrera.sconf4j.fluent.InstanceBuilder;
import com.github.dperezcabrera.sconf4j.fluent.InstanceBuilderBase;
import com.github.dperezcabrera.sconf4j.fluent.InterfaceBridge;
import com.github.dperezcabrera.sconf4j.fluent.PropertiesDataProvider;
import com.github.dperezcabrera.sconf4j.fluent.TypeAdapter;
import com.github.dperezcabrera.sconf4j.fluent.TypeAdapterBase;
import java.util.Properties;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public final class ConfiguratorFactory {

    static InterfaceBridge bridge = new AllowInterfaceBridge();
    static InstanceBuilder instanceProxyBuilder = new InstanceBuilderBase(bridge);
    static TypeAdapter typeAdapter = new TypeAdapterBase();

    private ConfiguratorFactory() {
    }

    public static <K> Configurator<K> configurator(Class<K> owner) {

        return new Configurator<K>() {
            public <T> T get(Class<T> target, String param) {
                return null;
            }

            @Override
            public <T> T get(Class<T> target) {
                return null;
            }

            @Override
            public <T> void subscribe(Class<T> target, Subscriber<T> subscriber) {

            }

            @Override
            public <T> T get(Class<T> target, Properties properties) {
                return null;
            }

            @Override
            public <T> T get(Class<T> target, Properties properties, String param) {
                return instanceProxyBuilder.build(new DataSetBase(new PropertiesDataProvider(properties), instanceProxyBuilder, typeAdapter), param, target);
            }
        };
    }
}
