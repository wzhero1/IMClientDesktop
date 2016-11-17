package nsp.im.client.desktop.base;

import com.alee.laf.menu.WebPopupMenu;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 * Բ�Ǿ��ε���ʽ�˵�������setContentLayout��addContent���������ݽ��в���
 */
@SuppressWarnings("serial")
public class RoundPopup extends WebPopupMenu {
	private FramePanel content;
	
	/**
	 * ����һ��Բ�Ǿ��ε���ʽ�˵�
	 */
	public RoundPopup() {
		setBorder(null);
		content = new FramePanel();
		setLayout(new BorderLayout());
		add(content, BorderLayout.CENTER);
	}
	
	/**
	 * ��ȡ��������
	 * @return ��������
	 */
	public JPanel getContent() {
		return content;
	}
	
	/**
	 * �������ݲ���
	 * @param mgr ���ݲ���
	 */
	public void setContentLayout(LayoutManager mgr) {
		content.setLayout(mgr);
	}
	
	/**
	 * ��������������
	 * @param comp Ҫ��ӵ����
	 * @param constraints ����Լ��
	 */
	public void addContent(Component comp, Object constraints) {
		content.add(comp, constraints);
	}

	/**
	 * ��������������
	 * @param comp Ҫ��ӵ����
	 */
	public void addContent(Component comp) {
		content.add(comp);
	}
	
	@Override
	protected void paintBorder(Graphics g) {
		//��ֹ���Ʊ߿�
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		//��ֹ��������
	}
	
	
}
