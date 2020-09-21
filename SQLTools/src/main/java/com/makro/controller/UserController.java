package com.makro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.makro.bean.User;
import com.makro.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
 
	@RequestMapping("/tuser")
	@ResponseBody
	public String getUser() {
		List<User> users = userService.getAllUser();
		return users.toString();
	}
}
