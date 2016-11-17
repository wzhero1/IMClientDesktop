package nsp.im.client.desktop.simplegui;

import static java.lang.Thread.sleep;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.alee.laf.checkbox.WebCheckBox;

import nsp.im.client.desktop.base.ButtonStyleMouseAdapter;
import nsp.im.client.desktop.base.HintField;
import nsp.im.client.desktop.base.HintPassField;
import nsp.im.client.desktop.base.RoundButton;
import nsp.im.client.desktop.base.RoundDialog;
import nsp.im.client.desktop.base.RoundPanel;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.desktop.utils.Globals;
import nsp.im.client.model.ex.IMException;

@SuppressWarnings("serial")
public class SignupPanel extends RoundPanel {
	private JTextField usernameField;// *****【1-usernameField】;
	private JTextField nicknameField;// *****【2-nicknameField】;
	private HintPassField passwordField;// *****【3.1-passwordField】;
	private JButton g_eye;// 控制密码可视与否的按钮；// *****【3.2-g_eye】;
	private HintPassField confirmField;// *****【4-confirmField】;
	private JCheckBox g_boxTel;// *****【5.1-g_boxTel】;
	private JTextField g_telField;// *****【5.2-g_telField】;
	private JCheckBox g_boxEmail;// *****【6.1-g_boxEmail】;
	private JTextField g_emailField;// *****【6.2-g_emailField】;
	private JTextField g_codesField;// *****【7.2-g_codesField】;
	private JButton g_getCodesButton;// *****【7.3-g_getCodesButton】获取验证码的按钮；
	private JButton signupButton;// *****【9.1-signupButton】;
	private JButton clearButton;// *****【9.2-clearButton】;
	private JLabel g_tip;// *****【8.1-g_tip】;
	private SignupListener listener;
	private static String text[] =
			{ "必填项，英语、数字的任意组合", "选填项，默认为用户名", "请输入密码", "请再次输入密码", "请输入11位手机号",
					"请输入正确邮箱号", "请输入邮箱中的验证码", "获取验证码", "Tips：" };
	private String g_username;
	private String g_nikename;
	private String g_email;
	private String g_tel;
	public boolean signState;// 20160511;
	public boolean getCodeState = false;// 获取验证码的状态;
	private ImageIcon g_iconOpen;
	private ImageIcon g_iconClose;
	private ImageIcon g_iconClear0;
	private ImageIcon g_iconClear1;

	public SignupPanel() {
		this(null);
	}

	public SignupPanel(String path) {
		super(path);
		initComponents();
		layComponents();
		initEventListeners();
		setBackground(StyleConsts.main_back);
		signState = false;// 20160511;
	}

