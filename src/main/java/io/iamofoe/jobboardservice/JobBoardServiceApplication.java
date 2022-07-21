package io.iamofoe.jobboardservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JobBoardServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobBoardServiceApplication.class, args);
    }

}
