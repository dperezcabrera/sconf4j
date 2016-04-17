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
import com.github.dperezcabrera.sconf4j.core.ConfiguratorException;
import com.github.dperezcabrera.sconf4j.core.DataContext;
import com.github.dperezcabrera.sconf4j.core.Mapping;
import com.github.dperezcabrera.sconf4j.core.TypeSupplier;
import com.github.dperezcabrera.sconf4j.factories.BeanInitializerBase;
import com.github.dperezcabrera.sconf4j.core.utils.PropertyUtils;
import com.github.dperezcabrera.sconf4j.core.utils.Utilities;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class CollectionsLoader extends AbstractLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(CollectionsLoader.class);

    public CollectionsLoader() {
        super(getFactories(), getInitializers());
    }

    private static LinkedHashMap<Predicate<Class<?>>, BeanFactory> getFactories() {
        LinkedHashMap<Predicate<Class<?>>, BeanFactory> result = new LinkedHashMap<>();
        result.put(type -> type.isArray(), (DataContext context, String propertyName, TypeSupplier typeSupplier) -> {
            Set<String> properties = context.getDataProvider().getSubProperties(propertyName);
            properties.remove(Utilities.EMPTY_STRING);
            return Array.newInstance(typeSupplier.get().getComponentType(), properties.size());
        });
        return result;
    }

    private static <K, T> void addToMapSet(K key, Map<K, Set<T>> map, T value) {
        Set<T> set = map.get(key);
        if (set == null) {
            set = new HashSet<>();
            map.put(key, set);
        }
        set.add(value);
    }

    private static Method findResult(Map<Integer, Set<Method>> mappingCandidates, Map<Integer, Set<Method>> otherCandidates, Class<?> type, String propertyName) {
        Comparator<Integer> c = (n0, n1) -> Integer.compare(n0, n1);
        Set<Method> candidates;
        if (!mappingCandidates.isEmpty()) {
            candidates = mappingCandidates.keySet().stream().min(c).map(mappingCandidates::get).get();
        } else if (!otherCandidates.isEmpty()) {
            candidates = otherCandidates.keySet().stream().min(c).map(otherCandidates::get).get();
        } else {
            throw new ConfiguratorException("There isn't a candidate Method for set property '" + propertyName + "' in class " + type);
        }
        if (candidates.size() > 1) {
            throw new ConfiguratorException("Ambiguous methods for set property '" + propertyName + "' candidates: " + candidates + " in class " + type);
        }
        return candidates.iterator().next();
    }

    private static Method getSetter(Class<?> type, String propertyName, Class<?> elementType) {
        String name = "set".concat(propertyName.substring(0, 1).toUpperCase()).concat(propertyName.substring(1));
        int bestDistance = Integer.MAX_VALUE;
        Map<Integer, Set<Method>> mappingCandidates = new HashMap<>();
        Map<Integer, Set<Method>> otherCandidates = new HashMap<>();
        for (Method method : type.getMethods()) {
            if (method.getParameterCount() == 1) {
                int distance = PropertyUtils.typeDistance(elementType, method.getParameterTypes()[0]);
                checkDistanceMethod(distance, bestDistance, method, propertyName, mappingCandidates, name, otherCandidates);
                bestDistance = Math.min(distance, bestDistance);
            }
        }
        return findResult(mappingCandidates, otherCandidates, type, propertyName);
    }

    private static void checkDistanceMethod(int distance, int bestDistance, Method method, String propertyName, Map<Integer, Set<Method>> mappingCandidates, String name, Map<Integer, Set<Method>> otherCandidates) {
        if (distance >= 0 && bestDistance >= distance) {
            Mapping mapping = method.getAnnotation(Mapping.class);
            if (mapping != null && mapping.property().equals(propertyName)) {
                addToMapSet(distance, mappingCandidates, method);  
            } else if (method.getName().equals(name)) {
                addToMapSet(distance, otherCandidates, method);
            }
        }
    }

    private static Object callMethod(Object target, String name, Supplier<Object> elementSupplier, Class<?> elementType) {
        Method method = getSetter(target.getClass(), name, elementType);;
        try {
            return method.invoke(target, new Object[]{elementSupplier.get()});
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            String message = method + " could not be invoked for property " + name;
            LOGGER.error(message, ex);
            throw new ConfiguratorException(message, ex);
        }
    }

    private static LinkedHashMap<Predicate<Class<?>>, BeanInitializer> getInitializers() {
        LinkedHashMap<Predicate<Class<?>>, BeanInitializer> result = new LinkedHashMap<>();
        result.put(Class::isArray, new BeanInitializerBase((target, name, elementSupplier, elementType) -> Array.set(target, Integer.valueOf(name), elementSupplier.get())));
        result.put(Map.class::isAssignableFrom, new BeanInitializerBase((target, name, elementSupplier, elementType) -> ((Map) target).put(name, elementSupplier.get())));
        result.put(Set.class::isAssignableFrom, new BeanInitializerBase((target, name, elementSupplier, elementType) -> ((Set) target).add(elementSupplier.get())));
        result.put(List.class::isAssignableFrom, new BeanInitializerBase((target, name, elementSupplier, elementType) -> ((List) target).add(elementSupplier.get())));
        result.put(PropertyUtils::isCommonObject, new BeanInitializerBase((target, name, elementSupplier, elementType) -> callMethod(target, name, elementSupplier, elementType)));
        return result;
    }
}
