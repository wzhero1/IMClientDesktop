package nsp.im.client.desktop.base;

import java.awt.Color;

import javax.swing.ComboBoxEditor;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxEditor;

/**
 * 带提示的下拉输入框
 */
@SuppressWarnings("serial")
public class HintComboBox extends RoundComboBox {
	private String hint;

	/**
	 * 构建一个带提示的下拉输入框
	 * @param hover 悬停颜色
	 * @param hint 提示内容
	 */
	public HintComboBox(Color hover, String hint) {
		super(hover, false);
		this.hint = hint;
		setUI(new HintComboBoxUI());
	}
	
	/**
	 * 获取输入内容
	 * @return 输入内容
	 */
	public String getText() {
		return getField().getText();
	}
	
	/**
	 * 设置文字内容
	 * @param t 文字内容
	 */
	public void setText(String t) {
		getField().setText(t);
	}
	
	/**
	 * 获取实现下拉输入框所用的的HintField
	 * @return 输入框中的HintField组件
	 */
	public HintField getField() {
		return (HintField)getEditor().getEditorComponent();
	}
	
	protected class HintComboBoxUI extends RoundComboBoxUI {
		@Override
		protected ComboBoxEditor createEditor() {
			ComboBoxEditor e = new HintComboEditor();
			return e;
		}
	}
	
	/**
	 * ComboBox内部的输入框生成器
	 */
	protected class HintComboEditor extends BasicComboBoxEditor {
		
		@Override
		public JTextField createEditorComponent() {
			HintEditorField f = new HintEditorField();
			f.setBorderPainted(false);
			f.setBackground(StyleConsts.text_back);
			f.addMouseListener(new ForwardMouseAdapter());
			return f;
		}
	}
	
	/**
	 * ComboBox内部的输入框
	 */
	private class HintEditorField extends HintField {

		public HintEditorField() {
			super(StyleConsts.text_back, hint);
			//禁止自身边框的显示
			setBorderPainted(false);
		}
	}
	
	/**
	 * 创建默认样式的提示下拉输入框
	 * @param hint 提示内容
	 * @return 创建的输入框
	 */
	public static HintComboBox createNormalComboBox(String hint) {
		return new HintComboBox(StyleConsts.btn_sel_back, hint);
	}
}
