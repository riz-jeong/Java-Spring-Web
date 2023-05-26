package com.spring.ch4.dao;

import com.spring.ch4.domain.*;

public interface UserDao {
    User selectUser(String id) throws Exception;
    int deleteUser(String id) throws Exception;
//    public User selectUser2(String id, String pwd) throws Exception; // SQL Injection 테스트
    int insertUser(User user) throws Exception;
    int updateUser(User user) throws Exception;
    int count() throws Exception;
    void deleteAll() throws Exception;
}