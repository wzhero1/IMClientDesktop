package nsp.im.client.desktop.msgitem;

import static nsp.im.client.desktop.chatarea.ChatDisplayArea.deleteListener;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nsp.im.client.desktop.base.BubbleText;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.desktop.base.TextRoundPopup;
import nsp.im.client.desktop.simplegui.ContactTransList;
import nsp.im.client.model.Chat;
import nsp.im.client.model.msg.TextBody;
import nsp.im.client.model.msg.UserMessage;

public class TextContentFiller implements ContentFiller  {
	private GridBagConstraints c;

    public static HashMap<UserMessage<?>, Boolean> deleteState = new HashMap<>();//信息是否删除标识
	@Override
	public void setup(Chat conversation) {
	}

	@Override
	public void fillContent(UserMessage<?> record, final JPanel parent) {
//		deleteState.put(record, false);
		TextBody body = (TextBody) record.getBody();
		boolean toleft = !record.isMyMessage();
		Color normal = toleft ? Color.WHITE : StyleConsts.btn_talk_back;
		Color hover = toleft ? StyleConsts.main_back : StyleConsts.btn_act_back;
		Color act = toleft ? StyleConsts.main_back : StyleConsts.btn_act_back;
		final BubbleText area = new BubbleText(toleft, normal, hover, act);
		area.setEditable(false);
		area.setFont(new Font("宋体", Font.BOLD, 16));
		area.setText(body.getText());
		area.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //文本信息右键功能消息
		TextRoundPopup menu = new TextRoundPopup();
		menu.addItem(" 复制 ", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();//获取剪切的文本
				String text = body.getText();
				StringSelection selection = new StringSelection(text);
				clipboard.setContents(selection, null);
			}
		});

        JLabel deleteItem = new JLabel(" 删除 ");
		menu.add(deleteItem);
		deleteItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() != MouseEvent.BUTTON1)
					return;
			    else {
					deleteState.put(record, true);
					menu.setVisible(false);
			    }
			}
		});
		deleteItem.addMouseListener(deleteListener);




		//       *****20161009******
        JLabel transferItem = new JLabel(" 转发 ");
        menu.add(transferItem);
        transferItem.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e){
        		if (e.getButton() == MouseEvent.BUTTON1) {
					ContactTransList dialog = new ContactTransList((c) -> {
						return true;
					},record);
					dialog.start();
				    dialog.getSelection();
				}
    			menu.setVisible(false);
			}
		});

		area.setComponentPopupMenu(menu);

		area.addComponentListener(new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent e) {
			}

			@Override
			public void componentResized(ComponentEvent e) {
				if (area.getPreferredSize().getWidth() < parent.getWidth()) {
					c.fill = GridBagConstraints.NONE;
					parent.add(area, c);
				}
				if (area.getPreferredSize().getWidth() >= parent.getWidth()) {
					c.fill = GridBagConstraints.HORIZONTAL;
					parent.add(area, c);
				}
			}

			@Override
			public void componentMoved(ComponentEvent e) {
			}

			@Override
			public void componentHidden(ComponentEvent arg0) {
			}
		});
		parent.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.weightx = 1;
		c.fill = GridBagConstraints.NONE;
		if (!record.isMyMessage())
			c.anchor = GridBagConstraints.WEST;
		else
			c.anchor = GridBagConstraints.EAST;
		parent.add(area, c);
	}
}
