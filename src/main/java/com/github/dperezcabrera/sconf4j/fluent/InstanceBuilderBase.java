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
