package nsp.im.client.desktop.utils;

import nsp.im.client.model.Chat;
import nsp.im.client.model.Group;
import nsp.im.client.model.Member;
import nsp.im.client.model.Recipient;
import nsp.im.client.model.User;

/**
 * 包含获取显示名称的实用方法
 */
public class NameGenerator {

	/**
	 * 获取会话所应显示的名称
	 * 
	 * @param conv 会话
	 * @return 显示名称
	 */
	public String getConvname(Chat conv) {
		Recipient<?> recipient = conv.getRecipient();
		if (recipient instanceof User)
			return getShowedUsername((User) recipient);
		else
			return ((Group) recipient).getGroupName();
	}

	/**
	 * 获取【用户】应显示的名称，依次取【标签】→【昵称】→【用户名】
	 * 
	 * @param user 用户
	 * @return 显示名称
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
