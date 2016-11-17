package nsp.im.client.desktop.msgitem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import nsp.im.client.desktop.base.BubbleBorder;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.desktop.utils.ImageUtil;
import nsp.im.client.model.Chat;
import nsp.im.client.model.msg.FileBody;
import nsp.im.client.model.msg.ImageBody;
import nsp.im.client.model.msg.UserMessage;

public class ImageContentFiller implements ContentFiller {

	@Override
	public void setup(Chat conversation) {
	}

	@Override
	public void fillContent(UserMessage<?> record, JPanel parent) {
		final ImageBody body = (ImageBody) record.getBody();
		BufferedImage image = ImageUtil.getImageOrDefault(body.getThumbnail());
		image = ImageUtil.limitWidth(image, 250);
		final JLabel label = new JLabel(new ImageIcon(image));
		label.setText(getLabel(body));
		label.setVerticalTextPosition(SwingConstants.BOTTOM);
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() != MouseEvent.BUTTON1)
					return;
				if (e.getClickCount() != 1)
					return;
				switch (body.getStatus()) {
				case WAIT:
					body.download();
					break;
				case DONE:
					try {
						Desktop.getDesktop().open(new File(body.getPath()));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					break;
				default:
					break;
				}
			}
		});
		record.getUpdateEvent().addListener(() -> {
			label.setText(getLabel(body));
		});
		JPanel panel = new JPanel();
		panel.setBackground(StyleConsts.text_back);
		boolean toleft = !record.isMyMessage();
		Border bd = new BubbleBorder(toleft,
				toleft ? Color.WHITE : StyleConsts.btn_sel_back);
		panel.setBorder(bd);
		panel.add(label, BorderLayout.CENTER);
		parent.setLayout(new BorderLayout());
		if (!record.isMyMessage())
			parent.add(panel, BorderLayout.WEST);
		else
			parent.add(panel, BorderLayout.EAST);
	}
	
	private String href(String text) {
		return "<html><a href=\"\">" + text + "</a></html>";
	}
	
	private String getLabel(ImageBody body) {
		switch (body.getStatus()) {
		case WAIT:
			return href("下载");
		case SENDING:
			return "发送中:" + (body.getTransffered() / (double)body.getLength()) + "%";
		case RECEIVING:
			return "接收中:" + (body.getTransffered() / (double)body.getLength()) + "%";
		case DONE:
			return "";
		}
		return null;
	}
}
