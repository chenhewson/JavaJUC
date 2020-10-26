package _12Semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:    SemaphoreDemo
 * Package:    Semaphore
 * Description:信号量机制
 * Datetime:    2020/7/1   13:33
 * Author:   hewson.chen@foxmail.com
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);//模拟三个资源
        for (int i = 1; i <= 10 ; i++) {//模拟10个线程抢3个资源
            new Thread(()->{
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"号车辆抢到车位");
                    try{ TimeUnit.SECONDS.sleep(2); }catch(InterruptedException e){ e.printStackTrace(); }
                    System.out.println(Thread.currentThread().getName()+"号车辆停车2秒，离开车位");
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {

                }
            },String.valueOf(i)).start();
        }
    }
}
