package nsp.im.client.desktop.group;

import static nsp.im.client.desktop.frames.MainFrame.switchChat;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import nsp.im.client.desktop.base.ButtonStyleMouseAdapter;
import nsp.im.client.desktop.base.RoundPanel;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.desktop.base.TextRoundPopup;
import nsp.im.client.desktop.simplegui.ContactSelDialog;
import nsp.im.client.desktop.utils.Globals;
import nsp.im.client.desktop.utils.ImageUtil;
import nsp.im.client.model.Contact;
import nsp.im.client.model.Group;
import nsp.im.client.model.Member;
import nsp.im.client.model.Member.Role;
import nsp.im.client.model.User;

@SuppressWarnings("serial")
public class MembersList extends RoundPanel {
	private static int COLS = 6;
	private HashSet<MemberClickListener> listeners;
	private HashMap<User, String> g_groupUserNikenameMap;
	private HashSet<User> g_groupUserSet;
	private Iterator<User> g_UserSetInterator;
	private HashMap<String, JLabel> g_groupNikenameLabelMap;
	private Set<String> g_groupNikenameSet;
	private Iterator<String> g_NikenameSetInterator;
	private  String[] g_groupMembers;
	private JLabel addLabel;//20160519;
//	private JLabel label;//20160519;
	private Group g_group;//20160519;
	public static Member tempSwitchMember = null;//保存组中右键处理的Member

	public MembersList() {
		listeners = new HashSet<>();
		setLayout(new GridLayout(0, COLS, 10, 10));
		setBackground(StyleConsts.main_back);
	}

	public void start() {
	}

