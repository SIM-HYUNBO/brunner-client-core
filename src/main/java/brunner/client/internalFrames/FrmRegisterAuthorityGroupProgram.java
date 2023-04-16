package brunner.client.internalFrames;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.google.gson.JsonObject;

import brunner.client.BrunnerTableModel;
import brunner.client.FrmBrunnerInternalFrame;
import brunner.client.JBrunnerTable;
import brunner.client.LayoutUtil;
import brunner.client.MessageBoxUtil;
import brunner.client.api.AuthorityGroup;
import brunner.client.api.AuthorityGroupProgram;
import brunner.client.api.BrunnerClientApi;
import brunner.client.api.Program;
import brunner.client.frames.MainFrame;
import mw.launchers.RPCClient;

public class FrmRegisterAuthorityGroupProgram extends FrmBrunnerInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel lblAuthorityGroupList;
	JTable tblAuthorityGroupList;
	JScrollPane scAuthorityGroupList;
	JButton btnViewAuthorityGroupList;

	JLabel lblProgramList;
	JTable tblAuthorityGroupProgramList;
	JScrollPane scProgramList;

	JLabel lblAuthorityGroupId;
	JTextField txtAuthorityGroupId;
	JLabel lblAuthorityGroupName;
	JTextField txtAuthorityGroupName;

	JLabel lblProgramId;
	JComboBox<String> cboProgramId;
	JLabel lblParentProgramId;
	JComboBox<String> cboParentProgramId;
	JLabel lblDisplaySeq;
	JTextField txtDisplaySeq;

	JLabel lblProgramName;
	JTextField txtProgramName;
	JLabel lblProgramPath;
	JTextArea txtProgramPath;
	JScrollPane scProgramPath;

	JButton btnRegisterAuthorityGroupProgram;
	JButton btnUnregisterAuthorityGroupProgram;

	HashMap<String, JsonObject> tmpProgramList;
	HashMap<String, JsonObject> tmpParentProgramList;

	public FrmRegisterAuthorityGroupProgram(RPCClient rpcClient, JsonObject connectionInfo, JsonObject loginUserInfo,
			String title, int width, int height, ImageIcon icon) {

		super(rpcClient, connectionInfo, loginUserInfo, title, width, height, icon);
	}

	protected void initLayout(int width, int height) {
		super.initLayout(width, height);

		tmpProgramList = new HashMap<String, JsonObject>();
		tmpParentProgramList = new HashMap<String, JsonObject>();

		setLayout(null);

		int y_controlPosition = LayoutUtil.y_ControlStart;

		lblAuthorityGroupList = new JLabel("권한그룹 목록");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, y_controlPosition, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblAuthorityGroupList);

		btnViewAuthorityGroupList = new JButton("조회");
		btnViewAuthorityGroupList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					btnViewAuthorityGroupList_Click();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		LayoutUtil.layoutComponent(this, LayoutUtil.x_RightButton_1, y_controlPosition, LayoutUtil.w_NormalButton,
				LayoutUtil.h_NormalPanelControl, btnViewAuthorityGroupList);

		String groupHeaders[] = { "권한그룹아이디", "권한그룹명" };
		String groupContents[][] = {};

		tblAuthorityGroupList = new JTable() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component comp = super.prepareRenderer(renderer, row, column);
				int modelRow = convertRowIndexToModel(row);

				if (!isRowSelected(modelRow))
					comp.setBackground(Color.WHITE);
				else
					comp.setBackground(Color.LIGHT_GRAY);

				return comp;
			}
		};

		tblAuthorityGroupList.setModel(new BrunnerTableModel(groupContents, groupHeaders, false));
		tblAuthorityGroupList.setShowHorizontalLines(true);
		tblAuthorityGroupList.setGridColor(Color.GRAY);
		tblAuthorityGroupList.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
					int row = tblAuthorityGroupList.getSelectedRow();

					try {
						tblAuthorityGroupList_rowSelected(row);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}

		});
		tblAuthorityGroupList.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				int row = tblAuthorityGroupList.getSelectedRow();

				try {
					tblAuthorityGroupList_rowSelected(row);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

		});

		for (int colIndex = 0; colIndex < tblAuthorityGroupList.getColumnCount(); colIndex++)
			tblAuthorityGroupList.getColumnModel().getColumn(colIndex)
					.setCellRenderer(JBrunnerTable.getDefaultTableCellRenderer());

		((DefaultTableCellRenderer) tblAuthorityGroupList.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(JLabel.CENTER);

		scAuthorityGroupList = new JScrollPane(tblAuthorityGroupList);

		y_controlPosition += btnViewAuthorityGroupList.getHeight();
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, y_controlPosition, LayoutUtil.w_fullWidthControl,
				LayoutUtil.h_ShortTable, scAuthorityGroupList);

		y_controlPosition += scAuthorityGroupList.getHeight();

		lblProgramList = new JLabel("프로그램 목록");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, y_controlPosition, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblProgramList);

		String programHeaders[] = { "프로그램아이디", "부모프로그램", "표시순번", "프로그램명", "프로그램 경로" };
		String programContents[][] = {};

		tblAuthorityGroupProgramList = new JTable() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component comp = super.prepareRenderer(renderer, row, column);
				int modelRow = convertRowIndexToModel(row);
				if (!isRowSelected(modelRow))
					comp.setBackground(Color.WHITE);
				else
					comp.setBackground(Color.LIGHT_GRAY);
				return comp;
			}
		};

		tblAuthorityGroupProgramList.setModel(new BrunnerTableModel(programContents, programHeaders, false));
		tblAuthorityGroupProgramList.setShowHorizontalLines(true);
		tblAuthorityGroupProgramList.setGridColor(Color.GRAY);
		tblAuthorityGroupProgramList.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
					int row = tblAuthorityGroupProgramList.getSelectedRow();

					tblProgramList_rowSelected(row);
				}
			}

		});

		tblAuthorityGroupProgramList.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				int row = tblAuthorityGroupProgramList.getSelectedRow();

				tblProgramList_rowSelected(row);
			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

		});

		for (int colIndex = 0; colIndex < tblAuthorityGroupProgramList.getColumnCount(); colIndex++)
			tblAuthorityGroupProgramList.getColumnModel().getColumn(colIndex)
					.setCellRenderer(JBrunnerTable.getDefaultTableCellRenderer());

		((DefaultTableCellRenderer) tblAuthorityGroupProgramList.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(JLabel.CENTER);

		y_controlPosition += lblProgramList.getHeight();

		scProgramList = new JScrollPane(tblAuthorityGroupProgramList);

		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, y_controlPosition, LayoutUtil.w_fullWidthControl,
				LayoutUtil.h_NormalTable, scProgramList);

		y_controlPosition += scProgramList.getHeight();
		lblAuthorityGroupId = new JLabel("권한그룹아이디");

		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, y_controlPosition, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblAuthorityGroupId);

		txtAuthorityGroupId = new JTextField();
		txtAuthorityGroupId.setEditable(false);
		txtAuthorityGroupId.setHorizontalAlignment(JTextField.CENTER);
		txtAuthorityGroupId.setEditable(true);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, y_controlPosition,
				LayoutUtil.w_NormalPanelControl, LayoutUtil.h_NormalPanelControl, txtAuthorityGroupId);

		lblAuthorityGroupName = new JLabel("권한그룹명");

		y_controlPosition += txtAuthorityGroupId.getHeight();
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, y_controlPosition,
				LayoutUtil.w_NormalPanelControl, LayoutUtil.h_NormalPanelControl, lblAuthorityGroupName);

		txtAuthorityGroupName = new JTextField("");
		txtAuthorityGroupName.setEditable(false);
		txtAuthorityGroupName.setHorizontalAlignment(JTextField.CENTER);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, y_controlPosition,
				LayoutUtil.w_NormalPanelControl, LayoutUtil.h_NormalPanelControl, txtAuthorityGroupName);

		lblProgramId = new JLabel("프로그램아이디");

		y_controlPosition += txtAuthorityGroupName.getHeight();
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, y_controlPosition,
				LayoutUtil.w_NormalPanelControl, LayoutUtil.h_NormalPanelControl, lblProgramId);

		cboProgramId = new JComboBox<String>();
		cboProgramId.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				cboProgramId_selectionChanged();
			}

		});
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, y_controlPosition,
				LayoutUtil.w_NormalPanelControl, LayoutUtil.h_NormalPanelControl, cboProgramId);

		lblParentProgramId = new JLabel("부모프로그램");

		y_controlPosition += cboProgramId.getHeight();
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, y_controlPosition, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblParentProgramId);

		cboParentProgramId = new JComboBox<String>();
		cboParentProgramId.setEnabled(false);
		cboParentProgramId.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				cboParentProgramId_selectionChanged();
			}

		});

		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, y_controlPosition,
				LayoutUtil.w_NormalPanelControl, LayoutUtil.h_NormalPanelControl, cboParentProgramId);

		lblDisplaySeq = new JLabel("표시순번");

		y_controlPosition = lblAuthorityGroupId.getY();
		LayoutUtil.layoutComponent(this, LayoutUtil.right_ControlStart_1, y_controlPosition, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblDisplaySeq);

		txtDisplaySeq = new JTextField();
		txtDisplaySeq.setHorizontalAlignment(JTextField.CENTER);
		txtDisplaySeq.setEditable(false);
		LayoutUtil.layoutComponent(this, LayoutUtil.right_ControlStart_2, y_controlPosition,
				LayoutUtil.w_NormalPanelControl, LayoutUtil.h_NormalPanelControl, txtDisplaySeq);

		lblProgramName = new JLabel("프로그램 명");
		y_controlPosition += txtDisplaySeq.getHeight();
		LayoutUtil.layoutComponent(this, LayoutUtil.right_ControlStart_1, y_controlPosition,
				LayoutUtil.w_NormalPanelControl, LayoutUtil.h_NormalPanelControl, lblProgramName);

		txtProgramName = new JTextField("");
		txtProgramName.setEditable(false);
		txtProgramName.setHorizontalAlignment(JTextField.CENTER);
		LayoutUtil.layoutComponent(this, LayoutUtil.right_ControlStart_2, y_controlPosition,
				LayoutUtil.w_NormalPanelControl, LayoutUtil.h_NormalPanelControl, txtProgramName);

		lblProgramPath = new JLabel("프로그램 경로");
		y_controlPosition += txtProgramName.getHeight();
		LayoutUtil.layoutComponent(this, LayoutUtil.right_ControlStart_1, y_controlPosition,
				LayoutUtil.w_NormalPanelControl, LayoutUtil.h_NormalPanelControl, lblProgramPath);

		txtProgramPath = new JTextArea("");
		txtProgramPath.setLineWrap(true);
		scProgramPath = new JScrollPane(txtProgramPath);
		scProgramPath.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scProgramPath.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		txtProgramPath.setEditable(false);
		;
		LayoutUtil.layoutComponent(this, LayoutUtil.right_ControlStart_2, y_controlPosition,
				LayoutUtil.w_NormalPanelControl, LayoutUtil.h_TallPanelControl, scProgramPath);

		btnRegisterAuthorityGroupProgram = new JButton("등록");
		btnRegisterAuthorityGroupProgram.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					btnRegisterAuthorityGroupProgram_click();
				} catch (Exception ex) {
					MessageBoxUtil.showExceptionMessageDialog(MainFrame.getInstance(), ex);
				}
			}

		});

		y_controlPosition = LayoutUtil.y_BottomButton;
		LayoutUtil.layoutComponent(this, LayoutUtil.x_RightButton_2, y_controlPosition, LayoutUtil.w_NormalButton,
				LayoutUtil.h_NormalPanelControl, btnRegisterAuthorityGroupProgram);

		btnUnregisterAuthorityGroupProgram = new JButton("삭제");
		btnUnregisterAuthorityGroupProgram.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					btnUnregisterAuthorityGroup_click();
				} catch (Exception ex) {
					MessageBoxUtil.showExceptionMessageDialog(MainFrame.getInstance(), ex);
				}
			}

		});

		LayoutUtil.layoutComponent(this, LayoutUtil.x_RightButton_1, y_controlPosition, LayoutUtil.w_NormalButton,
				LayoutUtil.h_NormalPanelControl, btnUnregisterAuthorityGroupProgram);

		try {
			viewProgramList();
			viewParentProgramList();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	void btnViewAuthorityGroupList_Click() throws Exception {
		JsonObject jReply = AuthorityGroup.getInstance().viewAuthorityGroupList(this.rpcClient, this.connectionInfo,
				this.loginUserInfo.get("SYSTEM_CODE").getAsString(), "");

		if (jReply.get("resultCode").getAsString().equals(BrunnerClientApi.resultCode_success)) {
			DefaultTableModel modelAuthorityGroupList = (DefaultTableModel) tblAuthorityGroupList.getModel();
			tblAuthorityGroupList.clearSelection();

			while (modelAuthorityGroupList.getRowCount() > 0)
				modelAuthorityGroupList.removeRow(0);

			if (jReply.get("resultCode").getAsString().equals(BrunnerClientApi.resultCode_success)) {
				for (int rowIndex = 0; rowIndex < jReply.get("authorityGroupList").getAsJsonArray()
						.size(); rowIndex++) {
					JsonObject jRowData = (JsonObject) jReply.get("authorityGroupList").getAsJsonArray().get(rowIndex);

					modelAuthorityGroupList.addRow(new Object[] { jRowData.get("authorityGroupId").getAsString(),
							jRowData.get("authorityGroupName").getAsString() });
				}
			} else {
				MessageBoxUtil.showErrorMessageDialog(this, jReply.get("resultMessage").getAsString());
			}

			while (tblAuthorityGroupProgramList.getRowCount() > 0)
				((DefaultTableModel) tblAuthorityGroupProgramList.getModel()).removeRow(0);

			clearForm();
		} else {
			MessageBoxUtil.showErrorMessageDialog(this, jReply.get("resultMessage").getAsString());
		}
	}

	void tblAuthorityGroupList_rowSelected(int row) throws Exception {
		String authorityGroupId = (String) tblAuthorityGroupList.getModel().getValueAt(row, 0);
		String authorityGroupName = (String) tblAuthorityGroupList.getModel().getValueAt(row, 1);

		txtAuthorityGroupId.setText(authorityGroupId);
		txtAuthorityGroupName.setText(authorityGroupName);

		viewAuthorityGroupProgramList();
	}

	void viewAuthorityGroupProgramList() throws Exception {
		JsonObject jReply = AuthorityGroupProgram.getInstance().viewAuthorityGroupProgramList(this.rpcClient,
				this.connectionInfo, this.loginUserInfo.get("SYSTEM_CODE").getAsString(),
				tblAuthorityGroupList.getValueAt(tblAuthorityGroupList.getSelectedRow(), 0).toString(), "");

		if (jReply.get("resultCode").getAsString().equals(BrunnerClientApi.resultCode_success)) {
			while (tblAuthorityGroupProgramList.getRowCount() > 0)
				((DefaultTableModel) tblAuthorityGroupProgramList.getModel()).removeRow(0);

			DefaultTableModel modelAuthorityGroupProgramList = (DefaultTableModel) tblAuthorityGroupProgramList
					.getModel();
			while (modelAuthorityGroupProgramList.getRowCount() > 0)
				modelAuthorityGroupProgramList.removeRow(0);

			if (jReply.get("resultCode").getAsString().equals(BrunnerClientApi.resultCode_success)) {
				for (int rowIndex = 0; rowIndex < jReply.get("authorityGroupProgramList").getAsJsonArray()
						.size(); rowIndex++) {
					JsonObject jRowData = (JsonObject) jReply.get("authorityGroupProgramList").getAsJsonArray()
							.get(rowIndex);
					modelAuthorityGroupProgramList.addRow(new Object[] {
							jRowData.get("programId").getAsString(),
							jRowData.get("parentProgramId").getAsString(),
							String.format("%d", jRowData.get("displaySeq").getAsInt()),
							jRowData.get("programName").getAsString(),
							jRowData.get("programClassPath").getAsString(),
					});
				}

				cboProgramId.setSelectedIndex(-1);
				cboParentProgramId.setSelectedIndex(-1);
				txtDisplaySeq.setText("");
				txtProgramName.setText("");
				txtProgramPath.setText("");
			}
			cboProgramId.setSelectedIndex(-1);
		} else {
			MessageBoxUtil.showErrorMessageDialog(this, jReply.get("resultMessage").getAsString());
		}
	}

	void tblProgramList_rowSelected(int row) {
		cboProgramId.setSelectedItem(
				tblAuthorityGroupProgramList.getValueAt(tblAuthorityGroupProgramList.getSelectedRow(), 0));
		cboParentProgramId.setSelectedItem(
				tblAuthorityGroupProgramList.getValueAt(tblAuthorityGroupProgramList.getSelectedRow(), 1));
		txtDisplaySeq.setText(
				tblAuthorityGroupProgramList.getValueAt(tblAuthorityGroupProgramList.getSelectedRow(), 2).toString());
		txtProgramName.setText(
				tblAuthorityGroupProgramList.getValueAt(tblAuthorityGroupProgramList.getSelectedRow(), 3).toString());
		txtProgramPath.setText(
				tblAuthorityGroupProgramList.getValueAt(tblAuthorityGroupProgramList.getSelectedRow(), 4).toString());
	}

	void btnRegisterAuthorityGroupProgram_click() throws Exception {
		if (txtAuthorityGroupId.getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "권한그룹 아이디를 입력하세요.");
			txtAuthorityGroupId.requestFocus();
			return;
		}

		if (cboProgramId.getSelectedIndex() <= 0) {
			JOptionPane.showMessageDialog(this, "프로그램 아이디를 입력하세요.");
			cboProgramId.requestFocus();
			return;
		}

		JsonObject jReply = AuthorityGroupProgram.getInstance().registerAuthorityGroupProgram(this.rpcClient,
				this.connectionInfo, this.loginUserInfo.get("SYSTEM_CODE").getAsString(), txtAuthorityGroupId.getText(),
				cboProgramId.getSelectedItem().toString(), this.loginUserInfo.get("USER_ID").getAsString());

		if (jReply.get(BrunnerClientApi.msgFieldName_resultCode).getAsString()
				.equals(BrunnerClientApi.resultCode_success)) {
			tblAuthorityGroupList_rowSelected(tblAuthorityGroupList.getSelectedRow());
		} else
			MessageBoxUtil.showErrorMessageDialog(MainFrame.getInstance(), jReply.get(BrunnerClientApi.msgFieldName_resultMessage).getAsString());
	}

	void btnUnregisterAuthorityGroup_click() throws Exception {
		if (txtAuthorityGroupId.getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "권한그룹 아이디를 입력하세요.");
			txtAuthorityGroupId.requestFocus();
			return;
		}

		if (cboProgramId.getSelectedIndex() <= 0) {
			JOptionPane.showMessageDialog(this, "프로그램 아이디를 입력하세요.");
			cboProgramId.requestFocus();
			return;
		}

		if (MessageBoxUtil.showConfirmMessageDialog(this, "삭제 하시겠습니끼?") == JOptionPane.YES_OPTION) {

			JsonObject jReply = AuthorityGroupProgram.getInstance().unregisterAuthorityGroupProgram(this.rpcClient,
					this.connectionInfo, this.loginUserInfo.get("SYSTEM_CODE").getAsString(),
					txtAuthorityGroupId.getText(), cboProgramId.getSelectedItem().toString(),
					this.loginUserInfo.get("USER_ID").getAsString());

			if (jReply.get(BrunnerClientApi.msgFieldName_resultCode).getAsString()
					.equals(BrunnerClientApi.resultCode_success)) {
				tblAuthorityGroupList_rowSelected(tblAuthorityGroupList.getSelectedRow());
			} else
				MessageBoxUtil.showErrorMessageDialog(MainFrame.getInstance(), jReply.get(BrunnerClientApi.msgFieldName_resultMessage).getAsString());
		}
	}

	void viewProgramList() throws Exception {
		JsonObject jReply = Program.getInstance().viewProgramList(this.rpcClient, this.connectionInfo,
				this.loginUserInfo.get("SYSTEM_CODE").getAsString(), "");

		if (jReply.get("resultCode").getAsString().equals(BrunnerClientApi.resultCode_success)) {

			cboProgramId.removeAllItems();
			cboProgramId.addItem("");
			tmpProgramList.clear();

			for (int rowIndex = 0; rowIndex < jReply.get("programList").getAsJsonArray().size(); rowIndex++) {
				JsonObject jRowData = (JsonObject) jReply.get("programList").getAsJsonArray().get(rowIndex);
				cboProgramId.addItem(jRowData.get("programId").getAsString());
				tmpProgramList.put(jRowData.get("programId").getAsString(), jRowData);
			}
			clearForm();
		} else {
			MessageBoxUtil.showErrorMessageDialog(this, jReply.get("resultMessage").getAsString());
		}
	}

	void cboProgramId_selectionChanged() {
		if (cboProgramId.getSelectedIndex() == -1 || cboProgramId.getSelectedItem().toString().trim().length() == 0
				|| tmpProgramList == null) {
			txtProgramName.setText("");
			txtProgramPath.setText("");
			return;
		}

		JsonObject selectedProgramInfo = (JsonObject) tmpProgramList.get(cboProgramId.getSelectedItem().toString());

		cboParentProgramId.setSelectedItem(selectedProgramInfo.get("parentProgramId").getAsString());
		txtDisplaySeq.setText(String.format("%d", selectedProgramInfo.get("displaySeq").getAsInt()));
		txtProgramName.setText(selectedProgramInfo.get("programName").getAsString());
		txtProgramPath.setText(selectedProgramInfo.get("programClassPath").getAsString());
	}

	void cboParentProgramId_selectionChanged() {
	}

	void viewParentProgramList() throws Exception {
		JsonObject jReply = Program.getInstance().viewProgramList(this.rpcClient, this.connectionInfo,
				this.loginUserInfo.get("SYSTEM_CODE").getAsString(), "");

		if (jReply.get("resultCode").getAsString().equals(BrunnerClientApi.resultCode_success)) {

			cboParentProgramId.removeAllItems();
			cboParentProgramId.addItem("");
			tmpParentProgramList.clear();

			for (int rowIndex = 0; rowIndex < jReply.get("programList").getAsJsonArray().size(); rowIndex++) {
				JsonObject jRowData = (JsonObject) jReply.get("programList").getAsJsonArray().get(rowIndex);
				cboParentProgramId.addItem(jRowData.get("programId").getAsString());
				tmpParentProgramList.put(jRowData.get("programId").getAsString(), jRowData);
			}
			clearForm();
		} else {
			MessageBoxUtil.showErrorMessageDialog(this, jReply.get("resultMessage").getAsString());
		}
	}

	void clearForm() {
		txtAuthorityGroupId.setText("");
		txtAuthorityGroupName.setText("");
		cboProgramId.setSelectedIndex(-1);
		cboParentProgramId.setSelectedIndex(-1);
		txtDisplaySeq.setText("");
		txtProgramName.setText("");
		txtProgramPath.setText("");
	}
}
