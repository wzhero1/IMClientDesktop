package nsp.im.client.desktop.contactlist;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import nsp.im.client.desktop.base.ActionMouseAdapter;
import nsp.im.client.desktop.base.ListView;
import nsp.im.client.desktop.base.SelectableDecorator;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.desktop.utils.Globals;
import nsp.im.client.desktop.utils.NameGenerator;
import nsp.im.client.desktop.utils.NameGrouper;
import nsp.im.client.model.Contact;

@SuppressWarnings("serial")
public class ContactsListView extends ListView {
	private HashSet<ContactClickListener> contactListeners;
	private HashSet<ContactCloseListener> closeListeners;
	private ContactItemFactory itemFactory;
	private Contact selected;

	public ContactsListView() {
		contactListeners = new HashSet<>();
		closeListeners = new HashSet<>();
	}

	public void setContactItemFactory(ContactItemFactory factory) {
		itemFactory = factory;
	}

	public void start() {
		if (itemFactory == null)
			itemFactory = new DefaultContactItemFactory();
		Globals.getAccount().getContactList().getUpdateEvent()
				.addListener(() -> {
					doRefresh();
				});
		doRefresh();
	}

	private void doRefresh() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				refresh();
			}
		});
	}

	private void refresh() {
		removeComponents();

		//names的key为首字母，value为首字母下的联系人
		HashMap<Character, ArrayList<Contact>> names = groupNames(
				Globals.getAccount().getContactList().getContacts());
		//clist是好友的字母列表
		ArrayList<Character> clist = new ArrayList<>(names.keySet());
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
		boolean deselect = selected != null;
		for (Character c : clist) {
			JLabel clabel = new JLabel(" " + c);
			clabel.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 2));
			clabel.setForeground(StyleConsts.font_back);
			clabel.setBackground(StyleConsts.listview_lbl);
			clabel.setOpaque(true);
			for (final Contact contact : names.get(c)){
					addComponent(clabel);
			}
		//	addComponent(clabel);
			// 20160601； 在联系人左侧面板上添加名字姓氏首字母；
			for (final Contact contact : names.get(c)) {
				if (contact.identify(selected))
					deselect = false;
					SelectableDecorator comp = itemFactory.makeContactItem(contact);
				comp.addMouseListener(new ActionMouseAdapter() {
					@Override
					public void actionPerformed(MouseEvent e) {
						selected = contact;
						for (ContactClickListener l : contactListeners) {
							l.onClickContact(contact);
						}
					}
				});
				addComponent(comp);
				if (contact.identify(selected)) {
					select(comp);
				}
			}
		}
		if (deselect) {
			selected = null;
			for (ContactCloseListener l : closeListeners)
				l.onClose();
		}
		validate();
	}

	private HashMap<Character, ArrayList<Contact>> groupNames(
			Collection<Contact> contacts) {
		HashMap<Character, ArrayList<Contact>> res = new HashMap<>();
		final NameGenerator ng = new NameGenerator();
		for (Contact contact : contacts) {
			String name = ng.getShowedUsername(contact);
			char c = NameGrouper.getGroup(name);
			if (!res.containsKey(c))
				res.put(c, new ArrayList<Contact>());
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

	public void addContactClickListener(ContactClickListener listener) {
		contactListeners.add(listener);
	}

	public void removeContactClickListener(ContactClickListener listener) {
		contactListeners.remove(listener);
	}

	public void addContactCloseListener(ContactCloseListener listener) {
		closeListeners.add(listener);
	}

	public void removeContactCloseListener(ContactCloseListener listener) {
		closeListeners.remove(listener);
	}

	public interface ContactClickListener {
		void onClickContact(Contact contact);
	}
	
	public interface ContactCloseListener {
		void onClose();
	}
	
	private SelectableDecorator makeRequests() {
		final JLabel lbl = new JLabel("新朋友", JLabel.CENTER);
		lbl.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0), 10));
		lbl.setFont(lbl.getFont().deriveFont(16f));
		SelectableDecorator p = new SelectableDecorator(lbl) {
			@Override
			public void onRestore() {
				lbl.setForeground(StyleConsts.font_back);
			}
			
			@Override
			public void onHover() {
				lbl.setForeground(StyleConsts.btn_sel_back);
			}
			
			@Override
			public void onSelect() {
				lbl.setForeground(StyleConsts.btn_act_back);
			}
		};
		p.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, StyleConsts.border));
		return p;
	}

}
