package nsp.im.client.desktop.utils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * ��������滭�����ĵ�ʵ�÷���
 */
public class GraphicsUtil {
	/**
	 * �����������Ļ滭������
	 * @param g ԭʼ�滭������
	 * @return �������Ļ滭������
	 */
	public static Graphics2D createHighQualityGraphics(Graphics g) {
		Graphics2D g2d = (Graphics2D)g.create();
		applyQualityRenderingHints(g2d);
		return g2d;
	}

	/**
	 * ���滭�����ĵ�������Ϊ��
	 * @param g2d Ҫ���õĻ滭������
	 */
	public static void applyQualityRenderingHints(Graphics2D g2d) {
		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
	}
}
