package nsp.im.client.desktop.conversationlist;

import nsp.im.client.desktop.base.SelectableDecorator;
import nsp.im.client.model.Chat;

/**
 * �Ի��������
 */
public interface ConversationItemFactory {
	/**
	 * ���ݶԻ�����һ�����
	 * @param conversation �Ի�
	 * @return ��������
	 */
	SelectableDecorator makeConversationItem(Chat conversation);
}
