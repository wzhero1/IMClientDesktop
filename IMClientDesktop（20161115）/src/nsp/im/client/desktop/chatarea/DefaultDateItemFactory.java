package nsp.im.client.desktop.chatarea;

import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * Ĭ�������������
 */
public class DefaultDateItemFactory implements DateItemFactory {
	@Override
	public JComponent makeDateItem(Date date) {
		String time = new SimpleDateFormat("MM��dd��hh:mm").format(date);
		JLabel label = new JLabel(time);
		label.setFont(new Font("����", Font.PLAIN, 12));
		label.setForeground(new Color(0x808080));
		label.setHorizontalAlignment(JLabel.CENTER);
		return label;
	}
}
