package nsp.im.client.desktop.base;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 * ת���������������м�����������¼�ת�������������
 */
public class ForwardMouseAdapter implements MouseListener, MouseWheelListener, MouseMotionListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		redispatch(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		redispatch(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		redispatch(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		redispatch(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		redispatch(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		redispatch(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		redispatch(e);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		redispatch(e);
	}
	
	private void redispatch(MouseEvent e) {
		//����ǰ������¼�ת��Ϊ���������¼������������ϴ������¼�
        JComponent source = (JComponent)e.getSource();
        MouseEvent parentEvent = SwingUtilities.convertMouseEvent(source, e, source.getParent());
        source.getParent().dispatchEvent(parentEvent);
	}

}
