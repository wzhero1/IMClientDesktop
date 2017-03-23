package nsp.im.client.desktop.base;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class ImageCodeComponent extends JComponent {
	private int g_width = 90;
	private int g_height = 35;
	Random g_random = new Random();// ʵ����Random;
	private String g_code = "";
	private boolean g_change = true;
	private BufferedImage g_image;

	@Override
	public void paint(Graphics g) {
		if (g_change) {
			g_image = new BufferedImage(g_width, g_height, BufferedImage.TYPE_INT_RGB);// ʵ����BufferedImage��
			Graphics gs = g_image.getGraphics();// ��ȡGraphics��Ķ���
			if (!g_code.isEmpty()) {
				g_code = "";// �����֤�룻
			}
			Font font = new Font("����", Font.BOLD, 22);// ͨ��Font�������壻
			gs.setFont(font);// �������壻
			gs.fillRect(0, 0, g_width, g_height);// ���һ�����Σ�
			Image img = null;
			try {
				img = ImageIO.read(new File("res/ImageCodeBackground.png"));// ����ͼ�ζ���
			} catch (IOException e) {
				e.printStackTrace();
			}
			g_image.getGraphics().drawImage(img, 0, 0, g_width, g_height, null);// �ڻ���ͼ������ϻ���ͼ��

			// ���Ƹ����ߣ�
			int startX1 = g_random.nextInt(20);// �����ȡ��һ������������x���ꣻ
			int startY1 = g_random.nextInt(20);// �����ȡ��һ������������y���ꣻ
			int startX2 = g_random.nextInt(30) + 35;// �����ȡ��һ���������յ��x���꣬Ҳ���ǵڶ��������ߵ����x���ꣻ
			int startY2 = g_random.nextInt(10) + 20;// �����ȡ��һ���������յ��y���꣬Ҳ���ǵڶ��������ߵ����y���ꣻ
			int startX3 = g_random.nextInt(30) + 90;// �����ȡ�ڶ����������յ��x���ꣻ
			int startY3 = g_random.nextInt(10) + 5;// �����ȡ�ڶ����������յ��y���ꣻ
			Graphics2D gs2d = (Graphics2D) gs;// ��������תָ���Ƕȣ�gs.setColor(Color.red);
			gs2d.setStroke(new BasicStroke(2.0f, 1, 1, 2.0f));
			gs2d.setColor(new Color(g_random.nextInt(255), g_random.nextInt(255), g_random.nextInt(255)));
			gs2d.drawLine(startX1, startY1, startX2, startY2);// ���Ƶ�һ�������ߣ�
			gs2d.setColor(
					new Color(20 + g_random.nextInt(120), 20 + g_random.nextInt(120), 20 + g_random.nextInt(120)));
			gs2d.drawLine(startX2, startY2, startX3, startY3);// ���Ƶڶ��������ߣ�

			// ����������֤���֣�
			for (int i = 0; i < 4; i++) {
				char ctmp = 'a';// ��ctmp���г�ʼ�����������a~z��A~Z����ĸ��
				if (g_random.nextInt(2) == 0) {
					ctmp = (char) (g_random.nextInt(26) + 65);// ����A~Z�Ĵ�д��ĸ��
				} else {
					ctmp = (char) (g_random.nextInt(26) + 97);// ����A~Z��Сд��ĸ��
				}
				g_code += ctmp;// ������֤�룻
				Color color = new Color(20 + g_random.nextInt(120), 20 + g_random.nextInt(120),
						20 + g_random.nextInt(120));// ���������ɫ��
				gs.setColor(color);// ������ɫ��
				AffineTransform trans = new AffineTransform();// ʵ����AffineTransform��
				trans.rotate(g_random.nextInt(45) * 3.14 / 180, 20 * i + 8, 6);
				float scaleSize = g_random.nextFloat() + 0.8f;// �������֣�
				if (scaleSize > 1f) {
					scaleSize = 1f;// ���scaleSize����1�������1��
				}
				trans.scale(scaleSize, scaleSize);// �������ţ�
				gs2d.setTransform(trans);// ����AffineTransform����
				gs.drawString(String.valueOf(ctmp), g_width / 6 * i + 15, g_height - 10);// ���Ƴ���֤�룻
			}
			g_change = false;
		}
		g.drawImage(g_image, 0, 0, null);// ������л��Ƴ���֤�룻
	}

	/*
	 * public void setChange(boolean value) { g_change = value; }
	 * 
	 * public boolean getChange() { return g_change; }
	 */

	public void draw() {// ������֤��ķ�����
		g_change = true;
		repaint();
	}

	public String getNum() {
		return g_code;
	}
}
