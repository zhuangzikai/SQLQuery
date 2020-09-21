package com.makro;

import org.springframework.boot.SpringApplication;

import com.makro.config.BootApplication;
/**
 * 启动类
 * @author Thierry.Zhuang
 *
 */
public class MainApplication {
	public static void main(String[] args) {
		SpringApplication.run(BootApplication.class, args);
	}
}
