package com.codezzz.mallcore.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * 在混淆时，由于所有类均变成了 A、B、C。。。，在 Spring 中会由于重名导致初始化失败
 * 该类用于处理此问题，使初始化的 Bean 名称为全限定名
 *
 * @author felixu
 * @since 2021.05.10
 */
public class CustomBeanNameGenerator extends AnnotationBeanNameGenerator {

    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        String beanClassName = definition.getBeanClassName();
        Assert.state(beanClassName != null, "No bean class name set");
        if(StringUtils.startsWithIgnoreCase(beanClassName, "com.isyscore.os")) {
            return beanClassName;
        }
        return super.generateBeanName(definition, registry);
    }
}
