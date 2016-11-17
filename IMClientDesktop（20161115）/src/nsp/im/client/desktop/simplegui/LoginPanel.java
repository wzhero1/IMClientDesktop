package nsp.im.client.desktop.simplegui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.progressbar.WebProgressBar;

import nsp.im.client.desktop.base.ButtonStyleMouseAdapter;
import nsp.im.client.desktop.base.DocumentChangeAdapter;
import nsp.im.client.desktop.base.HintComboBox;
import nsp.im.client.desktop.base.HintField;
import nsp.im.client.desktop.base.HintPassField;
import nsp.im.client.desktop.base.ImageCodeComponent;
import nsp.im.client.desktop.base.RoundButton;
import nsp.im.client.desktop.base.RoundDialog;
import nsp.im.client.desktop.base.RoundLabel;
import nsp.im.client.desktop.base.RoundPanel;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.desktop.utils.Globals;
import nsp.im.client.desktop.utils.GridBagUtil;
import nsp.im.client.desktop.utils.ImageUtil;
import nsp.im.client.model.Account;
import nsp.im.client.model.AuthManager.AccountEntry;
import nsp.im.client.model.ex.IMException;

/**
 * 登录主体界面
 */
@SuppressWarnings("serial")
public class LoginPanel extends RoundPanel {
	private HintComboBox usernameBox;
	private JPasswordField passwordField;
	private RoundLabel avatarLabel;
	private JButton loginButton;
	// private JButton loginningButton;
	private WebCheckBox rememBox;
	private LoginListener listener;
	private HashMap<String, AccountEntry> accs;
	private int g_fail3 = 0;// 20160521;
	private boolean g_failed = false;// 20160521;
	private boolean g_loginning = false;// 20160909
	private JTextField g_codeField;// 20160521;
	private ImageCodeComponent g_imageCodeComponent;
	private JButton g_changeCodeButton;
	private GridBagConstraints constraints;
	private String g_code;// 20160525;
	// private Response<IMService> getConnection;
	// private IMService overtimeConnection;
	private WebProgressBar loginningButton;// #
	private boolean passInput;
	private AccountEntry current;

	public LoginPanel() {
		accs = new HashMap<>();
		doInitialization();// 登录界面控件初始化
		layComponents();// 布置组件
		setListeners();
		setBackground(StyleConsts.main_back);
		passInput = false;
	}

	public void start() {
		for (AccountEntry acc : Globals.getModel().getAuthManager()
				.getRemembered()) {
			accs.put(acc.getUsername(), acc);
			usernameBox.addItem(acc.getUsername());
		}
		loadUser();
		if (usernameBox.getItemCount() != 0) {
			usernameBox.setSelectedIndex(0);
		}
		usernameBox.getField().getDocument()
				.addDocumentListener(new DocumentChangeAdapter() {
					@Override
					public void onChange() {
						loadUser();
					}
				});
		passwordField.getDocument()
				.addDocumentListener(new DocumentChangeAdapter() {
					@Override
					public void onChange() {
						passInput = true;
					}
				});
	}

