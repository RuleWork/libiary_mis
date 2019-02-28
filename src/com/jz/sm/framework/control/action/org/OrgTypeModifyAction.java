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

public class OrgTypeModifyAction implements FunctionAction,ActionListener{
	private JPanel panelWork = null;
	private JTabbedPane tabbedPane = null;//��ǩ���
	private JPanel panelModify = null;//�޸Ľ���
	private JPanel panelTest = null;//���Խ���
	private JPanel panelButton = null;//��ť���
	
	private JTable tableBody = null;//���
	private DefaultTableModel tableModel = null;//�����
	private JScrollPane scrollPane = null;//������
	private String[] title = null;//����
	private String[][] data = null;//����
	
	private JButton modifyButton = null;//�޸İ�ť
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
		this.panelModify = new JPanel(new BorderLayout());//�߿򲼾�
		this.title = new String[] {"ͼ����","ͼ������","ͼ�����"};//����
		this.data = this.getData(new OrgType());//����
		this.tableModel = new DefaultTableModel(this.data,this.title);
		this.tableBody = new JTable(this.tableModel);
		this.scrollPane = new JScrollPane(this.tableBody);//��������ӱ��
		this.panelModify.add(this.scrollPane, BorderLayout.CENTER);
		
		this.panelButton = new JPanel();
		this.modifyButton = new JButton("�޸�");
		this.modifyButton.addActionListener(this);
		this.refreshButton = new JButton("ˢ��");
		this.refreshButton.addActionListener(this);
		this.panelButton.add(this.modifyButton);
		this.panelButton.add(this.refreshButton);
		this.panelModify.add(this.panelButton, BorderLayout.SOUTH);
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
		this.tabbedPane.add("�޸�", this.panelModify);
		
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
		if (e.getSource() == this.modifyButton) {
			int n = this.tableBody.getSelectedRowCount();
			if (n != 1) {
				JOptionPane.showMessageDialog(null, "��ѡ��һ����¼��");
				return;
			}
			String orgTypeId = this.tableModel.getValueAt(this.tableBody.getSelectedRow(), 0).toString();
			ModifyDialog dialog = new ModifyDialog(this,orgTypeId);
			dialog.setSize(600,500);
			dialog.setLocationRelativeTo(null);
		} else if (e.getSource() == this.refreshButton) {
			this.refreshTable();
		}
	}
	
	public class ModifyDialog extends JDialog implements ActionListener{
		private OrgTypeModifyAction modifyAction = null;//�����޸����
		private OrgType allOrgType = null;//��������ĳ�е���������
		
		private JLabel orgTypeIdLabel = null;
		private JTextField orgTypeIdField = null;
		
		private JLabel orgTypeNameLabel = null;
		private JTextField orgTypeNameField = null;
		
		private JLabel orgTypeMemoLabel = null;
		private JTextArea orgTypeMemoArea = null;
		private JScrollPane orgTypeMemoScroll = null;
		
		private JPanel buttonPanel = null;
		private JButton buttonModify = null;
		private JButton buttonBack = null;
		
		private void init() {
			Container container = this.getContentPane();
			container.setLayout(new GridBagLayout());
			
			GridBagConstraints gbc = new GridBagConstraints();
			
			gbc.gridx = 0;
			gbc.gridy = 0;
			this.orgTypeIdLabel = new JLabel("ͼ���ţ�");
			container.add(this.orgTypeIdLabel,gbc);
			gbc.gridx = 1;
			gbc.gridy = 0;
			this.orgTypeIdField = new JTextField(20);
			this.orgTypeIdField.setEditable(false);
			this.orgTypeIdField.setText(this.allOrgType.getOrgTypeId());
			container.add(this.orgTypeIdField,gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 1;
			this.orgTypeNameLabel = new JLabel("ͼ�����ƣ�");
			container.add(this.orgTypeNameLabel,gbc);
			gbc.gridx = 1;
			gbc.gridy = 1;
			this.orgTypeNameField = new JTextField(20);
			this.orgTypeNameField.setText(this.allOrgType.getOrgTypeName());
			container.add(this.orgTypeNameField,gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 2;
			this.orgTypeMemoLabel = new JLabel("ͼ�������");
			container.add(this.orgTypeMemoLabel,gbc);
			gbc.gridx = 1;
			gbc.gridy = 2;
			this.orgTypeMemoArea = new JTextArea(8, 20);
			this.orgTypeMemoArea.setText(this.allOrgType.getOrgTypeMemo());
			this.orgTypeMemoScroll = new JScrollPane();
			this.orgTypeMemoScroll.getViewport().add(this.orgTypeMemoArea);
			container.add(this.orgTypeMemoScroll,gbc);
			
			this.buttonPanel = new JPanel();
			this.buttonPanel.setLayout(new FlowLayout());
			this.buttonModify = new JButton("����");
			//this.buttonModify.setActionCommand("save");
			this.buttonModify.addActionListener(this);
			this.buttonBack = new JButton("��ԭ");
			//this.buttonBack.setActionCommand("fade");
			this.buttonBack.addActionListener(this);
			this.buttonPanel.add(this.buttonModify);
			this.buttonPanel.add(this.buttonBack);
			gbc.gridx = 1;
			gbc.gridy = 3;
			container.add(this.buttonPanel,gbc);
			
			this.setTitle("��ϸ��Ϣ");
			this.setVisible(true);
		}
		
		public ModifyDialog() {

		}
		
		
		public ModifyDialog(OrgTypeModifyAction modifyAction, String orgTypeId) {
			this.modifyAction = modifyAction;
			this.allOrgType = new OrgTypeDAOlmpl().findById(orgTypeId);
			this.init();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == this.buttonModify) {
				String id = this.orgTypeIdField.getText();
				String name = this.orgTypeNameField.getText();
				String memo = this.orgTypeMemoArea.getText();
				OrgType orgType = new OrgType(id, name, memo);
				if (new OrgTypeDAOlmpl().update(orgType)) {
					JOptionPane.showMessageDialog(this, "�޸ĳɹ�");
					this.modifyAction.refreshTable();
				} else {
					JOptionPane.showMessageDialog(this, "�޸�ʧ��");
				}
			} else if (e.getSource() == this.buttonBack) {
				this.orgTypeNameField.setText(this.allOrgType.getOrgTypeName());
				this.orgTypeMemoArea.setText(this.allOrgType.getOrgTypeMemo());
			}
		}
	}
}
