package com.give928.java.java8.default_methods;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Spliterator;

public class App {
    public static void main(String[] args) {
        // 인터페이스 기본 메서드와 스태틱 메서드
        System.out.println();
        Foo foo = new DefaultFoo("jooho");
        foo.printName();
        foo.printNameUpperCase();
        Foo.printNameCapitalize();

        // 자바 8에서 추가한 기본 메서드로 인한 API 변화
        System.out.println();
        List<String> nameList = new ArrayList<>();
        nameList.add("jh");
        nameList.add("give928");
        nameList.add("yy");
        nameList.add("cs");

        nameList.forEach(System.out::println);
        System.out.println();
        nameList.forEach(System.out::println);
        System.out.println();

        Spliterator<String> nameListSpliterator = nameList.spliterator();
        while (nameListSpliterator.tryAdvance(System.out::println));
        System.out.println();
        Spliterator<String> nameListSpliterator1 = nameList.spliterator();
        Spliterator<String> nameListSpliterator2 = nameListSpliterator1.trySplit();
        while (nameListSpliterator1.tryAdvance(System.out::println));
        System.out.println("======================");
        while (nameListSpliterator2.tryAdvance(System.out::println));

        System.out.println();
        long k = nameList.stream().map(String::toUpperCase)
                .filter(s -> s.startsWith("J"))
                .count();
        System.out.println("k = " + k);

        System.out.println();
        nameList.removeIf(s -> s.startsWith("j"));
        nameList.forEach(System.out::println);

        System.out.println();
        Comparator<String> compareToIgnoreCase = String::compareToIgnoreCase;
        nameList.sort(compareToIgnoreCase.reversed().thenComparing(String::length));
        nameList.forEach(System.out::println);
    }
}
