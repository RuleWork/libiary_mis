package com.jz.sm.framework.control.listener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jz.sm.framework.model.dao.impl.FunctionImpl;
import com.jz.sm.framework.model.dao.impl.IFunction;
import com.jz.sm.framework.model.entity.Function;
import com.jz.sm.framework.model.entity.Menu;
import com.jz.sm.framework.model.entity.User;

public class MenuListener implements ActionListener{
	private JFrame mainFrame = null;//点击下面的主页面修改，需要传主页面作为参数
	private User user = null; //页面带权限
	
	private JPanel panelBody = null;
	private JPanel panelWelcome = null;
	private JPanel panelWork = null;//border中间
	private JPanel panelLeft = null;//border西边
	private JPanel panelButton = null;//panelLeft border的上边放button专用
	
	private Menu menu = null;
	
	public MenuListener() {
		
	}
	public MenuListener(JFrame mainFrame, User user) {
		this.mainFrame = mainFrame;
		this.user = user;
	}

	
	
	private List<JButton> getButtonList(String menuId,String roleId) {
		List<JButton> buttonList = new ArrayList<JButton>();
		IFunction dao = new FunctionImpl();
		List<Function> functionList = dao.findByMenuId(menuId,roleId);
		for (Function misFunction : functionList) {
			JButton tempButton = new JButton();
			System.out.println(misFunction.getFunctionId());
			tempButton.setActionCommand(misFunction.getFunctionId());
			tempButton.setText(misFunction.getFunctionName());
			tempButton.setToolTipText(misFunction.getFuncitonMemo());
			tempButton.addActionListener(new FunctionButtonListener(this.mainFrame));
			buttonList.add(tempButton);
		}
		return buttonList;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//第一层ToolBar,第二层PanelWelcome,第三层Label
		this.panelBody = (JPanel)this.mainFrame.getContentPane();
		Component[] components = this.panelBody.getComponents();
		this.panelWelcome = (JPanel)components[1];

		this.panelWelcome.removeAll();
		this.panelWelcome.repaint();
		
		this.panelWelcome.setLayout(new BorderLayout());
		
		this.panelWork = new JPanel();
		this.panelWelcome.add(this.panelWork, BorderLayout.CENTER);
		
		this.panelLeft = new JPanel(new BorderLayout());
		this.panelLeft.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.RED));
		this.panelButton = new JPanel(new GridLayout(6, 1));//六行一列
		System.out.println(e.getActionCommand());
		List<JButton> list = this.getButtonList(e.getActionCommand(),user.getRoleId());
		for (JButton jButton : list) {
			this.panelButton.add(jButton);
		}
		this.panelLeft.add(this.panelButton, BorderLayout.NORTH);
		
		this.panelWelcome.add(this.panelLeft, BorderLayout.WEST);
		//如果改变组件，就必须让底层组件刷新一遍
		this.mainFrame.setVisible(true);
	}

}
	