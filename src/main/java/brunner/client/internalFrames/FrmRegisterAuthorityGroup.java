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
import java.util.concurrent.TimeoutException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
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
import brunner.client.api.AuthorityGroup;
import brunner.client.api.BrunnerClientApi;
import brunner.client.frames.MainFrame;
import mw.launchers.RPCClient;

/***
 * 권한그룹관리
 */
public class FrmRegisterAuthorityGroup extends FrmBrunnerInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel lblAuthorityGroupList;
	JBrunnerTable tblAuthorityGroupList;
	JScrollPane scCommonCodeGroup;
	JButton btnViewAuthorityGroupList;

	JLabel lblAuthorityGroupId;
	JTextField txtAuthorityGroupId;
	JLabel lblAuthorityGroupName;
	JTextField txtAuthorityGroupName;

	JButton btnRegisterAuthorityGroup;
	JButton btnUnregisterAuthorityGroup;

	public FrmRegisterAuthorityGroup(RPCClient rpcClient, JsonObject connectionInfo, JsonObject loginUserInfo,
			String title, int width, int height, ImageIcon icon) {

		super(rpcClient, connectionInfo, loginUserInfo, title, width, height, icon);
	}

	protected void initLayout(int width, int height) {
		super.initLayout(width, height);

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

		tblAuthorityGroupList = JBrunnerTable.getNewInstance(lblAuthorityGroupList.getText());

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

		scCommonCodeGroup = new JScrollPane(tblAuthorityGroupList);

		y_controlPosition += btnViewAuthorityGroupList.getHeight();
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, y_controlPosition, LayoutUtil.w_fullWidthControl,
				LayoutUtil.h_NormalTable, scCommonCodeGroup);

		lblAuthorityGroupId = new JLabel("권한그룹아이디");

		y_controlPosition += scCommonCodeGroup.getHeight();
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, y_controlPosition, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblAuthorityGroupId);

		txtAuthorityGroupId = new JTextField();
		txtAuthorityGroupId.setHorizontalAlignment(JTextField.CENTER);
		txtAuthorityGroupId.setEditable(true);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, y_controlPosition,
				LayoutUtil.w_NormalPanelControl, LayoutUtil.h_NormalPanelControl, txtAuthorityGroupId);

		lblAuthorityGroupName = new JLabel("권한그룹명");

		y_controlPosition += txtAuthorityGroupId.getHeight();
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, y_controlPosition,
				LayoutUtil.w_NormalPanelControl, LayoutUtil.h_NormalPanelControl,
				lblAuthorityGroupName);

		txtAuthorityGroupName = new JTextField("");
		txtAuthorityGroupName.setHorizontalAlignment(JTextField.CENTER);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, y_controlPosition,
				LayoutUtil.w_NormalPanelControl, LayoutUtil.h_NormalPanelControl, txtAuthorityGroupName);

		btnRegisterAuthorityGroup = new JButton("등록");
		btnRegisterAuthorityGroup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					btnRegisterAuthorityGroup_click();
				} catch (Exception ex) {
					MessageBoxUtil.showExceptionMessageDialog(MainFrame.getInstance(), ex);
				}
			}

		});

		y_controlPosition = LayoutUtil.y_BottomButton;
		LayoutUtil.layoutComponent(this, LayoutUtil.x_RightButton_2, y_controlPosition, LayoutUtil.w_NormalButton,
				LayoutUtil.h_NormalPanelControl, btnRegisterAuthorityGroup);

		btnUnregisterAuthorityGroup = new JButton("삭제");
		btnUnregisterAuthorityGroup.addActionListener(new ActionListener() {
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
				LayoutUtil.h_NormalPanelControl, btnUnregisterAuthorityGroup);
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

			clearForm();
		} else {
			MessageBoxUtil.showErrorMessageDialog(this, jReply.get("resultMessage").getAsString());
		}
	}

	void tblAuthorityGroupList_rowSelected(int row)
			throws InterruptedException, TimeoutException, JsonSyntaxException, IOException, URISyntaxException {
		String authorityGroupId = (String) tblAuthorityGroupList.getModel().getValueAt(row, 0);
		String authorityGroupName = (String) tblAuthorityGroupList.getModel().getValueAt(row, 1);

		txtAuthorityGroupId.setText(authorityGroupId);
		txtAuthorityGroupName.setText(authorityGroupName);
	}

	void btnRegisterAuthorityGroup_click() throws Exception {

		if (txtAuthorityGroupId.getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "권한그룹 아이디를 입력하세요.");
			txtAuthorityGroupId.requestFocus();
			return;
		}

		JsonObject jReply = AuthorityGroup.getInstance().registerAuthorityGroup(this.rpcClient, this.connectionInfo,
				this.loginUserInfo.get("SYSTEM_CODE").getAsString(), this.loginUserInfo.get("USER_ID").getAsString(),
				txtAuthorityGroupId.getText(), txtAuthorityGroupName.getText());

		if (jReply.get(BrunnerClientApi.msgFieldName_resultCode).getAsString()
				.equals(BrunnerClientApi.resultCode_success)) {
			btnViewAuthorityGroupList_Click();
		} else
			MessageBoxUtil.showErrorMessageDialog(MainFrame.getInstance(),
					jReply.get(BrunnerClientApi.msgFieldName_resultMessage).getAsString());
	}

	void btnUnregisterAuthorityGroup_click() throws Exception {

		if (txtAuthorityGroupId.getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "권한그룹 아이디를 입력하세요.");
			txtAuthorityGroupId.requestFocus();
			return;
		}

		if (MessageBoxUtil.showConfirmMessageDialog(this, "삭제 하시겠습니끼?") == JOptionPane.YES_OPTION) {
			JsonObject jReply = AuthorityGroup.getInstance().unregisterAuthorityGroup(this.rpcClient,
					this.connectionInfo, this.loginUserInfo.get("SYSTEM_CODE").getAsString(),
					this.loginUserInfo.get("USER_ID").getAsString(), txtAuthorityGroupId.getText());

			if (jReply.get(BrunnerClientApi.msgFieldName_resultCode).getAsString()
					.equals(BrunnerClientApi.resultCode_success)) {
				btnViewAuthorityGroupList_Click();
			} else
				MessageBoxUtil.showErrorMessageDialog(MainFrame.getInstance(),
						jReply.get(BrunnerClientApi.msgFieldName_resultMessage).getAsString());
		}
	}

	void clearForm() {
		txtAuthorityGroupId.setText("");
		txtAuthorityGroupName.setText("");
	}
}
