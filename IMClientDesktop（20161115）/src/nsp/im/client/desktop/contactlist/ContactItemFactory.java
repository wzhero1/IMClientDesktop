package nsp.im.client.desktop.contactlist;

import nsp.im.client.desktop.base.SelectableDecorator;
import nsp.im.client.model.Contact;

/**
 * 联系人组件工厂
 */
public interface ContactItemFactory {
	/**
	 * 根据联系人制造一个组件
	 * @param contact 联系人
	 * @return 制造的组件
	 */
	SelectableDecorator makeContactItem(Contact contact);

}
