package com.zopa.ratecalculationsystem;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class RateCalculationSystemApplication {
    
    private static final Logger logger = LoggerFactory.getLogger(RateCalculationSystemApplication.class);
    
    public static void main(String[] args) {
        
        SpringApplication.run(RateCalculationSystemApplication.class, args);
        logger.info("RateCalculationSystemApplication started.");
    }
}
