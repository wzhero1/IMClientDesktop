package nsp.im.client.desktop.simplegui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

import nsp.im.client.desktop.base.HintField;
import nsp.im.client.desktop.base.RoundButton;
import nsp.im.client.desktop.base.RoundDialog;
import nsp.im.client.desktop.base.RoundLabel;
import nsp.im.client.desktop.base.RoundPanel;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.desktop.utils.Globals;
import nsp.im.client.desktop.utils.ImageUtil;
import nsp.im.client.model.User;

@SuppressWarnings("serial")
public class MyAccountPanel extends RoundPanel {
	private BufferedImage chosed = null;
	private File chosedFile;
	private RoundLabel avatarImage;// JLabel,20160511;
	private HintField nickField;
	private JLabel nameLabel;
	private JLabel nameField;
	private JButton saveBtn;
	private boolean g_imageChanged;// 20160519;
	private boolean g_nicknameChanged;// 20160519;
	private int g_changeNum = 0;// 20160519;
	private String g_nick0;// 20160519;

	public MyAccountPanel() {
		setBackground(StyleConsts.main_back);
		g_imageChanged = false;// 20160511;
		g_nicknameChanged = false;// 20160511;
		layComponents();
	}

	private void layComponents() {

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.CENTER;

		avatarImage = new RoundLabel();// JLabel,2060511;
		avatarImage.setHorizontalAlignment(JButton.CENTER);// JLabel,2060511;
		c.gridwidth = 2;// ɾ2�У�20160517;
		c.insets = new Insets(20, 5, 20, 5);
		c.fill = GridBagConstraints.NONE;
		add(avatarImage, c);

		nickField = HintField.createNormalField("δ�����ǳ�");
		nickField.setFont(nickField.getFont().deriveFont(18f));
		nickField.setColumns(15);
		c.insets = new Insets(10, 5, 10, 5);
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		add(nickField, c);

		nameLabel = new JLabel("�û�����", JLabel.TRAILING);
		nameLabel.setFont(nameLabel.getFont().deriveFont(15f));
		c.gridwidth = 1;
		c.gridy = 2;
		add(nameLabel, c);

		nameField = new JLabel("");
		nameField.setFont(nameField.getFont().deriveFont(15f));
		c.gridx = 1;
		c.gridy = 2;
		add(nameField, c);

		saveBtn = RoundButton.createNormalButton();
		saveBtn.setText("����");
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		add(saveBtn, c);

		setFocusable(true);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				requestFocusInWindow();
			}
		});
	}

	public void start() {
		User user = Globals.getAccount().getMyInfo();
		updateUser(user);
		user.getUpdateEvent().addListener(() -> {
			updateUser(user);
		});

		avatarImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("ѡ��ͷ��");
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);// ���ƽ���ѡ���ļ�
				chooser.setMultiSelectionEnabled(false);// ���ò��ɶ�ѡ��20160512��
				chooser.setFileHidingEnabled(true);// �����Ƿ���ʾ�����ļ���20160512��
				/*
				 * chooser.setAcceptAllFileFilterUsed(false);
				 * chooser.setFileFilter(new FileFilter() {
				 * 
				 * @Override public String getDescription() {// �ļ����� return
				 * "ͼƬ"; }
				 * 
				 * @Override public boolean accept(File file) {// 20160512; if
				 * (file.isDirectory()) { return true; } String name =
				 * file.getName(); if (name != null) { if
				 * (name.toLowerCase().endsWith("jpg") ||
				 * name.toLowerCase().endsWith("jpeg") ||
				 * name.toLowerCase().endsWith("png") ||
				 * name.toLowerCase().endsWith("bmp") ||
				 * name.toLowerCase().endsWith("gif")) { return true; } else {
				 * return false; } } return false; } });
				 */
				chooser.setFileFilter(new FileNameExtensionFilter("ͼƬ", "jpg",
						"jpeg", "png", "gif", "bmp"));// ������ע�ʹ��빦����ͬ��20160512��
				chooser.showOpenDialog(MyAccountPanel.this);// 20160512;
				// chooser.showDialog(MyAccountPanel.this, "ѡ��ͷ��");// 20160512;
				// chooser.setApproveButtonText("ȷ��");//���ú������á�����20160512;
				chosedFile = chooser.getSelectedFile();
				if (chosedFile == null)
					return;
				try {
					BufferedImage image = ImageIO.read(chosedFile);
					BufferedImage scaled = ImageUtil.scaleImage(image, 100,
							100);
					chosed = scaled;
					avatarImage.setIcon(new ImageIcon(scaled));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ev) {
				String nick = nickField.getText();
				if (!nick.equals(g_nick0)) {// 20160519;
					try {
						Globals.getAccount().getMyInfo().changeNickname(nick).get();
					} catch (Exception e) {
						RoundDialog.showMsg(MyAccountPanel.this, "�޸��ǳ�",
								"�޸�ʧ�ܡ�����");// 20160513;
						e.printStackTrace();
						return;
					}
					g_changeNum = 1;// 20160519;
				}
				if (chosed != null) {
					try {
						Globals.getAccount().getMyInfo()
								.changeAvatar(chosedFile);// 20160519;
					} catch (Exception e) {
						RoundDialog.showMsg(MyAccountPanel.this, "�޸�ͷ��",
								"�޸�ʧ�ܡ�����");// 20160513;
						e.printStackTrace();
						return;
					}
					if (g_changeNum == 0) {
						g_changeNum = 2;// 20160519;
					} else if (g_changeNum == 1) {
						g_changeNum = 3;// 20160519;
					}
				}
				switch (g_changeNum) {// 20160519;
				case 0:
					RoundDialog.showMsg(MyAccountPanel.this, "�޸���Ϣ",
							"��Ϣû���κα䶯���޸�ʧ�ܡ�����");
					break;
				case 1:
					RoundDialog.showMsg(MyAccountPanel.this, "�޸��ǳ�", "�޸ĳɹ���");
					g_nick0 = nick;// 20160519;
					g_nicknameChanged = true;// 20160519;
					g_changeNum = 0;// �޸ĺ����¸�ֵ��20160519;
					break;
				case 2:
					RoundDialog.showMsg(MyAccountPanel.this, "�޸�ͷ��", "�޸ĳɹ���");
					g_imageChanged = true;// 20160510;
					g_changeNum = 0;// �޸ĺ����¸�ֵ��20160519;
					chosed = null;// �޸ĺ����¸�ֵ��20160519;
					break;
				case 3:
					RoundDialog.showMsg(MyAccountPanel.this, "�޸���Ϣ", "�޸ĳɹ���");
					g_nick0 = nick;// 20160519;
					g_imageChanged = true;// 20160510;
					g_nicknameChanged = true;// 20160519;
					g_changeNum = 0;// �޸ĺ����¸�ֵ��20160519;
					chosed = null;// �޸ĺ����¸�ֵ��20160519;
					break;
				default:
					break;
				}
			}
		});
	}

	public boolean getImageChanged() {// 20160519;
		return g_imageChanged;
	}

	public void setImageChanged(boolean state) {// 20160519;
		g_imageChanged = state;
	}

	public boolean getNicknameChanged() {// 20160519;
		return g_nicknameChanged;
	}

	public void setNicknameChanged(boolean state) {// 20160519;
		g_nicknameChanged = state;
	}

	private void updateUser(User user) {
		BufferedImage avatar = ImageUtil.getUserImage(user, 100);
		avatarImage.setIcon(new ImageIcon(avatar));
		nameField.setText(user.getUsername());
		g_nick0 = user.getNickname();
		nickField.setText(g_nick0);
	}
}
