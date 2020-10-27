package _17CallableDemo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/*
 * Runnable和Callable的区别：
 * 1.    有无返回值。
 * 2.    实现的方法名不一样。
 * 3.    需要借助FutureTask实现类作为构造参数。
 * */
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        Callable新建一个线程的步骤：
//        1. 实现Callable接口。
        MyCallable myCallable = new MyCallable();
//        2. 将Callable的实现类作为FutureTask的参数，新建一个FutureTask对象。
        FutureTask futureTask = new FutureTask(myCallable);
        FutureTask futureTask2 = new FutureTask(myCallable);
//        3. 将FutureTask对象作为Thread()构造方法的参数,构造一个Thread。
        Thread thread1 = new Thread(futureTask, "AAA");
        Thread thread2 = new Thread(futureTask2, "BBB");
//        4.开启线程
        thread1.start();
        thread2.start();

        System.out.println(Thread.currentThread().getName()+"****************");

        int a = 100;

        int res = (int) futureTask.get();

        System.out.println(res+a);//返回值尽量在Main最后面来获取，否则会阻塞
    }
}

//有返回值
class MyCallable implements Callable<Integer>{
    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName()+"\tCallable线程启动");
        try{TimeUnit.SECONDS.sleep(2);}catch (InterruptedException e){e.printStackTrace();}
        return 1024;
    }
}