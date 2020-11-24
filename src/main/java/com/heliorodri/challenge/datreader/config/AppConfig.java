package com.heliorodri.challenge.datreader.config;

import com.heliorodri.challenge.datreader.connector.CustomerConnector;
import com.heliorodri.challenge.datreader.reader.DatProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.heliorodri.challenge.datreader"})
public class AppConfig {

    @Bean
    public DatProcessor datProcessor(){return new DatProcessor();}

    @Bean
    public CustomerConnector customerConnector(){
        return new CustomerConnector();
    }
}
