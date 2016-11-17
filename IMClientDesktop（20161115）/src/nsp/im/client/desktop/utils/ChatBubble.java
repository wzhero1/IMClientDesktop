package nsp.im.client.desktop.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Polygon;

import nsp.im.client.desktop.base.StyleConsts;

/**
 * ����������״
 */
public class ChatBubble {
	private Color border = StyleConsts.border;
	private int radius = StyleConsts.corner_rad;
	private int arrowWidth = 10;
	private int arrowHeight = 5;
	private int arrowY = 10;
	private int bubbleWidth;
	private int bubbleHeight;
	private Color background;
	private boolean toleft;
	private Insets insets;
	
	/**
	 * ����һ����������
	 * @param toleft
	 * @param width
	 * @param height
	 * @param back
	 */
	public ChatBubble(boolean toleft, int width, int height, Color back) {
		bubbleWidth = width;
		bubbleHeight = height;
		background = back;
		this.toleft = toleft;
		insets = new Insets(5, 5, 5, 5);
	}
	
	public void fillBubble(int x, int y, Graphics2D g2) {
        //�����ⲿ����
        g2.setColor(background);
        g2.fillRoundRect((toleft ? arrowWidth : 0) + x, y, bubbleWidth - arrowWidth, 
        		bubbleHeight, radius * 2, radius * 2);
        //�������ݼ�ͷ
        Polygon p = getArrow(x, y);
        g2.setColor(background);
        g2.fillPolygon(p);
	}
	
	public void drawBubble(int x, int y, Graphics2D g2) {
        //���ƾ��α߿�
        g2.setColor(border);
        g2.drawRoundRect((toleft ? arrowWidth : 0) + x, y, bubbleWidth - arrowWidth - 1, 
        		bubbleHeight - 1, radius * 2, radius * 2);
        //���Ƽ�ͷ�߿�
        Polygon p = getArrow(x, y);
        g2.setColor(border);
        g2.drawPolygon(p);
        //������ӱ���
        g2.setColor(background);
        g2.drawLine((toleft ? arrowWidth : bubbleWidth - arrowWidth - 1) + x, arrowY + y,
        		(toleft ? arrowWidth : bubbleWidth - arrowWidth - 1) + x, arrowY + arrowHeight + y);
	}
	
	public void fillInnerBubble(int x, int y, Graphics2D g2, Color back) {
        //�����ڲ�����
        g2.setColor(back);
        g2.fillRoundRect((toleft ? arrowWidth : 0) + insets.left + x, insets.top + y, 
        		bubbleWidth - arrowWidth - insets.left - insets.right, 
        		bubbleHeight - insets.top - insets.bottom, 
        		radius * 2, radius * 2);
	}
	
	/**
	 * ��ȡ���ݵ��߽�ľ���
	 * @return �ĸ�����
	 */
	public Insets getInsets() {
        int value = radius / 2;
        return new Insets(value, value + (toleft ? arrowWidth : 0), 
        		value, value + (toleft ? 0 : arrowWidth));
	}
	
	private Polygon getArrow(int x, int y) {
		//��ȡ��ͷ��״
        Polygon p = new Polygon();
        p.addPoint((toleft ? arrowWidth : bubbleWidth - arrowWidth - 1) + x, arrowY + y);
        p.addPoint((toleft ? arrowWidth : bubbleWidth - arrowWidth - 1) + x, arrowY + arrowHeight + y);
        p.addPoint((toleft ? 0 : bubbleWidth - 1) + x, arrowY + y);
        return p;
	}
}
