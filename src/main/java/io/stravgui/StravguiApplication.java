package io.stravgui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class StravguiApplication {

    static void main(String[] args) {
        log.info("Starting StravguiApplication");
        SpringApplication.run(StravguiApplication.class, args);
    }

}
