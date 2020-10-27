package _9ReadAndWrite;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ClassName:    ReadAndWriteDemo
 * Package:    ReadAndWrite
 * Description:读写锁
 * Datetime:    2020/6/30   16:39
 * Author:   hewson.chen@foxmail.com
 */
public class ReadAndWriteDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        for (int i = 0; i < 5; i++) {
            final int tempInt = i;
            new Thread(()->{
                myCache.put(tempInt+"",tempInt+"");
            },String.valueOf(i)).start();
        }

        for (int i = 0; i < 5; i++) {
            final int tempInt = i;
            new Thread(()->{
                myCache.get(tempInt+"");
            },String.valueOf(i)).start();
        }
    }
}
class MyCache{
    //模拟缓存的类，要用volatile保证可见性，Redis底层源码有大量的volatile修饰的字段。
    private volatile Map<String,Object> map = new HashMap<>();
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    public void put(String key,Object value){
//        //版本1：不使用写锁，进行写操作的线程中间会被加塞。
//        System.out.println(Thread.currentThread().getName()+"\t 正在写入："+key);
//        try{ TimeUnit.MICROSECONDS.sleep(300); }catch(InterruptedException e){ e.printStackTrace(); }
//        map.put(key,value);
//        System.out.println(Thread.currentThread().getName()+"\t 写入完成");

        //版本2：使用写锁，进行写操作的线程中间不会被加塞。
        lock.writeLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t 正在写入："+key);
            try{ TimeUnit.MICROSECONDS.sleep(300); }catch(InterruptedException e){ e.printStackTrace(); }
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"\t 写入完成");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.writeLock().unlock();
        }


    }
    public void get(String key){
//        //版本1：不使用读锁，进行读操作的线程中途可能会加塞到其他运行中的写线程。
//        System.out.println(Thread.currentThread().getName()+"\t 正在读取："+key);
//        try{ TimeUnit.MICROSECONDS.sleep(300); }catch(InterruptedException e){ e.printStackTrace(); }
//        Object result=map.get(key);
//        System.out.println(Thread.currentThread().getName()+"\t 读取完成，value："+result);

        //版本2：使用读锁，进行读操作的线程中途不会加塞到其他运行中的写线程。
        lock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t 正在读取："+key);
            try{ TimeUnit.MICROSECONDS.sleep(300); }catch(InterruptedException e){ e.printStackTrace(); }
            Object result=map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t 读取完成，value："+result);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.readLock().unlock();
        }
    }
}
