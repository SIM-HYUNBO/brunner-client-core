package brunner.client;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

public class LayoutUtil {
	/***
	 * 메인 화면 폭: 1600
	 */
	public static int w_MainFrame = 1600;

	/***
	 * 메인 화면 높이: 900
	 */
	public static int h_MainFrame = 900;

	/***
	 * internal frame 폭: 1200
	 */
	public static int w_InternalFrame = 1200;

	/***
	 * internal frame 높이: 600
	 */
	public static int h_InternalFrame = 600;

	/***
	 * 화면 내부 마진: 10
	 */
	public static int marginInternal = 10;

	public static int marginRightHiddenInternal = 10;
	public static int marginBottomHiddenInternal = 30;

	/***
	 * 일반 버튼 폭: 100
	 */
	public static int w_NormalButton = 100;

	/***
	 * 일반 레이블 폭: 120
	 */
	public static int w_NormalLabel = 120;

	public static int w_NormalPanelControl = (w_InternalFrame / 2) - w_NormalLabel - (marginInternal * 4);

	/***
	 * 패널 컨트롤 폭: 290
	 */
	public static int w_WidePanelControl = 290;

	/***
	 * 일반 패널 컨트롤 높이: 30
	 */
	public static int h_NormalPanelControl = 30;

	/***
	 * 큰 패널 컨트롤 높이
	 */
	public static int h_TallPanelControl = 60;

	/***
	 * 가장 큰 패널 컨트롤 높이
	 */
	public static int h_TallestPanelControl = 90;

	/***
	 * 일반 테이블 높이: 200
	 */
	public static int h_NormalTable = 200;

	/***
	 * 짧은 테이블 높이: 100
	 */
	public static int h_ShortTable = 100;

	public static int y_BottomButton = h_InternalFrame - marginInternal - marginBottomHiddenInternal
			- h_NormalPanelControl;

	/***
	 * date picker 폭: 120
	 */
	public static int w_datePicker = 120;

	/***
	 * date picker 높이: 30
	 */
	public static int h_datePicker = 30;

	public static int w_fullWidthControl = w_InternalFrame - (marginInternal * 2) - marginRightHiddenInternal;

	/***
	 * 컨트롤 간 마진: 5
	 */
	public static int marginConstrols = 5;

	public static int x_ControlStart_1 = marginInternal;
	public static int x_ControlStart_2 = x_ControlStart_1 + w_NormalLabel + marginConstrols;

	/***
	 * 컨트롤 배치 시작 y 좌표: 10
	 */
	public static int y_ControlStart = 10;
	public static int y_BodyPanelStart = 110;

	public static int right_ControlStart_1 = (w_InternalFrame / 2) + marginInternal;
	public static int right_ControlStart_2 = right_ControlStart_1 + w_NormalLabel + marginConstrols;

	/***
	 * 가장 우측 버튼의 x 좌표
	 */
	public static int x_RightButton_1 = w_InternalFrame - marginInternal - w_NormalButton - marginRightHiddenInternal;

	/***
	 * 두번쨰 우측 버튼의 x 좌표
	 */
	public static int x_RightButton_2 = x_RightButton_1 - w_NormalButton - marginConstrols;

	/***
	 * 세번쨰 우측 버튼의 x 좌표
	 */
	public static int x_RightButton_3 = x_RightButton_2 - w_NormalButton - marginConstrols;

	public static void layoutComponent(JInternalFrame frame, int x, int y, int width, int height,
			JComponent component) {
		component.setLocation(x, y);
		component.setSize(width, height);
		frame.add(component);
	}

	public static void layoutComponent(JDialog dlg, int x, int y, int width, int height, JComponent component) {

		component.setLocation(x, y);
		component.setSize(width, height);
		dlg.add(component);
	}

	public static void layoutComponent(JFrame frame, int x, int y, int width, int height, JComponent component) {
		component.setLocation(x, y);
		component.setSize(width, height);
		frame.add(component);
	}
}