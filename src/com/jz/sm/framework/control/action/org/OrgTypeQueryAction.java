package com.jz.sm.framework.control.action.org;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.jz.sm.framework.control.action.org.OrgTypeModifyAction.ModifyDialog;
import com.jz.sm.framework.model.dao.impl.IOrgTypeDAO;
import com.jz.sm.framework.model.dao.impl.OrgTypeDAOlmpl;
import com.jz.sm.framework.model.entity.OrgType;

public class OrgTypeQueryAction implements FunctionAction,ActionListener{
	private JPanel panelWork = null;
	private JTabbedPane tabbedPane = null;//标签面板
	private JPanel panelQuery = null;//修改界面
	private JPanel panelTest = null;//测试界面
	private JPanel panelButton = null;//按钮面板
	
	private JTable tableBody = null;//表格
	private DefaultTableModel tableModel = null;//灵活表格
	private JScrollPane scrollPane = null;//滚动条
	private String[] title = null;//列名
	private String[][] data = null;//行名
	
	private JButton detailsButton = null;//详情按钮
	
	private JPanel queryPanel = null;
	private JLabel queryLabel = null;
	private JTextField queryField = null;
	private JButton queryButton = null;
	private JButton backButton = null;
	
	//还原功能
	private void refreshTable() {
		this.tableBody.removeAll();
		this.tableBody.repaint();
		
		this.data = this.getData(new OrgType());//查询所有的数据
		this.tableModel = new DefaultTableModel(this.data, this.title);//将数据显示到表格中
		this.tableBody.setModel(this.tableModel);//设置表格为灵活表格
	}
	//获得数据
	private String[][] getData(OrgType orgType){
		String[][] myData = null;//返回查询值
		List<OrgType> list = new ArrayList<OrgType>();//放机构的容器
		IOrgTypeDAO dao = new OrgTypeDAOlmpl();//DAO
		list = dao.findByLike(orgType);
		myData = new String[list.size()][3];
		for (int i = 0; i < list.size(); i++) {//遍历数据
			OrgType temp = list.get(i);
			myData[i][0] = temp.getOrgTypeId();
			myData[i][1] = temp.getOrgTypeName();
			myData[i][2] = temp.getOrgTypeMemo();
		}
		return myData;
	}
	
	private void query(String Id) {
		IOrgTypeDAO dao = new OrgTypeDAOlmpl();
		OrgType orgType = dao.findById(Id);
		this.refreshTable();
		
	}
 	//建立表格面板
	private void initModifyPanel() {
		
		this.panelQuery = new JPanel(new BorderLayout());//边框布局
		
		this.queryPanel = new JPanel(new FlowLayout());
		this.queryLabel = new JLabel("图书信息：");
		this.queryField = new JTextField(16);
		this.queryButton = new JButton("查询");
		this.queryButton.addActionListener(this);
		this.backButton = new JButton("返回");
		this.backButton.addActionListener(this);
	    this.queryLabel.setHorizontalAlignment(JLabel.CENTER);
	    this.queryPanel.add(this.queryLabel);
	    this.queryPanel.add(this.queryField);
	    this.queryPanel.add(this.queryButton);
	    this.queryPanel.add(this.backButton);
	    this.panelQuery.add(this.queryPanel, BorderLayout.NORTH);
		
		this.title = new String[] {"图书编号","图书名称","图书简述"};//标题
		this.data = this.getData(new OrgType());//数据
		this.tableModel = new DefaultTableModel(this.data,this.title);
		this.tableBody = new JTable(this.tableModel);
		this.scrollPane = new JScrollPane(this.tableBody);//滚动条里加表格
		this.panelQuery.add(this.scrollPane, BorderLayout.CENTER);
		
		this.panelButton = new JPanel();
		this.detailsButton = new JButton("详细信息");
		this.detailsButton.addActionListener(this);
		this.panelButton.add(this.detailsButton);
		this.panelQuery.add(this.panelButton, BorderLayout.SOUTH);
	}
	//建立测试面板
	private void initTestPanel() {
		this.panelTest = new JPanel();
		this.panelTest.setBackground(Color.BLUE);
	}
	//总建立
	private void init() {
		this.panelWork.removeAll();
		this.panelWork.repaint();
		this.panelWork.setLayout(new BorderLayout());
		
		this.tabbedPane = new JTabbedPane();
		
		this.initModifyPanel();
		this.tabbedPane.add("删除", this.panelQuery);
		
		this.initTestPanel();
		this.tabbedPane.add("test", this.panelTest);
		
		this.panelWork.add(this.tabbedPane, BorderLayout.CENTER);
		
	}
	
