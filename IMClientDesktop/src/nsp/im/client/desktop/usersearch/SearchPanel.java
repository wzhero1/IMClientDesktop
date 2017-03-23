package nsp.im.client.desktop.usersearch;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import nsp.im.client.desktop.base.RoundButton;
import nsp.im.client.desktop.base.RoundDialog;
import nsp.im.client.desktop.base.RoundField;
import nsp.im.client.desktop.base.ScrollDecorator;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.desktop.utils.Globals;
import nsp.im.client.model.User;

@SuppressWarnings("serial")
public class SearchPanel extends JPanel {
	private JTextField searchField;
	private JButton searchBtn;
	private UsersListView userListView;

	public SearchPanel() {
		setBackground(StyleConsts.main_back);
		layComponents();
	}

	private void layComponents() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 10, 10);

		searchField = RoundField.createNormalField();
		c.weightx = 1;
		c.fill = GridBagConstraints.BOTH;
		add(searchField, c);

		searchBtn = RoundButton.createNormalButton();
		searchBtn.setText("搜索用户");
		c.gridx = 1;
		c.weightx = 0;
		add(searchBtn, c);

		userListView = new UsersListView();
		userListView.setBackground(StyleConsts.main_back);
		ScrollDecorator dec = new ScrollDecorator(userListView);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		add(ScrollDecorator.makeTranslucentBarDecorator(dec), c);
	}

	public void start() {
		userListView.start();
		searchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String query = searchField.getText();
				try {
					Collection<User> users =
							Globals.getAccount().getSearchService()
							.searchUsers(query).get();
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							System.out.println(users);
							if (users == null) {
								String title = "搜索失败";
								String info = "很抱歉，小I并没有搜到这个人呐=_=";
								RoundDialog.showMsg(title, info);// 20160526;
							} else {
								userListView.setUsers(users);
							}
						}
					});
				} catch (Exception e) {
				}
			}
		});
		searchBtn.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					String query = searchField.getText();
					try {
						Collection<User> users =
								Globals.getAccount().getSearchService()
								.searchUsers(query).get();
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								userListView.setUsers(users);
							}
						});
					} catch (Exception e1) {
					}
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});
		searchField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() != KeyEvent.VK_ENTER)
					return;
				String query = searchField.getText();
				try {
					Collection<User> users = Globals.getAccount().getSearchService()
							.searchUsers(query).get();
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							userListView.setUsers(users);
						}
					});
				} catch (Exception e) {
				}
			}

		});
	}
}
