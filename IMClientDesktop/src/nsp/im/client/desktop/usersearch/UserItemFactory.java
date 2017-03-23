package nsp.im.client.desktop.usersearch;

import javax.swing.JComponent;

import nsp.im.client.model.User;

/**
 * 用户组件工厂
 */
public interface UserItemFactory {
	/**
	 * 根据用户制造一个组件
	 * @param user 用户
	 * @return 制造的组件
	 */
	JComponent makeUserItem(User user);
}
