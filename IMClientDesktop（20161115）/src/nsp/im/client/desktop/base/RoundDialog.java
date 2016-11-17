package nsp.im.client.desktop.base;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Բ�Ǿ��ζԻ���
 */
@SuppressWarnings("serial")
public class RoundDialog extends JDialog {
	private static JLabel titletxt;// 20160512;
	private static RoundPanel content;// 20160512;
	private TitleBar titleBar;

	/**
	 * �½�Բ�Ǿ��ζԻ���
	 */
	public RoundDialog() {
		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 0));
		setContentPane(new FramePanel());
		setLayout(new BorderLayout());
		titleBar = new TitleBar(this);
		add(titleBar, BorderLayout.NORTH);
	}

	public RoundDialog(Component owner) {// 20160512;
		this();
		setLocationRelativeTo(owner);
	}

	public RoundDialog(int x, int y) {// 20160512;�������á�����
		this();
		setLocation(x, y);
	}

	/**
	 * �½�Բ�Ǿ��ζԻ��򣬲�ֱ�����ñ��������
	 * 
	 * @param title
	 *            ����
	 * @param content
	 *            ����
	 */
	public RoundDialog(JComponent title, JComponent content) {
		this();
		setTitle(title);
		setContent(content);
	}

	public RoundDialog(Component owner, JComponent title, JComponent content) {// 20160512;
		this(owner);
		setTitle(title);
		setContent(content);
	}

	public RoundDialog(int x, int y, JComponent title, JComponent content) {// 20160512;
		this(x, y);
		setTitle(title);
		setContent(content);
	}

	/**
	 * ���öԻ������
	 * 
	 * @param title
	 *            Ҫ���õı���
	 */
	public void setTitle(JComponent title) {
		titleBar.setTitle(title, true);
	}

	/**
	 * ���öԻ�������
	 * 
	 * @param content
	 *            Ҫ���õ�����
	 */
	public void setContent(JComponent content) {
		add(content, BorderLayout.CENTER);
	}

	/**
	 * �����Ƿ�ѡ��ĶԻ���
	 */
	private static class SimpleDialog extends RoundDialog {
		public boolean opt = false;

		public SimpleDialog(JComponent title, JComponent content) {
			super(title, content);
		}

		public SimpleDialog(Component owner, JComponent title, JComponent content) {// 20160512;
			super(owner, title, content);
		}

		public SimpleDialog(int x, int y, JComponent title, JComponent content) {// 20160512;
			super(x, y, title, content);
		}
	}

	/**
	 * ��������Ի��򣬲������û���ѡ��
	 * 
	 * @param title
	 *            �����ַ���
	 * @param msg
	 *            Ҫ��ʾ����Ϣ
	 * @return �û�ѡ���ǻ��
	 */
	public static boolean showOption(String title, String msg, boolean warning) {
		RoundButton ok = warning ? RoundButton.createDangerousButton() : RoundButton.createNormalButton();
		ok.setText("  ��  ");
		RoundButton cancel = RoundButton.createNormalButton();
		cancel.setText("  ��  ");
		final SimpleDialog dialog = createSimpleDlg(title, msg, new RoundButton[] { ok, cancel });
		// �����ťʱ������dialog�е�ѡ��ֵ�����շ��ظ�������
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialog.opt = true;
				dialog.dispose();
			}
		});
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		dialog.setModal(true);
		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
		return dialog.opt;
	}

	public static boolean showOption(Component owner, String title, String msg, boolean warning) {
		RoundButton ok = warning ? RoundButton.createDangerousButton() : RoundButton.createNormalButton();
		ok.setText("  ��  ");
		RoundButton cancel = RoundButton.createNormalButton();
		cancel.setText("  ��  ");
		final SimpleDialog dialog = createSimpleDlg(owner, title, msg, new RoundButton[] { ok, cancel });
		// �����ťʱ������dialog�е�ѡ��ֵ�����շ��ظ�������
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialog.opt = true;
				dialog.dispose();
			}
		});
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		dialog.setModal(true);
		dialog.pack();
		dialog.setVisible(true);
		return dialog.opt;
	}

	public static boolean showOption(int x, int y, String title, String msg, boolean warning) {
		RoundButton ok = warning ? RoundButton.createDangerousButton() : RoundButton.createNormalButton();
		ok.setText("  ��  ");
		RoundButton cancel = RoundButton.createNormalButton();
		cancel.setText("  ��  ");
		final SimpleDialog dialog = createSimpleDlg(x, y, title, msg, new RoundButton[] { ok, cancel });
		// �����ťʱ������dialog�е�ѡ��ֵ�����շ��ظ�������
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialog.opt = true;
				dialog.dispose();
			}
		});
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		dialog.setModal(true);
		dialog.pack();
		dialog.setVisible(true);
		return dialog.opt;
	}

	/**
	 * ������ʾ�Ի��򣬲������û���ѡ��
	 * 
	 * @param title
	 *            �����ַ���
	 * @param msg
	 *            Ҫ��ʾ����Ϣ
	 * @return �û�ѡ���ǻ��
	 */
	public static void showMsg(String title, String msg) {
		RoundButton ok = RoundButton.createNormalButton();
		ok.setText("  ȷ��  ");
		final SimpleDialog dialog = createSimpleDlg(title, msg, new RoundButton[] { ok });
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialog.dispose();
			}
		});
		dialog.setModal(true);
		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}

	public static void showMsg(Component owner, String title, String msg) {// 20160512;
		RoundButton ok = RoundButton.createNormalButton();
		ok.setText("  ȷ��  ");
		final SimpleDialog dialog = createSimpleDlg(owner, title, msg, new RoundButton[] { ok });
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialog.dispose();
			}
		});
		dialog.setModal(true);
		dialog.pack();
		dialog.setVisible(true);
	}

	public static void showMsg(int x, int y, String title, String msg) {// 20160512;
		RoundButton ok = RoundButton.createNormalButton();
		ok.setText("  ȷ��  ");
		final SimpleDialog dialog = createSimpleDlg(x, y, title, msg, new RoundButton[] { ok });
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialog.dispose();
			}
		});
		dialog.setModal(true);
		dialog.pack();
		dialog.setVisible(true);
	}

	// ͨ�����⡢��Ϣ����ť����һ���򵥶Ի���
	private static SimpleDialog createSimpleDlg(String title, String msg, RoundButton[] buttons) {// 20160511;
		setSimpleDlg(title, msg, buttons);
		SimpleDialog dialog = new SimpleDialog(titletxt, content);
		return dialog;
	}

	private static SimpleDialog createSimpleDlg(Component owner, String title, String msg, RoundButton[] buttons) {// 20160511;
		setSimpleDlg(title, msg, buttons);
		SimpleDialog dialog = new SimpleDialog(owner, titletxt, content);
		return dialog;
	}

	private static SimpleDialog createSimpleDlg(int x, int y, String title, String msg, RoundButton[] buttons) {// 20160511;
		setSimpleDlg(title, msg, buttons);
		SimpleDialog dialog = new SimpleDialog(x, y, titletxt, content);
		return dialog;
	}

	private static void setSimpleDlg(String title, String msg, RoundButton[] buttons) {
		// ���ñ���
		titletxt = new JLabel(title);
		titletxt.setFont(titletxt.getFont().deriveFont(16f));
		// ����չʾ����Ϣ
		content = new RoundPanel();
		content.setLayout(new BorderLayout());
		JLabel msgtxt = new JLabel(msg, JLabel.CENTER);
		msgtxt.setFont(msgtxt.getFont().deriveFont(15f));
		msgtxt.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		content.add(msgtxt, BorderLayout.CENTER);
		content.setBackground(StyleConsts.main_back);
		// ���ð�ť
		JPanel btns = new RoundPanel();
		btns.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
		btns.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		for (RoundButton button : buttons) {
			btns.add(button);
		}
		btns.setBackground(StyleConsts.main_back);
		content.add(btns, BorderLayout.SOUTH);
	}
}
