package nsp.im.client.desktop.chatarea;

import java.util.Date;

import javax.swing.JComponent;

/**
 * 日期组件工厂
 */
public interface DateItemFactory {
	/**
	 * 根据日期制造一个组件
	 * @param date 日期
	 * @return 制造的组件
	 */
	JComponent makeDateItem(Date date);
}
