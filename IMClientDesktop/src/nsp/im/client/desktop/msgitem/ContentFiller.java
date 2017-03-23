package nsp.im.client.desktop.msgitem;

import javax.swing.JPanel;

import nsp.im.client.model.Chat;
import nsp.im.client.model.msg.UserMessage;

/**
 * ������Ϣ���ݶ������������
 */
public interface ContentFiller {
	/**
	 * ���������������
	 * @param server IM����������
	 * @param conversation �Ự
	 */
	void setup(Chat conversation);
	/**
	 * �����Ϣ����
	 * @param record ��Ϣ��¼
	 * @param parent Ҫ��������
	 */
	void fillContent(UserMessage<?> record, JPanel parent);
}
