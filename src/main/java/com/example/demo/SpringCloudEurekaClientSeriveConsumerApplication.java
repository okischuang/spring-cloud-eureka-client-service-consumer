package com.example.demo;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@EnableFeignClients
public class SpringCloudEurekaClientSeriveConsumerApplication {
	@Autowired
	HelloClient client;
	
	@RequestMapping("/")
	@TimeLimiter(name = "defaults")
	public CompletableFuture<String> hello(@RequestParam(value = "timeout") int timeout) {
//		return circuitBreakerFactory.create("delay").run(null)
		return CompletableFuture.completedFuture(client.hello(timeout));
	}
	
    //Invoked when call to serviceB timeout
    private CompletableFuture<String> greetingFallBack(String name, Exception ex) {
        System.out.println("Call to serviceB is timed out");
        //return data from cache
        return CompletableFuture.completedFuture("Fallback");
    }

	public static void main(String[] args) {
		TimeLimiterConfig config = TimeLimiterConfig.custom()
				   .cancelRunningFuture(true)
				   .timeoutDuration(Duration.ofMillis(10000))
				   .build();
		// Create a TimeLimiterRegistry with a custom global configuration
		TimeLimiterRegistry timeLimiterRegistry = TimeLimiterRegistry.of(config);
		
		SpringApplication.run(SpringCloudEurekaClientSeriveConsumerApplication.class, args);
	}

}


@FeignClient(name = "service-provider", fallback = HelloFallback.class)
@LoadBalancerClient(name = "service-provider", configuration = LoadBalancerConfiguration.class)
interface HelloClient {
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	String hello(@RequestParam(value = "timeout") int timeout);
}

@Component
class HelloFallback implements HelloClient {

	@Override
	public String hello(int timeout) {
		return "Fallback Error";
	}
	
}



