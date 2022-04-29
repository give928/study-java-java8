package com.give928.java.java8.method_reference;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class App {
    public static void main(String[] args) {
        // 메서드 레퍼런스
        System.out.println();
        UnaryOperator<String> hiUnaryOperator = (s) -> "hi " + s;
        System.out.println("hiUnaryOperator.apply(\"jooho\") = " + hiUnaryOperator.apply("jooho"));
        UnaryOperator<String> hiMethodReferenceUnaryOperator = Greeting::hi;
        System.out.println("hiMethodReferenceUnaryOperator.apply(\"jooho\") = " + hiMethodReferenceUnaryOperator.apply("jooho"));

        Greeting greeting = new Greeting();
        UnaryOperator<String> helloMethodReferenceUnaryOperator = greeting::hello;
        System.out.println("helloMethodReferenceUnaryOperator.apply(\"jooho\") = " + helloMethodReferenceUnaryOperator.apply("jooho"));

        Supplier<Greeting> newGreetingSupplier = Greeting::new;
        System.out.println("newGreetingSupplier.get().hello(\"jooho\") = " + newGreetingSupplier.get().hello("jooho"));

        Function<String, Greeting> getGreetingByNameFunction = Greeting::new;
        Greeting greetingByName = getGreetingByNameFunction.apply("jooho");
        System.out.println("greetingByName.getName() = " + greetingByName.getName());

        String[] nameArray = {"jh", "yy", "cs"};
        System.out.println("Arrays.toString(names) = " + Arrays.toString(nameArray));
        Arrays.sort(nameArray, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        });
        System.out.println("Arrays.toString(names) = " + Arrays.toString(nameArray));
        Arrays.sort(nameArray, (o1, o2) -> o1.compareToIgnoreCase(o2));
        System.out.println("Arrays.toString(names) = " + Arrays.toString(nameArray));
        Arrays.sort(nameArray, String::compareToIgnoreCase);
        System.out.println("Arrays.toString(names) = " + Arrays.toString(nameArray));
    }
}
