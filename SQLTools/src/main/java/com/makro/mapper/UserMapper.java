package com.makro.mapper;

import java.util.List;

import com.makro.bean.User;

public interface UserMapper {
	List<User> getAllUser();
	User getUserByUsername(String username);
}
