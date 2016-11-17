package nsp.im.client.desktop.usersearch;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nsp.im.client.desktop.base.ButtonStyleMouseAdapter;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.desktop.utils.ImageUtil;
import nsp.im.client.model.User;

/**
 * Ĭ�ϵ��û��������
 */
public class DefaultUserItemFactory implements UserItemFactory {

	/**
	 * ����һ������
	 * 
	 * @param server IMServer����
	 */
	public DefaultUserItemFactory() {
	}

	@Override
	public JComponent makeUserItem(final User user) {
		// ��ʾ�û�ͷ��
		final JLabel txtLabel = new JLabel(getShowedInfo(user, "��ӵ���ϵ��", true), JLabel.LEFT);
		txtLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		final JLabel imgLabel = new JLabel();
		imgLabel.setIcon(new ImageIcon(ImageUtil.getUserImage(user, 40)));
		user.getUpdateEvent().addListener(() -> {
			txtLabel.setText(getShowedInfo(user, "��ӵ���ϵ��", true));
			imgLabel.setIcon(new ImageIcon(ImageUtil.getUserImage(user, 40)));
		});
		// �����������ϵ�������
		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		panel.add(imgLabel, BorderLayout.WEST);
		panel.add(txtLabel, BorderLayout.CENTER);
		// ������ϵ����Ӷ���
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent ev) {
				String nick = user.getNickname();
				if (nick == null || nick.trim().equals(""))
					nick = user.getUsername();
				try {
					RequestDialog dialog = new RequestDialog(user);
					String msg = dialog.getValidationMsg();
					if (msg == null)
						return;
					user.addToContacts(msg);
					txtLabel.setText(getShowedInfo(user, "�ȴ��Է���Ӧ", false));
					panel.removeMouseListener(this);
				} catch (Exception e) {
					if (!user.isContact())
						txtLabel.setText(getShowedInfo(user, "���ʧ�ܣ�����", true));
					else
						txtLabel.setText(getShowedInfo(user, "������ϵ����", false));
				}
			}
		});
		// ������꽻����ʽ
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
	
	private String getShowedInfo(User user, String msg, boolean href) {
		String showed = "<html>" + user.getUsername() + "<br/>";
		if (href)
			showed += "<a href=\"\">";
		showed += msg;
		if (href)
			showed += "</a>";
		showed += "</html>";
		return showed;
	}

}