	public void setGroup(final Group group) {
		g_group = group;
		updateGroup(group);
		group.getUpdateEvent().addListener(() -> {
			updateGroup(group);
		});
		/*
//		removeAll();//20160519;
		addLabel = new JLabel();//20160519;
		addLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		addLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
		addLabel.setHorizontalAlignment(JLabel.CENTER);
		ButtonStyleMouseAdapter.attach(addLabel, new ButtonStyleMouseAdapter() {
			@Override
			public void onRestore() {
				addLabel.setIcon(new ImageIcon(ImageUtil.getImage("res/add_g.png", 60)));
			}

			@Override
			public void onHover() {
				addLabel.setIcon(new ImageIcon(ImageUtil.getImage("res/add_c.png", 60)));
			}

			@Override
			public void onAct() {
				addLabel.setIcon(new ImageIcon(ImageUtil.getImage("res/add_c.png", 60)));
			}
		});
//		add(addLabel);//20160519;
		addLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					ContactSelDialog dialog = new ContactSelDialog();
					dialog.start(server);
					Set<Contact> contacts = dialog.getSelection();
					for (Contact contact : contacts) {
						g_group.inviteMember(contact.getUser());
					}
				}
			}
		});
		*/
		
/*		for (final Member m : g_group.getMembers()) {
			JLabel label = new JLabel();
			label.setHorizontalTextPosition(SwingConstants.CENTER);
			label.setVerticalTextPosition(SwingConstants.BOTTOM);
			label.setHorizontalAlignment(JLabel.CENTER);
			String nick = m.getNicknameInGroup();// 获取群内昵称；20160519；
			if (nick.trim().equals("")) {// 若群内昵称为空，则获取用户昵称作为群内昵称；20160519；
				nick = m.getUser().getNickname();
			}
			if (nick.trim().equals("")) {// 若用户昵称为空，则获取用户名作为群内昵称；20160519；
				nick = m.getUser().getUsername();
			}
			label.setText(nick);
			label.setIcon(new ImageIcon(ImageUtil.getUserImage(m.getUser(), 60)));
			TextRoundPopup menu = new TextRoundPopup();
			menu.addItem("删除成员", new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					server.getGroupManager().kickUserFromGroup(g_group, m);
				}
			});
			label.setComponentPopupMenu(menu);
			label.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					for (MemberClickListener l : listeners) {
						l.onClickMember(g_group, m);
					}
				}
			});
			add(label);
		}*/
		
/*		int num = 0;
		for (final Member m : g_group.getMembers()) {
			num++;
		}
		g_groupMembers = new String[num];
		int i = 0;
		for (final Member m : g_group.getMembers()) {
			String nick = m.getNicknameInGroup();// 获取群内昵称；20160519；
			if (nick.trim().equals("")) {// 若群内昵称为空，则获取用户昵称作为群内昵称；20160519；
				nick = m.getUser().getNickname();
			}
			if (nick.trim().equals("")) {// 若用户昵称为空，则获取用户名作为群内昵称；20160519；
				nick = m.getUser().getUsername();
			}
			g_groupMembers[i++] = nick;
		}
		Arrays.sort(g_groupMembers);//对群组中成员昵称进行排序；20160519；
		int j = 0;	
		for (final Member m : g_group.getMembers()) {
				JLabel label = new JLabel();
				label.setHorizontalTextPosition(SwingConstants.CENTER);
				label.setVerticalTextPosition(SwingConstants.BOTTOM);
				label.setHorizontalAlignment(JLabel.CENTER);
				label.setText(g_groupMembers[j++]);
				label.setIcon(new ImageIcon(ImageUtil.getUserImage(m.getUser(), 60)));
				TextRoundPopup menu = new TextRoundPopup();
				menu.addItem("删除成员", new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						server.getGroupManager().kickUserFromGroup(g_group, m);
					}
				});
				label.setComponentPopupMenu(menu);
				label.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						for (MemberClickListener l : listeners) {
							l.onClickMember(g_group, m);
						}
					}
				});
				add(label);
		}*/

		/*
		g_groupUserNikenameMap = new HashMap<User,String>();  //群组中用户和昵称组成的Map;20160519;
		g_groupUserSet = new HashSet<User>(); //用户-昵称Map中的keySet，即用户Set;20160519;
		
		g_groupNikenameLabelMap = new HashMap<String,JLabel>(); //群组中昵称和标签组成的Map;20160519;
		g_groupNikenameSet = new HashSet<String>(); //昵称-标签Map中的keySet，即昵称Set;20160519;
		
		for (final Member m : g_group.getMembers()) {
			JLabel label = new JLabel();
			label.setHorizontalTextPosition(SwingConstants.CENTER);
			label.setVerticalTextPosition(SwingConstants.BOTTOM);
			label.setHorizontalAlignment(JLabel.CENTER);
			String nick = m.getNicknameInGroup();// 获取群内昵称；20160519；
			if (nick.trim().equals("")) {// 若群内昵称为空，则获取用户昵称作为群内昵称；20160519；
				nick = m.getUser().getNickname();
			}
			if (nick.trim().equals("")) {// 若用户昵称为空，则获取用户名作为群内昵称；20160519；
				nick = m.getUser().getUsername();
			}
			g_groupUserNikenameMap.put(m.getUser(), nick);//把群中用户名和昵称加入该Map中；20160519；
			label.setText(nick);
			label.setIcon(new ImageIcon(ImageUtil.getUserImage(m.getUser(), 60)));
			TextRoundPopup menu = new TextRoundPopup();
			menu.addItem("删除成员", new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					g_group.kickMember(m);
					g_groupNikenameLabelMap.remove(g_groupUserNikenameMap.get(m));
					drawLabels();
					g_groupUserNikenameMap.remove(m);
				}
			});
			label.setComponentPopupMenu(menu);
			label.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					for (MemberClickListener l : listeners) {
						l.onClickMember(g_group, m);
					}
				}
			});
			g_groupNikenameLabelMap.put(g_groupUserNikenameMap.get(m), label);//把群中用户昵称和标签加入该Map中；20160519；
		}
		drawLabels();
		*/
	}

