package brunner.client.internalFrames;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.JDatePicker;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import brunner.client.BrunnerTableModel;
import brunner.client.FrmBrunnerInternalFrame;
import brunner.client.JBrunnerTable;
import brunner.client.LayoutUtil;
import brunner.client.MessageBoxUtil;
import brunner.client.api.AuthorityGroup;
import brunner.client.api.BrunnerClientApi;
import brunner.client.api.UserInfo;
import brunner.client.frames.MainFrame;
import mw.launchers.RPCClient;
import mw.utility.StringUtil;

public class FrmRegisterUserAuthorityGroup extends FrmBrunnerInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel lblUserList;
	JBrunnerTable tblUserList;
	JScrollPane scUserList;
	JButton btnViewUserAuthorityGroupList;

	JLabel lblUserId;
	JTextField txtUserId;
	JLabel lblUserName;
	JTextField txtUserName;
	JLabel lblAuthorityGroupId;
	JComboBox<String> cboAuthorityGroupId;
	JLabel lblAuthorityGroupName;
	JTextField txtAuthorityGroupName;
	JLabel lblLicenseExpiredData;
	JDatePicker dtLicenseExpireDate;

	JButton btnRegisterUserAuthorityGroup;

	HashMap<String, JsonObject> tmpAuthorityGroupList;

	public FrmRegisterUserAuthorityGroup(RPCClient rpcClient, JsonObject connectionInfo, JsonObject loginUserInfo,
			String title, int width, int height, ImageIcon icon) {

		super(rpcClient, connectionInfo, loginUserInfo, title, width, height, icon);
	}

	protected void initLayout(int width, int height) {
		super.initLayout(width, height);

		tmpAuthorityGroupList = new HashMap<String, JsonObject>();

		setLayout(null);

		int y_controlPosition = LayoutUtil.y_ControlStart;

		lblUserList = new JLabel("사용자 목록");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, y_controlPosition, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblUserList);

		btnViewUserAuthorityGroupList = new JButton("조회");
		btnViewUserAuthorityGroupList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					btnViewUserAuthorityGroupList_Click();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		LayoutUtil.layoutComponent(this, LayoutUtil.x_RightButton_1, y_controlPosition, LayoutUtil.w_NormalButton,
				LayoutUtil.h_NormalPanelControl, btnViewUserAuthorityGroupList);

		String groupHeaders[] = { "사용자아이디", "사용자이름", "권한그룹아이디", "권한그룹명", "라이센스만료일" };
		String groupContents[][] = {};

		tblUserList = JBrunnerTable.getNewInstance(lblUserList.getText());

		tblUserList.setModel(new BrunnerTableModel(groupContents, groupHeaders, false));
		tblUserList.setShowHorizontalLines(true);
		tblUserList.setGridColor(Color.GRAY);
		tblUserList.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
					int row = tblUserList.getSelectedRow();

					try {
						tblUserList_rowSelected(row);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}

		});
		tblUserList.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				int row = tblUserList.getSelectedRow();

				try {
					tblUserList_rowSelected(row);
				} catch (Exception ex) {
					ex.printStackTrace();
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

		for (int colIndex = 0; colIndex < tblUserList.getColumnCount(); colIndex++)
			tblUserList.getColumnModel().getColumn(colIndex)
					.setCellRenderer(JBrunnerTable.getDefaultTableCellRenderer());

		((DefaultTableCellRenderer) tblUserList.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(JLabel.CENTER);

		y_controlPosition += btnViewUserAuthorityGroupList.getHeight();

		scUserList = new JScrollPane(tblUserList);

		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, y_controlPosition, LayoutUtil.w_fullWidthControl,
				LayoutUtil.h_NormalTable, scUserList);

		y_controlPosition += scUserList.getHeight();

		lblUserId = new JLabel("사용자아이디");

		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, y_controlPosition, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblUserId);

		txtUserId = new JTextField();
		txtUserId.setHorizontalAlignment(JTextField.CENTER);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, y_controlPosition,
				LayoutUtil.w_NormalPanelControl, LayoutUtil.h_NormalPanelControl, txtUserId);

		y_controlPosition += txtUserId.getHeight();

		lblUserName = new JLabel("사용자이름");

		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, y_controlPosition, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblUserName);

		txtUserName = new JTextField("");
		txtUserName.setHorizontalAlignment(JTextField.CENTER);
		txtUserName.setEditable(false);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, y_controlPosition,
				LayoutUtil.w_NormalPanelControl, LayoutUtil.h_NormalPanelControl, txtUserName);

		y_controlPosition += txtUserName.getHeight();

		lblAuthorityGroupId = new JLabel("권한그룹아이디");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, y_controlPosition, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblAuthorityGroupId);

		cboAuthorityGroupId = new JComboBox<String>();
		cboAuthorityGroupId.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				try {
					cboAuthorityGroupId_SelectionChanged();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		});

		cboAuthorityGroupId.setAlignmentX(JTextField.CENTER_ALIGNMENT);
		cboAuthorityGroupId.setEditable(true);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, y_controlPosition,
				LayoutUtil.w_NormalPanelControl, LayoutUtil.h_NormalPanelControl, cboAuthorityGroupId);

		y_controlPosition += cboAuthorityGroupId.getHeight();

		lblAuthorityGroupName = new JLabel("권한그룹이름");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, y_controlPosition, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblAuthorityGroupName);

		txtAuthorityGroupName = new JTextField("");
		txtAuthorityGroupName.setEditable(false);
		txtAuthorityGroupName.setHorizontalAlignment(JTextField.CENTER);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, y_controlPosition,
				LayoutUtil.w_NormalPanelControl, LayoutUtil.h_NormalPanelControl, txtAuthorityGroupName);

		y_controlPosition += txtAuthorityGroupName.getHeight();
		lblLicenseExpiredData = new JLabel("라이센스 만료일");

		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, y_controlPosition, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblLicenseExpiredData);

		dtLicenseExpireDate = new JDatePicker();
		dtLicenseExpireDate.getModel().setDate(Calendar.getInstance().get(Calendar.YEAR),
				Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DATE));
		dtLicenseExpireDate.getModel().setSelected(true);
		dtLicenseExpireDate.setAlignmentX(JTextField.CENTER_ALIGNMENT);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, y_controlPosition, LayoutUtil.w_datePicker,
				LayoutUtil.h_datePicker, dtLicenseExpireDate);

		y_controlPosition = LayoutUtil.y_BottomButton;

		btnRegisterUserAuthorityGroup = new JButton("등록");
		btnRegisterUserAuthorityGroup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					btnRegisterUserAuthorityGroup_click();
				} catch (Exception ex) {
					MessageBoxUtil.showExceptionMessageDialog(MainFrame.getInstance(), ex);
				}
			}

		});

		LayoutUtil.layoutComponent(this, LayoutUtil.x_RightButton_1, y_controlPosition, LayoutUtil.w_NormalButton,
				LayoutUtil.h_NormalPanelControl, btnRegisterUserAuthorityGroup);

		try {
			viewAuthorityGroupList();
		} catch (Exception e) {
			MessageBoxUtil.showExceptionMessageDialog(this, e);
		}
	}

	void btnViewUserAuthorityGroupList_Click() throws Exception {
		JsonObject jReply = UserInfo.getInstance().viewUserAuthorityGroupList(this.rpcClient, this.connectionInfo,
				this.loginUserInfo.get("SYSTEM_CODE").getAsString(), "");

		if (jReply.get("resultCode").getAsString().equals(BrunnerClientApi.resultCode_success)) {
			DefaultTableModel modelCodeGroupList = (DefaultTableModel) tblUserList.getModel();
			tblUserList.clearSelection();

			while (modelCodeGroupList.getRowCount() > 0)
				modelCodeGroupList.removeRow(0);

			for (int rowIndex = 0; rowIndex < jReply.get("userList").getAsJsonArray().size(); rowIndex++) {
				JsonObject jRowData = (JsonObject) jReply.get("userList").getAsJsonArray().get(rowIndex);

				modelCodeGroupList.addRow(new Object[] {
						jRowData.get("userId").getAsString(),
						jRowData.get("userName").getAsString(),
						jRowData.get("authorityGroupId").getAsString(),
						jRowData.get("authorityGroupName").getAsString(),
						jRowData.get("licenseExpireDate").getAsString(),
				});
			}
			clearForm();
		} else {
			MessageBoxUtil.showErrorMessageDialog(this, jReply.get("resultMessage").getAsString());
		}
	}

	void tblUserList_rowSelected(int row)
			throws InterruptedException, TimeoutException, JsonSyntaxException, IOException, URISyntaxException {
		String userId = (String) tblUserList.getModel().getValueAt(row, 0);
		String userName = (String) tblUserList.getModel().getValueAt(row, 1);
		String authorityGroupId = (String) tblUserList.getModel().getValueAt(row, 2);
		String authorityGroupName = (String) tblUserList.getModel().getValueAt(row, 3);
		Date licenseExpireDate = null;

		licenseExpireDate = new Date();

		try {
			licenseExpireDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.parse((String) tblUserList.getModel().getValueAt(row, 4));
		} catch (Exception e) {
			e.printStackTrace();
		}

		txtUserId.setText(userId);
		txtUserName.setText(userName);
		cboAuthorityGroupId.setSelectedItem(authorityGroupId);
		txtAuthorityGroupName.setText(authorityGroupName);

		Calendar cal = Calendar.getInstance();
		cal.setTime(licenseExpireDate);

		dtLicenseExpireDate.getModel().setYear(cal.get(Calendar.YEAR));
		dtLicenseExpireDate.getModel().setMonth(cal.get(Calendar.MONTH));
		dtLicenseExpireDate.getModel().setDay(cal.get(Calendar.DATE));
	}

	void btnRegisterUserAuthorityGroup_click() throws Exception {
		if (txtUserId.getText().trim().length() == 0) {
			MessageBoxUtil.showErrorMessageDialog(this, "사용자아이디를 입력하세요.");
			txtUserId.requestFocus();
			return;
		}

		if (cboAuthorityGroupId.getSelectedItem() == null
				|| cboAuthorityGroupId.getSelectedItem().toString().trim().length() == 0) {
			MessageBoxUtil.showErrorMessageDialog(this, "권한그룹아이디를 입력하세요.");
			cboAuthorityGroupId.requestFocus();
			return;
		}

		JsonObject jReply = UserInfo.getInstance().registerUserAuthorityGroup(this.rpcClient, this.connectionInfo,
				this.loginUserInfo.get("SYSTEM_CODE").getAsString(), txtUserId.getText().toString(),
				cboAuthorityGroupId.getSelectedItem().toString(),
				((GregorianCalendar) dtLicenseExpireDate.getModel().getValue()).getTime(),
				this.loginUserInfo.get("USER_ID").getAsString());

		if (jReply.get(BrunnerClientApi.msgFieldName_resultCode).getAsString()
				.equals(BrunnerClientApi.resultCode_success)) {
			btnViewUserAuthorityGroupList_Click();
		} else
			MessageBoxUtil.showErrorMessageDialog(MainFrame.getInstance(),
					jReply.get(BrunnerClientApi.msgFieldName_resultMessage).getAsString());
	}

	void viewAuthorityGroupList() throws Exception {
		JsonObject jReply = AuthorityGroup.getInstance().viewAuthorityGroupList(this.rpcClient, this.connectionInfo,
				this.loginUserInfo.get("SYSTEM_CODE").getAsString(), "");

		if (jReply.get("resultCode").getAsString().equals(BrunnerClientApi.resultCode_success)) {
			cboAuthorityGroupId.addItem("");

			for (int rowIndex = 0; rowIndex < jReply.get("authorityGroupList").getAsJsonArray().size(); rowIndex++) {
				JsonObject jRowData = (JsonObject) jReply.get("authorityGroupList").getAsJsonArray().get(rowIndex);
				cboAuthorityGroupId.addItem(jRowData.get("authorityGroupId").getAsString());

				tmpAuthorityGroupList.put(jRowData.get("authorityGroupId").getAsString(), jRowData);
			}
		} else {
			MessageBoxUtil.showErrorMessageDialog(this, jReply.get("resultMessage").getAsString());
		}
	}

	void cboAuthorityGroupId_SelectionChanged()
			throws JsonSyntaxException, IOException, URISyntaxException, InterruptedException, TimeoutException {
		String authorityGroupId = (String) cboAuthorityGroupId.getSelectedItem();

		if (StringUtil.isNullOrEmpty(authorityGroupId) == true)
			return;

		JsonObject authorityGroupInfo = (JsonObject) tmpAuthorityGroupList.get(authorityGroupId);
		txtAuthorityGroupName.setText(authorityGroupInfo.get("authorityGroupName").getAsString());
	}

	void clearForm() {
		txtUserId.setText("");
		txtUserName.setText("");
		cboAuthorityGroupId.setSelectedIndex(-1);
		txtAuthorityGroupName.setText("");
	}
}
