package com.example.demo2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
public class Demo2Application {

	public static void main(String[] args) {

		SpringApplication.run(Demo2Application.class, args);

	}
	/*@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//文件最大
		factory.setMaxFileSize("102400KB"); //KB,MB
		/// 设置总上传数据总大小
		factory.setMaxRequestSize("1024000KB");
		factory.setLocation("/src/main/resources/static/images/");
		return factory.createMultipartConfig();
	}*/
	/**
	 * 文件上传临时路径
	 */
}
