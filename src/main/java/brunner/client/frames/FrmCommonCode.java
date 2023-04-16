package brunner.client.frames;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeoutException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import brunner.client.LayoutUtil;
import brunner.client.MessageBoxUtil;
import brunner.client.api.BrunnerClientApi;
import brunner.client.api.CommonCode;
import brunner.client.internalFrames.FrmRegisterCommonCode;
import mw.utility.ExceptionUtil;

public class FrmCommonCode extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel lblSystemCode;
	JTextField txtSystemCode;
	JLabel lblCommomCodeGroup;
	JTextField txtCommonCodeGroup;
	JLabel lblCommonCodeId;
	JTextField txtCommonCodeId;
	JLabel lblCommonCodeDesc;
	JTextField txtCommonCodeDesc;
	JLabel lblKey1;
	JTextField txtKey1;
	JLabel lblKey2;
	JTextField txtKey2;
	JLabel lblAttribute1;
	JTextField txtAttribute1;
	JLabel lblAttribute2;
	JTextField txtAttribute2;
	JLabel lblAttribute3;
	JTextField txtAttribute3;
	JLabel lblAttribute4;
	JTextField txtAttribute4;
	JLabel lblAttribute5;
	JTextField txtAttribute5;

	JButton btnRegister;
	JButton btnUnregister;

	FrmRegisterCommonCode commonCodeForm;

	public FrmCommonCode(FrmRegisterCommonCode commonCodeForm) {
		super(MainFrame.getInstance(), "공통 코드 등록", Dialog.ModalityType.DOCUMENT_MODAL);

		this.commonCodeForm = commonCodeForm;
		setModal(true);

		try {
			initLayout();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, ExceptionUtil.getFullMessage(e),
					"Exception", JOptionPane.ERROR_MESSAGE);
		}
	}

	void initLayout()
			throws JsonSyntaxException, IOException, URISyntaxException, InterruptedException, TimeoutException {
		this.setSize(450, 500);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		setLayout(null);

		lblSystemCode = new JLabel("시스템코드");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 30, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblSystemCode);
		txtSystemCode = new JTextField(commonCodeForm.getLoginUserInfo().get("SYSTEM_CODE").getAsString());
		txtSystemCode.setEditable(false);
		txtSystemCode.setHorizontalAlignment(JTextField.CENTER);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, 30, LayoutUtil.w_WidePanelControl,
				LayoutUtil.h_NormalPanelControl, txtSystemCode);

		lblCommomCodeGroup = new JLabel("코드그룹");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 60, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblCommomCodeGroup);

		if (commonCodeForm.tblCommonCodeGroup.getSelectedRows().length > 0) {
			txtCommonCodeGroup = new JTextField(commonCodeForm.tblCommonCodeGroup.getModel()
					.getValueAt(commonCodeForm.tblCommonCodeGroup.getSelectedRow(), 1).toString());
			txtCommonCodeGroup.setEditable(false);
		} else {
			txtCommonCodeGroup = new JTextField("");
			txtCommonCodeGroup.setEnabled(true);
		}

		txtCommonCodeGroup.setHorizontalAlignment(JTextField.CENTER);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, 60, LayoutUtil.w_WidePanelControl,
				LayoutUtil.h_NormalPanelControl, txtCommonCodeGroup);

		lblCommonCodeId = new JLabel("코드아이디");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 90, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblCommonCodeId);

		if (commonCodeForm.tblCommonCodeItem.getSelectedRows().length > 0) {
			txtCommonCodeId = new JTextField(commonCodeForm.tblCommonCodeItem.getModel()
					.getValueAt(commonCodeForm.tblCommonCodeItem.getSelectedRow(), 0).toString());
		} else {
			txtCommonCodeId = new JTextField("");
		}

		txtCommonCodeId.setHorizontalAlignment(JTextField.CENTER);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, 90, LayoutUtil.w_WidePanelControl,
				LayoutUtil.h_NormalPanelControl, txtCommonCodeId);

		lblCommonCodeDesc = new JLabel("코드설명");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 120, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblCommonCodeDesc);

		if (commonCodeForm.tblCommonCodeItem.getSelectedRows().length > 0) {
			txtCommonCodeDesc = new JTextField(commonCodeForm.tblCommonCodeItem.getModel()
					.getValueAt(commonCodeForm.tblCommonCodeItem.getSelectedRow(), 1).toString());
		} else {
			txtCommonCodeDesc = new JTextField("");
		}

		txtCommonCodeDesc.setHorizontalAlignment(JTextField.CENTER);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, 120, LayoutUtil.w_WidePanelControl,
				LayoutUtil.h_NormalPanelControl, txtCommonCodeDesc);

		lblKey1 = new JLabel("키1");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 150, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblKey1);

		if (commonCodeForm.tblCommonCodeItem.getSelectedRows().length > 0) {
			txtKey1 = new JTextField(commonCodeForm.tblCommonCodeItem.getModel()
					.getValueAt(commonCodeForm.tblCommonCodeItem.getSelectedRow(), 2).toString());
		} else {
			txtKey1 = new JTextField("");
		}

		txtKey1.setHorizontalAlignment(JTextField.CENTER);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, 150, LayoutUtil.w_WidePanelControl,
				LayoutUtil.h_NormalPanelControl, txtKey1);

		lblKey2 = new JLabel("키2");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 180, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblKey2);

		if (commonCodeForm.tblCommonCodeItem.getSelectedRows().length > 0) {
			txtKey2 = new JTextField(commonCodeForm.tblCommonCodeItem.getModel()
					.getValueAt(commonCodeForm.tblCommonCodeItem.getSelectedRow(), 3).toString());
		} else {
			txtKey2 = new JTextField("");
		}

		txtKey2.setHorizontalAlignment(JTextField.CENTER);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, 180, LayoutUtil.w_WidePanelControl,
				LayoutUtil.h_NormalPanelControl, txtKey2);

		lblAttribute1 = new JLabel("속성1");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 210, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblAttribute1);

		if (commonCodeForm.tblCommonCodeItem.getSelectedRows().length > 0) {
			txtAttribute1 = new JTextField(commonCodeForm.tblCommonCodeItem.getModel()
					.getValueAt(commonCodeForm.tblCommonCodeItem.getSelectedRow(), 4).toString());
		} else {
			txtAttribute1 = new JTextField("");
		}

		txtAttribute1.setHorizontalAlignment(JTextField.CENTER);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, 210, LayoutUtil.w_WidePanelControl,
				LayoutUtil.h_NormalPanelControl, txtAttribute1);

		lblAttribute2 = new JLabel("속성2");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 240, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblAttribute2);

		if (commonCodeForm.tblCommonCodeItem.getSelectedRows().length > 0) {
			txtAttribute2 = new JTextField(commonCodeForm.tblCommonCodeItem.getModel()
					.getValueAt(commonCodeForm.tblCommonCodeItem.getSelectedRow(), 5).toString());
		} else {
			txtAttribute2 = new JTextField("");
		}

		txtAttribute2.setHorizontalAlignment(JTextField.CENTER);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, 240, LayoutUtil.w_WidePanelControl,
				LayoutUtil.h_NormalPanelControl, txtAttribute2);

		lblAttribute3 = new JLabel("속성3");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 270, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblAttribute3);

		if (commonCodeForm.tblCommonCodeItem.getSelectedRows().length > 0) {
			txtAttribute3 = new JTextField(commonCodeForm.tblCommonCodeItem.getModel()
					.getValueAt(commonCodeForm.tblCommonCodeItem.getSelectedRow(), 6).toString());
		} else {
			txtAttribute3 = new JTextField("");
		}

		txtAttribute3.setHorizontalAlignment(JTextField.CENTER);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, 270, LayoutUtil.w_WidePanelControl,
				LayoutUtil.h_NormalPanelControl, txtAttribute3);

		lblAttribute4 = new JLabel("속성4");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 300, LayoutUtil.w_NormalPanelControl,
				LayoutUtil.h_NormalPanelControl, lblAttribute4);

		if (commonCodeForm.tblCommonCodeItem.getSelectedRows().length > 0) {
			txtAttribute4 = new JTextField(commonCodeForm.tblCommonCodeItem.getModel()
					.getValueAt(commonCodeForm.tblCommonCodeItem.getSelectedRow(), 7).toString());
		} else {
			txtAttribute4 = new JTextField("");
		}

		txtAttribute4.setHorizontalAlignment(JTextField.CENTER);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, 300, LayoutUtil.w_WidePanelControl,
				LayoutUtil.h_NormalPanelControl, txtAttribute4);

		lblAttribute5 = new JLabel("속성5");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 330, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblAttribute5);

		if (commonCodeForm.tblCommonCodeItem.getSelectedRows().length > 0) {
			txtAttribute5 = new JTextField(commonCodeForm.tblCommonCodeItem.getModel()
					.getValueAt(commonCodeForm.tblCommonCodeItem.getSelectedRow(), 8).toString());
		} else {
			txtAttribute5 = new JTextField("");
		}

		txtAttribute5.setHorizontalAlignment(JTextField.CENTER);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, 330, LayoutUtil.w_WidePanelControl,
				LayoutUtil.h_NormalPanelControl, txtAttribute5);

		btnRegister = new JButton("등록");
		btnRegister.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					btnRegister_Click();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});

		LayoutUtil.layoutComponent(this, getWidth()
				- (LayoutUtil.w_NormalButton + LayoutUtil.marginConstrols + LayoutUtil.marginRightHiddenInternal) * 2,
				420, LayoutUtil.w_NormalButton, LayoutUtil.h_NormalPanelControl, btnRegister);

		btnUnregister = new JButton("삭제");
		btnUnregister.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					btnUnregister_Click();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});

		LayoutUtil.layoutComponent(this, getWidth()
				- (LayoutUtil.w_NormalButton + LayoutUtil.marginConstrols + 2 * LayoutUtil.marginRightHiddenInternal),
				420, LayoutUtil.w_NormalButton, LayoutUtil.h_NormalPanelControl, btnUnregister);
	}

	/***
	 * 공통코드 등록
	 * 
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws JsonSyntaxException
	 */
	void btnRegister_Click() throws Exception {
		if (MessageBoxUtil.showConfirmMessageDialog(this, "등록 하시겠습니끼?") == JOptionPane.YES_OPTION) {
			String systemCode = txtSystemCode.getText();
			String commonCodeGroupId = txtCommonCodeGroup.getText();
			String commonCodeId = txtCommonCodeId.getText();
			String key1 = txtKey1.getText();
			String key2 = txtKey2.getText();
			String commonCodeDesc = txtCommonCodeDesc.getText();
			String attribute1 = txtAttribute1.getText();
			String attribute2 = txtAttribute2.getText();
			String attribute3 = txtAttribute3.getText();
			String attribute4 = txtAttribute4.getText();
			String attribute5 = txtAttribute5.getText();

			JsonObject jReply = CommonCode.getInstance().registerCommonCode(commonCodeForm.getRPCClient(),
					commonCodeForm.getConnectionInfo(), systemCode, commonCodeGroupId, commonCodeId, key1, key2,
					commonCodeDesc, attribute1, attribute2, attribute3, attribute4, attribute5);
			MessageBoxUtil.showInformationMessageDialog(this,
					jReply.get(BrunnerClientApi.msgFieldName_resultMessage).getAsString());
			this.commonCodeForm.tblCommonCodeGroup_rowSelected(this.commonCodeForm.tblCommonCodeGroup.getSelectedRow());
		}
	}

	/***
	 * 공통코드 삭제
	 * 
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws JsonSyntaxException
	 */
	void btnUnregister_Click() throws Exception {
		if (MessageBoxUtil.showConfirmMessageDialog(this, "삭제 하시겠습니끼?") == JOptionPane.YES_OPTION) {
			String systemCode = txtSystemCode.getText();
			String codeGroupId = txtCommonCodeGroup.getText();
			String codeId = txtCommonCodeId.getText();
			String key1 = txtKey1.getText();
			String key2 = txtKey2.getText();

			JsonObject jReply = CommonCode.getInstance().unregisterCommonCode(commonCodeForm.getRPCClient(),
					commonCodeForm.getConnectionInfo(), systemCode, codeGroupId, codeId, key1, key2);

			MessageBoxUtil.showInformationMessageDialog(this,
					jReply.get(BrunnerClientApi.msgFieldName_resultMessage).getAsString());

			this.commonCodeForm.tblCommonCodeGroup_rowSelected(this.commonCodeForm.tblCommonCodeGroup.getSelectedRow());
		}
	}
}
