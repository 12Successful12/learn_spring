package com.hzj.dao;

import com.hzj.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int findByUsernamePassword(String username, String password) {
        String sql = "select * from user where username=? and password=?";
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
        } catch (Exception e) {
            user = null;
        }
        return (user != null ? 1 : 0);
    }

    @Override
    public int updatePassword(String username, String password) {
        String sql = "update user set password=? where username=?";
        int update = jdbcTemplate.update(sql, password, username);
        return update;
    }

    @Override
    public int queryMoney(String username) {
        String sql = "select money from user where username=?";
        Integer money = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return money;
    }

    @Override
    public int updateMoney(String username, int money) {
        String sql = "update user set money=money+? where username=?";
        int update = jdbcTemplate.update(sql, money, username);
        return update;
    }

    @Override
    public int findByUsername(String username) {
        String sql = "select * from user where username=?";
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (Exception e) {
            user = null;
        }
        return (user != null ? 1 : 0);
    }

}
