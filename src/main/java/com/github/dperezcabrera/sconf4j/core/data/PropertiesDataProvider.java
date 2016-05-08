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
package com.github.dperezcabrera.sconf4j.core.data;

import com.github.dperezcabrera.sconf4j.core.DataProvider;
import com.github.dperezcabrera.sconf4j.core.utils.PropertyUtils;
import java.util.Properties;
import java.util.Set;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class PropertiesDataProvider implements DataProvider {

    private Properties properties;

    public PropertiesDataProvider(Properties properties) {
        this.properties = properties;
    }
    
    @Override
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    @Override
    public Set<String> getSubProperties(String key) {
        return PropertyUtils.subproperties( properties.stringPropertyNames(), key);
    }
}
