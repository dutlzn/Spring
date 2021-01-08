package com.spring;

import com.spring.controller.HelloController;
import com.spring.introduction.LittleUniverse;
import com.spring.service.WelcomeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.FileSystemXmlApplicationContext;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan("com.spring")
public class Entrance {
	public static void main(String[] args) throws InterruptedException {

		System.out.println("Hello, World!");

		String xmlPath = "D:\\code\\Spring\\spring-framework-5.2.0.RELEASE\\springdemo\\src\\main\\resources\\spring\\spring-config.xml";

//		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(xmlPath);
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Entrance.class);

		String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
		for (String beanDefinitionName : beanDefinitionNames) {
			System.out.println(beanDefinitionName);
		}
//		WelcomeService welcomeService = (WelcomeService) applicationContext.getBean("welcomeService");
//		welcomeService.sayHello("Li Ming");

		// aop
		System.out.println("==========================AOP=========================");
		HelloController helloController = (HelloController)applicationContext.getBean("helloController");
//		helloController.handleRequest();
		((LittleUniverse)helloController).burningUp();


	}
}
