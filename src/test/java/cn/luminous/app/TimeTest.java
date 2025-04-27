package cn.luminous.app;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * @program: jenkins-test
 * @description:
 * @author: wang
 * @create: 2025-04-27 11:16
 **/
public class TimeTest {

    @Test
    public void test2() {
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < 7; i++) {
//            ThreadUtil.sleep(1, TimeUnit.MINUTES);
            Calendar time = DateUtil.calendar(DateTime.from(now.atZone(ZoneId.systemDefault()).toInstant()));
            time.add(Calendar.DAY_OF_YEAR, i);
            System.out.println("time = " + time);
            DateTime dateTime = DateUtil.date(time.getTime());
            System.out.println("---------------------------------");
            System.out.println("dateTime = " + dateTime);
        }
    }


}
