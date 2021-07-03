package io.amoe.cloud.tools;

import io.amoe.cloud.exception.BizException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SpringContextUtils
 *
 * @author Amoe
 * @date 2021/3/2
 */
@Component("SpringContextUtils")
public class SpringContextUtils implements ApplicationContextAware {

    private static ApplicationContext ac;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ac = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        if (ac == null) {
            throw new RuntimeException("Application context is null");
        }
        return ac;
    }

    public static <T> ConcurrentHashMap<String, T> getBeansByType(Class<T> clazz) {
        Map<String, T> beans = getApplicationContext().getBeansOfType(clazz);
        return new ConcurrentHashMap<>(beans);
    }

    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }
}
