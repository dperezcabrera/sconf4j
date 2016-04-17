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
import java.util.Properties;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class ConfiguratorBase<K> extends AbstractConfigurator<K> {

    public ConfiguratorBase(Class<K> type) {
        super(type);
        Configuration config = (Configuration) ConfiguratorFactory.configurator(Configurator.class).get(Configuration.class);
        BeanContainer container = new BeanContainerBase();
        for (ContainerLoader defaultLoader : config.getLoaders()) {
            defaultLoader.load(container);
        }
        this.context = new ConfiguratorContext(container);
    }

    interface Configuration {

        ContainerLoader[] getLoaders();
        int getInt();
    }


    public ConfiguratorContext getContext() {
        return context;
    }

    public void setContext(ConfiguratorContext context) {
        this.context = context;
    }

    @Override
    public <T> T get(Class<T> target, Properties properties) {
        ConfiguratorContext cc = context;
        return (T) cc.getBeanContainer().get(new DataContextBase(new PropertiesDataProvider(properties), cc.getBeanContainer()), "", new TypeSupplierBase(() -> target));
    }

    @Override
    public <T> T get(Class<T> target, Properties properties, String param) {
        ConfiguratorContext cc = context;
        return (T) cc.getBeanContainer().get(new DataContextBase(new PropertiesDataProvider(properties), cc.getBeanContainer()), param, new TypeSupplierBase(() -> target));
    }
}
