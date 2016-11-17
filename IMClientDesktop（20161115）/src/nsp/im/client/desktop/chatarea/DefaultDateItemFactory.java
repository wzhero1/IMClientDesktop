package nsp.im.client.desktop.chatarea;

import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * 默认日期组件工厂
 */
public class DefaultDateItemFactory implements DateItemFactory {
	@Override
	public JComponent makeDateItem(Date date) {
		String time = new SimpleDateFormat("MM月dd日hh:mm").format(date);
		JLabel label = new JLabel(time);
		label.setFont(new Font("宋体", Font.PLAIN, 12));
		label.setForeground(new Color(0x808080));
		label.setHorizontalAlignment(JLabel.CENTER);
		return label;
	}
}
