package nsp.im.client.desktop.grouplist;

import nsp.im.client.desktop.base.SelectableDecorator;
import nsp.im.client.model.Group;

/**
 * 群组件工厂
 */
public interface GroupItemFactory {
	/**
	 * 根据群制造一个组件
	 * @param group 群
	 * @return 制造的组件
	 */
	SelectableDecorator makeGroupItem(Group group);
}
