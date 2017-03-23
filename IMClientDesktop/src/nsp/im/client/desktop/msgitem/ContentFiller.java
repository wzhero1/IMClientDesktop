package nsp.im.client.desktop.msgitem;

import javax.swing.JPanel;

import nsp.im.client.model.Chat;
import nsp.im.client.model.msg.UserMessage;

/**
 * 根据消息内容对容器进行填充
 */
public interface ContentFiller {
	/**
	 * 对填充器进行设置
	 * @param server IM服务器对象
	 * @param conversation 会话
	 */
	void setup(Chat conversation);
	/**
	 * 填充消息内容
	 * @param record 消息记录
	 * @param parent 要填充的容器
	 */
	void fillContent(UserMessage<?> record, JPanel parent);
}
