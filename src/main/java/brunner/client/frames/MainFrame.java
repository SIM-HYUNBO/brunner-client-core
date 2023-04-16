package brunner.client.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.google.gson.JsonObject;

import brunner.UIUtil;
import brunner.client.FrmBrunnerInternalFrame;
import brunner.client.IconUtil;
import brunner.client.JBrunnerTreeNode;
import brunner.client.LayoutUtil;
import brunner.client.api.AuthorityGroupProgram;
import brunner.client.api.BrunnerClientApi;
import mw.launchers.RPCClient;
import mw.utility.ExceptionUtil;
import brunner.client.MessageBoxUtil;
import brunner.client.PropertyUtil;
import mw.utility.StringUtil;

public class MainFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JDesktopPane desktopPane = new JDesktopPane() {
		Image img = UIUtil.getImage("background.jpg");

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.drawImage(this.img, 
						(int)(getSize().width - this.img.getWidth(null)) / 2, 
						(int)(getSize().height - this.img.getHeight(null)) / 2, 
						null);
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(this.img.getWidth(null), this.img.getHeight(null));
		}
	};

	JTree mainTree;
	FrmBrunnerLogin fLogin;
	JLabel statusLabel = null;
	JMenu mnuFile = null;
	JMenuItem mnuUserProfile = null;
	JMenuItem mnuLogout = null;
	JMenu mnuEdit = null;
	JMenuItem mnuUndo = null;
	JMenuItem mnuRedo = null;
	JMenuItem mnuCopy = null;
	JMenuItem mnuCut = null;
	JMenuItem mnuPaste = null;

	JMenu mnuLookAndFeel = null;
	JCheckBoxMenuItem mnuLookAndFeelCDEMotif = null;
	JMenuItem mnuLookAndFeelMetal = null;
	JCheckBoxMenuItem mnuLookAndFeelMetalDefault = null;
	JCheckBoxMenuItem mnuLookAndFeelMetalOcean = null;

	JCheckBoxMenuItem mnuLookAndFeelNimbus = null;
	JCheckBoxMenuItem mnuLookAndFeelWindows = null;
	JMenuItem mnuLookAndFeelJTatoo = null;
	JCheckBoxMenuItem mnuLookAndFeelJTatooAcryl = null;
	JCheckBoxMenuItem mnuLookAndFeelJTatooAero = null;
	JCheckBoxMenuItem mnuLookAndFeelJTatooAluminium = null;
	JCheckBoxMenuItem mnuLookAndFeelJTatooBernstein = null;
	JCheckBoxMenuItem mnuLookAndFeelJTatooFast = null;
	JCheckBoxMenuItem mnuLookAndFeelJTatooGraphite = null;
	JCheckBoxMenuItem mnuLookAndFeelJTatooHiFi = null;
	JCheckBoxMenuItem mnuLookAndFeelJTatooLuna = null;
	JCheckBoxMenuItem mnuLookAndFeelJTatooMcWin = null;
	JCheckBoxMenuItem mnuLookAndFeelJTatooMint = null;
	JCheckBoxMenuItem mnuLookAndFeelJTatooNoire = null;
	JCheckBoxMenuItem mnuLookAndFeelJTatooSmart = null;
	JCheckBoxMenuItem mnuLookAndFeelJTatooTexture = null;

	JMenu mnuWindow = null;
	JMenu mnuHelp = null;
	JMenuItem mnuAbout = null;

	JBrunnerTreeNode rootNode = null;

	JBrunnerTreeNode ndSystemMgmt = null;
	JBrunnerTreeNode ndCommonCode = null;

	JBrunnerTreeNode ndRegisterAuthorityGroup = null;
	JBrunnerTreeNode ndRegisterUserAuthorityGroup = null;
	JBrunnerTreeNode ndRegisterProgram = null;
	JBrunnerTreeNode ndRegisterAuthorityGroupProgram = null;

	JBrunnerTreeNode ndProduct = null;
	JBrunnerTreeNode ndRegisterProduct = null;
	JBrunnerTreeNode ndSales = null;
	JBrunnerTreeNode ndRegisterSales = null;
	JBrunnerTreeNode ndStock = null;
	JBrunnerTreeNode ndMoney = null;
	JBrunnerTreeNode ndCustomer = null;
	JBrunnerTreeNode ndRegisterCustomer = null;
	JBrunnerTreeNode ndReport = null;

	InputStream logoImage;
	static MainFrame instance;

	/* Construction de l'interface graphique */
	public MainFrame(FrmBrunnerLogin fLogin, String title) throws Exception {
		super(String.format("Brunner 판매 관리 시스템 v1.0 - %s", title));

		instance = this;

		this.setIconImage(IconUtil.getIconImage("brunnerLogo.png", 20, 20));

		this.fLogin = fLogin;
		userPrograms = new HashMap<String, JsonObject>();

		this.setSize(LayoutUtil.w_MainFrame, LayoutUtil.h_MainFrame);
		this.setLocationRelativeTo(null);

		// Mise en place du conteneur de sous-fenêtres
		desktopPane.setBackground(Color.gray);
		JPanel contentPane = (JPanel) this.getContentPane();
		contentPane.add(desktopPane, BorderLayout.CENTER);

		// Construction de la barre de menu
		createMenuBar();

		try {
			loadMenuTree(
					fLogin.getLoginUserInfo().get("SYSTEM_CODE").getAsString(),
					fLogin.getLoginUserInfo().get("USER_ID").getAsString());

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		mainTree = new JTree(rootNode);
		DefaultTreeCellRenderer renderer = new CustomTreeCellRenderer();
		mainTree.setCellRenderer(renderer);
		renderer.setTextSelectionColor(Color.white);
		renderer.setBackgroundSelectionColor(Color.blue);
		renderer.setBorderSelectionColor(Color.black);

		mainTree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) mainTree.getLastSelectedPathComponent();
					if (node == null)
						return;
					nodeMouseClick(node);
				} else if (e.getClickCount() == 2) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) mainTree.getLastSelectedPathComponent();
					if (node == null)
						return;
					nodeMouseDblClick(node);
				}
			}
		});

		// Autres Décorations
		contentPane.add(mainTree, BorderLayout.WEST);

		statusLabel = new JLabel();
		contentPane.add(statusLabel, BorderLayout.SOUTH);

		// Timer timer = new Timer(1000, this);
		// timer.setInitialDelay(0);
		// timer.start();
		UIUtil.setLookAndFeel(PropertyUtil.get("LookAndFeel"), this);
	}

	public static MainFrame getInstance() {
		return instance;
	}

	public FrmBrunnerLogin getLoginForm() {
		return fLogin;
	}

	public RPCClient getRPCClient() {
		return fLogin.getRPCClient();
	}

	public JsonObject getConnectionInfo() {
		return fLogin.getConnectionInfo();
	}

	void nodeMouseDblClick(DefaultMutableTreeNode node) {
		try {
			createInternalFrame(
					node,
					LayoutUtil.w_InternalFrame,
					LayoutUtil.h_InternalFrame,
					logoImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void nodeMouseClick(DefaultMutableTreeNode node) {

	}

	void loadMenuTree(String systemCode, String userId) throws Exception {
		rootNode = new JBrunnerTreeNode("Brunner 판매관리 시스템", "");
		loadMenuTree(systemCode, userId, rootNode);
	}

	HashMap<String, JsonObject> userPrograms = null;

	void loadMenuTree(
			String systemCode,
			String userId,
			JBrunnerTreeNode ndParent) throws Exception {

		JsonObject jReply;

		if (ndParent.equals(rootNode)) {
			jReply = AuthorityGroupProgram.getInstance().viewUserProgramList(
					fLogin.getRPCClient(),
					fLogin.getConnectionInfo(),
					systemCode,
					userId,
					ndParent.getId());
		} else {
			jReply = AuthorityGroupProgram.getInstance().viewUserProgramList(
					fLogin.getRPCClient(),
					fLogin.getConnectionInfo(),
					systemCode,
					userId,
					ndParent.getId());
		}

		if (jReply.get("resultCode").getAsString().equals(BrunnerClientApi.resultCode_success)) {
			for (int rowIndex = 0; rowIndex < jReply.get("programList").getAsJsonArray().size(); rowIndex++) {
				JsonObject jRowData = (JsonObject) jReply.get("programList").getAsJsonArray().get(rowIndex);
				String menuText = jRowData.get("programName").getAsString();
				JBrunnerTreeNode ndChild = new JBrunnerTreeNode(menuText, jRowData.get("programId").getAsString());
				ndParent.add(ndChild);

				userPrograms.put(jRowData.get("programName").getAsString(), jRowData);
				loadMenuTree(systemCode, userId, ndChild);
			}
		} else {
			MessageBoxUtil.showErrorMessageDialog(this, jReply.get("resultMessage").getAsString());
		}
	}

	JInternalFrame createInternalFrame(DefaultMutableTreeNode node, int width, int height, InputStream iconImage)
			throws IOException {
		FrmBrunnerInternalFrame window = null;
		JsonObject programInfo = userPrograms.get(node.toString());
		String programClassName = programInfo.get("programClassPath").getAsString();

		if (node.getChildCount() == 0) {
			if (StringUtil.isNullOrEmpty(programClassName) == false) {
				Class<?> c = null;
				if (programClassName.split(":").length == 2) {
					try {
						c = Class.forName(programClassName.split(":")[1]);
					} catch (ClassNotFoundException e) {
					}
					/*
					String jarPattern = programClassName.split(":")[0];
					try {
						try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(Paths.get("./lib"),
								jarPattern)) {
							Iterator<Path> ie = dirStream.iterator();
							while (true) {
								Path pathToJar = (Path) ie.next();
								if (pathToJar == null)
									break;

								URL[] urls = { new URL("jar:file:" + pathToJar + "!/") };
								URLClassLoader cl = URLClassLoader.newInstance(urls);
								c = cl.loadClass(programClassName.split(":")[1]);
								break;
							}
						}
					} catch (Exception e) {
						try {
							c = Class.forName(programClassName.split(":")[1]);
						} catch (ClassNotFoundException e1) {
						}
					}
					*/
				} else {
					try {
						c = Class.forName(programClassName);
					} catch (ClassNotFoundException e) {
					}
				}

				if (c == null) {
					MessageBoxUtil.showErrorMessageDialog(this,	String.format("화면을 불러오는 중 오류가 발생했습니다.\n%s", "화면 클래스를 찾을 수 없습니다"));

					return null;
				} else {
					JInternalFrame[] internalFrames = desktopPane.getAllFrames();
					for (int i = 0; i < internalFrames.length; i++) {
						if (c.getName().equals(internalFrames[i].getClass().getName())) {
							window = (FrmBrunnerInternalFrame) internalFrames[i];
							break;
						}
					}
				}
				try {
					if (window == null) {
						window = (FrmBrunnerInternalFrame) c.getDeclaredConstructor(
								fLogin.getRPCClient().getClass(),
								JsonObject.class,
								JsonObject.class,
								String.class,
								int.class,
								int.class,
								ImageIcon.class).newInstance(
			 							fLogin.getRPCClient(),
										fLogin.getConnectionInfo(),
										fLogin.getLoginUserInfo(),
										node.toString(),
										width,
										height,
										IconUtil.getIcon("brunnerLogo.png", 20, 20));

						window.setLocation(desktopPane.getComponentCount() * 25, desktopPane.getComponentCount() * 25);
						desktopPane.add(window);
					}

					// Pass the second frame to front
					try {
						window.moveToFront();
						window.setSelected(true);
					} catch (PropertyVetoException ex) {
						MessageBoxUtil.showErrorMessageDialog(this,	String.format("화면을 불러오는 중 오류가 발생했습니다.\n%s", ExceptionUtil.getFullMessage(ex)));
					}

					return window;
				} catch (Exception ex) {
					MessageBoxUtil.showErrorMessageDialog(this,	String.format("화면을 불러오는 중 오류가 발생했습니다.\n%s", ExceptionUtil.getFullMessage(ex)));
				}
			} else {
				MessageBoxUtil.showErrorMessageDialog(this, String.format("표시할 화면이 없습니다."));
			}
		}
		return null;
	}

	/* Methode de construction de la barre de menu */
	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		mnuFile = new JMenu("File");
		mnuUserProfile = new JMenuItem("User Profile");
		mnuUserProfile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mnuUserProfile_Click();
			}

		});
		mnuFile.add(mnuUserProfile);

		mnuLogout = new JMenuItem("Logout");
		mnuLogout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mnuLogout_Click(null);
			}

		});
		mnuFile.add(mnuLogout);
		menuBar.add(mnuFile);

		mnuEdit = new JMenu("Edit");
		mnuUndo = new JMenuItem("Undo");
		mnuEdit.add(mnuUndo);
		mnuRedo = new JMenuItem("Redo");
		mnuEdit.add(mnuRedo);
		mnuEdit.addSeparator();
		mnuCopy = new JMenuItem("Copy");
		mnuEdit.add(mnuCopy);
		mnuCut = new JMenuItem("Cut");
		mnuEdit.add(mnuCut);
		mnuPaste = new JMenuItem("Paste");
		mnuEdit.add(mnuPaste);
		menuBar.add(mnuEdit);

		mnuLookAndFeel = new JMenu("Look & Feel");
		mnuLookAndFeelCDEMotif = new JCheckBoxMenuItem("CDE/Motif");
		mnuLookAndFeelCDEMotif.addActionListener(this::mnuLookAndFeelCDEMotifListener);
		mnuLookAndFeel.add(mnuLookAndFeelCDEMotif);

		
		mnuLookAndFeelMetal = new JMenu("Metal");
		mnuLookAndFeelMetalDefault = new JCheckBoxMenuItem("Default Metal Theme");
		mnuLookAndFeelMetalDefault.addActionListener(this::mnuLookAndFeelMetalDefaultListener);
		mnuLookAndFeelMetal.add(mnuLookAndFeelMetalDefault);
		mnuLookAndFeelMetalOcean = new JCheckBoxMenuItem("Ocean Theme");
		mnuLookAndFeelMetalOcean.addActionListener(this::mnuLookAndFeelMetalOceanListener);
		mnuLookAndFeelMetal.add(mnuLookAndFeelMetalOcean);
		mnuLookAndFeel.add(mnuLookAndFeelMetal);

		mnuLookAndFeelNimbus = new JCheckBoxMenuItem("Nimbus");
		mnuLookAndFeelNimbus.addActionListener(this::mnuLookAndFeelNimbusListener);
		mnuLookAndFeel.add(mnuLookAndFeelNimbus);

		mnuLookAndFeelWindows = new JCheckBoxMenuItem("Windows");
		mnuLookAndFeel.add(mnuLookAndFeelWindows);
		mnuLookAndFeelWindows.addActionListener(this::mnuLookAndFeelWindowsListener);

		mnuLookAndFeelJTatoo = new JMenu("JTatoo");
		mnuLookAndFeelJTatooAcryl = new JCheckBoxMenuItem("Acryl");
		mnuLookAndFeelJTatooAcryl.addActionListener(this::mnuLookAndFeelJTatooAcrylListener);
		mnuLookAndFeelJTatoo.add(mnuLookAndFeelJTatooAcryl);
	 
		mnuLookAndFeelJTatooAero = new JCheckBoxMenuItem("Aero");
		mnuLookAndFeelJTatooAero.addActionListener(this::mnuLookAndFeelJTatooAeroListener);
		mnuLookAndFeelJTatoo.add(mnuLookAndFeelJTatooAero);
	 
		mnuLookAndFeelJTatooAluminium = new JCheckBoxMenuItem("Aluminium");
		mnuLookAndFeelJTatooAluminium.addActionListener(this::mnuLookAndFeelJTatooAluminiumListener);
		mnuLookAndFeelJTatoo.add(mnuLookAndFeelJTatooAluminium);

		mnuLookAndFeelJTatooBernstein = new JCheckBoxMenuItem("Bernstein");
		mnuLookAndFeelJTatooBernstein.addActionListener(this::mnuLookAndFeelJTatooBernsteinListener);
		mnuLookAndFeelJTatoo.add(mnuLookAndFeelJTatooBernstein);

		mnuLookAndFeelJTatooFast = new JCheckBoxMenuItem("Fast");
		mnuLookAndFeelJTatooFast.addActionListener(this::mnuLookAndFeelJTatooFastListener);
		mnuLookAndFeelJTatoo.add(mnuLookAndFeelJTatooFast);

		mnuLookAndFeelJTatooGraphite = new JCheckBoxMenuItem("Graphite");
		mnuLookAndFeelJTatooGraphite.addActionListener(this::mnuLookAndFeelJTatooGraphiteListener);
		mnuLookAndFeelJTatoo.add(mnuLookAndFeelJTatooGraphite);

		mnuLookAndFeelJTatooHiFi = new JCheckBoxMenuItem("HiFi");
		mnuLookAndFeelJTatooHiFi.addActionListener(this::mnuLookAndFeelJTatooHiFiListener);
		mnuLookAndFeelJTatoo.add(mnuLookAndFeelJTatooHiFi);

		mnuLookAndFeelJTatooLuna = new JCheckBoxMenuItem("Luna");
		mnuLookAndFeelJTatooLuna.addActionListener(this::mnuLookAndFeelJTatooLunaListener);
		mnuLookAndFeelJTatoo.add(mnuLookAndFeelJTatooLuna);

		mnuLookAndFeelJTatooMcWin = new JCheckBoxMenuItem("McWin");
		mnuLookAndFeelJTatooMcWin.addActionListener(this::mnuLookAndFeelJTatooMcWinListener);
		mnuLookAndFeelJTatoo.add(mnuLookAndFeelJTatooMcWin);

		mnuLookAndFeelJTatooMint = new JCheckBoxMenuItem("Mint");
		mnuLookAndFeelJTatooMint.addActionListener(this::mnuLookAndFeelJTatooMintListener);
		mnuLookAndFeelJTatoo.add(mnuLookAndFeelJTatooMint);

		mnuLookAndFeelJTatooNoire = new JCheckBoxMenuItem("Noire");
		mnuLookAndFeelJTatooNoire.addActionListener(this::mnuLookAndFeelJTatooNoireListener);
		mnuLookAndFeelJTatoo.add(mnuLookAndFeelJTatooNoire);

		mnuLookAndFeelJTatooSmart = new JCheckBoxMenuItem("Smart");
		mnuLookAndFeelJTatooSmart.addActionListener(this::mnuLookAndFeelJTatooSmartListener);
		mnuLookAndFeelJTatoo.add(mnuLookAndFeelJTatooSmart);

		mnuLookAndFeelJTatooTexture = new JCheckBoxMenuItem("Texture");
		mnuLookAndFeelJTatooTexture.addActionListener(this::mnuLookAndFeelJTatooTextureListener);
		mnuLookAndFeelJTatoo.add(mnuLookAndFeelJTatooTexture);

		mnuLookAndFeel.add(mnuLookAndFeelJTatoo);

		menuBar.add(mnuLookAndFeel);

		mnuWindow = new JMenu("Window");
		JMenuItem mnuCascade = new JMenuItem("Cascade");
		mnuCascade.addActionListener(this::mnuCascadeListener);
		mnuWindow.add(mnuCascade);
		JMenuItem mnuTileHorizontaly = new JMenuItem("Tile horizontaly");
		mnuTileHorizontaly.addActionListener(this::mnuTileHorizontalyListener);
		mnuWindow.add(mnuTileHorizontaly);
		JMenuItem mnuTileVerticaly = new JMenuItem("Tile verticaly");
		mnuTileVerticaly.addActionListener(this::mnuTileVerticalyListener);
		mnuWindow.add(mnuTileVerticaly);
		JMenuItem mnuIconifyAll = new JMenuItem("Iconify all");
		mnuIconifyAll.addActionListener(this::mnuIconifyAll);
		mnuWindow.add(mnuIconifyAll);
		menuBar.add(mnuWindow);

		mnuHelp = new JMenu("Help");
		mnuAbout = new JMenuItem("About Brunner ...");
		mnuAbout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mnuAbout_Click();
			}
		});

		mnuHelp.add(mnuAbout);
		menuBar.add(mnuHelp);

		this.setJMenuBar(menuBar);

		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				mnuLogout_Click(e);
			}
		});
	}

	void mnuAbout_Click() {
		FrmAbout fAbout = new FrmAbout(this.getLoginForm().getLoginUserInfo());
		fAbout.setVisible(true);
	}

	void mnuUserProfile_Click() {
		new FrmRegisterUser(this, this.fLogin, true).setVisible(true);
	}

	void mnuLogout_Click(WindowEvent e) {
		if (MessageBoxUtil.showConfirmMessageDialog(this, "로그아웃 하시겠습니끼?") == JOptionPane.YES_OPTION) {

			fLogin.initInput();
			fLogin.setVisible(true);

			while (desktopPane.getComponentCount() > 0)
				((JInternalFrame) desktopPane.getComponent(desktopPane.getComponentCount() - 1)).doDefaultCloseAction();

			this.setVisible(false);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);// yes
		} else
			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// cancel
	}

	/* Permet de cascader toutes les fenêtres internes */
	private void mnuCascadeListener(ActionEvent event) {
		JInternalFrame internalFrames[] = desktopPane.getAllFrames();
		for (int i = 0; i < internalFrames.length; i++) {
			internalFrames[internalFrames.length - i - 1].setBounds(i * 25, i * 25, LayoutUtil.w_InternalFrame,
					LayoutUtil.h_InternalFrame);
			internalFrames[internalFrames.length - i - 1].moveToFront();
		}
	}

	/* Permet d'aligner horizontalement toutes les fenêtres internes */
	private void mnuTileHorizontalyListener(ActionEvent event) {
		JInternalFrame internalFrames[] = desktopPane.getAllFrames();
		int frameWidth = desktopPane.getWidth() / internalFrames.length;
		for (int i = 0; i < internalFrames.length; i++) {
			internalFrames[i].setBounds(i * frameWidth, 0, frameWidth, desktopPane.getHeight());
			internalFrames[i].moveToFront();
		}
	}

	/* Permet d'aligner verticalement toutes les fenêtres internes */
	private void mnuTileVerticalyListener(ActionEvent event) {
		JInternalFrame internalFrames[] = desktopPane.getAllFrames();
		int frameHeight = desktopPane.getHeight() / internalFrames.length;
		for (int i = 0; i < internalFrames.length; i++) {
			internalFrames[i].setBounds(0, i * frameHeight, desktopPane.getWidth(), frameHeight);
			internalFrames[i].moveToFront();
		}
	}

	private void mnuLookAndFeelMetalDefaultListener(ActionEvent event) {
		try {
			UIUtil.setLookAndFeel("MetalDefault", this);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ExceptionUtil.getFullMessage(ex), "Exception", JOptionPane.ERROR_MESSAGE);
		}		
	}

	private void mnuLookAndFeelMetalOceanListener(ActionEvent event) {
		try {
			UIUtil.setLookAndFeel("MetalOcean", this);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ExceptionUtil.getFullMessage(ex), "Exception", JOptionPane.ERROR_MESSAGE);
		}		
	}

	private void mnuLookAndFeelCDEMotifListener(ActionEvent event) {
		try {
			UIUtil.setLookAndFeel("CDE/Motif", this);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ExceptionUtil.getFullMessage(ex), "Exception", JOptionPane.ERROR_MESSAGE);
		}				
	}
	private void mnuLookAndFeelNimbusListener(ActionEvent event) {
		try {
			UIUtil.setLookAndFeel("Nimbus", this);

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ExceptionUtil.getFullMessage(ex), "Exception", JOptionPane.ERROR_MESSAGE);
		}			
	}

	private void mnuLookAndFeelWindowsListener(ActionEvent event) {
		try {
			UIUtil.setLookAndFeel("Windows", this);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ExceptionUtil.getFullMessage(ex), "Exception", JOptionPane.ERROR_MESSAGE);
		}				
	}	

	private void mnuLookAndFeelJTatooAcrylListener(ActionEvent event) {
		try {
			UIUtil.setLookAndFeel("Acryl", this);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ExceptionUtil.getFullMessage(ex), "Exception", JOptionPane.ERROR_MESSAGE);
		}				
	}
	
	private void mnuLookAndFeelJTatooAeroListener(ActionEvent event) {
		try {
			UIUtil.setLookAndFeel("Aero", this);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ExceptionUtil.getFullMessage(ex), "Exception", JOptionPane.ERROR_MESSAGE);
		}				
	}

	private void mnuLookAndFeelJTatooAluminiumListener(ActionEvent event) {
		try {
			UIUtil.setLookAndFeel("Aluminum", this);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ExceptionUtil.getFullMessage(ex), "Exception", JOptionPane.ERROR_MESSAGE);
		}				
	}

	private void mnuLookAndFeelJTatooBernsteinListener(ActionEvent event) {
		try {
			UIUtil.setLookAndFeel("Bernstein", this);

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ExceptionUtil.getFullMessage(ex), "Exception", JOptionPane.ERROR_MESSAGE);
		}				
	}

	private void mnuLookAndFeelJTatooFastListener(ActionEvent event) {
		try {
			UIUtil.setLookAndFeel("Fast", this);

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ExceptionUtil.getFullMessage(ex),
					"Exception", JOptionPane.ERROR_MESSAGE);
		}				
	}

	private void mnuLookAndFeelJTatooGraphiteListener(ActionEvent event) {
		try {
			UIUtil.setLookAndFeel("Graphite", this);

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ExceptionUtil.getFullMessage(ex), "Exception", JOptionPane.ERROR_MESSAGE);
		}				
	}	

	private void mnuLookAndFeelJTatooHiFiListener(ActionEvent event) {
		try {
			UIUtil.setLookAndFeel("HiFi", this);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ExceptionUtil.getFullMessage(ex), "Exception", JOptionPane.ERROR_MESSAGE);
		}				
	}	

	private void mnuLookAndFeelJTatooLunaListener(ActionEvent event) {
		try {
			UIUtil.setLookAndFeel("Luna", this);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ExceptionUtil.getFullMessage(ex), "Exception", JOptionPane.ERROR_MESSAGE);
		}				
	}		

	private void mnuLookAndFeelJTatooMcWinListener(ActionEvent event) {
		try {
			UIUtil.setLookAndFeel("McWin", this);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ExceptionUtil.getFullMessage(ex), "Exception", JOptionPane.ERROR_MESSAGE);
		}				
	}	

	private void mnuLookAndFeelJTatooMintListener(ActionEvent event) {
		try {
			UIUtil.setLookAndFeel("Mint", this);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ExceptionUtil.getFullMessage(ex), "Exception", JOptionPane.ERROR_MESSAGE);
		}				
	}

	private void mnuLookAndFeelJTatooNoireListener(ActionEvent event) {
		try {
			UIUtil.setLookAndFeel("Noire", this);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ExceptionUtil.getFullMessage(ex), "Exception", JOptionPane.ERROR_MESSAGE);
		}				
	}

	private void mnuLookAndFeelJTatooSmartListener(ActionEvent event) {
		try {
			UIUtil.setLookAndFeel("Smart", this);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ExceptionUtil.getFullMessage(ex), "Exception", JOptionPane.ERROR_MESSAGE);
		}				
	}

	private void mnuLookAndFeelJTatooTextureListener(ActionEvent event) {
		try {
			UIUtil.setLookAndFeel("Texture", this);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ExceptionUtil.getFullMessage(ex), "Exception", JOptionPane.ERROR_MESSAGE);
		}				
	}	

	void setLookAndFeelMenu(JCheckBoxMenuItem onMenuItem){

		mnuLookAndFeelCDEMotif.setSelected(onMenuItem.getText() == mnuLookAndFeelCDEMotif.getText());
		mnuLookAndFeelMetalDefault.setSelected(onMenuItem.getText() == mnuLookAndFeelMetalDefault.getText());
		mnuLookAndFeelMetalOcean.setSelected(onMenuItem.getText() == mnuLookAndFeelMetalOcean.getText());
		mnuLookAndFeelNimbus.setSelected(onMenuItem.getText() == mnuLookAndFeelNimbus.getText());
		mnuLookAndFeelWindows.setSelected(onMenuItem.getText() == mnuLookAndFeelWindows.getText());
		mnuLookAndFeelJTatooAcryl.setSelected(onMenuItem.getText() == mnuLookAndFeelJTatooAcryl.getText());
		mnuLookAndFeelJTatooAero.setSelected(onMenuItem.getText() == mnuLookAndFeelJTatooAero.getText());
		mnuLookAndFeelJTatooAluminium.setSelected(onMenuItem.getText() == mnuLookAndFeelJTatooAluminium.getText());
		mnuLookAndFeelJTatooBernstein.setSelected(onMenuItem.getText() == mnuLookAndFeelJTatooBernstein.getText());
		mnuLookAndFeelJTatooFast.setSelected(onMenuItem.getText() == mnuLookAndFeelJTatooFast.getText());
		mnuLookAndFeelJTatooGraphite.setSelected(onMenuItem.getText() == mnuLookAndFeelJTatooGraphite.getText());
		mnuLookAndFeelJTatooHiFi.setSelected(onMenuItem.getText() == mnuLookAndFeelJTatooHiFi.getText());
		mnuLookAndFeelJTatooLuna.setSelected(onMenuItem.getText() == mnuLookAndFeelJTatooLuna.getText());
		mnuLookAndFeelJTatooMcWin.setSelected(onMenuItem.getText() == mnuLookAndFeelJTatooMcWin.getText());
		mnuLookAndFeelJTatooMint.setSelected(onMenuItem.getText() == mnuLookAndFeelJTatooMint.getText());
		mnuLookAndFeelJTatooNoire.setSelected(onMenuItem.getText() == mnuLookAndFeelJTatooNoire.getText());
		mnuLookAndFeelJTatooSmart.setSelected(onMenuItem.getText() == mnuLookAndFeelJTatooSmart.getText());
		mnuLookAndFeelJTatooTexture.setSelected(onMenuItem.getText() == mnuLookAndFeelJTatooTexture.getText());
	}

	/* Permet d'iconifier toutes les fenêtres internes */
	private void mnuIconifyAll(ActionEvent event) {
		JInternalFrame internalFrames[] = desktopPane.getAllFrames();
		for (int i = 0; i < internalFrames.length; i++) {
			try {
				internalFrames[internalFrames.length - i - 1].setIcon(true);
			} catch (PropertyVetoException exception) {
				exception.printStackTrace();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		int sec = cal.get(Calendar.SECOND);

		statusLabel.setText(year + "년 " + month + "월 " + day + "일 " + hour + "시 " + min + "분 " + sec + "초");
	}

	class CustomTreeCellRenderer extends DefaultTreeCellRenderer {
		private static final long serialVersionUID = 1L;

		ImageIcon brunnerLogoIcon = null;
		ImageIcon brunnerMenu1Icon = null;
		ImageIcon brunnerMenu2Icon = null;
		ImageIcon brunnerMenu3Icon = null;

		public CustomTreeCellRenderer() {
			brunnerLogoIcon = IconUtil.getIcon("brunnerLogo.png", 20, 20);
			brunnerMenu1Icon = IconUtil.getIcon("menu1.png", 20, 20);
			brunnerMenu2Icon = IconUtil.getIcon("menu2.png", 20, 20);
			brunnerMenu3Icon = IconUtil.getIcon("menu3.png", 20, 20);
		}

		public Component getTreeCellRendererComponent(
				JTree tree,
				Object value,
				boolean isSelected,
				boolean expanded,
				boolean leaf,
				int row,
				boolean hasFocus) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			String labelText = (String) node.getUserObject();

			if (node.getParent() == null) {
				this.setIcon(brunnerLogoIcon);
				this.setOpenIcon(brunnerLogoIcon);
				this.setClosedIcon(brunnerLogoIcon);
				this.setLeafIcon(brunnerLogoIcon);
			} else if (node.getParent().getParent() == null) {
				this.setIcon(brunnerMenu1Icon);
				this.setOpenIcon(brunnerMenu1Icon);
				this.setClosedIcon(brunnerMenu1Icon);
				this.setLeafIcon(brunnerMenu1Icon);
			} else if (node.getParent().getParent().getParent() == null) {
				this.setIcon(brunnerMenu2Icon);
				this.setOpenIcon(brunnerMenu2Icon);
				this.setClosedIcon(brunnerMenu2Icon);
				this.setLeafIcon(brunnerMenu2Icon);
			} else if (node.getParent().getParent().getParent().getParent() == null) {
				this.setIcon(brunnerMenu3Icon);
				this.setOpenIcon(brunnerMenu3Icon);
				this.setClosedIcon(brunnerMenu3Icon);
				this.setLeafIcon(brunnerMenu3Icon);
			} else {
				;
			}

			setText(labelText);
			return this;
		};

		public Color getBackgroundNonSelectionColor() {
			return (null);
		}

		public Color getBackground() {
			return (null);
		}
	}
	
	public void updateLookAndFeel(String lookAndFeelName){
		switch(lookAndFeelName){
			case "MetalDefault":
				setLookAndFeelMenu(mnuLookAndFeelMetalDefault);
				break;
			case "MetalOcean":
				setLookAndFeelMenu(mnuLookAndFeelMetalOcean);
				break;
			case "CDE/Motif":
				setLookAndFeelMenu(mnuLookAndFeelCDEMotif);
				break;			
			case "Nimbus":
				setLookAndFeelMenu(mnuLookAndFeelNimbus);
				break;			
			case "Windows":
				setLookAndFeelMenu(mnuLookAndFeelWindows);
				break;
			case "Acryl":
				setLookAndFeelMenu(mnuLookAndFeelJTatooAcryl);
				break;
			case "Aero":
				setLookAndFeelMenu(mnuLookAndFeelJTatooAero);
				break;
			case "Aluminum":
				setLookAndFeelMenu(mnuLookAndFeelJTatooAluminium);
				break;
			case "Bernstein":
				setLookAndFeelMenu(mnuLookAndFeelJTatooBernstein);
				break;
			case "Fast":
				setLookAndFeelMenu(mnuLookAndFeelJTatooFast);			
				break;
			case "Graphite":
				setLookAndFeelMenu(mnuLookAndFeelJTatooGraphite);
				break;
			case "HiFi":
				setLookAndFeelMenu(mnuLookAndFeelJTatooHiFi);
				break;
			case "Luna":
				setLookAndFeelMenu(mnuLookAndFeelJTatooLuna);
				break;
			case "McWin":
				setLookAndFeelMenu(mnuLookAndFeelJTatooMcWin);
				break;
			case "Mint":
				setLookAndFeelMenu(mnuLookAndFeelJTatooMint);
				break;
			case "Noire":
				setLookAndFeelMenu(mnuLookAndFeelJTatooNoire);
				break;
			case "Smart":
				setLookAndFeelMenu(mnuLookAndFeelJTatooSmart);
				break;
			case "Texture":
				setLookAndFeelMenu(mnuLookAndFeelJTatooTexture);
				break;
			default:
			;
		}
	}
}
