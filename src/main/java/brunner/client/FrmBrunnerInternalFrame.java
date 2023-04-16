package brunner.client;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;

import com.google.gson.JsonObject;

import mw.launchers.RPCClient;

public class FrmBrunnerInternalFrame extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	protected RPCClient rpcClient;
	protected JsonObject connectionInfo;
	protected JsonObject loginUserInfo;
	protected ImageIcon icon;

	public FrmBrunnerInternalFrame(RPCClient rpcClient, JsonObject connectionInfo, JsonObject loginUserInfo,
			String title, int width, int height, ImageIcon icon) {

		super(title);

		this.rpcClient = rpcClient;
		this.connectionInfo = connectionInfo;
		this.loginUserInfo = loginUserInfo;
		this.icon = icon;
		initLayout(width, height);
	}

	protected void initLayout(int width, int height) {
		this.setFrameIcon(getIcon());

		setSize(width, height);
		setVisible(true);
		setResizable(false);
		setClosable(true);
		setIconifiable(true);
		setMaximizable(false);
	}

	public ImageIcon getIcon() {
		return icon;
	}
}
