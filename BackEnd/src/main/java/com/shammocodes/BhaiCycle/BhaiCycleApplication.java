package com.shammocodes.BhaiCycle;

import com.github.messenger4j.Messenger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BhaiCycleApplication {

	@Bean
	public Messenger messenger(@Value("${messenger4j.pageAccessToken}") String pageAccessToken,
							   @Value("${messenger4j.appSecret}") final String appSecret,
							   @Value("${messenger4j.verifyToken}") final String verifyToken) {
		return Messenger.create(pageAccessToken, appSecret, verifyToken);
	}
	public static void main(String[] args) {
		SpringApplication.run(BhaiCycleApplication.class, args);
	}

//	@Value("${MY_VAR}")
	String s = "Hello world";

	@GetMapping("/hello")
	public String hello(){

		return s;
	}

}
