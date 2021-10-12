package com.cxr.other.spring.importSelector;

import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 这一套组合拳我们实现了什么？
 *
 * 1.通过注解的方式开启了自动配置
 *      -自动配置就是我们可以把一些没有打注解的bean交给spring管理，我想给spring就给不想就不给（通过@Enablexxx
 * 2.为什么是自定义的才能被扫描呢？
 *      -这就是我们的本意啊，如果想扫描@service这种，spring不已经帮我们做好了吗。。。
 * 3.大胆猜测：Mybatis的@Mapper配合@Enablexxx是怎么实现的？
 *      -先通过register把@Enablexxx所在的包注册为基本包
 *      -再用selectImports这个东西把基本包下的（我们得先配置规则比如配置了@Mapper)加载到IOC容器
 *            🏆🏆🏆🏆🏆🏆🏆🏆：注意这里说错了，但是自定义的话是可以这么实现的吧，@Mapperscan可以直接通过register配合ClassPathScanningCandidateComponentProvider
 *                                   来直接的把需要的bean注入到ioc容器中
 *      -实现的效果就是@Enablexxx之后@Mapper自动生效（@Mapper本身不是Spring的）
 */
public class HelloImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // 1. 定义扫描包的名称
        String[] basePackages = null;
        // 2. 判读 @Import注解的类上有没有@ComponentScan注解
        if (importingClassMetadata.hasAnnotation(ComponentScan.class.getName())) {
            // 3. 取出ComponentScan注解的属性(basePackage/value)
            Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(ComponentScan.class.getName());
            // 4. 取出basePackages属性的值
            assert attributes != null;
            basePackages = (String[]) attributes.get("basePackages");
        }

        // 5. 判读是否有此注解，是否指定了包扫描的信息, 如果没有此注解，则扫描
        // @Import 类所在包及其子包
        if (basePackages == null || basePackages.length == 0) {
            String basePackage = null;
            try {
                // 6. 取出 @Import 所在包的包名
                basePackage = Class.forName(importingClassMetadata.getClassName()).getPackage().getName();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            // 7. 把包名填充到basePackages中
            basePackages = new String[]{basePackage};
        }
        //false：不使用默认的扫描规则 https://blog.csdn.net/weixin_39724194/article/details/103480547
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        // 切记：是扫描basePackage下的符合我们过滤规则的包
        TypeFilter helloServiceFilter2 = new AssignableTypeFilter(UserService.class);
        scanner.addIncludeFilter(helloServiceFilter2);
        Set<String> classes = new HashSet<>();
        for (String basePackage : basePackages) {
            scanner.findCandidateComponents(basePackage).forEach(beanDefinition -> classes.add(beanDefinition.getBeanClassName()));
        }
        return classes.toArray(new String[classes.size()]);
    }

}
