package com.give928.java.java8.annotation;

import java.util.Arrays;
import java.util.List;

@Chicken("양념")
@Chicken("반반")
@Chicken("간장")
public class App {
    public static void main(String[] args) {
        Chicken[] chickens = App.class.getAnnotationsByType(Chicken.class);
        Arrays.stream(chickens).forEach(chicken -> System.out.println("chicken.value() = " + chicken.value()));

        System.out.println();

        ChickenContainer chickenContainer = App.class.getAnnotation(ChickenContainer.class);
        Arrays.stream(chickenContainer.value()).forEach(chicken -> System.out.println("chicken.value() = " + chicken.value()));
    }

    @Chicken
    static class FeelsLikeChicken<@Chicken T> {
        private List<@Chicken String> names = Arrays.asList("jooho", "kim");

        public static <@Chicken C> void print(@Chicken C c) throws @Chicken RuntimeException {
            System.out.println("c = " + c);
        }
    }
}
