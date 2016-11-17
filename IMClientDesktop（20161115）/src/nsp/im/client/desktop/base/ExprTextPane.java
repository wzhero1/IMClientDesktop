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
 * 支持表情的文本框
 */
@SuppressWarnings("serial")
public class ExprTextPane extends WebTextPane {
	
	public ExprTextPane() {
		//设置支持表情的编辑器
		setEditorKit(new ExprEditorKit());
		
		//设置文档变化监听器，文本内容变化时，检测表情对应的字符串并设置对应的属性，编辑器会根据属性自动进行处理
		getDocument().addDocumentListener(new DocumentChangeAdapter() {

			
			@Override
			public void onChange() {
				//在检测到内容变化的同时进行内容变化会发生异常
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
			
			//递归搜索文档中所有元素
			private void traverse(Element root) {
				//检测到叶子节点，进行处理
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
							//可以转义成表情，为字符串添加表情属性
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
				//递归
				for (int i = 0; i < root.getElementCount(); i++) {
					traverse(root.getElement(i));
				}
			}
		});
	}

	/**
	 * 在文本框中插入表情
	 * @param e 表情
	 */
	public void insertExpr(Expression e) {
		//获取当前属性集
		MutableAttributeSet attr = getInputAttributes();
		//设置表情属性
		attr.removeAttribute(attr);
		ExprView.setExpr(attr, e);
		//插入表情对应字符串
		replaceSelection(strFromExpr(e));
		//恢复属性集原值
		attr.removeAttribute(attr);
	}

	/**
	 * 支持表情的编辑器
	 */
	private static class ExprEditorKit extends StyledEditorKit {
		private static final long serialVersionUID = 5880698295006399810L;

		public ViewFactory getViewFactory() {
	        return new ExprViewFactory();
	    }
	    
	    /**
	     * 支持按字母断行和显示表情的ViewFactory
	     */
	    private static class ExprViewFactory implements ViewFactory {
	        public View create(Element elem) {

	            String kind = elem.getName();
	            if (kind != null) {
	                if (kind.equals(AbstractDocument.ContentElementName)) {
	                	//生成支持按字母断行的LabelView
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
	                	//找到表情属性，生成支持表情显示的View
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
