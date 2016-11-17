package nsp.im.client.desktop.chatarea;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import nsp.im.client.desktop.base.ScrollDecorator;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.desktop.utils.Globals;
import nsp.im.client.model.Chat;

@SuppressWarnings("serial")
public class ChatPanel extends JPanel {
	private HashMap<Object, ChatDisplayArea> chats;
	private JPanel chatsContainer;
	private CardLayout chatCards;
	private ChatInputArea input;
	private Chat currentChat;

	public ChatPanel() {
		layComponents();
	}

	private void layComponents() {
		setLayout(new BorderLayout());
		chatsContainer = new JPanel();
		chats = new HashMap<>();
		chatCards = new CardLayout();
		chatsContainer.setLayout(chatCards);
		input = new ChatInputArea();
		input.setPreferredSize(new Dimension(0, 200));
		add(input, BorderLayout.SOUTH);
		add(chatsContainer, BorderLayout.CENTER);
	}

	public void start() {
		input.start();
		Globals.getAccount().getChatList().getUpdateEvent().addListener(() -> {
			for (Chat c : Globals.getAccount().getChatList().getChats()) {
				if (currentChat != null && currentChat.getRecipient()
						.identify(c.getRecipient())) {
					return;
				}
			}
			if (currentChat != null) {
				String id = currentChat.getRecipient().identify() + "";
				chats.remove(id);
			}
		});
	}

	public void setConversation(Chat conversation) {
		if (currentChat != null) {
			ChatDisplayArea area =
					chats.get(currentChat.getRecipient().identify());
			if (area != null)
				area.setActive(false);
		}
		currentChat = conversation;
		input.setConversation(conversation);
		Object id = conversation.getRecipient().identify();
		if (!chats.containsKey(id)) {
			ChatDisplayArea area = new ChatDisplayArea();// area:每次新增对话的整行区域
			area.start();
			area.setConversation(conversation);
			ScrollDecorator scrollDisplay = new ScrollDecorator(area, true);
			scrollDisplay.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
					StyleConsts.border));
			chatsContainer.add(
					ScrollDecorator.makeTranslucentBarDecorator(scrollDisplay),
					id);
			chats.put(id, area);
		}
		chats.get(id).setActive(true);
		chatCards.show(chatsContainer, id + "");
	}

	public void requireChatFocus() {
		input.requireChatFocus();
	}
}
