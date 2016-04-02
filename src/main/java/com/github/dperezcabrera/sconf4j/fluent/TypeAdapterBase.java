package com.github.dperezcabrera.sconf4j.fluent;

import com.github.dperezcabrera.sconf4j.InitializeConfiguratorException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class TypeAdapterBase implements TypeAdapter {

    private static final Map<Predicate<Class<?>>, TypeAdapter> GET_ADAPTERS = buildAdapters();
    private static final Map<Class<?>, Class<?>> DEFAULT_INSTANCIATE_CONTAINERS = new HashMap<Class<?>, Class<?>>() {
        {
            put(List.class, ArrayList.class);
            put(Map.class, HashMap.class);
            put(Set.class, HashSet.class);
        }
    };

    @Override
    public Object adapt(DataSet dataSet, String propertyName, Method invokedMethod, int dimension) {
        Object result = null;
        Class<?> type = getSubtype(dimension, invokedMethod);
        for (Entry<Predicate<Class<?>>, TypeAdapter> e : GET_ADAPTERS.entrySet()) {
            if (e.getKey().test(type)) {
                return e.getValue().adapt(dataSet, propertyName, invokedMethod, dimension);
            }
        }
        return result;
    }

    private static Class<?> arraySubtypeGetterHelper(int dimensions, Class<?> c) {
        if (dimensions <= 0) {
            return c;
        } else {
            return arraySubtypeGetterHelper(dimensions - 1, c.getComponentType());
        }
    }

    private static Class<?> getSubtype(int dimensions, Method m) {
        Class<?> result = m.getReturnType();
        if (dimensions > 0) {
            if (result.isArray()) {
                result = arraySubtypeGetterHelper(dimensions, result);
            } else {
                result = m.getAnnotation(Mapping.class).value()[dimensions - 1];
            }
        }
        return result;
    }

    private static <T> T instancianteContainer(Class<T> type) {
        try {
            if (type.isInterface()) {
                return (T) DEFAULT_INSTANCIATE_CONTAINERS.get(type).newInstance();
            } else {
                return (T) type.newInstance();
            }
        } catch (Exception e) {
            throw new InitializeConfiguratorException("", e);
        }
    }

    private static class TypeAdapterTemplate implements TypeAdapter {

        private static interface Builder {

            Object build(Class<?> containerType, Class<?> type, int size);
        }

        private static interface Adder {

            void add(Object target, String name, Object element);
        }

        private static interface PostProcessor {

            Object process(Class<?> type, Object target, int size);
        }

        private Builder builder;
        private Adder adder;
        private PostProcessor postProcessor;

        public TypeAdapterTemplate(Builder builder, Adder adder, PostProcessor postProcessor) {
            this.builder = builder;
            this.adder = adder;
            this.postProcessor = postProcessor;
        }

        private static List<String> namesAfterPrefix(String prefix, DataProvider data) {
            List<String> result = new ArrayList<>();
            Set<String> items = new HashSet<>();
            String currentName = "&";
            for (String name : data.getPropertyNames()) {
                if (name.startsWith(prefix) && !name.startsWith(currentName)) {
                    int newNameIndex = name.indexOf(".", prefix.length() + 1);
                    if (newNameIndex < 0) {
                        currentName = name;
                    } else {
                        currentName = name.substring(0, newNameIndex);
                    }
                    if (!items.contains(currentName)) {
                        result.add(currentName);
                        items.add(currentName);
                    }
                }
            }
            return result;
        }

        @Override
        public Object adapt(DataSet dataSet, String propertyName, Method invokedMethod, int dimensions) {
            List<String> names = namesAfterPrefix(propertyName, dataSet.getDataProvider());
            Object obj = null;
            if (!names.isEmpty()) {
                Class<?> containerType = getSubtype(dimensions, invokedMethod);
                Class<?> insideType = getSubtype(dimensions + 1, invokedMethod);
                obj = builder.build(containerType, insideType, names.size());
                for (String childProperty : names) {
                    Object child = dataSet.getAdapter(insideType).adapt(dataSet, childProperty, invokedMethod, dimensions + 1);
                    adder.add(obj, childProperty.substring(childProperty.lastIndexOf(".") + 1), child);
                }
                if (postProcessor != null) {
                    obj = postProcessor.process(containerType, obj, names.size());
                }
            }
            return obj;
        }
    }
     private static class SimpleTypeAdapter implements TypeAdapter {

         Function<String, Object> function;

        public SimpleTypeAdapter(Function<String, Object> function) {
            this.function = function;
        }
         
        @Override
        public Object adapt(DataSet dataSet, String propertyName, Method invokeMethod, int dimension) {
            String propertyValue = dataSet.getDataProvider().getProperty(propertyName);
            Object result = null;
            if (propertyValue != null){
                result = function.apply(propertyValue);
            }
            return result;
        }
     }

    private static Map<Predicate<Class<?>>, TypeAdapter> buildAdapters() {
        TypeAdapterTemplate.Builder arrayTemplateBuilder = (containerType, type, size) -> Array.newInstance(type, size);
        TypeAdapterTemplate.Adder arrayTemplateAdder = (target, name, element) -> Array.set(target, Integer.valueOf(name), element);

        Map<Predicate<Class<?>>, TypeAdapter> result = new LinkedHashMap<>();
        result.put(type -> type.isArray(), new TypeAdapterTemplate(arrayTemplateBuilder, arrayTemplateAdder, null));
        result.put(type -> type.isEnum(), (dataSet, propertyName, method, dimension) -> Enum.valueOf((Class<? extends Enum>) method.getReturnType(), dataSet.getDataProvider().getProperty(propertyName)));
        result.put(type -> List.class.isAssignableFrom(type), new TypeAdapterTemplate(arrayTemplateBuilder, arrayTemplateAdder, (type, target, size) -> {
            List list = (List) instancianteContainer(type);
            IntStream.range(0, size - 1).mapToObj(i -> Array.get(target, i)).forEach(e -> list.add(e));
            return list;
        }));
        result.put(type -> Map.class.isAssignableFrom(type), new TypeAdapterTemplate((containerType, type, size) -> instancianteContainer(containerType), (target, name, element) -> ((Map) target).put(name, element), null));
        result.put(type -> Set.class.isAssignableFrom(type), new TypeAdapterTemplate((containerType, type, size) -> instancianteContainer(containerType), (target, name, element) -> ((Set) target).add(element), null));
        result.put(type -> type.isInterface(), (dataSet, propertyName, method, dimension) -> dataSet.newProxyInstance(propertyName, getSubtype(dimension, method)));
        result.put(type -> type == String.class, (dataSet, propertyName, method, dimension) -> dataSet.getDataProvider().getProperty(propertyName));
        result.put(type -> type == Boolean.class || type == boolean.class, new SimpleTypeAdapter(p -> Boolean.valueOf(p)));
        result.put(type -> type == Byte.class || type == byte.class, new SimpleTypeAdapter(p -> Byte.valueOf(p)));
        result.put(type -> type == Double.class || type == double.class, new SimpleTypeAdapter(p -> Double.valueOf(p)));
        result.put(type -> type == Float.class || type == float.class, new SimpleTypeAdapter(p -> Float.valueOf(p)));
        result.put(type -> type == Integer.class || type == int.class, new SimpleTypeAdapter(p -> Integer.valueOf(p)));
        result.put(type -> type == Long.class || type == long.class, new SimpleTypeAdapter(p -> Long.valueOf(p)));
        result.put(type -> type == Short.class || type == short.class, new SimpleTypeAdapter(p -> Short.valueOf(p)));
        result.put(type -> type == Character.class || type == char.class, (dataSet, propertyName, method, dimension) -> {
            Object obj = null;
            String value = dataSet.getDataProvider().getProperty(propertyName);
            if (value != null) {
                if (value.length() == 1) {
                    obj = value.charAt(0);
                } else {
                    throw new ClassCastException("\"" + value + "\" cannot be cast to char");
                }
            }
            return obj;
        });
        return result;
    }
}
