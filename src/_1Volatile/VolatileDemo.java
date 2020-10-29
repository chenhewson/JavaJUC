package _1Volatile;

//单例模式，DCL多线程安全版
public class VolatileDemo {
    private volatile VolatileDemo volatileDemo = null;
    public VolatileDemo getInstance(){
        if (volatileDemo ==null){
            synchronized (VolatileDemo.class){
                if (volatileDemo == null){
                    volatileDemo = new VolatileDemo();
                }
            }
        }
        return volatileDemo;
    }
}
