package nsp.im.client.desktop.base;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//鼠标的单击
//单独监听
/**
 * 单独监听鼠标左键单击事件，归结到actionPerformed方法
 */
public class ActionMouseAdapter extends MouseAdapter {
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
			actionPerformed(e);
	}
	
	/**
	 * 监听到鼠标左键单击时，触发此方法
	 * @param e 触发方法的鼠标事件
	 */
	public void actionPerformed(MouseEvent e) {}
}
