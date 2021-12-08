package com.cxr.other.strangeDemo.queue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

class DelayObject<T> implements Delayed {

    private T name;
    private long time;   //延时时间

    public DelayObject(T name, long delayTime) {
        this.name = name;
        //因为这里加了当前时间毫秒值，所以getDelay里面才减去了当前时间毫秒值
        //所以getDelay返回的结果只是这个元素 现在剩下的时间 比如10s过期 那就是10
        this.time = System.currentTimeMillis() + delayTime;
    }

    @Override
    /**
     * 返回元素到激活时刻的剩余时长
     * 就是我们把对象放进queue里面了，调用下面的这个方法就能返回这个元素啥时候过期
     * 即啥时候后能被take()方法拿到
     */
    public long getDelay(TimeUnit unit) {
        long diff = time - System.currentTimeMillis();
        //diff是时间毫秒值格式的 转化成TimeUnit.MILLISECONDS(毫秒)
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    /**
     * 队列里面元素的排列顺序
     * 下面这个就是按照剩余时间 从小到大排序
     */
    @Override
    public int compareTo(Delayed obj) {
        long l = getDelay(TimeUnit.MILLISECONDS) - obj.getDelay(TimeUnit.MILLISECONDS);
        if (l == 0) return 0;
        if (l < 0) return -1;
        return 1;
    }

    @Override
    public String toString() {
        Date date = new Date(time);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return "\nDelayObject:{"
                + "name=" + name
                + ", time=" + sd.format(date)
                + "}";
    }
}
