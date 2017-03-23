package nsp.im.client.desktop.base;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import nsp.im.client.desktop.utils.GraphicsUtil;

/**
 * 可以以图片作为背景的JPanel
 */
@SuppressWarnings("serial")
public class ImagePanel extends JPanel {
	private BufferedImage image;
	
	/**
	 * 构造空的ImagePanel
	 */
	public ImagePanel() {
		setOpaque(false);
	}

	/**
	 * 设置背景图
	 * @param image 背景图
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image == null) {
        	return;
        }
        Graphics2D g2 = GraphicsUtil.createHighQualityGraphics(g);
    	g2.drawImage(image, 0, 0, null);
        g2.dispose();
    }
}
