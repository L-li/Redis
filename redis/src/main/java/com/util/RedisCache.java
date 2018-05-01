package com.util;

/**
 * 　Spring对于缓存只是提供了抽象的接口，并且通过接口来调用功能，没有具体的实现类，所以需要我们自己实现具体的操作。
 * 在上面配置中可知，每个实现类都会注入一个redisTemplate实例，我们就可以通过redisTemplate来操作redis
 */


import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.*;

import static com.util.SerializeUtil.*;

// Spring对于缓存只是提供了抽象的接口，并且通过接口来调用功能，没有具体的实现类，所以需要我们自己实现具体的操作
public class RedisCache implements Cache {

    private RedisTemplate<String, Object> redisTemplate;
    private String name;

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        // TODO Auto-generated method stub  
        return this.name;
    }

    public Object getNativeCache() {
        // TODO Auto-generated method stub  
        return this.redisTemplate;
    }

    public ValueWrapper get(Object key) {
        // TODO Auto-generated method stub
        final String keyf = key.toString();
        Object object = null;
        object = redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = keyf.getBytes();
                byte[] value = connection.get(key);
                if (value == null) {
                    System.out.println(keyf + " 缓存不存在.");
                    return null;
                }
                return unserialize(value);
            }
        });
        return (object != null ? new SimpleValueWrapper(object) : null);
    }

    public void put(Object key, Object value) {
        // TODO Auto-generated method stub
        System.out.println("加入缓存");
        System.out.println("put key:" + key);
        System.out.println("put value:" + value);
        final String keyf = key.toString();
        final Object valuef = value;
        final long liveTime = 0;    // 新数据加入缓存, 默认生存时间, 可自行设定
        redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyb = keyf.getBytes();
                byte[] valueb = serialize(valuef);
                connection.set(keyb, valueb);
                if (liveTime > 0) {
                    connection.expire(keyb, liveTime);
                }
                return 1L;
            }
        });
    }


    public void evict(Object key) {
        // TODO Auto-generated method stub  
        System.out.println("del key:删除缓存,key=" + key.toString());
        final String keyf = key.toString();
        redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                return connection.del(keyf.getBytes());
            }
        });
    }

    public void clear() {
        // TODO Auto-generated method stub  
        System.out.println("clear key:清理缓存");
        redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "缓存清理完毕.";
            }
        });
    }

    public <T> T get(Object key, Class<T> type) {
        // TODO Auto-generated method stub
        return null;
    }

    public ValueWrapper putIfAbsent(Object key, Object value) {
        // TODO Auto-generated method stub
        return null;
    }

}

