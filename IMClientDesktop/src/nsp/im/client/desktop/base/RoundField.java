package nsp.im.client.desktop.base;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

import nsp.im.client.desktop.utils.GraphicsUtil;

/**
 * Բ�Ǿ��ε��ı������
 */
@SuppressWarnings("serial")
public class RoundField extends JTextField {
	private Color border;

	private boolean borderPainted = true;
	private boolean g_focus = false;//��ⲿ����û�ʧȥ�����״̬��
	private boolean g_mouseAttach = false;//�������Ƿ��ڲ�����Χ�ڵ�״̬��
	private ImageIcon g_icon = new ImageIcon(""); //�ı���������ͼ�ꣻ

	/**
	 * ����Բ�Ǿ����ı������
	 * 
	 * @param hover
	 *            ��ͣ��ɫ
	 */
	public RoundField(final Color hover) {
		setBorderAttach(hover,0);
	}
	
	/**
	 * ����Բ�Ǿ����ı������
	 * 
	 * @param hover
	 *            ��ͣ��ɫ
	 * @param path
	 *            �ı���ͼ��ͼƬ����·��
	 */
	public RoundField(final Color hover,String path) {
		setBorderAttach(hover,20);
		g_icon = new ImageIcon(path);
	}
	
	/**
	 * �����ı���߿�仯����
	 * 
	 * @param hover
	 *            ��ͣ��ɫ
	 * @param size
	 *            �������������ı������
	 */
	private void setBorderAttach(Color hover, int size) {
		setFont(getFont().deriveFont(16f));
		setOpaque(false);
		addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				g_focus = false;
				if (!g_mouseAttach) {
					border = StyleConsts.border;
				}
				repaint();
			}

			@Override
			public void focusGained(FocusEvent e) {
				g_focus = true;
			}
		});
		
		ButtonStyleMouseAdapter.attach(this, new ButtonStyleMouseAdapter() {
			@Override
			public void onRestore() {
				if (g_focus) {
					border = hover;
				} else {
					border = StyleConsts.border;
				}
				g_mouseAttach = false;
				repaint();
			}

			@Override
			public void onHover() {
				border = hover;
				g_mouseAttach = true;
				repaint();
			}

			@Override
			public void onAct() {
				border = hover;
				g_mouseAttach = true;
				repaint();
			}
		});
		//�����ı���������20
		Insets insets = new Insets(0, size, 0, 0);
		this.setMargin(insets);
		setBorder(BorderFactory.createEmptyBorder(StyleConsts.corner_rad, StyleConsts.corner_rad + size,
				StyleConsts.corner_rad, StyleConsts.corner_rad));
	}

	public void setBorderPainted(boolean borderPainted) {
		this.borderPainted = borderPainted;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = GraphicsUtil.createHighQualityGraphics(g);
		g2.setColor(StyleConsts.text_back);
		g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, StyleConsts.corner_rad * 2, StyleConsts.corner_rad * 2);
		g2.dispose();
		super.paintComponent(g);
		Insets insets = getInsets();
		//���ı����л���֮ǰͼƬ
		g_icon.paintIcon(this, g, (insets.left-g_icon.getIconWidth())/2, (this.getHeight()-g_icon.getIconHeight())/2);
	}

	@Override
	protected void paintBorder(Graphics g) {
		if (!borderPainted)
			return;
		Graphics2D g2 = GraphicsUtil.createHighQualityGraphics(g);
		g2.setColor(border);
		g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, StyleConsts.corner_rad * 2, StyleConsts.corner_rad * 2);
		g2.dispose();
	}

	/**
	 * ����Ĭ����ʽ�������
	 * 
	 * @return �����������
	 */
	public static RoundField createNormalField() {
		return new RoundField(StyleConsts.btn_sel_back);
	}
}
