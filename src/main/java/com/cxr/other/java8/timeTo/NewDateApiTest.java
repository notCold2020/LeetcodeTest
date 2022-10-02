package com.cxr.other.java8.timeTo;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class NewDateApiTest {
    public static void main(String[] args) {

        // 旧的方式，可以new
        Date date = new Date();
        System.out.println("old api" + date);

        // 新的方式，只能通过给定的方法获取
        LocalDate newDate = LocalDate.now();             // 日期 2020-12-12
        LocalTime newTime = LocalTime.now();             // 时间 16:30:00:000
        LocalDateTime newDateTime = LocalDateTime.now(); // 日期+时间 2020-12-12T16:30:00.000
        System.out.println("newDate" + newDate);
        System.out.println("newTime" + newTime);
        System.out.println("newDateTime" + newDateTime);

        // 还可以组合哟 日期组合Time
        LocalDateTime combineDateTime = LocalDateTime.of(newDate, newTime);
        System.out.println("combineDateTime" + combineDateTime);

        // 创建指定时间
        LocalDate customDate = LocalDate.of(2020, 11, 5);
        LocalTime customTime = LocalTime.of(16, 30, 0);
        LocalDateTime customDateTime = LocalDateTime.of(2020, 11, 5, 16, 30, 0);
        System.out.println("customDate" + customDate);
        System.out.println("customTime" + customTime);
        System.out.println("customDateTime" + customDateTime);
    }

}

class NewDateApiTest2 {
    public static void main(String[] args) {

        // 获取时间参数的年、月、日（有时需求要用到）
        System.out.println("获取时间参数的年、月、日：");
        LocalDateTime param = LocalDateTime.now();

        //一键转换localDate
        LocalDate localDate = param.toLocalDate();

        System.out.println("year:" + param.getYear());
        System.out.println("month:" + param.getMonth());
        System.out.println("monthValue:" + param.getMonthValue());//月的值 int  12月
        System.out.println("day:" + param.getDayOfMonth());
        System.out.println("hour:" + param.getHour());
        System.out.println("minute:" + param.getMinute());
        System.out.println("second:" + param.getSecond() + "\n");

        // 计算昨天的同一时刻（由于对象不可修改，所以返回的是新对象）
        System.out.println("计算前一天的当前时刻：");
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime yesterday = today.plus(-1, ChronoUnit.DAYS);
        System.out.println("today:" + today);
        System.out.println("yesterday:" + yesterday);
        System.out.println("same object:" + today.equals(yesterday) + "\n");

        // 计算当天的00点和24点（你看，这里就看到组合的威力了）
        System.out.println("计算当天的00点和24点：");
        LocalDateTime todayBegin = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        System.out.println("todayBegin:" + todayBegin);
        System.out.println("todayEnd:" + todayEnd + "\n");

        System.out.println("计算一周、一个月、一年前的当前时刻：");
        LocalDateTime oneWeekAgo = today.minus(1, ChronoUnit.WEEKS);
        LocalDateTime oneMonthAgo = today.minus(1, ChronoUnit.MONTHS);
        LocalDateTime oneYearAgo = today.minus(1, ChronoUnit.YEARS);
        System.out.println("oneWeekAgo" + oneWeekAgo);
        System.out.println("oneMonthAgo" + oneMonthAgo);
        System.out.println("oneYearAgo" + oneYearAgo + "\n");
    }

}

/**
 * 修改LocalDateTime里面的某个 字段
 * 比如修改当前日期为6号
 */
class NewDateApiTest3 {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("now:" + now);
        // 将day修改为6号
        /**
         * with就是修改的意思 比如将当前日期修改为6号
         * 或者将当前的LocalDate修改为这个月的第一天
         */
        LocalDateTime modifiedDateTime = now.with(ChronoField.DAY_OF_MONTH, 6);
        System.out.println("modifiedDateTime:" + modifiedDateTime);

    }
}

/**
 * 比较两个LocalDateTime谁在前谁在后
 */
class NewDateApiTest4 {
    public static void main(String[] args) {

        LocalDateTime today = LocalDateTime.now();
        //加1s
        LocalDateTime after = today.plusSeconds(1);

        boolean result = after.isAfter(today);
        System.out.println("result=" + result);
        System.out.println(today);
        System.out.println(after);
    }
}

/**
 * 获取本国的时区时间
 */
