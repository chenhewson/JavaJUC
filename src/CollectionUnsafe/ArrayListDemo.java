package CollectionUnsafe;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ClassName:    ArrayListDemo
 * Package:    CollectionUnsafe
 * Description:
 * Datetime:    2020/6/23   13:58
 * Author:   hewson.chen@foxmail.com
 */
public class ArrayListDemo {
    public static void main(String[] args) {
        //Collection是接口
        //Collections是集合工具类
//        List<String> arrayList = new ArrayList<>();
//        List<String> arrayList = Collections.synchronizedList(new ArrayList<>());
//        List<String> arrayList = new CopyOnWriteArrayList<>();
        Map<Object,Object> map = new ConcurrentHashMap<>();
        map.put("13","23");
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
        //java.util.ConcurrentModificationException
        /*
         * 1     ArrayList.add()在多线程环境下出现的故障现象
         *       java.util.ConcurrentModificationException
         * 2     导致原因
         *
         * 3     解决方案
         * 3.1   Vector
         * 3.2   Collections.synchronizedList(new ArrayList);
         *
         *
         * 4     优化建议
         *
         * */
    }
}
