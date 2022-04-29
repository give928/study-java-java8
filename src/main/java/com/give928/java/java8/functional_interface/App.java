package com.give928.java.java8.functional_interface;

import java.util.function.*;

public class App {
    public static void main(String[] args) {
        // @FunctionalInterface 로 인터페이스 직접 생성
        /*
        // 익명 내부클래스 anonymous inner class
        TestFunctionalInterface testFunctionalInterface = new TestFunctionalInterface() {
            int i = 0;
            @Override
            public int sum(int a, int b) {
                System.out.println("Hello TestFunctionalInterface.sum");
                return a + b + ++i; // 내부 상태로 값이 변경되면 순수 함수가 아니다.
            }
        };
        */
        RunSomething runSomething = (a, b) -> a + b;

        System.out.println(runSomething.sum(1, 2));
        System.out.println(runSomething.sum(1, 2)); // 동일한 입력에 동일한 결과가 나와야 순수 함수

        // Java가 기본으로 제공하는 함수형 인터페이스
        // Function<T, R>
        System.out.println();
        Plus10 plus10 = new Plus10();
        System.out.println("plus10.apply(1) = " + plus10.apply(3));

        Function<Integer, Integer> plus10Function = (i) -> i + 10;
        System.out.println("plus10Function.apply(1) = " + plus10Function.apply(3));

        Function<Integer, Integer> multiply3Function = (i) -> i * 3;
        System.out.println("multiply3Function.apply(1) = " + multiply3Function.apply(3));

        Function<Integer, Integer> multiply3AndPlus10Function = plus10Function.compose(multiply3Function);
        System.out.println("multiply3AndPlus10Function.apply(1) = " + multiply3AndPlus10Function.apply(3));

        Function<Integer, Integer> plus10AndMultiply3Function = plus10Function.andThen(multiply3Function);
        System.out.println("plus10AndMultiply3Function.apply(1) = " + plus10AndMultiply3Function.apply(3));

        // BiFunction<T, U, R>
        System.out.println();
        BiFunction<Integer, Integer, Integer> sumFunction = (i, j) -> i + j;
        System.out.println("sumFunction.apply(1, 2) = " + sumFunction.apply(1, 2));

        // Consumer<T>
        System.out.println();
        Consumer<Integer> printConsumer = (i) -> System.out.printf("printConsumer: %d%n", i);
        printConsumer.accept(2);

        // Supplier<T>
        System.out.println();
        Supplier<Integer> get10Supplier = () -> 10;
        System.out.println("get10Supplier.get() = " + get10Supplier.get());

        // Predicate<T>
        System.out.println();
        Predicate<String> isStartsWithJoohoPredicate = (s) -> s.startsWith("jooho");
        System.out.println("isStartsWithJoohoPredicate.test(\"jooho\") = " + isStartsWithJoohoPredicate.test("jooho"));
        Predicate<Integer> isOddPredicate = (i) -> i % 2 == 1;
        System.out.println("isOddPredicate.test(1) = " + isOddPredicate.test(1));
        Predicate<Integer> isGreaterThen5Predicate = (i) -> i > 5;
        System.out.println("isOddPredicate.and(isGreaterThen5Predicate).test(7) = " + isOddPredicate.and(isGreaterThen5Predicate).test(7));
        System.out.println("isOddPredicate.and((i) -> i > 3  ).test(7) = " + isOddPredicate.and((i) -> i > 3).test(5));

        // UnaryOperator<T>
        System.out.println();
        UnaryOperator<Integer> plus10UnaryOperator = (i) -> i + 10;
        System.out.println("plus10UnaryOperator.apply(3) = " + plus10UnaryOperator.apply(3));

        // BinaryOperator<T>
        System.out.println();
        BinaryOperator<Integer> sumBinaryOperator = (i, j) -> i + j;
        System.out.println("sumBinaryOperator.apply(1, 2) = " + sumBinaryOperator.apply(1, 2));

        // 변수 캡처(Variable Capture)
        System.out.println();
        App app = new App();
        app.variableCapture();
    }

    private int baseNumber = 0;
    private void variableCapture() {
        int baseNumber = 10; // 값이 변경되지 않는 사실상 final인 경우 effective final

        // 내부 클래스
        class LocalClass {
            void printBaseNumber(Integer baseNumber) { // 클래스가 별도의 스코프라서 상위 변수가 가려진다. shadowing
                System.out.println("baseNumber = " + baseNumber);
            }
        }
        LocalClass localClass = new LocalClass();
        localClass.printBaseNumber(100);

        // 익명 클래스
        Consumer<Integer> integerConsumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer baseNumber) { // 클래스가 별도의 스코프라서 상위 변수가 가려진다. shadowing
                System.out.println("baseNumber = " + baseNumber);
            }
        };
        integerConsumer.accept(100);

        // 람다
        IntConsumer printInt = (i) -> {
            // int baseNumber = 100; // Variable 'baseNumber' is already defined in the scope
            // 람다는 스코프가 람다를 감싼 부분까지라서 동일한 변수명을 선언할 수 없다.
            System.out.println("baseNumber = " + baseNumber);
        };
        printInt.accept(10);
    }
}
