package _11CyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * ClassName:    CyclicBarrierDemo
 * Package:    CyclicBarrier
 * Description:循环阻塞屏障，当达到阻塞数量时，打破屏障，所有线程终止阻塞。
 * Datetime:    2020/7/1   10:52
 * Author:   hewson.chen@foxmail.com
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,()->{System.out.println("龙珠集齐，召唤神龙");});
        for (int i = 1; i <= 7 ; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"号龙珠已收集");
                try {
                    //让所有线程在await()方法执行时阻塞
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }
}

/*
*
* 解释：
*       1   CyclicBarrier做的事情是，让一组线程到达一个屏障（也可以叫同步点-Barrier）时被阻塞，
*           直到最后一个线程到达屏障时，屏障才会开门，所有被屏障拦截的线程才会继续干活。
*           而之所以叫Cyclic，是因为所有等待线程被释放后，CyclicBarrier可以被重用。
*       2   而CountDownLatch是个多线程计数器，只有当默认传入的数字自减到0时，才会唤醒被阻塞的线程。
* */
