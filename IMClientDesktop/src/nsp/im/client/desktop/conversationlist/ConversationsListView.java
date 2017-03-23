package nsp.im.client.desktop.conversationlist;

import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.List;

import javax.swing.SwingUtilities;

import nsp.im.client.desktop.base.ActionMouseAdapter;
import nsp.im.client.desktop.base.ListView;
import nsp.im.client.desktop.base.SelectableDecorator;
import nsp.im.client.desktop.utils.Globals;
import nsp.im.client.model.Chat;

/**
 * 会话列表
 */
@SuppressWarnings("serial")
public class ConversationsListView extends ListView {
	private ConversationItemFactory itemFactory;
	private HashSet<ConversationClickListener> conversationListeners;
	private HashSet<ConversationCloseListener> closeListeners;
	private Chat selected;
	
	/**
	 * 构建一个会话列表
	 */
	public ConversationsListView() {
		conversationListeners = new HashSet<>();
		closeListeners = new HashSet<>();
	}
	
	/**
	 * 运行会话列表
	 * @param server
	 */
	public void start() {
		if (itemFactory == null) {
			itemFactory = new DefaultConversationItemFactory();
		}
		//进入主界面第一次加载会话列表
		refresh(Globals.getAccount().getChatList().getChats());
		//需要刷新会话列表的情况
		Globals.getAccount().getChatList().getUpdateEvent().addListener(() -> {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					refresh(Globals.getAccount().getChatList().getChats());
				}
			});
		});

		//20160911防止未出现列表的好友消息不能被监听到
//		for(Chat c :Globals.getAccount().getChatList().getChats()){
//			c.addNewMessageListener((msg) -> {
//			SwingUtilities.invokeLater(new Runnable() {
//				@Override
//				public void run() {
//					refresh(Globals.getAccount().getChatList().getChats());
//				}
//			});
//		});
//		}

	}
	
	/**
	 * 手动选择一个会话
	 * @param conversation
	 */
	public void selectConversation(Chat conversation) {
		selected = conversation;
	}
	
	//刷新会话列表
	private void refresh(List<Chat> conversations) {
		removeComponents();
		boolean deselect = selected != null;
		for (final Chat c : conversations) {
			if (selected != null && selected.getRecipient().identify(c.getRecipient()))
				deselect = false;
			SelectableDecorator comp = itemFactory.makeConversationItem(c);
			comp.addMouseListener(new ActionMouseAdapter() {
				@Override
				public void actionPerformed(MouseEvent e) {
					//处理会话的活动状态
					selected = c;
					for (ConversationClickListener l : conversationListeners) {
						l.onClickConversation(c);
					}
				}
			});
			addComponent(comp);
			if (selected != null && selected.getRecipient().identify(c.getRecipient()))
				select(comp);
		}
		if (deselect) {
			selected = null;
			for (ConversationCloseListener l : closeListeners)
				l.onClose();
		}
	}

	/**
	 * 添加会话点击监听器
	 * @param listener 监听器
	 */
	public void addConversationClickListener(ConversationClickListener listener) {
		conversationListeners.add(listener);
	}
	
	/**
	 * 移除会话点击监听器
	 * @param listener 监听器
	 */
	public void removeConversationClickListener(ConversationClickListener listener) {
		conversationListeners.remove(listener);
	}
	
	public void addConversationCloseListener(ConversationCloseListener listener) {
		closeListeners.add(listener);
	}

	public void removeConversationCloseListener(ConversationCloseListener listener) {
		closeListeners.remove(listener);
	}
	
	/**
	 * 会话点击监听器
	 */
	public interface ConversationClickListener {
		/**
		 * 某个会话被点击时执行的方法
		 * @param conversation 会话
		 */
		void onClickConversation(Chat conversation);
	}
	
	public interface ConversationCloseListener {
		void onClose();
	}
}
