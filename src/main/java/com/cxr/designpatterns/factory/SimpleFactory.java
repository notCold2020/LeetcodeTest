package com.cxr.designpatterns.factory;

abstract class INoodles {
    /**
     * 描述每种面条啥样的
     */
    public abstract void desc();
}

class LzNoodles extends INoodles {
    @Override
    public void desc() {
        System.out.println("兰州拉面 上海的好贵 家里才5 6块钱一碗");
    }
}

class PaoNoodles extends INoodles {
    @Override
    public void desc() {
        System.out.println("泡面好吃 可不要贪杯");
    }
}

class SimpleNoodlesFactory {
    public static final int TYPE_LZ = 1;//兰州拉面
    public static final int TYPE_PM = 2;//泡面
    public static final int TYPE_GK = 3;//干扣面

    public static INoodles createNoodles(int type) {
        switch (type) {
            case TYPE_LZ:
                return new LzNoodles();
            case TYPE_PM:
                return new PaoNoodles();
            case TYPE_GK:
            default:
                return (INoodles) new Object();
        }
    }
}

public class SimpleFactory {
    public static void main(String[] args) {
        INoodles noodles = SimpleNoodlesFactory.createNoodles(SimpleNoodlesFactory.TYPE_GK);
        noodles.desc();
    }
}


