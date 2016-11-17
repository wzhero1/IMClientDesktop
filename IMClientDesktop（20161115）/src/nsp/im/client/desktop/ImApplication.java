package nsp.im.client.desktop;

import java.net.InetSocketAddress;

import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.FontUIResource;

import com.alee.laf.WebLookAndFeel;

import nsp.im.client.desktop.base.RoundDialog;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.desktop.frames.MainFrame;
import nsp.im.client.desktop.frames.StartFrame;
import nsp.im.client.desktop.simplegui.ContactTransList;
import nsp.im.client.desktop.utils.Globals;
import nsp.im.client.model.ModelRoot;

public class ImApplication {

	public ImApplication(String ip, int port, String dir) {
		Globals.setModel(new ModelRoot(new InetSocketAddress(ip, port)));
	}

	public void run() {
		setStyle();
		showLoginFrame();
	}

	private void showLoginFrame() {
		final StartFrame sf = new StartFrame();
		sf.setLoginListener(() -> SwingUtilities.invokeLater(() -> {
			sf.dispose();
			showMainFrame();
		}));
		sf.start();
		sf.setVisible(true);
		sf.setLocationRelativeTo(null);// 将窗口至于屏幕的中央
	}

	private void showMainFrame() {
		final MainFrame cf = new MainFrame();
		ContactTransList.cf = cf;
		cf.start();
		cf.setVisible(true);
		cf.setLocationRelativeTo(null);

		cf.startEvents();

		cf.setLogoutListener(() -> {
			Globals.getAccount().logOut().onSuccess((nil) -> {
				cf.dispose();
				showLoginFrame();
				Globals.setAccount(null);
			});
		});

		Globals.getAccount().getKickOffNotifier().getKickOffEvent()
				.addListener(() -> {
					cf.dispose();
					showLoginFrame();
					Globals.setAccount(null);
					RoundDialog.showMsg("已下线", "账号在另一处登录");
				});
	}

	private void setStyle() {
		setWhole();
		setFonts();
		setPopup();
		ToolTipManager.sharedInstance().setInitialDelay(200);// 工具提示，显示延迟毫秒数
	}

	private void setWhole() {
		try {
			// UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");//设置图形界面外观,默认为nimbus
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());// 默认为metal风格
			WebLookAndFeel.initializeManagers();
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	// private void setWhole() {
	// try {
	// WebLookAndFeel.globalControlFont = new
	// FontUIResource(StyleConsts.default_font);
	// UIManager.setLookAndFeel(new WebLookAndFeel());
	// WebLookAndFeel.initializeManagers();
	// } catch (UnsupportedLookAndFeelException e) {
	// e.printStackTrace();
	// }
	// }

	private void setFonts() {
		FontUIResource val = new FontUIResource(StyleConsts.default_font);
		UIManager.put("Button.font", val);
		UIManager.put("ToggleButton.font", val);
		UIManager.put("RadioButton.font", val);
		// UIManager.put("CheckBox.font", val);
		UIManager.put("ColorChooser.font", val);
		UIManager.put("ComboBox.font", val);
		// UIManager.put("Label.font", val);
		UIManager.put("List.font", val);
		UIManager.put("MenuBar.font", val);
		UIManager.put("MenuItem.font", val);
		UIManager.put("RadioButtonMenuItem.font", val);
		UIManager.put("CheckBoxMenuItem.font", val);
		UIManager.put("Menu.font", val);
		UIManager.put("PopupMenu.font", val);
		// UIManager.put("OptionPane.font", val);
		UIManager.put("Panel.font", val);
		UIManager.put("ProgressBar.font", val);
		UIManager.put("ScrollPane.font", val);
		UIManager.put("Viewport.font", val);
		UIManager.put("TabbedPane.font", val);
		UIManager.put("Table.font", val);
		UIManager.put("TableHeader.font", val);
		UIManager.put("TextField.font", val);
		// UIManager.put("PasswordField.font", val);
		UIManager.put("TextArea.font", val);
		UIManager.put("TextPane.font", val);
		UIManager.put("EditorPane.font", val);
		UIManager.put("TitledBorder.font", val);
		// UIManager.put("ToolBar.font", val);
		UIManager.put("ToolTip.font", val);
		UIManager.put("Tree.font", val);
	}

	// 弹出菜单
	private void setPopup() {
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);// 避免弹出窗口被其他组件覆盖
	}
}
