package nsp.im.client.desktop.base;

import java.awt.Color;

import javax.swing.ComboBoxEditor;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxEditor;

/**
 * ����ʾ�����������
 */
@SuppressWarnings("serial")
public class HintComboBox extends RoundComboBox {
	private String hint;

	/**
	 * ����һ������ʾ�����������
	 * @param hover ��ͣ��ɫ
	 * @param hint ��ʾ����
	 */
	public HintComboBox(Color hover, String hint) {
		super(hover, false);
		this.hint = hint;
		setUI(new HintComboBoxUI());
	}
	
	/**
	 * ��ȡ��������
	 * @return ��������
	 */
	public String getText() {
		return getField().getText();
	}
	
	/**
	 * ������������
	 * @param t ��������
	 */
	public void setText(String t) {
		getField().setText(t);
	}
	
	/**
	 * ��ȡʵ��������������õĵ�HintField
	 * @return ������е�HintField���
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
	 * ComboBox�ڲ��������������
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
	 * ComboBox�ڲ��������
	 */
	private class HintEditorField extends HintField {

		public HintEditorField() {
			super(StyleConsts.text_back, hint);
			//��ֹ����߿����ʾ
			setBorderPainted(false);
		}
	}
	
	/**
	 * ����Ĭ����ʽ����ʾ���������
	 * @param hint ��ʾ����
	 * @return �����������
	 */
	public static HintComboBox createNormalComboBox(String hint) {
		return new HintComboBox(StyleConsts.btn_sel_back, hint);
	}
}
