package com.hzj.service;

import com.hzj.dao.UserDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    @Qualifier("userDaoImpl")
    private UserDaoImpl userDaoImpl;

    @Override
    public int findByUsernamePassword(String username, String password) {
        return userDaoImpl.findByUsernamePassword(username, password);
    }

    @Override
    public int updatePassword(String username, String password) {
        return userDaoImpl.updatePassword(username, password);
    }

    @Override
    public int queryMoney(String username) {
        return userDaoImpl.queryMoney(username);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int transactionMoney(String username, String anotherUsername, int money) {
        // 查询用户是否存在
        int byUsername = userDaoImpl.findByUsername(anotherUsername);
        if (byUsername != 1) {
            return 0;
        }

        // 查询余额
        if (money >= 0) {
            int leftMoney = userDaoImpl.queryMoney(username);
            if (money > leftMoney) return 0;
        } else {
            return 0;
        }

        // 执行转账
        int upMon1 = userDaoImpl.updateMoney(username, -money);
        int upMon2 = userDaoImpl.updateMoney(anotherUsername, money);
        if ((upMon1 | upMon2) == 0) {
            return 0;
        }
        // 转账成功
        return 1;
    }
}
