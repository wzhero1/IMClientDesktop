package nsp.im.client.desktop.base;

import java.awt.Graphics;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RoundPanel extends JPanel {
	private String path = null;
	
	public RoundPanel() {
	}
	
	public RoundPanel(String path) {
		this.path = path;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		//�Ի���������м���
		g.setClip(new RoundRectangle2D.Double(0, 0, getSize().width, getSize().height,
				StyleConsts.corner_rad * 2, StyleConsts.corner_rad * 2));
		super.paintComponent(g);
		//����JPanel�ı���ͼƬ
		g.drawImage(new ImageIcon(path).getImage(), 0, 0, getWidth(), getHeight(), null);
	}
}
