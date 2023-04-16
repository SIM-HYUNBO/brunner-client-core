package brunner.client.frames;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeoutException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import brunner.client.IconUtil;
import brunner.client.LayoutUtil;
import mw.utility.ExceptionUtil;
import mw.utility.JsonUtil;

public class FrmAbout extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel lblBrunnerInfo;
	JLabel lblLicense;
	JTextArea txtLicense;
	JScrollPane scLicense;

	JButton btnClose;

	JsonObject loginUserInfo;

	public FrmAbout(JsonObject loginUserInfo) {
		super(MainFrame.getInstance(), "About Brunner", Dialog.ModalityType.DOCUMENT_MODAL);

		try {
			this.setIconImage(IconUtil.getIconImage("brunnerLogo.png", 20, 20));
			this.loginUserInfo = loginUserInfo;

			initLayout();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, ExceptionUtil.getFullMessage(e),
					"Exception", JOptionPane.ERROR_MESSAGE);
		}
	}

	void initLayout()
			throws JsonSyntaxException, IOException, URISyntaxException, InterruptedException, TimeoutException {
		this.setSize(325, 500);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		setLayout(null);
		lblBrunnerInfo = new JLabel("Brunner System");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 20, LayoutUtil.w_NormalLabel, 25, lblBrunnerInfo);

		JPanel pnlLogo = new JPanel() {
			private static final long serialVersionUID = 1L;

			public void paint(Graphics g) {
				g.drawImage(IconUtil.getIconImage("brunnerLogo.png", 100, 100), 0, 0, null);
			}
		};
		LayoutUtil.layoutComponent(this, 20, 50, 100, 100, pnlLogo);

		lblLicense = new JLabel("User License");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 190, LayoutUtil.w_NormalLabel, 25, lblLicense);
		
		txtLicense = new JTextArea("");
		txtLicense.setLineWrap(true);
		txtLicense.setEditable(false);
		txtLicense.setBorder(null);
		scLicense = new JScrollPane(txtLicense);
		scLicense.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, 220, LayoutUtil.w_WidePanelControl,
				LayoutUtil.h_NormalTable, scLicense);

		btnClose = new JButton("닫기");
		btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnClose_Click();
			}

		});
		LayoutUtil.layoutComponent(this, 190, 420, 110, LayoutUtil.h_NormalPanelControl, btnClose);

		JsonObject licenseInfo = this.loginUserInfo.get("userLicenseInfo").getAsJsonObject();
		txtLicense.setText(JsonUtil.toBeautifyString(licenseInfo));
	}

	void btnClose_Click() {
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
}