package _18ThreadPoolDemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo_Part1 {
    public static void main(String[] args) {
        //查看几个线程
//        System.out.println(Runtime.getRuntime().availableProcessors());
        ExecutorService threadpool = Executors.newFixedThreadPool(5);//一池5个线程
        ExecutorService threadpool2 = Executors.newSingleThreadExecutor();
        ExecutorService threadpool3 = Executors.newCachedThreadPool();

//        模拟10个用户办理业务，每个用户就算一个来自外部的请求线程
        try {
            for (int i = 0; i < 10; i++) {
                threadpool.execute(() -> {
                    System.out.println(Thread.currentThread().getName()+"号线程办理用户的请求");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadpool.shutdown();
        }
    }
}