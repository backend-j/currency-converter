package me.backendj.currencyconverter.config;

import me.backendj.currencyconverter.common.CurrencyApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Duration;

@Configuration
@EnableScheduling
public class AppConfig {

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        builder.setConnectTimeout(Duration.ofDays(15000));
        builder.setReadTimeout(Duration.ofDays(15000));
        return builder;
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {
            @Autowired
            CurrencyApiService currencyApiService;

            @Override
            public void run(ApplicationArguments args) {
                currencyApiService.getCurrency();
            }
        };
    }
}
