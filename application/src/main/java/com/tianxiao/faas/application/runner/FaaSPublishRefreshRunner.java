package com.tianxiao.faas.application.runner;

import com.tianxiao.faas.biz.publisher.FaaSPublisher;

import com.tianxiao.faas.common.util.NamedThreadPools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;


@Component
/**
 * 用于刷新本地脚本编译后的缓存
 */
public class FaaSPublishRefreshRunner implements CommandLineRunner {
    private static Logger log = LoggerFactory.getLogger(FaaSPublishRefreshRunner.class);
    @Resource
    private FaaSPublisher faaSPublisher;
    ExecutorService kafkaListenerPoll = NamedThreadPools.newFixedThreadPool(1, "faaSPublishRefresh-thread");

    @Override
    public void run(String... args) throws Exception {
        kafkaListenerPoll.submit((Runnable) () -> {
            while (true) {
                try {
                    faaSPublisher.refreshCache();
                    Thread.sleep(10000L);
                } catch (Exception e) {
                    log.error("refreshCache-error", e);
                }
            }
        });
    }
}
