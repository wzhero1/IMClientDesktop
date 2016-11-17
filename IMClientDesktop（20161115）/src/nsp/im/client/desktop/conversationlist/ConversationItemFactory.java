package nsp.im.client.desktop.conversationlist;

import nsp.im.client.desktop.base.SelectableDecorator;
import nsp.im.client.model.Chat;

/**
 * 对话组件工厂
 */
public interface ConversationItemFactory {
	/**
	 * 根据对话制造一个组件
	 * @param conversation 对话
	 * @return 制造的组件
	 */
	SelectableDecorator makeConversationItem(Chat conversation);
}
