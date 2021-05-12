package com.keyin.designpattern.create5.factory;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static junit.framework.TestCase.assertNotNull;

/**
 * @description:
 * @author: yinke
 * @create: 2021-04-27 16:54
 **/


public class FactoryTest {

    @Test
    public void whenGetSimpleBean_thenReturnConstructedBean() {

        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        Foo foo = context.getBean(Foo.class);

        assertNotNull(foo);
    }

    @Test
    public void whenGetPrototypeBean_thenReturnConstructedBean() {

        String expectedName = "Some name";
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        Bar bar = context.getBean(Bar.class, expectedName);

        assertNotNull(bar);
    }
}
