package nsp.im.client.desktop.base;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

/**
 * 模仿按钮样式的鼠标事件监听器，将所有鼠标事件归结到三个动作：正常、悬停、点击
 */
public class ButtonStyleMouseAdapter extends MouseAdapter {
	/**
	 * 按钮恢复到正常状态时的动作
	 */
	public void onRestore() {}
	
	/**
	 * 鼠标悬停在按钮上时的动作 
	 */
	public void onHover() {}
	
	/**
	 * 鼠标点击按钮时的动作
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
	 * 一个实用方法，在添加监听器时同时将目标组件设置到正常状态
	 * @param c 目标组件
	 * @param states 要添加的按钮样式监听器
	 */
	public static void attach(JComponent c, ButtonStyleMouseAdapter states) {
		c.addMouseListener(states);
		states.onRestore();
	}
}
