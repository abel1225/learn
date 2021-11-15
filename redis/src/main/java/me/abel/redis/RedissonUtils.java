package me.abel.redis;


import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;

/**
 * @Description
 * @Author Abel.li
 * @Date 2021/11/11 上午11:18
 */
public class RedissonUtils {

    public static RedissonClient redisson() {
        Config config = new Config();
        config.useClusterServers()
                .addNodeAddress("127.0.0.1:7004", "127.0.0.1:7001");
        return Redisson.create(config);
    }

    /**
     * 获取字符串对象
     * @param redisson
     * @param objectName
     * @return
     */
    public static <T> RBucket<T> getRBucket(RedissonClient redisson, String objectName){
        RBucket<T> bucket=redisson.getBucket(objectName);
        return bucket;
    }

    /**
     * 获取Map对象
     * @param redisson
     * @param objectName
     * @return
     */
    public static <K,V> RMap<K, V> getRMap(RedissonClient redisson, String objectName){
        RMap<K, V> map=redisson.getMap(objectName);
        return map;
    }

    /**
     * 获取有序集合
     * @param redisson
     * @param objectName
     * @return
     */
    public static <V> RSortedSet<V> getRSortedSet(RedissonClient redisson, String objectName){
        RSortedSet<V> sortedSet=redisson.getSortedSet(objectName);
        return sortedSet;
    }

    /**
     * 获取集合
     * @param redisson
     * @param objectName
     * @return
     */
    public static <V> RSet<V> getRSet(RedissonClient redisson, String objectName){
        RSet<V> rSet=redisson.getSet(objectName);
        return rSet;
    }

    /**
     * 获取列表
     * @param redisson
     * @param objectName
     * @return
     */
    public static <V> RList<V> getRList(RedissonClient redisson,String objectName){
        RList<V> rList=redisson.getList(objectName);
        return rList;
    }

    /**
     * 获取队列
     * @param redisson
     * @param objectName
     * @return
     */
    public static <V> RQueue<V> getRQueue(RedissonClient redisson,String objectName){
        RQueue<V> rQueue=redisson.getQueue(objectName);
        return rQueue;
    }

    /**
     * 获取双端队列
     * @param redisson
     * @param objectName
     * @return
     */
    public static <V> RDeque<V> getRDeque(RedissonClient redisson,String objectName){
        RDeque<V> rDeque=redisson.getDeque(objectName);
        return rDeque;
    }

    /**
     * 获取可重入锁
     * @param redisson
     * @param objectName
     * @return
     */
    public static RLock getRLock(RedissonClient redisson,String objectName){
        RLock rLock=redisson.getLock(objectName);
        return rLock;
    }

    /**
     * 获取原子数
     * @param redisson
     * @param objectName
     * @return
     */
    public static RAtomicLong getRAtomicLong(RedissonClient redisson,String objectName){
        RAtomicLong rAtomicLong=redisson.getAtomicLong(objectName);
        return rAtomicLong;
    }

    /**
     * 获取记数锁
     * @param redisson
     * @param objectName
     * @return
     */
    public static RCountDownLatch getRCountDownLatch(RedissonClient redisson,String objectName){
        RCountDownLatch rCountDownLatch=redisson.getCountDownLatch(objectName);
        return rCountDownLatch;
    }

    /**
     * 获取消息的Topic
     * @param redisson
     * @param objectName
     * @return
     */
    public static RTopic getRTopic(RedissonClient redisson,String objectName){
        RTopic rTopic=redisson.getTopic(objectName);
        return rTopic;
    }
}
