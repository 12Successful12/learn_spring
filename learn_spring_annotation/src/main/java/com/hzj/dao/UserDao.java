package com.hzj.dao;

public interface UserDao {
    int findByUsernamePassword(String username, String password);

    int updatePassword(String username, String password);

    int queryMoney(String username);

    int updateMoney(String username, int money);

    int findByUsername(String username);
}
