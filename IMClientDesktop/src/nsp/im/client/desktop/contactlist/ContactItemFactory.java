package nsp.im.client.desktop.contactlist;

import nsp.im.client.desktop.base.SelectableDecorator;
import nsp.im.client.model.Contact;

/**
 * ��ϵ���������
 */
public interface ContactItemFactory {
	/**
	 * ������ϵ������һ�����
	 * @param contact ��ϵ��
	 * @return ��������
	 */
	SelectableDecorator makeContactItem(Contact contact);

}
