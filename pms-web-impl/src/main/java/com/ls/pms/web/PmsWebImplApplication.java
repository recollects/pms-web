package com.ls.pms.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author yejiadong
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class},scanBasePackages = "com.ls.pms.web")
@Slf4j
public class PmsWebImplApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(PmsWebImplApplication.class, args);
        log.info("spring boot 启动环境和配置信息如下：" + context.getEnvironment());
    }

}
