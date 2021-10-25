package com.tianxiao.faas.biz.publisher;

import com.tianxiao.faas.biz.domain.FaaSServiceDomain;
import com.tianxiao.faas.biz.domain.aggregate.FaaSAggregate;
import com.tianxiao.faas.biz.factory.FaaSAggregateFactory;
import com.tianxiao.faas.biz.infrastructure.repositories.FaaSServiceRepository;
import com.tianxiao.faas.common.enums.biz.FaaSServiceStatusEnum;
import com.tianxiao.faas.common.util.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class FaaSRedisPublisher implements FaaSPublisher {
    @Resource
    private RedisTemplate redisTemplate;
    @Autowired
    private Environment env;
    @Resource
    private FaaSServiceRepository faaSServiceRepository;
    @Resource
    private FaaSAggregateFactory faaSAggregateFactory;

    private String register_cache_key = "faaS_register_cache_";

    private String publish_cache_key = "faaS_publish_cache_";


    @Override
    public void register(String ip) {
        redisTemplate.opsForSet().add(getEnvKey(register_cache_key), ip);
    }

    @Override
    public void offline(String ip) {
        redisTemplate.opsForSet().remove(getEnvKey(register_cache_key), ip);
    }

    @Override
    public List<String> servers() {
        Set members = redisTemplate.opsForSet().members(getEnvKey(register_cache_key));
        return new ArrayList<>(members);
    }

    @Override
    public void publish(String serviceName) {
        List<String> servers = this.servers();
        if (servers == null) {
            return;
        }
        for (String server : servers) {
            redisTemplate.opsForSet().add(getEnvKey(publish_cache_key) + server, serviceName);
        }
    }

    @Override
    public void refreshCache() {
        Set<String> members = redisTemplate.opsForSet().members(getEnvKey(publish_cache_key) + IpUtils.getLocalHostAddress());
        if (members == null) {
            return;
        }
        for (String server : members) {
            FaaSServiceDomain domain = faaSServiceRepository.get(server, FaaSServiceStatusEnum.ONLINE);
            if (domain == null) {
                continue;
            }
            FaaSAggregate faaSAggregate = faaSAggregateFactory.build(domain);
            faaSAggregate.refresh();
            redisTemplate.opsForSet().remove(getEnvKey(publish_cache_key) + IpUtils.getLocalHostAddress(), server);
        }
    }

    private String getEnvKey(String key) {
        String envString = env.getProperty("server.env");
        return key + envString;
    }
}
