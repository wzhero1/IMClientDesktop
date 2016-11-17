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

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

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
public class GetPasswordPanel extends RoundPanel {
	private JTextField usernameField;// *****��1-usernameField��;
	private HintPassField passwordField;// *****��3.1-passwordField��;
	private JButton g_eye;// ��������������İ�ť��// *****��3.2-g_eye��;
	private HintPassField confirmField;// *****��4-confirmField��;
	private JRadioButton g_RadioTel;// *****��5.1-g_RadioTel��;
	private JTextField g_telField;// *****��5.2-g_telField��;
	private JRadioButton g_RadioEmail;// *****��6.1-g_RadioEmail��;
	private JTextField g_emailField;// *****��6.2-g_emailField��;
	private JTextField g_codesField;// *****��7.2-g_codesField��;
	private JButton g_getCodesButton;// *****��7.3-g_getCodesButton����ȡ��֤��İ�ť��
	private JButton changePasswordButton;// *****��9.1-changePasswordButton��;
	private JButton clearButton;// *****��9.2-clearButton��;
	private JLabel g_tip;// *****��8.1-g_tip��;
	private PassListener listener;
	private static String text[] = { "������", "������������", "���ٴ���������",
			"������11λ�ֻ���", "��������ȷ�����", "��������֤��",
			"��ȡ��֤��", "Tips��" };
	public boolean getPasswordState;// 20160511;
	private ImageIcon g_iconOpen;
	private ImageIcon g_iconClose;
	private ImageIcon g_iconClear0;
	private ImageIcon g_iconClear1;
	private ImageIcon g_iconOn;
	private ImageIcon g_iconOff;

	public GetPasswordPanel() {
		this(null);
	}
	
	public GetPasswordPanel(String path) {
		super(path);
		initComponents();
		layComponents();
		initEventListeners();
		setBackground(StyleConsts.main_back);
		getPasswordState = false;// 20160511;
	}

	/**
	 * �Ը��������г�ʼ��
	 */
	private void initComponents() {
		usernameField = HintField.createNormalField(text[0], "res/before/user.png");// *****��1-usernameField��;
		usernameField.setFont(getFont().deriveFont(12));
		usernameField.setColumns(15);// (15);

		passwordField = HintPassField.createNormalField(text[1], "res/before/lock.png");// *****��3.1-passwordField��;
//		passwordField = new HintPassField(StyleConsts.btn_sel_back, text[1], "res/before/lock.png");// *****��3.1-passwordField��;
		passwordField.setFont(getFont().deriveFont(12));
		passwordField.setColumns(15);

		g_iconOpen = new ImageIcon("res/before/open.png");
		g_iconClose = new ImageIcon("res/before/close.png");
		g_eye = new JButton(g_iconClose);// *****��3.2-g_eye��;
		g_eye.setToolTipText("<html><font color = blue>������ʾ���ģ�</font></html>");
		g_eye.setFocusPainted(false);
		g_eye.setContentAreaFilled(false);
		g_eye.setBorderPainted(false);
		g_eye.setFocusable(false);

		confirmField = HintPassField.createNormalField(text[2], "");// *****��4-confirmField��;
		confirmField.setFont(getFont().deriveFont(12));
		confirmField.setColumns(15);

		ButtonGroup group = new ButtonGroup();
		group.add(g_RadioTel);
		group.add(g_RadioEmail);
		g_iconOn = new ImageIcon("res/before/on.png");
		g_iconOff = new ImageIcon("res/before/off.png");
		
		g_RadioTel = new JRadioButton(g_iconOff);// *****��5.1-g_RadioTel��;
		g_RadioTel.setOpaque(false);
		g_RadioTel.setFocusable(false);

		g_telField = HintField.createNormalField(text[3], "res/before/tel.png");// *****��5.2-g_telField��;
		g_telField.setFont(getFont().deriveFont(12));
		g_telField.setColumns(15);

		g_RadioEmail = new JRadioButton("", g_iconOn, true);// *****��6.1-g_RadioEmail��;
		g_RadioEmail.setToolTipText("<html><font color = blue>Ĭ����������һ����룡</font></html>");
		g_RadioEmail.setOpaque(false);
		g_RadioEmail.setFocusable(false);

		g_emailField = HintField.createNormalField(text[4], "res/before/email.png");// *****��6.2-g_emailField��;
		g_emailField.setFont(getFont().deriveFont(12));
		g_emailField.setColumns(15);

		g_codesField = HintField.createNormalField(text[5], "res/before/fill.png");// *****��7.2-g_codesField��;
		g_codesField.setFont(getFont().deriveFont(12));
		g_codesField.setColumns(15);

		g_getCodesButton = new JButton(text[6]);// *****��7.3-g_getCodesButton��;
		g_getCodesButton.setToolTipText("<html><font color = blue>��ȡ������֤��</font></html>");
		g_getCodesButton.getFont().deriveFont(6);
		g_getCodesButton.setFocusPainted(false);
		g_getCodesButton.setBorderPainted(false);
		g_getCodesButton.setFocusable(false);

		changePasswordButton = RoundButton.createNormalButton();// *****��9.1-changePasswordButton��;
		changePasswordButton.setText("�޸�����");
		changePasswordButton.setFont(new Font("������", Font.BOLD, 15));
		changePasswordButton.setFocusable(false);

		g_iconClear0 = new ImageIcon("res/before/clear0.png");
		g_iconClear1 = new ImageIcon("res/before/clear1.png");
		clearButton = new JButton(g_iconClear0);// *****��9.2-clearButton��;
		clearButton.setToolTipText("<html><font color = blue>��������������������ݣ�</font></html>");
		clearButton.setFocusPainted(false);
		clearButton.setContentAreaFilled(false);
		clearButton.setBorderPainted(false);
		clearButton.setFocusable(false);

		g_tip = new JLabel(".");// *****��8.1-g_tip��;
		g_tip.setForeground(getBackground());
	}

