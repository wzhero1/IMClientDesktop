package nsp.im.client.desktop.simplegui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

import nsp.im.client.desktop.base.ButtonStyleMouseAdapter;
import nsp.im.client.desktop.base.ListView;
import nsp.im.client.desktop.base.SelectableDecorator;
import nsp.im.client.desktop.base.StyleConsts;

@SuppressWarnings("serial")
public class AccountOptionList extends ListView {
	private OptionClickListener listener;
	
	public AccountOptionList() {
		listener = new OptionClickListener() {
			@Override
			public void onSignout() {}
			@Override
			public void onMyInfo() {}
			@Override
			public void onChangePass() {}
			@Override
			public void onGeneralSetting(){}
		};
		SelectableDecorator myInfo = wrapSel("我的账户");
		myInfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				listener.onMyInfo();
			}
		});
		SelectableDecorator generalSetting = wrapSel("通用设置");
		generalSetting.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				listener.onGeneralSetting();
			}
		});
		SelectableDecorator changePass = wrapSel("修改密码");
		changePass.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				listener.onChangePass();
			}
		});
		JLabel signout = wrap("注销账户");
		signout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				listener.onSignout();
			}
		});
		addComponent(myInfo);
		addComponent(generalSetting);
		addComponent(changePass);
		addComponent(signout);
	}
	
	private SelectableDecorator wrapSel(String option) {
		final JLabel lbl = new JLabel(option, JLabel.CENTER);
		lbl.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0), 10));
		lbl.setFont(lbl.getFont().deriveFont(16f));
		SelectableDecorator p = new SelectableDecorator(lbl) {
			@Override
			public void onRestore() {
				lbl.setForeground(StyleConsts.font_back);
			}
			
			@Override
			public void onHover() {
				lbl.setForeground(StyleConsts.btn_sel_back);
			}
			
			@Override
			public void onSelect() {
				lbl.setForeground(StyleConsts.btn_act_back);
			}
		};
		p.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, StyleConsts.border));
		return p;
	}
	
	private JLabel wrap(String option) {
		final JLabel lbl = new JLabel(option, JLabel.CENTER);
		lbl.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0), 10));
		lbl.setFont(lbl.getFont().deriveFont(16f));
		ButtonStyleMouseAdapter.attach(lbl, new ButtonStyleMouseAdapter() {
			@Override
			public void onRestore() {
				lbl.setForeground(StyleConsts.font_back);
			}
			
			@Override
			public void onHover() {
				lbl.setForeground(StyleConsts.close_hov);
			}
			
			@Override
			public void onAct() {
				lbl.setForeground(StyleConsts.close_sel);
			}
		});
		Border b = BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, StyleConsts.border), lbl.getBorder());
		lbl.setBorder(b);
		return lbl;
	}
	
	public void setOptionClickListener(OptionClickListener l) {
		listener = l;
	}
	
	public interface OptionClickListener {
		void onMyInfo();
		void onChangePass();
		void onSignout();
		void onGeneralSetting();
	}

}
