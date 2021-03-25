package com.neoniou.tools;

import com.neoniou.tools.utils.ThreadUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Neo.Zzj
 * @date 2020/7/14
 */
@EnableScheduling
@SpringBootApplication
public class MyToolsApplication {

    public static void main(String[] args) {
        ThreadUtil.createDefaultThreadPool();
        SpringApplication.run(MyToolsApplication.class, args);
    }
}
