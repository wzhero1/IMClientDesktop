package nsp.im.client.desktop.utils;

import java.awt.GridBagConstraints;

/**
 * ��������GridBagLayout��ʵ�÷���
 */
public class GridBagUtil {

	/**
	 * ��������Լ�����������С
	 * @param c Ҫ���õ�Լ��
	 * @param x Լ����x����
	 * @param y Լ����y����
	 * @param width Լ���Ŀ��
	 * @param height Լ���ĸ߶�
	 */
	static public void setRectangle(GridBagConstraints c, int x, int y, int width, int height) {
		c.gridx = x;//gridx:ָ�������������ʾ����ʼ�ߵĵ�Ԫ�������еĵ�һ����Ԫ��Ϊgridx=0
		c.gridy = y;//ָ��λ�������ʾ����Ķ����ĵ�Ԫ���������ϱߵĵ�Ԫ��Ϊ gridy=0��
		c.gridwidth = width;//ָ�������ʾ�����ĳһ���еĵ�Ԫ������
		c.gridheight = height;//ָ���������ʾ�����һ���еĵ�Ԫ������
	}
}
