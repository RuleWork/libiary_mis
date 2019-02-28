package com.jz.sm.framework.model.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jz.sm.framework.model.entity.Function;
import com.jz.sm.framework.model.util.DBUtil;

public class FunctionImpl implements IFunction{

	@Override
	public List<Function> findByMenuId(String menuId,String roleId) {
			List<Function> list = new ArrayList<Function>();
			DBUtil dbUtil = new DBUtil();
			String sql = "select * from misFunction where menuId = '" + menuId + "'" +
					     " and functionId in (select functionId from auth where roleId = '" + roleId + "')";
			System.out.println(sql);
			ResultSet rs = dbUtil.excuteQuary(sql);
			try {
				while(rs.next()) {
					Function temp = new Function();
					temp.setFunctionId(rs.getString(1));
					temp.setFunctionName(rs.getString(2));
					temp.setFunctionClass(rs.getString(3));
					temp.setFuncitonMemo(rs.getString(4));
					temp.setMenuId(rs.getString(5));
					list.add(temp);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbUtil.close();
			}
			return list;
		}

	@Override
	public Function findByFunctionId(String functionId) {
		String sql = "select * from misFunction where functionId = '"+ functionId +"'";
		DBUtil dbUtil = new DBUtil();
		Function misFunction = null;
		ResultSet rs = dbUtil.excuteQuary(sql);
		try {
			while(rs.next()) {
				misFunction = new Function();
				misFunction.setFunctionClass(rs.getString("functionClass"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return misFunction;
	}
	
	
	
}
