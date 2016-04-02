package com.github.dperezcabrera.sconf4j;

import com.github.dperezcabrera.sconf4j.fluent.InterfaceBridge;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
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
public class AllowInterfaceBridge implements InterfaceBridge {

    private static final Logger LOGGER = LoggerFactory.getLogger(AllowInterfaceBridge.class);

    @Override
    public InvocationHandler buidBridge(Class<?> type, InvocationHandler target) {
        return new BridgeInvocationHandler(type, target);
    }

    private static class BridgeInvocationHandler implements InvocationHandler {

        private static final Map<Class<?>, Object> DEFAULT_PRIMITIVES = new HashMap<Class<?>, Object>() {
            {
                put(boolean.class, false);
                put(byte.class, (byte) 0);
                put(char.class, (char) 0);
                put(double.class, 0d);
                put(float.class, 0f);
                put(int.class, 0);
                put(long.class, 0L);
                put(short.class, (short) 0);
            }
        };

        private InvocationHandler target;
        private Set<Method> denyMethods;

        private BridgeInvocationHandler(Class<?> type, InvocationHandler target) {
            this.target = target;
            this.denyMethods = getIgnoreMethods(type);
        }

        private static Set<Method> getIgnoreMethods(Class<?> type) {
            Set<Method> result = Collections.emptySet();
            if (type.isInterface()) {
                result = new HashSet<>();
                for (Method method : type.getMethods()) {
                    Class<?> returnType = method.getReturnType();
                    if (returnType == Void.TYPE || method.getParameterCount() > 2) {
                        LOGGER.warn("Unespected definition '{}'", method);
                        result.add(method);
                    } else if (!method.getName().startsWith("get")) {
                        if (!method.getName().startsWith("is")) {
                            LOGGER.warn("Unespected definition '{}'", method);
                            result.add(method);
                        } else if (returnType != boolean.class && returnType != Boolean.class) {
                            LOGGER.warn("Unespected definition '{}'", method);
                            result.add(method);
                        }
                    } else if (method.getParameterCount() == 1 && returnType != method.getParameterTypes()[0]) {
                        LOGGER.warn("Unespected definition '{}'", method);
                        result.add(method);
                    } else {
                        getIgnoreMethods(returnType);
                    }
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
                if (result == null && args != null && args.length == 1) {
                    result = args[0];
                }
            }
            return result;
        }
    }
}
