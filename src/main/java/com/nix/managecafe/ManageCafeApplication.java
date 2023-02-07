package com.nix.managecafe;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;


import java.util.TimeZone;

@SpringBootApplication
@EntityScan(basePackageClasses = {
        ManageCafeApplication.class,
        Jsr310JpaConverters.class
})
public class ManageCafeApplication {
    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    protected final Logger logger = LoggerFactory.getLogger(ManageCafeApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ManageCafeApplication.class, args);
    }


}
