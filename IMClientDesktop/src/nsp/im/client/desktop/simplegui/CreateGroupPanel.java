package nsp.im.client.desktop.simplegui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import nsp.im.client.desktop.base.HintField;
import nsp.im.client.desktop.base.RoundButton;
import nsp.im.client.desktop.base.RoundDialog;
import nsp.im.client.desktop.base.RoundPanel;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.desktop.utils.Globals;

@SuppressWarnings("serial")
public class CreateGroupPanel extends RoundPanel {
	private JTextField nameField;
	private JButton createBtn;
	
	public CreateGroupPanel() {
		setBackground(StyleConsts.main_back);
		layComponents();
	}
	
	private void layComponents() {
		nameField = HintField.createNormalField("群名称");
		nameField.setColumns(15);
		nameField.setFont(nameField.getFont().deriveFont(16f));
		createBtn = RoundButton.createNormalButton();
		createBtn.setText("创建");
		
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(nameField, constraints);
		constraints.gridy = 1;
		add(createBtn, constraints);
	}
	
	public void start() {
		createBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				String groupname = new String(nameField.getText());
				try {
					Globals.getAccount().getGroupList().createGroup(groupname).get();
				} catch(Exception e) {
					RoundDialog.showMsg("创建失败", "创建失败");
					e.printStackTrace();
					return;
				}
				RoundDialog.showMsg("创建成功", "创建成功");
			}
		});
	}
}
