package com.makro.mapper;

import java.util.List;

import com.makro.bean.User;

public interface UserMapper {
	List<User> getAllUser();
	User getUserByUsername(String username);
	List<User> getUserList(String username);
	int addUser(User user);
	int updateUser(User user);
	int deleteUser(int id) throws Exception;
}
