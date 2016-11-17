package nsp.im.client.desktop.base;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * 将任意组件装饰为一个可选择的组件，需要实现onRestore/onHover/onSelect方法
 */
@SuppressWarnings("serial")
public class SelectableDecorator extends JPanel {
	private JComponent c;
	private boolean selected;
	private MouseAdapter states;
	
	/**
	 * 装饰一个组件使之可选择
	 * @param c 被装饰的组件
	 */
	public SelectableDecorator(JComponent c) {
		this.c = c;
		setOpaque(false);
		setLayout(new BorderLayout());
		add(c, BorderLayout.CENTER);
		states = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (!selected()) {
					onHover();
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if (!selected()) {
					onRestore();
				}
			}
		};
		c.addMouseListener(states);
		c.addMouseListener(new ForwardMouseAdapter());
		onRestore();
	}
	
	/**
	 * 获取被装饰的组件
	 * @return 被装饰的组件
	 */
	public JComponent getComponent() {
		return c;
	}
	
	/**
	 * 选择组件
	 */
	public void select() {
		if (selected)
			return;
		selected = true;
		onSelect();
	}
	
	/**
	 * 取消选择组件
	 */
	public void deselect() {
		if (!selected)
			return;
		selected = false;
		onRestore();
	}
	
	/**
	 * 查看组件是否被选择
	 * @return 是否被选择
	 */
	public boolean selected() {
		return selected;
	}
	
	/**
	 * 组件恢复正常时的动作
	 */
	protected void onRestore() {}
	
	/**
	 * 鼠标悬停在组件上时的动作
	 */
	protected void onHover() {}
	
	/**
	 * 组件被选择时的动作
	 */
	protected void onSelect() {}
}
