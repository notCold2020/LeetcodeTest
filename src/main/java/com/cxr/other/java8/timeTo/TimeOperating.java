package com.cxr.other.java8.timeTo;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeOperating {

    public void main1(String[] args) {
        // 获取当前年份、月份、日期
        Calendar cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        int month = cale.get(Calendar.MONTH) + 1;
        int day = cale.get(Calendar.DATE);
        int hour = cale.get(Calendar.HOUR_OF_DAY);
        int minute = cale.get(Calendar.MINUTE);
        int second = cale.get(Calendar.SECOND);
        int dow = cale.get(Calendar.DAY_OF_WEEK);
        int dom = cale.get(Calendar.DAY_OF_MONTH);
        int doy = cale.get(Calendar.DAY_OF_YEAR);

        System.out.println("Current Date: " + cale.getTime());
        System.out.println("Year: " + year);
        System.out.println("Month: " + month);
        System.out.println("Day: " + day);
        System.out.println("Hour: " + hour);
        System.out.println("Minute: " + minute);
        System.out.println("Second: " + second);
        System.out.println("Day of Week: " + dow);
        System.out.println("Day of Month: " + dom);
        System.out.println("Day of Year: " + doy);

        // 获取当月第一天和最后一天
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String firstday, lastday;
        // 获取前月的第一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = format.format(cale.getTime());
        // 获取前月的最后一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        lastday = format.format(cale.getTime());
        System.out.println("本月第一天和最后一天分别是 ： " + firstday + " and " + lastday);

        // 获取当前日期字符串
        Date d = new Date();
        System.out.println("当前日期字符串1：" + format.format(d));
        System.out.println("当前日期字符串2：" + year + "/" + month + "/" + day + " "
                + hour + ":" + minute + ":" + second);

        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        SimpleDateFormat myFmt2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//等价于now.toLocaleString()
        SimpleDateFormat myFmt4 = new SimpleDateFormat(
                "一年中的第 D 天 一年中第w个星期 一月中第W个星期 在一天中k时 z时区");
        Date now = new Date();
        System.out.println(myFmt2.format(now));

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String s = "01:23:20";
        try {
            Date date = formatter.parse(s);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:MM:SS");
        String s2 = "02:00:02";
        String s = "00:23:20";
        Long aLong1 = DateToLongsecond(s2);
        Long aLong = DateToLongsecond(s);

        System.out.println(getDuration(aLong + aLong1));
    }

    public static Long DateToLongsecond(String date) {
        //00 0这种字符串转成long的时候 自动变为0L
        String[] split = date.split(":");
        return Long.parseLong(split[0]) * 60 * 60 + Long.parseLong(split[1]) * 60 + Long.parseLong(split[2]);
    }

    /**
     * 根据给定的秒数时长返回"hh:mm:ss"格式的时长。
     *
     * @param seconds 秒数时长。
     * @return 返回格式为"hh:mm:ss"的时长。
     */
    public static String getDuration(Long seconds) {
        if (seconds == null) return null;
        Long min = seconds / 60;
        Long sec = seconds % 60;
        Long hour;
        String minStr = "";
        String secStr;
        String hourStr = "";
        String timeStr;
        secStr = sec.toString();

        if (sec < 10) {
            secStr = "0" + secStr;
        }
        if (min < 60) {
            if (min < 10) {
                minStr = "0";
            }
            minStr += min.toString();
            timeStr = "00:" + minStr + ":" + secStr;

        } else {
            hour = min / 60;
            min = min % 60;
            if (min < 10) {
                minStr = "0";
            }
            minStr += min.toString();
            if (hour < 10) {
                hourStr = "0";
            }
            hourStr += hour.toString();
            timeStr = hourStr + ":" + minStr + ":" + secStr;

        }
        return timeStr;
    }

    /**
     * 两int 相除返回百分数 1 2 50%
     * @param top
     * @param below
     * @return
     */
    private static String deciMal(int top, int below) {
        double result = new BigDecimal((float) top / below).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String s = String.valueOf(result * 100).split("\\.")[0] + "%";
        return s;
    }
}
