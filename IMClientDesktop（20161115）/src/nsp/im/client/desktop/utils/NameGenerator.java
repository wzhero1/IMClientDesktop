package nsp.im.client.desktop.utils;

import nsp.im.client.model.Chat;
import nsp.im.client.model.Group;
import nsp.im.client.model.Member;
import nsp.im.client.model.Recipient;
import nsp.im.client.model.User;

/**
 * ������ȡ��ʾ���Ƶ�ʵ�÷���
 */
public class NameGenerator {

	/**
	 * ��ȡ�Ự��Ӧ��ʾ������
	 * 
	 * @param conv �Ự
	 * @return ��ʾ����
	 */
	public String getConvname(Chat conv) {
		Recipient<?> recipient = conv.getRecipient();
		if (recipient instanceof User)
			return getShowedUsername((User) recipient);
		else
			return ((Group) recipient).getGroupName();
	}

	/**
	 * ��ȡ���û���Ӧ��ʾ�����ƣ�����ȡ����ǩ�������ǳơ������û�����
	 * 
	 * @param user �û�
	 * @return ��ʾ����
	 */
	public String getShowedUsername(User user) {
		String showed = user.getRemark();
		if (showed.length() == 0 && user instanceof Member)
			showed = ((Member) user).getMemberName();
		if (showed.length() == 0)
			showed = user.getNickname();
		if (showed.length() == 0)
			showed = user.getUsername();
		return showed;
	}
}
