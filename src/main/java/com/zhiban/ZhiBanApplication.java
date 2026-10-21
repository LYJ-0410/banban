package com.zhiban;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 伴伴启动类
 * 
 * @author BanBan
 */
@SpringBootApplication
@MapperScan("com.zhiban.mapper")
public class ZhiBanApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ZhiBanApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("  伴伴 BanBan 启动成功！");
        System.out.println("  接口文档: http://localhost:8080/swagger-ui.html");
        System.out.println("========================================\n");
    }
}
