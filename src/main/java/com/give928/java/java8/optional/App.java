package com.give928.java.java8.optional;

import com.give928.java.java8.domains.OnlineClass;
import com.give928.java.java8.domains.Progress;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class App {
    public static void main(String[] args) {
        List<OnlineClass> springClasses = new ArrayList<>();
        springClasses.add(new OnlineClass(1, "spring boot", true));
        springClasses.add(new OnlineClass(5, "rest api development", false));

        Optional<OnlineClass> optional = springClasses.stream()
                .filter(oc -> oc.getTitle().startsWith("spring"))
                .findFirst();

        boolean hasOnlineClass = optional.isPresent();
        System.out.println("hasOnlineClass = " + hasOnlineClass);

        optional.ifPresent(oc -> System.out.println("oc = " + oc));

        OnlineClass onlineClass1 = optional.orElse(createNewOnlineClass("new jpa class 1"));
        System.out.println("onlineClass1 = " + onlineClass1);

        OnlineClass onlineClass2 = optional.orElseGet(() -> createNewOnlineClass("new jpa class 2"));
        System.out.println("onlineClass2 = " + onlineClass2);

//        OnlineClass onlineClass3 = optional.orElseThrow(); // NoSuchElementException
//        System.out.println("onlineClass3 = " + onlineClass3);

//        OnlineClass onlineClass4 = optional.orElseThrow(IllegalStateException::new);
//        System.out.println("onlineClass4 = " + onlineClass4);

        Optional<OnlineClass> onlineClass5 = optional.filter(OnlineClass::isClosed);
        System.out.println("onlineClass5.isEmpty() = " + onlineClass5.isEmpty());
        System.out.println("onlineClass5.isPresent() = " + onlineClass5.isPresent());

        Optional<Integer> integerOptional = optional.map(OnlineClass::getId);
        System.out.println("integerOptional.isPresent() = " + integerOptional.isPresent());

        Optional<Optional<Progress>> progressOptionalOptional = optional.map(OnlineClass::getProgress);
        Optional<Progress> progressOptional1 = progressOptionalOptional.orElse(Optional.empty());
        System.out.println("progressOptional1.isPresent() = " + progressOptional1.isPresent());

        Optional<Progress> progressOptional2 = optional.flatMap(OnlineClass::getProgress);
        System.out.println("progressOptional2.isPresent() = " + progressOptional2.isPresent());
    }

    private static OnlineClass createNewOnlineClass(String title) {
        System.out.println("creating " + title);
        return new OnlineClass(10, title, false);
    }
}
