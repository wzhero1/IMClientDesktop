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
 * �Ự�б�
 */
@SuppressWarnings("serial")
public class ConversationsListView extends ListView {
	private ConversationItemFactory itemFactory;
	private HashSet<ConversationClickListener> conversationListeners;
	private HashSet<ConversationCloseListener> closeListeners;
	private Chat selected;
	
	/**
	 * ����һ���Ự�б�
	 */
	public ConversationsListView() {
		conversationListeners = new HashSet<>();
		closeListeners = new HashSet<>();
	}
	
	/**
	 * ���лỰ�б�
	 * @param server
	 */
	public void start() {
		if (itemFactory == null) {
			itemFactory = new DefaultConversationItemFactory();
		}
		//�����������һ�μ��ػỰ�б�
		refresh(Globals.getAccount().getChatList().getChats());
		//��Ҫˢ�»Ự�б�����
		Globals.getAccount().getChatList().getUpdateEvent().addListener(() -> {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					refresh(Globals.getAccount().getChatList().getChats());
				}
			});
		});

		//20160911��ֹδ�����б�ĺ�����Ϣ���ܱ�������
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
	 * �ֶ�ѡ��һ���Ự
	 * @param conversation
	 */
	public void selectConversation(Chat conversation) {
		selected = conversation;
	}
	
	//ˢ�»Ự�б�
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
					//����Ự�Ļ״̬
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
	 * ��ӻỰ���������
	 * @param listener ������
	 */
	public void addConversationClickListener(ConversationClickListener listener) {
		conversationListeners.add(listener);
	}
	
	/**
	 * �Ƴ��Ự���������
	 * @param listener ������
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
	 * �Ự���������
	 */
	public interface ConversationClickListener {
		/**
		 * ĳ���Ự�����ʱִ�еķ���
		 * @param conversation �Ự
		 */
		void onClickConversation(Chat conversation);
	}
	
	public interface ConversationCloseListener {
		void onClose();
	}
}
