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

import com.jz.sm.framework.model.dao.impl.IOrgTypeDAO;
import com.jz.sm.framework.model.dao.impl.OrgTypeDAOlmpl;
import com.jz.sm.framework.model.entity.OrgType;

public class OrgTypeDeleteAction implements FunctionAction,ActionListener{
	private JPanel panelWork = null;
	private JTabbedPane tabbedPane = null;//标签面板
	private JPanel panelDelete = null;//修改界面
	private JPanel panelTest = null;//测试界面
	private JPanel panelButton = null;//按钮面板
	
	private JTable tableBody = null;//表格
	private DefaultTableModel tableModel = null;//灵活表格
	private JScrollPane scrollPane = null;//滚动条
	private String[] title = null;//列名
	private String[][] data = null;//行名
	
	private JButton deleteButton = null;//修改按钮
	private JButton refreshButton = null;//还原按钮
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
		myData = new String[list.size()][3];//三行也可动态，返回row
		for (int i = 0; i < list.size(); i++) {//遍历数据
			OrgType temp = list.get(i);
			myData[i][0] = temp.getOrgTypeId();
			myData[i][1] = temp.getOrgTypeName();
			myData[i][2] = temp.getOrgTypeMemo();
		}
		return myData;
	}
 	//建立表格面板
	private void initModifyPanel() {
		this.panelDelete = new JPanel(new BorderLayout());//边框布局
		this.title = new String[] {"图书编号","图书名称","图书简述"};//标题
		this.data = this.getData(new OrgType());//数据
		this.tableModel = new DefaultTableModel(this.data,this.title);
		this.tableBody = new JTable(this.tableModel);
		this.scrollPane = new JScrollPane(this.tableBody);//滚动条里加表格
		this.panelDelete.add(this.scrollPane, BorderLayout.CENTER);
		
		this.panelButton = new JPanel();
		this.deleteButton= new JButton("删除");
		this.deleteButton.addActionListener(this);
		this.refreshButton = new JButton("刷新");
		this.refreshButton.addActionListener(this);
		this.panelButton.add(this.deleteButton);
		this.panelButton.add(this.refreshButton);
		this.panelDelete.add(this.panelButton, BorderLayout.SOUTH);
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
		this.tabbedPane.add("删除", this.panelDelete);
		
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
		if (e.getSource() == this.deleteButton) {
			int n = this.tableBody.getSelectedRowCount();
			if (n != 1) {
				JOptionPane.showMessageDialog(null, "请选择一条记录！");
				return;
			}
			int x = JOptionPane.showConfirmDialog(null, "确认删除吗?", "确认对话框", JOptionPane.YES_NO_OPTION);
			if (x == JOptionPane.YES_OPTION) {
				String orgTypeId = this.tableModel.getValueAt(this.tableBody.getSelectedRow(), 0).toString();
				IOrgTypeDAO dao = new OrgTypeDAOlmpl();
				boolean result = dao.delete(orgTypeId);
				if (result == true) {
					JOptionPane.showMessageDialog(null, "删除成功！");
				} else {
					JOptionPane.showMessageDialog(null, "删除失败");
				}

			}
					} else if (e.getSource() == this.refreshButton) {
						this.refreshTable();
		}
	}
}
