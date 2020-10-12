package com.makro.controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.makro.bean.User;
import com.makro.service.UserService;
import com.makro.utils.Result;
import com.makro.utils.ResultFactory;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@CrossOrigin
	@RequestMapping(value = "/api/goodsdetails", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Result getGoodsDetails(@RequestBody Map<String, String> dateStr) {
		System.out.println(dateStr.toString());
		return ResultFactory.buildSuccessResult("查询成功。");
	}
	@RequestMapping("/tuser")
	@ResponseBody
	public String getUser() {
		List<User> users = userService.getAllUser();
		return users.toString();
	}
	@CrossOrigin
	@RequestMapping(value = "/api/login",method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Result login(@RequestBody User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String message = String.format("登录失败，详细信息 [%s]。", bindingResult.getFieldError().getDefaultMessage());
			return ResultFactory.buildFailResult(message);
		}
		String pwd = user.getPassword();
		if (pwd != null) {
			if (!Objects.equals(pwd, userService.getUserByUsername(user.getUsername()).getPassword())) {
				String message = String.format("登录失败，详细信息 [用户名或密码不正确]。");
				return ResultFactory.buildFailResult(message);
			}
		}
		return ResultFactory.buildSuccessResult("登录成功。");
	}
	
	
}
