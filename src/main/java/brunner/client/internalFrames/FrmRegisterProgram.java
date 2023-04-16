package brunner.client.internalFrames;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import brunner.client.BrunnerTableModel;
import brunner.client.FrmBrunnerInternalFrame;
import brunner.client.JBrunnerTable;
import brunner.client.LayoutUtil;
import brunner.client.MessageBoxUtil;
import brunner.client.api.BrunnerClientApi;
import brunner.client.api.Program;
import brunner.client.frames.MainFrame;
import mw.launchers.RPCClient;

/***
 * 프로그램관리
 */
public class FrmRegisterProgram extends FrmBrunnerInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel lblProgramList;
	JBrunnerTable tblProgramList;
	JScrollPane scProgramList;
	JButton btnViewProgramList;

	JLabel lblProgramId;
	JTextField txtProgramId;
	JLabel lblParentProgramId;
	JComboBox<String> cboParentProgramId;
	JLabel lblDisplaySeq;
	JTextField txtDisplaySeq;

	JLabel lblProgramName;
	JTextField txtProgramName;
	JLabel lblProgramPath;
	JTextArea txtProgramPath;
	JScrollPane scProgramPath;

	JButton btnRegisterProgram;
	JButton btnUnregisterProgram;

	HashMap<String, JsonObject> tmpParentProgramList;

	public FrmRegisterProgram(RPCClient rpcClient, JsonObject connectionInfo, JsonObject loginUserInfo, String title,
			int width, int height, ImageIcon icon) {

		super(rpcClient, connectionInfo, loginUserInfo, title, width, height, icon);
	}

	protected void initLayout(int width, int height) {
		super.initLayout(width, height);

		tmpParentProgramList = new HashMap<String, JsonObject>();

		setLayout(null);

		int y_controlPosition = LayoutUtil.y_ControlStart;

		lblProgramList = new JLabel("프로그램 목록");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, y_controlPosition, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblProgramList);

		btnViewProgramList = new JButton("조회");
		btnViewProgramList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					btnViewProgramList_Click();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		LayoutUtil.layoutComponent(this, LayoutUtil.x_RightButton_1, y_controlPosition, LayoutUtil.w_NormalButton,
				LayoutUtil.h_NormalPanelControl, btnViewProgramList);

		String groupHeaders[] = { "프로그렘아이디", "부모프로그램", "표시순번", "프로그램명", "프로그램 경로" };
		String groupContents[][] = {};

		tblProgramList = JBrunnerTable.getNewInstance(lblProgramList.getText());

		tblProgramList.setModel(new BrunnerTableModel(groupContents, groupHeaders, false));
		tblProgramList.setShowHorizontalLines(true);
		tblProgramList.setGridColor(Color.GRAY);
		tblProgramList.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
					int row = tblProgramList.getSelectedRow();

					try {
						tblProgramList_rowSelected(row);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		tblProgramList.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				int row = tblProgramList.getSelectedRow();

				try {
					tblProgramList_rowSelected(row);
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

		for (int colIndex = 0; colIndex < tblProgramList.getColumnCount(); colIndex++)
			tblProgramList.getColumnModel().getColumn(colIndex)
					.setCellRenderer(JBrunnerTable.getDefaultTableCellRenderer());

		((DefaultTableCellRenderer) tblProgramList.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(JLabel.CENTER);

		scProgramList = new JScrollPane(tblProgramList);
		y_controlPosition += btnViewProgramList.getHeight();
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, y_controlPosition, LayoutUtil.w_fullWidthControl,
				LayoutUtil.h_NormalTable, scProgramList);

		lblProgramId = new JLabel("프로그램아이디");

		y_controlPosition += scProgramList.getHeight();
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, y_controlPosition, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblProgramId);

		txtProgramId = new JTextField();
		txtProgramId.setHorizontalAlignment(JTextField.CENTER);
		txtProgramId.setEditable(true);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, y_controlPosition,
				LayoutUtil.w_NormalPanelControl, LayoutUtil.h_NormalPanelControl, txtProgramId);

		lblParentProgramId = new JLabel("부모프로그램");

		y_controlPosition += txtProgramId.getHeight();
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, y_controlPosition, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblParentProgramId);

		cboParentProgramId = new JComboBox<String>();
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, y_controlPosition,
				LayoutUtil.w_NormalPanelControl, LayoutUtil.h_NormalPanelControl, cboParentProgramId);

		lblDisplaySeq = new JLabel("표시순번");

		y_controlPosition += cboParentProgramId.getHeight();
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, y_controlPosition,
				LayoutUtil.w_NormalPanelControl, LayoutUtil.h_NormalPanelControl, lblDisplaySeq);

		txtDisplaySeq = new JTextField("");
		txtDisplaySeq.setHorizontalAlignment(JTextField.CENTER);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, y_controlPosition,
				LayoutUtil.w_NormalPanelControl, LayoutUtil.h_NormalPanelControl, txtDisplaySeq);

		lblProgramName = new JLabel("프로그램 명");

		y_controlPosition += txtDisplaySeq.getHeight();
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, y_controlPosition,
				LayoutUtil.w_NormalPanelControl, LayoutUtil.h_NormalPanelControl, lblProgramName);

		txtProgramName = new JTextField("");
		txtProgramName.setHorizontalAlignment(JTextField.CENTER);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, y_controlPosition,
				LayoutUtil.w_NormalPanelControl, LayoutUtil.h_NormalPanelControl, txtProgramName);

		lblProgramPath = new JLabel("프로그램 경로");

		y_controlPosition += txtProgramName.getHeight();
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, y_controlPosition,
				LayoutUtil.w_NormalPanelControl, LayoutUtil.h_NormalPanelControl, lblProgramPath);

		txtProgramPath = new JTextArea("");
		txtProgramPath.setLineWrap(true);
		scProgramPath = new JScrollPane(txtProgramPath);
		scProgramPath.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scProgramPath.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, y_controlPosition,
				LayoutUtil.w_NormalPanelControl, LayoutUtil.h_TallestPanelControl, scProgramPath);

		btnRegisterProgram = new JButton("등록");
		btnRegisterProgram.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					btnRegisterProgram_click();
					viewParentProgramList();
				} catch (Exception ex) {
					MessageBoxUtil.showExceptionMessageDialog(MainFrame.getInstance(), ex);
				}
			}

		});

		y_controlPosition = LayoutUtil.y_BottomButton;
		LayoutUtil.layoutComponent(this, LayoutUtil.x_RightButton_2, y_controlPosition, LayoutUtil.w_NormalButton,
				LayoutUtil.h_NormalPanelControl, btnRegisterProgram);

		btnUnregisterProgram = new JButton("삭제");
		btnUnregisterProgram.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					btnUnregisterProgram_click();
					viewParentProgramList();
				} catch (Exception ex) {
					MessageBoxUtil.showExceptionMessageDialog(MainFrame.getInstance(), ex);
				}
			}

		});

		LayoutUtil.layoutComponent(this, LayoutUtil.x_RightButton_1, LayoutUtil.y_BottomButton,
				LayoutUtil.w_NormalButton, LayoutUtil.h_NormalPanelControl, btnUnregisterProgram);
		try {
			viewParentProgramList();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	void btnViewProgramList_Click() throws Exception {
		JsonObject jReply = Program.getInstance().viewProgramList(this.rpcClient, this.connectionInfo,
				this.loginUserInfo.get("SYSTEM_CODE").getAsString(), "");

		if (jReply.get("resultCode").getAsString().equals(BrunnerClientApi.resultCode_success)) {
			DefaultTableModel modelCodeGroupList = (DefaultTableModel) tblProgramList.getModel();
			tblProgramList.clearSelection();

			while (modelCodeGroupList.getRowCount() > 0)
				modelCodeGroupList.removeRow(0);

			for (int rowIndex = 0; rowIndex < jReply.get("programList").getAsJsonArray().size(); rowIndex++) {
				JsonObject jRowData = (JsonObject) jReply.get("programList").getAsJsonArray().get(rowIndex);

				modelCodeGroupList.addRow(new Object[] {
						jRowData.get("programId").getAsString(),
						jRowData.get("parentProgramId").getAsString(),
						String.format("%d", jRowData.get("displaySeq").getAsInt()),
						jRowData.get("programName").getAsString(),
						jRowData.get("programClassPath").getAsString()
				});
			}
			clearForm();
		} else {
			MessageBoxUtil.showErrorMessageDialog(this, jReply.get("resultMessage").getAsString());
		}
	}

	void tblProgramList_rowSelected(int row)
			throws InterruptedException, TimeoutException, JsonSyntaxException, IOException, URISyntaxException {
		String programId = (String) tblProgramList.getModel().getValueAt(row, 0);
		String parentProgramId = (String) tblProgramList.getModel().getValueAt(row, 1);
		String displaySeq = (String) tblProgramList.getModel().getValueAt(row, 2);
		String programName = (String) tblProgramList.getModel().getValueAt(row, 3);
		String programClassPath = (String) tblProgramList.getModel().getValueAt(row, 4);

		txtProgramId.setText(programId);
		cboParentProgramId.setSelectedItem(parentProgramId);
		txtDisplaySeq.setText(displaySeq);
		txtProgramName.setText(programName);
		txtProgramPath.setText(programClassPath);
	}

	void btnRegisterProgram_click() throws Exception {

		if (txtProgramId.getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "프로그램 아이디를 입력하세요.");
			txtProgramId.requestFocus();
			return;
		}

		JsonObject jReply = Program.getInstance().registerProgram(this.rpcClient, this.connectionInfo,
				this.loginUserInfo.get("SYSTEM_CODE").getAsString(), this.loginUserInfo.get("USER_ID").getAsString(),
				txtProgramId.getText(), cboParentProgramId.getSelectedItem().toString(),
				Integer.parseInt(txtDisplaySeq.getText()), txtProgramName.getText(), txtProgramPath.getText());

		if (jReply.get(BrunnerClientApi.msgFieldName_resultCode).getAsString()
				.equals(BrunnerClientApi.resultCode_success)) {
			btnViewProgramList_Click();
		} else
			MessageBoxUtil.showErrorMessageDialog(MainFrame.getInstance(),
					jReply.get(BrunnerClientApi.msgFieldName_resultMessage).getAsString());
	}

	void btnUnregisterProgram_click() throws Exception {

		if (txtProgramId.getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "프로그램 아이디를 입력하세요.");
			txtProgramId.requestFocus();
			return;
		}

		if (MessageBoxUtil.showConfirmMessageDialog(this, "삭제 하시겠습니끼?") == JOptionPane.YES_OPTION) {
			JsonObject jReply = Program.getInstance().unregisterProgram(
					this.rpcClient, this.connectionInfo, this.loginUserInfo.get("SYSTEM_CODE").getAsString(),
					this.loginUserInfo.get("USER_ID").getAsString(), txtProgramId.getText());

			if (jReply.get(BrunnerClientApi.msgFieldName_resultCode).getAsString()
					.equals(BrunnerClientApi.resultCode_success)) {
				btnViewProgramList_Click();
			} else
				MessageBoxUtil.showErrorMessageDialog(MainFrame.getInstance(),
						jReply.get(BrunnerClientApi.msgFieldName_resultMessage).getAsString());
		}
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
		txtProgramId.setText("");
		txtProgramName.setText("");
		cboParentProgramId.setSelectedIndex(-1);
		txtProgramPath.setText("");
		txtDisplaySeq.setText("");
	}
}