	/**
	 * �Ը��������в������
	 */
	private void layComponents() {
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 3, 0);// (10, 10, 10, 10);
		constraints.fill = GridBagConstraints.HORIZONTAL;// BOTH
		constraints.weightx = 0.1;

		Font labelFont = new Font("΢���ź�", Font.PLAIN, 12);
		String text[] = { "�û�����", "���룺", "ȷ�����룺", "�ֻ����һ�", "������һ�", "��֤�룺" };
		for (int i = 0; i < 6; i++) {
			if (i != 3) {
				JLabel label = new JLabel();
				label.setHorizontalAlignment(JLabel.TRAILING);
				label.setName("label" + i);
				label.setFont(labelFont);
				label.setText(text[i]);
				constraints.gridy = i + 1;
				add(label, constraints);// *****����ӱ�ǩ��;
				constraints.insets = new Insets(3, 10, 3, 3);
			} else {
				constraints.insets = new Insets(20, 10, 3, 3);
				g_RadioTel.setFont(labelFont);
				g_RadioTel.setText(text[i]);
				add(g_RadioTel, constraints, 0, 4, 1, 1);// *****��4.1-g_boxTel��;
				i++;
				constraints.insets = new Insets(3, 10, 3, 3);
				g_RadioEmail.setFont(labelFont);
				g_RadioEmail.setText(text[i]);
				add(g_RadioEmail, constraints, 0, 5, 1, 1);// *****��5.1-g_boxEmail��;
			}
		}
		constraints.insets = new Insets(10, 3, 3, 3);
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.weightx = 1;
		add(usernameField, constraints, 1, 1, 2, 1);// *****��1.2-usernameField��;

		constraints.insets = new Insets(3, 3, 3, 3);
		add(passwordField, constraints, 1, 2, 2, 1);// *****��2.2-passwordField��;
		add(confirmField, constraints, 1, 3, 2, 1);// *****��3.2-confirmField��;
		add(g_emailField, constraints, 1, 5, 2, 1);// *****��5.2-g_emailField��;
		add(g_codesField, constraints, 1, 6, 2, 1);// *****��6.2-g_codesField��;

		constraints.insets = new Insets(20, 3, 3, 0);
		add(g_telField, constraints, 1, 4, 2, 1);// *****��4.2-g_telField��;

		constraints.insets = new Insets(5, 3, 10, 3);
		add(changePasswordButton, constraints, 1, 8, 2, 1);// *****��8.1-changePasswordButton��;

		constraints.anchor = GridBagConstraints.WEST;
		constraints.weightx = 0;
		add(clearButton, constraints, 3, 8, 1, 1);// *****��8.2-clearButton��;

		constraints.insets = new Insets(3, -10, 3, 0);
		add(g_eye, constraints, 3, 2, 3, 1);// *****��2.3-g_eye��;
		constraints.insets = new Insets(3, 3, 3, 6);
		add(g_getCodesButton, constraints, 3, 6, 2, 1);// *****��6.3-g_getCodesButton��;

