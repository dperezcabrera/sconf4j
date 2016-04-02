package com.github.dperezcabrera.sconf4j.integration;

import com.github.dperezcabrera.sconf4j.Configurator;
import com.github.dperezcabrera.sconf4j.ConfiguratorFactory;
import com.github.dperezcabrera.sconf4j.fluent.Mapping;
import com.github.dperezcabrera.sconf4j.integration.ExampleConfigurableTest.ExampleConfiguration.Inner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class ExampleConfigurableTest {

    @Test
    public void example() {
        String param = "some.prefix";
        final Configurator<ExampleConfigurableTest> configurator = ConfiguratorFactory.configurator(ExampleConfigurableTest.class);

        ExampleConfiguration example = configurator.get(ExampleConfiguration.class, properties4example(param), param);

        assertEquals(111, example.getIndex());
        assertEquals(1, example.getDefined(0));
        assertEquals(1, example.getDefined());
        assertEquals(0, example.getUndefined(0));
        assertEquals("supername", example.getName());
        assertEquals(true, example.isSomething());
        assertEquals(ExampleConfiguration.Enum.ONE, example.getEnum());
        assertEquals(new ArrayList<>(Arrays.asList(10, 1, 1, 3, 3)), example.getArray());

        Inner[][] inners = example.getOthers();
        assertNotNull(inners);
        assertEquals(4, inners.length);
        assertEquals(2, inners[0].length);
        assertEquals("field-0.0-1", inners[0][0].getField());
        assertEquals("field-0.0-2", inners[0][0].getField2());
        assertEquals("field-0.1-1", inners[0][1].getField());
        assertEquals("field-0.1-2", inners[0][1].getField2());
        assertEquals(2, inners[1].length);
        assertEquals("field-1.0-1", inners[1][0].getField());
        assertEquals("field-1.0-2", inners[1][0].getField2());
        assertEquals("field-1.1-1", inners[1][1].getField());
        assertEquals("field-1.1-2", inners[1][1].getField2());
        assertEquals(3, inners[2].length);
        assertEquals("field-2.0-1", inners[2][0].getField());
        assertEquals(null, inners[2][0].getField2());
        assertEquals(null, inners[2][1].getField());
        assertEquals("field-2.1-2", inners[2][1].getField2());
        assertEquals("field-2.2-1", inners[2][2].getField());
        assertEquals(null, inners[2][2].getField2());
        assertEquals(1, inners[3].length);
        assertEquals(null, inners[3][0].getField());
        assertEquals("field-3.0-2", inners[3][0].getField2());
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
        p.setProperty(prefix + ".indexs", "--");
        p.setProperty(prefix + ".something", "True");
        p.setProperty(prefix + ".array.0", "10");
        p.setProperty(prefix + ".array.1", "1");
        p.setProperty(prefix + ".array.2", "1");
        p.setProperty(prefix + ".array.3", "3");
        p.setProperty(prefix + ".array.4", "3");
        p.setProperty(prefix + ".array.5", "3");
        p.setProperty(prefix + ".enum", "ONE");
        return p;
    }

    public interface ExampleConfiguration {

        public int getIndex();

        public String getName();

        public boolean isSomething();

        @Mapping(Integer.class)
        public ArrayList<Integer> getArray();

        public int getUndefined(int defaultValueIfNoExist);
        public int getDefined();
        public int getDefined(int defaultValueIfNoExist);

        public Enum getEnum();

        public Inner[][] getOthers();

        public interface Inner {

            public String getField();

            public String getField2();
        }

        public enum Enum {
            ONE,
            TWO
        }
    }
}
