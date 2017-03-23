package nsp.im.client.desktop.base;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

/**
 * ģ�°�ť��ʽ������¼�������������������¼���ᵽ������������������ͣ�����
 */
public class ButtonStyleMouseAdapter extends MouseAdapter {
	/**
	 * ��ť�ָ�������״̬ʱ�Ķ���
	 */
	public void onRestore() {}
	
	/**
	 * �����ͣ�ڰ�ť��ʱ�Ķ��� 
	 */
	public void onHover() {}
	
	/**
	 * �������ťʱ�Ķ���
	 */
	public void onAct() {}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		onHover();
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		onRestore();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() != MouseEvent.BUTTON1)
			return;
		onAct();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() != MouseEvent.BUTTON1)
			return;
		if (e.getComponent().contains(e.getPoint()))
			onHover();
		else
			onRestore();
	}
	
	/**
	 * һ��ʵ�÷���������Ӽ�����ʱͬʱ��Ŀ��������õ�����״̬
	 * @param c Ŀ�����
	 * @param states Ҫ��ӵİ�ť��ʽ������
	 */
	public static void attach(JComponent c, ButtonStyleMouseAdapter states) {
		c.addMouseListener(states);
		states.onRestore();
	}
}
