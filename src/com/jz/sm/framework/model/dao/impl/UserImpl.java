package com.jz.sm.framework.model.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.jz.sm.framework.model.entity.User;
import com.jz.sm.framework.model.util.DBUtil;

public class UserImpl implements IUser{

	@Override
	public boolean save(User user) {
		return false;
	}

	@Override
	public boolean delete(String userId) {
		return false;
	}

	@Override
	public boolean update(User user) {
		return false;
	}
	@Override
	public User findById(String userId) {
		User temp = null; 
		String sql = "select * from misUser where userId = '" + userId + "'";
		System.out.println(sql);
		DBUtil dbUtil = new DBUtil();
		ResultSet rs = dbUtil.excuteQuary(sql);
		try {
			while(rs.next()) {
				temp = new User();
				temp.setUserId(rs.getString(1));
				temp.setUserPassword(rs.getString(2));
				temp.setUserName(rs.getString(3));
				temp.setRoleId(rs.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return temp;
	
	}

	@Override
	public List<User> findByLike(User user) {
		return null;
	}
	
}
