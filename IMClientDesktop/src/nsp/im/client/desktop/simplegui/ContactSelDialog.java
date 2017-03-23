package nsp.im.client.desktop.simplegui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nsp.im.client.desktop.base.RoundButton;
import nsp.im.client.desktop.base.RoundDialog;
import nsp.im.client.desktop.base.RoundPanel;
import nsp.im.client.desktop.base.ScrollDecorator;
import nsp.im.client.desktop.base.SelectableDecorator;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.desktop.contactlist.ContactItemFactory;
import nsp.im.client.desktop.utils.ImageUtil;
import nsp.im.client.desktop.utils.NameGenerator;
import nsp.im.client.model.Contact;
import nsp.im.client.model.GroupInvitation;

@SuppressWarnings("serial")
public class ContactSelDialog extends RoundDialog {
	private GroupInvitation invitation;
	private InviteeListView listview;
	private RoundButton ok;
	private RoundButton cancel;

	public ContactSelDialog(GroupInvitation invitation) {
		this.invitation = invitation;
		layComponents();
		setListeners();
	}
	
	private void layComponents() {
		setTitle(new JLabel("选择联系人"));
		listview = new InviteeListView(invitation);
		ScrollDecorator sd = new ScrollDecorator(listview);
		JPanel content = new RoundPanel();
		content.setLayout(new BorderLayout());
		content.add(ScrollDecorator.makeTranslucentBarDecorator(sd),
				BorderLayout.CENTER);
		content.setBackground(StyleConsts.main_back);
		JPanel btns = new RoundPanel();
		btns.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
		btns.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		ok = RoundButton.createNormalButton();
		ok.setText("  确定  ");
		cancel = RoundButton.createNormalButton();
		cancel.setText("  取消  ");
		btns.add(ok);
		btns.add(cancel);
		btns.setBackground(StyleConsts.main_back);
		content.add(btns, BorderLayout.SOUTH);
		setContent(content);
	}
	
	private void setListeners() {
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				invitation.commit();
				dispose();
			}
		});
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
	
	public void start() {
		listview.setContactItemFactory(new SelContactItemFactory());
		listview.start();
		setModal(true);
		setSize(300, 600);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
	private class SelContactItemFactory implements ContactItemFactory {

		/**
		 * 添加好友进群组的样式好友单个列表的样式
		 */
		@Override
		public SelectableDecorator makeContactItem(final Contact contact) {
			String showed = "";
			String nick = new NameGenerator()
					.getShowedUsername(contact);
			showed += nick;
			JLabel txtLabel = new JLabel(showed, JLabel.LEFT);
			txtLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			txtLabel.setFont(txtLabel.getFont().deriveFont(16f));
			final JLabel imgLabel = new JLabel();
			imgLabel.setIcon(new ImageIcon(
					ImageUtil.getUserImage(contact, 40)));
			final JLabel selLabel = new JLabel();
			final JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
			panel.add(imgLabel, BorderLayout.WEST);
			panel.add(txtLabel, BorderLayout.CENTER);
			//func.apply判断是否好友是否已在群列表中
			//if (func.apply(contact)) {
				panel.add(selLabel, BorderLayout.EAST);
			//}
			selLabel.setIcon(
					new ImageIcon(ImageUtil.getImage("res/sel_f.png", 20)));
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
			sel.addMouseListener(new MouseAdapter() {
				private boolean seled = false;
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						seled = !seled;
						String resName = seled ? "res/sel_t.png"
								: "res/sel_f.png";
						selLabel.setIcon(
								new ImageIcon(ImageUtil.getImage(resName, 20)));
					}
				}
			});
			selLabel.setIcon(
					new ImageIcon(ImageUtil.getImage("res/sel_f.png", 20)));
			return sel;
		}
		
	}
}
