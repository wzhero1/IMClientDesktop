package nsp.im.client.desktop.simplegui;

import java.awt.event.MouseEvent;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import nsp.im.client.desktop.base.ActionMouseAdapter;
import nsp.im.client.desktop.base.ListView;
import nsp.im.client.desktop.base.SelectableDecorator;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.desktop.contactlist.ContactItemFactory;
import nsp.im.client.desktop.utils.NameGenerator;
import nsp.im.client.desktop.utils.NameGrouper;
import nsp.im.client.model.Contact;
import nsp.im.client.model.GroupInvitation;
import nsp.im.client.model.GroupInvitation.Invitee;

@SuppressWarnings("serial")
public class InviteeListView extends ListView {
	private ContactItemFactory itemFactory;
	private GroupInvitation invitation;
	private Invitee selected;

	public InviteeListView(GroupInvitation invitation) {
		this.invitation = invitation;
	}

	public void setContactItemFactory(ContactItemFactory factory) {
		itemFactory = factory;
	}
	
	public void start() {
		refresh();
	}

	private void refresh() {
		removeComponents();
		HashMap<Character, ArrayList<Invitee>> names = groupNames(invitation.getInvitees());
		ArrayList<Character> clist = new ArrayList<Character>(names.keySet());
		Collections.sort(clist, new Comparator<Character>() {
			@Override
			public int compare(Character o1, Character o2) {
				return o1 - o2;
			}
		});
//		SelectableDecorator requests = makeRequests();
//		requests.addMouseListener(new ActionMouseAdapter() {
//			@Override
//			public void actionPerformed(MouseEvent e) {
//				RequestsDialog dialog = new RequestsDialog();
//				dialog.start(server);
//				dialog.setModal(true);
//				dialog.setSize(300, 600);
//				dialog.setLocationRelativeTo(null);
//				dialog.setVisible(true);
//			}
//		});
//		addComponent(requests);
		for (Character c : clist) {
			JLabel clabel = new JLabel(" " + c);
			clabel.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 2));
			clabel.setForeground(StyleConsts.font_back);
			clabel.setBackground(StyleConsts.listview_lbl);
			clabel.setOpaque(true);
			addComponent(clabel);// 20160601； 在联系人左侧面板上添加名字姓氏首字母；
			for (final Invitee contact : names.get(c)) {
				SelectableDecorator comp = itemFactory.makeContactItem(contact);
				comp.addMouseListener(new ActionMouseAdapter() {
					@Override
					public void actionPerformed(MouseEvent e) {
						selected = contact;
						contact.select();
					}
				});
				addComponent(comp);
				if (contact == selected) {
					select(comp);
				}
			}
		}
		validate();
	}

	private HashMap<Character, ArrayList<Invitee>> groupNames(
			Collection<Invitee> contacts) {
		HashMap<Character, ArrayList<Invitee>> res = new HashMap<>();
		final NameGenerator ng = new NameGenerator();
		for (Invitee contact : contacts) {
			String name = ng.getShowedUsername(contact);
			char c = NameGrouper.getGroup(name);
			if (!res.containsKey(c))
				res.put(c, new ArrayList<Invitee>());
			res.get(c).add(contact);
		}
		// final Collator clt = Collator.getInstance();
		final Collator clt = Collator.getInstance(java.util.Locale.CHINA);// 20160601;
		for (Character c : res.keySet()) {
			Collections.sort(res.get(c), new Comparator<Contact>() {
				@Override
				public int compare(Contact c1, Contact c2) {
					String n1 = ng.getShowedUsername(c1);
					String n2 = ng.getShowedUsername(c2);
					return clt.compare(n1, n2);
				}

			});
			// Collections.sort(res.get(c),clt);//20160601;改为该名后出错，联系人页无法正常显示
		}
		return res;
	}

}
