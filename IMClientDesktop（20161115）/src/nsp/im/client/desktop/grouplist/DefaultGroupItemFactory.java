package nsp.im.client.desktop.grouplist;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nsp.im.client.desktop.base.SelectableDecorator;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.desktop.base.TextRoundPopup;
import nsp.im.client.desktop.utils.ImageUtil;
import nsp.im.client.model.Group;

/**
 * Ĭ�ϵ�Ⱥ�������
 */
public class DefaultGroupItemFactory implements GroupItemFactory {
	
	/**
	 * ����һ��Ⱥ�������
	 * @param server IM����������
	 */
	public DefaultGroupItemFactory() {
	}

	@Override
	public SelectableDecorator makeGroupItem(final Group group) {
		//Ⱥ����
		JLabel txtLabel = new JLabel(group.getGroupName(), JLabel.LEFT);
		txtLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		txtLabel.setFont(txtLabel.getFont().deriveFont(16f));
		//ȺͼƬ
		JLabel imgLabel = new JLabel();
		imgLabel.setIcon(new ImageIcon(ImageUtil.getGroupImage(group, 40)));
		//�������
		group.getUpdateEvent().addListener(() -> {
			txtLabel.setText(group.getGroupName());
			imgLabel.setIcon(new ImageIcon(ImageUtil.getGroupImage(group, 40)));
		});
		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		panel.add(imgLabel, BorderLayout.WEST);
		panel.add(txtLabel, BorderLayout.CENTER);
		//���ÿ��ò���
		TextRoundPopup menu = new TextRoundPopup();
		menu.addItem("�˳�Ⱥ", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				group.leave();
			}
		});
		panel.setComponentPopupMenu(menu);
		//������꽻����ʽ
		@SuppressWarnings("serial")
		SelectableDecorator sel = new SelectableDecorator(panel) {
			@Override
			public void onRestore() {
				panel.setBackground(StyleConsts.listview_back);
			}
			
			@Override
			public void onHover() {
				panel.setBackground(StyleConsts.listview_hov);
			}
			
			@Override
			public void onSelect() {
				panel.setBackground(StyleConsts.listview_sel);
			}
		};
		return sel;
	}
}
