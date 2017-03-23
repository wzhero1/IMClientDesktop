package nsp.im.client.desktop.contactlist;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nsp.im.client.desktop.base.ButtonStyleMouseAdapter;
import nsp.im.client.desktop.base.ListView;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.desktop.utils.Globals;
import nsp.im.client.desktop.utils.ImageUtil;
import nsp.im.client.model.ContactRequest;

@SuppressWarnings("serial")
public class RequestsListView extends ListView {

	public RequestsListView() {
	}

	public void setRequests() {
		removeComponents();
		for (ContactRequest req : Globals.getAccount().getContactRequestList()
				.getRequests()) {
			addComponent(makeReqItem(req));
		}
		validate();
	}

	private JPanel makeReqItem(ContactRequest req) {

		// 显示用户昵称
		String nick = req.getRequester().getNickname();
		if (nick == null || nick.trim().equals(""))
			nick = req.getRequester().getUsername();
		// 显示用户头像
		final JLabel txtLabel = new JLabel(nick, JLabel.LEFT);
		txtLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		final JLabel imgLabel = new JLabel();
		imgLabel.setIcon(
				new ImageIcon(ImageUtil.getUserImage(req.getRequester(), 40)));
		req.getUpdateEvent().addListener(() -> {
			imgLabel.setIcon(new ImageIcon(
					ImageUtil.getUserImage(req.getRequester(), 40)));
		});
		final JLabel accLabel = new JLabel("<html><a href=\"\">接受</a></html>");
		accLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		final JLabel ignLabel = new JLabel("<html><a href=\"\">拒绝</a></html>");
		ignLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		// 将以上组件组合到容器中
		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		panel.add(imgLabel, BorderLayout.WEST);
		panel.add(txtLabel, BorderLayout.CENTER);
		final JPanel subPanel = new JPanel();
		subPanel.setLayout(new BorderLayout());
		subPanel.add(accLabel, BorderLayout.NORTH);
		subPanel.add(ignLabel, BorderLayout.SOUTH);
		subPanel.setOpaque(false);
		panel.add(subPanel, BorderLayout.EAST);
		// 设置联系人添加动作
		accLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent ev) {
				try {
					req.reply(true).get();
				} catch (Exception e) {
				}
				setRequests();
			}
		});
		// 设置联系人添加动作
		ignLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent ev) {
				try {
					req.reply(false).get();
				} catch (Exception e) {
				}
				setRequests();
			}
		});
		// 设置鼠标交互样式
		ButtonStyleMouseAdapter.attach(panel, new ButtonStyleMouseAdapter() {
			@Override
			public void onRestore() {
				panel.setBackground(StyleConsts.main_back);
			}

			@Override
			public void onHover() {
				panel.setBackground(StyleConsts.listview_back);
			}

			@Override
			public void onAct() {
				panel.setBackground(StyleConsts.listview_hov);
			}
		});

		return panel;
	}
}
