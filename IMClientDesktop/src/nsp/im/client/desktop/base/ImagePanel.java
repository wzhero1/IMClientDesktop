package nsp.im.client.desktop.base;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import nsp.im.client.desktop.utils.GraphicsUtil;

/**
 * ������ͼƬ��Ϊ������JPanel
 */
@SuppressWarnings("serial")
public class ImagePanel extends JPanel {
	private BufferedImage image;
	
	/**
	 * ����յ�ImagePanel
	 */
	public ImagePanel() {
		setOpaque(false);
	}

	/**
	 * ���ñ���ͼ
	 * @param image ����ͼ
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
