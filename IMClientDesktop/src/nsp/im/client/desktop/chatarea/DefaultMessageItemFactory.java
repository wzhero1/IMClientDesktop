package nsp.im.client.desktop.chatarea;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.desktop.msgitem.ContentFiller;
import nsp.im.client.desktop.msgitem.FileContentFiller;
import nsp.im.client.desktop.msgitem.ImageContentFiller;
import nsp.im.client.desktop.msgitem.TextContentFiller;
import nsp.im.client.desktop.utils.ImageUtil;
import nsp.im.client.desktop.utils.NameGenerator;
import nsp.im.client.model.Chat;
import nsp.im.client.model.msg.FileBody;
import nsp.im.client.model.msg.ImageBody;
import nsp.im.client.model.msg.MessageBody;
import nsp.im.client.model.msg.TextBody;
import nsp.im.client.model.msg.UserMessage;


public class DefaultMessageItemFactory implements MessageItemFactory {
	private Chat conversation;

	private static HashMap<Class<? extends MessageBody<?>>, Class<? extends ContentFiller>> classMap;

	static {
		classMap = new HashMap<>();
		classMap.put(TextBody.class, TextContentFiller.class);
		classMap.put(ImageBody.class, ImageContentFiller.class);
		classMap.put(FileBody.class, FileContentFiller.class);
	}

	public DefaultMessageItemFactory(Chat conversation) {
		this.conversation = conversation;
	}

	@Override
	public JComponent makeMessageItem(UserMessage<?> record) {
		MessageFrame frame = new MessageFrame(record);//frame:一列消息框架
		frame.setBackground(StyleConsts.main_back);
		JPanel itemPanel = frame.getItemPanel();
		itemPanel.setBackground(StyleConsts.main_back);
		Class<? extends ContentFiller> clazz = classMap
				.get(record.getBody().getClass());
		if (clazz == null)
			return new JLabel();
		ContentFiller filler;
		try {
			filler = clazz.newInstance();
			filler.setup(conversation);
			filler.fillContent(record, itemPanel);
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return frame;
	}

	private class MessageFrame extends JPanel {
		private static final long serialVersionUID = 3949613331728747110L;
		private GridBagConstraints c;
		private JPanel itemPanel;

		public MessageFrame(UserMessage<?> record) {
			setLayout(new GridBagLayout());

			String nick = new NameGenerator().getShowedUsername(record.getSender());
			JLabel nameLabel = new JLabel(nick);
			nameLabel.setForeground(new Color(0x808080));
			BufferedImage avatar = ImageUtil.getUserImage(record.getSender(),
					50);
			JLabel avatarLabel = new JLabel(new ImageIcon(avatar));
			record.getSender().getUpdateEvent().addListener(() -> {
				nameLabel.setText(new NameGenerator().getShowedUsername(record.getSender()));
				avatarLabel.setIcon(new ImageIcon(
						ImageUtil.getUserImage(record.getSender(), 50)));
			});
			JLabel fillLabel = new JLabel();

			avatarLabel.setPreferredSize(new Dimension(50, 50));
			fillLabel.setPreferredSize(new Dimension(50, 50));
			fillLabel.setMinimumSize(new Dimension(50, 50));



			c = new GridBagConstraints();
			c.insets = new Insets(5, 5, 5, 5);
			c.gridy = 0;
			c.gridwidth = 1;
			c.gridheight = 2;
			c.weightx = 0;
			c.weighty = 1;
			c.anchor = GridBagConstraints.NORTH;
			if (!record.isMyMessage()) {
				c.gridx = 0;
				add(avatarLabel, c);
				c.gridx = 2;
				add(fillLabel, c);
			} else {
				c.gridx = 0;
				add(fillLabel, c);
				c.gridx = 2;
				add(avatarLabel, c);
			}

			c.gridheight = 1;
			c.gridx = 1;
			c.weightx = 1;
			c.weighty = 0;
			if (!record.isMyMessage()) {
				c.anchor = GridBagConstraints.WEST;
			} else {
				c.anchor = GridBagConstraints.EAST;
			}
			add(nameLabel, c);

			c.gridy = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.CENTER;
			c.fill = GridBagConstraints.HORIZONTAL;
			itemPanel = new JPanel();
			add(itemPanel, c);
		}

		public JPanel getItemPanel() {
			return itemPanel;
		}
	}

}
