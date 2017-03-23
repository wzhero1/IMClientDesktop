package nsp.im.client.desktop.grouplist;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import nsp.im.client.desktop.base.ActionMouseAdapter;
import nsp.im.client.desktop.base.ButtonStyleMouseAdapter;
import nsp.im.client.desktop.base.ListView;
import nsp.im.client.desktop.base.SelectableDecorator;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.desktop.utils.Globals;
import nsp.im.client.model.Group;

@SuppressWarnings("serial")
public class GroupsListView extends ListView {
	private HashSet<GroupClickListener> groupListeners;
	private HashSet<GroupCloseListener> closeListeners;
	private GroupItemFactory itemFactory;
	private Group selected;

	public GroupsListView() {
		groupListeners = new HashSet<>();
		closeListeners = new HashSet<>();
	}

	public void start() {
		if (itemFactory == null)
			itemFactory = new DefaultGroupItemFactory();
		Globals.getAccount().getGroupList().getUpdateEvent()
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
		JComponent addBtn = makeAddBtn();
		addBtn.addMouseListener(new ActionMouseAdapter() {
			@Override
			public void actionPerformed(MouseEvent ev) {
				try {
					selected = Globals.getAccount().getGroupList().createGroup(
							Globals.getAccount().getMyInfo().getUsername()
									+ "Group")
							.get();
					doRefresh();
				} catch (Exception e) {

				}
			}
		});
		addComponent(addBtn);
		boolean deselect = selected != null;
		for (final Group g : Globals.getAccount().getGroupList().getGroups()) {
			if (g.identify(selected))
				deselect = false;
			SelectableDecorator comp = itemFactory.makeGroupItem(g);
			comp.addMouseListener(new ActionMouseAdapter() {
				@Override
				public void actionPerformed(MouseEvent e) {
					selected = g;
					for (GroupClickListener l : groupListeners) {
						l.onClickGroup(g);
					}
				}
			});
			addComponent(comp);
			if (g.identify(selected))
				select(comp);
		}
		if (deselect) {
			selected = null;
			for (GroupCloseListener l : closeListeners) {
				l.onClose();
			}
		}
		validate();
	}

	public void addGroupClickListener(GroupClickListener listener) {
		groupListeners.add(listener);
	}

	public void removeGroupClickListener(GroupClickListener listener) {
		groupListeners.remove(listener);
	}
	
	public void addGroupCloseListener(GroupCloseListener listener) {
		closeListeners.add(listener);
	}
	
	public void removeGroupCloseListener(GroupCloseListener listener) {
		closeListeners.remove(listener);
	}

	public interface GroupClickListener {
		void onClickGroup(Group group);
	}
	
	public interface GroupCloseListener {
		void onClose();
	}

	private JComponent makeAddBtn() {
		final JLabel lbl = new JLabel("ÐÂ½¨Èº", JLabel.CENTER);
		lbl.setBorder(
				BorderFactory.createLineBorder(new Color(0, 0, 0, 0), 10));
		lbl.setFont(lbl.getFont().deriveFont(16f));
		ButtonStyleMouseAdapter.attach(lbl, new ButtonStyleMouseAdapter() {
			@Override
			public void onRestore() {
				lbl.setForeground(StyleConsts.font_back);
			}

			@Override
			public void onHover() {
				lbl.setForeground(StyleConsts.btn_sel_back);
			}

			@Override
			public void onAct() {
				lbl.setForeground(StyleConsts.btn_act_back);
			}
		});
		lbl.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
				StyleConsts.border));
		// lbl.addMouseListener(new ForwardMouseAdapter());
		return lbl;
	}

}
