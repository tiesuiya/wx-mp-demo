package com.minghang.hfq.weixin.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

/**
 * RedisDao
 *
 * @Author: lihong
 * @Date: 2018/8/21
 * @Description
 */
@Slf4j
@Repository
public class RedisDao {

    /**
     * access_token在redis存储用key
     */
    private static final String ACCESS_TOKEN_KEY = "wechat_mp_access_token_key";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 读取accessToken
     *
     * @return accessToken
     */
    public String getAccessToken() {
        return redisTemplate.opsForValue().get(ACCESS_TOKEN_KEY);
    }

    /**
     * 添加accessToken
     *
     * @param accessToken 微信token
     * @param seconds     有效期（秒）
     */
    public void putAccessToken(String accessToken, int seconds) {
        redisTemplate.opsForValue().set();
        try (Jedis jedis = jedisPool.getResource()) {
            String result = jedis.setex(ACCESS_TOKEN_KEY, seconds, accessToken);
            log.debug(String.format("Redis添加accessToken结果：%s(%s)", result, accessToken));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
