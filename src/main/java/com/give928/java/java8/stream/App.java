package com.give928.java.java8.stream;

import com.give928.java.java8.domains.OnlineClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
    public static void main(String[] args) {
        // 스트림 API
        List<String> nameList = new ArrayList<>();
        nameList.add("jh");
        nameList.add("give928");
        nameList.add("yy");
        nameList.add("cs");

        System.out.println();
        nameList.clear();
        nameList.add("jh");
        nameList.add("give928");
        nameList.add("yy");
        nameList.add("cs");

        Stream<String> nameListToUpperCaseStream = nameList.stream().map(s -> {
            System.out.println("### s = " + s); // 종료 오퍼레이션까지 실행되지 않는다.
            return s.toUpperCase();
        });
        System.out.println("===============");
        nameListToUpperCaseStream.forEach(System.out::println);

        System.out.println();
        List<String> nameToUpperCaseStreamList = nameList.parallelStream().map(s -> {
            String u = s.toUpperCase();
            System.out.printf("[%s] %s -> %s\n", Thread.currentThread(), s, u);
            return u;
        }).collect(Collectors.toList());
        nameToUpperCaseStreamList.forEach(System.out::println);

        System.out.println();
        System.out.println();

        List<OnlineClass> springClasses = new ArrayList<>();
        springClasses.add(new OnlineClass(1, "spring boot", true));
        springClasses.add(new OnlineClass(2, "spring data jpa", true));
        springClasses.add(new OnlineClass(3, "spring mvc", false));
        springClasses.add(new OnlineClass(4, "spring core", false));
        springClasses.add(new OnlineClass(5, "rest api development", false));

        List<OnlineClass> javaClasses = new ArrayList<>();
        javaClasses.add(new OnlineClass(6, "The Java, Test", true));
        javaClasses.add(new OnlineClass(7, "The Java, Code manipulation", true));
        javaClasses.add(new OnlineClass(8, "The Java, 8 to 11", false));

        List<List<OnlineClass>> onlineClasses = new ArrayList<>();
        onlineClasses.add(springClasses);
        onlineClasses.add(javaClasses);

        System.out.println("스프링 수업 중에서 spring 으로 시작하는 수업");
        springClasses.stream().filter(oc -> oc.getTitle().startsWith("spring"))
                .forEach(System.out::println);

        System.out.println("스프링 수업 중에서 close 되지 않은 수업");
        springClasses.stream().filter(Predicate.not(OnlineClass::isClosed))
                .forEach(System.out::println);

        System.out.println("스프링 수업 중에서 수업 이름만 모아서 스트림 만들기");
        springClasses.stream().map(OnlineClass::getTitle)
                .forEach(System.out::println);

        System.out.println("두 수업 목록에 들어있는 모든 수업 아이디 출력");
        onlineClasses.stream().flatMap(Collection::stream)
                .forEach(System.out::println);

        System.out.println("10부터 1씩 증가하는 무제한 스트림 중에서 앞에 10개 빼고 최대 10개 까지만");
        Stream.iterate(0, i -> i + 1)
                .skip(10)
                .limit(10)
                .forEach(System.out::println);

        System.out.println("자바 수업 중에서 Test가 들어있는 수업이 있는지 확인");
        boolean containsTestJavaClass = javaClasses.stream().anyMatch(oc -> oc.getTitle().contains("Test"));
        System.out.println("containsTestJavaClass = " + containsTestJavaClass);

        System.out.println("스프링 수업 중에서 제목에 spring이 들어간 제목만 모아서 List로 만들기");
        List<String> titleContainsSpringClasses = springClasses.stream().filter(oc -> oc.getTitle().contains("spring"))
                .map(OnlineClass::getTitle)
                .collect(Collectors.toList());
        titleContainsSpringClasses.forEach(System.out::println);
    }
}
