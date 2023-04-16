package brunner.client.internalFrames;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.google.gson.JsonObject;

import brunner.client.BrunnerTableModel;
import brunner.client.FrmBrunnerInternalFrame;
import brunner.client.JBrunnerTable;
import brunner.client.LayoutUtil;
import brunner.client.MessageBoxUtil;
import brunner.client.api.BrunnerClientApi;
import brunner.client.api.CommonCode;
import brunner.client.frames.FrmCommonCode;
import brunner.client.frames.FrmCommonCodeGroup;
import mw.launchers.RPCClient;

/***
 * 공통코드관리
 */
public class FrmRegisterCommonCode extends FrmBrunnerInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel lblCommonCodeGroup;
	public JBrunnerTable tblCommonCodeGroup;
	JScrollPane scCommonCodeGroup;
	JButton btnRegisterCommonCodeGroup;
	JButton btnViewCodeGroupList;

	JLabel lblCommonCodeItem;
	public JBrunnerTable tblCommonCodeItem;
	JScrollPane scCommonCodeItem;

	JButton btnRegisterCommonCode;

	FrmCommonCodeGroup fRegisterCommonCodeGroup;
	FrmCommonCode fRegisterCommonCode;

	public FrmRegisterCommonCode(RPCClient rpcClient, JsonObject connectionInfo, JsonObject loginUserInfo, String title,
			int width, int height, ImageIcon icon) {
		super(rpcClient, connectionInfo, loginUserInfo, title, width, height, icon);
	}

	protected void initLayout(int width, int height) {
		super.initLayout(width, height);

		this.setLayout(null);

		int y_controlPosition = LayoutUtil.y_ControlStart;

		lblCommonCodeGroup = new JLabel("공통코드그룹");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, y_controlPosition, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblCommonCodeGroup);

		btnViewCodeGroupList = new JButton("조회");
		btnViewCodeGroupList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					btnViewCodeGroupList_Click();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		LayoutUtil.layoutComponent(this, LayoutUtil.x_RightButton_1, y_controlPosition, LayoutUtil.w_NormalButton,
				LayoutUtil.h_NormalPanelControl, btnViewCodeGroupList);

		String groupHeaders[] = { "시스템번호", "그룹아이디", "그룹설명", "속성 1", "속성 2", "속성 3", "속성 4", "속성 5" };
		String groupContents[][] = {};

		tblCommonCodeGroup = JBrunnerTable.getNewInstance(lblCommonCodeGroup.getText());

		tblCommonCodeGroup.setModel(new BrunnerTableModel(groupContents, groupHeaders, false));
		tblCommonCodeGroup.setShowHorizontalLines(true);
		tblCommonCodeGroup.setGridColor(Color.GRAY);
		tblCommonCodeGroup.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
					int row = tblCommonCodeGroup.getSelectedRow();
					try {
						tblCommonCodeGroup_rowSelected(row);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		tblCommonCodeGroup.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				int row = tblCommonCodeGroup.getSelectedRow();
				try {
					tblCommonCodeGroup_rowSelected(row);
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

		((DefaultTableCellRenderer) tblCommonCodeGroup.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(JLabel.CENTER);

		scCommonCodeGroup = new JScrollPane(tblCommonCodeGroup);

		for (int colIndex = 0; colIndex < tblCommonCodeGroup.getColumnCount(); colIndex++)
			tblCommonCodeGroup.getColumnModel().getColumn(colIndex)
					.setCellRenderer(JBrunnerTable.getDefaultTableCellRenderer());

		y_controlPosition += btnViewCodeGroupList.getHeight();
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, y_controlPosition, LayoutUtil.w_fullWidthControl,
				LayoutUtil.h_NormalTable, scCommonCodeGroup);

		btnRegisterCommonCodeGroup = new JButton("등록");
		btnRegisterCommonCodeGroup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnRegisterCommonCodeGroup_Click();
			}
		});

		y_controlPosition += scCommonCodeGroup.getHeight();
		LayoutUtil.layoutComponent(this, LayoutUtil.x_RightButton_1, y_controlPosition, LayoutUtil.w_NormalButton,
				LayoutUtil.h_NormalPanelControl, btnRegisterCommonCodeGroup);

		y_controlPosition += btnViewCodeGroupList.getHeight();

		lblCommonCodeItem = new JLabel("공통코드");
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, y_controlPosition, LayoutUtil.w_NormalLabel,
				LayoutUtil.h_NormalPanelControl, lblCommonCodeItem);

		String itemHeaders[] = { "코드아이디", "코드설명", "키 1", "키 2", "속성 1", "속성 2", "속성 3", "속성 4", "속성 5" };
		String itemContents[][] = {};

		tblCommonCodeItem = JBrunnerTable.getNewInstance(lblCommonCodeItem.getText());
		tblCommonCodeItem.setModel(new BrunnerTableModel(itemContents, itemHeaders, false));
		tblCommonCodeItem.setShowHorizontalLines(true);
		tblCommonCodeItem.setGridColor(Color.GRAY);

		((DefaultTableCellRenderer) tblCommonCodeItem.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(JLabel.CENTER);

		scCommonCodeItem = new JScrollPane(tblCommonCodeItem);

		for (int colIndex = 0; colIndex < tblCommonCodeItem.getColumnCount(); colIndex++)
			tblCommonCodeItem.getColumnModel().getColumn(colIndex)
					.setCellRenderer(JBrunnerTable.getDefaultTableCellRenderer());

		y_controlPosition += lblCommonCodeItem.getHeight();
		LayoutUtil.layoutComponent(this, LayoutUtil.x_ControlStart_1, y_controlPosition, LayoutUtil.w_fullWidthControl,
				LayoutUtil.h_NormalTable, scCommonCodeItem);

		btnRegisterCommonCode = new JButton("등록");
		btnRegisterCommonCode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnRegisterCommonCode_Click();
			}
		});

		y_controlPosition += scCommonCodeItem.getHeight();
		LayoutUtil.layoutComponent(this, LayoutUtil.x_RightButton_1, y_controlPosition, LayoutUtil.w_NormalButton,
				LayoutUtil.h_NormalPanelControl, btnRegisterCommonCode);
	}

	public RPCClient getRPCClient() {
		return this.rpcClient;
	}

	public JsonObject getLoginUserInfo() {
		return this.loginUserInfo;
	}

	public JsonObject getConnectionInfo() {
		return this.connectionInfo;
	}

	public void btnViewCodeGroupList_Click() throws Exception {
		JsonObject jReply = CommonCode.getInstance().viewCommonCodeGroupList(this.rpcClient, this.connectionInfo,
				this.loginUserInfo.get("SYSTEM_CODE").getAsString(), "");

		if (jReply.get("resultCode").getAsString().equals(BrunnerClientApi.resultCode_success)) {
			DefaultTableModel modelCodeGroupList = (DefaultTableModel) tblCommonCodeGroup.getModel();
			tblCommonCodeGroup.clearSelection();

			while (modelCodeGroupList.getRowCount() > 0)
				modelCodeGroupList.removeRow(0);

			for (int rowIndex = 0; rowIndex < jReply.get("commonCodeItemList").getAsJsonArray().size(); rowIndex++) {
				JsonObject jRowData = (JsonObject) jReply.get("commonCodeItemList").getAsJsonArray().get(rowIndex);

				modelCodeGroupList.addRow(new Object[] { jRowData.get("systemCode").getAsString(),
						jRowData.get("commonCodeGroupId").getAsString(),
						jRowData.get("commonCodeGroupDesc").getAsString(),
						jRowData.get("attribute1").getAsString(),
						jRowData.get("attribute2").getAsString(),
						jRowData.get("attribute3").getAsString(),
						jRowData.get("attribute4").getAsString(),
						jRowData.get("attribute5").getAsString()
				});
			}

			DefaultTableModel modelCodeItemList = (DefaultTableModel) tblCommonCodeItem.getModel();

			while (modelCodeItemList.getRowCount() > 0)
				modelCodeItemList.removeRow(0);
		} else {
			MessageBoxUtil.showErrorMessageDialog(this, jReply.get("resultMessage").getAsString());
		}

		JBrunnerTable.resizeColumnWidth(tblCommonCodeGroup);
	}

	public void tblCommonCodeGroup_rowSelected(int row) throws Exception {
		String systemCode = (String) tblCommonCodeGroup.getModel().getValueAt(row, 0);
		String commonCodeGroupId = (String) tblCommonCodeGroup.getModel().getValueAt(row, 1);

		JsonObject jReply = CommonCode.getInstance().viewCommonCodeItemList(this.rpcClient, this.connectionInfo,
				systemCode, commonCodeGroupId);

		if (jReply.get("resultCode").getAsString().equals(BrunnerClientApi.resultCode_success)) {
			DefaultTableModel model = (DefaultTableModel) tblCommonCodeItem.getModel();
			tblCommonCodeItem.clearSelection();

			while (model.getRowCount() > 0)
				model.removeRow(0);

			for (int rowIndex = 0; rowIndex < jReply.get("commonCodeItemList").getAsJsonArray().size(); rowIndex++) {
				JsonObject jRowData = (JsonObject) jReply.get("commonCodeItemList").getAsJsonArray().get(rowIndex);

				model.addRow(new Object[] { jRowData.get("commonCodeId").getAsString(),
						jRowData.get("commonCodeDesc").getAsString(),
						jRowData.get("key1").getAsString(),
						jRowData.get("key2").getAsString(),
						jRowData.get("attribute1").getAsString(),
						jRowData.get("attribute2").getAsString(),
						jRowData.get("attribute3").getAsString(),
						jRowData.get("attribute4").getAsString(),
						jRowData.get("attribute5").getAsString()
				});
			}
		} else {
			MessageBoxUtil.showErrorMessageDialog(this, jReply.get("resultMessage").getAsString());
		}
	}

	void btnRegisterCommonCodeGroup_Click() {
		fRegisterCommonCodeGroup = new FrmCommonCodeGroup(this);
		fRegisterCommonCodeGroup.setVisible(true);
	}

	void btnRegisterCommonCode_Click() {
		fRegisterCommonCode = new FrmCommonCode(this);
		fRegisterCommonCode.setVisible(true);
	}
}
