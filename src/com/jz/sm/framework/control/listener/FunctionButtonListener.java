package com.jz.sm.framework.control.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jz.sm.framework.control.action.org.FunctionAction;
import com.jz.sm.framework.model.dao.impl.FunctionImpl;


public class FunctionButtonListener implements ActionListener{
	private JFrame mainFrame = null;
	
	public FunctionButtonListener() {

	}
	
	public FunctionButtonListener(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		JPanel panelBody = (JPanel)this.mainFrame.getContentPane();
//		Component[] components1 = panelBody.getComponents();
//		JPanel panelWelcome = (JPanel)components1[1];
		JPanel panelWelcome  = 	(JPanel)panelBody.getComponent(1);
		JPanel panelWork = (JPanel)panelWelcome.getComponent(0);
		
		String functionId = e.getActionCommand();
		System.out.println(functionId);
		String functionClass = new FunctionImpl().findByFunctionId(functionId).getFunctionClass();
		System.out.println(functionClass);
		Class myClass = null;
		FunctionAction action = null;
		//java的反射机制
		try {
			myClass = Class.forName(functionClass);
			action = (FunctionAction)myClass.newInstance();
			action.execute(panelWork);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		this.mainFrame.setVisible(true);
	}
	
}
