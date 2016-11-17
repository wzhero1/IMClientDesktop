package nsp.im.client.desktop.chatarea;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import nsp.im.client.desktop.base.ButtonStyleMouseAdapter;
import nsp.im.client.desktop.base.Expression;
import nsp.im.client.desktop.base.RoundPanel;
import nsp.im.client.desktop.base.RoundPopup;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.desktop.utils.ImageUtil;

/**
 * ExprPopup 表情包弹出式菜单
 */
@SuppressWarnings("serial")
public class ExprPopup extends RoundPopup {
	private RoundPanel panel;
	private ExprSelListener listener;
	
	public ExprPopup() {
		panel = new RoundPanel();
		panel.setBackground(StyleConsts.main_back);
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		panel.setLayout(new GridLayout(0, 8));
		listener = new ExprSelListener() {
			@Override
			public void onSelExpr(Expression e) {}
		};
		setContentLayout(new BorderLayout());
		addContent(panel, BorderLayout.CENTER);
		for (Expression e : Expression.values()) {
			addExpr(e);
		}
	}
	
	private void addExpr(final Expression e) {
		String res = "res/expr/" + e.name().toLowerCase() + ".png";
		final JLabel lbl = new JLabel(new ImageIcon(ImageUtil.getImage(res, 30)));//(res, 30);
		lbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getButton() != MouseEvent.BUTTON1)
					return;
				listener.onSelExpr(e);
				lbl.setBackground(StyleConsts.main_back);
				setVisible(false);
			}
		});
		lbl.setOpaque(true);
		ButtonStyleMouseAdapter.attach(lbl, new ButtonStyleMouseAdapter() {
			@Override
			public void onRestore() {
				lbl.setBackground(StyleConsts.main_back);
			}
			
			@Override
			public void onHover() {
				lbl.setBackground(StyleConsts.listview_hov);
			}
			
			@Override
			public void onAct() {
				lbl.setBackground(StyleConsts.listview_sel);
			}
		});
		panel.add(lbl);
	}
	
	public void setExprSelListener(ExprSelListener l) {
		listener = l;
	}
	
	public interface ExprSelListener {
		void onSelExpr(Expression e);
	}
}