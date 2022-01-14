package com.hzj.service;

import com.hzj.dao.UserDao;

public class UserServiceImpl implements UserService{

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public int findByUsernamePassword(String username, String password) {
        return userDao.findByUsernamePassword(username, password);
    }

    @Override
    public int updatePassword(String username, String password) {
        return userDao.updatePassword(username, password);
    }

    @Override
    public int queryMoney(String username) {
        return userDao.queryMoney(username);
    }

    @Override
    public int transactionMoney(String username, String anotherUsername, int money) {
        // 查询用户是否存在
        int byUsername = userDao.findByUsername(anotherUsername);
        if (byUsername != 1) {
            return 0;
        }

        // 查询余额
        if (money >= 0) {
            int leftMoney = userDao.queryMoney(username);
            if (money > leftMoney) return 0;
        } else {
            return 0;
        }

        // 执行转账
        int upMon1 = userDao.updateMoney(username, -money);
        int upMon2 = userDao.updateMoney(anotherUsername, money);
        if ((upMon1 | upMon2) == 0) {
            return 0;
        }
        // 转账成功
        return 1;
    }
}
