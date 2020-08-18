package FairAndUnFair;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ClassName:    ReentrantLock
 * Package:    FairAndUnFair
 * Description:
 * Datetime:    2020/6/30   15:00
 * Author:   hewson.chen@foxmail.com
 */
public class ReentrantLockDemo {
    public static void main(String[] args) {
        Lock uflock=new ReentrantLock();//默认非公平锁
        Lock flock=new ReentrantLock(true);//公平锁


    }
}

class Phone{
    public synchronized void sendSMS() throws Exception{
        System.out.println(Thread.currentThread().getId()+"\t *****incoked sendSMS()");
        sendEmail();
    }

    public synchronized void sendEmail() throws Exception{
        System.out.println(Thread.currentThread().getId()+"\t *****incoked sendEmail()");
    }
}

/*
* 1     非公平锁：多个线程按照申请锁的顺序获取锁，又肯后申请的比先申请的线程优先获取锁。效率更高。会出现优先级反转，吞吐量比较大
*           synchronized也是非公平锁
* 2     公平锁：多个线程按照申请锁的顺序来获得锁
*
* */
