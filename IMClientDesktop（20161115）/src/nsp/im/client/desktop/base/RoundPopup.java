package nsp.im.client.desktop.base;

import com.alee.laf.menu.WebPopupMenu;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 * 圆角矩形弹出式菜单，调用setContentLayout和addContent对其中内容进行操作
 */
@SuppressWarnings("serial")
public class RoundPopup extends WebPopupMenu {
	private FramePanel content;
	
	/**
	 * 构造一个圆角矩形弹出式菜单
	 */
	public RoundPopup() {
		setBorder(null);
		content = new FramePanel();
		setLayout(new BorderLayout());
		add(content, BorderLayout.CENTER);
	}
	
	/**
	 * 获取内容容器
	 * @return 内容容器
	 */
	public JPanel getContent() {
		return content;
	}
	
	/**
	 * 设置内容布局
	 * @param mgr 内容布局
	 */
	public void setContentLayout(LayoutManager mgr) {
		content.setLayout(mgr);
	}
	
	/**
	 * 添加组件到内容中
	 * @param comp 要添加的组件
	 * @param constraints 布局约束
	 */
	public void addContent(Component comp, Object constraints) {
		content.add(comp, constraints);
	}

	/**
	 * 添加组件到内容中
	 * @param comp 要添加的组件
	 */
	public void addContent(Component comp) {
		content.add(comp);
	}
	
	@Override
	protected void paintBorder(Graphics g) {
		//禁止绘制边框
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		//禁止绘制内容
	}
	
	
}
