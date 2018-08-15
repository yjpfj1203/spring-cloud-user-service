package com.example.user;

import com.example.user.util.AppContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class UserApplication {
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(UserApplication.class, args);
		AppContextUtil.setApplicationContext(ctx);
	}
}
