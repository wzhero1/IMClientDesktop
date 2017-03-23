package nsp.im.client.desktop.simplegui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import nsp.im.client.desktop.base.HintField;
import nsp.im.client.desktop.base.RoundButton;
import nsp.im.client.desktop.base.RoundPanel;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.desktop.utils.ImageUtil;
import nsp.im.client.model.Contact;

@SuppressWarnings("serial")
public class ContactPanel extends RoundPanel {
	private JLabel avatarImage;
	private JLabel nickLabel;
	private JLabel nameLabel;
	private JLabel nameField;
	private JLabel remarkLabel;
	private HintField remarkField;
	private JButton chatBtn;
	private HashSet<ChatListener> listeners;
	private Contact currentContact;

	public ContactPanel() {
		setBackground(StyleConsts.main_back);
		listeners = new HashSet<>();
		layComponents();
	}

	private void layComponents() {

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;

		avatarImage = new JLabel();
		avatarImage.setHorizontalAlignment(JLabel.CENTER);
		c.gridwidth = 2;
		add(avatarImage, c);

		nickLabel = new JLabel();
		nickLabel.setHorizontalAlignment(JLabel.CENTER);
		nickLabel.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.BOLD, 25));// 20160518;(27f);
		c.insets = new Insets(5, 0, 15, 0);// 20160518;
		c.gridy = 1;
		add(nickLabel, c);

		nameLabel = new JLabel("”√ªß√˚£∫", JLabel.TRAILING);
		nameLabel.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 13));// 20160518;(15f);
		c.insets = new Insets(10, 0, 0, 0);// 20160518;(5, 5, 5, 5);
		c.gridwidth = 1;
		c.gridy = 2;
		add(nameLabel, c);

		remarkLabel = new JLabel("±∏   ◊¢£∫", JLabel.TRAILING);// 20160518;
		remarkLabel.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 13));// 20160518;(15f);
		c.gridy = 3;
		add(remarkLabel, c);

		nameField = new JLabel("");
		nameField.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.BOLD, 14));// 20160518;(15f);
		c.insets = new Insets(8, 0, 0, 0);// 20160518;
		c.gridx = 1;
		c.gridy = 2;
		add(nameField, c);

		remarkField = HintField.createNormalField("∞¥°æEnter°øº¸ÃÌº”…Ë÷√");// 20160518;("Œ¥…Ë÷√");
		remarkField.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 13));// 20160518;(15f);
		remarkField.setColumns(12);// 20160518;(15);
		c.insets = new Insets(10, 0, 0, 0);// 20160518;(5, 5, 5, 5);
		c.gridy = 3;
		add(remarkField, c);

		chatBtn = RoundButton.createNormalButton();
		chatBtn.setText("∑¢ œ˚ œ¢");// 20160518;("∑¢œ˚œ¢");
		chatBtn.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 15));// 20160518;(15f);
		c.insets = new Insets(15, 0, 35, 0);// 20160518;(10, 5, 10, 5);
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2;
		c.ipady = 5;
		add(chatBtn, c);

		setFocusable(true);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				requestFocusInWindow();
			}
		});
	}

	public void start() {
		remarkField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String r = remarkField.getText();
				currentContact.changeRemark(r);
			}
		});
		chatBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (ChatListener l : listeners)
					l.onChat(currentContact);
			}
		});
	}

	public void setContact(Contact contact) {
		currentContact = contact;
		updateContact(contact);
	}

	public void addChatListener(ChatListener l) {
		listeners.add(l);
	}

	public void removeChatListener(ChatListener l) {
		listeners.remove(l);
	}

	public interface ChatListener {
		void onChat(Contact contact);
	}
	
	private void updateContact(Contact contact) {
		BufferedImage avatar = ImageUtil.getUserImage(contact, 100);
		avatarImage.setIcon(new ImageIcon(avatar));
		String nick = contact.getNickname();
		nickLabel.setText(nick);
		nameField.setText(contact.getUsername());
		String remark = contact.getRemark();
		remarkField.setText(remark);
	}
}
