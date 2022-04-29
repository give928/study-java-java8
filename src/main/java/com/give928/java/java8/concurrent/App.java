package com.give928.java.java8.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        App app = new App();
//        app.testExtendsThread();
//        app.testLambdaThread();
//        app.testThreadInterrupt();
//        app.testThreadJoin();

//        app.testExecutors();

//        app.testCallableAndFuture();
        app.testCompletableFuture();
    }

    private static Runnable getRunnable(String message) {
        return () -> System.out.println(message + ": " + Thread.currentThread().getName());
    }

    private void testCompletableFuture() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture1 = new CompletableFuture<>();
        completableFuture1.complete("jooho");
        System.out.println("completableFuture1.get() = " + completableFuture1.get());

        CompletableFuture<String> completableFuture2 = CompletableFuture.completedFuture("jooho");
        System.out.println("completableFuture2.get() = " + completableFuture2.get());

        CompletableFuture<Void> completableFuture3 = CompletableFuture.runAsync(
                () -> System.out.println("CompletableFuture.runAsync: " + Thread.currentThread().getName()));
        System.out.println("completableFuture3.get() = " + completableFuture3.get());

        CompletableFuture<String> completableFuture4 = CompletableFuture.supplyAsync(() -> {
            System.out.println("completableFuture4.supplyAsync: " + Thread.currentThread().getName());
            return "jooho";
        });
        System.out.println("completableFuture4.get() = " + completableFuture4.get());

        CompletableFuture<String> completableFuture5 = CompletableFuture.supplyAsync(() -> {
            System.out.println("completableFuture5.supplyAsync: " + Thread.currentThread().getName());
            return "jooho";
        }).thenApply(s -> {
            System.out.println("completableFuture5.thenApply: " + Thread.currentThread().getName());
            return s.toUpperCase();
        });
        System.out.println("completableFuture5.get() = " + completableFuture5.get());

        CompletableFuture<Void> completableFuture6 = CompletableFuture.supplyAsync(() -> {
            System.out.println("completableFuture6.supplyAsync: " + Thread.currentThread().getName());
            return "jooho";
        }).thenAccept(s -> {
            System.out.println("completableFuture6.thenAccept: " + Thread.currentThread().getName());
            System.out.println("completableFuture6.thenAccept.s.toUpperCase() = " + s.toUpperCase());
        });
        System.out.println("completableFuture6.get() = " + completableFuture6.get());

        CompletableFuture<Void> completableFuture7 = CompletableFuture.supplyAsync(() -> {
            System.out.println("completableFuture7.supplyAsync: " + Thread.currentThread().getName());
            return "jooho";
        }).thenRun(() -> System.out.println("completableFuture7.thenRun: " + Thread.currentThread().getName()));
        System.out.println("completableFuture7.get() = " + completableFuture7.get());

        // 직접 스레드풀을 만들어서 사용하는 경우
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        CompletableFuture<String> completableFuture8 = CompletableFuture.supplyAsync(() -> {
            System.out.println("completableFuture8.supplyAsync: " + Thread.currentThread().getName());
            return "jooho";
        }, executorService).thenApplyAsync(s -> {
            System.out.println("completableFuture8.thenApplyAsync: " + Thread.currentThread().getName());
            return s.toUpperCase();
        }, executorService);
        System.out.println("completableFuture8.get()  = " + completableFuture8.get());

        // 조합
        System.out.println();

        CompletableFuture<String> taskCompletableFuture1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task1 " + Thread.currentThread().getName());
            return "Task1";
        });

        CompletableFuture<String> completableFuture9 = taskCompletableFuture1.thenCompose(
                this::getWorldCompletableFuture);
        System.out.println("completableFuture9.get() = " + completableFuture9.get());

        CompletableFuture<String> taskCompletableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task2 " + Thread.currentThread().getName());
            return "Task2";
        });

        CompletableFuture<String> completableFuture10 = taskCompletableFuture1.thenCombine(
                taskCompletableFuture2,
                (s1, s2) -> s1 + " " + s2);
        System.out.println("completableFuture10.get() = " + completableFuture10.get());

        CompletableFuture<Integer> taskCompletableFuture3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task3 " + Thread.currentThread().getName());
            return 3;
        });

        // 3개의 리턴 타입이 다르다.
        CompletableFuture<Void> completableFuture11 = CompletableFuture.allOf(taskCompletableFuture1,
                                                                              taskCompletableFuture2,
                                                                              taskCompletableFuture3)
                .thenAccept(
                        unused -> {
                            System.out.println("unused = " + unused); // 리턴 값이 null이다.
                        });
        System.out.println("completableFuture11.get() = " + completableFuture11.get());

        CompletableFuture[] completableFutures = {taskCompletableFuture1, taskCompletableFuture2, taskCompletableFuture3};
        CompletableFuture<List<Object>> completableFuture12 = CompletableFuture.allOf(completableFutures).thenApply(
                unused -> {
                    System.out.println("completableFuture12.allOf: " + Thread.currentThread().getName());
                    return Arrays.asList(completableFutures).stream().map(CompletableFuture::join)
                            .collect(Collectors.toList());
                });
        System.out.println("completableFuture12.get() = " + completableFuture12.get());

        CompletableFuture<Void> completableFuture13 = CompletableFuture.anyOf(taskCompletableFuture1, taskCompletableFuture2, taskCompletableFuture3)
                .thenAccept(s -> System.out.println("completableFuture13.anyOf: " + s));
        System.out.println("completableFuture13.get() = " + completableFuture13.get());

        // 예외처리
        System.out.println();

        boolean isError = true;

        CompletableFuture<String> completableFuture14 = CompletableFuture.supplyAsync(() -> {
            if (isError) {
                throw new IllegalStateException();
            }

            System.out.println("completableFuture14.runAsync: " + Thread.currentThread().getName());
            return "Hello";
        }).exceptionally(throwable -> {
            System.out.println("completableFuture14.runAsync.exceptionally.throwable.getMessage() = " + throwable.getMessage());
            return "Error";
        });
        System.out.println("completableFuture14.get() = " + completableFuture14.get());

        CompletableFuture<String> completableFuture15 = CompletableFuture.supplyAsync(() -> {
            if (isError) {
                throw new IllegalStateException();
            }

            System.out.println("completableFuture15.runAsync: " + Thread.currentThread().getName());
            return "Hello";
        }).handle((s, throwable) -> {
            if (throwable != null) {
                System.out.println("completableFuture15.runAsync.handle.throwable = " + throwable);
                return "Error";
            }
            System.out.println("completableFuture15.runAsync.handle.s = " + s);
            return s;
        });
        System.out.println("completableFuture15.get() = " + completableFuture15.get());

        System.out.println("end!");

        executorService.shutdown();
    }

    private CompletableFuture<String> getWorldCompletableFuture(String message) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("App.getWorldCompletableFuture " + Thread.currentThread().getName());
            return message + " World";
        });
    }

    private void testCallableAndFuture() throws InterruptedException, ExecutionException {
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        Callable<String> hello = () -> {
            Thread.sleep(2000L);
            return "Hello";
        };

        Callable<String> java = () -> {
            Thread.sleep(3000L);
            return "Java";
        };

        Callable<String> jooho = () -> {
            Thread.sleep(1000L);
            return "Jooho";
        };

        Future<String> helloFuture1 = executorService.submit(hello);
        System.out.println("started!");
        System.out.println("helloFuture1.isDone() = " + helloFuture1.isDone());
        System.out.println("helloFuture1.get() = " + helloFuture1.get()); // 블록킹
        System.out.println("helloFuture1.isDone() = " + helloFuture1.isDone());
        System.out.println("End!");
        System.out.println();

        Future<String> helloFuture2 = executorService.submit(hello);
        System.out.println("started!");
        System.out.println("helloFuture2.isDone() = " + helloFuture2.isDone());
        boolean cancel = helloFuture2.cancel(true);
        System.out.println("cancel = " + cancel);
        try {
            String s = helloFuture2.get();
            System.out.println("s = " + s);
        } catch (CancellationException e) {
            System.out.println("canceled! can not get");
        }
        System.out.println("helloFuture2.isDone() = " + helloFuture2.isDone());
        System.out.println("End!");
        System.out.println();

        List<Future<String>> futures = executorService.invokeAll(Arrays.asList(hello, java, jooho));
        for (Future<String> future : futures) {
            System.out.println("future.get() = " + future.get());
        }
        System.out.println();

        String s = executorService.invokeAny(Arrays.asList(hello, java, jooho));
        System.out.println("s = " + s);

        executorService.shutdown();
    }

    private void testExecutors() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(getRunnable("execute1"));
        executorService.execute(getRunnable("execute2"));
        executorService.submit(getRunnable("submit1"));
        executorService.submit(getRunnable("submit2"));
        executorService.submit(getRunnable("submit3"));
        executorService.shutdown();

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        scheduledExecutorService.schedule(getRunnable("schedule1"), 3, TimeUnit.SECONDS);
        scheduledExecutorService.schedule(getRunnable("schedule2"), 3, TimeUnit.SECONDS);
        scheduledExecutorService.schedule(getRunnable("schedule3"), 3, TimeUnit.SECONDS);
        scheduledExecutorService.scheduleAtFixedRate(getRunnable("scheduleAtFixedRate"), 1, 2, TimeUnit.SECONDS);

        Thread.sleep(1000 * 10);
        scheduledExecutorService.shutdown();
    }

    private void testExtendsThread() {
        HelloThread helloThread = new HelloThread();
        helloThread.start();
        System.out.println("hello: " + Thread.currentThread().getName());
    }

    private void testLambdaThread() {
        Thread thread = new Thread(() -> System.out.println("world: " + Thread.currentThread().getName()));
        thread.start();
        System.out.println("hello: " + Thread.currentThread().getName());
    }

    private void testThreadInterrupt() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                System.out.println("Thread: " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    System.out.println("interrupted!!");
                    return;
                }
            }
        });
        thread.start();

        System.out.println("Hello: " + Thread.currentThread().getName());
        Thread.sleep(3000L);
        thread.interrupt();
    }

    private void testThreadJoin() {
        Thread thread = new Thread(() -> {
            System.out.println("Thread: " + Thread.currentThread().getName());
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        });
        thread.start();

        System.out.println("Hello: " + Thread.currentThread().getName());
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(thread + " is finished");
    }

    static class HelloThread extends Thread {
        @Override
        public void run() {
            System.out.println("world: " + Thread.currentThread().getName());
        }
    }
}
