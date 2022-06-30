package com.example.demo;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class FeignConfiguration {
	
	@Value("${request.timeout:3000}")	
	private int timeout;
	
	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> circuitBreakerFactoryCustomizer() {
		System.out.printf("timeout config: %d msec", timeout);
		TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(timeout)).build();
		return resilience4JCircuitBreakerFactory -> resilience4JCircuitBreakerFactory
				.configure(builder -> builder.timeLimiterConfig(timeLimiterConfig), "HelloClient#hello(int)");
	}
	
    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