	private void drawLabels() {//重排群组中用户标签；20160519；
//		g_groupUserSet = (HashSet<User>) g_groupUserNikenameMap.keySet();//群组用户Set;20160519;
//		g_UserSetInterator = g_groupUserSet.iterator();//用户Set迭代器;20160519;
		
		g_groupNikenameSet = g_groupNikenameLabelMap.keySet();//群组昵称Set;20160519;
		g_NikenameSetInterator = g_groupNikenameSet.iterator();//昵称Set迭代器;20160519;
		
		removeAll();
		add(addLabel);
		
		g_groupMembers = new String[g_group.getMembers().size()];
		int i = 0;
		while (g_NikenameSetInterator.hasNext()) {
			String nickName = g_NikenameSetInterator.next();
			g_groupMembers[i++] = nickName;
		}
		Arrays.sort(g_groupMembers,String.CASE_INSENSITIVE_ORDER);//对群组中成员昵称进行排序；20160519；
		for (int j = 0;j<i; j++) {
			add(g_groupNikenameLabelMap.get(g_groupMembers[j]));
		}
	}

	public void addMemberClickListener(MemberClickListener l) {
		listeners.add(l);
	}

	public void removeMemberClickListener(MemberClickListener l) {
		listeners.remove(l);
	}

	public interface MemberClickListener {
		void onClickMember(Group g_group, Member member);

		void onAddMember();
	}
	
	private synchronized void updateGroup(Group group) {
		removeAll();//20160519;
		addLabel = new JLabel();//20160519;
		addLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		addLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
		addLabel.setHorizontalAlignment(JLabel.CENTER);
		ButtonStyleMouseAdapter.attach(addLabel, new ButtonStyleMouseAdapter() {
			@Override
			public void onRestore() {
				addLabel.setIcon(new ImageIcon(ImageUtil.getImage("res/add_g.png", 60)));
			}

			@Override
			public void onHover() {
				addLabel.setIcon(new ImageIcon(ImageUtil.getImage("res/add_c.png", 60)));
			}

			@Override
			public void onAct() {
				addLabel.setIcon(new ImageIcon(ImageUtil.getImage("res/add_c.png", 60)));
			}
		});
		add(addLabel);//20160519;
		addLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					ContactSelDialog dialog = new ContactSelDialog(group.makeInvitation());
					dialog.start();
				}
			}
		});

		LinkedList<Member> sortedMembers = new LinkedList<>();
		Member owner = null;
		for (Member m : group.getMembers()) {
			if (m.getRole() == Member.Role.OWNER)
				owner = m;
			else
				sortedMembers.add(m);
		}
		sortedMembers.sort((ma, mb) -> {
			return String.CASE_INSENSITIVE_ORDER.compare(getNickname(ma), getNickname(mb));
		});
		if (owner != null)
			sortedMembers.addFirst(owner);
		
		boolean isOwner = group.getMyMember().getRole() == Role.OWNER;
		for (final Member m : sortedMembers) {
			JLabel label = new JLabel();
			label.setHorizontalTextPosition(SwingConstants.CENTER);
			label.setVerticalTextPosition(SwingConstants.BOTTOM);
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setText(getNickname(m));
			label.setIcon(new ImageIcon(ImageUtil.getUserImage(m, 60)));
			if (!m.isMe()) {
				TextRoundPopup menu = new TextRoundPopup();
				if(isOwner){
                    menu.addItem(" 删除成员 ", new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
						m.kickFromGroup();
					}
                    });
				}
                //20160918:发送消息监听设置
				JLabel sendLbl = new JLabel(" 发送消息 ");
				menu.add(sendLbl);
				sendLbl.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						menu.setVisible(false);
						tempSwitchMember = m;
					}
				});
				sendLbl.addMouseListener(switchChat);
				label.setComponentPopupMenu(menu);
			}
			label.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					for (MemberClickListener l : listeners) {
						l.onClickMember(g_group, m);
					}
				}
			});
			add(label);
		}
		revalidate();
		repaint();
	}
	
	private String getNickname(Member m) {
		String nick = m.getNickname();// 获取群内昵称；20160519；
		if (nick.trim().equals("")) {// 若群内昵称为空，则获取用户昵称作为群内昵称；20160519；
			nick = m.getNickname();
		}
		if (nick.trim().equals("")) {// 若用户昵称为空，则获取用户名作为群内昵称；20160519；
			nick = m.getUsername();
		}
		return nick;
	}
}
