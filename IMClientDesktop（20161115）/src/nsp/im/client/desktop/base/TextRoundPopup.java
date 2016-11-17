package nsp.im.client.desktop.base;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

/**
 * 文本选项的圆角矩形弹出式菜单
 */
@SuppressWarnings("serial")
public class TextRoundPopup extends RoundPopup {
	private RoundPanel panel;
	
	/**
	 * 构造一个文本选项弹出式菜单
	 */
	public TextRoundPopup() {
		panel = new RoundPanel();
		panel.setBackground(StyleConsts.main_back);
		//上下留白，避免文本重绘时破坏圆角
		panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		setContentLayout(new BorderLayout());
		addContent(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	}
	/**
	 * 20160918向菜单中添加新的文本插件
     *@param lbl 添加的控件
 	 */
	public void add(JLabel lbl){
		lbl.setOpaque(true);
		lbl.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
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
	/**
	 * 向菜单中添加新的文本和选择后触发的动作
	 * @param item 文本
	 * @param listener 文本选择监听器
	 */
	public void addItem(String item, final ActionListener listener) {
		final JLabel lbl = new JLabel(item);
		lbl.setOpaque(true);
		lbl.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
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
		lbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() != MouseEvent.BUTTON1)
					return;
				listener.actionPerformed(new ActionEvent(e.getSource(), e.getID(), null));
				setVisible(false);
			}
		});
		panel.add(lbl);
	}
}
