package com.neoniou.tools;

import com.neoniou.tools.utils.ThreadUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Neo.Zzj
 * @date 2020/7/14
 */
@SpringBootApplication
public class MyToolsApplication {

    public static void main(String[] args) {
        ThreadUtil.createDefaultThreadPool();
        SpringApplication.run(MyToolsApplication.class, args);
    }
}
