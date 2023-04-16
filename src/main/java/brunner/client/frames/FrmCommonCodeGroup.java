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

/***
 * 공통코드그룹관리
 */
public class FrmCommonCodeGroup extends JDialog {

	private static final long serialVersionUID = 1L;

	JLabel lblSystemCode;
	JTextField txtSystemCode;
	JLabel lblCommomCodeGroup;
	JTextField txtCommonCodeGroup;
	JLabel lblCommonCodeGroupDesc;
	JTextField txtCommonCodeGroupDesc;
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

	public FrmCommonCodeGroup(FrmRegisterCommonCode commonCodeForm) {
		super(MainFrame.getInstance(), "공통 코드 그룹 등록", Dialog.ModalityType.DOCUMENT_MODAL);
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
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 30, LayoutUtil.w_NormalPanelControl,
				LayoutUtil.h_NormalPanelControl, lblSystemCode);
		txtSystemCode = new JTextField(commonCodeForm.getLoginUserInfo().get("SYSTEM_CODE").getAsString());
		txtSystemCode.setEditable(false);
		txtSystemCode.setHorizontalAlignment(JTextField.CENTER);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, 30, LayoutUtil.w_WidePanelControl,
				LayoutUtil.h_NormalPanelControl, txtSystemCode);

		lblCommomCodeGroup = new JLabel("코드그룹 아이디");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 60, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblCommomCodeGroup);

		if (commonCodeForm.tblCommonCodeGroup.getSelectedRows().length > 0) {
			txtCommonCodeGroup = new JTextField(commonCodeForm.tblCommonCodeGroup.getModel()
					.getValueAt(commonCodeForm.tblCommonCodeGroup.getSelectedRow(), 1).toString());
		} else {
			txtCommonCodeGroup = new JTextField("");
		}

		txtCommonCodeGroup.setHorizontalAlignment(JTextField.CENTER);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, 60, LayoutUtil.w_WidePanelControl,
				LayoutUtil.h_NormalPanelControl, txtCommonCodeGroup);

		lblCommonCodeGroupDesc = new JLabel("코드그룹 설명");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 90, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblCommonCodeGroupDesc);

		if (commonCodeForm.tblCommonCodeGroup.getSelectedRows().length > 0) {
			txtCommonCodeGroupDesc = new JTextField(commonCodeForm.tblCommonCodeGroup.getModel()
					.getValueAt(commonCodeForm.tblCommonCodeGroup.getSelectedRow(), 2).toString());
		} else {
			txtCommonCodeGroupDesc = new JTextField("");
		}

		txtCommonCodeGroupDesc.setHorizontalAlignment(JTextField.CENTER);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, 90, LayoutUtil.w_WidePanelControl,
				LayoutUtil.h_NormalPanelControl, txtCommonCodeGroupDesc);

		lblAttribute1 = new JLabel("속성1");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 120, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblAttribute1);

		if (commonCodeForm.tblCommonCodeGroup.getSelectedRows().length > 0) {
			txtAttribute1 = new JTextField(commonCodeForm.tblCommonCodeGroup.getModel()
					.getValueAt(commonCodeForm.tblCommonCodeGroup.getSelectedRow(), 3).toString());
		} else {
			txtAttribute1 = new JTextField("");
		}

		txtAttribute1.setHorizontalAlignment(JTextField.CENTER);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, 120, LayoutUtil.w_WidePanelControl,
				LayoutUtil.h_NormalPanelControl, txtAttribute1);

		lblAttribute2 = new JLabel("속성2");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 150, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblAttribute2);

		if (commonCodeForm.tblCommonCodeGroup.getSelectedRows().length > 0) {
			txtAttribute2 = new JTextField(commonCodeForm.tblCommonCodeGroup.getModel()
					.getValueAt(commonCodeForm.tblCommonCodeGroup.getSelectedRow(), 4).toString());
		} else {
			txtAttribute2 = new JTextField("");
		}

		txtAttribute2.setHorizontalAlignment(JTextField.CENTER);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, 150, LayoutUtil.w_WidePanelControl,
				LayoutUtil.h_NormalPanelControl, txtAttribute2);

		lblAttribute3 = new JLabel("속성3");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 180, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblAttribute3);

		if (commonCodeForm.tblCommonCodeGroup.getSelectedRows().length > 0) {
			txtAttribute3 = new JTextField(commonCodeForm.tblCommonCodeGroup.getModel()
					.getValueAt(commonCodeForm.tblCommonCodeGroup.getSelectedRow(), 5).toString());
		} else {
			txtAttribute3 = new JTextField("");
		}

		txtAttribute3.setHorizontalAlignment(JTextField.CENTER);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, 180, LayoutUtil.w_WidePanelControl,
				LayoutUtil.h_NormalPanelControl, txtAttribute3);

		lblAttribute4 = new JLabel("속성4");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 210, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblAttribute4);

		if (commonCodeForm.tblCommonCodeGroup.getSelectedRows().length > 0) {
			txtAttribute4 = new JTextField(commonCodeForm.tblCommonCodeGroup.getModel()
					.getValueAt(commonCodeForm.tblCommonCodeGroup.getSelectedRow(), 6).toString());
		} else {
			txtAttribute4 = new JTextField("");
		}

		txtAttribute4.setHorizontalAlignment(JTextField.CENTER);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, 210, LayoutUtil.w_WidePanelControl,
				LayoutUtil.h_NormalPanelControl, txtAttribute4);

		lblAttribute5 = new JLabel("속성5");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 240, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblAttribute5);

		if (commonCodeForm.tblCommonCodeGroup.getSelectedRows().length > 0) {
			txtAttribute5 = new JTextField(commonCodeForm.tblCommonCodeGroup.getModel()
					.getValueAt(commonCodeForm.tblCommonCodeGroup.getSelectedRow(), 7).toString());
		} else {
			txtAttribute5 = new JTextField("");
		}

		txtAttribute5.setHorizontalAlignment(JTextField.CENTER);
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_2, 240, LayoutUtil.w_WidePanelControl,
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

		LayoutUtil.layoutComponent(this, this.getWidth()
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

		LayoutUtil.layoutComponent(this, this.getWidth()
				- (LayoutUtil.w_NormalButton + LayoutUtil.marginConstrols + 2 * LayoutUtil.marginRightHiddenInternal),
				420, LayoutUtil.w_NormalButton, LayoutUtil.h_NormalPanelControl, btnUnregister);
	}

	/***
	 * 공통코드 그룹 등록
	 * 
	 * @throws Exception
	 */
	void btnRegister_Click() throws Exception {
		if (MessageBoxUtil.showConfirmMessageDialog(this, "등록 하시겠습니끼?") == JOptionPane.YES_OPTION) {
			String systemCode = txtSystemCode.getText();
			String codeGroupId = txtCommonCodeGroup.getText();
			String codeGroupDesc = txtCommonCodeGroupDesc.getText();
			String attribute1 = txtAttribute1.getText();
			String attribute2 = txtAttribute2.getText();
			String attribute3 = txtAttribute3.getText();
			String attribute4 = txtAttribute4.getText();
			String attribute5 = txtAttribute5.getText();

			JsonObject jReply = CommonCode.getInstance().registerCommonCodeGroup(commonCodeForm.getRPCClient(),
					commonCodeForm.getConnectionInfo(), systemCode, codeGroupId, codeGroupDesc, attribute1, attribute2,
					attribute3, attribute4, attribute5);

			MessageBoxUtil.showInformationMessageDialog(this,
					jReply.get(BrunnerClientApi.msgFieldName_resultMessage).getAsString());

			this.commonCodeForm.btnViewCodeGroupList_Click();
		}
	}

	/***
	 * 공통코드 그룹 삭제
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

			JsonObject jReply = CommonCode.getInstance().unregisterCommonCodeGroup(commonCodeForm.getRPCClient(),
					commonCodeForm.getConnectionInfo(), systemCode, codeGroupId,
					commonCodeForm.getLoginUserInfo().get("USER_ID").getAsString());

			MessageBoxUtil.showInformationMessageDialog(this,
					jReply.get(BrunnerClientApi.msgFieldName_resultMessage).getAsString());

			this.commonCodeForm.btnViewCodeGroupList_Click();
		}
	}
}
