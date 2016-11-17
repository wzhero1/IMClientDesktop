package nsp.im.client.desktop.base;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.Position;
import javax.swing.text.View;

import nsp.im.client.desktop.utils.GraphicsUtil;
import nsp.im.client.desktop.utils.ImageUtil;

/**
 * ר������ʾ����ͼƬ��View
 */
public class ExprView extends View {
	
	public static String ExprElementName = "expr";
	public static String ExprAttribute = "expr"; 
	
    private Icon origin;
    private Icon backed;

    /**
     * ����TextComponent�е�Ԫ�ع����µ�ExprView��element���������ȷ�ı������ԣ���setExpr����
     * @param elem ���ڹ���View��Ԫ��
     */
    public ExprView(Element elem) {
        super(elem);
        //��ȡ����ͼƬ
        AttributeSet attr = elem.getAttributes();
        Expression e = (Expression)attr.getAttribute(ExprAttribute);
        BufferedImage img = ImageUtil.getImage("res/expr/" + e + ".png", StyleConsts.expression_len);
        origin = new ImageIcon(img);
        //���ɱ�ѡ������µı���ͼƬ
        BufferedImage filled = new BufferedImage(30, 30, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = filled.createGraphics();
        GraphicsUtil.applyQualityRenderingHints(g);
        g.drawImage(img, 0, 0, StyleConsts.listview_hov, null);
        g.dispose();
        backed = new ImageIcon(filled);
    }
	
	/**
	 * ���ݱ��������ȷ������
	 * @param attr Ҫ��ӵ������Լ���
	 * @param expr ����
	 */
	public static void setExpr(MutableAttributeSet attr, Expression expr) {
		attr.addAttribute(AbstractDocument.ElementNameAttribute, ExprElementName);
		attr.addAttribute(ExprAttribute, expr);
	}

    @Override
    public void paint(Graphics g, Shape a) {
        Rectangle alloc = a.getBounds();
        Container c = getContainer();
        //����ÿ����ѡ�������жϱ����Ƿ�ѡ��
        boolean selected = false;
        if (c instanceof JTextComponent) {
        	int p0 = getStartOffset();
        	int p1 = getEndOffset();
        	JTextComponent tc = (JTextComponent)c;
        	Highlighter.Highlight[] hs = tc.getHighlighter().getHighlights();
        	for (Highlighter.Highlight h : hs) {
        		int hStart = h.getStartOffset();
        		int hEnd = h.getEndOffset();
        		if (hStart <= p0 && hEnd >= p1) {
        			selected = true;
        			break;
        		}
        	}
        }
        //���Ʊ���
        Graphics2D g2d = GraphicsUtil.createHighQualityGraphics(g);
        if (selected) {
            backed.paintIcon(c, g2d, alloc.x, alloc.y);
        }
        else {
            origin.paintIcon(c, g2d, alloc.x, alloc.y);	
        }
        g2d.dispose();
    }

    @Override
    public float getPreferredSpan(int axis) {
        switch (axis) {
        case View.X_AXIS:
            return origin.getIconWidth();
        case View.Y_AXIS:
            return origin.getIconHeight();
        default:
            throw new IllegalArgumentException("Invalid axis: " + axis);
        }
    }

    @Override
    public float getAlignment(int axis) {
        switch (axis) {
        case View.Y_AXIS:
            return 1;
        default:
            return super.getAlignment(axis);
        }
    }

    @Override
    public Shape modelToView(int pos, Shape a, Position.Bias b) throws BadLocationException {
    	//����IconView
        int p0 = getStartOffset();
        int p1 = getEndOffset();
        if ((pos >= p0) && (pos <= p1)) {
            Rectangle r = a.getBounds();
            if (pos == p1) {
                r.x += r.width;
            }
            r.width = 0;
            return r;
        }
        throw new BadLocationException(pos + " not in range " + p0 + "," + p1, pos);
    }

    @Override
    public int viewToModel(float x, float y, Shape a, Position.Bias[] bias) {
    	//����IconView
        Rectangle alloc = (Rectangle) a;
        if (x < alloc.x + (alloc.width / 2)) {
            bias[0] = Position.Bias.Forward;
            return getStartOffset();
        }
        bias[0] = Position.Bias.Backward;
        return getEndOffset();
    }
	
    @Override
    public float getMinimumSpan(int axis) {
    	//����Line Break�㷨����Ҫ����0������ȷ����
    	if (axis == View.X_AXIS) {
    		return 0;
    	}
    	return super.getMinimumSpan(axis);
    }

}
