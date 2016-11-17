package nsp.im.client.desktop.base;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * 能纵向添加组件并不断自动扩展的JPanel，并能自动对可选择的组件进行管理
 */
@SuppressWarnings("serial")
public class ListView extends JPanel {
	private SelectableDecorator currentSel; //当前被选组件
	private GridBagConstraints constraints;
	
	public ListView() {
		setLayout(new GridBagLayout());
		constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.PAGE_START;
		constraints.gridx = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridy = GridBagConstraints.RELATIVE;
		constraints.weightx = 0.1;
		constraints.weighty = 0;
	}
	
	/**
	 * 添加一个不可选的组件
	 * @param comp 要添加的组件
	 */
	public void addComponent(JComponent comp) {
		add(comp, constraints);
		revalidate();
	}
	
	/**
	 * 添加一个可选组件
	 * @param comp 要添加的组件
	 */
	public void addComponent(final SelectableDecorator comp) {
		add(comp, constraints);
		//添加鼠标监听器以自动处理选择时间
		comp.addMouseListener(new ActionMouseAdapter() {
			@Override
			public void actionPerformed(MouseEvent e) {
				if (currentSel != null) {
					currentSel.deselect();
				}
				comp.select();
				currentSel = comp;
			}
		});
		revalidate();
	}
	
	/**
	 * 添加一个不可选的组件
	 * @param comp 要添加的组件
	 */
	public void addComponentSilently(JComponent comp) {
		add(comp, constraints);
	}
	
	/**
	 * 添加一个可选组件
	 * @param comp 要添加的组件
	 */
	public void addComponentSilently(final SelectableDecorator comp) {
		add(comp, constraints);
		//添加鼠标监听器以自动处理选择时间
		comp.addMouseListener(new ActionMouseAdapter() {
			@Override
			public void actionPerformed(MouseEvent e) {
				if (currentSel != null) {
					currentSel.deselect();
				}
				comp.select();
				currentSel = comp;
			}
		});
	}
	
	/**
	 * 查看当前被选组件
	 * @return 被选组件
	 */
	protected SelectableDecorator selected() {
		return currentSel;
	}
	
	/**
	 * 手动选择一个组件
	 * @param comp 要选择的组件
	 */
	protected void select(SelectableDecorator comp) {
		if (currentSel != null) {
			currentSel.deselect();
		}
		comp.select();
		currentSel = comp;
	}
	
	/**
	 * 移除所有组件
	 */
	public void removeComponents() {
		removeAll();
		revalidate();
	}
	
	@Override
	public Dimension getPreferredSize() {
		//preferredSize本不会自动调整大小，但minimumSize会
		return getMinimumSize();
	}
	
	@Override
	public boolean isPreferredSizeSet() {
		//使得布局管理器总是调用getPreferredSize
		return true;
	}
}
