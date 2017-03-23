package nsp.im.client.desktop.base;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * ���������װ��Ϊһ����ѡ����������Ҫʵ��onRestore/onHover/onSelect����
 */
@SuppressWarnings("serial")
public class SelectableDecorator extends JPanel {
	private JComponent c;
	private boolean selected;
	private MouseAdapter states;
	
	/**
	 * װ��һ�����ʹ֮��ѡ��
	 * @param c ��װ�ε����
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
	 * ��ȡ��װ�ε����
	 * @return ��װ�ε����
	 */
	public JComponent getComponent() {
		return c;
	}
	
	/**
	 * ѡ�����
	 */
	public void select() {
		if (selected)
			return;
		selected = true;
		onSelect();
	}
	
	/**
	 * ȡ��ѡ�����
	 */
	public void deselect() {
		if (!selected)
			return;
		selected = false;
		onRestore();
	}
	
	/**
	 * �鿴����Ƿ�ѡ��
	 * @return �Ƿ�ѡ��
	 */
	public boolean selected() {
		return selected;
	}
	
	/**
	 * ����ָ�����ʱ�Ķ���
	 */
	protected void onRestore() {}
	
	/**
	 * �����ͣ�������ʱ�Ķ���
	 */
	protected void onHover() {}
	
	/**
	 * �����ѡ��ʱ�Ķ���
	 */
	protected void onSelect() {}
}
