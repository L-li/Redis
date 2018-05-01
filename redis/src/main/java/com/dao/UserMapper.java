package com.dao;

import com.entity.User;

import java.util.List;

public interface UserMapper {
    User selectById(int id);

    Integer insertUser(User user);

}