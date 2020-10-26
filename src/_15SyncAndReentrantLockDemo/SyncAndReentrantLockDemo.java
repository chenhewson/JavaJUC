package _15SyncAndReentrantLockDemo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 使用ReentrantLock解决线程直接按顺序执行，这是利用synchronized和ReentrantLock的第五区别：
 * Reentrantlock支持唤醒特定的线程这一特性来实现的。
 * 题目：
 * AA线程打印5次，BB线程打印10次，CC线程打印15次。
 * 紧接着
 * AA线程打印5次，BB线程打印10次，CC线程打印15次。
 * ...
 * 来十轮
 * */
public class SyncAndReentrantLockDemo {
    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    shareResource.Print5();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    shareResource.Print10();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "B").start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 15; i++) {
                    shareResource.Print15();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "C").start();
    }
}

class ShareResource {
    private int number = 1;//A=1,B=2,C=3
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void Print5() throws InterruptedException {
        //    1 判断,用while防止虚假唤醒
        lock.lock();
        try {
            while (number != 1) {
                c1.await();
            }
            //    2 干活
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            //    3 通知
            number = 2;
            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void Print10() throws InterruptedException {
        lock.lock();
        try {
            //    1 判断,用while防止虚假唤醒
            while (number != 2) {
                c2.await();
            }
            //    2 干活
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            //    3 通知
            number = 3;
            c3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void Print15() throws InterruptedException {
        lock.lock();
        try {
            //    1 判断,用while防止虚假唤醒
            while (number != 3) {
                c3.await();
            }
            //    2 干活
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            //    3 通知
            number = 1;
            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

}
