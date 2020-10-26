package _16ProdConsumerBlockQueue;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ProdConsumerBlockQueue_Demo {
}


class ShareResource{
    private volatile boolean flag = true;//默认开启，进行阻塞+队列
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    BlockingQueue<String> blockingQueue = null;

    public ShareResource(BlockingQueue<String> blockingQueue){
        this.blockingQueue = blockingQueue;
    }
}