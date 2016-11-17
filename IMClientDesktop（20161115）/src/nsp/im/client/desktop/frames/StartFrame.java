package nsp.im.client.desktop.frames;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import nsp.im.client.desktop.base.ButtonStyleMouseAdapter;
import nsp.im.client.desktop.base.FramePanel;
import nsp.im.client.desktop.base.ImageCodeComponent;
import nsp.im.client.desktop.base.RoundPanel;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.desktop.base.TitleBar;
import nsp.im.client.desktop.simplegui.GetPasswordPanel;
import nsp.im.client.desktop.simplegui.GetPasswordPanel.PassListener;
import nsp.im.client.desktop.simplegui.LoginPanel;
import nsp.im.client.desktop.simplegui.LoginPanel.LoginListener;
import nsp.im.client.desktop.simplegui.SignupPanel;
import nsp.im.client.desktop.simplegui.SignupPanel.SignupListener;
import nsp.im.client.desktop.utils.ImageUtil;

/**
 * 登录整体界面
 */
@SuppressWarnings("serial")
public class StartFrame extends JFrame {
	private JLabel signupLabel;
	private JLabel forgetPasswordLabel;
	private LoginPanel loginPanel;
	private JPanel signupPanel1;// 20160525;登录页最下方页面；
	private SignupPanel signupPanel;// 20160511;注册页中主页面；
	private GetPasswordPanel getPasswordPanel;// 忘记密码页中主页面；
	private boolean g_dialog1State = false;// 20160512;
	private JDialog dialog1;// 20160512;
	private JDialog dialog2;// 20160731;
	ImageCodeComponent g_imageCodePane;//20160521;
	private TitleBar titleBar;

