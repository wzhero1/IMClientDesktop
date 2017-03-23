package nsp.im.client.desktop.base;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * 带提示的输入框
 */
@SuppressWarnings("serial")
public class HintField extends RoundField {
	private boolean empty;
	private String hint;

	/**
	 * 构造一个带提示的输入框
	 * 
	 * @param hover
	 *            悬停颜色
	 * @param hint
	 *            提示内容
	 */
	public HintField(Color hover, String hint) {
		super(hover);
		this.hint = hint;
		judgmentText();
		super.setText(this.hint);
	}

	/**
	 * 构造一个带提示的输入框
	 * 
	 * @param hover
	 *            悬停颜色
	 * @param hint
	 *            提示内容
	 * @param path
	 *            提示内容
	 */
	public HintField(Color hover, String hint, String path) {
		super(hover, path);
		this.hint = hint;
		judgmentText();
		super.setText(this.hint);
	}

	private void judgmentText() {
		empty = true;
		// 焦点变化时，判断内容是否为空
		addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (getText().equals("")) {
					empty = true;
					HintField.super.setText(HintField.this.hint);
				}
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				if (empty) {
					empty = false;
					HintField.super.setText("");
				}
			}
		});
		// 根据是否为空设置文本颜色
		getDocument().addDocumentListener(new DocumentChangeAdapter() {
			@Override
			public void onChange() {
				if (empty) {
					setForeground(StyleConsts.font_gray);
				} else {
					setForeground(new Color(0));
				}
			}
		});
	}

	@Override
	public void setText(String t) {
		// 首先检查内容是否为空
		if (t.length() == 0) {
			empty = true;
			super.setText(hint);
		} else {
			empty = false;
			super.setText(t);
		}
	}

	@Override
	public String getText() {
		// 首先检查内容是否为空
		if (empty) {
			return "";
		} else {
			return super.getText();
		}
	}

	/**
	 * 创建默认样式的提示输入框
	 * 
	 * @param hint
	 *            提示内容
	 * @return 创建的提示框
	 */
	public static HintField createNormalField(String hint) {
		return new HintField(StyleConsts.btn_sel_back, hint);
	}

	/**
	 * 创建带图标的提示输入框
	 * 
	 * @param hint
	 *            提示内容
	 * @param path
	 *            图标图片所在路径
	 * @return 创建的提示框
	 */
	public static HintField createNormalField(String hint, String path) {
		return new HintField(StyleConsts.btn_sel_back, hint, path);
	}

	/**
	 * 创建指定字体大小的默认样式提示输入框
	 * 
	 * @param hint
	 *            提示内容
	 * @param size
	 *            提示内容的字体大小
	 * @return 创建的提示框
	 */
	public static HintField createNormalField(String hint, int size) {
		HintField field = new HintField(StyleConsts.btn_sel_back, hint);
		field.setFont(field.getFont().deriveFont(size));
		return field;
	}
}
