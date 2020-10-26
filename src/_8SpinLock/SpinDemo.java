package _8SpinLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * ClassName:    SpinDemo
 * Package:    SpinLock
 * Description:自旋锁
 * Datetime:    2020/6/30   15:54
 * Author:   hewson.chen@foxmail.com
 */
public class SpinDemo {
    //原子引用线程
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public static void main(String[] args) {
        SpinDemo spinDemo = new SpinDemo();
        new Thread(()->{
            spinDemo.mylock();
            try{ TimeUnit.SECONDS.sleep(5); }catch(InterruptedException e){ e.printStackTrace(); }
            spinDemo.myUnlock();
        },"AA").start();
        
        try{ TimeUnit.SECONDS.sleep(1); }catch(InterruptedException e){ e.printStackTrace(); }
        
        new Thread(()->{
            spinDemo.mylock();
            try{ TimeUnit.SECONDS.sleep(1); }catch(InterruptedException e){ e.printStackTrace(); }
            spinDemo.myUnlock();
        },"BB").start();
    }
    public void mylock(){
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"\t come in (*^_^*)");
        while (!atomicReference.compareAndSet(null,thread)){
        }
    }

    public void myUnlock(){
        Thread thread=Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(Thread.currentThread().getName()+"\t invoked myUnlock");
    }
}
/*
* 1     自旋锁：尝试获取锁的线程不会立即阻塞，而是采用循环的方式尝试获取锁
*       优点：减少线程上下文切换的消耗。
*       缺点：消耗CPU
*
* 执行结果：
*       AA	 come in (*^_^*)
        BB	 come in (*^_^*)
        AA	 invoked myUnlock
        BB	 invoked myUnlock
*
*
* */