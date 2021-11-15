package me.abel.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

@RunWith(JUnit4.class)
public class RedissionTest {

    @Test
    public void testRedisLock (){
        RedissonClient redissonClient = RedissonUtils.redisson();
        RLock lock = RedissonUtils.getRLock(redissonClient, "redission");

    }
}
