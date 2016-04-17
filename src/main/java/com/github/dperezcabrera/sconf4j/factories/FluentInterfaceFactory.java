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
package com.github.dperezcabrera.sconf4j.factories;

import com.github.dperezcabrera.sconf4j.core.BeanFactory;
import com.github.dperezcabrera.sconf4j.core.Cacheable;
import com.github.dperezcabrera.sconf4j.core.DataContext;
import com.github.dperezcabrera.sconf4j.core.TypeSupplier;
import com.github.dperezcabrera.sconf4j.core.TypeMethodSupplier;
import com.github.dperezcabrera.sconf4j.core.utils.PropertyUtils;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class FluentInterfaceFactory implements BeanFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(FluentInterfaceFactory.class);

    @Override
    public Object get(DataContext dataSet, String prefix, TypeSupplier supplier) {
        Class<?> type = supplier.get();
        return (Cacheable) () -> Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, new ProxyBeanInterface(type, new BeanInterface(dataSet, prefix)));
    }

    private static class ProxyBeanInterface implements InvocationHandler {

        private static final Map<Class<?>, Object> DEFAULT_PRIMITIVES = initDefaultValuePrimitiveTypes();

        private BeanInterface target;
        private Set<Method> denyMethods;

        private ProxyBeanInterface(Class<?> type, BeanInterface target) {
            this.target = target;
            this.denyMethods = getIgnoreMethods(type);
        }

        private static void checkIgnoreMethod(Method method, Set<Method> ignoreSet) {
            Class<?> returnType = method.getReturnType();
            if (returnType == void.class || method.getParameterCount() > 1) {
                LOGGER.warn("Unespected definition '{}'", method);
                ignoreSet.add(method);
            } else if (!method.getName().startsWith("get")) {
                if (!method.getName().startsWith("is")) {
                    LOGGER.warn("Unespected definition '{}'", method);
                    ignoreSet.add(method);
                } else if (returnType != boolean.class && returnType != Boolean.class) {
                    LOGGER.warn("Unespected definition '{}'", method);
                    ignoreSet.add(method);
                }
            } else if (method.getParameterCount() == 1 && returnType != method.getParameterTypes()[0]) {
                LOGGER.warn("Unespected definition '{}'", method);
                ignoreSet.add(method);
            } else {
                getIgnoreMethods(returnType);
            }
        }

        private static Set<Method> getIgnoreMethods(Class<?> type) {
            Set<Method> result = Collections.emptySet();
            if (type.isInterface()) {
                result = new HashSet<>();
                for (Method method : type.getMethods()) {
                    checkIgnoreMethod(method, result);
                }
            }
            return result;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object result = null;
            if (denyMethods.contains(method)) {
                if (method.getReturnType().isPrimitive()) {
                    LOGGER.warn("Unespected call to '{}'", method);
                    result = DEFAULT_PRIMITIVES.get(method.getReturnType());
                }
            } else {
                result = target.invoke(proxy, method, args);
                if (result == null && args != null) {
                    result = args[0];
                }
            }
            return result;
        }

        private static Map<Class<?>, Object> initDefaultValuePrimitiveTypes() {
            Map<Class<?>, Object> result = new HashMap<>();
            result.put(boolean.class, false);
            result.put(byte.class, (byte) 0);
            result.put(char.class, (char) 0);
            result.put(double.class, 0d);
            result.put(float.class, 0f);
            result.put(int.class, 0);
            result.put(long.class, 0L);
            result.put(short.class, (short) 0);
            return result;
        }
    }

    private static class BeanInterface implements InvocationHandler {

        DataContext dataSet;
        String key;

        public BeanInterface(DataContext dataSet, String key) {
            this.dataSet = dataSet;
            this.key = key;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return dataSet.getBeanFactory().get(dataSet, PropertyUtils.getPropertyFromMethod(method, key), new TypeMethodSupplier(method, 0));
        }
    }
}
