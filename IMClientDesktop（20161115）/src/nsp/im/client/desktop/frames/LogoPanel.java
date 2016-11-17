package nsp.im.client.desktop.frames;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.alee.laf.label.WebLabel;
import nsp.im.client.desktop.base.RoundPanel;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.desktop.utils.ImageUtil;

@SuppressWarnings("serial")
public class LogoPanel extends RoundPanel {
	private int g_size = 0;// 20160513;

	public LogoPanel(Color back) {
		setLayout(new BorderLayout());
		WebLabel lbl = new WebLabel("NSP IM", JLabel.CENTER);
		lbl.setForeground(StyleConsts.listview_sel);
		lbl.setFont(lbl.getFont().deriveFont(20f));
		lbl.setBorder(BorderFactory.createLineBorder(back, 20));
		lbl.setHorizontalAlignment(JLabel.CENTER);
		setBackground(back);
		add(lbl, BorderLayout.CENTER);
	}

	public LogoPanel(int size, Color back) {// 20160513;
		g_size = size;
		setLayout(new BorderLayout());
		JLabel lbl = new JLabel();
		lbl.setHorizontalAlignment(JLabel.CENTER);
		lbl.setIcon(new ImageIcon(ImageUtil.getImage("res/NSP35.png", g_size, false, 0.2f)));//20160517;
		setBackground(back);
		add(lbl, BorderLayout.CENTER);
	}

	public LogoPanel() {
		this(new Color(0, 0, 0, 0));
	}

	public LogoPanel(int size) {// 20160513;
		this(size, new Color(0, 0, 0, 0));
	}
}
