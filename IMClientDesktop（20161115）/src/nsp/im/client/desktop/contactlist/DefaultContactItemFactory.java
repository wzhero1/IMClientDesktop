package nsp.im.client.desktop.contactlist;

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
import nsp.im.client.desktop.utils.NameGenerator;
import nsp.im.client.model.Contact;

public class DefaultContactItemFactory implements ContactItemFactory {

	public DefaultContactItemFactory() {
	}

	@Override
	public SelectableDecorator makeContactItem(final Contact contact) {
		final JLabel txtLabel = new JLabel(
				new NameGenerator().getShowedUsername(contact),
				JLabel.LEFT);
		txtLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		txtLabel.setFont(txtLabel.getFont().deriveFont(16f));
		contact.getUpdateEvent().addListener(() -> {
			txtLabel.setText(new NameGenerator()
					.getShowedUsername(contact));
		});
		final JLabel imgLabel = new JLabel();
		imgLabel.setIcon(
				new ImageIcon(ImageUtil.getUserImage(contact, 40)));
		contact.getUpdateEvent().addListener(() -> {
			imgLabel.setIcon(new ImageIcon(
					ImageUtil.getUserImage(contact, 40)));
		});
		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		panel.add(imgLabel, BorderLayout.WEST);
		panel.add(txtLabel, BorderLayout.CENTER);
		TextRoundPopup menu = new TextRoundPopup();
		menu.addItem("É¾³ýÁªÏµÈË", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				contact.removeFromContacts();
			}
		});
		panel.setComponentPopupMenu(menu);
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
