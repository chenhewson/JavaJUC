package _13BlockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:   BlockingQueueDemo
 * Package:     BlockingQueue
 * Description: 阻塞队列
 * Datetime:    2020/7/1   13:51
 * Author:      hewson.chen@foxmail.com
 */
/*
* 1 阻塞队列(有生成线程和消费线程)
*   2.1 阻塞队列有无好的一面
*   2.2 不得不阻塞，你如何管理阻塞的内容
*
* */
public class BlockingQueueDemo {
    public static void main(String[] args){
        /*
        * 1.同步队列
        * 容量只有1，存储元素必须被消费完才可以继续存储*/
        BlockingQueue blockingQueue = new SynchronousQueue();
        //生成线程
        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName() + "\t put 1");
                blockingQueue.put("1");

                System.out.println(Thread.currentThread().getName() + "\t put 2");
                blockingQueue.put("2");

                System.out.println(Thread.currentThread().getName() + "\t put 3");
                blockingQueue.put("3");
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        },"AAA").start();

        //消费线程
        new Thread(()->{
            try{TimeUnit.SECONDS.sleep(5);}catch (InterruptedException e){e.printStackTrace();}
            try {
                System.out.println(Thread.currentThread().getName()+"\t take \t"+blockingQueue.take());
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        },"BBB").start();
    }
}

/*运行结果：
* 消费线程开启时，消费到的是1，而不是3.这就说明了，生产线程往同步阻塞队列丢数据1后，
* 同步阻塞队列中的元素没有被消费，所以生产线程无法继续添加2，直到等待5秒之后，1被消费，生产线程才会往同步阻塞线程中继续添加2*/