	/**
	 * 对各部件进行初始化
	 */
	private void initComponents() {
		usernameField =
				HintField.createNormalField(text[0], "res/before/user.png");// *****【1-usernameField】;
		usernameField.setFont(getFont().deriveFont(12));
		usernameField.setColumns(15);// (15);

		nicknameField = HintField.createNormalField(text[1], "");// *****【2-nicknameField】;
		nicknameField.setFont(getFont().deriveFont(12));
		nicknameField.setColumns(15);

		passwordField =
				HintPassField.createNormalField(text[2], "res/before/lock.png");// *****【3.1-passwordField】;
		// passwordField = new HintPassField(StyleConsts.btn_sel_back, text[2],
		// "res/before/lock.png");// *****【3.1-passwordField】;
		passwordField.setFont(getFont().deriveFont(12));
		passwordField.setColumns(15);

		g_iconOpen = new ImageIcon("res/before/open.png");
		g_iconClose = new ImageIcon("res/before/close.png");
		g_eye = new JButton(g_iconClose);// *****【3.2-g_eye】;
		g_eye.setToolTipText("<html><font color = blue>单击显示明文！</font></html>");
		g_eye.setFocusPainted(false);
		g_eye.setContentAreaFilled(false);
		g_eye.setBorderPainted(false);
		g_eye.setFocusable(false);

		confirmField = HintPassField.createNormalField(text[3], "");// *****【4-confirmField】;
		confirmField.setFont(getFont().deriveFont(12));
		confirmField.setColumns(15);

		g_boxTel = new WebCheckBox();// *****【5.1-g_boxTel】;
		// g_boxTel.setBackground(StyleConsts.main_back);
		g_boxTel.setOpaque(false);
		// g_boxTel.setForeground(StyleConsts.rempawd_gre);
		g_boxTel.setFocusable(false);
		g_boxTel.setHorizontalAlignment(WebCheckBox.TRAILING);

		g_telField = HintField.createNormalField(text[4], "res/before/tel.png");// *****【5.2-g_telField】;
		g_telField.setFont(getFont().deriveFont(12));
		g_telField.setColumns(15);

		g_boxEmail = new WebCheckBox("", true);// *****【6.1-g_boxEmail】;
		g_boxEmail.setToolTipText(
				"<html><font color = blue>默认用邮箱号注册！</font></html>");
		// g_boxEmail.setBackground(StyleConsts.main_back);
		g_boxEmail.setOpaque(false);
		// g_boxEmail.setFocusable(true);
		g_boxEmail.setFocusable(false);
		g_boxEmail.setHorizontalAlignment(WebCheckBox.TRAILING);

		g_emailField =
				HintField.createNormalField(text[5], "res/before/email.png");// *****【6.2-g_emailField】;
		g_emailField.setFont(getFont().deriveFont(12));
		g_emailField.setColumns(15);

		g_codesField =
				HintField.createNormalField(text[6], "res/before/fill.png");// *****【7.2-g_codesField】;
		g_codesField.setFont(getFont().deriveFont(12));
		g_codesField.setColumns(15);

		g_getCodesButton = new JButton(text[7]);// *****【7.3-g_getCodesButton】;
		g_getCodesButton.setToolTipText(
				"<html><font color = blue>获取邮箱验证码</font></html>");
		g_getCodesButton.getFont().deriveFont(6);
		g_getCodesButton.setFocusPainted(false);
		g_getCodesButton.setBorderPainted(false);
		g_getCodesButton.setFocusable(false);

		signupButton = RoundButton.createNormalButton();// *****【9.1-signupButton】;
		signupButton.setText("注 册");
		signupButton.setFont(new Font("新宋体", Font.BOLD, 15));
		signupButton.setFocusable(false);

		g_iconClear0 = new ImageIcon("res/before/clear0.png");
		g_iconClear1 = new ImageIcon("res/before/clear1.png");
		clearButton = new JButton(g_iconClear0);// *****【9.2-clearButton】;
		clearButton.setToolTipText(
				"<html><font color = blue>清空输入框中已输入的内容！</font></html>");
		clearButton.setFocusPainted(false);
		clearButton.setContentAreaFilled(false);
		clearButton.setBorderPainted(false);
		clearButton.setFocusable(false);

		g_tip = new JLabel(".");// *****【8.1-g_tip】;
		g_tip.setForeground(getBackground());
	}

	/**
	 * 对各部件进行布局设计
	 */
	private void layComponents() {
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 3, 0);// (10, 10, 10, 10);
		constraints.fill = GridBagConstraints.HORIZONTAL;// BOTH
		constraints.weightx = 0.1;

