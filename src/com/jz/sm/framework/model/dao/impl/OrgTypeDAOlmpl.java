package com.jz.sm.framework.model.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jz.sm.framework.model.entity.OrgType;
import com.jz.sm.framework.model.util.DBUtil;

public class OrgTypeDAOlmpl implements IOrgTypeDAO {

	@Override
	public boolean save(OrgType orgType) {
		String id = orgType.getOrgTypeId();
		String name = orgType.getOrgTypeName();
		String memo = orgType.getOrgTypeMemo();
		String sql = "insert into orgType(orgTypeId, orgTypeName, orgTypeMemo) "
				+ "	  values ('"+ id +"','"+ name +"','"+ memo +"')";                            
		System.out.println(sql);
		DBUtil dbUtil = new DBUtil();
		int row = dbUtil.executeUpdate(sql);
		return (row == 1);
	}

	@Override
	public boolean delete(String orgTypeId) {
		String sql = "delete from orgType where orgTypeId = '"+ orgTypeId +"'";
		DBUtil dbUtil = new DBUtil();
		int row = dbUtil.executeUpdate(sql);
		return (row == 1);
	}

	@Override
	public boolean update(OrgType orgType) { 
		String sql = "update orgType set orgTypeName = '"
				+ orgType.getOrgTypeName() +"',orgTypeMemo = '"
				+ orgType.getOrgTypeMemo() +"' where orgTypeId = " 
				+ orgType.getOrgTypeId();
		DBUtil dbUtil = new DBUtil();
		int row = dbUtil.executeUpdate(sql);
		return (row == 1);
	}

	@Override
	public OrgType findById(String Id) {
		String sql = "select orgTypeId,orgTypeName,orgTypeMemo from orgType where orgTypeId = " + Id;
		DBUtil dbUtil = new DBUtil();
		ResultSet rs = dbUtil.excuteQuary(sql);
		OrgType orgType = null;
		try {
			if (rs.next()) {
				String orgTypeId = rs.getString(1);
				String orgTypeName = rs.getString(2);
				String orgTypeMemo = rs.getString(3);
				orgType = new OrgType(orgTypeId,orgTypeName,orgTypeMemo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return orgType;
	}

	@Override
	public List<OrgType> findByLike(OrgType orgType) {
		List<OrgType> list = new ArrayList<OrgType>();
		
		String id = orgType.getOrgTypeId();
		String name = orgType.getOrgTypeName();
		String memo = orgType.getOrgTypeMemo();
		
		String select = "select * from orgType ";
		String where = "where 1=1 ";
		if(id != null && id.length() > 0) {
			where = where + "and orgTypeId like '%" + id + "%' ";
		}
		if(name != null && name.length() > 0) {
			where = where + "and orgTypeName like '%" + name + "%' ";
		}
		if(memo != null && memo.length() > 0) {
			where = where + "and orgTypeMemo like '%" + memo + "%'";
		}
		
		String sql = select + where;
		System.out.println(sql);
		
		DBUtil dbUtil = new DBUtil();
		ResultSet rs = dbUtil.excuteQuary(sql);
		try {
			while(rs.next()) {
				OrgType temp = new OrgType();
				temp.setOrgTypeId(rs.getString(1));
				temp.setOrgTypeName(rs.getString(2));
				temp.setOrgTypeMemo(rs.getString(3));
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return list;
	}
	
}	
