package com.makro.controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.makro.bean.User;
import com.makro.config.DataSourceConstants;
import com.makro.config.DynamicDataSourceContextHolder;
import com.makro.config.LogFilter;
import com.makro.service.UserService;
import com.makro.utils.Result;
import com.makro.utils.ResultFactory;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping("/tuser")
	@ResponseBody
	public String getUser() {
		DynamicDataSourceContextHolder.setContextKey(DataSourceConstants.DS_KEY_MASTER);
		List<User> users = userService.getAllUser();
		return users.toString();
	}
	@CrossOrigin
	@RequestMapping(value = "/api/login",method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	@LogFilter("登录")
	public Result login(@RequestBody User user) {
		DynamicDataSourceContextHolder.setContextKey(DataSourceConstants.DS_KEY_MASTER);
		String pwd = user.getPassword();
		if (pwd != null) {
			if (!Objects.equals(pwd, userService.getUserByUsername(user.getUsername()).getPassword())) {
				String message = String.format("登录失败，详细信息 [用户名或密码不正确]。");
				return ResultFactory.buildFailResult(message);
			}
		}
		return ResultFactory.buildSuccessResult("登录成功。");
	}
	@CrossOrigin
	@RequestMapping(value = "/api/userList", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	@LogFilter("用户列表")
	public Result getUserList(@RequestBody Map<String, String> usernameMap) {
		String username = usernameMap.get("username");
		if (username != null) {
			username = username.trim();
		}
		DynamicDataSourceContextHolder.setContextKey(DataSourceConstants.DS_KEY_MASTER);
		List<User> list = userService.getUserList(username);
		return ResultFactory.buildSuccessResult(list);
	}
	@CrossOrigin
	@RequestMapping(value = "/api/addUser", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	@LogFilter("添加用户")
	public Result addUser(@RequestBody User user) {
		DynamicDataSourceContextHolder.setContextKey(DataSourceConstants.DS_KEY_MASTER);
		int flag = 0;
		String msg = "";
		try {
			flag = userService.addUser(user);
		} catch (Exception e) {
			msg = e.getCause().getMessage();
		}
		if (flag == 1) {
			return ResultFactory.buildSuccessResult("添加成功");
		} else {
			return ResultFactory.buildResult(500, "添加失败", msg);
		}
	}
	@CrossOrigin
	@RequestMapping(value = "/api/updateUser", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	@LogFilter("更新用户")
	public Result updateUser(@RequestBody User user) {
		DynamicDataSourceContextHolder.setContextKey(DataSourceConstants.DS_KEY_MASTER);
		int flag = 0;
		String msg = "";
		try {
			flag = userService.updateUser(user);
		} catch (Exception e) {
			msg = e.getCause().getMessage();
		}
		if (flag == 1) {
			return ResultFactory.buildSuccessResult("更新成功");
		} else {
			return ResultFactory.buildResult(500, "更新失败", msg);
		}
	}
	@CrossOrigin
	@RequestMapping(value = "/api/deleteUser", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	@LogFilter("删除用户")
	public Result updateUser(@RequestBody Map<String, Integer> idMap) {
		int id = idMap.get("id").intValue();
		DynamicDataSourceContextHolder.setContextKey(DataSourceConstants.DS_KEY_MASTER);
		int flag = 0;
		String msg = "";
		try {
			flag = userService.deleteUser(id);
		} catch (Exception e) {
			msg = e.getCause().getMessage();
		}
		if (flag == 1) {
			return ResultFactory.buildSuccessResult("删除成功");
		} else {
			return ResultFactory.buildResult(500, "删除失败", msg);
		}
	}
}
