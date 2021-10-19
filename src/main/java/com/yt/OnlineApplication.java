package com.yt;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yt.mapper")
public class OnlineApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineApplication.class, args);
    }

}
