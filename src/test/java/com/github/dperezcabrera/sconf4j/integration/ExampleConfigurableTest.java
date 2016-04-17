package com.github.dperezcabrera.sconf4j.integration;

import com.github.dperezcabrera.sconf4j.Configurator;
import com.github.dperezcabrera.sconf4j.ConfiguratorFactory;
import com.github.dperezcabrera.sconf4j.core.ConfiguratorException;
import com.github.dperezcabrera.sconf4j.core.Mapping;
import com.github.dperezcabrera.sconf4j.integration.ExampleConfigurableTest.ExampleConfiguration.Inner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.startsWith;
import org.junit.Test;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
public class ExampleConfigurableTest {

    String param = "some.prefix";
    ExampleConfiguration exampleConfig;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void beforeTest() {
        final Configurator<ExampleConfigurableTest> configurator = ConfiguratorFactory.configurator(ExampleConfigurableTest.class);
        exampleConfig = configurator.get(ExampleConfiguration.class, properties4example(param), param);
    }
    //*

    @Test
    public void configGet2DArray() {
        Inner[][] inners = exampleConfig.getOthers();
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

    @Test
    public void configGetIndex() {
        assertEquals(111, exampleConfig.getIndex());
    }

    @Test
    public void configGetDefinedWithDefault() {
        assertEquals(1, exampleConfig.getDefined(1));
    }

    @Test
    public void configGetDefinedWithOther() {
        assertEquals(1, exampleConfig.getDefined(0));
    }

    @Test
    public void configGetDefined() {
        assertEquals(1, exampleConfig.getDefined());
    }

    @Test
    public void configGetUndefined() {
        assertEquals(0, exampleConfig.getUndefined(0));
    }

    @Test
    public void configGetOtherUndefined() {
        assertEquals(1, exampleConfig.getUndefined(1));
    }

    @Test
    public void configGetString() {
        assertEquals("supername", exampleConfig.getName());
    }

    @Test
    public void configIsBoolean() {
        assertEquals(true, exampleConfig.isSomething());
    }

    @Test
    public void configGetEnum() {
        assertEquals(ExampleConfiguration.Enum.ONE, exampleConfig.getEnum());
    }

    @Test
    public void configGetList() {
        assertEquals(new ArrayList<>(Arrays.asList(10, 1, 1, 3, 3, 3)), exampleConfig.getArray());
    }

    @Test
    public void configGetClass() {
        assertEquals(String.class, exampleConfig.getType());
    }

    @Test
    public void configIngoreMethodPrimitive() {
        assertEquals(0, exampleConfig.ignoreInt(1));
    }

    @Test
    public void configIgnoreMethodBoolean() {
        assertEquals(false, exampleConfig.ignoreBoolean());
    }

    @Test
    public void configIgnoreObject() {
        assertEquals(null, exampleConfig.ignoreObject());
    }

    @Test
    public void configGetChar() {
        assertEquals('3', exampleConfig.getC());
    }

    @Test
    public void configGetObject() {
        assertNotNull(exampleConfig.getExample());
        assertEquals((short) 3, exampleConfig.getExample().getS());
    }

    @Test
    public void configGetNullPointerExceptionInObject() {
        thrown.expect(NullPointerException.class);
        exampleConfig.getNullPointerException();
    }

    @Test
    public void configGetConfiguratorExceptionInObject() {
        thrown.expect(ConfiguratorException.class);
        exampleConfig.getExceptionExample();
    }

    @Test
    public void configGetConfiguratorExceptionInAmbiguousSetter() {
        thrown.expect(ConfiguratorException.class);
        thrown.expectMessage(startsWith("Ambiguous methods for set property"));
        exampleConfig.getSetterExcp();
    }

    @Test
    public void configGetConfiguratorExceptionInAmbiguousSetterMapped() {
        thrown.expect(ConfiguratorException.class);
        thrown.expectMessage(startsWith("Ambiguous methods for set property"));
        exampleConfig.getSetterExcpMapped();
    }

    @Test
    public void configGetConfiguratorExceptionInSetter3() {
        thrown.expect(ConfiguratorException.class);
        thrown.expectMessage(startsWith("There isn't a candidate Method for set property "));
        exampleConfig.getSetterExcp3();
    }

    @Test
    public void configGetConfiguratorExceptionInSetter4() {
        thrown.expect(ConfiguratorException.class);
        thrown.expectMessage(containsString(" could not be invoked for property "));
        exampleConfig.getSetterExcp4();
    }
    
    @Test
    public void configGetConfiguratorExceptionInSetter5() {
        thrown.expect(ConfiguratorException.class);
        exampleConfig.getSetterExcp5();
    }

    @Test
    public void configGetArrayIndexError() {
        thrown.expect(ConfiguratorException.class);
        thrown.expectMessage(startsWith("Wrong indexes"));
        exampleConfig.getArrayErr();
    }

    @Test
    public void configGetConfiguratorExceptionInSetter() {
        thrown.expect(ConfiguratorException.class);
        thrown.expectMessage(startsWith("Could not build an Object to property '" + param + ".setterExcp2'"));
        exampleConfig.getSetterExcp2();
    }
    
    @Test
    public void configGetConfiguratorExceptionChar() {
        thrown.expect(ClassCastException.class);
        exampleConfig.getChar();
    }

    @Test
    public void configGetArrayEmpty() {
        assertNotNull(exampleConfig.getEmptyArray());
        assertEquals(0, exampleConfig.getEmptyArray().length);
    }

    @Test
    public void configGetEmpty() {
        assertNull(exampleConfig.getNullArray());
    }

    @Test
    public void configGetNull() {
        assertNull(exampleConfig.getNull());
    }

    //*/
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

    public interface ExampleConfiguration {

        public int[] getNullArray();

        public Integer[] getEmptyArray();

        public Object getNull();
        
        public Character getChar();

        public ObjectExample getExample();

        public ObjectExceptionExample getExceptionExample();

        public ObjectSetterExceptionExample getSetterExcp();

        @Mapping(value = ObjectSetterExceptionExample3.class, property = "setterExcp")
        public ObjectSetterExceptionExample3 getSetterExcp3();

        @Mapping(value = ObjectSetterExceptionExample4.class, property = "setterExcp")
        public ObjectSetterExceptionExample4 getSetterExcp4();

        @Mapping(value = ObjectSetterExceptionExample5.class, property = "setterExcp")
        public ObjectSetterExceptionExample5 getSetterExcp5();

        @Mapping(value = ObjectSetterExceptionExampleMapped.class, property = "setterExcp")
        public ObjectSetterExceptionExampleMapped getSetterExcpMapped();

        public ObjectSetterExceptionExample2 getSetterExcp2();

        public int getIndex();

        public void getNothing();

        public String getName();

        public String getNames(String n, String s);

        public boolean isSomething();

        public Boolean isSomethingWrapper();

        public String isSomethingString();

        public boolean ignoreBoolean();

        public int ignoreInt(int d, int f);

        public int ignoreInt(int d);

        public int getNullPointerException();

        public Object ignoreObject();

        @Mapping(value = Integer.class)
        public ArrayList<Integer> getArray();

        public Integer[] getArrayErr();

        public int getUndefined(int defaultValueIfNoExist);

        public int getIgnoredObject(Object defaultValueIfNoExist);

        @Mapping(value = int.class, property = "defined")
        public int getDefined();

        public int getDefined(int defaultValueIfNoExist);

        public Class<?> getType();

        public Enum getEnum();

        public char getC();

        public Inner[][] getOthers();

        public interface Inner {

            public String getField();

            public String getField2();
        }

        public enum Enum {
            ONE,
            TWO
        }

        public class ObjectExample {

            short s;

            public ObjectExample() {
            }

            public short getS() {
                return s;
            }

            public void setS(short s) {
                this.s = s;
            }
        }

        public class ObjectExceptionExample {

            int s;

            public void setExcp(int n) {
                throw new RuntimeException();
            }

            public int getExcp() {
                return s;
            }

            public List getList() {
                return null;
            }
        }

        public class ObjectSetterExceptionExample {

            @Mapping(value = Integer.class)
            public List<Integer> getList() {
                return null;
            }

            @Mapping(value = Integer.class)
            public void setList(ArrayList<Integer> list) {
            }

            @Mapping(value = Integer.class)
            public void setList(LinkedList<Integer> list) {
            }
        }

        public class ObjectSetterExceptionExample4 {

            @Mapping(value = Integer.class)
            public List<Integer> getList() {
                return null;
            }

            @Mapping(value = Integer.class)
            public void setList(List<Integer> list) {
                throw new RuntimeException();
            }

            @Mapping(value = Integer.class)
            public void setLista(LinkedList<Integer> list) {
                throw new RuntimeException();
            }
        }

        public class ObjectSetterExceptionExample5 {

            @Mapping(value = Integer.class)
            public List<Integer> getList() {
                return null;
            }

            @Mapping(value = Integer.class)
            public void setLista(List<Integer> list) {
                throw new RuntimeException();
            }

            @Mapping(value = Integer.class)
            public void setList(LinkedList<Integer> list) {
                throw new RuntimeException();
            }
        }

        public class ObjectSetterExceptionExample3 {

            @Mapping(value = Integer.class)
            public List<Integer> getList() {
                return null;
            }
        }

        public class ObjectSetterExceptionExampleMapped {

            @Mapping(value = Integer.class)
            public List<Integer> getList() {
                return null;
            }

            @Mapping(value = Integer.class, property = "list")
            public void setList(ArrayList<Integer> list) {
            }

            @Mapping(value = Integer.class, property = "list")
            public void setList(LinkedList<Integer> list) {
            }
        }

        public class ObjectSetterExceptionExample2 {

            private ObjectSetterExceptionExample2() {
            }

            public ObjectSetterExceptionExample2(int n) {
            }
        }
    }
}
