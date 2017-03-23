package nsp.im.client.desktop.contactlist;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import nsp.im.client.desktop.base.ScrollDecorator;
import nsp.im.client.desktop.contactlist.ContactsListView.ContactClickListener;

@SuppressWarnings("serial")
public class ContactsPanel extends JPanel {
	private ContactsListView listview;
	
	public ContactsPanel() {
		layComponents();
	}
	
	private void layComponents() {
		setLayout(new BorderLayout());
		listview = new ContactsListView();
		ScrollDecorator sd = new ScrollDecorator(listview);
		add(ScrollDecorator.makeTranslucentBarDecorator(sd), BorderLayout.CENTER);
	}
	
	public void start() {
		listview.start();
	}
	
	public void addContactClickListener(ContactClickListener listener) {
		listview.addContactClickListener(listener);
	}
	
	public void removeContactClickListener(ContactClickListener listener) {
		listview.removeContactClickListener(listener);
	}
}
