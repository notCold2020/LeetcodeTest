package com.cxr.designpatterns;


//生产者消费者模式
//可以考虑使用阻塞队列

import java.util.ArrayList;
import java.util.List;

//缓冲区
class Buffer {
    private List<Integer> bufferList = new ArrayList<>();
    private final static int MAX = 10;
    private final static int MIN = 0;

    //此方法会获取缓冲区的工作线程
    public synchronized int getWork() throws InterruptedException {
        while (MIN == bufferList.size()) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("异常捕获");
            }
        }
        Integer i = bufferList.remove(0);
        notifyAll();
        return i;
    }

    //此方法会向缓冲区添加工作
    public synchronized void addWorker(int value) {
        while (MAX == bufferList.size()) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("异常捕获");
            }
        }
        bufferList.add(value);
        notifyAll();
    }
}

class Consumer extends Thread {
    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        int count = 1;//计数器
        //一直在这等待查看缓冲区是否有工作
        while (true) {
            try {
                System.out.println("消费第" + count + "次，获得的值为" + buffer.getWork());
                ++count;
                //争抢时间片的过程 本质上就是线程频繁切换 实际上还是单线程
                //所以才会打印到控制台的时候 生产者消费者一起打印 然后间隔1s 再打印
//                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Producer extends Thread {
    private Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        int a = 0;
        for (int i = 0; i < 100; i++) {
            this.buffer.addWorker(a);
            System.out.println("生产者生产了" + i + ",第" + (a + 1) + "次生产");
            ++a;
        }
    }
}

public class ProducerConsumer {
    public static void main(String[] args) {
        //当前对象全是这个玩意
        Buffer buffer = new Buffer();
        Thread consumer = new Consumer(buffer);
        Thread producer = new Producer(buffer);
        producer.start();
        consumer.start();
    }
}
