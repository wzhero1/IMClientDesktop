package nsp.im.client.desktop.frames;

import static nsp.im.client.desktop.group.MembersList.tempSwitchMember;
import static nsp.im.client.desktop.msgitem.TextContentFiller.deleteState;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.FontUIResource;

import com.alee.laf.WebLookAndFeel;

import nsp.common.func.SafeProc0;
import nsp.im.client.desktop.base.ActionMouseAdapter;
import nsp.im.client.desktop.base.ButtonStyleMouseAdapter;
import nsp.im.client.desktop.base.FramePanel;
import nsp.im.client.desktop.base.RoundDialog;
import nsp.im.client.desktop.base.RoundPanel;
import nsp.im.client.desktop.base.ScrollDecorator;
import nsp.im.client.desktop.base.SelectableDecorator;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.desktop.base.TitleBar;
import nsp.im.client.desktop.chatarea.ChatPanel;
import nsp.im.client.desktop.contactlist.AnswerDialog;
import nsp.im.client.desktop.contactlist.ContactsListView;
import nsp.im.client.desktop.contactlist.ContactsListView.ContactClickListener;
import nsp.im.client.desktop.conversationlist.ConversationsListView;
import nsp.im.client.desktop.conversationlist.ConversationsListView.ConversationClickListener;
import nsp.im.client.desktop.conversationlist.DefaultConversationItemFactory;
import nsp.im.client.desktop.group.GroupPanel;
import nsp.im.client.desktop.group.GroupPanel.GroupChatListener;
import nsp.im.client.desktop.grouplist.GroupsListView;
import nsp.im.client.desktop.grouplist.GroupsListView.GroupClickListener;
import nsp.im.client.desktop.simplegui.AccountOptionList;
import nsp.im.client.desktop.simplegui.AccountOptionList.OptionClickListener;
import nsp.im.client.desktop.simplegui.ChangePassPanel;
import nsp.im.client.desktop.simplegui.ContactPanel;
import nsp.im.client.desktop.simplegui.ContactPanel.ChatListener;
import nsp.im.client.desktop.simplegui.GeneralSettingPanel;
import nsp.im.client.desktop.simplegui.MyAccountPanel;
import nsp.im.client.desktop.usersearch.SearchPanel;
import nsp.im.client.desktop.utils.Globals;
import nsp.im.client.desktop.utils.ImageUtil;
import nsp.im.client.desktop.utils.NameGenerator;
import nsp.im.client.model.Chat;
import nsp.im.client.model.Contact;
import nsp.im.client.model.ContactRequest;
import nsp.im.client.model.Group;
import nsp.im.client.model.User;
import nsp.im.client.model.msg.Message;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	// private static int frameWidth = 860;
	// private static int frameHeight = 597;// 600;20160518;
	private static int frameWidth;
	private static int frameHeight;
	private TitleBar titleBar;
	private LogoutListener listener;
	private JLabel avatarLabel;// 20160510;
	private Chat conversation;
	public static MouseListener switchChat;

	// �����������Ӧ���ַ���
	private static String avatar_card = "avatar";
	private static String conversation_card = "conversation";
	private static String contact_card = "contact";
	private static String group_card = "group";
	private static String search_card = "search";
	private static String generalsetting_card = "generalsetting";

	// �������������а�ť
	private JPanel toolBar;
	private SelectableDecorator avatar;
	private SelectableDecorator contactsBtn;
	private SelectableDecorator groupsBtn;
	private SelectableDecorator conversationsBtn;
	private SelectableDecorator searchUserBtn;
	private JLabel logoutBtn;

	// �Ự����
	private ConversationsListView conversationList;
	private ChatPanel chatPanel;
	private String titleTxt;
	// ��ϵ�˽���
	private ContactsListView contactList;
	private ContactPanel contactPanel;
	// Ⱥ����
	private GroupsListView groupList;
	private GroupPanel groupPanel;
	// �˻���Ϣ����
	private AccountOptionList accountOptList;
	private MyAccountPanel p;// 20160510;
	// ͨ����Ϣ����
	private GeneralSettingPanel genSetPanel;// 20160825
	// �û���������
	private SearchPanel searchUserPanel;

	// �����
	private JPanel leftContents;
	private CardLayout leftCards;

	// �����
	private JPanel rightContents;
	private CardLayout rightCards;

	// ��ק��������
	private boolean dragX;
	private boolean dragY;
	private int disX;
	private int disY;
	private User g_user;
	private String g_userName;
	private String g_nickName;
	private TrayIcon g_trayIcon;
	private SystemTray g_systemTray;

	private AtomicBoolean pullingReqs;
	private SafeProc0 reqsListener;

	/**
	 * ����һ��������
	 */
	public MainFrame() {
		try {
			WebLookAndFeel.globalControlFont =
					new FontUIResource(StyleConsts.default_font);
			UIManager.setLookAndFeel(new WebLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		// setTitle("NSP IM");// �������ã�20160513;
		if (DefaultConversationItemFactory.getIsNewMsg() == true)// �����յ���Ϣ��˸
			setIconImage(Toolkit.getDefaultToolkit().createImage(""));// ���ô���ͼ��;
		else
			setIconImage(
					Toolkit.getDefaultToolkit().createImage("res/NSP35.png"));// ���ô���ͼ��;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// 20160517;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {// 20160512;
				if (getState() != Frame.ICONIFIED) {// 20160528;���Գ�ʱ�䲻�øý��棬�᲻�������ʾ�쳣���⣻
					setVisible(true);
				} else {
					setVisible(false);
				}
				if (p != null && p.getImageChanged()) {
					avatarLabel.setIcon(new ImageIcon(ImageUtil.getUserImage(
							Globals.getAccount().getMyInfo(), 24)));// 36;24;
					p.setImageChanged(false);
				}
				if (p != null && p.getNicknameChanged()) {
					// 20160519;�����޸��ǳƺ��ϵͳ���̵ĸ��£�
					g_nickName = g_user.getNickname();
					g_systemTray.remove(g_trayIcon);
					setSystemTray();
					p.setNicknameChanged(false);
				}
			}
		});
		// ����ΪԲ����Ӱ����
		setUndecorated(true);// ���ñ������������޷�������ʾԲ����Ӱ
		setBackground(new Color(0, 0, 0, 0));// ��ֹ�������ƣ������޷�������ʾԲ����Ӱ
		setContentPane(new FramePanel());
		// ���ڽ��ñ������������Ҫ�ֶ���������
		setManualResizable();
		layComponents();
		setListeners();
	}

	private void layComponents() {
		layToolBar();// ���ù�����

		/*
		 * ��������ΪCardLayout�����Ӳ�ͬ�Ŀ�ѡ��� ͨ�������������ť����������Ӧ���ַ����������Ӷ��ҵ�Ӧ����ʾ�Ŀ�ѡ���
		 * ͨ��CardLayout�Ĺ��ܣ�������ɿ�ѡ�����л�
		 */

		// ��ʼ�����п�ѡ������
		conversationList = new ConversationsListView();
		conversationList.setBackground(StyleConsts.listview_back);
		contactList = new ContactsListView();
		groupList = new GroupsListView();
		accountOptList = new AccountOptionList();

		// ���������
		JPanel leftPanel = new RoundPanel();
		leftPanel.setLayout(new BorderLayout());
		leftPanel.setBackground(StyleConsts.listview_back);
		leftPanel.add(new LogoPanel(), BorderLayout.NORTH);// Ƕ��logo��Ŀǰ����ռλ(NSP
															// IM ��ǩ)
		leftCards = new CardLayout();
		leftContents = new RoundPanel();
		leftContents.setLayout(leftCards);
		// ���ø���������Ӧ������ѡ���
		leftContents.add(
				ScrollDecorator.makeTranslucentBarDecorator(
						new ScrollDecorator(conversationList)),
				conversation_card);
		leftContents.add(ScrollDecorator.makeTranslucentBarDecorator(
				new ScrollDecorator(contactList)), contact_card);
		leftContents.add(ScrollDecorator.makeTranslucentBarDecorator(
				new ScrollDecorator(groupList)), group_card);
		leftContents.add(ScrollDecorator.makeTranslucentBarDecorator(
				new ScrollDecorator(accountOptList)), avatar_card);
		leftContents.add(ScrollDecorator.makeTranslucentBarDecorator(
				new ScrollDecorator(new JPanel())), search_card);
		leftPanel.add(leftContents, BorderLayout.CENTER);// leftContentsǶ�뵽leftPanel��
		leftPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1,
				StyleConsts.border));

		// ���������
		JPanel rightPanel = new RoundPanel();
		rightPanel.setLayout(new BorderLayout());
		rightPanel.setBackground(StyleConsts.main_back);
		titleBar = new TitleBar(this);
		rightPanel.add(titleBar, BorderLayout.NORTH);// �滻��ԭ������С������󻯣��رս���
		rightCards = new CardLayout();
		rightContents = new RoundPanel();
		rightContents.setLayout(rightCards);
		// ���ø���������Ӧ���Ҳ��ѡ��壬�󲿷���ʱ��logoռλ;//20160513;
		int size = 100;
		rightContents.add(new LogoPanel(size, StyleConsts.main_back),
				conversation_card);
		rightContents.add(new LogoPanel(size, StyleConsts.main_back),
				contact_card);
		rightContents.add(new LogoPanel(size, StyleConsts.main_back),
				group_card);
		rightContents.add(new LogoPanel(size, StyleConsts.main_back),
				avatar_card);

		searchUserPanel = new SearchPanel();
		genSetPanel = new GeneralSettingPanel();
		rightContents.add(searchUserPanel, search_card);
		rightPanel.add(rightContents, BorderLayout.CENTER);// rightContentsǶ�뵽rightPanel��,λ���Ҳ�������

		// ���������嵽��������
		RoundPanel mainPanel = new RoundPanel();
		mainPanel.setLayout(new BorderLayout());
		leftPanel.setPreferredSize(new Dimension(250, 0));
		mainPanel.add(leftPanel, BorderLayout.WEST);
		mainPanel.add(rightPanel, BorderLayout.CENTER);
		setLayout(new BorderLayout());
		add(mainPanel, BorderLayout.CENTER);
		add(toolBar, BorderLayout.WEST);
	}

	/**
	 * ��ʼ����������
	 *
	 * @param server IM����������
	 */
	public void start() {
		setSystemTray();// 20160519;
		conversationList.start();
		contactList.start();
		groupList.start();
		searchUserPanel.start();
		avatarLabel = (JLabel) avatar.getComponent();
		avatarLabel.setIcon(new ImageIcon(
				ImageUtil.getUserImage(Globals.getAccount().getMyInfo(), 24)));// 36;
		Globals.getAccount().getMyInfo().getUpdateEvent().addListener(() -> {
			avatarLabel.setIcon(new ImageIcon(ImageUtil
					.getUserImage(Globals.getAccount().getMyInfo(), 24)));// 36;
		});

		// 20160919:���䲻ͬ�ֱ���
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
		frameWidth = (int) (0.4479 * (double) screenWidth);
		frameHeight = (int) (0.5528 * (double) screenHeight);
		setSize(frameWidth, frameHeight);
		setResizable(false);
		// ���õ��Ⱥ��Աת���������
		switchChat = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() != MouseEvent.BUTTON1)
					return;
				else {
					if (tempSwitchMember.isContact()) {
						try {
							Chat conv = tempSwitchMember.openChat().get();
							conversationList.selectConversation(conv);
							openChat(conv);
							switchTo(conversationsBtn, conversation_card);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		};
	}

	// ���ù�����
	private void layToolBar() {
		toolBar = new JPanel(new GridBagLayout());
		toolBar.setBackground(StyleConsts.side_back);

		// �˻���Ϣ��ť
		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 0;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(12, 11, 12, 11);// (10, 10, 10, 10);
		final JLabel l_avatar = new JLabel();
		l_avatar.setHorizontalAlignment(JLabel.CENTER);
		l_avatar.setToolTipText("<html><font color = blue>�˻���Ϣ</font></html>");
		avatar = new SelectableDecorator(l_avatar) {
			@Override
			public void onRestore() {
				l_avatar.setBorder(BorderFactory
						.createLineBorder(l_avatar.getForeground(), 2, true));// StyleConsts.btn_fore;
			}

			@Override
			public void onHover() {
				l_avatar.setBorder(BorderFactory
						.createLineBorder(StyleConsts.listview_sel, 2, true));// btn_sel_back;
			}

			@Override
			public void onSelect() {
				l_avatar.setBorder(BorderFactory
						.createLineBorder(StyleConsts.btn_act_back, 2, true));
			}
		};
		toolBar.add(avatar, c);

		// �Ự��ť
		c.gridy = 1;
		final JLabel l_conv = new JLabel();
		l_conv.setToolTipText("<html><font color = blue>����</font></html>");
		l_conv.setHorizontalAlignment(JLabel.CENTER);
		conversationsBtn = new SelectableDecorator(l_conv) {
			@Override
			public void onRestore() {
				l_conv.setIcon(new ImageIcon(
						ImageUtil.getImage("res/tool/chat_n.png", 22)));// 30;
			}

			@Override
			public void onHover() {
				l_conv.setIcon(new ImageIcon(
						ImageUtil.getImage("res/tool/chat_h.png", 22)));
			}

			@Override
			public void onSelect() {
				l_conv.setIcon(new ImageIcon(
						ImageUtil.getImage("res/tool/chat_s.png", 22)));
			}
		};
		toolBar.add(conversationsBtn, c);

		// ͨѶ¼��ť
		c.gridy = 2;
		final JLabel l_cont = new JLabel();
		l_cont.setToolTipText("<html><font color = blue>ͨѶ¼</font></html>");
		l_cont.setHorizontalAlignment(JLabel.CENTER);
		contactsBtn = new SelectableDecorator(l_cont) {
			@Override
			public void onRestore() {
				l_cont.setIcon(new ImageIcon(
						ImageUtil.getImage("res/tool/contact_n.png", 18)));// 30;
			}

			@Override
			public void onHover() {
				l_cont.setIcon(new ImageIcon(
						ImageUtil.getImage("res/tool/contact_h.png", 18)));
			}

			@Override
			public void onSelect() {
				l_cont.setIcon(new ImageIcon(
						ImageUtil.getImage("res/tool/contact_s.png", 18)));
			}
		};
		toolBar.add(contactsBtn, c);

		// Ⱥ�鰴ť
		c.gridy = 3;
		final JLabel l_grp = new JLabel();
		l_grp.setToolTipText("<html><font color = blue>Ⱥ��</font></html>");
		l_grp.setHorizontalAlignment(JLabel.CENTER);
		groupsBtn = new SelectableDecorator(l_grp) {
			@Override
			public void onRestore() {
				l_grp.setIcon(new ImageIcon(
						ImageUtil.getImage("res/tool/group_n.png", 18)));// 30;
			}

			@Override
			public void onHover() {
				l_grp.setIcon(new ImageIcon(
						ImageUtil.getImage("res/tool/group_h.png", 18)));
			}

			@Override
			public void onSelect() {
				l_grp.setIcon(new ImageIcon(
						ImageUtil.getImage("res/tool/group_s.png", 18)));
			}
		};
		toolBar.add(groupsBtn, c);

		// �û�������ť
		c.gridy = 4;
		final JLabel l_srch = new JLabel();
		l_srch.setToolTipText("<html><font color = blue>�������û�</font></html>");
		l_srch.setHorizontalAlignment(JLabel.CENTER);
		searchUserBtn = new SelectableDecorator(l_srch) {
			@Override
			public void onRestore() {
				l_srch.setIcon(new ImageIcon(
						ImageUtil.getImage("res/tool/search_n.png", 18)));
			}

			@Override
			public void onHover() {
				l_srch.setIcon(new ImageIcon(
						ImageUtil.getImage("res/tool/search_h.png", 18)));
			}

			@Override
			public void onSelect() {
				l_srch.setIcon(new ImageIcon(
						ImageUtil.getImage("res/tool/search_s.png", 18)));
			}
		};
		toolBar.add(searchUserBtn, c);

		// ��䲿��
		c.gridy = 5;
		c.weighty = 1;
		toolBar.add(new JLabel(), c);

		// �˳���¼��ť
		c.weighty = 0;
		c.insets = new Insets(10, 10, 15, 10);
		c.gridy = 6;
		logoutBtn = new JLabel();
		logoutBtn
				.setToolTipText("<html><font color = blue>�˳����˻�</font></html>");
		logoutBtn.setHorizontalAlignment(JLabel.CENTER);
		toolBar.add(logoutBtn, c);
		ButtonStyleMouseAdapter.attach(logoutBtn,
				new ButtonStyleMouseAdapter() {
					@Override
					public void onRestore() {
						logoutBtn.setIcon(new ImageIcon(ImageUtil
								.getImage("res/tool/logout_n.png", 19)));
					}

					@Override
					public void onHover() {
						logoutBtn.setIcon(new ImageIcon(ImageUtil
								.getImage("res/tool/logout_h.png", 19)));
					}

					@Override
					public void onAct() {
						logoutBtn.setIcon(new ImageIcon(ImageUtil
								.getImage("res/tool/logout_s.png", 19)));
					}
				});

		conversationsBtn.select();// Ĭ��ѡ�еİ�ť

	}

	/**
	 * @���ߣ�lll
	 * @ʱ�䣺2016��4��16������3:37:04;20160519; @��Ҫ���ܣ�ϵͳ�������ã� @�޸����ݣ�
	 */
	private void setSystemTray() {
		if (SystemTray.isSupported()) {
			try {
				g_systemTray = SystemTray.getSystemTray();// 20160519;
				// �˻���Ϣ��20160519��
				g_user = Globals.getAccount().getMyInfo(); // 20160519;
				g_userName = g_user.getUsername(); // 20160519;
				g_nickName = g_user.getNickname(); // 20160519;
				if (!g_nickName.isEmpty()) {
					g_userName = g_userName + "(" + g_nickName + ")";
				}
				g_trayIcon =
						new TrayIcon(ImageIO.read(new File("res/trayNSP.png")),
								"NSP IM: " + g_userName + "");// 20160519;
				g_trayIcon.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (e.getButton() == MouseEvent.BUTTON1) {
							showFrame();
						}
					}
				});
				PopupMenu popupMenu = new PopupMenu();
				popupMenu.setFont(new Font("΢���ź�", Font.PLAIN, 12));
				MenuItem changeUser = new MenuItem("�����ʻ�");
				changeUser.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						removeDeleteMessage();
						if (listener != null) {
							listener.onLogout();
						}
						try {
							g_systemTray.remove(g_trayIcon);
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				});
				MenuItem exItem = new MenuItem("�˳�");
				exItem.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						removeDeleteMessage();// 20160917���˳�ʱɾ��ѡ���ˡ�ɾ�����ļ�¼
						System.exit(0);
					}
				});
				popupMenu.add(changeUser);
				popupMenu.addSeparator();
				popupMenu.add(exItem);
				g_trayIcon.setPopupMenu(popupMenu);
				try {
					g_systemTray.add(g_trayIcon);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}

	protected void showFrame() {
		if (this.getState() == Frame.NORMAL
				|| this.getState() == Frame.ICONIFIED) {
			this.setVisible(true);
			this.setState(Frame.NORMAL);
		}
	}

	// ���ø���������Ķ���
	private void setListeners() {
		listener = new LogoutListener() {// 20160517;������

			@Override
			public void onLogout() {
			}
		};
		setTools();
		accountOptList.setOptionClickListener(new OptionClickListener() {
			@Override
			public void onSignout() {
			}

			@Override
			public void onGeneralSetting() {// 20160825;����
				rightContents.add(genSetPanel, generalsetting_card);
				genSetPanel.start();
				rightCards.show(rightContents, generalsetting_card);
			}

			@Override
			public void onMyInfo() {
				p = new MyAccountPanel();
				p.start();
				rightContents.add(p, avatar_card);
				rightCards.show(rightContents, avatar_card);
			}

			@Override
			public void onChangePass() {
				ChangePassPanel p = new ChangePassPanel();
				p.start();
				rightContents.add(p, avatar_card);
				rightCards.show(rightContents, avatar_card);
			}
		});
		conversationList
				.addConversationClickListener(new ConversationClickListener() {
					@Override
					public void onClickConversation(Chat conv) {
						openChat(conv);
					}
				});
		conversationList.addConversationCloseListener(() -> {
			closeChat();
		});
		groupList.addGroupClickListener(new GroupClickListener() {
			@Override
			public void onClickGroup(Group group) {
				openGroup(group);
			}
		});
		groupList.addGroupCloseListener(() -> {
			closeGroup();
		});
		contactList.addContactClickListener(new ContactClickListener() {
			@Override
			public void onClickContact(Contact contact) {
				openContact(contact);
			}
		});
		contactList.addContactCloseListener(() -> {
			closeContact();
		});
	}

	// ���ø�����������ť�Ķ���
	private void setTools() {
		// ���˵ǳ���ť������Ķ���ĳ�����������
		contactsBtn.addMouseListener(
				new ToolSelAdapter(contactsBtn, contact_card));
		groupsBtn.addMouseListener(new ToolSelAdapter(groupsBtn, group_card));
		conversationsBtn.addMouseListener(
				new ToolSelAdapter(conversationsBtn, conversation_card));
		searchUserBtn.addMouseListener(
				new ToolSelAdapter(searchUserBtn, search_card));
		avatar.addMouseListener(new ToolSelAdapter(avatar, avatar_card));
		// �ǳ���ť
		logoutBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() != MouseEvent.BUTTON1) {
					return;
				}
				boolean opt = RoundDialog.showOption(leftContents, "�˳���¼",
						"ȷ��Ҫ�˳���¼��", true);// 20160513;
				if (opt == true) {
					removeDeleteMessage();
					// server.logout().waitForFinish();// 20160517;
					if (listener != null) {
						listener.onLogout();
					}
					try {// 20160525;
						g_systemTray.remove(g_trayIcon);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		});
	}

	// �򿪻Ự��Ӧ���Ҳ����
	public void openChat(Chat conv) {
		titleTxt = new NameGenerator().getConvname(conv);
		setTitleBar(true);
		if (chatPanel == null) {
			chatPanel = new ChatPanel();
			chatPanel.start();
		}
		rightContents.add(chatPanel, conversation_card);
		chatPanel.setConversation(conv);
		rightCards.show(rightContents, conversation_card);
		chatPanel.requireChatFocus();
	}

	private void closeChat() {
		setTitleBar(false);
		rightContents.add(new LogoPanel(100, StyleConsts.main_back),
				conversation_card);
		rightCards.show(rightContents, conversation_card);
	}

	// ����ϵ�˶�Ӧ���Ҳ����
	private void openContact(Contact cont) {
		if (contactPanel == null) {
			contactPanel = new ContactPanel();
			contactPanel.start();
			rightContents.add(contactPanel, contact_card);
		}
		contactPanel.setContact(cont);
		contactPanel.addChatListener(new ChatListener() {
			@Override
			public void onChat(Contact contact) {
				try {
					Chat conv = contact.openChat().get();
					conversationList.selectConversation(conv);
					openChat(conv);
					switchTo(conversationsBtn, conversation_card);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		rightCards.show(rightContents, contact_card);
	}

	private void closeContact() {
		rightContents.add(new LogoPanel(100, StyleConsts.main_back),
				contact_card);
		rightCards.show(rightContents, contact_card);
	}

	// ��Ⱥ���Ӧ���Ҳ����
	private void openGroup(Group group) {
		if (groupPanel == null) {
			groupPanel = new GroupPanel();
			groupPanel.start();
			rightContents.add(groupPanel, group_card);
		}
		groupPanel.setGroup(group);
		groupPanel.addGroupChatListener(new GroupChatListener() {
			@Override
			public void onGroupChat(Group group) {
				try {
					Chat conv = group.openChat().get();
					conversationList.selectConversation(conv);
					openChat(conv);
					switchTo(conversationsBtn, conversation_card);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		rightCards.show(rightContents, group_card);
	}

	private void closeGroup() {
		rightContents.add(new LogoPanel(100, StyleConsts.main_back),
				group_card);
		rightCards.show(rightContents, group_card);
	}

	/**
	 * 20160917:�˳�ʱ������ɾ���ĶԻ�
	 */
	private void removeDeleteMessage() {
		if (!deleteState.isEmpty()) {
			for (Chat chat : Globals.getAccount().getChatList().getChats()) {
				for (Message<?> record : chat.getMessages()) {
					if (deleteState.containsKey(record)) {
						if (deleteState.get(record))
							record.delete();
					}
				}
			}
		}
	}

	/**
	 * ��������ť��������
	 */
	private class ToolSelAdapter extends ActionMouseAdapter {
		private SelectableDecorator tool;
		private String name;

		/**
		 * ����������ť�Ͷ�Ӧ�Ľ����ַ�����������һ���γ���������
		 *
		 * @param tool
		 * @param name
		 */
		public ToolSelAdapter(SelectableDecorator tool, String name) {
			this.tool = tool;
			this.name = name;
		}

		@Override
		public void actionPerformed(MouseEvent e) {
			switchTo(tool, name);
		}
	}

	// ��ת����Ӧ����
	private void switchTo(SelectableDecorator tool, String name) {
		avatar.deselect();
		contactsBtn.deselect();
		groupsBtn.deselect();
		conversationsBtn.deselect();
		searchUserBtn.deselect();
		tool.select();
		leftCards.show(leftContents, name);
		rightCards.show(rightContents, name);
		setTitleBar(name.equals(conversation_card));

	}

	// �����Ƿ���ʾ��������
	private void setTitleBar(boolean show) {
		if (show) {
			JLabel lbl = new JLabel(titleTxt, JLabel.CENTER);
			// lbl.setFont(lbl.getFont().deriveFont(16f));
			// lbl.setFont(new Font("΢���ź�", Font.PLAIN, 16));
			lbl.setFont(new Font("Times", Font.PLAIN, 16));
			titleBar.setTitle(lbl, true);
		} else {
			titleBar.setTitle(new JLabel(), true);
		}
	}

	// ��Ϊ�ֶ��ɵ���С
	private void setManualResizable() {
		// ������ϲ಻�䣬ͨ��ά��������Ҳ���²�ľ����������
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (horizontal(e.getX()))
					dragX = true;
				if (vertical(e.getY()))
					dragY = true;
				disX = getWidth() - e.getX();
				disY = getHeight() - e.getY();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				dragX = false;
				dragY = false;
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (horizontal(e.getX())) {
					setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
				} else if (vertical(e.getY())) {
					setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			// �鿴����Ƿ���ˮƽ����ק����״̬
			private boolean horizontal(int x) {
				// ������󻯵����
				if ((getExtendedState() & JFrame.MAXIMIZED_BOTH) != 0)
					return false;
				return x > getWidth() - 20;
			}

			// �鿴����Ƿ��ڴ�ֱ����ק����״̬
			private boolean vertical(int y) {
				// ������󻯵����
				if ((getExtendedState() & JFrame.MAXIMIZED_BOTH) != 0)
					return false;
				return y > getHeight() - 20;
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Dimension size = getSize();
				if (dragX) {
					int width = e.getX() + disX;
					width = Math.max(width, frameWidth);
					setSize(width, size.height);
					repaint();
				}
				if (dragY) {
					int height = e.getY() + disY;
					height = Math.max(height, frameHeight);
					setSize(size.width, height);
					repaint();
				}
			}
		});
	}

	@Override
	public void setExtendedState(int state) {
		// �������ʱ�Ĵ�С�����򴰿ڻḲ��������Ļ������������
		if ((state & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH) {
			Rectangle bounds = getGraphicsConfiguration().getBounds();
			Rectangle maxBounds = null;
			if (bounds.x == 0 && bounds.y == 0) {
				Insets screenInsets = getToolkit()
						.getScreenInsets(getGraphicsConfiguration());
				maxBounds = new Rectangle(screenInsets.left, screenInsets.top,
						bounds.width - screenInsets.right - screenInsets.left,
						bounds.height - screenInsets.bottom - screenInsets.top);
			} else {
				maxBounds = null;
			}
			super.setMaximizedBounds(maxBounds);
		}
		super.setExtendedState(state);
	}

	// ��GridBagLayoutʱ��ӿؼ��Ŀ�ݷ���
	public void add(Component c, GridBagConstraints constraints, int x, int y,
			int w, int h) {
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = w;
		constraints.gridheight = h;
		add(c, constraints);
	}

	/**
	 * ���õǳ�������
	 *
	 * @param l ������
	 */
	public void setLogoutListener(LogoutListener l) {
		listener = l;
	}

	/**
	 * �ǳ�������
	 */
	public interface LogoutListener {
		/**
		 * �û��ǳ�ʱִ�д˷���
		 */
		void onLogout();
	}

	public void startEvents() {

		Globals.getAccount().getContactRequestList().getUpdateEvent()
				.addListener(new SafeProc0() {

					@Override
					public void invoke() {
						pullRequests();
					}
				});
		pullRequests();
		Globals.getAccount().getChatList().getUpdateEvent().addListener(() -> {
			toFront();
		});
	}

	private void pullRequests() {
		if (pullingReqs == null) {
			pullingReqs = new AtomicBoolean(false);
			reqsListener = () -> {
				pullRequests();
			};
			Globals.getAccount().getContactRequestList().getUpdateEvent()
					.addListener(reqsListener);
		}
		if (!pullingReqs.compareAndSet(false, true))
			return;
		while (true) {
			List<ContactRequest> reqs =
					Globals.getAccount().getContactRequestList().getRequests();
			if (reqs.isEmpty())
				break;
			for (ContactRequest item : reqs) {
				AnswerDialog dialog = new AnswerDialog(item);
				boolean accepted = dialog.getSelection();
				try {
				item.reply(accepted).get();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		pullingReqs.set(false);
	}
}
