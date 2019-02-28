package com.jz.sm.framework.model.dao.impl;

import java.util.List;

import com.jz.sm.framework.model.entity.User;

public interface IUser {
	public boolean save(User user);
	
	public boolean delete(String userId);
	
	public boolean update(User User);
	
	public User findById(String userId);
	public List<User> findByLike(User user);
}
