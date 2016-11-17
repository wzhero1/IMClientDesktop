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
 * 默认的会话组件工厂
 * 更新对话人的消息个数头像与最后发来的信息内容
 */
public class DefaultConversationItemFactory implements ConversationItemFactory {
	private static boolean isNewMsg;// 判断是否有新消息的状态（暂时设计上有缺陷，出现多个联系人时点击一个就会停止闪烁）

	/**
	 * 构造一个会话组件工厂
	 * 
	 * @param server
	 */
	public DefaultConversationItemFactory() {
	}

	@Override
	public SelectableDecorator makeConversationItem(final Chat conversation) {
		final JLabel imgLabel = new JLabel(); // 图片标签
		imgLabel.setIcon(makeIcon(conversation));
		final JLabel nameLabel = new JLabel("", JLabel.LEFT); // 对方名称标签
		nameLabel.setFont(nameLabel.getFont().deriveFont(16f));
		nameLabel.setText(makeName(conversation));
		final JLabel msgLabel = new JLabel("", JLabel.LEFT); // 最后消息标签
		msgLabel.setFont(nameLabel.getFont().deriveFont(13f));
		msgLabel.setText(makeMsg(conversation));
		// 文字标签容器
//		if(!msgLabel.getText().equals("暂无消息")) {
		JPanel subPanel = new JPanel();
		subPanel.setOpaque(false);
		subPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		subPanel.setLayout(new GridLayout(0, 1));
		subPanel.add(nameLabel);
		subPanel.add(msgLabel);
		// 所有标签容器
		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		panel.add(imgLabel, BorderLayout.WEST);
		panel.add(subPanel, BorderLayout.CENTER);
		// 设置可用动作
		TextRoundPopup menu = new TextRoundPopup();
		menu.addItem("删除会话记录", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				conversation.close();
			}
		});
		// 刷新用户和群信息
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
		// 产生新消息时，刷新标签
		conversation.getUpdateEvent().addListener(() -> {
			nameLabel.setText(makeName(conversation));
			msgLabel.setText(makeMsg(conversation));
			imgLabel.setIcon(makeIcon(conversation));
		});
		// 设置鼠标交互样式
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

	// 获取会话所应显示的对方名称
	private String makeName(Chat conversation) {
		String nick = new NameGenerator().getConvname(conversation);
		return nick;
	}

	// 获取会话所应显示的最后消息
	public static String makeMsg(Chat conversation) {
		if (conversation.getMessages().size() > 0) {
			Message<?> last = conversation.getMessages()
					.get(conversation.getMessages().size() - 1);
			MessageBody<?> body = last.getBody();
			if (body instanceof TextBody)
				return ((TextBody) body).getText().replaceAll("/e.*?&/",
						"表情");
			else if (body instanceof FileBody)
				return "文件";
			else if (body instanceof ImageBody)
				return "图片";
		}
		return "暂无消息";
	}

	// 获取会话所应显示的图片
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
