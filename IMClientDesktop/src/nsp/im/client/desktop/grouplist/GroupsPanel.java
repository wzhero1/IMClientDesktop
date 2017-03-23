package nsp.im.client.desktop.grouplist;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import nsp.im.client.desktop.base.ScrollDecorator;
import nsp.im.client.desktop.grouplist.GroupsListView.GroupClickListener;

@SuppressWarnings("serial")
public class GroupsPanel extends JPanel {
	private GroupsListView listview;
	
	public GroupsPanel() {
		layComponents();
	}
	
	private void layComponents() {
		setLayout(new BorderLayout());
		listview = new GroupsListView();
		ScrollDecorator sd = new ScrollDecorator(listview);
		add(ScrollDecorator.makeTranslucentBarDecorator(sd), BorderLayout.CENTER);
	}
	
	public void start() {
		listview.start();
	}
	
	public void addGroupClickListener(GroupClickListener listener) {
		listview.addGroupClickListener(listener);
	}
	
	public void removeGroupClickListener(GroupClickListener listener) {
		listview.removeGroupClickListener(listener);
	}
}
