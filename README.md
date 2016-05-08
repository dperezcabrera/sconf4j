[![License](http://img.shields.io/:license-gpl3-blue.svg)](http://www.gnu.org/licenses/gpl-3.0.html)
[![Build Status](https://travis-ci.org/dperezcabrera/sconf4j.svg?branch=master)](https://travis-ci.org/dperezcabrera/sconf4j)
[![Coverage Status](https://coveralls.io/repos/github/dperezcabrera/sconf4j/badge.svg?branch=master)](https://coveralls.io/github/dperezcabrera/sconf4j?branch=master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/5988bcd325c14e1ba8723618c3c98c60)](https://www.codacy.com/app/dperezcabrera/sconf4j?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=dperezcabrera/sconf4j&amp;utm_campaign=Badge_Grade)
[![GitHub issues](https://img.shields.io/github/issues-raw/dperezcabrera/sconf4j.svg?maxAge=2592000)](https://github.com/dperezcabrera/sconf4j/issues)


# Simple Configurator for Java

The Simple Configurator for Java software library provides a simple configurator interface which enables a Java application to read configuration data from properties easily.

## Basic usage

```java
String prefix = "prefix";
ExampleConfiguration config = ConfiguratorFactory.configurator(ConfigurationOwner.class).get(ExampleConfiguration.class, properties, prefix);
```

## Based on interface definition
```java

    public interface ExampleConfiguration {   

        public int getIndex();

        public String getName();

        public boolean isSomething();

        @Mapping(value = Integer.class)
        public ArrayList<Integer> getArray();

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
    }
```

## Data from properties
```java
Properties properties = new Properties();
properties.setProperty("prefix.index", "111");
properties.setProperty("prefix.defined", "1");
properties.setProperty("prefix.others.0.0.field", "field-0.0-1");
properties.setProperty("prefix.others.0.0.field2", "field-0.0-2");
properties.setProperty("prefix.others.0.1.field", "field-0.1-1");
properties.setProperty("prefix.others.0.1.field2", "field-0.1-2");
properties.setProperty("prefix.others.1.0.field", "field-1.0-1");
properties.setProperty("prefix.others.1.0.field2", "field-1.0-2");
properties.setProperty("prefix.others.1.1.field", "field-1.1-1");
properties.setProperty("prefix.others.1.1.field2", "field-1.1-2");
properties.setProperty("prefix.others.2.0.field", "field-2.0-1");
properties.setProperty("prefix.others.2.1.field2", "field-2.1-2");
properties.setProperty("prefix.others.2.2.field", "field-2.2-1");
properties.setProperty("prefix.others.3.0.field2", "field-3.0-2");
properties.setProperty("prefix.name", "supername");
properties.setProperty("prefix.something", "True");
properties.setProperty("prefix.array.0", "10");
properties.setProperty("prefix.array.1", "1");
properties.setProperty("prefix.array.2", "1");
properties.setProperty("prefix.array.3", "3");
properties.setProperty("prefix.array.4", "3");
properties.setProperty("prefix.array.5", "3");
properties.setProperty("prefix.enum", "ONE");
properties.setProperty("prefix.type", "java.lang.String");
properties.setProperty("prefix.c", "c");
```


## Maven install
```shell
mvn clean install
```

## Maven local repository dependency
```xml
<dependency>
    <groupId>com.github.dperezcabrera</groupId>
    <artifactId>sconf4j</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```