		Font labelFont = new Font("微软雅黑", Font.PLAIN, 12);
		String text[] =
				{ "用户名：", "昵称：", "密码：", "确认密码：", "手机号注册 ", "邮箱号注册 ", "验证码：" };
		for (int i = 0; i < 7; i++) {
			if (i != 4) {
				JLabel label = new JLabel();
				label.setHorizontalAlignment(JLabel.TRAILING);
				label.setName("label" + i);
				label.setFont(labelFont);
				label.setText(text[i]);
				constraints.gridy = i + 1;
				add(label, constraints);// *****【添加标签】;
				constraints.insets = new Insets(3, 10, 3, 3);
			} else {
				constraints.insets = new Insets(20, 10, 3, 3);
				g_boxTel.setFont(labelFont);
				g_boxTel.setText(text[i]);
				add(g_boxTel, constraints, 0, 5, 1, 1);// *****【5.1-g_boxTel】;
				i++;
				constraints.insets = new Insets(3, 10, 3, 3);
				g_boxEmail.setFont(labelFont);
				g_boxEmail.setText(text[i]);
				add(g_boxEmail, constraints, 0, 6, 1, 1);// *****【6.1-g_boxEmail】;
			}
		}
		constraints.insets = new Insets(10, 3, 3, 3);
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.weightx = 1;
		add(usernameField, constraints, 1, 1, 2, 1);// *****【1.2-usernameField】;

		constraints.insets = new Insets(3, 3, 3, 3);
		add(nicknameField, constraints, 1, 2, 2, 1);// *****【2.2-nicknameField】;
		add(passwordField, constraints, 1, 3, 2, 1);// *****【3.2-passwordField】;
		add(confirmField, constraints, 1, 4, 2, 1);// *****【4.2-confirmField】;
		add(g_emailField, constraints, 1, 6, 2, 1);// *****【6.2-g_emailField】;
		add(g_codesField, constraints, 1, 7, 2, 1);// *****【7.2-g_codesField】;

		constraints.insets = new Insets(20, 3, 3, 0);
		add(g_telField, constraints, 1, 5, 2, 1);// *****【5.2-g_telField】;

		constraints.insets = new Insets(5, 3, 10, 3);
		add(signupButton, constraints, 1, 9, 2, 1);// *****【9.1-signupButton】;

		constraints.anchor = GridBagConstraints.WEST;
		constraints.weightx = 0;
		add(clearButton, constraints, 3, 9, 1, 1);// *****【9.2-clearButton】;

		constraints.insets = new Insets(3, -10, 3, 0);
		add(g_eye, constraints, 3, 3, 3, 1);// *****【3.3-g_eye】;
		constraints.insets = new Insets(3, 3, 3, 6);
		add(g_getCodesButton, constraints, 3, 7, 2, 1);// *****【7.3-g_getCodesButton】;

		constraints.insets = new Insets(15, 3, 0, 3);
		g_tip.setHorizontalAlignment(JLabel.TRAILING);
		add(g_tip, constraints, 0, 8, 3, 1);// *****【8.1-g_tip】;

