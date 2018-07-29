package com.service;

import com.dao.UserDao;
import com.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    /**
     * @param id
     * @return
     * @Cacheable 当标记在一个方法上时表示该方法是支持缓存的，当标记在一个类上时则表示该类所有的方法都是支持缓存的。
     * <p>
     * 最常用的注解，会把被注解方法的返回值缓存。工作原理是：首先在缓存中查找，
     * 如果没有执行方法并缓存结果，然后返回数据。此注解的缓存名必须指定，
     * 和cacheManager中的caches中的某一个Cache的name值相对应。
     * 可以使用value或cacheNames指定。
     * 如果没有指定key属性，spring会使用默认的主键生成器产生主键。
     * <p>
     */
    @Cacheable(value = "myStringCache", key = "'id_'+#id")
    public User selectByPrimaryKey(Integer id) {
        User user = userDao.selectById(id);
        return user;
    }

    // 先执行方法，然后将返回值放回缓存。可以用作缓存的更新

    /**
     * 对于使用@Cacheable标注的方法，
     * Spring在每次执行前都会检查Cache中是否存在相同key的缓存元素，
     * 如果存在就不再执行该方法，而是直接从缓存中获取结果进行返回，
     * 否则才会执行并将返回结果存入指定的缓存中。@CachePut也可以声明一个方法支持缓存功能。
     * 与@Cacheable不同的是使用@CachePut标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果，
     * 而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。
     *
     * @param user
     * @return
     */
    @CachePut(value = "myStringCache", key = "'id_'+#user.getId()")
    public User insertSelective(User user) {
        Integer res = userDao.insertUser(user);
        if (null != res) {
            return user;
        } else {
            return null;
        }
    }

    /**
     * 是用来标注在需要清除缓存元素的方法或类上的。
     * 当标记在一个类上时表示其中所有的方法的执行都会触发缓存的清除操作。
     * allEntries属性默认为false，当为true时,表示清除全部key.
     *
     * @param id
     * @return
     */
    @CacheEvict(value = "myStringCache", key = "'id_'+#id", allEntries = false)
    public String clearKey(Integer id) {
        String res = "delete key is id_" + id;
        return res;
    }

}