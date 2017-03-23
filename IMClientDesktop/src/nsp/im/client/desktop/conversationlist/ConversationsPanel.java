package nsp.im.client.desktop.conversationlist;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import nsp.im.client.desktop.base.ScrollDecorator;
import nsp.im.client.desktop.conversationlist.ConversationsListView.ConversationClickListener;

/**
 * �Ự���
 */
@SuppressWarnings("serial")
public class ConversationsPanel extends JPanel {
	private ConversationsListView listview;

	/**
	 * ����һ���Ự���
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
	 * ���лỰ���
	 * 
	 * @param server IM����������
	 */
	public void start() {
		listview.start();
	}

	/**
	 * ��ӻỰ���������
	 * 
	 * @param listener ������
	 */
	public void addConversationClickListener(
			ConversationClickListener listener) {
		listview.addConversationClickListener(listener);
	}

	/**
	 * �Ƴ��Ự���������
	 * 
	 * @param listener ������
	 */
	public void removeConversationClickListener(
			ConversationClickListener listener) {
		listview.removeConversationClickListener(listener);
	}
}
