package com.give928.java.java8.functional_interface;

/**
 * 함수형 인터페이스
 *
 * 추상 메서드를 딱 하남나 가지고 있는 인터페이스
 * SAM(Single Abstract Method) 인터페이스
 * @FunctionalInterface 애너테이션을 가지고 있는 인터페이스
 *
 * static 메서드, default 메서드는 있어도 된다.
 */
@FunctionalInterface
public interface RunSomething {
    int sum(int a, int b);

    static void printName() {
        System.out.println("jooho");
    }

    default void printAge() {
        System.out.println("34");
    }
}
