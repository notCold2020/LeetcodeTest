package com.cxr.other.spring.value;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan("com.cxr.other.spring.value")
//@Configuration
/**
 * 这里的classpath值得就是资源目录resources，下面这里是可以点进去的
 * 说白了你想用@Value不得告诉人家配置文件在哪吗
 */
@PropertySource(value = "classpath:application.yml")
public class Appconfig {
}
