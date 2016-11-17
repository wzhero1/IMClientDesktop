package nsp.im.client.desktop.simplegui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JPasswordField;

import nsp.im.client.desktop.base.HintPassField;
import nsp.im.client.desktop.base.RoundButton;
import nsp.im.client.desktop.base.RoundDialog;
import nsp.im.client.desktop.base.RoundPanel;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.desktop.utils.Globals;

@SuppressWarnings("serial")
public class ChangePassPanel extends RoundPanel {
	private JPasswordField oldPassField;
	private JPasswordField newPassField;
	private JPasswordField confField;
	private JButton changeBtn;
	private JButton g_clearBtn;// 20160513;

	public ChangePassPanel() {
		setBackground(StyleConsts.main_back);
		layComponents();
	}

	private void layComponents() {
		oldPassField = HintPassField.createNormalField("旧密码");
		oldPassField.setColumns(25);// 20160513;
		oldPassField.setFont(oldPassField.getFont().deriveFont(18f));// 20160513;
		newPassField = HintPassField.createNormalField("新密码");
		newPassField.setColumns(25);// 20160513;
		newPassField.setFont(newPassField.getFont().deriveFont(18f));// 20160513;
		confField = HintPassField.createNormalField("确认密码");
		confField.setColumns(25);// 20160513;
		confField.setFont(confField.getFont().deriveFont(18f));// 20160513;
		changeBtn = RoundButton.createNormalButton();
		changeBtn.setText("修 改");// 20160513;
		g_clearBtn = RoundButton.createNormalButton();// 20160513;
		g_clearBtn.setText("清 空");// 20160513;

		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(-5,0,5,0);//(10, 10, 10, 10); 20160513;
		constraints.gridwidth = 2;// 20160513;
		add(oldPassField, constraints);
		constraints.insets = new Insets(0,0,5,0);// 20160513;
		constraints.gridy = 1;
		add(newPassField, constraints);
		constraints.gridy = 2;
		add(confField, constraints);
		constraints.gridy = 3;
		constraints.gridwidth = 1;// 20160513;
		constraints.insets = new Insets(25,5,0,0);// 20160513;
		add(changeBtn, constraints);
		constraints.gridx = 1;// 20160513;
		constraints.insets = new Insets(25,0,0,5);
		constraints.fill = GridBagConstraints.VERTICAL;
		constraints.anchor = GridBagConstraints.EAST;
		add(g_clearBtn, constraints);// 20160513;
	}

	public void start() {
		changeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				String pass = new String(oldPassField.getPassword());
				String pass1 = new String(newPassField.getPassword());
				String pass2 = new String(confField.getPassword());
				if (!pass1.equals(pass2)) {
					RoundDialog.showMsg(ChangePassPanel.this, "修改密码", "检查两次输入密码是否一致");// 20160513;
					return;
				}
				try {
					Globals.getAccount().getMyInfo().changePassword(pass, pass1).get();
				} catch (Exception e) {
					RoundDialog.showMsg(ChangePassPanel.this, "修改密码", "修改失败");// 20160513;
					e.printStackTrace();
					return;
				}
				RoundDialog.showMsg(ChangePassPanel.this, "修改密码", "修改成功");// 20160513;
			}
		});

		changeBtn.addKeyListener(new KeyAdapter() {// 20160513;
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					changeBtn.doClick();
				}
			}
		});

		oldPassField.addKeyListener(new KeyAdapter() {// 20160513;
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					changeBtn.doClick();
				}
			}
		});

		newPassField.addKeyListener(new KeyAdapter() {// 20160513;
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					changeBtn.doClick();
				}
			}
		});

		confField.addKeyListener(new KeyAdapter() {// 20160513;
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					changeBtn.doClick();
				}
			}
		});

		g_clearBtn.addActionListener(new ActionListener() {// 20160513;

			@Override
			public void actionPerformed(ActionEvent e) {
				oldPassField.setText("");
				newPassField.setText("");
				confField.setText("");
			}
		});

		g_clearBtn.addKeyListener(new KeyAdapter() {// 20160513;
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					g_clearBtn.doClick();
				}
			}
		});
	}
}
