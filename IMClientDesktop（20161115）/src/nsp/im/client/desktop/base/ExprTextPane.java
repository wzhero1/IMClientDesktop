package nsp.im.client.desktop.base;

import com.alee.laf.text.WebTextPane;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.LabelView;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.ParagraphView;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

/**
 * ֧�ֱ�����ı���
 */
@SuppressWarnings("serial")
public class ExprTextPane extends WebTextPane {
	
	public ExprTextPane() {
		//����֧�ֱ���ı༭��
		setEditorKit(new ExprEditorKit());
		
		//�����ĵ��仯���������ı����ݱ仯ʱ���������Ӧ���ַ��������ö�Ӧ�����ԣ��༭������������Զ����д���
		getDocument().addDocumentListener(new DocumentChangeAdapter() {

			
			@Override
			public void onChange() {
				//�ڼ�⵽���ݱ仯��ͬʱ�������ݱ仯�ᷢ���쳣
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						refresh();
					}
				});
			}
			
			private void refresh() {
				Element root = getDocument().getDefaultRootElement();
				traverse(root);
			}
			
			//�ݹ������ĵ�������Ԫ��
			private void traverse(Element root) {
				//��⵽Ҷ�ӽڵ㣬���д���
				if (root.getName().equals(AbstractDocument.ContentElementName)) {
					int start = root.getStartOffset();
					int len = root.getEndOffset() - start;
					try {
						String content = getDocument().getText(start, len);
						Pattern pat = Pattern.compile("/e.*?&/");
						Matcher mat = pat.matcher(content);
						while (mat.find()) {
							String cnt = mat.group();
							int l = mat.start() + start;
							int r = mat.end() + start;
							MutableAttributeSet attr = new SimpleAttributeSet();
							Expression expr = exprFromStr(cnt);
							//����ת��ɱ��飬Ϊ�ַ�����ӱ�������
							if (expr != null) {
								ExprView.setExpr(attr, exprFromStr(cnt));
								((AbstractDocument)getDocument()).replace(l, r - l, cnt, attr);
							}
						}
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
					return;
				}
				//�ݹ�
				for (int i = 0; i < root.getElementCount(); i++) {
					traverse(root.getElement(i));
				}
			}
		});
	}

	/**
	 * ���ı����в������
	 * @param e ����
	 */
	public void insertExpr(Expression e) {
		//��ȡ��ǰ���Լ�
		MutableAttributeSet attr = getInputAttributes();
		//���ñ�������
		attr.removeAttribute(attr);
		ExprView.setExpr(attr, e);
		//��������Ӧ�ַ���
		replaceSelection(strFromExpr(e));
		//�ָ����Լ�ԭֵ
		attr.removeAttribute(attr);
	}

	/**
	 * ֧�ֱ���ı༭��
	 */
	private static class ExprEditorKit extends StyledEditorKit {
		private static final long serialVersionUID = 5880698295006399810L;

		public ViewFactory getViewFactory() {
	        return new ExprViewFactory();
	    }
	    
	    /**
	     * ֧�ְ���ĸ���к���ʾ�����ViewFactory
	     */
	    private static class ExprViewFactory implements ViewFactory {
	        public View create(Element elem) {

	            String kind = elem.getName();
	            if (kind != null) {
	                if (kind.equals(AbstractDocument.ContentElementName)) {
	                	//����֧�ְ���ĸ���е�LabelView
	                    return new WrapLabelView(elem);
	                } else if (kind.equals(AbstractDocument.ParagraphElementName)) {
	                    return new ParagraphView(elem);
	                } else if (kind.equals(AbstractDocument.SectionElementName)) {
	                    return new BoxView(elem, View.Y_AXIS);
	                } else if (kind.equals(StyleConstants.ComponentElementName)) {
	                    return new ComponentView(elem);
	                } else if (kind.equals(StyleConstants.IconElementName)) {
	                    return new IconView(elem);
	                } else if (kind.equals(ExprView.ExprElementName)) {
	                	//�ҵ��������ԣ�����֧�ֱ�����ʾ��View
	                	return new ExprView(elem);
	                }
	            }
	            return new LabelView(elem);
	        }
	    }   
	}
	
	private static String strFromExpr(Expression e) {
		return "/e&" + e.name() + "&/";
	}
	
	private static Expression exprFromStr(String s) {
		try {
			return Expression.valueOf(s.substring(3, s.length() - 2));
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
	
}
