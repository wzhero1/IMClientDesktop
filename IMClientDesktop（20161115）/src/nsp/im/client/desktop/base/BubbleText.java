package nsp.im.client.desktop.base;

import java.awt.*;

import nsp.im.client.desktop.utils.ChatBubble;
import nsp.im.client.desktop.utils.GraphicsUtil;

/**
 * ����������״�ı���
 */
@SuppressWarnings("serial")
public class BubbleText extends ExprTextPane {
	private boolean toleft;

    /**
     * ����һ����������
     * @param toleft ��ͷ�Ƿ�����
     * @param normal ������ɫ
     * @param hover ��ͣ��ɫ
     * @param act ������ɫ
     */
    public BubbleText(boolean toleft, final Color normal, final Color hover, final Color act) {
    	this.toleft = toleft;
        setOpaque(false);
        setBorder(null);
		ButtonStyleMouseAdapter.attach(this, new ButtonStyleMouseAdapter() {
			@Override
			public void onRestore() {
				setBackground(normal);
			}
			@Override
			public void onHover() {
				setBackground(hover);
			}
			@Override
			public void onAct() {
				setBackground(act);
			}
		});
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = GraphicsUtil.createHighQualityGraphics(g);
        //��丸����
        g2.setColor(getParent().getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());
        //��������
        ChatBubble bubble = new ChatBubble(toleft, getWidth(), getHeight(), getBackground());
        bubble.fillBubble(0, 0, g2);
        bubble.drawBubble(0, 0, g2);
        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    public Insets getInsets() {
    	int thick = 10;
        Insets insets = new ChatBubble(toleft, 0, 0, getBackground()).getInsets();
        return new Insets(insets.top + thick, insets.left + thick, 
        		insets.bottom + thick, insets.right + thick);
    }
    
    @Override
    public void scrollRectToVisible(Rectangle aRect) {
    	//��ֹ�Զ��������ɼ�λ�ã���ScrollDecorator��ɸ���
    }
}
