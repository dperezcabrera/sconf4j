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
package com.github.dperezcabrera.sconf4j.integration;

import com.github.dperezcabrera.sconf4j.integration.ExampleConfiguration.Inner;
import com.github.dperezcabrera.sconf4j.integration.SpringIntegrationTest.ConfigFoo;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.github.dperezcabrera.sconf4j.spring.EnableSConf4j;
import com.github.dperezcabrera.sconf4j.spring.AutowiredConfiguration;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConfigFoo.class)
public class SpringIntegrationTest {

    String param = "some.prefix";

    @AutowiredConfiguration
    ExampleConfiguration exampleConfig;

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

    @Configuration
    @EnableSConf4j
    public static class ConfigFoo {

    }
}
