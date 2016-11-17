package nsp.im.client.desktop.chatarea;

import javax.swing.JComponent;

import nsp.im.client.model.msg.UserMessage;

/**
 * ��Ϣ�������
 */
public interface MessageItemFactory {
	/**
	 * ������Ϣ��¼����һ�����
	 * @param record ��Ϣ��¼
	 * @return ��������
	 */
	JComponent makeMessageItem(UserMessage<?> record);
}
