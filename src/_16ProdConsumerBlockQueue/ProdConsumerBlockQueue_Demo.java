package _16ProdConsumerBlockQueue;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ProdConsumerBlockQueue_Demo {
    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource(new ArrayBlockingQueue<>(10));
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t 生产线程启动");
            try {
                shareResource.MyProd();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"Product").start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t 消费线程启动");
            try {
                shareResource.MyConsumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"Comsumer").start();

        //生产消费5秒钟
        try{TimeUnit.SECONDS.sleep(5);}catch (InterruptedException e){e.printStackTrace();}

        //时间到，mian线程叫停
        System.out.println("5秒时间到，大老板叫停");
        shareResource.Stop();
    }
}


class ShareResource{
    private volatile boolean FLAG = true;//默认开启，进行阻塞+队列
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    BlockingQueue<String> blockingQueue = null;

    public ShareResource(BlockingQueue<String> blockingQueue){
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    public void MyProd() throws Exception{
        String data = null;
        boolean retValue = false;
        while (FLAG){
            data = atomicInteger.getAndIncrement() + "";//i++的原子引用版本
            retValue = blockingQueue.offer(data,2L, TimeUnit.SECONDS);//往阻塞队列生产数据。如果队满阻塞就等待两秒，超时就放弃往队列对数据
            if (retValue){
                System.out.println(Thread.currentThread().getName()+"线程\t 插入队列蛋糕" + data +"成功");
            }else {
                System.out.println(Thread.currentThread().getName()+"线程\t 插入队列蛋糕" + data +"失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName()+"大老板叫停生产蛋糕，表示flag=false");
    }

    public void MyConsumer() throws Exception{
        String data = null;
        while (FLAG){
            data = blockingQueue.poll(2L,TimeUnit.SECONDS);
            if (null == data || data.equalsIgnoreCase("")) {
                FLAG =  false;
                System.out.println(Thread.currentThread().getName()+"线程\t 超过2秒没有取到蛋糕，退出"+"\n\n");
                return;
            }
            System.out.println(Thread.currentThread().getName()+"线程\t 消费队列蛋糕" + data +"成功");
        }
    }

    public void Stop(){
        FLAG = false;
    }
}