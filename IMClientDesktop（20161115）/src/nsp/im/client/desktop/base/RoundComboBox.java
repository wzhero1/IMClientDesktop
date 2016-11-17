package nsp.im.client.desktop.base;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseEvent;

import javax.swing.ComboBoxEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.plaf.basic.BasicComboBoxUI;

import com.alee.laf.combobox.WebComboBox;
import nsp.im.client.desktop.utils.GraphicsUtil;

/**
 * Բ�Ǿ��ε����������
 */
@SuppressWarnings("serial")
public class RoundComboBox extends WebComboBox{
	private Color border;

	/**
	 * ����һ��Բ�Ǿ������������
	 * @param hover ��ͣ��ɫ
	 */
	public RoundComboBox(Color hover) {
		this(hover, true);
	}
	
	//setui��ѡ���Է���������ж���UI
	protected RoundComboBox(final Color hover, boolean setui) {
		setFont(getFont().deriveFont(16f));
		setOpaque(false);
		setEditable(true);
		ButtonStyleMouseAdapter.attach(this, new ButtonStyleMouseAdapter() {
			@Override
			public void onRestore() {
				border = StyleConsts.border;
				repaint();
			}
			@Override
			public void onHover() {
				border = hover;
				repaint();
			}
			@Override
			public void onAct() {
				border = hover;
				repaint();
			}
		});
		//��UI�����ڲ������ʽ
		if (setui)
			setUI(new RoundComboBoxUI());
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = GraphicsUtil.createHighQualityGraphics(g);
		g2.setColor(StyleConsts.text_back);
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), StyleConsts.corner_rad * 2, StyleConsts.corner_rad * 2);
		g2.dispose();
		super.paintComponent(g);
	}
	
	@Override
	protected void paintBorder(Graphics g) {
		Graphics2D g2 = GraphicsUtil.createHighQualityGraphics(g);
		g2.setColor(border);
		g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, StyleConsts.corner_rad * 2, StyleConsts.corner_rad * 2);
		g2.dispose();
	}
	
	/**
	 * ���ڴ���ComboBox�ڲ��������ʽ
	 */
	protected static class RoundComboBoxUI extends BasicComboBoxUI {
		
		@Override
		protected JButton createArrowButton() {
			JButton b = new TransparentArrowButton();
			//��Ҫֹͣת���������¼��������޷����������˵�
			b.addMouseListener(new ForwardMouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {}
				@Override
				public void mouseReleased(MouseEvent e) {}
			});
			return b;
		}
		
		@Override
		protected ComboBoxEditor createEditor() {
			ComboBoxEditor e = new TransparentComboEditor();
			return e;
		}
		
		@Override
		protected void installListeners() {
			super.installListeners();
			//��ֹ�ڵ�������ʱ���������˵�
			comboBox.removeMouseListener(popupMouseListener);
		}
		
		@Override
		public void configureArrowButton() {
			// TODO Auto-generated method stub
			super.configureArrowButton();
			arrowButton.setFocusable(false);
		}
	}
	
	/**
	 * ComboBox�ڲ���������ť��Ϊ�������Σ�͸������
	 */
	private static class TransparentArrowButton extends JButton {
		
		private Color arrow;

		/**
		 * ����һ��������ť
		 */
		public TransparentArrowButton() {
			ButtonStyleMouseAdapter.attach(this, new ButtonStyleMouseAdapter() {
				@Override
				public void onRestore() {
					arrow = StyleConsts.listview_hov;
					repaint();
				}
				@Override
				public void onHover() {
					arrow = StyleConsts.side_back;
					repaint();
				}
				@Override
				public void onAct() {
					arrow = StyleConsts.side_back;
					repaint();
				}
			});
			setContentAreaFilled(false);
			setFocusPainted(true);
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = GraphicsUtil.createHighQualityGraphics(g);
			g2.setColor(arrow);
	        Polygon p = new Polygon();
//	        p.addPoint((getWidth() + 10) / 2, (getWidth() - 25) / 2);
//	        p.addPoint((getWidth() + 30) / 2, (getWidth() - 25) / 2);
//	        p.addPoint((getWidth()+ 20) / 2, (getWidth() - 15) / 2);
			p.addPoint((getWidth() - 10) / 2, (getWidth() - 5) / 2);
			p.addPoint((getWidth() + 10) / 2, (getWidth() - 5) / 2);
			p.addPoint((getWidth()) / 2, (getWidth() + 5) / 2);
	        g2.fillPolygon(p);
			g2.dispose();
			super.paintComponent(g);
		}
		
		@Override
		protected void paintBorder(Graphics g) {
			//��ֹ������߿�
		}
	}
	
	/**
	 * ComboBox�ڲ��������������
	 */
	protected static class TransparentComboEditor extends BasicComboBoxEditor {
		
		@Override
		public JTextField createEditorComponent() {
			JTextField f = new EditorField();
			f.setBackground(StyleConsts.text_back);
			f.addMouseListener(new ForwardMouseAdapter());
			return f;
		}
	}
	
	/**
	 * ComboBox�ڲ��������
	 */
	private static class EditorField extends RoundField {

		public EditorField() {
			super(StyleConsts.text_back);
			//��ֹ����߿����ʾ
			setBorderPainted(false);
		}
	}
	
	/**
	 * ����Ĭ����ʽ��Բ�����������
	 * @return �����������
	 */
	public static RoundComboBox createNormalComboBox() {
		return new RoundComboBox(StyleConsts.btn_sel_back);
	}
}