	private void doInitialization() {
		usernameBox = HintComboBox.createNormalComboBox("用户名");
		usernameBox.setFont(
				new Font("宋体", Font.PLAIN, usernameBox.getFont().getSize()));

		passwordField = HintPassField.createNormalField("密码");
		passwordField.setColumns(24);// (20);(15);
		passwordField.setFont(
				new Font("宋体", Font.PLAIN, passwordField.getFont().getSize()));

		rememBox = new WebCheckBox("记住密码");
		rememBox.setBackground(StyleConsts.main_back);
		rememBox.setFont(
				new Font("宋体", Font.PLAIN, rememBox.getFont().getSize()));
		rememBox.setForeground(StyleConsts.rempawd_gre);
		// *****可设计为选中状态字体颜色为深灰；
		loginButton = RoundButton.createNormalButton();
		loginButton.setText("登 录");
		loginButton.setFont(new Font("新宋体", Font.BOLD, 16));

		// test popup menu
		// TextRoundPopup menu = new TextRoundPopup();
		// menu.addItem("hello", new ActionListener() {
		// @Override
		// public void actionPerformed(ActionEvent e) {
		//
		// }
		// });
		// loginButton.setComponentPopupMenu(menu);

		// 20160909 登录中按钮
		loginningButton = new WebProgressBar();
		loginningButton.setIndeterminate(true);
		loginningButton.setPreferredSize(loginButton.getPreferredSize());
		// loginningButton = RoundButton.createNormalButton();
		// loginningButton.setText("登 录 中...");
		// loginningButton.setFont(new Font("新宋体", Font.BOLD, 16));

		// 尝试修改成圆角头像图片；
		avatarLabel = new RoundLabel();
		avatarLabel.setHorizontalAlignment(JButton.CENTER);

		g_codeField = HintField.createNormalField("验证码");
		g_codeField.setColumns(24);
		g_codeField.setFont(
				new Font("宋体", Font.PLAIN, usernameBox.getFont().getSize()));

		g_changeCodeButton = new JButton(new ImageIcon("res/refresh.png"));
		g_changeCodeButton.setToolTipText("点击换下一张【验证码】");
		g_changeCodeButton.setContentAreaFilled(false);// *****设置不绘制按钮的内容区域，默认为true,即绘制；
		g_changeCodeButton.setBorderPainted(false);// *****设置不绘制按钮的边框，默认为true,即绘制；
		g_changeCodeButton.setFocusPainted(false);

		g_imageCodeComponent = new ImageCodeComponent();
		g_code = g_imageCodeComponent.getNum();
	}

	public void layComponents() {
		setLayout(new GridBagLayout());
		constraints = new GridBagConstraints();// 用来控制添加进的组件的显示位置
		constraints.fill = GridBagConstraints.BOTH;// 在水平和垂直方向上同时调整组件大小
													// 当组件的显示区域大于它所请求的显示区域的大小时使用此字段

		if (!g_failed) {// 20160525;
			constraints.insets = new Insets(5, 5, 5, 5);// 组件与其显示区域之间间距的最小量，top,left,bottom,right
			GridBagUtil.setRectangle(constraints, 0, 1, 1, 1);
			add(usernameBox, constraints);// *****添加usernameBox;

			constraints.insets = new Insets(0, 5, 5, 5);// 20160525;
			GridBagUtil.setRectangle(constraints, 0, 2, 1, 1);
			add(passwordField, constraints);// *****添加passwordField;
			constraints.fill = GridBagConstraints.NONE;// 不重新调整组件大小
			constraints.anchor = GridBagConstraints.WEST;// 当组件小于其显示区域时使用anchor
															// 将组件置于其显示区域的左部，并在垂直方向上居中
			GridBagUtil.setRectangle(constraints, 0, 3, 1, 1);
			add(rememBox, constraints);// *****添加rememBox;
			rememBox.setFocusable(false);

			constraints.insets = new Insets(10, 5, 5, 5);// 20160525;
			constraints.fill = GridBagConstraints.BOTH;
			GridBagUtil.setRectangle(constraints, 0, 5, 1, 1);// 20160521;
			if (!g_loginning) {
				add(loginButton, constraints);// *****添加loginButton;
			} else {
				add(loginningButton, constraints);// *****添加loginningButton;
			}

			constraints.fill = GridBagConstraints.NONE;
			constraints.anchor = GridBagConstraints.CENTER;
			GridBagUtil.setRectangle(constraints, 0, 0, 1, 1);
			constraints.insets = new Insets(0, 5, 15, 5);// (20, 5, 20, 5);
			add(avatarLabel, constraints);// *****添加avatarLabel;
		} else {
			GridBagUtil.setRectangle(constraints, 1, 0, 1, 1);
			constraints.insets = new Insets(0, -20, 15, -10);// (20, 5, 20, 5);
			add(avatarLabel, constraints);// *****添加avatarLabel;

			constraints.insets = new Insets(0, -60, 5, -40);// (20, 5, 20, 5);
			GridBagUtil.setRectangle(constraints, 1, 1, 1, 1);
			add(usernameBox, constraints);// *****添加usernameBox;

			GridBagUtil.setRectangle(constraints, 1, 2, 1, 1);
			add(passwordField, constraints);// *****添加passwordField;

			GridBagUtil.setRectangle(constraints, 1, 3, 1, 1);
			add(g_codeField, constraints);// *****添加g_codeField;

			constraints.insets = new Insets(20, -60, 0, -40);// (20, 5, 20, 5);
			GridBagUtil.setRectangle(constraints, 1, 5, 1, 1);// 20160521;

			if (!g_loginning) {
				add(loginButton, constraints);// 20160909 *****添加loginButton;
			} else {
				add(loginningButton, constraints);// 20160909
													// *****添加loginningButton;
			}

			constraints.fill = GridBagConstraints.NONE;
			constraints.insets = new Insets(10, 0, 8, -10);
			rememBox.setBorder(new EmptyBorder(0, 0, 0, 0));
			GridBagUtil.setRectangle(constraints, 0, 4, 1, 1);
			add(rememBox, constraints);// *****添加rememBox;
			rememBox.setFocusable(false);

			constraints.fill = GridBagConstraints.BOTH;
			constraints.insets = new Insets(10, 0, 5, -5);
			GridBagUtil.setRectangle(constraints, 2, 4, 1, 1);
			add(g_changeCodeButton, constraints);

			constraints.anchor = GridBagConstraints.EAST;
			constraints.fill = GridBagConstraints.BOTH;
			constraints.insets = new Insets(1, 30, 1, 0);
			GridBagUtil.setRectangle(constraints, 1, 4, 1, 1);
			add(g_imageCodeComponent, constraints);// *****添加g_imageCodePane;
		}
		setFocusable(true);
	}

