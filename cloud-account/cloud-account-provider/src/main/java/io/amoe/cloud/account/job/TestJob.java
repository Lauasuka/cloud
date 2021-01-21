package io.amoe.cloud.account.job;

import io.amoe.cloud.account.config.ThreadPoolConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * TestJob
 *
 * @author 519001
 * @date 2021/1/20
 */
@Slf4j
@Component
public class TestJob {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    @Autowired
    @Qualifier(ThreadPoolConfig.CloudThreadPoolBeanName.CLOUD_POOL)
    private ThreadPoolTaskExecutor executor;

    @Scheduled(cron = "0/10 * * * * ?")
    public void job() {
        executor.submit(() -> log.info("当前数：{}", atomicInteger.incrementAndGet()));
    }
}