class NewDateApiTest5 {
    public static void main(String[] args) {
        // 当地时间
        LocalDateTime now = LocalDateTime.now();
        System.out.println("localDateTime:" + now);
        // 时区（id的形式），默认的是本国时区
        ZoneId zoneId = ZoneId.systemDefault();
        // 为localDateTime补充时区信息
        ZonedDateTime beijingTime = now.atZone(zoneId);
        System.out.println("beijingTime:" + beijingTime);
    }
}

/**
 * 获取其他时区的时间
 */
class NewDateApiTest6 {
    public static void main(String[] args) {
        // 当地时间
        LocalDateTime now = LocalDateTime.now();
        System.out.println("localDateTime:" + now);
        // 时区（id的形式）
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        // 补充时区信息
        ZonedDateTime shanghai = now.atZone(zoneId);
        System.out.println("上海时间:" + shanghai);

        // 当前上海时间对应东京时间是几点呢？
        ZonedDateTime tokyoHot = shanghai.withZoneSameInstant(ZoneId.of("Asia/Tokyo"));
        System.out.println("东京时间:" + tokyoHot);
    }
}

/**
 * ZonedDateTime转LocalDateTime
 * ZonedDateTime就是个包含时区信息的类
 * LocalDateTime想转换过去就用atZone方法加入我们的zoneId信息
 */
class NewDateApiTest7 {
    public static void main(String[] args) {
        // 当地时间
        LocalDateTime now = LocalDateTime.now();
        System.out.println("localDateTime:" + now);
        // 时区（id的形式）
        ZoneId zoneId = ZoneId.of("Asia/Tokyo");
        // 补充时区信息
        ZonedDateTime tokyoTime = now.atZone(zoneId);
        System.out.println("tokyoTime:" + tokyoTime);

        // ZonedDateTime转LocalDateTime
        LocalDateTime localDateTime = tokyoTime.toLocalDateTime();
    }
}

/**
 * LocalDateTime 和 Date相互转化
 * LocalDateTime->ZonedDateTime->Instant->Date
 * Date->Instant->LocalDateTime
 */
class NewDateApiTest8 {
    public static void main(String[] args) {

        // 先把LocalDateTime变为ZonedDateTime，然后调用toInstant()
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
        // Instant是代表本初子午线的时间，所以比我们的东八区要晚8小时
        Instant instant = zonedDateTime.toInstant();
        System.out.println("zonedDateTime:" + zonedDateTime);
        System.out.println("instant:" + instant);

        // 转为Date
        Date date = Date.from(instant);
        System.out.println("date:" + date);


        Date date1 = new Date();
        // Date也有toInstant()
        Instant instant1 = date1.toInstant();
        System.out.println("date:" + date1);
        System.out.println("instant:" + instant1);

        // 不带时区：LocalDateTime.of()，带时区：LocalDateTime.ofInstant()
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant1, ZoneId.systemDefault());
        System.out.println("localDateTime:" + localDateTime);

    }
}

/**
 * 转换成秒
 */
class NewDateApiTest9 {
    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date.getTime() / 1000);

        LocalDateTime now = LocalDateTime.now();

        long result = now.toEpochSecond(ZoneOffset.ofHours(8));
        System.out.println(result);

        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(result, 0, ZoneOffset.ofHours(8));
        System.out.println(localDateTime);

        //转换成毫秒 位数不一样 当前时间毫秒值是18位的
        LocalDateTime.of(LocalDate.now(), LocalTime.MIN).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();

        System.out.println(LocalDateTime.of(LocalDate.now(), LocalTime.MIN).toEpochSecond(ZoneOffset.ofHours(8)));
    }
}

/**
 * 格式化
 */
class NewDateApiTest10 {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("格式化前:" + now);
        String format = now.format(DateTimeFormatter.ISO_DATE_TIME);
        System.out.println("默认格式:" + format);
        String other = now.format(DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println("其他格式:" + other);

        LocalDateTime now2 = LocalDateTime.now();
        System.out.println("格式化前:" + now2);
        String format2 = now2.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("格式化后:" + format2);
//        String util = util(5);
//        System.out.println(util);
    }

    /**
     * 时间戳转字符串
     */
    public static String test(Integer days) {
        /**
         * Instant.ofEpochMilli(xxx)这里面就是把毫秒时间戳转换为localdatetime
         * 他还有个好兄弟是
         * Instant.ofEpochSecond是吧秒时间戳换换位我们需要的格式的
         *
         */
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String donetime = ftf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli
                (Long.parseLong("这里写已知的秒时间戳") * 1000L), ZoneId.systemDefault()));
        return donetime;
    }
}


