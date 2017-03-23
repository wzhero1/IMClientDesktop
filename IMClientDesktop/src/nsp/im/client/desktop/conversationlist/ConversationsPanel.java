package nsp.im.client.desktop.conversationlist;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import nsp.im.client.desktop.base.ScrollDecorator;
import nsp.im.client.desktop.conversationlist.ConversationsListView.ConversationClickListener;

/**
 * 会话面板
 */
@SuppressWarnings("serial")
public class ConversationsPanel extends JPanel {
	private ConversationsListView listview;

	/**
	 * 构造一个会话面板
	 */
	public ConversationsPanel() {
		layComponents();
	}

	private void layComponents() {
		setLayout(new BorderLayout());
		listview = new ConversationsListView();
		ScrollDecorator sd = new ScrollDecorator(listview);
		add(ScrollDecorator.makeTranslucentBarDecorator(sd),
				BorderLayout.CENTER);
	}

	/**
	 * 运行会话面板
	 * 
	 * @param server IM服务器对象
	 */
	public void start() {
		listview.start();
	}

	/**
	 * 添加会话点击监听器
	 * 
	 * @param listener 监听器
	 */
	public void addConversationClickListener(
			ConversationClickListener listener) {
		listview.addConversationClickListener(listener);
	}

	/**
	 * 移除会话点击监听器
	 * 
	 * @param listener 监听器
	 */
	public void removeConversationClickListener(
			ConversationClickListener listener) {
		listview.removeConversationClickListener(listener);
	}
}
