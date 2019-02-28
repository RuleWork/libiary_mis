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
	private JTabbedPane tabbedPane = null;//��ǩ���
	private JPanel panelDelete = null;//�޸Ľ���
	private JPanel panelTest = null;//���Խ���
	private JPanel panelButton = null;//��ť���
	
	private JTable tableBody = null;//���
	private DefaultTableModel tableModel = null;//�����
	private JScrollPane scrollPane = null;//������
	private String[] title = null;//����
	private String[][] data = null;//����
	
	private JButton deleteButton = null;//�޸İ�ť
	private JButton refreshButton = null;//��ԭ��ť
	//��ԭ����
	private void refreshTable() {
		this.tableBody.removeAll();
		this.tableBody.repaint();
		
		this.data = this.getData(new OrgType());//��ѯ���е�����
		this.tableModel = new DefaultTableModel(this.data, this.title);//��������ʾ�������
		this.tableBody.setModel(this.tableModel);//���ñ��Ϊ�����
	}
	//�������
	private String[][] getData(OrgType orgType){
		String[][] myData = null;//���ز�ѯֵ
		List<OrgType> list = new ArrayList<OrgType>();//�Ż���������
		IOrgTypeDAO dao = new OrgTypeDAOlmpl();//DAO
		list = dao.findByLike(orgType);
		myData = new String[list.size()][3];//����Ҳ�ɶ�̬������row
		for (int i = 0; i < list.size(); i++) {//��������
			OrgType temp = list.get(i);
			myData[i][0] = temp.getOrgTypeId();
			myData[i][1] = temp.getOrgTypeName();
			myData[i][2] = temp.getOrgTypeMemo();
		}
		return myData;
	}
 	//����������
	private void initModifyPanel() {
		this.panelDelete = new JPanel(new BorderLayout());//�߿򲼾�
		this.title = new String[] {"ͼ����","ͼ������","ͼ�����"};//����
		this.data = this.getData(new OrgType());//����
		this.tableModel = new DefaultTableModel(this.data,this.title);
		this.tableBody = new JTable(this.tableModel);
		this.scrollPane = new JScrollPane(this.tableBody);//��������ӱ��
		this.panelDelete.add(this.scrollPane, BorderLayout.CENTER);
		
		this.panelButton = new JPanel();
		this.deleteButton= new JButton("ɾ��");
		this.deleteButton.addActionListener(this);
		this.refreshButton = new JButton("ˢ��");
		this.refreshButton.addActionListener(this);
		this.panelButton.add(this.deleteButton);
		this.panelButton.add(this.refreshButton);
		this.panelDelete.add(this.panelButton, BorderLayout.SOUTH);
	}
	//�����������
	private void initTestPanel() {
		this.panelTest = new JPanel();
		this.panelTest.setBackground(Color.BLUE);
	}
	//�ܽ���
	private void init() {
		this.panelWork.removeAll();
		this.panelWork.repaint();
		this.panelWork.setLayout(new BorderLayout());
		
		this.tabbedPane = new JTabbedPane();
		
		this.initModifyPanel();
		this.tabbedPane.add("ɾ��", this.panelDelete);
		
		this.initTestPanel();
		this.tabbedPane.add("test", this.panelTest);
		
		this.panelWork.add(this.tabbedPane, BorderLayout.CENTER);
		
	}
	
	//���룬��ʼ��
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
				JOptionPane.showMessageDialog(null, "��ѡ��һ����¼��");
				return;
			}
			int x = JOptionPane.showConfirmDialog(null, "ȷ��ɾ����?", "ȷ�϶Ի���", JOptionPane.YES_NO_OPTION);
			if (x == JOptionPane.YES_OPTION) {
				String orgTypeId = this.tableModel.getValueAt(this.tableBody.getSelectedRow(), 0).toString();
				IOrgTypeDAO dao = new OrgTypeDAOlmpl();
				boolean result = dao.delete(orgTypeId);
				if (result == true) {
					JOptionPane.showMessageDialog(null, "ɾ���ɹ���");
				} else {
					JOptionPane.showMessageDialog(null, "ɾ��ʧ��");
				}

			}
					} else if (e.getSource() == this.refreshButton) {
						this.refreshTable();
		}
	}
}