	// 重新加载登录页面
	private void changeLay() {
		setVisible(false);
		layComponents();
		setVisible(true);
		g_code = g_imageCodeComponent.getNum();
	}

	private void setListeners() {
		/**
		 * @作者：lll
		 * @时间：2016年3月27日下午12:11:06
		 * @主要功能：【记住密码字样处颜色】变化设置 @修改内容：
		 */
		ButtonStyleMouseAdapter.attach(rememBox, new ButtonStyleMouseAdapter() {
			@Override
			public void onRestore() {// *****恢复；
				if (!rememBox.isSelected()) {
					rememBox.setForeground(StyleConsts.rempawd_gre);// 组件背景色
				} else {
					rememBox.setForeground(Color.BLACK);
				}
			}

			@Override
			public void onHover() {// *****悬停；
				rememBox.setForeground(StyleConsts.rempawd_gre1);
			}

			@Override
			public void onAct() {// *****作用；
				rememBox.setForeground(Color.BLACK);
			}
		});

		listener = new LoginListener() {
			@Override
			public void onLogin() {
			}
		};

		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loginningState();
				// try{
				// Thread.sleep(1000L);
				// }catch (InterruptedException e1){
				// throw new RuntimeException(e1);
				// }
				testLogin();
			}
		});

		loginButton.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					loginningState();
					testLogin();
				}
			}
		});

		usernameBox.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					testLogin();
				}
			}
		});

		passwordField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					testLogin();
				}
			}
		});

		rememBox.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					testLogin();
				}
			}
		});

		g_codeField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					testLogin();
				}
			}
		});

		g_changeCodeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				g_imageCodeComponent.draw();
				g_code = g_imageCodeComponent.getNum();
			}
		});
	}

	public void testLogin() {
		String username = usernameBox.getText();
		String password = new String(passwordField.getPassword());
		String title = "登录失败";
		String info = "";
		if (username.equals("用户名") || username.length() == 0) {
			info = "检查用户名是否为空";
		} else if (password.equals("密码") || password.length() == 0) {
			info = "检查密码是否为空";
		}
		if (info.length() != 0) {
			RoundDialog.showMsg(this, title, info);// 20160526;
			loginRefresh();
			return;
		}

		if (g_failed) {
			String code = g_codeField.getText().toLowerCase();
			if (code.equals("验证码") || code.length() == 0) {
				info = "检查验证码是否为空";
				RoundDialog.showMsg(this, title, info);// 20160526;
				loginRefresh();
				return;
			}

			g_changeCodeButton.doClick();
			if (!code.equals(g_code.toLowerCase())) {// 20160526;
				RoundDialog.showMsg(this, title, "验证码错误");
				loginRefresh();
				return;
			}
		}

		// 20160910：解决卡死问题
		Thread testThread = new Thread(() -> {
			try {
				AccountEntry usedAcc = current;
				if (usedAcc == null)
					usedAcc = Globals.getModel().getAuthManager().makeEntry(usernameBox.getText());
				Account account = usedAcc.logIn(passInput ? password : null,
						rememBox.isSelected()).get();
				Globals.setAccount(account);
				listener.onLogin();
				g_loginning = false;// 20160909
				loginButton.setEnabled(true);
				g_fail3 = 0;// 20160523;
			} catch (IMException e) {
				if (e.getError().equals("BAD_NETWORK"))
					RoundDialog.showMsg(this, title, "连接失败");// 20160517;
				else
					RoundDialog.showMsg(this, title, "检查用户名密码是否正确");// 20160517;
				e.printStackTrace();
				g_fail3++;// 20160523;
				loginRefresh();
				if (!g_failed && g_fail3 >= 3) {
					g_failed = true;
					loginRefresh();
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		});
		testThread.start();
		// testThread.stop();

	}

	// 20160910解决卡死问题
	// private CompletableFuture<IMService> getConnect(){
	// new Thread(()->{
	// getConnection = server.connect(e -> {e.printStackTrace();});
	// }).start();
	// return getConnection;
	// }
	private void loadUser() {// 20160526;
		String username = usernameBox.getText();
		current = accs.get(username);
		if (current == null) {
			passwordField.setText("");
			rememBox.setSelected(false);
			passInput = false;
			avatarLabel.setIcon(new ImageIcon(ImageUtil.getImage("res/default_avatar.png", 100)));
		} else {
			if (current.passwordRemembered()) {
				passwordField.setText("********");
				rememBox.setSelected(true);
			} else {
				passwordField.setText("");
				rememBox.setSelected(false);
			}
			passInput = false;
			BufferedImage img = ImageUtil.scaleImage(
					ImageUtil.getImageOrDefault(current.getAvatar()), 100, 100);// 20160516;
			avatarLabel.setIcon(new ImageIcon(img));
		}
	}

	// 20160909显示登录中的界面
	private void loginningState() {
		loginningButton.setVisible(true);
		loginButton.setEnabled(false);
		loginButton.setVisible(false);
		g_loginning = true;
		changeLay();
	}

	// 20160909切换登录中的界面为正常登录界面
	private void loginRefresh() {
		loginningButton.setVisible(false);
		loginButton.setEnabled(true);
		loginButton.setVisible(true);
		g_loginning = false;
		changeLay();
	}

	public void setUser(String[] user) {// 20160511;
		usernameBox.setText(user[0]);
		passwordField.setText(user[1]);
		avatarLabel.setIcon(new ImageIcon(
				ImageUtil.getImage("res/default_avatar.png", 100)));
		rememBox.setSelected(false);
	}

	public void setLoginListener(LoginListener l) {
		listener = l;
	}

	public interface LoginListener {
		void onLogin();
	}
}
