package nsp.im.client.desktop.grouplist;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nsp.im.client.desktop.base.SelectableDecorator;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.desktop.base.TextRoundPopup;
import nsp.im.client.desktop.utils.ImageUtil;
import nsp.im.client.model.Group;

/**
 * 默认的群组件工厂
 */
public class DefaultGroupItemFactory implements GroupItemFactory {
	
	/**
	 * 构造一个群组件工厂
	 * @param server IM服务器对象
	 */
	public DefaultGroupItemFactory() {
	}

	@Override
	public SelectableDecorator makeGroupItem(final Group group) {
		//群名称
		JLabel txtLabel = new JLabel(group.getGroupName(), JLabel.LEFT);
		txtLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		txtLabel.setFont(txtLabel.getFont().deriveFont(16f));
		//群图片
		JLabel imgLabel = new JLabel();
		imgLabel.setIcon(new ImageIcon(ImageUtil.getGroupImage(group, 40)));
		//组件容器
		group.getUpdateEvent().addListener(() -> {
			txtLabel.setText(group.getGroupName());
			imgLabel.setIcon(new ImageIcon(ImageUtil.getGroupImage(group, 40)));
		});
		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		panel.add(imgLabel, BorderLayout.WEST);
		panel.add(txtLabel, BorderLayout.CENTER);
		//设置可用操作
		TextRoundPopup menu = new TextRoundPopup();
		menu.addItem("退出群", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				group.leave();
			}
		});
		panel.setComponentPopupMenu(menu);
		//设置鼠标交互样式
		@SuppressWarnings("serial")
		SelectableDecorator sel = new SelectableDecorator(panel) {
			@Override
			public void onRestore() {
				panel.setBackground(StyleConsts.listview_back);
			}
			
			@Override
			public void onHover() {
				panel.setBackground(StyleConsts.listview_hov);
			}
			
			@Override
			public void onSelect() {
				panel.setBackground(StyleConsts.listview_sel);
			}
		};
		return sel;
	}
}
