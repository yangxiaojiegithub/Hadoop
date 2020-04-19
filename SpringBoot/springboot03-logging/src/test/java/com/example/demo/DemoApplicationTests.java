package com.example.demo;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    // 日志记录器
    Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    void contextLoads() {
        // 日志级别，由低到高 trace<debug<info<warn<error
        logger.trace("这是trace日志...");
        logger.debug("这是debug日志...");
        logger.info("这是info日志....");
        logger.warn("这是warn日志...");
        logger.error("这是error日志..");
    }

}
