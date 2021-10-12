package com.cxr.other.strangeDemo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * @Author: CiXingrui
 * @Create: 2021/9/30 2:22 下午
 *
 * 1.@Data注解都包括什么？
 * 相当于@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
 * @RequiredArgsConstructor： 1.为类中需要特殊处理的字段生成构造方法：(1.final修饰的字段 2.被@NonNull注解的字段) 应该不会影响无参构造
 * 2.写在类上
 * @RequiredArgsConstructor(onConstructor = @_(@Autowired))
 * 但是被注入的字段需要用final定义或者@nonnull注解
 */
public class shallowDeepCopyDemo {
    public static void main(String[] args) throws CloneNotSupportedException {
        test02();
    }

    /**
     * ！！重点结论：以后用BeanUtils.copyProperties();注意点
     * ！！看看前者里面是不是有bean，不然就浅拷贝了
     *
     * 深拷贝可以用JSON的方式，这不就是序列化吗
     */
    private static void test01() {
        Teacher1 build = Teacher1.builder().name("teacher").tel("1234").student(Student1.builder().name("sutdent").
                tel("66666").build()).build();

        Teacher1 build2 = new Teacher1();
        BeanUtils.copyProperties(build,build2);
        build2.getStudent().setName("build2Sutendt");

        System.out.println(build);
        System.out.println(build2);
    }

    /**
     * 现象：还是浅拷贝
     * @throws CloneNotSupportedException
     */
    private static void test02() throws CloneNotSupportedException {
        Teacher1 build = Teacher1.builder().name("teacher").tel("1234").student(Student1.builder().name("sutdent").
                tel("66666").build()).build();

        Teacher1 clone = (Teacher1) build.clone();
        clone.getStudent().setName("7777");
    }


    /**
     * 现象：因为我们修改了clone方法,所以深拷贝了
     * @throws CloneNotSupportedException 如果不实现Cloneable会抛出这个异常
     */
    private static void test03() throws CloneNotSupportedException {
        Teacher2 build = Teacher2.builder().name("teacher").tel("1234").student(Student2.builder().name("sutdent").
                tel("66666").build()).build();

        Teacher2 clone = (Teacher2) build.clone();
        clone.getStudent().setName("7777");
    }


    /**
     * BeanUtils.copyProperties()
     * https://blog.csdn.net/sunroyfcb/article/details/102854129
     *
     * 1.需要复制的属性名要相同;
     * 2.对于要复制的属性，源对象必须有get方法，目标对象必须有set方法;
     * 3.目标对象的set方法所需的参数类型和源对象get方法的返回类型保持一致。
     *
     * 所以内部类行不行？
     * 不行,违反了上面的3,set方法可能是A类的inner类 get方法是B类的inner类
     * 所以内部类单独copy
     *
     * 所以List<String> -> List<Integer>可以吗？
     * 可以的 泛型都是编译期的
     */
    private static void test04() {
    }

}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Teacher1 implements Cloneable{
    public String name;//这不有它的构造方法了吗
    public String tel;
    public Student1 student;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Student1 implements Cloneable{
    public String name;
    public String tel;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Teacher2 implements Cloneable{
    public String name;//这不有它的构造方法了吗
    public String tel;
    public Student2 student;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Teacher2 clone = (Teacher2) super.clone();
        clone.student = (Student2) student.clone();
        return clone;
    }
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Student2 implements Cloneable{
    public String name;
    public String tel;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
