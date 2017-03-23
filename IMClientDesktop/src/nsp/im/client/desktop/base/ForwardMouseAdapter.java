package nsp.im.client.desktop.base;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 * 转发监听器，将所有监听到的鼠标事件转发给组件的容器
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
		//将当前组件的事件转换为其容器的事件，并在容器上触发此事件
        JComponent source = (JComponent)e.getSource();
        MouseEvent parentEvent = SwingUtilities.convertMouseEvent(source, e, source.getParent());
        source.getParent().dispatchEvent(parentEvent);
	}

}
