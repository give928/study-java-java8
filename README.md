# 백기선님 더 자바, Java 8 스터디

## 소개

- 2014년 3월 출시, LTS(Long-Term-Support)
- Java 8 72% 사용, Java 11 42%

### 자바 8의 주요 기능의 의미

- 함수형 프로그래밍
    - 함수형 인터페이스
    - 람다 표현식
    - 메서드 레퍼런스
- 비동기 프로그래밍
    - CompletableFuture
- 편의성 개선
    - Optional
    - Date & Time
    - 인터페이스(기본 메서드 등등)
    - ...

## 함수형 인터페이스와 람다 표현식

### 함수형 인터페이스(Functional Interface)

- 추상 메소드를 딱 하나만 가지고 있는 인터페이스
- SAM(Single Abstract Method) 인터페이스
- @FunctionalInterface 애너테이션을 가지고 있는 인터페이스

### 람다 표현식(Lambda Expressions)

- 함수형 인터페이스의 인스턴스를 만드는 방법으로 쓰일 수 있다.
- 코드를 줄일 수 있다.
- 메소드 매개변수, 리턴 타입, 변수로 만들어 사용할 수도 있다.

### 자바에서 함수형 프로그래밍

- 함수를 First class object로 사용할 수 있다.
- 순수 함수(Pure function)
    - 사이드 이펙트 만들 수 없다.(함수 밖에 있는 값을 변경하지 못한다. 참조도..)
    - 상태가 없다.(함수 밖에 정의되어 있는)
- 고차 함수(Higher-Order Function)
    - 함수가 함수를 매개변수로 받을 수 있고 함수를 리턴할 수도 있다.
- 불변성

### Java가 기본으로 제공하는 함수형 인터페이스

- [java.lang.function](https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html) 패키지
- 자바에서 미리 정의해둔 자주 사용할만한 함수 인터페이스
- Function<T, R>
    - T 타입을 받아서 R 타입을 리턴하는 함수 인터페이스
        - R apply(T t)
    - 함수 조합용 메서드
        - andThen
        - compose
- BiFunction<T, U, R>
    - 두 개의 값(T, U)를 받아서 R 타입을 리턴하는 함수 인터페이스
        - R apply(T t, U u)
- Consumer<T>
    - T 타입을 받아서 아무값도 리턴하지 않는 함수 인터페이스
        - void accept(T t)
    - 함수 조합용 메서드
        - andThen
- Supplier<T>
    - T 타입의 값을 제공하는 함수 인터페이스
        - T get()
- Predicate<T>
    - T 타입을 받아서 boolean을 리턴하는 함수 인터페이스
        - boolean test(T t)
    - 함수 조합용 메서드
        - and
        - or
        - negate
- UnaryOperator<T>
    - Function<T, R>의 툭수한 형태로, 입력값 하나를 받아서 동일한 타입을 리턴하는 함수 인터페이스
- BinaryOperator<T>
    - BiFunction<T, U, R>의 특수한 형태로, 동일한 타입의 입력값 두개를 받아 리턴하는 함수

### 람다

- (매개변수 리스트) → {바디}
- 매개변수 리스트
    - 없을 때: ()
    - 1개: (one) 또는 one
    - 여러개: (one, two)
    - 매개변수의 타입은 생략 가능, 컴파일러가 추론(infer)하지만 명시할 수도 있다.(Integer one, Integer two)
- 바디
    - 화살표 오른쪽에 함수 본문을 정의한다.
    - 여러 줄인 경우에 { }를 사용해서 묶는다.
    - 한 줄인 경우에 생략 가능, return도 생략 가능
