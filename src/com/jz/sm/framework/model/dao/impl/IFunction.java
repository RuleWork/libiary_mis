package com.jz.sm.framework.model.dao.impl;

import java.util.List;

import com.jz.sm.framework.model.entity.Function;

public interface IFunction {
	public List<Function> findByMenuId(String menuId,String roleId);
	
	public Function findByFunctionId(String functionId);
}
