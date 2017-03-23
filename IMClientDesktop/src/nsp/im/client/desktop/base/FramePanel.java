package nsp.im.client.desktop.base;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.Transparency;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jhlabs.image.GaussianFilter;

import nsp.im.client.desktop.utils.GraphicsUtil;

/**
 * 置于无边框JFrame中最外层容器的JPanel，决定了窗口的样式，带有圆角边框和背景阴影
 */
@SuppressWarnings("serial")
public class FramePanel extends JPanel {
	private int radius = StyleConsts.corner_rad;
	private int shadowWidth = 3;

	private BufferedImage shadow;

	/**
	 * 构造一个FramePanel
	 */
	public FramePanel() {
		setOpaque(false);
		setBorder(new EmptyBorder(shadowWidth, shadowWidth, shadowWidth, shadowWidth));
	}

	@Override
	public void invalidate() {
		super.invalidate();
	}

	@Override
	protected void paintComponent(Graphics g) {
		//剪裁绘制区域到圆角矩形
		Graphics gc = g.create();
		gc.setClip(getRect());
		super.paintComponent(gc);
		gc.dispose();
		//计算坐标与大小
		Insets insets = getInsets();
		int x = insets.left;
		int y = insets.top;
		int width = getWidth() - (insets.left + insets.right);
		int height = getHeight() - (insets.top + insets.bottom);
		//生成阴影背景
		if (shadow == null) {
			shadow = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = shadow.createGraphics();
			g2d.setColor(getBackground());
			g2d.fillRect(0, 0, width, height);
			g2d.dispose();
			shadow = generateShadow(shadow, shadowWidth, Color.BLACK, 0.9f);
		}
		//绘制阴影
		Graphics2D gc2d = GraphicsUtil.createHighQualityGraphics(g);
		gc2d.drawImage(shadow, 0, 0, getWidth(), getHeight(), this);
		//绘制内容
		gc2d.setColor(getBackground());
		gc2d.fillRoundRect(x, y, width, height, radius * 2, radius * 2);
		gc2d.dispose();
	}

	@Override
	protected void paintChildren(Graphics g) {
		//剪裁绘制区域到圆角矩形
		Graphics gc = g.create();
		gc.setClip(getRect());
		super.paintChildren(gc);
		gc.dispose();
	}
	
	@Override
	protected void paintBorder(Graphics g) {
		//剪裁绘制区域到圆角矩形
		Graphics gc = g.create();
		gc.setClip(getRect());
		super.paintBorder(gc);
		gc.dispose();
	}
	
	private Shape getRect() {
		//获取剪裁区域大小
		Insets insets = getInsets();
		int x = insets.left;
		int y = insets.top;
		int width = getWidth() - (insets.left + insets.right);
		int height = getHeight() - (insets.top + insets.bottom);
		return new RoundRectangle2D.Double(x, y, width, height, radius * 2, radius * 2);
	}

	//以下各方法来自网络，目的在于生成阴影背景图案
	private static BufferedImage createCompatibleImage(int width, int height) {
		return createCompatibleImage(width, height, Transparency.TRANSLUCENT);
	}

	private static BufferedImage createCompatibleImage(int width, int height, int transparency) {
		BufferedImage image = getGraphicsConfigurations().createCompatibleImage(width, height, transparency);
		image.coerceData(true);
		return image;
	}

	private static GraphicsConfiguration getGraphicsConfigurations() {
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
	}

	private static BufferedImage generateBlur(BufferedImage imgSource, int size, Color color, float alpha) {
		GaussianFilter filter = new GaussianFilter(size);

		int imgWidth = imgSource.getWidth();
		int imgHeight = imgSource.getHeight();

		BufferedImage imgBlur = createCompatibleImage(imgWidth, imgHeight);
		Graphics2D g2 = imgBlur.createGraphics();
		GraphicsUtil.applyQualityRenderingHints(g2);

		g2.drawImage(imgSource, 0, 0, null);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, alpha));
		g2.setColor(color);

		g2.fillRect(0, 0, imgSource.getWidth(), imgSource.getHeight());
		g2.dispose();

		imgBlur = filter.filter(imgBlur, null);

		return imgBlur;
	}

	private static BufferedImage generateShadow(BufferedImage imgSource, int size, Color color, float alpha) {
		int imgWidth = imgSource.getWidth() + (size * 2);
		int imgHeight = imgSource.getHeight() + (size * 2);

		BufferedImage imgMask = createCompatibleImage(imgWidth, imgHeight);
		Graphics2D g2 = imgMask.createGraphics();
		GraphicsUtil.applyQualityRenderingHints(g2);

		int x = Math.round((imgWidth - imgSource.getWidth()) / 2f);
		int y = Math.round((imgHeight - imgSource.getHeight()) / 2f);
		g2.drawImage(imgSource, x, y, null);
		g2.dispose();

		// ---- Blur here ---
		BufferedImage imgGlow = generateBlur(imgMask, (size * 2), color, alpha);

		return imgGlow;
	}
}