	//传入，开始画
	@Override
	public void execute(JPanel panelWork) {
		this.panelWork = panelWork;
		this.init();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		 OrgType orgType = null;
		 if (e.getSource() == this.detailsButton) {
			String orgTypeId = this.tableModel.getValueAt(this.tableBody.getSelectedRow(), 0).toString();
			QueryDialog dialog = new QueryDialog(this,orgTypeId);
			dialog.setSize(600,500);
			dialog.setLocationRelativeTo(null);

		 } else if (e.getSource() == this.queryButton) {
			this.tableBody.removeAll();
			this.tableBody.repaint();
			 
			orgType = new OrgType();
			orgType.setOrgTypeName(this.queryField.getText());
			this.data = this.getData(orgType);
			this.tableModel = new DefaultTableModel(this.data, this.title);
			this.tableBody.setModel(this.tableModel);
		 } else if (e.getSource() == this.backButton) {
			this.queryField.setText("");
			this.refreshTable();
		}
	}
	
	public class QueryDialog extends JDialog {
		private OrgTypeQueryAction modifyAction = null;//传入修改组件
		private OrgType allOrgType = null;//传入表格中某行的所有数据
		
		private JLabel orgTypeIdLabel = null;
		private JTextField orgTypeIdField = null;
		
		private JLabel orgTypeNameLabel = null;
		private JTextField orgTypeNameField = null;
		
		private JLabel orgTypeMemoLabel = null;
		private JTextArea orgTypeMemoArea = null;
		private JScrollPane orgTypeMemoScroll = null;
		
		
		
		private void init() {
			Container container = this.getContentPane();
			container.setLayout(new GridBagLayout());
			
			GridBagConstraints gbc = new GridBagConstraints();
			
			gbc.gridx = 0;
			gbc.gridy = 0;
			this.orgTypeIdLabel = new JLabel("图书编号：");
			container.add(this.orgTypeIdLabel,gbc);
			gbc.gridx = 1;
			gbc.gridy = 0;
			this.orgTypeIdField = new JTextField(20);
			this.orgTypeIdField.setEditable(false);
			this.orgTypeIdField.setText(this.allOrgType.getOrgTypeId());
			container.add(this.orgTypeIdField,gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 1;
			this.orgTypeNameLabel = new JLabel("图书名称：");
			container.add(this.orgTypeNameLabel,gbc);
			gbc.gridx = 1;
			gbc.gridy = 1;
			this.orgTypeNameField = new JTextField(20);
			this.orgTypeNameField.setEditable(false);
			this.orgTypeNameField.setText(this.allOrgType.getOrgTypeName());
			container.add(this.orgTypeNameField,gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 2;
			this.orgTypeMemoLabel = new JLabel("图书简述：");
			container.add(this.orgTypeMemoLabel,gbc);
			gbc.gridx = 1;
			gbc.gridy = 2;
			this.orgTypeMemoArea = new JTextArea(8, 20);
			this.orgTypeMemoArea.setEditable(false);
			this.orgTypeMemoArea.setText(this.allOrgType.getOrgTypeMemo());
			this.orgTypeMemoScroll = new JScrollPane();
			this.orgTypeMemoScroll.getViewport().add(this.orgTypeMemoArea);
			container.add(this.orgTypeMemoScroll,gbc);
			
			
			
			this.setTitle("详细信息");
			this.setVisible(true);
		}
		
		public QueryDialog() {

		}
		
		
		public QueryDialog(OrgTypeQueryAction action,String orgTypeId) {
			this.modifyAction = action;
			this.allOrgType = new OrgTypeDAOlmpl().findById(orgTypeId);
			this.init();
		}

		
	}
}
