package com.dao;

import com.entity.User;


public interface UserDao {
    User selectById(int id);

    Integer insertUser(User user);

}