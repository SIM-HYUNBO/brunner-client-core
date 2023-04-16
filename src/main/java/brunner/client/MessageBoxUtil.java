package brunner.client;

import java.awt.Component;

import javax.swing.JOptionPane;

import mw.utility.ExceptionUtil;

public class MessageBoxUtil {

	public static void showInformationMessageDialog(Component parentComponent, String message) {
		JOptionPane.showMessageDialog(parentComponent, message, "Information", JOptionPane.INFORMATION_MESSAGE);
	}	

	public static void showWarningMessageDialog(Component parentComponent, String message) {
		JOptionPane.showMessageDialog(parentComponent, message, "Warning", JOptionPane.WARNING_MESSAGE);
	}
	
	public static void showErrorMessageDialog(Component parentComponent, String message) {
		JOptionPane.showMessageDialog(parentComponent, message, "Errror", JOptionPane.ERROR_MESSAGE);
	}

	public static void showExceptionMessageDialog(Component parentComponent, String message) {
		JOptionPane.showMessageDialog(parentComponent, message, "Exception", JOptionPane.ERROR_MESSAGE);
	}

	public static void showExceptionMessageDialog(Component parentComponent, Exception ex) {
		JOptionPane.showMessageDialog(parentComponent, ExceptionUtil.getFullMessage(ex), "Exception", JOptionPane.ERROR_MESSAGE);
	}	
	
	public static int showConfirmMessageDialog(Component parentComponent, String message) {
		return JOptionPane.showConfirmDialog(parentComponent, message, "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}	
}
