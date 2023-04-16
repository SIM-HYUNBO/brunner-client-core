package brunner;

import java.awt.Image;
import java.awt.Window;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

import brunner.client.frames.MainFrame;

public class UIUtil {
	public static ImageIcon getIcon(String imageFileName, int width, int height) {
		InputStream imageStream = null;
		Image logoImage = null;
		Image scaledLogoImage = null;

		try {
			if (imageStream == null) {
				imageStream = UIUtil.class.getClassLoader().getResourceAsStream(imageFileName);
				logoImage = ImageIO.read(imageStream);
				scaledLogoImage = logoImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ImageIcon(scaledLogoImage);
	}

	public static Image getIconImage(String imageFileName, int width, int height) {
		InputStream imageStream = null;
		Image logoImage = null;
		Image scaledLogoImage = null;

		try {
			if (imageStream == null) {
				imageStream = UIUtil.class.getClassLoader().getResourceAsStream(imageFileName);
				logoImage = ImageIO.read(imageStream);
				scaledLogoImage = logoImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return scaledLogoImage;
	}

	public static Image getImage(String imageFileName) {
		InputStream imageStream = null;
		Image logoImage = null;

		try {
			if (imageStream == null) {
				imageStream = UIUtil.class.getClassLoader().getResourceAsStream(imageFileName);
				logoImage = ImageIO.read(imageStream);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return logoImage;
	}

    public static void setLookAndFeel(String lookAndFeelName, Window c) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, IOException{
		switch(lookAndFeelName){
			case "MetalDefault":
				for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
					if ("Metal".equals(info.getName())) {
						MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
						UIManager.setLookAndFeel(new MetalLookAndFeel());
						SwingUtilities.updateComponentTreeUI(c); // 모든 UI 변경
						break;
					}
				}			
				break;
			case "MetalOcean":
				for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
					if ("Metal".equals(info.getName())) {
						MetalLookAndFeel.setCurrentTheme(new OceanTheme());
						UIManager.setLookAndFeel(new MetalLookAndFeel());
						SwingUtilities.updateComponentTreeUI(c); // 모든 UI 변경
						break;
					}		
				}	
				break;
			case "CDE/Motif":
				for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
					if (lookAndFeelName.equals(info.getName())) {
						UIManager.setLookAndFeel(info.getClassName());
						SwingUtilities.updateComponentTreeUI(c); // 모든 UI 변경
						break;
					}
				}	
				break;			
			case "Nimbus":
				for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
					if (lookAndFeelName.equals(info.getName())) {
						UIManager.setLookAndFeel(info.getClassName());
						SwingUtilities.updateComponentTreeUI(c); // 모든 UI 변경
						break;
					}
				}	
				break;			
			case "Windows":
				for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
					if (lookAndFeelName.equals(info.getName())) {
						UIManager.setLookAndFeel(info.getClassName());
						SwingUtilities.updateComponentTreeUI(c); // 모든 UI 변경
						break;
					}
				}			
				break;
			case "Acryl":
				UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
				SwingUtilities.updateComponentTreeUI(c); // 모든 UI 변경
				break;
			case "Aero":
				UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
				SwingUtilities.updateComponentTreeUI(c); // 모든 UI 변경
				break;
			case "Aluminum":
				UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
				SwingUtilities.updateComponentTreeUI(c); // 모든 UI 변경
				break;
			case "Bernstein":
				UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
				SwingUtilities.updateComponentTreeUI(c); // 모든 UI 변경
				break;
			case "Fast":
				UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
				SwingUtilities.updateComponentTreeUI(c); // 모든 UI 변경
				break;
			case "Graphite":
				UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
				SwingUtilities.updateComponentTreeUI(c); // 모든 UI 변경
				break;
			case "HiFi":
				UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
				SwingUtilities.updateComponentTreeUI(c); // 모든 UI 변경
				break;
			case "Luna":
				UIManager.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");
				SwingUtilities.updateComponentTreeUI(c); // 모든 UI 변경
				break;
			case "McWin":
				UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
				SwingUtilities.updateComponentTreeUI(c); // 모든 UI 변경
				break;
			case "Mint":
				UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
				SwingUtilities.updateComponentTreeUI(c); // 모든 UI 변경
				break;
			case "Noire":
				UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
				SwingUtilities.updateComponentTreeUI(c); // 모든 UI 변경
				break;
			case "Smart":
				UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
				SwingUtilities.updateComponentTreeUI(c); // 모든 UI 변경
				break;
			case "Texture":
				UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
				SwingUtilities.updateComponentTreeUI(c); // 모든 UI 변경
				break;
			default:
			;
		}
        if(c instanceof MainFrame)
            ((MainFrame)c).updateLookAndFeel(lookAndFeelName);
	}
}