		constraints.insets = new Insets(15, 3, 0, 3);
		g_tip.setHorizontalAlignment(JLabel.TRAILING);
		add(g_tip, constraints, 0, 7, 3, 1);// *****��7.1-g_tip��;

		setFocusable(true);
	}

	/**
	 * �Ը��������м���
	 */
	private void initEventListeners() {
		g_eye.addActionListener(new ActionListener() {// *****��2.3-g_eye��;

			@Override
			public void actionPerformed(ActionEvent e) {
				String s1 = passwordField.getText();
				String s2 = confirmField.getText();
				passwordField.setText("");
				confirmField.setText("");
				String tips = null;
				if (g_eye.getIcon() == g_iconOpen) {
					g_eye.setIcon(g_iconClose);
					tips = "������ʾ���ģ�";
					passwordField.setHint(true);//��ʾ���ģ�
					confirmField.setHint(true);//��ʾ���ģ�
				} else {
					g_eye.setIcon(g_iconOpen);
					tips = "������ʾ���ģ�";
					passwordField.setHint(false);//��ʾ���ģ�
					confirmField.setHint(false);//��ʾ���ģ�
				}
				passwordField.setText((s1.equals(text[1])) ? "" : s1);
				confirmField.setText((s2.equals(text[2])) ? "" : s2);
				g_eye.setToolTipText("<html><font color = blue>" + tips + "</font></html>");
			}
		});
		
		g_RadioTel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				g_RadioTel.setSelected(true);
				g_RadioTel.setIcon(g_iconOn);
				g_RadioEmail.setSelected(false);
				g_RadioEmail.setIcon(g_iconOff);
			}
		});
		
		g_RadioEmail.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				g_RadioEmail.setSelected(true);
				g_RadioEmail.setIcon(g_iconOn);
				g_RadioTel.setSelected(false);
				g_RadioTel.setIcon(g_iconOff);
			}
		});

		ButtonStyleMouseAdapter.attach(clearButton, new ButtonStyleMouseAdapter() {// *****��9.2-clearButton��;
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

		g_RadioTel.addActionListener(new ActionListener() {// *****��5.1-g_boxTel��;

			@Override
			public void actionPerformed(ActionEvent e) {
				g_tip.setText(".");
				g_tip.setForeground(getBackground());
			}
		});

		g_RadioEmail.addActionListener(new ActionListener() {// *****��6.1-g_boxEmail��;

			@Override
			public void actionPerformed(ActionEvent e) {
				g_tip.setText(".");
				g_tip.setForeground(getBackground());
			}
		});
	}

	/**
	 * ���ӷ����������ز���
	 */
	public void start() {

		usernameField.addKeyListener(new KeyAdapter() {// *****��1.2-usernameField��;
			public void keyPressed(KeyEvent e) {
				g_tip.setText(".");
				g_tip.setForeground(getBackground());
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					testSignup();
				}
			}
		});

		passwordField.addKeyListener(new KeyAdapter() {// *****��3.2-passwordField��;
			public void keyPressed(KeyEvent e) {
				g_tip.setText(".");
				g_tip.setForeground(getBackground());
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					testSignup();
				}
			}
		});

		confirmField.addKeyListener(new KeyAdapter() {// *****��4.2-confirmField��;
			public void keyPressed(KeyEvent e) {
				g_tip.setText(".");
				g_tip.setForeground(getBackground());
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					testSignup();
				}
			}
		});

		g_telField.addKeyListener(new KeyAdapter() {// *****��5.2-g_telField��;
			public void keyPressed(KeyEvent e) {
				g_tip.setText(".");
				g_tip.setForeground(getBackground());
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					testSignup();
				}
			}
		});

		g_emailField.addKeyListener(new KeyAdapter() {// *****��6.2-g_emailField��;
			public void keyPressed(KeyEvent e) {
				g_tip.setText(".");
				g_tip.setForeground(getBackground());
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					testSignup();
				}
			}
		});

		g_codesField.addKeyListener(new KeyAdapter() {// *****��7.2-g_codesField��;
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					testSignup();
				}
			}
		});

		g_getCodesButton.addActionListener(new ActionListener() {// *****��7.3-g_getCodesButton��;

			@Override
			public void actionPerformed(ActionEvent e) {
				getCodes();
				if (!g_tip.equals(text[7]) || !g_tip.equals("")) {
					g_tip.setForeground(Color.red);
				}
			}
		});

		changePasswordButton.addActionListener(new ActionListener() {// *****��9.1-changePasswordButton��;
			@Override
			public void actionPerformed(ActionEvent e) {
				testSignup();
				if (!g_tip.equals(text[7]) || !g_tip.equals("")) {
					g_tip.setForeground(Color.red);
				}
			}
		});
	}

	/**
	 * ��֤���У�鼰��ȡ����
	 */
	private void getCodes() {
        if(!testCodes()){
			return;
		}
		// ��д��������֤�룻
		try {
			// �������������֤�룬�跢���û������ֻ��Ż�����ţ�
			// service.signUp(username, pass1, g_telField.getText(),
			// g_emailField.getText()).get();
			Globals.getModel().getOfflineAccountService()
				.getVerificationCode(usernameField.getText(),true, "12345678910", g_emailField.getText()).get(IMException.class);
		} catch (IMException e) {
				if (e.getError().name().equals("BAD_NETWORK"))
					RoundDialog.showMsg(g_telField, "��֤���ȡʧ��", "����ʧ��");
				else
					RoundDialog.showMsg(g_telField, "��֤���ȡʧ��", "��֤���ȡʧ��");
		}
		RoundDialog.showMsg(g_telField, "��֤���ȡ�ɹ�", "��֤���ȡ�ɹ�");

		// �յ���֤�����ʾ��
		g_tip.setText(text[7] + "����ղ���д��֤�룡");

		//������֤��60s�ĵ���ʱ
		new Thread(()->{
			g_emailField.setEnabled(false);
			g_getCodesButton.setEnabled(false);
			for(int i = 60;i > 0;i--) {
                g_getCodesButton.setText(Integer.toString(i));
                try {
                    sleep(1000);  // 1�����һ����ʾ
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
            g_emailField.setEnabled(true);
			g_getCodesButton.setEnabled(true);
            g_getCodesButton.setText("��ȡ��֤��");
		}).start();


//		//��д��������֤�룻
//		if (g_codesField.getText().length() == 0) {
//			g_tip.setText(text[7] + "��֤��Ϊ�գ�");
//			return;
//		}
//
//		//У����֤�벢����ע�᣻
//
//		try {
////			service.signUp(username, pass1, g_telField.getText(), g_emailField.getText()).get();
//		} catch (Exception e) {
//			RoundDialog.showMsg(usernameField, "ע��ʧ��", "ע��ʧ��");
//			e.printStackTrace();
//			return;
//		}
//
//		// RoundDialog.showMsg(clearButton,"ע��ɹ�", "ע��ɹ�");
//		RoundDialog.showMsg(usernameField, "�޸�����ɹ�", "��������");
//		getPasswordState = true;// 20160511;
//		if (listener != null) {
//			listener.onPass();
//		}
	}

	/**
	 * ��֤���У�鼰��ȡ����
	 */
	private boolean testCodes() {
		//������֤�룻
		String username = usernameField.getText();
		String pass1 = new String(passwordField.getPassword());
		String pass2 = new String(confirmField.getPassword());
		if (username.equals(text[0]) || username.length() == 0) {
			g_tip.setText(text[7] + "�û���Ϊ�գ�");
			return false;
		}
		if (pass1.equals(text[1]) || pass1.length() == 0) {
			g_tip.setText(text[7] + "����Ϊ�գ�");
			return false;
		}
		if (pass2.equals(text[2]) || pass2.length() == 0) {
			g_tip.setText(text[7] + "ȷ������Ϊ�գ�");
			return false;
		}
		if (!pass1.equals(pass2)) {
			g_tip.setText(text[7] + "�������벻һ�£�");
			return false;
		}
		if (!g_RadioEmail.isSelected() && !g_RadioTel.isSelected()) {
			g_tip.setText(text[7] + "δѡ��ע�᷽ʽ��");
			return false;
		}
		if (g_RadioEmail.isSelected() && g_emailField.getText().length() == 0) {
			g_tip.setText(text[7] + "�����Ϊ�գ�");
			return false;
		}
		if (!g_RadioEmail.isSelected() && g_emailField.getText().length() > 0) {
			g_tip.setText(text[7] + "δѡ�������ע�ᡱѡ�");
			return false;
		}
		if (g_RadioTel.isSelected() && g_telField.getText().length() == 0) {
			g_tip.setText(text[7] + "�ֻ���Ϊ�գ�");
			return false;
		}

		if (!testFormate(1)) {
			return false;
		}

		if (!g_RadioEmail.isSelected()) {
			g_tip.setText(text[7] + "Ŀǰ�Բ�֧���ֻ����޸Ĺ��ܡ�����");
			return false;
		}
		return true;
	}
	
	/**
	 * �޸İ�ť��У��ͷ�������
	 */
	public void testSignup() {
		if(!testCodes()){
			return;
		}
		String username = usernameField.getText();
		String pass1 = new String(passwordField.getPassword());
        try {
            System.out.println(g_codesField.getText());
//            Globals.getModel().getOfflineAccountService().resetPassword(username, pass1, g_telField.getText(),
//                    g_emailField.getText(), g_codesField.getText()).get(IMException.class);
            Globals.getModel().getOfflineAccountService().resetPassword(username, pass1, "12345678901",
                    g_emailField.getText(), g_codesField.getText()).get();
			} catch (IMException e) {
				String message = null;
				if (e.getError().equals("MALFORMED_FIELD"))
					message = "�������������ֶθ�ʽ";
				JOptionPane.showMessageDialog(this.getParent(), message, "�޸�ʧ��",
						JOptionPane.WARNING_MESSAGE);
				// RoundDialog.showMsg(passwordField, "ע��ʧ��", "ע��ʧ��");
				e.printStackTrace();
				return;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			JOptionPane.showMessageDialog(this.getParent(), "�޸ĳɹ�", "�޸ĳɹ�",
					JOptionPane.INFORMATION_MESSAGE);
			getPasswordState = true;// 20160511;
			if (listener != null) {
				listener.onPass();
			}
	}

	/**
	 * ���������������������
	 */
	private void clear() {
		usernameField.setText("");
//		nicknameField.setText("");
		passwordField.setText("");
		confirmField.setText("");
		g_telField.setText("");
		g_emailField.setText("");
		g_codesField.setText("");
	}

	/**
	 * �ж�����������ݸ�ʽ����ȷ��
	 * <p>
	 * 1-�û���/�ǳ�; 2-����; 3-�ֻ���; 4-�����
	 * 
	 * @param i
	 * @return boolean
	 */
	private boolean testFormate(int i) {
		boolean legal = true;
		String content = null;
		switch (i) {
		//�ж��û���/�ǳƵĸ�ʽ��
		case 1:
			content = usernameField.getText();
			if (content.indexOf(" ") != -1) {
				legal = false;
				g_tip.setText(text[7] + "�û����д��ڿո񡣡���");
				break;
			}
		/*	content = nicknameField.getText();
			if (content.indexOf(" ") != -1) {
				legal = false;
				g_tip.setText(text[8] + "�ǳ��д��ڿո񡣡���");
				break;
			}*/
		//�ж�����/ȷ������ĸ�ʽ��
		case 2:
			content = passwordField.getText();
			if (content.indexOf(" ") != -1) {
				legal = false;
				g_tip.setText(text[7] + "���������д��ڿո񡣡���");
				break;
			}
			content = confirmField.getText();
			if (content.indexOf(" ") != -1) {
				legal = false;
				g_tip.setText(text[7] + "����ȷ�������д��ڿո񡣡���");
				break;
			}
		//�ж�����ŵĸ�ʽ��	
		case 3:
			if (g_RadioEmail.isSelected()) {
				content = g_emailField.getText();
				if (content.indexOf(" ") != -1) {
					legal = false;
					g_tip.setText(text[7] + "������д��ڿո񡣡���");
					break;
				}
//				if (!content.matches("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$")) {
//				if (!content.matches("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$")) {
				if (!content.matches("^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$")) {
					legal = false;
					g_tip.setText(text[7] + "����Ÿ�ʽ����");
					break;
				}
				
			}
		//�ж��ֻ��ŵĸ�ʽ��
		case 4:
			content = g_telField.getText();
			if (content.length() > 0) {
				if (content.indexOf(" ") != -1) {
					legal = false;
					g_tip.setText(text[7] + "�ֻ����д��ڿո񡣡���");
					break;
				}
				if (!content.matches("1[3,5,7,8]\\d{9}")) {
					legal = false;
					g_tip.setText(text[7] + "�ֻ��Ÿ�ʽ����");
					break;
				}
			}			
		default:
			break;
		}
		return legal;
	}

	/**
	 * ���������һؼ�����
	 */
	public void setPassListener(PassListener l) {
		listener = l;
	}

	public interface PassListener {
		void onPass();
	}

	public void add(Component c, GridBagConstraints constraints, int x, int y, int w, int h) {// �˷���������ӿؼ���������
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = w;
		constraints.gridheight = h;
		add(c, constraints);
	}
}