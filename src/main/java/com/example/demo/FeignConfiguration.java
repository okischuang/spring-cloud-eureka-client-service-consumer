package com.example.demo;

import java.time.Duration;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class FeignConfiguration {
	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> circuitBreakerFactoryCustomizer() {
		System.out.println("config customize");
		TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(10)).build();
		return resilience4JCircuitBreakerFactory -> resilience4JCircuitBreakerFactory
				.configure(builder -> builder.timeLimiterConfig(timeLimiterConfig), "HelloClient#hello(int)");
	}
	
    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
