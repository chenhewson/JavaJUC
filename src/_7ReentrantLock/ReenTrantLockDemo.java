package _7ReentrantLock;

/**
 * ClassName:    ReenTrantLockDemo
 * Package:    ReentrantLock
 * Description:
 * Datetime:    2020/6/30   15:33
 * Author:   hewson.chen@foxmail.com
 */
public class ReenTrantLockDemo {
    public static void main(String[] args) {
        Phone phone=new Phone();
        new Thread(()->{
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"1").start();
    }
}

class Phone{
    public synchronized void sendSMS() throws Exception{
        System.out.println(Thread.currentThread().getId()+"\t incoked sendSMS()");
        sendEmail();
    }

    public synchronized void sendEmail() throws Exception{
        System.out.println(Thread.currentThread().getId()+"\t *****incoked sendEmail()");
    }
}

/*
* 1     可重入锁（又名递归锁，套娃锁！！）：可以防止死锁
*           ReenTrantLock/Synchronized是可重入锁
*           解释：外层方法method1已经抢占到锁，在内部调用method2时，method2就会自动获取锁。
*               即外层同步方法可以直接调用内层同步方法。
*
*       执行结果：
*               11	 *****incoked sendSMS()
                11	 *****incoked sendEmail()
*       每次的线程id都是11，说明每次都是同一个线程。同理，reentrantlock也可以。
*
* */
