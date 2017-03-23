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
 * ��¼�������
 */
@SuppressWarnings("serial")
public class StartFrame extends JFrame {
	private JLabel signupLabel;
	private JLabel forgetPasswordLabel;
	private LoginPanel loginPanel;
	private JPanel signupPanel1;// 20160525;��¼ҳ���·�ҳ�棻
	private SignupPanel signupPanel;// 20160511;ע��ҳ����ҳ�棻
	private GetPasswordPanel getPasswordPanel;// ��������ҳ����ҳ�棻
	private boolean g_dialog1State = false;// 20160512;
	private JDialog dialog1;// 20160512;
	private JDialog dialog2;// 20160731;
	ImageCodeComponent g_imageCodePane;//20160521;
	private TitleBar titleBar;

	public StartFrame() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());//Ĭ��Ϊmetal���
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
//		setAlwaysOnTop(true);// ********���ô���������Ļ��ǰ��,20160512
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().createImage("res/NSP35.png"));// ****���ô��ں�̨��ʾͼ�ꣻ
		//setIconImage(Toolkit.getDefaultToolkit().createImage("res/blank.png"));// ****���ô��ں�̨��ʾͼ�ꣻ
		setUndecorated(true);// ******���ñ�������
		setBackground(new Color(55, 255, 55, 0));// ******��ɫ͸��(0, 0, 0,
													// 0);��¼��ť��new Color(55,
													// 255, 55, 0)��
		FramePanel framePanel = new FramePanel();
		setContentPane(framePanel);
		framePanel.setBackground(StyleConsts.main_back);
		
		layComponents();
		setListeners();
	}

	private void layComponents() {
		titleBar = new TitleBar(this, false);// *****ʹ���Զ����������Panel��
		JLabel title = new JLabel("NSP IM");
		title.setIcon(new ImageIcon(ImageUtil.getImage("res/NSP35.png", 15)));
		title.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 14));
		title.setBorder(BorderFactory.createEmptyBorder(10, 12, 5, 5));
		// 20160511(5, 5, 5, 5);
		titleBar.setTitle(title, false);// *****��ơ�1-���⡿��

		loginPanel = new LoginPanel();// *****��ơ�2-ע��ҳ����

		signupPanel1 = new RoundPanel();// *****��ơ�3-ע��ҳ����
		signupPanel1.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5,0,10,0);
		signupPanel1.setBackground(StyleConsts.main_back);
		forgetPasswordLabel = new JLabel("��������");// *****��3.1-�������롿��
		forgetPasswordLabel.setForeground(StyleConsts.link_back);
		forgetPasswordLabel.setFont(new Font("������", Font.BOLD, 14));
		forgetPasswordLabel.setHorizontalAlignment(JLabel.CENTER);
		signupPanel1.add(forgetPasswordLabel, constraints);
		signupLabel = new JLabel("ע���˻�");// *****��3.2-ע���˻�����
		signupLabel.setForeground(StyleConsts.link_back);
		signupLabel.setFont(new Font("������", Font.BOLD, 14));
		signupLabel.setHorizontalAlignment(JLabel.CENTER);
		constraints.gridx = 1;
		constraints.insets = new Insets(5,80,10,0);
		signupPanel1.add(signupLabel, constraints);

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		add(titleBar, c);// *****��ӡ�titleBar��ҳ��

		c.weighty = 1;
		c.gridy = 1;
		add(loginPanel, c);// *****��ӡ�loginPanel��ҳ��

		c.weightx = 0;
		c.weighty = 0;
		c.gridy = 2;
		add(signupPanel1, c);// *****��ӡ�signupPanel1��ҳ��
	}

	public void start() {
		loginPanel.start();
		setSize(262, 427);// (270, 450);(267, 432);
		setResizable(false);// ���ô��ڲ��ɵ�����С��
	}

	private void setListeners() {
		/**
		 * @���ߣ�lll
		 * @ʱ�䣺2016��3��27������11:02:02 @��Ҫ���ܣ���ע���ǩ��ɫ���仯���ã� @�޸����ݣ�
		 */
		ButtonStyleMouseAdapter.attach(signupLabel, new ButtonStyleMouseAdapter() {
			@Override
			public void onRestore() {// *****�ָ���
				signupLabel.setForeground(StyleConsts.link_back);
			}

			@Override
			public void onHover() {// *****��ͣ��
				signupLabel.setForeground(StyleConsts.link_back1);
			}

			@Override
			public void onAct() {// *****���ã�
				signupLabel.setForeground(StyleConsts.link_back2);
			}
		});

		// ��ע���ǩ������¼����ã�
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
		 * @���ߣ�lll
		 * @ʱ�䣺2016��7��31������10:31:02 @��Ҫ���ܣ������������ǩ��ɫ���仯���ã� @�޸����ݣ�
		 */
		ButtonStyleMouseAdapter.attach(forgetPasswordLabel, new ButtonStyleMouseAdapter() {
			@Override
			public void onRestore() {// *****�ָ���
				forgetPasswordLabel.setForeground(StyleConsts.link_back);
			}
			
			@Override
			public void onHover() {// *****��ͣ��
				forgetPasswordLabel.setForeground(StyleConsts.link_back1);
			}
			
			@Override
			public void onAct() {// *****���ã�
				forgetPasswordLabel.setForeground(StyleConsts.link_back2);
			}
		});
		
		// �����������ǩ������¼����ã�
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
		dialog1 = new JDialog(this, "ע��", true);// 20160512;
		dialog1.setResizable(false); //�����û����ɵ����öԻ����С��
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
		dialog2 = new JDialog(this, "�����һ�", true);
		dialog2.setResizable(false); //�����û����ɵ����öԻ����С��
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
