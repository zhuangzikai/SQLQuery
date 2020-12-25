package com.makro.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.makro.bean.User;
import com.makro.mapper.UserMapper;
import com.makro.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{
	@Autowired
	private UserMapper userMapper;
 
	@Override
	public List<User> getAllUser() {
		return userMapper.getAllUser();
	}
	@Override
	public User getUserByUsername(String username) {
		return userMapper.getUserByUsername(username);
	}
	@Override
	public List<User> getUserList(String username) {
		return userMapper.getUserList(username);
	}
	@Override
	public int addUser(User user) {
		return userMapper.addUser(user);
	}
	@Override
	public int updateUser(User user) {
		return userMapper.updateUser(user);
	}
	@Override
	public int deleteUser(int id) throws Exception{
		return userMapper.deleteUser(id);
	}
}
