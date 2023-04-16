package brunner.client.frames;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map.Entry;
import java.util.concurrent.TimeoutException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import brunner.client.LayoutUtil;
import brunner.client.MessageBoxUtil;
import brunner.client.api.BrunnerClientApi;
import mw.launchers.RPCClient;
import mw.utility.ExceptionUtil;
import mw.utility.JsonUtil;

public class FrmMessageSender extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JsonObject jConnectionInfo = null;
	JsonObject jMessageInfo = null;
	RPCClient client = new RPCClient();

	JTabbedPane tabMain;
	JLabel lblConnectionInfo;
	JComboBox<String> cboConnections;
	JTextArea txtConnectionInfo;
	JScrollPane scbConnectionInfo;
	JButton btnConnect;
	JPanel panelConnectionInfo;

	JLabel lblRequestInfo;
	JComboBox<String> cboRequestMessages;
	JTextArea txtRequestInfo;
	JScrollPane scbRequestInfo;
	JButton btnSendRequest;
	JPanel panelRequestInfo;

	JLabel lblResponseInfo;
	JTextArea txtResponseInfo;
	JPanel panelResopnseInfo;
	JScrollPane scbResponseInfo;

	JsonObject requestMessages = null;
	JsonObject connections = null;

	public static void main(String[] argv) throws JsonSyntaxException, IOException, URISyntaxException {
		FrmMessageSender frm = new FrmMessageSender();
		frm.Init();
		frm.setVisible(true);
	}

	public void Init() throws JsonSyntaxException, IOException, URISyntaxException {
		this.setLayout(null);
		this.setTitle("Brunner Client");
		this.setSize(600, 800);

		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				frm_resized();
			}
		});

		centerWindow();

		tabMain = new JTabbedPane();

		// connection info

		panelConnectionInfo = new JPanel();
		panelConnectionInfo.setLayout(null);
		tabMain.addTab("Connection", panelConnectionInfo);

		lblConnectionInfo = new JLabel("Connection Info");
		LayoutUtil.layoutComponent(this, 10, 15, 100, 10, lblConnectionInfo);

		cboConnections = new JComboBox<String>();
		cboConnections.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cboConnections_selectionChanged();
			}
		});

		cboConnections.setSize(410, 20);
		cboConnections.setLocation(150, 10);
		panelConnectionInfo.add(cboConnections);

		txtConnectionInfo = new JTextArea();
		txtConnectionInfo.setLineWrap(true);
		panelConnectionInfo.add(txtConnectionInfo);

		scbConnectionInfo = new JScrollPane(txtConnectionInfo);
		scbConnectionInfo.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		scbConnectionInfo.setSize(540, 630);
		scbConnectionInfo.setLocation(10, 40);
		panelConnectionInfo.add(scbConnectionInfo);

		btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					btnConnect_Click();
				} catch (IOException ex) {
					MessageBoxUtil.showExceptionMessageDialog(MainFrame.getInstance(), ex);
				} catch (TimeoutException ex) {
					MessageBoxUtil.showExceptionMessageDialog(MainFrame.getInstance(), ex);
				}
			}

		});

		btnConnect.setSize(120, 20);
		btnConnect.setLocation(420, 680);
		panelConnectionInfo.add(btnConnect);

		// request info

		panelRequestInfo = new JPanel();
		panelRequestInfo.setLayout(null);
		tabMain.addTab("Request", panelRequestInfo);

		lblRequestInfo = new JLabel("Request Messages");
		lblRequestInfo.setSize(120, 10);
		lblRequestInfo.setLocation(10, 15);
		panelRequestInfo.add(lblRequestInfo);

		cboRequestMessages = new JComboBox<String>();
		cboRequestMessages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cboRequestMessages_selectionChanged();
			}
		});

		cboRequestMessages.setSize(410, 20);
		cboRequestMessages.setLocation(150, 10);
		panelRequestInfo.add(cboRequestMessages);

		txtRequestInfo = new JTextArea();
		txtRequestInfo.setLineWrap(true);
		panelRequestInfo.add(txtRequestInfo);

		scbRequestInfo = new JScrollPane(txtRequestInfo);
		scbRequestInfo.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		scbRequestInfo.setSize(540, 630);
		scbRequestInfo.setLocation(10, 40);
		panelRequestInfo.add(scbRequestInfo);

		btnSendRequest = new JButton("Request");
		btnSendRequest.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnSend_Click();
			}

		});

		btnSendRequest.setSize(120, 20);
		btnSendRequest.setLocation(420, 680);
		panelRequestInfo.add(btnSendRequest);

		// response info

		panelResopnseInfo = new JPanel();
		panelResopnseInfo.setLayout(null);
		tabMain.addTab("Response", panelResopnseInfo);

		lblResponseInfo = new JLabel("Response Info");
		LayoutUtil.layoutComponent(this, 10, 10, 100, 10, lblResponseInfo);

		txtResponseInfo = new JTextArea();
		txtResponseInfo.setLineWrap(true);
		panelResopnseInfo.add(txtResponseInfo);

		scbResponseInfo = new JScrollPane(txtResponseInfo);
		scbResponseInfo.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		scbResponseInfo.setSize(540, 640);
		scbResponseInfo.setLocation(10, 40);
		panelResopnseInfo.add(scbResponseInfo);

		tabMain.setSize(560, 740);
		tabMain.setLocation(10, 10);
		tabMain.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				tabMain_resized();
			}
		});
		this.add(tabMain);

		LoadConnections();
		LoadRequestMessages();
	}

	void cboConnections_selectionChanged() {
		if (cboConnections.getSelectedItem() != null) {
			JsonObject selectedConnection = (JsonObject) connections.get(cboConnections.getSelectedItem().toString());
			if (selectedConnection == null)
				this.txtConnectionInfo.setText("");
			else
				this.txtConnectionInfo.setText(JsonUtil.toBeautifyString(selectedConnection));
		}
	}

	void cboRequestMessages_selectionChanged() {
		if (cboRequestMessages.getSelectedItem() != null) {
			JsonObject selectedMessage = (JsonObject) requestMessages
					.get(cboRequestMessages.getSelectedItem().toString());
			if (selectedMessage == null)
				this.txtRequestInfo.setText("");
			else
				this.txtRequestInfo.setText(JsonUtil.toBeautifyString(selectedMessage));
		}
	}

	void LoadConnections() throws JsonSyntaxException, IOException, URISyntaxException {
		connections = (JsonObject) new JsonParser()
				.parse(new String(getClass().getClassLoader().getResourceAsStream("Connections.json").readAllBytes()));

		cboConnections.addItem("");
		for (Entry<String, JsonElement> e : connections.entrySet()) {
			cboConnections.addItem(e.getKey());
		}
	}

	void LoadRequestMessages() throws JsonSyntaxException, IOException, URISyntaxException {
		requestMessages = (JsonObject) new JsonParser().parse(
				new String(getClass().getClassLoader().getResourceAsStream("RequestMessages.json").readAllBytes()));

		cboRequestMessages.addItem("");
		for (Entry<String, JsonElement> e : requestMessages.entrySet()) {
			cboRequestMessages.addItem(e.getKey());
		}
	}

	void btnConnect_Click() throws IOException, TimeoutException {
		jConnectionInfo = (JsonObject) new JsonParser().parse(txtConnectionInfo.getText());

		if (client.IsOpen() == true)
			client.close();

		client.connect(
				jConnectionInfo.get(BrunnerClientApi.msgFieldName_hostName).getAsString(),
				jConnectionInfo.get(BrunnerClientApi.msgFieldName_portNumber).getAsInt(),
				"admin",
				"admin",
				jConnectionInfo.get(BrunnerClientApi.msgFieldName_queueName).getAsString());

		if (client.IsOpen() == true) {
			this.tabMain.setSelectedIndex(1);
		}
	}

	void btnSend_Click() {
		try {
			this.txtResponseInfo.setText("");

			jMessageInfo = (JsonObject) new JsonParser().parse(txtRequestInfo.getText());

			JsonParser parser = new JsonParser();
			String strResponse = client.requestSync(
					jConnectionInfo.get(BrunnerClientApi.msgFieldName_queueName).getAsString(),
					jMessageInfo.toString(), jMessageInfo.get("requestTimeoutMS").getAsInt());

			if (strResponse != null) {
				JsonElement e = parser.parse(strResponse);

				if (e instanceof JsonNull) {
					this.txtResponseInfo.setText(String.format("Received Reply: JsonNull"));
				} else if (e instanceof JsonObject) {
					JsonObject jResponse = (JsonObject) e;

					this.txtResponseInfo.setText(JsonUtil.toBeautifyString(jResponse));
					this.tabMain.setSelectedIndex(2);
				} else {
					this.txtResponseInfo.setText(String.format("Received Reply: %s", strResponse));
				}
			} else
				this.txtResponseInfo.setText(String.format("Received Reply: null"));
		} catch (Exception e) {
			this.txtResponseInfo.setText(ExceptionUtil.getFullMessage(e));
			this.tabMain.setSelectedIndex(2);
		}
	}

	void frm_resized() {
		tabMain.setSize(this.getWidth() - 30, this.getHeight() - 60);
	}

	void tabMain_resized() {
		try {
			this.scbConnectionInfo.setSize(tabMain.getWidth() - 20, tabMain.getHeight() - 110);
			this.btnConnect.setLocation(tabMain.getWidth() - 140, tabMain.getHeight() - 60);
			this.cboRequestMessages.setSize(tabMain.getWidth() - 160, 20);
			this.scbRequestInfo.setSize(tabMain.getWidth() - 20, tabMain.getHeight() - 110);
			this.btnSendRequest.setLocation(tabMain.getWidth() - 140, tabMain.getHeight() - 60);
			this.scbResponseInfo.setSize(tabMain.getWidth() - 20, tabMain.getHeight() - 110);
		} catch (Exception e) {
			MessageBoxUtil.showExceptionMessageDialog(MainFrame.getInstance(), e);
		}
	}

	void centerWindow() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		setLocation(x, y);
	}
}
