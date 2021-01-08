package com.spring.controller;

import com.spring.service.HelloService;
import com.spring.service.HiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class HiController {
	@Autowired
	private HiService hiService;

	
	public void handleRequest() {
		hiService.sayHi();
		hiService.justWantToSayHi();
	}
}