		setFocusable(true);
	}

	/**
	 * 对各部件进行监听
	 */
	private void initEventListeners() {

		g_eye.addActionListener(new ActionListener() {// *****【3.3-g_eye】;

			@Override
			public void actionPerformed(ActionEvent e) {
				String s1 = passwordField.getText();
				String s2 = confirmField.getText();
				passwordField.setText("");
				confirmField.setText("");
				String tips = null;
				if (g_eye.getIcon() == g_iconOpen) {
					g_eye.setIcon(g_iconClose);
					tips = "单击显示密文！";
					passwordField.setHint(true);// 显示密文；
					confirmField.setHint(true);// 显示密文；
				} else {
					g_eye.setIcon(g_iconOpen);
					tips = "单击显示明文！";
					passwordField.setHint(false);// 显示明文；
					confirmField.setHint(false);// 显示明文；
				}
				passwordField.setText((s1.equals(text[2])) ? "" : s1);
				confirmField.setText((s2.equals(text[3])) ? "" : s2);
				g_eye.setToolTipText(
						"<html><font color = blue>" + tips + "</font></html>");
			}
		});

		ButtonStyleMouseAdapter.attach(clearButton,
				new ButtonStyleMouseAdapter() {// *****【9.2-clearButton】;
					@Override
					public void onRestore() {
						clearButton.setIcon(g_iconClear0);
						repaint();
					}

					@Override
					public void onHover() {
						clearButton.setIcon(g_iconClear1);
						repaint();
					}

					@Override
					public void onAct() {
						clearButton.setIcon(g_iconClear1);
						repaint();
					}
				});
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clear();
				g_tip.setText(".");
				g_tip.setForeground(getBackground());
			}
		});

		g_boxTel.addActionListener(new ActionListener() {// *****【5.1-g_boxTel】;

			@Override
			public void actionPerformed(ActionEvent e) {
				g_tip.setText(".");
				g_tip.setForeground(getBackground());
			}
		});

		g_boxEmail.addActionListener(new ActionListener() {// *****【6.1-g_boxEmail】;

			@Override
			public void actionPerformed(ActionEvent e) {
				g_tip.setText(".");
				g_tip.setForeground(getBackground());
			}
		});
	}

	/**
	 * 连接服务器后的相关操作
	 */
	public void start() {

		usernameField.addKeyListener(new KeyAdapter() {// *****【1.2-usernameField】;
			public void keyPressed(KeyEvent e) {
				g_tip.setText(".");
				g_tip.setForeground(getBackground());
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					testSignup();
				}
			}
		});

		nicknameField.addKeyListener(new KeyAdapter() {// *****【2.2-nicknameField】;
			public void keyPressed(KeyEvent e) {
				g_tip.setText(".");
				g_tip.setForeground(getBackground());
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					testSignup();
				}
			}
		});

		passwordField.addKeyListener(new KeyAdapter() {// *****【3.2-passwordField】;
			public void keyPressed(KeyEvent e) {
				g_tip.setText(".");
				g_tip.setForeground(getBackground());
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					testSignup();
				}
			}
		});

		confirmField.addKeyListener(new KeyAdapter() {// *****【4.2-confirmField】;
			public void keyPressed(KeyEvent e) {
				g_tip.setText(".");
				g_tip.setForeground(getBackground());
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					testSignup();
				}
			}
		});

		g_telField.addKeyListener(new KeyAdapter() {// *****【5.2-g_telField】;
			public void keyPressed(KeyEvent e) {
				g_tip.setText(".");
				g_tip.setForeground(getBackground());
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					testSignup();
				}
			}
		});

		g_emailField.addKeyListener(new KeyAdapter() {// *****【6.2-g_emailField】;
			public void keyPressed(KeyEvent e) {
				g_tip.setText(".");
				g_tip.setForeground(getBackground());
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					testSignup();
				}
			}
		});

		g_codesField.addKeyListener(new KeyAdapter() {// *****【7.2-g_codesField】;
			public void keyPressed(KeyEvent e) {
				g_tip.setText(".");
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					testSignup();
				}
			}
		});

		g_getCodesButton.addActionListener(new ActionListener() {// *****【7.3-g_getCodesButton】;

			@Override
			public void actionPerformed(ActionEvent e) {
				getCodes();
				if (!g_tip.equals(text[8]) || !g_tip.equals("")) {
					g_tip.setForeground(Color.red);
				}
			}
		});

		signupButton.addActionListener(new ActionListener() {// *****【9.1-signupButton】;
			@Override
			public void actionPerformed(ActionEvent e) {
				testSignup();
				if (!g_tip.equals(text[8]) || !g_tip.equals("")) {
					g_tip.setForeground(Color.red);
				}
			}
		});
	}

	/**
	 * 获取验证码
	 */
	private void getCodes() {
		if(!testContent()){
			return;
		}
		if (!g_boxEmail.isSelected()) {
			g_tip.setText(text[8] + "目前仍不支持手机号注册功能。。。");
			return;
		}

		// 填写并发送验证码；
		try {
			// 向服务器请求验证码，需发送用户名、手机号或邮箱号；
			// service.signUp(username, pass1, g_telField.getText(),
			// g_emailField.getText()).get();
			g_username = usernameField.getText();
			g_email = g_emailField.getText();
			Globals.getModel().getOfflineAccountService()
				.getVerificationCode(g_username,true, "12345678910", g_email);
		} catch (Exception e) {
			if (e instanceof IMException) {
				if (((IMException) e).getError().equals("BAD_NETWORK"))
					RoundDialog.showMsg(g_telField, "验证码获取失败", "连接失败");
				else
					RoundDialog.showMsg(g_telField, "验证码获取失败", "验证码获取失败");
			}
			e.printStackTrace();
			return;
		}
		RoundDialog.showMsg(g_telField, "验证码获取成功", "验证码获取成功");

		// 收到验证码后提示；
		g_tip.setText(text[8] + "请查收并填写验证码！");
		getCodeState = true;
		if (!g_telField.getText().isEmpty()) {
			g_tel = g_telField.getText();
		}
		//增加验证码60s的倒计时
		new Thread(()->{
			g_emailField.setEnabled(false);
			g_getCodesButton.setEnabled(false);
			for(int i = 60;i > 0;i--) {
                g_getCodesButton.setText(Integer.toString(i));
                try {
                    sleep(1000);  // 1秒更新一次显示
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
            g_emailField.setEnabled(true);
			g_getCodesButton.setEnabled(true);
            g_getCodesButton.setText("获取验证码");
		}).start();
	}

	/**
	 * 注册按钮的校验和发送请求方法
	 */
	public void testSignup() {
//		if (!getCodeState) {
//			return;
//		}
		if (!testContent()) {
			return;
		}
		g_username = usernameField.getText();
		g_email = (!g_emailField.getText().isEmpty()) ? g_emailField.getText()
				: "abc@abc.com";
		g_tel = (!g_telField.getText().isEmpty()) ? g_telField.getText()
				: "12345678910";
		g_nikename = (!nicknameField.getText().isEmpty())
				? nicknameField.getText() : g_username;

		String pass1 = new String(passwordField.getPassword());
		String pass2 = new String(confirmField.getPassword());
		if (pass1.equals(text[2]) || pass1.length() == 0) {
			g_tip.setText(text[8] + "密码为空！");
			return;
		}
		if (pass2.equals(text[3]) || pass2.length() == 0) {
			g_tip.setText(text[8] + "确认密码为空！");
			return;
		}
		if (!pass1.equals(pass2)) {
			g_tip.setText(text[8] + "两次密码不一致！");
			return;
		}
		if (g_codesField.getText().length() == 0) {
			g_tip.setText(text[8] + "验证码为空！");
			return;
		}

		try {
			BufferedImage image =
					ImageIO.read(new File("res/default_avatar.png"));
			ByteArrayOutputStream stream = new ByteArrayOutputStream(4096);
			ImageIO.write(image, "png", stream);// 不太清楚具体起什么作用？
			byte[] avatar = stream.toByteArray();
			System.out.println(g_codesField.getText());
			Globals.getModel().getOfflineAccountService().signUp(g_username, pass1, g_nikename, avatar, "01234567890",
					g_email, g_codesField.getText()).get();
		} catch (IMException e) {
			String message = null;
			if (e.getError().equals("USERNAME_CONFLICT"))
				message = "用户已存在";
			else if (e.getError().equals("MALFORMED_FIELD"))
				message = "服务器不接受字段格式";
			JOptionPane.showMessageDialog(this.getParent(), message, "注册失败",
					JOptionPane.WARNING_MESSAGE);
			// RoundDialog.showMsg(passwordField, "注册失败", "注册失败");
			e.printStackTrace();
			return;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		JOptionPane.showMessageDialog(this.getParent(), "注册成功", "注册成功",
				JOptionPane.INFORMATION_MESSAGE);
		// RoundDialog.showMsg(passwordField, "注册成功", "注册成功");
		signState = true;// 20160511;
		if (listener != null) {
			listener.onSignup();
		}
	}

	/**
	 * 获取验证码前提交内容的校验
	 */
	private boolean testContent() {
		String username = usernameField.getText();
		if (username.equals(text[0]) || username.length() == 0) {
			g_tip.setText(text[8] + "用户名为空！");
			return false;
		}
		if (!g_boxEmail.isSelected() && !g_boxTel.isSelected()) {
			g_tip.setText(text[8] + "未选择注册方式！");
			return false;
		}
		if (g_boxEmail.isSelected() && g_emailField.getText().length() == 0) {
			g_tip.setText(text[8] + "邮箱号为空！");
			return false;
		}
		if (!g_boxEmail.isSelected() && g_emailField.getText().length() > 0) {
			g_tip.setText(text[8] + "未选择“邮箱号注册”选项！");
			return false;
		}
		if (g_boxTel.isSelected() && g_telField.getText().length() == 0) {
			g_tip.setText(text[8] + "手机号为空！");
			return false;
		}

		if (!testFormate(1)) {
			return false;
		}
		return true;
	}

	/**
	 * 返回用户
	 */
	public String[] getUser() {// 20160511;
		String user[] = { usernameField.getText(),
				new String(passwordField.getPassword()) };
		return user;
	}

	/**
	 * 取得注册状态
	 */
	public boolean getSignState() {// 20160511;
		return signState;
	}

	/**
	 * 设置注册状态
	 */
	public void setSignState(boolean state) {// 20160511;
		signState = state;
	}

	/**
	 * 清空输入框中已输入的内容
	 */
	private void clear() {
		usernameField.setText("");
		nicknameField.setText("");
		passwordField.setText("");
		confirmField.setText("");
		g_telField.setText("");
		g_emailField.setText("");
		g_codesField.setText("");
	}

	/**
	 * 判断输入框中内容格式的正确性
	 * <p>
	 * 1-用户名/昵称; 2-密码; 3-手机号; 4-邮箱号
	 * 
	 * @param i
	 * @return boolean
	 */
	private boolean testFormate(int i) {
		boolean legal = true;
		String content = null;
		switch (i) {
		// 判断用户名/昵称的格式；
		case 1:
			content = usernameField.getText();
			if (content.indexOf(" ") != -1) {
				legal = false;
				g_tip.setText(text[8] + "用户名中存在空格。。。");
				break;
			}
			content = nicknameField.getText();
			if (!content.isEmpty()) {
				if (content.indexOf(" ") != -1) {
					legal = false;
					g_tip.setText(text[8] + "昵称中存在空格。。。");
					break;
				}
			}
			// 判断邮箱号的格式；
		case 2:
			if (g_boxEmail.isSelected()) {
				content = g_emailField.getText();
				if (content.indexOf(" ") != -1) {
					legal = false;
					g_tip.setText(text[8] + "邮箱号中存在空格。。。");
					break;
				}
				// if
				// (!1+1
				// content.matches("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"))
				// {
				// if
				// (!content.matches("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$"))
				// {
				if (!content.matches(
						"^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$")) {
					legal = false;
					g_tip.setText(text[8] + "邮箱号格式错误！");
					break;
				}

			}
			// 判断手机号的格式；
		case 3:
			content = g_telField.getText();
			if (content.length() > 0) {
				if (content.indexOf(" ") != -1) {
					legal = false;
					g_tip.setText(text[8] + "手机号中存在空格。。。");
					break;
				}
				if (!content.matches("1[3,5,7,8]\\d{9}")) {
					legal = false;
					g_tip.setText(text[8] + "手机号格式错误！");
					break;
				}
			}
		default:
			break;
		}
		return legal;
	}

	/**
	 * 设置注册监听器
	 */
	public void setSignupListener(SignupListener l) {
		listener = l;
	}

	public interface SignupListener {
		void onSignup();
	}

	public void add(Component c, GridBagConstraints constraints, int x, int y,
			int w, int h) {// 此方法用来添加控件到容器中
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = w;
		constraints.gridheight = h;
		add(c, constraints);
	}
}