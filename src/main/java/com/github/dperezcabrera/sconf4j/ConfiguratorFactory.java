package com.github.dperezcabrera.sconf4j;

import com.github.dperezcabrera.sconf4j.fluent.DataSetBase;
import com.github.dperezcabrera.sconf4j.fluent.InstanceBuilder;
import com.github.dperezcabrera.sconf4j.fluent.InstanceBuilderBase;
import com.github.dperezcabrera.sconf4j.fluent.InterfaceBridge;
import com.github.dperezcabrera.sconf4j.fluent.PropertiesDataProvider;
import com.github.dperezcabrera.sconf4j.fluent.TypeAdapter;
import com.github.dperezcabrera.sconf4j.fluent.TypeAdapterBase;
import java.util.Properties;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public final class ConfiguratorFactory {

    static InterfaceBridge bridge = new AllowInterfaceBridge();
    static InstanceBuilder instanceProxyBuilder = new InstanceBuilderBase(bridge);
    static TypeAdapter typeAdapter = new TypeAdapterBase();

    private ConfiguratorFactory() {
    }

    public static <K> Configurator<K> configurator(Class<K> owner) {

        return new Configurator<K>() {
            public <T> T get(Class<T> target, String param) {
                return null;
            }

            @Override
            public <T> T get(Class<T> target) {
                return null;
            }

            @Override
            public <T> void subscribe(Class<T> target, Subscriber<T> subscriber) {

            }

            @Override
            public <T> T get(Class<T> target, Properties properties) {
                return null;
            }

            @Override
            public <T> T get(Class<T> target, Properties properties, String param) {
                return instanceProxyBuilder.build(new DataSetBase(new PropertiesDataProvider(properties), instanceProxyBuilder, typeAdapter), param, target);
            }
        };
    }
}
