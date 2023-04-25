package com.maxtropy.arch.openplatform.sdk.core.config;

import com.maxtropy.arch.openplatform.sdk.core.OpenPlatformSdkClient;
import com.maxtropy.arch.openplatform.sdk.core.controller.CoreController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author luwang
 * @description
 * @date 2022/09/21
 */
@Configuration
@Slf4j
@EnableConfigurationProperties(OpenPlatformProperties.class)
@ServletComponentScan(basePackages = "com.maxtropy.arch.openplatform")
@ComponentScan(basePackages = "com.maxtropy.arch.openplatform")
public class OpenPlatformAutoConfiguration implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Resource
    private OpenPlatformProperties openPlatformProperties;

    @Bean
    @ConditionalOnMissingBean
    public OpenPlatformSdkClient getOpenPlatformSdkClient() {
        OpenPlatformSdkClient sdkClient = OpenPlatformSdkClient.Builder.build(openPlatformProperties.getEndpoint(),
                openPlatformProperties.getAppKey(), openPlatformProperties.getAppSecret());
        return sdkClient;
    }

    @ConditionalOnProperty(
            prefix = "maxtropy.openplatform",
            value = {"integratedCoreUri"},
            havingValue = "true"
    )
    @Bean
    public void initCoreController() throws Exception {
        RequestMappingHandlerMapping requestMappingHandlerMapping=(RequestMappingHandlerMapping)applicationContext.getBean("requestMappingHandlerMapping");
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(CoreController.class);
        defaultListableBeanFactory.registerBeanDefinition("coreController", beanDefinitionBuilder.getBeanDefinition());
        Method method=requestMappingHandlerMapping.getClass().getSuperclass().getSuperclass().getDeclaredMethod("detectHandlerMethods", Object.class);
        method.setAccessible(true);
        method.invoke(requestMappingHandlerMapping,"coreController");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
