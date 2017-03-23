package nsp.im.client.desktop.base;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * �������������������Զ���չ��JPanel�������Զ��Կ�ѡ���������й���
 */
@SuppressWarnings("serial")
public class ListView extends JPanel {
	private SelectableDecorator currentSel; //��ǰ��ѡ���
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
	 * ���һ������ѡ�����
	 * @param comp Ҫ��ӵ����
	 */
	public void addComponent(JComponent comp) {
		add(comp, constraints);
		revalidate();
	}
	
	/**
	 * ���һ����ѡ���
	 * @param comp Ҫ��ӵ����
	 */
	public void addComponent(final SelectableDecorator comp) {
		add(comp, constraints);
		//��������������Զ�����ѡ��ʱ��
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
	 * ���һ������ѡ�����
	 * @param comp Ҫ��ӵ����
	 */
	public void addComponentSilently(JComponent comp) {
		add(comp, constraints);
	}
	
	/**
	 * ���һ����ѡ���
	 * @param comp Ҫ��ӵ����
	 */
	public void addComponentSilently(final SelectableDecorator comp) {
		add(comp, constraints);
		//��������������Զ�����ѡ��ʱ��
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
	 * �鿴��ǰ��ѡ���
	 * @return ��ѡ���
	 */
	protected SelectableDecorator selected() {
		return currentSel;
	}
	
	/**
	 * �ֶ�ѡ��һ�����
	 * @param comp Ҫѡ������
	 */
	protected void select(SelectableDecorator comp) {
		if (currentSel != null) {
			currentSel.deselect();
		}
		comp.select();
		currentSel = comp;
	}
	
	/**
	 * �Ƴ��������
	 */
	public void removeComponents() {
		removeAll();
		revalidate();
	}
	
	@Override
	public Dimension getPreferredSize() {
		//preferredSize�������Զ�������С����minimumSize��
		return getMinimumSize();
	}
	
	@Override
	public boolean isPreferredSizeSet() {
		//ʹ�ò��ֹ��������ǵ���getPreferredSize
		return true;
	}
}
