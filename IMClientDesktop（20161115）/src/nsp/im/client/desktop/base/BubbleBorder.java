package nsp.im.client.desktop.base;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.border.Border;

import nsp.im.client.desktop.utils.ChatBubble;
import nsp.im.client.desktop.utils.GraphicsUtil;

/**
 * 聊天气泡形状边框
 */
public class BubbleBorder implements Border {
	private Color back;
	private boolean toleft;
	
    /**
     * 构造边框，指定箭头位置
     * @param toleft 箭头是否向左
     * @param backgroud 气泡颜色
     */
    public BubbleBorder(boolean toleft, Color backgroud) {
    	this.toleft = toleft;
        this.back = backgroud;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = GraphicsUtil.createHighQualityGraphics(g);
        //填充父容器
        g2.setColor(c.getParent().getBackground());
        g2.fillRect(0, 0, c.getWidth(), c.getHeight());
        //绘制气泡
        ChatBubble bubble = new ChatBubble(toleft, width, height, back);
        bubble.fillBubble(0, 0, g2);
        bubble.drawBubble(0, 0, g2);
        bubble.fillInnerBubble(0, 0, g2, c.getBackground());
        g2.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new ChatBubble(toleft, 0, 0, back).getInsets();
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

}
