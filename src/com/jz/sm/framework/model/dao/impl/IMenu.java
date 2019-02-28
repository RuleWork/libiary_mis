package com.jz.sm.framework.model.dao.impl;

import java.util.List;

import com.jz.sm.framework.model.entity.Menu;

public interface IMenu {
	public List<Menu> findBySql(String sql);
	
}
