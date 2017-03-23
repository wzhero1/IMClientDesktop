package nsp.im.client.desktop.base;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;

import com.alee.laf.button.WebButton;
import nsp.im.client.desktop.utils.GraphicsUtil;

/**
 * 圆角矩形按钮
 */
@SuppressWarnings("serial")
public class RoundButton extends WebButton {
	
	/**
	 * 构造一个圆角矩形按钮
	 * @param normal 正常颜色
	 * @param hover 悬停颜色
	 * @param act 动作颜色
	 */
	public RoundButton(final Color normal, final Color hover, final Color act) {
		setContentAreaFilled(false);
		setFocusPainted(false);
		setForeground(StyleConsts.btn_fore);
		setFont(getFont().deriveFont(16f));
		setDrawSides(false, false, false, false);//不要边框
		ButtonStyleMouseAdapter.attach(this, new ButtonStyleMouseAdapter() {
			@Override
			public void onRestore() {
                setTopBgColor(normal);//weblaf控件下设置背景颜色
				setBottomBgColor(normal);
			}

			@Override
			public void onHover() {
                setTopBgColor(hover);
				setBottomBgColor(hover);
			}
			@Override
			public void onAct() {
                setTopBgColor(act);
				setBottomBgColor(act);
			}

		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = GraphicsUtil.createHighQualityGraphics(g);
		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 
				StyleConsts.corner_rad * 2, StyleConsts.corner_rad * 2);
		g2.dispose();
		super.paintComponent(g);
	}
	
	@Override
	protected void paintBorder(Graphics g) {
		Graphics2D g2 = GraphicsUtil.createHighQualityGraphics(g);
		g2.setColor(StyleConsts.border);
		g2.drawRoundRect(0, 0, getWidth(), getHeight(), 
				StyleConsts.corner_rad * 2, StyleConsts.corner_rad * 2);
		g2.dispose();
	}
	
	/**
	 * 创建默认样式的按钮
	 * @return 创建的按钮
	 */
	public static RoundButton createNormalButton() {
		return new RoundButton(StyleConsts.btn_back, StyleConsts.btn_sel_back, StyleConsts.btn_act_back);
	}
	
	/**
	 * 创建默认样式的危险操作按钮
	 * @return 创建的按钮
	 */
	public static RoundButton createDangerousButton() {
		return new RoundButton(StyleConsts.close_sel, StyleConsts.close_hov, StyleConsts.close_sel);
	}
}
