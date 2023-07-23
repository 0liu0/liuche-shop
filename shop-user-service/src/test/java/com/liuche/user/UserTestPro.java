package com.liuche.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author 刘彻
 * @Date 2023/7/23 23:51
 * @PackageName: com.liuche.user
 * @ClassName: UserTestPro
 * @Description: TODO
 */
@SpringBootTest(classes = {UserApplication.class})
public class UserTestPro {
    @Test
    public void test01() {
        System.out.println("nihao a");
    }
}
