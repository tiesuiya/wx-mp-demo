package com.minghang.hfq.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * @Author: lihong
 * @Date: 2018/8/9
 * @Description
 */
@Slf4j
@RunWith(SpringRunner.class)
public class RedisTest {

    @Test
    public void testRedis() {
        RedisDao redisDao = new RedisDao("127.0.0.1", 6379, null);
        Seckill seckill = new Seckill();
        seckill.setId("1");
        seckill.setContent("内容" + UUID.randomUUID());
        log.info("put:{}", redisDao.putSeckill(seckill));
        log.info("get:{}", redisDao.getSeckill(1));
    }
}
