package com.entity;

import lombok.Data;

import java.io.Serializable;


// 注意这里一定要实现 Serializable, 否则无法序列化 User 类
@Data
public class User implements Serializable {

    private int id;

    private String name;

    private Integer age;

}