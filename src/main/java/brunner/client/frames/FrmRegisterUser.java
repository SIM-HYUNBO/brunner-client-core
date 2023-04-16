package brunner.client.frames;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import com.google.gson.JsonObject;

import brunner.UIUtil;
import brunner.client.LayoutUtil;
import brunner.client.MessageBoxUtil;
import brunner.client.PropertyUtil;
import brunner.client.api.BrunnerClientApi;
import brunner.client.api.CommonCode;
import brunner.client.api.UserInfo;
import mw.utility.ExceptionUtil;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/***
 * 사용자정보관리
 */
public class FrmRegisterUser extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel lblUserId;
	JTextField txtUserId;
	JLabel lblUserPassword;
	JPasswordField txtUserPassword;
	JLabel lblUserName;
	JTextField txtUserName;
	JLabel lblRegisterName;
	JTextField txtRegisterName;
	JLabel lblRegisterNumber;
	JTextField txtRegisterNumber;
	JLabel lblSalesType;
	JComboBox<String> cboSalesType;
	JLabel lblSalesCategory;
	JComboBox<String> cboSalesCategory;
	JLabel lblAddress;
	JTextArea txtAddress;
	JScrollPane scAddress;
	JLabel lblPhoneNumber;
	JTextField txtPhoneNumber;
	JLabel lblEMailId;
	JTextField txtEMailId;

	JLabel lblUserLicenseKey;
	JTextField txtUserLicenseKey;

	JButton btnOK;
	JButton btnCancel;
	FrmBrunnerLogin loginForm;
	boolean isReadOnly;

	public FrmRegisterUser(MainFrame mainForm, FrmBrunnerLogin loginForm, boolean isReadOnly) {
		super(mainForm, isReadOnly == false ? "회원 가입" : "사용자 프로필", Dialog.ModalityType.DOCUMENT_MODAL);
		this.loginForm = loginForm;

		try {
			this.isReadOnly = isReadOnly;
			initLayout();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, ExceptionUtil.getFullMessage(e),
					"Exception", JOptionPane.ERROR_MESSAGE);
		}
	}

	void initLayout() throws Exception {
		this.setSize(410, 550);
		this.setLocationRelativeTo(null);

		setLayout(null);
		lblUserId = new JLabel("로그인아이디");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 30, 200, 25, lblUserId);
		txtUserId = new JTextField("");
		txtUserId.setHorizontalAlignment(JTextField.CENTER);
		txtUserId.setEditable(isReadOnly == false);
		if (isReadOnly)
			txtUserId.setText(loginForm.getLoginUserInfo().get("USER_ID").getAsString());
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, 30, 250, 25, txtUserId);

		lblUserPassword = new JLabel("비밀번호");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 60, 200, 25, lblUserPassword);
		txtUserPassword = new JPasswordField("");
		txtUserPassword.setHorizontalAlignment(JTextField.CENTER);
		txtUserPassword.setEditable(isReadOnly == false);
		if (isReadOnly)
			txtUserPassword.setText(loginForm.getLoginUserInfo().get("PASSWORD").getAsString());
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, 60, 250, 25, txtUserPassword);

		lblUserName = new JLabel("이름");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 90, 250, 25, lblUserName);
		txtUserName = new JTextField("");
		txtUserName.setHorizontalAlignment(JTextField.CENTER);
		txtUserName.setEditable(isReadOnly == false);
		if (isReadOnly)
			txtUserName.setText(loginForm.getLoginUserInfo().get("USER_NAME").getAsString());
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, 90, 250, 25, txtUserName);

		lblRegisterName = new JLabel("상호명");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 120, 250, 25, lblRegisterName);
		txtRegisterName = new JTextField("");
		txtRegisterName.setHorizontalAlignment(JTextField.CENTER);
		txtRegisterName.setEditable(isReadOnly == false);
		if (isReadOnly)
			txtRegisterName.setText(loginForm.getLoginUserInfo().get("REGISTER_NAME").getAsString());
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, 120, 250, 25, txtRegisterName);

		lblRegisterNumber = new JLabel("사업자등록번호");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 150, 250, 25, lblRegisterNumber);
		txtRegisterNumber = new JTextField("");
		txtRegisterNumber.setHorizontalAlignment(JTextField.CENTER);
		txtRegisterNumber.setEditable(isReadOnly == false);
		if (isReadOnly)
			txtRegisterNumber.setText(loginForm.getLoginUserInfo().get("REGISTER_NO").getAsString());
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, 150, 250, 25, txtRegisterNumber);

		lblSalesType = new JLabel("업태");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 180, 250, 25, lblSalesType);
		cboSalesType = new JComboBox<String>();
		cboSalesType.setAlignmentX(JComboBox.CENTER_ALIGNMENT);
		cboSalesType.setEnabled(isReadOnly == false);
		if (isReadOnly) {
			cboSalesType.addItem(loginForm.getLoginUserInfo().get("SALES_TYPE").getAsString());
			cboSalesType.setSelectedItem(loginForm.getLoginUserInfo().get("SALES_TYPE").getAsString());
		}
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, 180, 250, 25, cboSalesType);

		lblSalesCategory = new JLabel("업종");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 210, 250, 25, lblSalesCategory);
		cboSalesCategory = new JComboBox<String>();
		cboSalesCategory.setAlignmentX(JComboBox.CENTER_ALIGNMENT);
		cboSalesCategory.setEnabled(isReadOnly == false);
		if (isReadOnly) {
			cboSalesCategory.addItem(loginForm.getLoginUserInfo().get("SALES_CATEGORY").getAsString());
			cboSalesCategory.setSelectedItem(loginForm.getLoginUserInfo().get("SALES_CATEGORY").getAsString());
		}
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, 210, 250, 25, cboSalesCategory);

		lblAddress = new JLabel("주소");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 240, 250, 25, lblAddress);
		txtAddress = new JTextArea();
		txtAddress.setLineWrap(true);
		scAddress = new JScrollPane(txtAddress,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		txtAddress.setAlignmentX(JComboBox.LEFT_ALIGNMENT);
		txtAddress.setEditable(isReadOnly == false);
		if (isReadOnly)
			txtAddress.setText(loginForm.getLoginUserInfo().get("ADDRESS").getAsString());
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, 240, 250, 80, scAddress);

		lblPhoneNumber = new JLabel("전화번호");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 325, 200, 25, lblPhoneNumber);
		txtPhoneNumber = new JTextField("");
		txtPhoneNumber.setHorizontalAlignment(JTextField.CENTER);
		txtPhoneNumber.setEditable(isReadOnly == false);
		if (isReadOnly)
			txtPhoneNumber.setText(loginForm.getLoginUserInfo().has("PHONE_NUMBER") == false ? ""
					: loginForm.getLoginUserInfo().get("PHONE_NUMBER").getAsString());

		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, 325, 250, 25, txtPhoneNumber);

		lblEMailId = new JLabel("이메일 주소");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 355, 200, 25, lblEMailId);
		txtEMailId = new JTextField("");
		txtEMailId.setHorizontalAlignment(JTextField.CENTER);
		txtEMailId.setEditable(isReadOnly == false);
		if (isReadOnly)
			txtEMailId.setText(loginForm.getLoginUserInfo().has("EMAIL_ID") == false ? ""
					: loginForm.getLoginUserInfo().get("EMAIL_ID").getAsString());
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, 355, 250, 25, txtEMailId);

		lblUserLicenseKey = new JLabel("라이선스키");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 385, 200, 25, lblUserLicenseKey);
		txtUserLicenseKey = new JTextField("");
		txtUserLicenseKey.setEditable(false);
		txtUserLicenseKey.setHorizontalAlignment(JTextField.CENTER);
		if (isReadOnly)
			txtUserLicenseKey.setText((loginForm.getLoginUserInfo().has("userLicenseInfo") &&
					(loginForm.getLoginUserInfo().get("userLicenseInfo").getAsJsonObject()).has("LICENSE_KEY"))
							? loginForm.getLoginUserInfo().get("userLicenseInfo").getAsJsonObject().get("LICENSE_KEY")
									.getAsString()
							: "");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, 385, 250, 25, txtUserLicenseKey);

		btnOK = new JButton("회원가입");
		btnOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnRegisterUser_Click();
			}

		});

		LayoutUtil.layoutComponent(this,
				165,
				470,
				110,
				LayoutUtil.h_NormalPanelControl,
				btnOK);

		btnOK.setVisible(isReadOnly == false);

		btnCancel = new JButton("닫기");
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnClose_Click();
			}

		});

		LayoutUtil.layoutComponent(this, 275,	470, 110, LayoutUtil.h_NormalPanelControl, btnCancel);

		viewSalesTypeList();
		viewSalesCategoryList();

		UIUtil.setLookAndFeel(PropertyUtil.get("LookAndFeel"), this);
	}

	void btnRegisterUser_Click() {
		// 항목별 체크 후 회원가입 처리
		if (txtUserId.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "로그인 아이디를 입력하세요.");
			txtUserId.requestFocus();
			return;
		}

		if (new String(txtUserPassword.getPassword()).trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "로그인 비밀번호를 입력하세요.");
			txtUserPassword.requestFocus();
			return;
		}

		if (txtUserName.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "사용자 이름를 입력하세요.");
			txtUserName.requestFocus();
			return;
		}

		if (txtRegisterName.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "상호명을 입력하세요.");
			txtRegisterName.requestFocus();
			return;
		}

		if (txtRegisterNumber.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "사업자등록번호를 입력하세요.");
			txtRegisterNumber.requestFocus();
			return;
		}

		if (cboSalesType.getSelectedItem() == null || cboSalesType.getSelectedItem().toString().trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "업태를 선택하세요.");
			cboSalesType.requestFocus();
			return;
		}

		if (cboSalesCategory.getSelectedItem() == null
				|| cboSalesCategory.getSelectedItem().toString().trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "업종을 선택하세요.");
			cboSalesCategory.requestFocus();
			return;
		}

		if (txtAddress.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "주소를 입력하세요.");
			txtAddress.requestFocus();
			return;
		}

		if (txtPhoneNumber.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "전화번호를 입력하세요.");
			txtPhoneNumber.requestFocus();
			return;
		}

		if (txtEMailId.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "이메일 주소를 입력하세요.");
			txtEMailId.requestFocus();
			return;
		}

		if (MessageBoxUtil.showConfirmMessageDialog(this,
			"등록 하시겠습니끼?") == JOptionPane.YES_OPTION) {

			JsonObject jReply;

			try {
				jReply = UserInfo.getInstance().registerUser(
						loginForm.getRPCClient(),
						loginForm.getConnectionInfo(),
						BrunnerClientApi.SYSTEM_CODE_DEFAULT,
						txtUserId.getText(),
						new String(txtUserPassword.getPassword()),
						txtUserName.getText(),
						txtAddress.getText(),
						txtPhoneNumber.getText(),
						txtEMailId.getText(),
						txtRegisterNumber.getText(),
						txtRegisterName.getText(),
						cboSalesType.getSelectedItem().toString(),
						cboSalesCategory.getSelectedItem().toString(),
						"Y");

				MessageBoxUtil.showInformationMessageDialog(this,
						jReply.get(BrunnerClientApi.msgFieldName_resultMessage).getAsString());

				if (jReply.has("licenseKey") == true)
					this.txtUserLicenseKey.setText(jReply.get("licenseKey").getAsString());
			} catch (Exception e) {
				MessageBoxUtil.showExceptionMessageDialog(this, e);
			}
		}
	}

	void btnClose_Click() {
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}

	void viewSalesTypeList() throws Exception {
		JsonObject jReply = CommonCode.getInstance().viewCommonCodeItemList(
				loginForm.getRPCClient(),
				loginForm.getConnectionInfo(),
				BrunnerClientApi.SYSTEM_CODE_DEFAULT,
				"SALES_TYPE");

		cboSalesType.addItem("");

		for (int rowIndex = 0; rowIndex < jReply.get("commonCodeItemList").getAsJsonArray().size(); rowIndex++) {
			JsonObject jRowData = (JsonObject) jReply.get("commonCodeItemList").getAsJsonArray().get(rowIndex);

			cboSalesType.addItem(jRowData.get("commonCodeId").getAsString());
		}
	}

	void viewSalesCategoryList() throws Exception {
		JsonObject jReply = CommonCode.getInstance().viewCommonCodeItemList(
				loginForm.getRPCClient(),
				loginForm.getConnectionInfo(),
				BrunnerClientApi.SYSTEM_CODE_DEFAULT,
				"SALES_CATEGORY");

		cboSalesCategory.addItem("");
		for (int rowIndex = 0; rowIndex < jReply.get("commonCodeItemList").getAsJsonArray().size(); rowIndex++) {
			JsonObject jRowData = (JsonObject) jReply.get("commonCodeItemList").getAsJsonArray().get(rowIndex);

			cboSalesCategory.addItem(jRowData.get("commonCodeId").getAsString());
		}

	}
}
