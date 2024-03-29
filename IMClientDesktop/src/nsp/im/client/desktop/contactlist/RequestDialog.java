package nsp.im.client.desktop.contactlist;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nsp.im.client.desktop.base.RoundButton;
import nsp.im.client.desktop.base.RoundDialog;
import nsp.im.client.desktop.base.RoundPanel;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.desktop.utils.ImageUtil;
import nsp.im.client.model.ContactRequest;

@SuppressWarnings("serial")
public class RequestDialog extends RoundDialog {
	private ContactRequest req;
	private RoundButton ok;
	private RoundButton cancel;
	private boolean sel;

	public RequestDialog(ContactRequest req) {
		this.req = req;
		layComponents();
		setListeners();
	}

	private void layComponents() {
		setTitle(new JLabel("��������" + req.getRequester().getUsername()));
		JPanel content = new RoundPanel();
		content.setLayout(new BorderLayout());
		JPanel msg = new RoundPanel();
		msg.setLayout(new FlowLayout(FlowLayout.CENTER));
		msg.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		msg.add(
				new JLabel(new ImageIcon(
						ImageUtil.getUserImage(req.getRequester(), 40))));
		msg.add(new JLabel(req.getMessage()));
		JPanel btns = new RoundPanel();
		btns.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
		btns.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		ok = RoundButton.createNormalButton();
		ok.setText("  ����  ");
		cancel = RoundButton.createDangerousButton();
		cancel.setText("  �ܾ�  ");
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
				sel = true;
				dispose();
			}
		});
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sel = false;
				dispose();
			}
		});
	}
	
	public boolean getSelection() {
		setLocationRelativeTo(null);
		pack();
		setModal(true);
		setVisible(true);
		return sel;
	}
}
