package com.cxr.designpatterns;

import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface Fruit {
    default void eat() {
        System.out.println("水果1");
    }
}

interface Fruit2 {
    default void eat() {
        System.out.println("水果2");
    }
}

//真实对象只完成核心业务，不需要做准备及善后工作
class FruitImpl implements Fruit, Fruit2 {

    @Override
    public void eat() {
        //如果有这个方法 那么就一定执行咱们自己实现的方法 也就是本方法
    }
}

/**
 * 静态代理实现方式
 * 调用同名方法
 */
class StaticProxy implements Fruit {
    //真实对象
    private Fruit fruit;

    //构造方法直接注入
    public StaticProxy(Fruit fruit) {
        this.fruit = fruit;
    }

    private void prepare() {
        System.out.println("准备工作：洗水果");
    }

    @Override
    public void eat() {
        prepare();
        fruit.eat();
        after();
    }

    private void after() {
        System.out.println("善后工作:扔垃圾");
    }
}
// ####################jdk动态代理####################################################

/**
 * jdk动态代理：当有成千上万个类需要去实现代理的时候，静态代理满足不了
 * 需求：有一个类，传入任何对象，可以调用其方法
 * 调用方式：
 * Fruit fruit = new RealFruit();
 * Fruit proxy = (Fruit) DynamicProxyFactory.getProxy(fruit);
 * proxy.eat();
 * 代理类 实现InvocationHandler接口
 */
public class DynamicProxy implements InvocationHandler {

    private FruitImpl fruitImpl;

    public void setTarget(FruitImpl fruitImpl) {
        this.fruitImpl = fruitImpl;
    }

    /**
     * Object proxy:jdk创建的代理对象 无需赋值
     * Method method: 目标类中的方法
     * Object[] args: 目标类中的方法的参数
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("这里可以调用其他类的方法，表示增强");
        method.invoke(fruitImpl, args);
        System.out.println("这里同理+1");
        return null;
    }
}

//动态代理对象工厂
class DynamicProxyFactory {
    public static Object getProxy(FruitImpl fruitImpl) {
        DynamicProxy handle = new DynamicProxy();
        handle.setTarget(fruitImpl);
        Fruit proxy = (Fruit) Proxy.newProxyInstance(FruitImpl.class.getClassLoader(), FruitImpl.class.getInterfaces(), handle);
        return proxy;
    }
}

//####################cgLib代理#################################
/**
 * 动态代理和静态代理都需要子类实现接口
 * 那么如果有的类没有实现接口呢？
 * cglib代理会虚拟出一个继承了目标类的子类
 * 盲猜是 子类对父类方法进行增强
 */
class MyMethodInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("这里是对目标类进行增强！！！");
        //注意这里的方法调用，不是用反射哦！！！
        Object object = proxy.invokeSuper(obj, args);
        return object;
    }
}

class CgLibProxy {
    public static void main(String[] args) {
        //在指定目录下生成动态代理类，我们可以反编译看一下里面到底是一些什么东西
//        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\java\\java_workapace");

        //创建Enhancer对象，类似于JDK动态代理的Proxy类，下一步就是设置几个参数
        Enhancer enhancer = new Enhancer();
        //设置目标类的字节码文件 为生成的代理类指定父类
        enhancer.setSuperclass(Fruit.class);
        //设置回调函数
        enhancer.setCallback(new MyMethodInterceptor());

        //这里的creat方法就是正式创建代理类
        Fruit fruit = (Fruit) enhancer.create();
        //调用代理类的eat方法
        fruit.eat();
    }
}
