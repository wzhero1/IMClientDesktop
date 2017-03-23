package nsp.im.client.desktop.group;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

import nsp.im.client.desktop.base.HintField;
import nsp.im.client.desktop.base.RoundButton;
import nsp.im.client.desktop.base.RoundPanel;
import nsp.im.client.desktop.base.ScrollDecorator;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.model.Group;

@SuppressWarnings("serial")
public class GroupPanel extends RoundPanel {
	private MembersList membersList;
	private JLabel nameLabel;
	private JLabel nickLabel;
	private HintField nameField;
	private HintField nickField;
	private JButton chatBtn;
	private HashSet<GroupChatListener> listeners;
   // public static HashSet<ChatListener> chatListeners;
	private Group currentGroup;
	private RoundPanel labelsPanel;

	public GroupPanel() {
		setBackground(StyleConsts.main_back);
		listeners = new HashSet<>();
		layComponents();
	}

	private void layComponents() {

		setLayout(new BorderLayout());

		membersList = new MembersList() {
			@Override
			public void paint(Graphics g) {
				labelsPanel.repaint();
				super.paint(g);
			}
		};
		ScrollDecorator dec = new ScrollDecorator(membersList);
		RoundPanel listPanel = new RoundPanel();
		listPanel.setLayout(new BorderLayout());
		listPanel.add(ScrollDecorator.makeTranslucentBarDecorator(dec),
				BorderLayout.CENTER);
		listPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
				StyleConsts.border));
		add(listPanel, BorderLayout.CENTER);

		labelsPanel = new RoundPanel();
		labelsPanel.setLayout(new GridBagLayout());
		labelsPanel.setBackground(StyleConsts.main_back);
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 5, 10, 5);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;

		nameLabel = new JLabel("群名：", JLabel.TRAILING);
		nameLabel.setFont(nameLabel.getFont().deriveFont(15f));
		c.insets = new Insets(5, 5, 5, 5);
		c.gridwidth = 1;
		c.gridy = 1;
		labelsPanel.add(nameLabel, c);

		nickLabel = new JLabel("群内昵称：", JLabel.TRAILING);
		nickLabel.setFont(nickLabel.getFont().deriveFont(15f));
		c.gridy = 2;
		labelsPanel.add(nickLabel, c);

		nameField = HintField.createNormalField("未设置");
		nameField.setFont(nameLabel.getFont().deriveFont(15f));
		nameField.setColumns(15);
		c.gridx = 1;
		c.gridy = 1;
		labelsPanel.add(nameField, c);

		nickField = HintField.createNormalField("未设置");
		nickField.setFont(nameLabel.getFont().deriveFont(15f));
		nickField.setColumns(15);
		c.gridy = 2;
		labelsPanel.add(nickField, c);

		chatBtn = RoundButton.createNormalButton();
		chatBtn.setText("进入聊天");// ("发消息");20160510;
		c.insets = new Insets(10, 5, 10, 5);
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		c.ipady = 5;
		labelsPanel.add(chatBtn, c);

		add(labelsPanel, BorderLayout.SOUTH);

		setFocusable(true);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				requestFocusInWindow();
			}
		});
	}

	public void start() {
		membersList.start();
		nameField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String r = nameField.getText();
				if (!r.equals("")) {
					currentGroup.changeMyMemberName(r);
				}
			}
		});
		nickField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String r = nickField.getText();
				if (r.equals("")) {
					r = "";
				}
				currentGroup.changeMyMemberName(r);
			}
		});
		chatBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (GroupChatListener l : listeners)
					l.onGroupChat(currentGroup);
			}
		});

	}

	public void setGroup(Group group) {
		currentGroup = group;
		membersList.setGroup(group);
		nameField.setText(group.getGroupName());
		nickField.setText(group.getMyMember().getMemberName());
	}

	@Override
	public void repaint() {
		// TODO Auto-generated method stub
		super.repaint();
	}

	public void addGroupChatListener(GroupChatListener l) {
		listeners.add(l);
	}

	public void removeGroupChatListener(GroupChatListener l) {
		listeners.remove(l);
	}

//	public void addChatListener(ChatListener l) {
//		chatListeners.add(l);
//	}
//
//	public void removeChatListener(ChatListener l) {
//		chatListeners.remove(l);
//	}
	public interface GroupChatListener {
		void onGroupChat(Group group);
	}
}
