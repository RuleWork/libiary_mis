package com.jz.sm.framework.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import com.jz.sm.framework.model.dao.impl.IUser;
import com.jz.sm.framework.model.dao.impl.UserImpl;
import com.jz.sm.framework.model.entity.User;

import javafx.scene.control.ProgressBar;

public class LoginFrame extends JFrame implements ActionListener{
	User user = null;
	
	private JPanel panelCenter = null;
	private JPanel panelButton = null;
	
	private JLabel labelTitle = null;
	
	private JLabel labelId = null;
	private JTextField fieldId = null;
	
	private JLabel labelPassword = null;
	private JPasswordField passwordField = null;
	
	private JButton buttonLogin = null;
	private JButton buttonReset = null;
	
	private JProgressBar progressBar = null;
	
	private Timer timer;
	int n = 100;
	
	private boolean checkUser(String id,String password) {
		boolean flag = false;
		IUser dao = new UserImpl();
		User temp = dao.findById(id);
		if (temp != null && temp.getUserPassword().equals(password)) {
			flag = true;
			this.user = temp;
		}
		return flag;
	}
	
	private void init() {
		Container content = this.getContentPane();
		content.setLayout(new BorderLayout());
		
		this.panelCenter = new WelcomePane();
		this.panelCenter.setLayout(new GridBagLayout());

		content.add(panelCenter,BorderLayout.CENTER);
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		this.labelTitle = new JLabel("图书馆管理系统");
		this.labelTitle.setFont(new Font("", Font.BOLD, 16));//设置字体
		gbc.gridx = 1;
		gbc.gridy = 0;
		this.panelCenter.add(this.labelTitle, gbc);
		
		this.labelId = new JLabel("用户名：");
		this.labelId.setForeground(Color.PINK);
		gbc.gridx = 0;
		gbc.gridy = 1;
		this.panelCenter.add(this.labelId, gbc);
		
		this.fieldId = new JTextField(16);
		gbc.gridx = 1;
		gbc.gridy = 1;
		this.panelCenter.add(this.fieldId, gbc);
	
		this.labelPassword = new JLabel("密     码：");
		this.labelPassword.setForeground(Color.PINK);
		gbc.gridx = 0;
		gbc.gridy = 2;
		this.panelCenter.add(this.labelPassword, gbc);
		
		this.passwordField = new JPasswordField(16);
		gbc.gridx = 1;
		gbc.gridy = 2;
		this.panelCenter.add(this.passwordField, gbc);
		
		this.panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
		this.panelButton.setOpaque(false);//设置透明
		
		this.buttonLogin = new JButton("登录");
		this.buttonReset = new JButton("重置");
		
		this.panelButton.add(this.buttonLogin);
		this.panelButton.add(this.buttonReset);
		gbc.gridx = 1;
		gbc.gridy = 3;
		this.panelCenter.add(this.panelButton, gbc);
		
		this.buttonLogin.addActionListener(this);
		this.buttonReset.addActionListener(this);
		
		this.progressBar = new JProgressBar(1,100);
		this.progressBar.setStringPainted(true);
		this.progressBar.setString("正在加载程序.....");
		this.getContentPane().add(this.progressBar, "South");
		
		this.timer = new Timer(50, this);
		this.timer.addActionListener(this);
		this.timer.start();
		
		//设置回车快捷键
		buttonLogin.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0),JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		this.setTitle("用户登录");
		this.setSize(400,300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public LoginFrame() {
		this.init();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.buttonLogin) {
			String id = this.fieldId.getText();
			String password = new String(this.passwordField.getPassword());
			if (id == null || id.length() <= 0 || password == null || password.length() <= 0) {
				JOptionPane.showMessageDialog(this, "用户名密码不能为空！");
				return;
			}
			if (this.checkUser(id, password)) {
				JOptionPane.showMessageDialog(this, "登录成功！");
				MainFrame mainFrame = new MainFrame(this.user);
				mainFrame.setSize(1200,900);
				mainFrame.setLocationRelativeTo(null);
				mainFrame.setVisible(true);
				this.dispose();
				
			} else {
				JOptionPane.showMessageDialog(this, "登录失败");
			}
		}else if (e.getSource() == this.buttonReset) {
			this.fieldId.setText("");
			this.passwordField.setText("");
		}else if (--n > 0) {
			this.fieldId.setEditable(false);
			this.passwordField.setEditable(false);
			
			this.progressBar.setValue(100-n);
			this.timer.restart();
		}else {
			this.fieldId.setEditable(true);
			this.passwordField.setEditable(true);
			this.timer.stop();
		}
	}
}
