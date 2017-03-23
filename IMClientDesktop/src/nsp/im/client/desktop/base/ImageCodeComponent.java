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
	Random g_random = new Random();// 实例化Random;
	private String g_code = "";
	private boolean g_change = true;
	private BufferedImage g_image;

	@Override
	public void paint(Graphics g) {
		if (g_change) {
			g_image = new BufferedImage(g_width, g_height, BufferedImage.TYPE_INT_RGB);// 实例化BufferedImage；
			Graphics gs = g_image.getGraphics();// 获取Graphics类的对象；
			if (!g_code.isEmpty()) {
				g_code = "";// 清空验证码；
			}
			Font font = new Font("黑体", Font.BOLD, 22);// 通过Font构造字体；
			gs.setFont(font);// 设置字体；
			gs.fillRect(0, 0, g_width, g_height);// 填充一个矩形；
			Image img = null;
			try {
				img = ImageIO.read(new File("res/ImageCodeBackground.png"));// 创建图形对象；
			} catch (IOException e) {
				e.printStackTrace();
			}
			g_image.getGraphics().drawImage(img, 0, 0, g_width, g_height, null);// 在缓冲图像对象上绘制图像；

			// 绘制干扰线；
			int startX1 = g_random.nextInt(20);// 随机获取第一条干扰线起点的x坐标；
			int startY1 = g_random.nextInt(20);// 随机获取第一条干扰线起点的y坐标；
			int startX2 = g_random.nextInt(30) + 35;// 随机获取第一条干扰线终点的x坐标，也就是第二条干扰线的起点x坐标；
			int startY2 = g_random.nextInt(10) + 20;// 随机获取第一条干扰线终点的y坐标，也就是第二条干扰线的起点y坐标；
			int startX3 = g_random.nextInt(30) + 90;// 随机获取第二条干扰线终点的x坐标；
			int startY3 = g_random.nextInt(10) + 5;// 随机获取第二条干扰线终点的y坐标；
			Graphics2D gs2d = (Graphics2D) gs;// 将文字旋转指定角度；gs.setColor(Color.red);
			gs2d.setStroke(new BasicStroke(2.0f, 1, 1, 2.0f));
			gs2d.setColor(new Color(g_random.nextInt(255), g_random.nextInt(255), g_random.nextInt(255)));
			gs2d.drawLine(startX1, startY1, startX2, startY2);// 绘制第一条干扰线；
			gs2d.setColor(
					new Color(20 + g_random.nextInt(120), 20 + g_random.nextInt(120), 20 + g_random.nextInt(120)));
			gs2d.drawLine(startX2, startY2, startX3, startY3);// 绘制第二条干扰线；

			// 输出随机的验证文字；
			for (int i = 0; i < 4; i++) {
				char ctmp = 'a';// 对ctmp进行初始化，随机生成a~z或A~Z的字母；
				if (g_random.nextInt(2) == 0) {
					ctmp = (char) (g_random.nextInt(26) + 65);// 生成A~Z的大写字母；
				} else {
					ctmp = (char) (g_random.nextInt(26) + 97);// 生成A~Z的小写字母；
				}
				g_code += ctmp;// 更新验证码；
				Color color = new Color(20 + g_random.nextInt(120), 20 + g_random.nextInt(120),
						20 + g_random.nextInt(120));// 生成随机颜色；
				gs.setColor(color);// 设置颜色；
				AffineTransform trans = new AffineTransform();// 实例化AffineTransform；
				trans.rotate(g_random.nextInt(45) * 3.14 / 180, 20 * i + 8, 6);
				float scaleSize = g_random.nextFloat() + 0.8f;// 缩放文字；
				if (scaleSize > 1f) {
					scaleSize = 1f;// 如果scaleSize大于1，则等于1；
				}
				trans.scale(scaleSize, scaleSize);// 进行缩放；
				gs2d.setTransform(trans);// 设置AffineTransform对象；
				gs.drawString(String.valueOf(ctmp), g_width / 6 * i + 15, g_height - 10);// 绘制出验证码；
			}
			g_change = false;
		}
		g.drawImage(g_image, 0, 0, null);// 在面板中绘制出验证码；
	}

	/*
	 * public void setChange(boolean value) { g_change = value; }
	 * 
	 * public boolean getChange() { return g_change; }
	 */

	public void draw() {// 生成验证码的方法；
		g_change = true;
		repaint();
	}

	public String getNum() {
		return g_code;
	}
}
