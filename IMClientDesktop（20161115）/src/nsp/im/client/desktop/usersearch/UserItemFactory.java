package nsp.im.client.desktop.usersearch;

import javax.swing.JComponent;

import nsp.im.client.model.User;

/**
 * �û��������
 */
public interface UserItemFactory {
	/**
	 * �����û�����һ�����
	 * @param user �û�
	 * @return ��������
	 */
	JComponent makeUserItem(User user);
}
