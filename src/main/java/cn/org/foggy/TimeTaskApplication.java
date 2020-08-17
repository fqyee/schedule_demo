package cn.org.foggy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TimeTaskApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TimeTaskApplication.class, args);
        System.out.println("TimeTaskApplication is running context: " + context);
    }

}
