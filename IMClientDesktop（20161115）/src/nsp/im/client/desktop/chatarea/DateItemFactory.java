package nsp.im.client.desktop.chatarea;

import java.util.Date;

import javax.swing.JComponent;

/**
 * �����������
 */
public interface DateItemFactory {
	/**
	 * ������������һ�����
	 * @param date ����
	 * @return ��������
	 */
	JComponent makeDateItem(Date date);
}
