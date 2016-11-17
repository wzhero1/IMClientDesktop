package nsp.im.client.desktop.usersearch;

import java.util.Collection;

import javax.swing.JComponent;

import nsp.im.client.desktop.base.ListView;
import nsp.im.client.model.User;

@SuppressWarnings("serial")
public class UsersListView extends ListView {
	private UserItemFactory itemFactory;
	
	public UsersListView() {}
	
	public void setUsers(Collection<User> users) {
		removeComponents();
		for (User user: users) {
			JComponent comp = itemFactory.makeUserItem(user);
			addComponent(comp);
		}
		validate();
	}
	
	public void start() {
		if (itemFactory == null)
			itemFactory = new DefaultUserItemFactory();
	}
}
