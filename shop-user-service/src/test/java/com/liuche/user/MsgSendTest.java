package com.liuche.user;

import com.liuche.user.util.SendMsgUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @Author 刘彻
 * @Date 2023/7/25 18:24
 * @PackageName: com.liuche.user
 * @ClassName: MsgSendTest
 * @Description: TODO
 */
@SpringBootTest
public class MsgSendTest {
    @Resource
    private SendMsgUtil sendMsgUtil;
    @Test
    public void test01() {
        sendMsgUtil.sendMsg("15271167085@163.com","发送验证码","520520520520");
    }
    @Test
    public void testCode() {
        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000; // 生成 100000 到 999999 之间的随机数
        System.out.println("随机的六位数是：" + randomNumber);
    }
}
