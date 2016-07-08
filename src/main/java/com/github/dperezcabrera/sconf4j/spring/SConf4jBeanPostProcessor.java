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

package com.github.dperezcabrera.sconf4j.spring;

import com.github.dperezcabrera.sconf4j.ConfiguratorFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Properties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class SConf4jBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String string) throws BeansException {
        Class type = bean.getClass();
        while (type != null) {
            for (Field field : type.getDeclaredFields()) {
                if (field.isAnnotationPresent(AutowiredConfiguration.class)) {
                    AutowiredConfiguration sconf = field.getAnnotation(AutowiredConfiguration.class);
                    if (field.getType().isInterface()){
                        try {
                            field.setAccessible(true);
                            Field modifiersField = Field.class.getDeclaredField("modifiers");
                            modifiersField.setAccessible(true);
                            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                            field.set(bean, ConfiguratorFactory.configurator(Object.class).get(field.getType(), properties4example("some.prefix"), "some.prefix"));
                        } catch (Exception ex) {
                            
                        }
                    }
                }
            }
            type = type.getSuperclass();
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String string) throws BeansException {
        return o;
    }
    
     private Properties properties4example(String prefix) {
        Properties p = new Properties();
        p.setProperty(prefix + ".index", "111");
        p.setProperty(prefix + ".defined", "1");
        p.setProperty(prefix + ".others.0.0.field", "field-0.0-1");
        p.setProperty(prefix + ".others.0.0.field2", "field-0.0-2");
        p.setProperty(prefix + ".others.0.1.field", "field-0.1-1");
        p.setProperty(prefix + ".others.0.1.field2", "field-0.1-2");
        p.setProperty(prefix + ".others.1.0.field", "field-1.0-1");
        p.setProperty(prefix + ".others.1.0.field2", "field-1.0-2");
        p.setProperty(prefix + ".others.1.1.field", "field-1.1-1");
        p.setProperty(prefix + ".others.1.1.field2", "field-1.1-2");
        p.setProperty(prefix + ".others.2.0.field", "field-2.0-1");
        p.setProperty(prefix + ".others.2.1.field2", "field-2.1-2");
        p.setProperty(prefix + ".others.2.2.field", "field-2.2-1");
        p.setProperty(prefix + ".others.3.0.field2", "field-3.0-2");
        p.setProperty(prefix + ".name", "supername");
        p.setProperty(prefix + ".char", "supername");
        p.setProperty(prefix + ".indexs", "--");
        p.setProperty(prefix + ".something", "True");
        p.setProperty(prefix + ".array.0", "10");
        p.setProperty(prefix + ".array.1", "1");
        p.setProperty(prefix + ".array.2", "1");
        p.setProperty(prefix + ".array.3", "3");
        p.setProperty(prefix + ".array.4", "3");
        p.setProperty(prefix + ".array.5", "3");
        p.setProperty(prefix + ".emptyArray", "");
        p.setProperty(prefix + ".arrayErr.0", "10");
        p.setProperty(prefix + ".arrayErr.1", "1");
        p.setProperty(prefix + ".arrayErr.2", "1");
        p.setProperty(prefix + ".arrayErr.4", "3");
        p.setProperty(prefix + ".arrayErr.5", "3");
        p.setProperty(prefix + ".enum", "ONE");
        p.setProperty(prefix + ".type", "java.lang.String");
        p.setProperty(prefix + ".example.s", "3");
        p.setProperty(prefix + ".exceptionExample.excp", "3");
        p.setProperty(prefix + ".setterExcp.list.0", "3");
        p.setProperty(prefix + ".setterExcp.list.1", "4");
        p.setProperty(prefix + ".setterExcp.list.2", "5");
        p.setProperty(prefix + ".setterExcp2.n.0", "3");
        p.setProperty(prefix + ".c", "3");
        return p;
    }

}
