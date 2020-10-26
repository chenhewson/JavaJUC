package _10CountDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * ClassName:    CountDownLatchDemo
 * Package:    CountDownLatch
 * Description:
 * Datetime:    2020/7/1   9:45
 * Author:   hewson.chen@foxmail.com
 */
public class CountDownLatchDemo {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 0; i <5 ; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"号同学上完晚自习，离开教室");
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"\t**************班长关门");
    }
}
/*
* 执行结果：
*       0号同学上完晚自习，离开教室
        1号同学上完晚自习，离开教室
        2号同学上完晚自习，离开教室
        3号同学上完晚自习，离开教室
        4号同学上完晚自习，离开教室
        main	**************班长关门
*
* 解释：
*       CountDownLatch是个多线程计数器，只有当默认传入的数字自减到0时，才会唤醒被阻塞的线程。
*       CyclicBarrier与之相反，是自增到某个值后，才会唤醒被阻塞的线程。
*
* */
