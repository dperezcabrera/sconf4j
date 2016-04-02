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
package com.github.dperezcabrera.sconf4j.fluent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class InstanceBuilderBase implements InstanceBuilder {

    InterfaceBridge bridge;

    public InstanceBuilderBase(InterfaceBridge bridge) {
        this.bridge = bridge;
    }

    @Override
    public <T> T build(DataSet dataSet, String prefix, Class<T> type) {
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, bridge.buidBridge(type, new ProxyInterface(dataSet, prefix)));
    }

    private static class ProxyInterface implements InvocationHandler {

        List<String> allowPrefixList = Arrays.asList("get", "is");
        DataSet dataSet;
        String key = "";

        public ProxyInterface(DataSet dataSet, String key) {
            this.dataSet = dataSet;
            if (key != null) {
                this.key = key;
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            for (String methodPrefix : allowPrefixList) {
                if (method.getName().startsWith(methodPrefix)) {
                    String propertyName = getPropertyName(method, methodPrefix.length());
                    return dataSet.getAdapter(method.getReturnType()).adapt(dataSet, propertyName, method, 0);
                }
            }
            return null;
        }

        private String getPropertyName(Method method, int prefixMethodLength) {
            Mapping mapping = method.getAnnotation(Mapping.class);
            String tail;
            if (mapping != null && !mapping.property().isEmpty()) {
                tail = mapping.property();
            } else {
                tail = method.getName().substring(prefixMethodLength);
                tail = tail.substring(0, 1).toLowerCase().concat(tail.substring(1));
            }
            return key.concat(".").concat(tail);
        }
    }
}
