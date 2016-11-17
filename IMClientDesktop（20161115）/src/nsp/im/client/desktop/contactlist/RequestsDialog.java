package nsp.im.client.desktop.contactlist;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nsp.im.client.desktop.base.RoundButton;
import nsp.im.client.desktop.base.RoundDialog;
import nsp.im.client.desktop.base.RoundPanel;
import nsp.im.client.desktop.base.ScrollDecorator;
import nsp.im.client.desktop.base.StyleConsts;

@SuppressWarnings("serial")
public class RequestsDialog extends RoundDialog {
	private RequestsListView listview;
	private RoundButton ok;
	
	public RequestsDialog() {
		layComponents();
		setListeners();
	}
	
	private void layComponents() {
		setTitle(new JLabel("联系人请求"));
		listview = new RequestsListView();
		ScrollDecorator sd = new ScrollDecorator(listview);
		JPanel content = new RoundPanel();
		content.setLayout(new BorderLayout());
		content.add(ScrollDecorator.makeTranslucentBarDecorator(sd), BorderLayout.CENTER);
		content.setBackground(StyleConsts.main_back);
		JPanel btns = new RoundPanel();
		btns.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
		btns.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		ok = RoundButton.createNormalButton();
		ok.setText("  关闭  ");
		btns.add(ok);
		btns.setBackground(StyleConsts.main_back);
		content.add(btns, BorderLayout.SOUTH);
		setContent(content);
	}
	
	private void setListeners() {
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
	
	public void start() {
		listview.setRequests();
	}
}