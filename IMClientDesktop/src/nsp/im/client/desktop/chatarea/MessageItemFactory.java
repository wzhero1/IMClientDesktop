package nsp.im.client.desktop.chatarea;

import javax.swing.JComponent;

import nsp.im.client.model.msg.UserMessage;

/**
 * 消息组件工厂
 */
public interface MessageItemFactory {
	/**
	 * 根据消息记录制造一个组件
	 * @param record 消息记录
	 * @return 制造的组件
	 */
	JComponent makeMessageItem(UserMessage<?> record);
}