	public StartFrame() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());//默认为metal风格
		} catch (ClassNotFoundException |InstantiationException|IllegalAccessException|
				UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		addWindowListener(new WindowAdapter() {// 20160511;
			@Override
			public void windowActivated(WindowEvent e) {
				if (signupPanel != null && signupPanel.signState) {
					loginPanel.setUser(signupPanel.getUser());
					signupPanel.setSignState(false);
				}
			}
		});
		setTitle("IIE IM");
//		setAlwaysOnTop(true);// ********设置窗口总在屏幕最前方,20160512
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().createImage("res/NSP35.png"));// ****设置窗口后台显示图标；
		//setIconImage(Toolkit.getDefaultToolkit().createImage("res/blank.png"));// ****设置窗口后台显示图标；
		setUndecorated(true);// ******禁用标题栏；
		setBackground(new Color(55, 255, 55, 0));// ******黑色透明(0, 0, 0,
													// 0);登录按钮绿new Color(55,
													// 255, 55, 0)；
		FramePanel framePanel = new FramePanel();
		setContentPane(framePanel);
		framePanel.setBackground(StyleConsts.main_back);
		
		layComponents();
		setListeners();
	}

	private void layComponents() {
		titleBar = new TitleBar(this, false);// *****使用自定义标题栏的Panel；
		JLabel title = new JLabel("NSP IM");
		title.setIcon(new ImageIcon(ImageUtil.getImage("res/NSP35.png", 15)));
		title.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 14));
		title.setBorder(BorderFactory.createEmptyBorder(10, 12, 5, 5));
		// 20160511(5, 5, 5, 5);
		titleBar.setTitle(title, false);// *****设计【1-标题】；

		loginPanel = new LoginPanel();// *****设计【2-注册页】；

		signupPanel1 = new RoundPanel();// *****设计【3-注册页】；
		signupPanel1.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5,0,10,0);
		signupPanel1.setBackground(StyleConsts.main_back);
		forgetPasswordLabel = new JLabel("忘记密码");// *****【3.1-忘记密码】；
		forgetPasswordLabel.setForeground(StyleConsts.link_back);
		forgetPasswordLabel.setFont(new Font("新宋体", Font.BOLD, 14));
		forgetPasswordLabel.setHorizontalAlignment(JLabel.CENTER);
		signupPanel1.add(forgetPasswordLabel, constraints);
		signupLabel = new JLabel("注册账户");// *****【3.2-注册账户】；
		signupLabel.setForeground(StyleConsts.link_back);
		signupLabel.setFont(new Font("新宋体", Font.BOLD, 14));
		signupLabel.setHorizontalAlignment(JLabel.CENTER);
		constraints.gridx = 1;
		constraints.insets = new Insets(5,80,10,0);
		signupPanel1.add(signupLabel, constraints);

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		add(titleBar, c);// *****添加【titleBar】页；

		c.weighty = 1;
		c.gridy = 1;
		add(loginPanel, c);// *****添加【loginPanel】页；

		c.weightx = 0;
		c.weighty = 0;
		c.gridy = 2;
		add(signupPanel1, c);// *****添加【signupPanel1】页；
	}

	public void start() {
		loginPanel.start();
		setSize(262, 427);// (270, 450);(267, 432);
		setResizable(false);// 设置窗口不可调整大小；
	}

	private void setListeners() {
		/**
		 * @作者：lll
		 * @时间：2016年3月27日上午11:02:02 @主要功能：【注册标签颜色】变化设置； @修改内容：
		 */
		ButtonStyleMouseAdapter.attach(signupLabel, new ButtonStyleMouseAdapter() {
			@Override
			public void onRestore() {// *****恢复；
				signupLabel.setForeground(StyleConsts.link_back);
			}

			@Override
			public void onHover() {// *****悬停；
				signupLabel.setForeground(StyleConsts.link_back1);
			}

			@Override
			public void onAct() {// *****作用；
				signupLabel.setForeground(StyleConsts.link_back2);
			}
		});

		// 【注册标签】鼠标事件设置；
		signupLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() != MouseEvent.BUTTON1)
					return;
				g_dialog1State = true;// 20160512;
				showSignupDialog();
			}
		});
		
		/**
		 * @作者：lll
		 * @时间：2016年7月31日上午10:31:02 @主要功能：【忘记密码标签颜色】变化设置； @修改内容：
		 */
		ButtonStyleMouseAdapter.attach(forgetPasswordLabel, new ButtonStyleMouseAdapter() {
			@Override
			public void onRestore() {// *****恢复；
				forgetPasswordLabel.setForeground(StyleConsts.link_back);
			}
			
			@Override
			public void onHover() {// *****悬停；
				forgetPasswordLabel.setForeground(StyleConsts.link_back1);
			}
			
			@Override
			public void onAct() {// *****作用；
				forgetPasswordLabel.setForeground(StyleConsts.link_back2);
			}
		});
		
		// 【忘记密码标签】鼠标事件设置；
		forgetPasswordLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() != MouseEvent.BUTTON1)
					return;
				showGetPasswordDialog();
			}
		});
	}

	private void showSignupDialog() {
		dialog1 = new JDialog(this, "注册", true);// 20160512;
		dialog1.setResizable(false); //设置用户不可调整该对话框大小；
		dialog1.setIconImage(getIconImage());
		signupPanel = new SignupPanel();// 20160511;
//		signupPanel = new SignupPanel("res/before/bacground.png");
		signupPanel.start();
		signupPanel.setSignupListener(new SignupListener() {
			@Override
			public void onSignup() {
				g_dialog1State = false;// 20160512;
				dialog1.dispose();
			}
		});
		dialog1.add(signupPanel);
		dialog1.setSize(480,360);// (300, 250);(433, 332);(537,332);(480,360);
		 dialog1.setLocationRelativeTo(loginPanel);// 20160512;
		dialog1.setVisible(true);
	}
	
	private void showGetPasswordDialog() {
		dialog2 = new JDialog(this, "密码找回", true);
		dialog2.setResizable(false); //设置用户不可调整该对话框大小；
//		dialog2.setIconImage(getIconImage());
		getPasswordPanel = new GetPasswordPanel();
//		getPasswordPanel = new GetPasswordPanel("res/before/bacground.png");
		getPasswordPanel.start();
		getPasswordPanel.setPassListener(new PassListener() {
			@Override
			public void onPass() {
				dialog2.dispose();
			}
		});
		dialog2.add(getPasswordPanel);
		dialog2.setSize(480,360);// (300, 250);(433, 332);(537,332);(480,360);
		dialog2.setLocationRelativeTo(loginPanel);// 20160512;
		dialog2.setVisible(true);
	}

	public void setLoginListener(LoginListener l) {
		loginPanel.setLoginListener(l);
	}
}
