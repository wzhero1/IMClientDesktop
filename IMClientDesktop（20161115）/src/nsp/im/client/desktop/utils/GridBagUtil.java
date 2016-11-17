package nsp.im.client.desktop.utils;

import java.awt.GridBagConstraints;

/**
 * 包含关于GridBagLayout的实用方法
 */
public class GridBagUtil {

	/**
	 * 快速设置约束的坐标与大小
	 * @param c 要设置的约束
	 * @param x 约束的x坐标
	 * @param y 约束的y坐标
	 * @param width 约束的宽度
	 * @param height 约束的高度
	 */
	static public void setRectangle(GridBagConstraints c, int x, int y, int width, int height) {
		c.gridx = x;//gridx:指定包含组件的显示区域开始边的单元格，其中行的第一个单元格为gridx=0
		c.gridy = y;//指定位于组件显示区域的顶部的单元格，其中最上边的单元格为 gridy=0。
		c.gridwidth = width;//指定组件显示区域的某一行中的单元格数。
		c.gridheight = height;//指定在组件显示区域的一列中的单元格数。
	}
}
