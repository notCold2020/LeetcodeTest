package com.cxr.designpatterns;

/**
 * 单例设计模式
 * 懒汉式
 * 饿汉式-->线程安全
 *
 * 只要是单例模式 构造器就私有 必须保证只有一个实例存在
 * 私有了就不能在其他类里面new对象了
 */
public class SingleObject {
    //饿汉式，线程安全,static修饰的 就这么一份自然线程安全
    private static SingleObject object = new SingleObject();

    private SingleObject() {
    }

    public static SingleObject getInstance() {
        return object;
    }

    public static void main(String[] args) {
        System.out.println(EnumSingleObject.getInstance());
    }

}

class LazyUnsafeSingleObject {
    //懒汉式 线程不安全
    private static LazyUnsafeSingleObject object;

    private LazyUnsafeSingleObject() {
    }

    public static LazyUnsafeSingleObject getInstance() {
        if (null == object) {
            //两个线程都走到这了呢 就不安全了
            object = new LazyUnsafeSingleObject();
        }
        return object;
    }
}

/**
 * 缺点：每个过来的线程都还要尝试获取锁 太重了
 */
class LazySafeSingleObject {
    //懒汉式 线程安全
    private static LazySafeSingleObject object = null;

    private LazySafeSingleObject() {
    }

    public static LazySafeSingleObject getInstance() {

        synchronized (LazySafeSingleObject.class) {
            if (null == object) {
                object = new LazySafeSingleObject();
            }
        }

        return object;
    }
}


//懒汉式 双检锁 线程安全 双重监察所
class DoubleLockSingleObject {
    /**
     * volatile:禁用指令重排
     * 最里面的new对象的过程不是原子操作
     * 先分配空间 执行构造方法初始化对象 再把这个对象指向这个空间
     * 后两步可能发生指令重排 如果只有一个线程到没啥事
     * 如果这个时候B来了 发现A已经把对象指向这个空间了（但是还没执行构造方法 只是把这个位置占了 那么B就会直接不走==null 第一个判空 而是返回一个lazyMan对象 但是这个对象是一片虚无）
     */
    private static volatile DoubleLockSingleObject object;

    private DoubleLockSingleObject() {
    }

    public static DoubleLockSingleObject getInstance() {
        //第一次其实可以没有 它的作用是提高效率 避免多线程访问时每个线程都尝试获取锁
        if (null == object) {
            synchronized (DoubleLockSingleObject.class) {
                /**
                 * 第二次是延迟加载 lazy的思想
                 * A B线程来，在第一个if判断都通过了
                 * B线程抢到锁 A线程在等待
                 * B线程实例化了公共的对象 return了
                 * 这个时候A进来了 直接第二个if判断 避免重新实例化对象 更高效
                 */
                if (null == object) {
                    /**
                     * 不是原子操作 2,3可能发生指令重排
                     * 1.开辟空间
                     * 2.执行构造方法初始化对象
                     * 3.把对象指向这个空间
                     */
                    object = new DoubleLockSingleObject();
                }
            }
        }
        return object;
    }

}

/**
 * 延迟加载 而且只会加载一次 没有调用前不占内存 保证线程安全
 */
class staticSingleObject {

    private staticSingleObject() {
    }

    private static class Holder {
        static staticSingleObject staticSingleObject = new staticSingleObject();
    }

    private static staticSingleObject getInstance() {
        return Holder.staticSingleObject;
    }

}


/**
 * 枚举类单例
 * 1.反射会破坏单例结构
 * 2.没提供了自动序列化机制
 */
class EnumSingleObject {
    private EnumSingleObject() {
    }

    private enum SingleObject {
        INSTANCE;
        private final EnumSingleObject instance;

        SingleObject() {
            instance = new EnumSingleObject();
        }

        private EnumSingleObject getInstance() {
            return instance;
        }
    }

    public static EnumSingleObject getInstance() {
        return SingleObject.INSTANCE.getInstance();
    }
}


