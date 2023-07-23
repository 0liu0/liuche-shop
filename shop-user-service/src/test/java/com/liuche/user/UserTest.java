package com.liuche.user;


import com.liuche.user.model.User;
import com.liuche.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


/**
 * @Author 刘彻
 * @Date 2023/7/23 23:05
 * @PackageName: com.liuche.user
 * @ClassName: UserTest
 * @Description: TODO
 */
@SpringBootTest(classes = {UserApplication.class})
public class UserTest {
    @Resource
    private UserService userService;

    @Test
    public void test01() {
        System.out.println("nihao a");
//        User user = new User();
//        user.setName("liuche");
//        user.setMail("706716852@qq.com");
//        user.setPwd("11111111");
//        user.setPoints(1000);
//        user.setSex(1);
//        user.setHeadImg("https://liucheoss.oss-cn-zhangjiakou.aliyuncs.com/2023/07/23/38f20270b7d04878b06082855bd4bf2dikun.jpg");
//        user.setSecret("liuche");
//        user.setSlogan("我是最帅的");
//        boolean save = userService.save(user);
//        System.out.println("save:"+save);
//        boolean flag = userService.removeById(1);
        User byId = userService.getById(1);
        System.out.println("byId:" + byId);
    }
}
