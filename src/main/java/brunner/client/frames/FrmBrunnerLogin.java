package brunner.client.frames;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeoutException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import brunner.UIUtil;
import brunner.client.IconUtil;
import brunner.client.LayoutUtil;
import brunner.client.MessageBoxUtil;
import brunner.client.PropertyUtil;
import brunner.client.api.BrunnerClientApi;
import brunner.client.api.UserInfo;
import mw.launchers.RPCClient;

public class FrmBrunnerLogin extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	RPCClient mwClient = null;
	JsonObject jConnectionInfo = null;
	JsonObject jLoginUserInfo = null;

	// JFrame frame;
	JButton btnLogin, btnRegister;
	JTextField txtUserId;
	JPasswordField txtPassword;
	JLabel lblUsername, lblPassword;
	GridBagLayout gbl;
	GridBagConstraints gbc;
	
	public FrmBrunnerLogin() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, IOException {
		System.setProperty("file.encoding", "UTF-8");

		initLayout();
	}

	void initLayout() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, IOException {
		setTitle("로그인 - Brunner");
		setLayout(null);

		setIconImage(IconUtil.getIconImage("brunnerLogo.png", 20, 20));

		btnLogin = new JButton("로그인");
		btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					btnLogin_Click();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		btnRegister = new JButton("회원가입");
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

		txtUserId = new JTextField(15);
		txtUserId.setHorizontalAlignment(JTextField.CENTER);
		txtPassword = new JPasswordField(15);
		txtPassword.setHorizontalAlignment(JPasswordField.CENTER);
		txtPassword.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						btnLogin_Click();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

		});

		lblUsername = new JLabel("아이디");
		lblPassword = new JLabel("비밀번호");

		LayoutUtil.layoutComponent(this, 50, 50, 80, 25, lblUsername);
		LayoutUtil.layoutComponent(this, 160, 50, 200, 25, txtUserId);
		LayoutUtil.layoutComponent(this, 50, 80, 80, 25, lblPassword);
		LayoutUtil.layoutComponent(this, 160, 80, 200, 25, txtPassword);
		LayoutUtil.layoutComponent(this, 200, 120, 80, 25, btnLogin);
		LayoutUtil.layoutComponent(this, 280, 120, 80, 25, btnRegister);

		this.setSize(400, 200);
		this.getContentPane().setBackground(Color.WHITE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		UIUtil.setLookAndFeel(PropertyUtil.get("LookAndFeel"), this);
	
		this.setVisible(true);

		this.addWindowListener(new WindowAdapter() 
		{
		  public void windowClosed(WindowEvent e)
		  {
			;
		  }
		
		  public void windowClosing(WindowEvent e)
		  {
			System.exit(0);
		  }
		});
	}

	public void initInput() {
		txtUserId.setText("");
		txtPassword.setText("");
	}

	void btnLogin_Click() throws Exception {

		if (mwClient == null || mwClient.IsOpen() == false)
			initConnection();

		if (login() == true) {
			
			setVisible(false);
			initInput();

			MainFrame frame = new MainFrame(this, this.getLoginUserInfo().get("REGISTER_NAME") == null ? ""
			: this.getLoginUserInfo().get("REGISTER_NAME").getAsString());

			frame.setVisible(true);
		}
	}

	void btnRegister_Click() throws JsonSyntaxException, IOException, URISyntaxException, TimeoutException {
		if (mwClient == null || mwClient.IsOpen() == false)
			initConnection();

		new FrmRegisterUser(null, this, false).setVisible(true);
	}

	public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, IOException {
		new FrmBrunnerLogin();
	}

	void initConnection() throws JsonSyntaxException, IOException, URISyntaxException, TimeoutException {
		// JAR 파일 내부에서 연결 설정 (텍스트 파일)을 읽음
		JsonObject jConnectionInfoes = (JsonObject) new JsonParser()
				.parse(new String(getClass().getClassLoader().getResourceAsStream("Connections.json").readAllBytes()));
		jConnectionInfo = jConnectionInfoes.get("BrunnerServer").getAsJsonObject();

		mwClient = new RPCClient();
		mwClient.connect(jConnectionInfo.get("hostName").getAsString(), jConnectionInfo.get("portNumber").getAsInt(),
				jConnectionInfo.get("userName").getAsString(), jConnectionInfo.get("password").getAsString(),
				jConnectionInfo.get("queueName").getAsString());
	}

	public RPCClient getRPCClient() {
		return mwClient;
	}

	public JsonObject getConnectionInfo() {
		return jConnectionInfo;
	}

	boolean login() {
		boolean ret = false;

		try {
			if (txtUserId.getText().trim().equals("")) {
				MessageBoxUtil.showErrorMessageDialog(MainFrame.getInstance(), "로그인아이디를 입력하세요.");
				return ret;
			}

			if (new String(txtPassword.getPassword()).trim().equals("")) {
				MessageBoxUtil.showErrorMessageDialog(MainFrame.getInstance(), "비밀번호를 입력하세요.");
				return ret;
			}

			JsonObject jReply = UserInfo.getInstance().login(mwClient, jConnectionInfo,
					BrunnerClientApi.SYSTEM_CODE_DEFAULT, txtUserId.getText(), new String(txtPassword.getPassword()));
			if (jReply.get(BrunnerClientApi.msgFieldName_resultCode).getAsString()
					.equals(BrunnerClientApi.resultCode_success)) {
				ret = true;
				jLoginUserInfo = (JsonObject) new JsonParser()
						.parse(jReply.get("userInfo").getAsJsonObject().toString());
			} else {
				MessageBoxUtil.showErrorMessageDialog(MainFrame.getInstance(), jReply.get(BrunnerClientApi.msgFieldName_resultMessage).getAsString());
			}
		} catch (Exception e) {
			MessageBoxUtil.showExceptionMessageDialog(MainFrame.getInstance(), e);
		}

		return ret;
	}

	public JsonObject getLoginUserInfo() {
		return jLoginUserInfo;
	}
}
