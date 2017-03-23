package nsp.im.client.desktop.base;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;

import nsp.im.client.desktop.utils.GraphicsUtil;

public class RoundLabel extends JButton {
	private static final long serialVersionUID = 1L;

	public RoundLabel() {
		// setForeground(StyleConsts.btn_fore);
		ButtonStyleMouseAdapter.attach(this, new ButtonStyleMouseAdapter() {
			@Override
			public void onRestore() {
				setBackground(StyleConsts.listview_lbl);
			}

			@Override
			public void onHover() {
				setBackground(StyleConsts.btn_act_back);
			}

			@Override
			public void onAct() {
				setBackground(StyleConsts.btn_act_back);// (StyleConsts.close_hov);20160512;
			}
		});

		setContentAreaFilled(false);
		setFocusPainted(false);
		setFont(getFont().deriveFont(16f));
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = GraphicsUtil.createHighQualityGraphics(g);
		g2.setColor(getBackground());
		g2.fillRoundRect(13, 1, getWidth() - 26, getHeight() - 2, StyleConsts.corner_rad * 3,
				StyleConsts.corner_rad * 3);
		g2.dispose();
		super.paintComponent(g);
	}

	@Override
	protected void paintBorder(Graphics g) {
		Graphics2D g2 = GraphicsUtil.createHighQualityGraphics(g);
		g2.setColor(StyleConsts.border);
		g2.drawRoundRect(15, 1, getWidth() - 29, getHeight() - 2, StyleConsts.corner_rad * 3,
				StyleConsts.corner_rad * 3);
		g2.dispose();
	}
}