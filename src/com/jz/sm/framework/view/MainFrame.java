package com.jz.sm.framework.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import com.jz.sm.framework.control.listener.MenuListener;
import com.jz.sm.framework.model.dao.impl.IMenu;
import com.jz.sm.framework.model.dao.impl.MenuImpl;
import com.jz.sm.framework.model.entity.Menu;
import com.jz.sm.framework.model.entity.User;

public class MainFrame extends JFrame implements ActionListener{
	User user = null;
	
	private JMenuBar menuBar = null;
	private JMenu menuHelp = null;
	private JMenuItem itemAbout = null;
	private JMenuItem itemContent = null;
	
	
	private JToolBar toolBar = null;
	private JLabel labelWelcome = null;
	private JLabel labelNameText = null;
	private JButton buttonOut = null;
	private JButton buttonBack = null;
	
	private JPanel panelWelcome = null;//欢迎面板
	
	private JLabel labelState = null;//制作标签
	
	public MainFrame(User user) {
		this.user = user;
		this.init();
	}
	
	private void createMenu() {
		IMenu dao = new MenuImpl();
		
		String sql1 = "select * from menu where menuId like '__'";
		List<Menu> list1 = dao.findBySql(sql1);
		for (Menu myMenu1 : list1) {
			JMenu menuTemp = new JMenu();
			menuTemp.setActionCommand(myMenu1.getMenuId());
			menuTemp.setText(myMenu1.getMenuName());
			menuTemp.setToolTipText(myMenu1.getMenuMemo());
			
			String sql2 = "select * from menu where menuId like '" + myMenu1.getMenuId() + "__'";
			List<Menu> list2 = dao.findBySql(sql2);
			for (Menu myMenu2 : list2) {
				JMenuItem itemTemp = new JMenuItem();
				itemTemp.setActionCommand(myMenu2.getMenuId());
				itemTemp.setText(myMenu2.getMenuName());
				itemTemp.setToolTipText(myMenu2.getMenuMemo());
				itemTemp.addActionListener(new MenuListener(this, this.user));
				menuTemp.add(itemTemp);
			}
			this.menuBar.add(menuTemp);
		}
	}
	
	private void init() {
		JPanel panel = (JPanel)this.getContentPane();
		panel.setLayout(new BorderLayout());
		
		this.menuBar = new JMenuBar();
		this.menuHelp = new JMenu("帮助");
		this.itemAbout = new JMenuItem("关于");
		this.itemContent = new JMenuItem("内容");
		this.menuHelp.add(this.itemAbout);
		this.menuHelp.add(this.itemContent);
		this.createMenu();
		this.menuBar.add(this.menuHelp);
		this.setJMenuBar(this.menuBar);
		
		this.toolBar = new JToolBar();
		this.toolBar.setFloatable(false);//设置不可拖动
		this.toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		this.labelWelcome = new JLabel("欢迎你: ");
		this.labelNameText = new JLabel(this.user.getUserName());
		
		this.buttonOut = new JButton("首页");
		this.buttonBack = new JButton("重新登录");
		
		this.buttonOut.addActionListener(this);
		this.buttonBack.addActionListener(this);
		
		this.toolBar.add(this.labelWelcome);
		this.toolBar.add(this.labelNameText);
		this.toolBar.add(this.buttonOut);
		this.toolBar.add(this.buttonBack);
		
		panel.add(this.toolBar, BorderLayout.NORTH);
		
		this.panelWelcome = new WelcomePane();
		panel.add(this.panelWelcome, BorderLayout.CENTER);
		
		//给所有矩形的组件加边框，单位为像素
		this.panelWelcome.setBorder(BorderFactory.createMatteBorder(2, 0, 1, 0, Color.RED));
		
		this.labelState = new JLabel("图书馆管理系统");
	    this.labelState.setHorizontalAlignment(JLabel.CENTER);
	    panel.add(this.labelState, BorderLayout.SOUTH);
	    
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setTitle("用户主界面");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.buttonBack) {
			LoginFrame loginFrame = new LoginFrame();
			loginFrame.setVisible(true);
			this.dispose();
		}else if (e.getSource() == this.buttonOut) {
			MainFrame mainFrame = new MainFrame(this.user);
			mainFrame.setSize(1200,900);
			mainFrame.setLocationRelativeTo(null);
			mainFrame.setVisible(true);
			this.dispose();
		}
	}
}
