package nsp.im.client.desktop.base;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//���ĵ���
//��������
/**
 * �������������������¼�����ᵽactionPerformed����
 */
public class ActionMouseAdapter extends MouseAdapter {
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
			actionPerformed(e);
	}
	
	/**
	 * ����������������ʱ�������˷���
	 * @param e ��������������¼�
	 */
	public void actionPerformed(MouseEvent e) {}
}
