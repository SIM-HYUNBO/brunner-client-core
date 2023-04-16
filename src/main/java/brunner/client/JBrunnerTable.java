package brunner.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class JBrunnerTable extends JTable {

	private static final long serialVersionUID = 1L;

	String title;
	JPopupMenu popup;
	String currentFilter = "";;

	public JBrunnerTable(String title) {
		this.title = title;
		popup = new JPopupMenu();
		JMenuItem mnuExportToExcel = new JMenuItem("Export to Excel");
		mnuExportToExcel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					exportToExcel(String.format("%s.xls", title));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		popup.add(mnuExportToExcel);

		JMenuItem mnuFilter = new JMenuItem("Filter");
		mnuFilter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				applyFilter();
			}

		});
		popup.add(mnuFilter);

		JTableHeader header = this.getTableHeader();
		header.addMouseListener(new BrunnerTableHeaderMouseListener(this));

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {

			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					triggerPopup(e);
				}
			}
		});
	}

	public void triggerPopup(MouseEvent e) {
		if (e.getSource() instanceof JTable) {
			JTable source = (JTable) e.getSource();

			int row = source.rowAtPoint(e.getPoint());
			int column = source.columnAtPoint(e.getPoint());

			if (!source.isRowSelected(row))
				source.changeSelection(row, column, false, false);
		}
		popup.show(e.getComponent(), e.getX(), e.getY());
	}

	void exportToExcel(String filePath) throws Exception {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet(this.title);

		Row row;
		Cell cell;

		// write the column headers
		row = sheet.createRow(0);
		for (int c = 0; c < getModel().getColumnCount(); c++) {
			cell = row.createCell(c);
			cell.setCellValue(getModel().getColumnName(c));
		}

		// write the data rows
		for (int r = 0; r < getModel().getRowCount(); r++) {
			row = sheet.createRow(r + 1);
			for (int c = 0; c < getModel().getColumnCount(); c++) {
				cell = row.createCell(c);
				Object value = getModel().getValueAt(r, c);
				if (value instanceof String) {
					cell.setCellValue((String) value);
				} else if (value instanceof Double) {
					cell.setCellValue((Double) value);
				}
			}
		}

		FileOutputStream out = new FileOutputStream(filePath);
		workbook.write(out);
		out.close();
		workbook.close();

		MessageBoxUtil.showInformationMessageDialog(null,
				String.format("[%s] 파일을 저장했습니다.", new File(filePath).getAbsolutePath()));

		runExcel(new File(filePath).getAbsolutePath().toString());
	}

	public static void runExcel(String filePath) {
		try {
			java.awt.Desktop.getDesktop().open(new File(filePath)); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void applyFilter() {
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(this.getModel());
		String strFilter = JOptionPane.showInputDialog("Table filter : ", currentFilter);
		if (strFilter != null) {
			currentFilter = strFilter;
			sorter.setRowFilter(RowFilter.regexFilter(currentFilter));
		} else {
			sorter.setRowFilter(RowFilter.regexFilter(currentFilter));
		}
		this.setRowSorter(sorter);
	}

	public String getCurrentFilter() {
		return currentFilter;
	}

	public static JBrunnerTable getNewInstance(String title) {
		return new JBrunnerTable(title) {
			private static final long serialVersionUID = 1L;

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component comp = super.prepareRenderer(renderer, row, column);
				int modelRow = convertRowIndexToModel(row);
				if (!isRowSelected(modelRow))
					comp.setBackground(Color.WHITE);
				else
					comp.setBackground(Color.LIGHT_GRAY);
				return comp;
			}
		};
	}

	public static DefaultTableCellRenderer getDefaultTableCellRenderer() {
		return new DefaultTableCellRenderer() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				if (value instanceof String) {
					String amount = value.toString();
					this.setText(amount);
					this.setFont(new Font(null, Font.PLAIN, 12)); // 보통 글꼴
					this.setHorizontalAlignment(SwingConstants.CENTER); // 가운데 정렬
				}
				return this;
			}
		};
	}

	public static void resizeColumnWidth(JTable table) {
		final TableColumnModel columnModel = table.getColumnModel();
		for (int column = 0; column < table.getColumnCount(); column++) {
			int width = 50; // Min width
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, column);
				Component comp = table.prepareRenderer(renderer, row, column);
				width = Math.max(comp.getPreferredSize().width + 1, width);
			}
			columnModel.getColumn(column).setPreferredWidth(width);
		}
	}

}
