package nsp.im.client.desktop.usersearch;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nsp.im.client.desktop.base.HintField;
import nsp.im.client.desktop.base.RoundButton;
import nsp.im.client.desktop.base.RoundDialog;
import nsp.im.client.desktop.base.RoundField;
import nsp.im.client.desktop.base.RoundPanel;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.desktop.utils.ImageUtil;
import nsp.im.client.model.User;

@SuppressWarnings("serial")
public class RequestDialog extends RoundDialog {
	private User user;
	private RoundButton ok;
	private RoundButton cancel;
	private RoundField msgField;
	private String valMsg;

	public RequestDialog(User user) {
		this.user = user;
		layComponents();
		setListeners();
	}

	private void layComponents() {
		setTitle(new JLabel("添加联系人：" + user.getUsername()));
		JPanel content = new RoundPanel();
		content.setFocusable(true);
		content.setLayout(new BorderLayout());
		JPanel msg = new RoundPanel();
		msg.setLayout(new FlowLayout(FlowLayout.CENTER));
		msg.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		msg.add(
				new JLabel(new ImageIcon(
						ImageUtil.getUserImage(user, 40))));
		msgField = HintField.createNormalField("验证消息");
		msg.add(msgField);
		JPanel btns = new RoundPanel();
		btns.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
		btns.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		ok = RoundButton.createNormalButton();
		ok.setText("  添加  ");
		cancel = RoundButton.createDangerousButton();
		cancel.setText("  取消  ");
		btns.add(ok);
		btns.add(cancel);
		btns.setBackground(StyleConsts.main_back);
		content.add(msg, BorderLayout.CENTER);
		content.add(btns, BorderLayout.SOUTH);
		setContent(content);
	}

	private void setListeners() {
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				valMsg = msgField.getText();
				dispose();
			}
		});
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				valMsg = null;
				dispose();
			}
		});
	}
	
	public String getValidationMsg() {
		setLocationRelativeTo(null);
		pack();
		setModal(true);
		setVisible(true);
		return valMsg;
	}
}
