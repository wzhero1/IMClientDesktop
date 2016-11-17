package nsp.im.client.desktop.grouplist;

import nsp.im.client.desktop.base.SelectableDecorator;
import nsp.im.client.model.Group;

/**
 * Ⱥ�������
 */
public interface GroupItemFactory {
	/**
	 * ����Ⱥ����һ�����
	 * @param group Ⱥ
	 * @return ��������
	 */
	SelectableDecorator makeGroupItem(Group group);
}
