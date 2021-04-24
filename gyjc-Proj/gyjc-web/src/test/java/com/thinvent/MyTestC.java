package com.thinvent;

import com.github.pagehelper.PageHelper;
import com.thinvent.service.BasestationService;
import com.thinvent.service.secure.ActiveConnectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;


/**
 * 简单的测试增删改查
 */
@RunWith(SpringRunner.class)
@EnableAsync
@SpringBootTest
public class MyTestC {

    @Autowired
    private ActiveConnectService activeConnectService;

    @Autowired
    private BasestationService basestationService;

    @Test
    public void test() {
        System.out.println(activeConnectService.list());
    }

    @Test
    public void mytest() {
        for (int i=0;i<10000;i++) {
            System.out.println(basestationService.getEarthStations(new Random().nextInt(500), null, null, null, null, null));
        }

    }


}
