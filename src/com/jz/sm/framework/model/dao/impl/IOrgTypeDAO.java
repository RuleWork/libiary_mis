package com.jz.sm.framework.model.dao.impl;

import java.util.List;

import com.jz.sm.framework.model.entity.OrgType;

public interface IOrgTypeDAO {
	
	public boolean save(OrgType orgType);
	
	public boolean delete(String orgTypeId);
	
	public boolean update(OrgType orgType);
	
	public OrgType findById(String orgTypeId);
	public List<OrgType> findByLike(OrgType orgType);
	
}
