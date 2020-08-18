package CAS;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ClassName:    casDemo
 * Package:    CAS
 * Description:
 * Datetime:    2020/5/19   10:45
 * Author:   hewson.chen@foxmail.com
 */
public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);//没有初始值，默认为0
        atomicInteger.compareAndSet(5, 6);//param1=expect,param2=update
        atomicInteger.getAndIncrement();
    }
}
