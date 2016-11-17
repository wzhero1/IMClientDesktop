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
 * �ı�ѡ���Բ�Ǿ��ε���ʽ�˵�
 */
@SuppressWarnings("serial")
public class TextRoundPopup extends RoundPopup {
	private RoundPanel panel;
	
	/**
	 * ����һ���ı�ѡ���ʽ�˵�
	 */
	public TextRoundPopup() {
		panel = new RoundPanel();
		panel.setBackground(StyleConsts.main_back);
		//�������ף������ı��ػ�ʱ�ƻ�Բ��
		panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		setContentLayout(new BorderLayout());
		addContent(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	}
	/**
	 * 20160918��˵�������µ��ı����
     *@param lbl ��ӵĿؼ�
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
	 * ��˵�������µ��ı���ѡ��󴥷��Ķ���
	 * @param item �ı�
	 * @param listener �ı�ѡ�������
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
