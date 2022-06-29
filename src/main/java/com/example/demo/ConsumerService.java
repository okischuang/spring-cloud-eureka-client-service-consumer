package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

//@Controller
//public class ConsumerService {
//
//    @Autowired
//    private RestTemplate restTemplate;
//    
//	@Autowired
//    private ServiceProviderClient svcProviderClient;
//
//    public String callProducer() {
//        ResponseEntity<String> result =
//                this.restTemplate.getForEntity(
//                        "http://service-provider/hello",
//                        String.class,
//                        "");
//        if (result.getStatusCode() == HttpStatus.OK) {
//            System.out.printf(result.getBody() + " called in callProducer");
//            return result.getBody();
//        } else {
//            System.out.printf(" is it empty");
//            return " empty ";
//        }
//    }
//    
//    @RequestMapping("/greeting")
//    public String greeting() {
//        return svcProviderClient.getService();
//    }
//    
//    
//}


//@FeignClient("service-provider")
//public interface ConsumerService {
//	
//	@RequestMapping(method = RequestMethod.GET, value = "./hello")
//	String getService();
//}
