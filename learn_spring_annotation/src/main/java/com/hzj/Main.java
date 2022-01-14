package com.hzj;

import com.hzj.service.UserService;
import com.hzj.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new Start().start();
    }
}

class Start {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userServiceImpl;

    // 界面登录界面
    public void userInterface() {
        System.out.println("*************************************");
        System.out.println("************欢迎来到网上银行************");
        System.out.println("*************************************");
        System.out.println("************用户名：      ************");
        System.out.println("************密码：        ************");
        System.out.println("*************************************");
    }

    // 选择功能界面
    public void selectFunction() {
        System.out.println("*************************************");
        System.out.println("************欢迎来到网上银行************");
        System.out.println("*************************************");
        System.out.println("************1、修改密码    ************");
        System.out.println("************2、查询余额    ************");
        System.out.println("************3、转账       ************");
        System.out.println("************4、退出       ************");
        System.out.println("*************************************");
    }

    // 退出界面
    public void endInterface() {
        System.out.println("*************************************");
        System.out.println("************欢迎来到网上银行************");
        System.out.println("*************************************");
        System.out.println("************谢谢使用网上银行************");
        System.out.println("*************************************");
    }

    public void start() {
        userInterface();
        Scanner input = new Scanner(System.in);
        String username;
        String password;
        // 输入用户名、密码
        System.out.print("用户名：");
        username = input.next();
        System.out.print("密码：");
        password = input.next();
        System.out.println();

//        ApplicationContext context = new AnnotationConfigApplicationContext(UserServiceImpl.class);
//        UserServiceImpl userServiceImpl = context.getBean("userServiceImpl", UserServiceImpl.class);
        // 查询数据库中是否有该用户名、密码
        int findByUsernamePasswordResult = userServiceImpl.findByUsernamePassword(username, password);
        while(findByUsernamePasswordResult != 1) {
            System.out.println("用户名或密码错误！请重试！");
            userInterface();
            System.out.print("用户名：");
            username = input.next();
            System.out.print("密码：");
            password = input.next();
            System.out.println();
            findByUsernamePasswordResult = userServiceImpl.findByUsernamePassword(username, password);
        }

        while(true) {
            // 选择功能界面
            selectFunction();
            int select = input.nextInt();
            int ret;
            switch (select) {
                // 1、修改密码
                case 1:
                    System.out.println("请输入原密码：");
                    String oldPassword = input.next();
                    // 验证原密码是否正确
                    for (int i = 3; i > 0 ; ) {
                        if (!oldPassword.equals(password)) {
                            System.out.println("原密码错误！还剩"+(--i)+"次！");
                            oldPassword = input.next();
                        } else {
                            break;
                        }
                        if (i == 0)
                            System.exit(1);
                    }
                    System.out.println("请输入新密码：");
                    String newPassword = input.next();
                    ret = userServiceImpl.updatePassword(username, newPassword);
                    if (ret == 1) {
                        System.out.println("修改成功！");
                        password = newPassword;
                    } else {
                        System.out.println("修改失败！");
                    }
                    break;
                // 2、查询余额
                case 2:
                    int money = userServiceImpl.queryMoney(username);
                    System.out.println("余额："+money);
                    break;
                // 3、转账
                case 3:
                    System.out.println("请输入转账账号：");
                    String anotherUsername = input.next();
                    System.out.println("请输入转账金额：");
                    int transMoney = input.nextInt();
                    int i = userServiceImpl.transactionMoney(username, anotherUsername, transMoney);
                    if (i != 1) {
                        System.out.println("转账失败！");
                    } else {
                        System.out.println("转账成功！");
                    }
                    break;
                case 4:
                    endInterface();
                    System.exit(1);
            }
        }
    }
}
