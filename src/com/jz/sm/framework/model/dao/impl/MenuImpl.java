package com.jz.sm.framework.model.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jz.sm.framework.model.entity.Menu;
import com.jz.sm.framework.model.util.DBUtil;

public class MenuImpl implements IMenu {

	@Override
	public List<Menu> findBySql(String sql) {
		List<Menu> list = new ArrayList<Menu>();
		DBUtil dbUtil = new DBUtil();
		ResultSet rs = dbUtil.excuteQuary(sql);
		try {
			while(rs.next()) {
				Menu temp = new Menu();
				temp.setMenuId(rs.getString(1));
				temp.setMenuName(rs.getString(2));
				temp.setMenuMemo(rs.getString(3));
			    list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return list;
	
	
	
	}

}