- 변수 캡처(Variable Capture)
    - 로컬 변수 캡처
        - final이거나 effective final인 경우에만 참조할 수 있다.
        - 그렇지 않을 경우 concurrency 문제가 생길 수 있어서 컴파일러가 방지한다.
    - effective final
        - 이것도 역시 Java 8부터 지원하는 기능으로 “사실상" final인 변수
        - final 키워드 사용하지 않은 변수를 익명 클래스 구현체 또는 람다에서 참조할 수 있다.
    - 익명 클래스 구현체와 달리 ‘쉐도잉'하지 않는다.
        - 익명 클래스는 새로 스콥을 만들지만, 람다는 람다를 감싸고 있는 스콥과 같다.
    - 참고
        - [https://docs.oracle.com/javase/tutorial/java/javaOO/nested.html#shadowing](https://docs.oracle.com/javase/tutorial/java/javaOO/nested.html#shadowing)
        - [https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html)

## 메서드 레퍼런스

- 람다가 하는 일이 기존 메서드 또는 생성자를 호출하는 거라면, 메서드 레퍼런스를 사용해서 매우 간결하게 표현할 수 있다.
- 메서드 참조하는 방법

| 스태틱 메서드 참조 | 타입::스태틱 메서드 |
| --- | --- |
| 특정 객체의 인스턴스 메서드 참조 | 객체 레퍼런스::인스턴스 메서드 |
| 임의 객체의 인스턴스 메서드 참조 | 타입::인스턴스 메서드 |
| 생성자 참조 | 타입::new |

- 메서드 또는 생성자의 매개변수로 람다의 입력값을 받는다.
- 리턴값 또는 생성한 객체는 람다의 리턴값이다.

## 인터페이스 기본 메서드와 스태틱 메서드

### 기본 메서드(Default Methods)

- 인터페이스에 메서드 선언이 아니라 구현체를 제공하는 방법
- 해당 인터페이스를 구현한 클래스를 깨트리지 않고 새 기능을 추가할 수 있다.
- 기본 메서드는 구현체가 모르게 추가된 기능으로 그만큼 리스크가 있다.
    - 컴파일 에러는 아니지만 구현체에 따라 런타임 에러가 발생할 수 있다.
    - 반드시 문서화 할 것(@implSpec 자바독 태그 사용)
- Object가 제공하는 기능(equals, hashCode 등)는 기본 메서드로 제공할 수 없다.
    - 구현체가 재정의해야 한다.
- 본인이 수정할 수 있는 인터페이스에만 기본 메서드를 제공할 수 있다.
- 인터페이스를 상속받는 인터페이스에서 다시 추상 메서드로 변경할 수 있다.
- 인터페이스 구현체가 재정의 할 수도 있다.
- 동일한 기본 메서드가 존재하는 2개 이상의 인터페이스를 구현하는 경우 컴파일 에러가 발생한다.
    - 재정의해서 충돌을 해결할 수 있다.

### 스태틱 메서드

- 해당 타입 관련 헬퍼 또는 유틸리티 메서드를 제공할 때 인터페이스에 스태틱 메서드를 제공할 수 있다.
- 참고
    - [https://docs.oracle.com/javase/tutorial/java/IandI/nogrow.html](https://docs.oracle.com/javase/tutorial/java/IandI/nogrow.html)
    - [https://docs.oracle.com/javase/tutorial/java/IandI/defaultmethods.html](https://docs.oracle.com/javase/tutorial/java/IandI/defaultmethods.html)

### 자바 8에서 추가한 기본 메서드로 인한 API 변화

- Iterable의 기본 메서드
    - forEach()
    - spliterator()
- Collection의 기본 메서드
    - stream() / parallelStream()
    - removeIf(Predicate)
    - spliterator()
- Comparator의 기본 메서드 및 스태틱 메서드
    - reversed()
    - thenComparing()
    - static reverseOrder() / naturalOrder()
    - static nulssFirst() / nullsLast()
    - static comparing()

## 스트림 API

- Stream
    - sequence of elements supporting sequential and parallel aggregate operations
    - 데이터를 담고 있는 저장소(컬렉션)이 아니다.
    - Functional in nature, 스트림이 처리하는 데이터 소스를 변경하지 않는다.
    - 스트림으로 처리하는 데이터는 오직 한번만 처리한다.
    - 무제한일 수도 있다.(Short Circuit 메서드를 사용해서 제한할 수 있다.)
    - 중개 오퍼레이션은 근본적으로 lazy 하다.(종료 오퍼레이션이 실행되기 전까지 중개 오퍼레이션은 실행되지 않는다.)
    - 손쉽게 병렬 처리할 수 있다.
- 스트림 파이프라인
    - 0 또는 다수의 중개 오퍼레이션(intermediate operation)과 한개의 종료 오퍼레이션(terminal operation)으로 구성한다.
    - 스트림의 데이터 소스는 오직 터미널 오퍼레이션을 실행할 때에만 처리한다.
- 중개 오퍼레이션
    - **Stream을 리턴한다.**
    - Stateless / Stateful 오퍼레이션으로 더 상세하게 구분할 수도 있다.(대부분은 Stateless지만 distinct나 sorted처럼 이전 이전 소스데이터를 참조해야 하는 오퍼레이션은 Stateful 오퍼레이션이다.)
    - filter, map, limit, skip, sorted, ...
- 종료 오퍼레이션
    - **Stream을 리턴하지 않는다.**
    - collect, allMatch, count, forEach, min, max, ...

### 스트림 API 사용 예제

- 걸러내기
    - Filter(Predicate)
- 변경하기
    - Map(Function) / FlatMap(Function)
- 생성하기
    - generate(Supplier) / Iterate(T seed, UnaryOperator)
- 제한하기
    - limit(long) / skip(long)
- 스트림에 있는 데이터가 특정 조건을 만족하는지 확인
    - anyMatch(), allMatch(), nonMatch()
- 개수 세기
    - count()
- 스트림을 데이터 하나로 뭉치기

## Optional

- 자바 프로그래밍에서 NullPointerException을 종종 보게 되는 이유
    - null을 리턴하니까! && null 체크를 깜빡했으니까!
- 메서드에서 작업 중 특별한 상황에서 값을 제대로 리턴할 수 없는 경우 선택할 수 있는 방법
    - 예외를 던진다.(비싸다. 스택트레이스를 찍어두니까.)
    - null을 리턴한다.(비용 문제가 없지만 그 코드를 사용하는 클라이언트 코드가 주의해야한다.)
    - (자바 8부터) Optional을 리턴한다.(클라이언트 코드에게 명시적으로 빈 값일 수도 있다는 걸 알려주고, 빈 값이 경우에 대한 처리를 강제한다.)
- Optional
    - 오직 값 한 개가 들어있을 수도 없을 수도 있는 컨테이너
- 주의할 것
    - 리턴 값으로만 쓰기를 권장한다.(메서드 매개변수 타입, 맵의 키 타입, 인스턴스 필드 타입으로 쓰지 말자.)
    - Optional을 리턴하는 메서드에서 null을 리턴하지 말자.
    - 프리미티브 타입용 Optional은 따로 있다. OptionalInt, OptionalLong, ...
    - collections, maps, streams, arrays, and optionals 은 Optional로 감싸지 말 것.
- Optional 만들기
    - Optional.of()
    - Optional.ofNullable()
    - Optional.empty()
- Optional에 값이 있는지 없는지 확인하기
    - isPresent()
    - isEmpty() (Java 11부터 제공)
- Optional에 있는 값 가져오기
    - get()
    - 만약에 비어있는 Optional에서 무엇인가를 꺼낸다면?
- Optional에 값이 있는 경우에 그 값을 가지고 ~~를 하라.
    - ifPresent(Consumer)
- Optional에 값이 있으면 가져오고 없는 경우에 ~~를 리턴하라.
    - orElse(T)
    - 값이 있는 경우에도 실행된다.
    - 상수나 이미 만들어진 값을 리턴할 때 사용하면 좋을 것 같다.
- Optional에 값이 있으면 가져오고 없는 경우에 ~~를 하라.
    - orElseGet(Supplier)
    - 값이 없는 경우에만 실행된다.
    - 동적으로 처리할때 사용하면 좋을 것 같다.
- Optional에 값이 있으면 가져오고 없는 경우 에러를 던져라.
    - orElseThrow()
- Optional에 들어있는 값 걸러내기
    - Optional filter(Predicate)
- Optional에 들어있는 값 변환하기
    - Optional map(Function)
    - Optional flatMap(Function): Optional 안에 들어있는 인스턴스가 Optional인 경우에 사용하면 편리하다.

## Date/Time API

- 자바 8에 새로운 날짜와 시간 API가 생긴 이유
    - 그전까지 사용하던 [java.util.Date](http://java.util.Date) 클래스는 mutable 하기 때문에 thread safe하지 않다.
    - 클래스 이름이 명확하지 않다. Date인데 시간까지 다룬다.
    - 버그 발생할 여지가 많다.(타입 안정성이 없고, 월이 0부터 시작한다거나..)
    - 날짜 시간 처리가 복잡한 애플리케이션에서는 보통 [Joda Time](https://www.joda.org/joda-time/)을 쓰곤했다.
- 자바 8에서 제공하는 Date-Time API
    - JSR-310 스팩의 구현체를 제공한다.
    - 디자인 철학
        - Clear
        - Fluent
        - Immutable
        - Extensible
- 주요 API
    - 기계용 시간(machine time)과 인류용 시간(human time)으로 나눌 수 있다.
    - 기계용 시간은 EPOCK(1970년 1월 1일 0시 0분 0초)부터 현재까지의 타임스탬프를 표현한다.
    - 인류용 시간은 우리가 흔히 사용하는 연, 월, 일, 시, 분, 초 등을 표현현다.
    - 타임스탬프는 Instant를 사용한다.
    - 특정 날짜(LocalDate), 시간(LocalTime), 일시(LocalDateTime)를 사용할 수 있다.
    - 기간을 표현할 때는 Duration(시간 기반)과 Period(날짜 기반)를 사용할 수 있다.
    - DateTimeFormatter를 사용해서 일시를 특정한 문자열로 포매팅할 수 있다.
- 참고
    - [https://docs.oracle.com/javase/tutorial/datetime/overview/design.html](https://docs.oracle.com/javase/tutorial/datetime/overview/design.html)
- 지금 이 순간을 기계 시간으로 표현하는 방법
    - Instant.now(): 현재 UTC(GMT)를 리턴한다.
    - Universal Time Coordinated == Greenwich Mean Time

    ```java
    Instant now = Instant.now();
    System.out.println(now);
    System.out.println(now.atZone(ZoneId.of("UTC")));
    
    ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
    System.out.println(zonedDateTime);
    ```

- 인류용 일시를 표현하는 방법
    - LocalDateTime.now(): 현재 시스템 Zone에 해당하는(로컬) 일시를 리턴한다.
    - LocalDateTime.of(int, Month, int, int, int, int): 로컬의 특정 일시를 리턴한다.
    - ZonedDateTime.of(int, Month, int, int, int, int, ZoneId): 특정 Zone의 특정 일시를 리턴한다.
- 기간을 표현하는 방법
    - Period / Duration .between()

    ```java
    Period between = Period.between(today, birthDay);
    System.out.println(between.get(ChronoUnit.MONTHS));
    ```

- 파싱 또는 포매팅
    - 미리 정의해둔 포맷 참고 - [https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html)
    - LocalDateTime.parse(String, DateTimeFormatter);
    - Datetime

    ```java
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
    LocalDate localDate = LocalDate.parse("07/15/1982", dateTimeFormatter);
    System.out.println("dateTimeFormatter = " + dateTimeFormatter);
    System.out.println("localDate.format(dateTimeFormatter) = " + localDate.format(dateTimeFormatter));
    ```

- 레거시 API 지원
    - GregorianCalendar와 Date타입의 인스턴스를 Instant나 ZonedDateTime으로 변환 가능
    - java.util.TimeZone에서 java.time.ZoneId로 상호 변환 가능

    ```java
    ZoneId newZoneAPI = TimeZone.getTimeZone("PST").toZoneId();
    TimeZone legacyZoneAPI = TimeZone.getTimeZone(newZoneAPI);
    ```

## CompletableFuture

### 자바 Concurrent 프로그래밍

- Concurrent 소프트웨어
    - 동시에 여러 작업을 할 수 있는 소프트웨어
    - 예) 웹 브라우저로 유튜브를 보면서 키보드로 문서에 타이핑을 할 수 있따.
    - 예) 녹화를 하면서 인텔리제이로 코딩을 하고 워드에 적어둔 문서를 보거나 수정할 수 있다.
- 자바에서 지원하는 Concurrent 프로그래밍
    - 멀티프로세싱(ProcessBuilder)
    - 멀티스레드
- 자바 멀티스레드 프로그래밍
    - Thread / Runnable
- Thread 상속

    ```java
    public static void main(String[] args) {
        HelloThread helloThread = new HelloThread();
        helloThread.start();
        System.out.println("hello: " + Thread.currentThread().getName());
    }
    
    static class HelloThread extends Thread {
        @Override
        public void run() {
            System.out.println("world: " + Thread.currentThread().getName());
        }
    }
    ```

- Runnable 구현 또는 람다

    ```java
    Thread thread = new Thread(() -> System.out.println("world: " + Thread.currentThread().getName()));
    thread.start();
    System.out.println("hello: " + Thread.currentThread().getName());
    ```

- 스레드 주요 기능
    - 현재 스레드 멈춰두기(sleep): 다른 스레드가 처리할 수 있도록 기회를 주지만 그렇다고 락을 놔주진 않는다.(잘못하면 데드락 걸릴 수 있다.)
    - 다른 스레드 깨우기(interupt): 다른 스레드를 깨워서 interruptedException을 발생 시킨다. 그 에러가 발생했을 때 할 일은 코딩하기 나름. 종료 시킬 수도 있고 계속 하던 일을 할 수도 있고.
    - 다른 스레드 기다리기(join): 다른 스레드가 끝날 때까지 기다린다.

### Executors

- 고수준(High-Level) Concurrency 프로그래밍
    - 스레드를 만들고 관리하는 작업을 애플리케이션에서 분리
    - 그런 기능을 Executors에게 위임
- Executors가 하는 일
    - 스레드 만들기: 애플리케이션이 사용할 스레드 풀을 만들어 관리한다.
    - 스레드 관리: 스레드 생명 주기를 관리한다.
    - 작업 처리 및 실행: 스레드로 실행할 작업을 제공할 수 있는 API를 제공한다.
    - [https://docs.oracle.com/javase/tutorial/essential/concurrency/executors.html](https://docs.oracle.com/javase/tutorial/essential/concurrency/executors.html)
- 주요 인터페이스
    - Executor: execute(Runnable)
    - ExecutorService: Executor 상속 받은 인터페이스로, Callable도 실행할 수 있으며, Executor를 종료 시키거나, 여러 Callable을 동시에 실행하는 등의 기능을 제공한다.
    - ScheduledExecutorService: ExecutorService를 상속 받은 인터페이스로 특정 시간 이후에 또는 주기적으로 작업을 실행할 수 있다.
- ExecutorService로 작업 실행하기

    ```java
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.submit(() -> {
        System.out.println("Hello: " + Thread.currentThread().getName());
    });
    ```

- ExecutorService로 멈추기

    ```java
    executorService.shutdown(); // 처리중인 작업 기다렸다가 종료
    executorService.shutdownNow(); // 즉시 종료
    ```

- Fork/Join 프레임워크
    - ExecutorService의 구현체로 손쉽게 멀티 프로세서를 활용할 수 있게 도와준다.
    - [https://docs.oracle.com/javase/tutorial/essential/concurrency/forkjoin.html](https://docs.oracle.com/javase/tutorial/essential/concurrency/forkjoin.html)

### Callable과 Future

- Callable
    - Runnable과 유사하지만 작업의 결과를 받을 수 있다. 리턴이 있다.
- Future
    - 비동기적인 작업의 현재 상태를 조회하거나 결과를 가져올 수 있다.
    - [https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Future.html](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Future.html)
- 결과를 가져오기 get()

    ```java
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Future<String> helloFuture = executorService.submit(() -> {
        Thread.sleep(2000L);
        return "Callable";
    });
    System.out.println("Hello");
    String result = helloFuture.get();
    System.out.println(result);
    executorService.shutdown();
    ```

    - **블록킹 콜이다.**
    - 타임아웃(최대한으로 기다릴 시간)을 설정할 수 있다.
- 작업 상태 확인하기 isDone()
    - 완료 했으면 true 아니면 false를 리턴한다.
- 작업 취소하기 cancel()
    - 취소 했으면 true 못했으면 false를 리턴한다.
    - parameter로 true를 전달하면 현재 진행중인 스레드를 interrupt하고 그러지 않으면 현재 진행중인 작업이 끝날때까지 기다린다.
- 여러 작업 동시에 실행하기 invokeAll()
    - 동시에 실행한 작업 중에 제일 오래 걸리는 작업 만큼 시간이 걸린다.
- 여러 작업 중에 하나라도 먼저 응답이 오면 끝내기 invokeAny()
    - 동시에 실행한 작업 중에 제일 짧게 걸리는 작업 만큼 시간이 걸린다.
    - single thread면 처음꺼
    - **블록킹 콜이다.**

### CompletableFuture

- 자바에서 비동기(Asynchronous) 프로그래밍을 가능하게 하는 인터페이스
    - Futre를 사용해서도 어느정도 가능했지만 하기 힘들 일들이 많았다.
- Future로는 하기 어렵던 작업들
    - Future를 외부에서 완료 시킬 수 없다. 취소하거나, get()에 타임아웃을 설정할 수는 있다.
    - 블로킹 코드(get())를 사용하지 않고서는 작업이 끝났을 때 콜백을 실행할 수 없다.
    - 여러 Future를 조합할 수 없다. 예) Event 정보 가져온 다음 Event에 참석하는 회원 목록 가져오기
    - 예외 처리용 API를 제공하지 않는다.
- CompletableFuture
    - implements Future
    - implements CompletionStage
- 비동기로 작업 실행하기
    - 리턴값이 없는 경우: runAsync()
    - 리턴값이 있는 경우: supplyAsync()
    - 원하는 Executor(스레드풀)를 사용해서 실행할 수도 있다.(기본은 ForkJoinpool.commonPool())
- 콜백 제공하기
    - thenApply(Function): 리턴값을 받는 리턴있는 콜백
    - thenAccept(Consumer): 리턴값을 받는 리턴없는 콜백
    - thenRun(Runnable): 리턴값을 받지 않고 리턴 없는 콜백
    - 콜백 자체를 또 다른 스레드에서 실행할 수 있다.
- 조합하기
    - thenCompose(): 두 작업이 서로 이어서 실행하도록 조합
    - thenCombine(): 두 작업을 독립적으로 실행하고 둘 다 종료 했을 때 콜백 실행
    - allOf(): 여러 작업을 모두 실행하고 모든 작업 결과에 콜백 실행
    - anyOf(): 여러 작업 중에 가장 빨리 끝난 하나의 결과에 콜백 실행
- 예외처리
    - exceptionally(Function)
    - handle(BiFunction)

## 기타

### 애노테이션의 변화

- 애노테이션 관련 두가지 큰 변화
    - 자바 8부터 애너테이션을 타입 선언부에도 사용할 수 있게 됨.
    - 자바 8부터 애너테이션을 중복해서 사용할 수 있게 됨.
- 타입 선언 부
    - 제네릭 타입
    - 변수 타입
    - 매개변수 타입
    - 예외 타입
    - ...
- 타입에 사용할 수 있으려면
    - TYPE_PARAMETER: 타입 변수에만 사용할 수 있다.
    - TYPE_USE: 타입 변수를 포함해서 모든 타입 선언부에 사용할 수 있다.
- 중복 사용할 수 있는 애너테이션을 만들기
    - 중복 사용할 애너테이션 만들기
    - 중복 애너테이션 컨테이너 만들기
        - 컨테이너 애너테이션은 중복 애너테이션과 @Retention 및 @Target이 같거나 더 넓어야 한다.

### 배열 Parallel 정렬

- Arrays.parallelSort()
    - Fork/Join 프레임워크를 사용해서 배열을 병렬로 정렬하는 기능을 제공한다.
- 벙령 정렬 알고리듬
    - 배열을 둘로 계속 쪼갠다.
    - 합치면서 정렬한다.
- sort()와 parallelSort() 비교

    ```java
    int size = 1500;
    int[] numbers = new int[size];
    Random random = new Random();
    IntStream.range(0, size).forEach(i -> numbers[i] = random.nextInt());
    
    long start = System.nanoTime();
    Arrays.sort(numbers);
    System.out.println("serial sorting took = " + (System.nanoTime() - start));
    
    IntStream.range(0, size).forEach(i -> numbers[i] = random.nextInt());
    start = System.nanoTime();
    Arrays.parallelSort(numbers);
    System.out.println("parallel sorting took = " + (System.nanoTime() - start));
    ```

    - serial sorting took = 456000
      parallel sorting took = 330375
    - 알고리듬 효율성은 같다. 시간O(n logN) 공간 O(n)

### Metaspace

JVM의 여러 메모리 영역 중에서 PermGen 메모리 영역이 없어지고 Metaspace 영역이 생겼다.

- PermGen
    - permanent generation, 클래스 메타데이터를 담는 곳
    - Heap 영역에 속함
    - 기본값으로 제한된 크기를 가지고 있음
    - -XX:PermSize=N, PermGen 초기 사이즈 설정
    - XX:MaxPermSize=N, PermGen 최대 사이즈 설정
- Metaspace
    - 클래스 메타데이터를 담는 곳
    - Heap 영역이 아니라, Native 메모리 영역이다.
    - 기본값으로 제한된 크기를 가지고 있지 않다.(필요한 만큼 계속 늘어난다.)
    - 자바 8부터는 PermGen 관련 java 옵션은 무시한다.
    - -XX:MetaspaceSize=N, Metaspace 초기 사이즈 설정
    - -XX:MaxMetaspaceSize=N, Metaspace 최대 사이즈 설정
- jstat
    - JVM 의 Heap Memory 상태를 실시간으로 추적하는 Tool
