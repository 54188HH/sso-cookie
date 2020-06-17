package com.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @program: sso-cookie
 * @description:
 * @author: liuzhenqi
 * @create: 2020-06-17 16:04
 */
@SpringBootApplication
public class SsoPblcModule {
  public static void main(String[] args) {
    SpringApplication.run(SsoPblcModule.class,args);
  }
  @Bean
  public RestTemplate getRestTemplate(){
    return new RestTemplate();
  }
}
