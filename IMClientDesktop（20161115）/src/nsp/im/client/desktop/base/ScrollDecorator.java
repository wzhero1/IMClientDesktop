package nsp.im.client.desktop.base;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;

import javax.swing.BoundedRangeModel;
import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.plaf.LayerUI;

import nsp.im.client.desktop.utils.GraphicsUtil;

/**
 * װ������һ��JComponent��ʹ֮��Ƕ��һ���ɹ�����������
 */
@SuppressWarnings("serial")
public class ScrollDecorator extends JScrollPane {
	private JComponent comp;
	private JPanel viewportPanel;

	/**
	 * װ��һ��JComponent
	 * @param comp Ҫװ�ε�JComponent
	 * @param autoScroll �Ƿ�����Զ�����
	 */
	public ScrollDecorator(JComponent comp, boolean autoScroll) {
		this.comp = comp;
		//��Ҫ����һ���ɹ�������壬������������޷���������
		viewportPanel = new ScrollablePanel();
		viewportPanel.setLayout(new GridBagLayout());
		setViewportView(viewportPanel);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.PAGE_START;
		constraints.gridx = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridy = GridBagConstraints.RELATIVE;
		constraints.weightx = 0.1;
		constraints.weighty = 0;
		viewportPanel.add(comp, constraints);
		
		setBackground(comp.getBackground());
		setBorder(null);
		if (autoScroll)
			setAutoScroll();
	}

	/**
	 * װ��һ��JComponent�������Զ�����
	 * @param comp Ҫװ�ε�JComponent
	 */
	public ScrollDecorator(JComponent comp) {
		this(comp, false);
	}

	@Override
	public void setBackground(Color bg) {
		super.setBackground(bg);
		getViewport().setBackground(bg);
	}

	/**
	 * ��ȡ��װ�ε����
	 * @return ��װ�ε����
	 */
	public JComponent getComponent() {
		return comp;
	}

	//�����Զ�����
	private void setAutoScroll() {
		getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {

			private int previousMaximum;
			private long previousTime;
			private boolean adjustScrollBar = true;

			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {

				JScrollBar scrollBar = (JScrollBar) e.getSource();
				BoundedRangeModel listModel = scrollBar.getModel();
				int value = listModel.getValue();
				int extent = listModel.getExtent();
				int maximum = listModel.getMaximum();

				//�������������ֵδ�ı�ʱ����Ϊ���û����ƶ�����������������ײ����򴥷��Զ�����
				if (previousMaximum == maximum && System.currentTimeMillis() - previousTime > 100) {
					adjustScrollBar = value + extent >= maximum;
				}
				
				if (adjustScrollBar) {
					scrollBar.removeAdjustmentListener(this); //��ʱ�Ƴ��˼���������ֹ�ظ�����
					value = maximum - extent;
					scrollBar.setValue(value);
					scrollBar.addAdjustmentListener(this);
				}

				previousMaximum = maximum;
				previousTime = System.currentTimeMillis();
			}
		});
	}

	/**
	 * ��͸����������LayerUI
	 */
	public static class BarLayerUI extends LayerUI<ScrollDecorator> {
		private int normal_alpha = 48;
		private int select_alpha = 80;
		private boolean selected = false;	//����Ƿ���ͣ�ڹ�������
		private boolean contained = false;	//����Ƿ���������
		private boolean dragging = false;	//�Ƿ�����ק������
		private double pBar = 0;
		private ScrollDecorator dec;
		private JScrollBar bar;

		@Override
		public void installUI(JComponent c) {
			super.installUI(c);
			@SuppressWarnings("unchecked")
			JLayer<ScrollDecorator> l = (JLayer<ScrollDecorator>) c;
			dec = l.getView();
			bar = dec.getVerticalScrollBar();
			//������������ƶ��¼�
			l.setLayerEventMask(AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK);
		}

		@Override
		public void paint(Graphics g, JComponent c) {
			super.paint(g, c);
			BoundedRangeModel m = bar.getModel();
			int mh = m.getMaximum() - m.getMinimum();
			if (mh == 0) {
				return;
			}
			if (!contained && !dragging) {
				return;
			}
			if (m.getExtent() == mh) {
				return;
			}
			//Ӧ����ʾ��������������ʾ����ֹλ��
			int mb = m.getValue() - m.getMinimum();
			int me = m.getValue() + m.getExtent() - m.getMinimum();
			int beg = dec.viewport.getHeight() * mb / mh;
			int end = dec.viewport.getHeight() * me / mh;
			Graphics2D g2 = GraphicsUtil.createHighQualityGraphics(g);
			g2.setColor(new Color(0, 0, 0, (selected || dragging) ? select_alpha : normal_alpha));
			g2.fillRoundRect(c.getWidth() - StyleConsts.corner_rad - 2, beg, StyleConsts.corner_rad + 1, end - beg,
					StyleConsts.corner_rad, StyleConsts.corner_rad);
			g2.dispose();
		}

		@Override
		protected void processMouseMotionEvent(MouseEvent e, JLayer<? extends ScrollDecorator> l) {
			Point p = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), l);
			if (l.contains(p) && l.getWidth() - p.getX() < StyleConsts.corner_rad * 2)
				selected = true;
			else
				selected = false;
			l.repaint();
			
			if (dragging) {
				//��������ڹ������ϵİٷֱ�λ�ò��䣬�����µĹ�����λ��
				double pTrack = p.getY() / dec.viewport.getHeight();
				BoundedRangeModel m = bar.getModel();
				int mh = m.getMaximum() - m.getMinimum();
				double prop = (double) m.getExtent() / mh;
				double pVal = pTrack - prop * pBar;
				int val = m.getMinimum() + (int) (mh * pVal);
				m.setValue(val);
			}
		}

		@Override
		protected void processMouseEvent(MouseEvent e, JLayer<? extends ScrollDecorator> l) {
			if (e.getID() == MouseEvent.MOUSE_ENTERED)
				contained = true;
			else if (e.getID() == MouseEvent.MOUSE_EXITED)
				contained = false;
			l.repaint();
			if (e.getID() == MouseEvent.MOUSE_PRESSED && selected) {
				//��ʼ��ק����¼��ǰ����ڹ������ϵİٷֱ�λ��
				Point p = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), l);
				double pTrack = p.getY() / dec.viewport.getHeight();
				BoundedRangeModel m = bar.getModel();
				int mh = m.getMaximum() - m.getMinimum();
				double pStart = (double) (m.getValue() - m.getMinimum()) / mh;
				double pEnd = (double) (m.getExtent()) / mh + pStart;
				pBar = (pTrack - pStart) / (pEnd - pStart);
				dragging = true;
			} else if (e.getID() == MouseEvent.MOUSE_RELEASED) {
				dragging = false;
			}
		}
	}

	/**
	 * ʵ�÷�������ScrollDecorator����Ϊ��͸����������ʽ
	 * @param dec Ҫ���õ�ScrollDecorator
	 * @return ��װ���JLayer���
	 */
	public static JLayer<ScrollDecorator> makeTranslucentBarDecorator(ScrollDecorator dec) {
		dec.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		dec.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		dec.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		return new JLayer<ScrollDecorator>(dec, new BarLayerUI());
	}
}
