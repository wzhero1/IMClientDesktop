package nsp.im.client.desktop.conversationlist;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nsp.im.client.desktop.base.SelectableDecorator;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.desktop.base.TextRoundPopup;
import nsp.im.client.desktop.utils.ImageUtil;
import nsp.im.client.desktop.utils.NameGenerator;
import nsp.im.client.model.Chat;
import nsp.im.client.model.Group;
import nsp.im.client.model.Recipient;
import nsp.im.client.model.User;
import nsp.im.client.model.msg.FileBody;
import nsp.im.client.model.msg.ImageBody;
import nsp.im.client.model.msg.Message;
import nsp.im.client.model.msg.MessageBody;
import nsp.im.client.model.msg.TextBody;

/**
 * Ĭ�ϵĻỰ�������
 * ���¶Ի��˵���Ϣ����ͷ�������������Ϣ����
 */
public class DefaultConversationItemFactory implements ConversationItemFactory {
	private static boolean isNewMsg;// �ж��Ƿ�������Ϣ��״̬����ʱ�������ȱ�ݣ����ֶ����ϵ��ʱ���һ���ͻ�ֹͣ��˸��

	/**
	 * ����һ���Ự�������
	 * 
	 * @param server
	 */
	public DefaultConversationItemFactory() {
	}

	@Override
	public SelectableDecorator makeConversationItem(final Chat conversation) {
		final JLabel imgLabel = new JLabel(); // ͼƬ��ǩ
		imgLabel.setIcon(makeIcon(conversation));
		final JLabel nameLabel = new JLabel("", JLabel.LEFT); // �Է����Ʊ�ǩ
		nameLabel.setFont(nameLabel.getFont().deriveFont(16f));
		nameLabel.setText(makeName(conversation));
		final JLabel msgLabel = new JLabel("", JLabel.LEFT); // �����Ϣ��ǩ
		msgLabel.setFont(nameLabel.getFont().deriveFont(13f));
		msgLabel.setText(makeMsg(conversation));
		// ���ֱ�ǩ����
//		if(!msgLabel.getText().equals("������Ϣ")) {
		JPanel subPanel = new JPanel();
		subPanel.setOpaque(false);
		subPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		subPanel.setLayout(new GridLayout(0, 1));
		subPanel.add(nameLabel);
		subPanel.add(msgLabel);
		// ���б�ǩ����
		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		panel.add(imgLabel, BorderLayout.WEST);
		panel.add(subPanel, BorderLayout.CENTER);
		// ���ÿ��ö���
		TextRoundPopup menu = new TextRoundPopup();
		menu.addItem("ɾ���Ự��¼", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				conversation.close();
			}
		});
		// ˢ���û���Ⱥ��Ϣ
		panel.setComponentPopupMenu(menu);
		Recipient<?> recipient = conversation.getRecipient();
		if (recipient instanceof User) {
			final User user = (User) recipient;
			user.getUpdateEvent().addListener(() -> {
				nameLabel.setText(makeName(conversation));
				imgLabel.setIcon(makeIcon(conversation));
			});
		} else {
			final Group group = (Group) recipient;
			group.getUpdateEvent().addListener(() -> {
				nameLabel.setText(makeName(conversation));
				imgLabel.setIcon(makeIcon(conversation));
			});
		}
		// ��������Ϣʱ��ˢ�±�ǩ
		conversation.getUpdateEvent().addListener(() -> {
			nameLabel.setText(makeName(conversation));
			msgLabel.setText(makeMsg(conversation));
			imgLabel.setIcon(makeIcon(conversation));
		});
		// ������꽻����ʽ
		@SuppressWarnings("serial")
		SelectableDecorator sel = new SelectableDecorator(panel) {
			@Override
			public void onRestore() {
				panel.setBackground(StyleConsts.listview_back);
				msgLabel.setForeground(StyleConsts.font_gray);
			}

			@Override
			public void onHover() {
				panel.setBackground(StyleConsts.listview_hov);
				msgLabel.setForeground(StyleConsts.font_gray);
			}

			@Override
			public void onSelect() {
				panel.setBackground(StyleConsts.listview_sel);
				msgLabel.setForeground(StyleConsts.font_black);
			}
		};
		return sel;
	}

	// ��ȡ�Ự��Ӧ��ʾ�ĶԷ�����
	private String makeName(Chat conversation) {
		String nick = new NameGenerator().getConvname(conversation);
		return nick;
	}

	// ��ȡ�Ự��Ӧ��ʾ�������Ϣ
	public static String makeMsg(Chat conversation) {
		if (conversation.getMessages().size() > 0) {
			Message<?> last = conversation.getMessages()
					.get(conversation.getMessages().size() - 1);
			MessageBody<?> body = last.getBody();
			if (body instanceof TextBody)
				return ((TextBody) body).getText().replaceAll("/e.*?&/",
						"����");
			else if (body instanceof FileBody)
				return "�ļ�";
			else if (body instanceof ImageBody)
				return "ͼƬ";
		}
		return "������Ϣ";
	}

	// ��ȡ�Ự��Ӧ��ʾ��ͼƬ
	private ImageIcon makeIcon(Chat conversation) {
		BufferedImage avatar = null;
		Recipient<?> recipient = conversation.getRecipient();
		if (recipient instanceof Group) {
			avatar = ImageUtil
					.getGroupImage((Group) recipient, 40);
		} else {
			avatar = ImageUtil
					.getUserImage((User) recipient, 40);
		}
		ImageIcon icon = new ImageIcon(
				ImageUtil.notifiedImage(avatar, conversation.getUnread()));
		return icon;
	}
	public static boolean getIsNewMsg(){
		return isNewMsg;
	}
}
