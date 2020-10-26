package _14ProdConsumerTradition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 传统版生产者消费者
 * 题目：一个初始值为0的变量，两个线程对其交替操作，一个加1，一个减1，来5轮
 * 1 线程      操作（方法）      资源类
 * 2 判断      干活      通知
 * 3 防止线程虚假唤醒*/
public class ProdConsumer_TraditionDemo {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();
        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                try {
                    shareData.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"AAA").start();

        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                try {
                    shareData.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"BBB").start();
    }
}

//资源类
class ShareData {
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() throws Exception {
        lock.lock();
        try {
            //1 判断，不能用if，只能用while，防止虚假唤醒
            while (number != 0) {
                //等待，不能生产
                condition.await();//wait()，是object类，与线程无关
            }
            //2 生产
            number++;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            //3.唤醒通知
            condition.signalAll();//notify()，是object类，与线程无关
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() throws Exception {
        lock.lock();
        try {
            //1 判断，不能用if，只能用while
            while (number == 0) {
                //等待，不能消费
                condition.await();
            }
            //2 消费
            number--;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            //3.唤醒通知
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
