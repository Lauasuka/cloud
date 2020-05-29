package io.amoe.cloud.account.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.amoe.cloud.annotation.WebLog;
import io.amoe.cloud.tools.NetworkTools;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @author Amoe
 * @date 2019/11/2 16:31
 */
@Aspect
@Slf4j
@Component
@Order(1000)
public class WebLogAspect {

    @Resource
    private ObjectMapper mapper;

    /**
     * 申明一个切点
     */
    @Pointcut("@within(io.amoe.cloud.annotation.WebLog)")
    public void webLog() {
    }

    /**
     * 前置通知
     * 在方法之前织入代码
     * @param joinPoint
     * @throws Exception
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = attributes != null ? attributes.getRequest() : null;

        // 获取 @WebLog 注解的描述信息
        String methodDescription = getAspectLogDescription(joinPoint);

        Optional.ofNullable(httpServletRequest).ifPresent(request -> {
            // 打印请求相关参数
            log.info("========================================== Start ==========================================");
            // 打印请求 url
            log.info("访问地址    : {}", request.getRequestURL().toString());
            // 打印描述信息
            if (StringUtils.isBlank(methodDescription)) {
                log.info("接口描述    : {} > {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            } else {
                log.info("接口描述    : {}", methodDescription);
            }
            // 打印 Http method
            log.info("请求方式    : {}", request.getMethod());
            // 打印调用 controller 的全路径以及执行方法
            log.info("接口路径    : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            // 打印请求入参
            try {
                log.info("接口入参    : {}", mapper.writeValueAsString(joinPoint.getArgs()));
            } catch (JsonProcessingException e) {
                // ignore
            }
            // 打印请求的 IP
            log.info("来源IP地址  : {}", NetworkTools.getRealIp(request));
        });
    }

    /**
     * 环绕通知
     * 在方法被调用之前和之后织入代码
     * @param joinPoint
     * @return
     */
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        log.info("响应参数    : {}", mapper.writeValueAsString(result));
        log.info("调用耗时    : {}", System.currentTimeMillis() - startTime);
        return result;
    }

    /**
     * 成功调用通知
     * 在方法被成功调用之后织入代码
     * @param val
     */
    @AfterReturning(returning = "val", pointcut = "webLog()")
    public void doAfterReturning(Object val) {
        // ignore
    }

    /**
     * 调用异常通知
     * 在方法被调用出现异常之后织入代码
     * @param ex
     */
    @AfterThrowing(pointcut = "webLog()", throwing = "ex")
    public void doAfterThrowing(Throwable ex) {
        // ignore
    }

    /**
     * 后置通知
     * 不管方法是否成功执行，在方法之后运行插入建议
     */
    @After("webLog()")
    public void doAfter() {
        log.info("==========================================  End  ==========================================");
    }

    /**
     * 获取 @WebLog {@link WebLog} 注解的描述信息
     */
    private String getAspectLogDescription(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class<?> targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        StringBuilder description = new StringBuilder();

        WebLog annotation = targetClass.getAnnotation(WebLog.class);
        if (annotation != null) {
            description.append(annotation.value());
        }

        for (Method method : methods) {
            if (method == null) {
                continue;
            }
            if (methodName.equals(method.getName())) {
                Class<?>[] classes = method.getParameterTypes();
                if (classes.length == arguments.length) {
                    WebLog log = method.getAnnotation(WebLog.class);
                    if (description.length() > 0 && log != null) {
                        description.append(" -- ");
                    }
                    Optional.ofNullable(log).ifPresent(obj -> description.append(obj.value()));
                    break;
                }
            }
        }
        return description.toString();
    }
}
