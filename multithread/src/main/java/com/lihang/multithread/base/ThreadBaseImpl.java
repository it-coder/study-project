package com.lihang.multithread.base;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * 多线程的基础实现
 *
 * @author lih
 * @create 2019-04-23-15:32.
 */
public class ThreadBaseImpl {

    /**
     * 线程的基本实现1， 实现Runnable接口
     *  1. 需要实例化一个Thread线程，然后传入我们的MyThread实例
     *  2. 当传入一个Runnable target实例时 Thread实例的run将调用target.run()
     */
    public static class MyThread implements Runnable {
        @Override
        public void run() {
            System.out.println("线程1");
        }
    }

    /**
     *  线程的基本实现2：继承Thread
     *  1. Thread本质是一个实现Runnable接口的实例，代表一个线程的实例
     *  2. 启动线程是start()方法，该方法是native方法，它将启动一个新线程并执行run()方法
     */
    public static class MyThread2 extends Thread {
        @Override
        public void run() {
            System.out.println("线程2");
        }
    }

    public static class MyCallback implements Callable {
        private String name;

        public MyCallback(String name) {
            this.name = name;
        }
        @Override
        public Object call() throws Exception {
            return name;
        }
    }

    public static void multi() {
        ExecutorService pool = Executors.newFixedThreadPool(10);

        List<Future> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Future submit = pool.submit(new MyCallback("MyCallback" + i));
            list.add(submit);
        }
        pool.shutdown();
        List<String> collect = list.stream().map(x -> {
            try {
                return x.get().toString();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(toList());
        String join = String.join(",", collect);
        System.out.println(join);
    }

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        new Thread(myThread).start();

        MyThread2 myThread2 = new MyThread2();
        myThread2.start();
        multi();
    }
}
