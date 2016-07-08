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

import com.github.dperezcabrera.sconf4j.core.Mapping;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
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
