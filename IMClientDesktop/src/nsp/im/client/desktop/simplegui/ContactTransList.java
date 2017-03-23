package nsp.im.client.desktop.simplegui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.function.Function;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nsp.im.client.desktop.base.RoundButton;
import nsp.im.client.desktop.base.RoundDialog;
import nsp.im.client.desktop.base.RoundPanel;
import nsp.im.client.desktop.base.ScrollDecorator;
import nsp.im.client.desktop.base.SelectableDecorator;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.desktop.base.TitleBar;
import nsp.im.client.desktop.chatarea.ChatPanel;
import nsp.im.client.desktop.contactlist.ContactsListView;
import nsp.im.client.desktop.contactlist.ContactsListView.ContactClickListener;
import nsp.im.client.desktop.conversationlist.ConversationsListView;
import nsp.im.client.desktop.frames.MainFrame;
import nsp.im.client.desktop.usersearch.SearchPanel;
import nsp.im.client.model.Chat;
import nsp.im.client.model.Contact;
import nsp.im.client.model.ex.IMException;
import nsp.im.client.model.msg.TextBody;
import nsp.im.client.model.msg.UserMessage;

/**
 * 转发联系人选择界面
 **/

public class ContactTransList extends RoundDialog {

	private ContactsListView listview;
	private Contact selection;
	private RoundButton ok;
	private RoundButton cancel;
	private HashSet<ChatListener> listeners;
	private Contact currentContact;
	private Chat conversation;
	private TextBody body;

	private ConversationsListView conversationList;
	private static String conversation_card = "conversation";
	private String titleTxt;
	private TitleBar titleBar;
	private ChatPanel chatPanel;
	private JPanel rightContents;
	private CardLayout rightCards;

	public static MainFrame cf;

	private SelectableDecorator avatar;
	private SelectableDecorator contactsBtn;
	private SelectableDecorator groupsBtn;
	private SelectableDecorator searchUserBtn;

	// 五个界面所对应的字符串
	private static String avatar_card = "avatar";
	private static String contact_card = "contact";
	private static String group_card = "group";
	private static String search_card = "search";
	private static String generalsetting_card = "generalsetting";

	private SearchPanel searchUserPanel;
	private GeneralSettingPanel genSetPanel;// 20160825

	// 左面板
	private JPanel leftContents;
	private CardLayout leftCards;
	private SelectableDecorator conversationsBtn;

	public static Function<Contact, Boolean> func = (Contact) -> true;

	public ContactTransList(Function<Contact, Boolean> function,
			UserMessage<?> record) {
		body = (TextBody) record.getBody();
		listeners = new HashSet<>();
		conversationList = new ConversationsListView();
		func = function;
		layComponents();
		setListeners();
	}

	private void layComponents() {
		setTitle(new JLabel("选择联系人"));
		listview = new ContactsListView();
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

	private void sendMessage(String message) {
		conversation.sendText(message).onFailure(IMException.class, (e) -> {
			String err = e.getError().name();
			if (err.equals("NOT_CONTACT"))
				RoundDialog.showMsg("发送失败", "对方已非好友");
			else if (err.equals("NOT_IN_GROUP"))
				RoundDialog.showMsg("发送失败", "已不在群组中");
		});
	}

	private void sentText() {
		String text = body.getText(); // 获取文本
		sendMessage(text);
	}

	private void setListeners() {
		listview.addContactClickListener(new ContactClickListener() {
			@Override
			public void onClickContact(Contact contact) {
				selection = contact;
				try {
					conversation = selection.openChat().get();
					// 打开联系人对应的右面板
					cf.openChat(conversation);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (ChatListener l : listeners) {
					l.onChat(currentContact);
				}
				try {
					sentText();
				} catch (Exception e1) {
					e1.printStackTrace();
				} finally {
					dispose();
				}
			}
		});
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	public void setContact(Contact contact) {
		currentContact = contact;
		// updateContact(contact);
	}

	public void start() {
		listview.start();
	}

	public void getSelection() {
		setModal(true);
		setSize(300, 600);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public interface ChatListener {
		void onChat(Contact contact);
	}

	// private void openChat(Chat conv) {
	// titleTxt = new NameGenerator(server).getConvname(conv);
	// setTitleBar(true);
	// if (chatPanel == null) {
	// chatPanel = new ChatPanel();
	// chatPanel.start(server);
	// }
	// rightContents.add(chatPanel, conversation_card);
	// chatPanel.setConversation(conv);
	// rightCards.show(rightContents, conversation_card);
	// chatPanel.requireChatFocus();
	// }

	// private void switchTo(SelectableDecorator tool, String name) {
	// avatar.deselect();
	// contactsBtn.deselect();
	// groupsBtn.deselect();
	// conversationsBtn.deselect();
	// searchUserBtn.deselect();
	// tool.select();
	// leftCards.show(leftContents, name);
	// rightCards.show(rightContents, name);
	// setTitleBar(name.equals(conversation_card));
	//
	// }
	//
	// 设置是否显示标题文字
	// private void setTitleBar(boolean show) {
	// if (show) {
	// JLabel lbl = new JLabel(titleTxt, JLabel.CENTER);
	// lbl.setFont(new Font("Times", Font.PLAIN, 16));
	// titleBar.setTitle(lbl, true);
	// } else {
	// titleBar.setTitle(new JLabel(), true);
	// }
	// }
}
