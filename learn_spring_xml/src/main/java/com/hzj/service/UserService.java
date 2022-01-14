package com.hzj.service;

public interface UserService {
    // 查询用户名密码进行登录 1-成功 0-失败
    int findByUsernamePassword(String username, String password);

    // 修改密码 1-成功 0-失败
    int updatePassword(String username, String password);

    // 查询余额
    int queryMoney(String username);

    // 转账
    int transactionMoney(String username, String anotherUsername, int money);
}
