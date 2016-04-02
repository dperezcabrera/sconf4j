package com.github.dperezcabrera.sconf4j.fluent;

import java.lang.reflect.InvocationHandler;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public interface InterfaceBridge {

    InvocationHandler buidBridge(Class<?> type, InvocationHandler target);
}
