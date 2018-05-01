package com.entity;

import java.io.Serializable;


// 注意这里一定要实现 Serializable, 否则无法序列化 User 类
public class User implements Serializable {

    private int id;

    private String name;

    private Integer age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}