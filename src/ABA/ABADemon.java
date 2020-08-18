package ABA;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ClassName:    ABADemon
 * Package:    ABA
 * Description:演示出现ABA问题
 * Datetime:    2020/5/19   14:59
 * Author:   hewson.chen@foxmail.com
 */
public class ABADemon {
    public static void main(String[] args) {
        //时间
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp);
        Date date = new Date(timestamp.getTime());
        System.out.println(date);

        //字符串
        //时间效率：StringBuilder》StringBuffer》String
        //1.StringBuilder非线程安全，速度最快
        StringBuilder stringBuilder = new StringBuilder();
        StringBuffer stringBuffer = new StringBuffer();
    }
}
