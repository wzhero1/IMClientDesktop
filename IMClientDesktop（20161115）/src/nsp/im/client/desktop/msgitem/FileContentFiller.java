package nsp.im.client.desktop.msgitem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import nsp.im.client.desktop.base.BubbleBorder;
import nsp.im.client.desktop.base.ForwardMouseAdapter;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.domain.msg.TransferStatus;
import nsp.im.client.model.Chat;
import nsp.im.client.model.msg.FileBody;
import nsp.im.client.model.msg.UserMessage;

public class FileContentFiller implements ContentFiller {
	
	@Override
	public void setup(Chat conversation) {
	}
	
	@Override
	public void fillContent(UserMessage<?> record, JPanel parent) {
		final FileBody body = (FileBody)record.getBody();
		String filename = body.getFileName();
		BufferedImage image = new BufferedImage(80, 80, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setColor(new Color(0xa0e0ff));
		g.fillRect(0, 0, 80, 80);
		String[] names = filename.split("\\.");
		String suffix = names[names.length - 1];
		g.setColor(Color.WHITE);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(new Font("黑体" ,Font.BOLD, 30));
		g.drawString(suffix, 40 - g.getFontMetrics().stringWidth(suffix) / 2, 
				40 + (g.getFontMetrics().getAscent() - g.getFontMetrics().getDescent())/ 2);
		g.dispose();
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		c.gridheight = GridBagConstraints.REMAINDER;
		JLabel imageLabel = new JLabel(new ImageIcon(image));
		imageLabel.addMouseListener(new ForwardMouseAdapter());
		panel.add(imageLabel, c);
		
		c.gridx = 1;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 2;
		JLabel nameLabel = new JLabel(getFilename(filename));
		Font f = nameLabel.getFont();
		nameLabel.setFont(f.deriveFont(Font.BOLD, 15));
		nameLabel.addMouseListener(new ForwardMouseAdapter());
		panel.add(nameLabel, c);
		
		c.gridy = 1;
		c.weighty = 1;
		int sz = body.getLength();
		String suf = "B";
		if (sz > 1024) {
			suf = "KB";
			sz /= 1024;
		}
		if (sz > 1024) {
			suf = "MB";
			sz /= 1024;
		}
		JLabel sizeLabel = new JLabel(sz + suf);
		sizeLabel.setFont(f.deriveFont(12f));
		sizeLabel.setForeground(new Color(0x808080));
		sizeLabel.addMouseListener(new ForwardMouseAdapter());
		panel.add(sizeLabel, c);

		final JLabel opLabel = new JLabel(getLabel(body));
		opLabel.addMouseListener(new ForwardMouseAdapter());
		c.gridy = 2;
		panel.add(opLabel, c);
		record.getUpdateEvent().addListener(() -> {
			opLabel.setText(getLabel(body));
		});
		
		panel.addMouseListener(new MouseAdapter() {
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
		panel.setBackground(StyleConsts.text_back);
		boolean toleft = !record.isMyMessage();
		Border bd = new BubbleBorder(toleft, toleft ? Color.WHITE : StyleConsts.btn_sel_back);
		panel.setBorder(bd);
		parent.setLayout(new BorderLayout());
		if (!record.isMyMessage())
			parent.add(panel, BorderLayout.WEST);
		else
			parent.add(panel, BorderLayout.EAST);
	}
	
	private String href(String text) {
		return "<html><a href=\"\">" + text + "</a></html>";
	}
	
	private String getFilename(String name) {
		if (name.length() >= 12) {
			return name.substring(0, 10) + "...";
		}
		else {
			return name;
		}
	}
	
	private String getLabel(FileBody body) {
		switch (body.getStatus()) {
		case WAIT:
			return href("下载");
		case SENDING:
			return "发送中:" + (body.getTransffered() / (double)body.getLength()) + "%";
		case RECEIVING:
			return "接收中:" + (body.getTransffered() / (double)body.getLength()) + "%";
		case DONE:
			return href("打开");
		}
		return null;
	}
}
