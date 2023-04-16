package brunner.client;

import javax.swing.table.DefaultTableModel;

public class BrunnerTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	boolean cellEditable;

	public BrunnerTableModel(Object[][] data, Object[] columnNames, boolean cellEditable) {
		super(data, columnNames);
		this.cellEditable = cellEditable;
	}

	public boolean isCellEditable() {
		return cellEditable;
	}
}
