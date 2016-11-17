package nsp.im.client.desktop.base;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * 带提示的密文输入框
 */
@SuppressWarnings("serial")
public class HintPassField extends RoundPassField {
	private boolean empty;
	private String hint;
	private boolean g_hint = true;//设置文本内容显示明文还是密文的开关

	/**
	 * 构建一个带提示的密文输入框
	 *
	 * @param hover
	 *            悬停颜色
	 * @param hint
	 *            提示内容
	 */
	public HintPassField(Color hover, String hint) {
		super(hover);
		this.hint = hint;
		judgmentText();
		super.setText(this.hint);
	}

	/**
	 * 构建一个带提示的密文输入框
	 *
	 * @param hover
	 *            悬停颜色
	 * @param hint
	 *            提示内容
	 * @param path
	 *            提示内容
	 */
	public HintPassField(Color hover, String hint, String path) {
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
				if (getPassword().length == 0) {
					empty = true;
					HintPassField.super.setText(HintPassField.this.hint);
				}
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				if (empty) {
					empty = false;
					HintPassField.super.setText("");
				}
			}
		});
		// 根据是否为空设置文本颜色
		getDocument().addDocumentListener(new DocumentChangeAdapter() {
			@Override
			public void onChange() {
				if (empty) {
					setForeground(StyleConsts.font_gray);
					setEchoChar((char) 0);
				} else {
					setForeground(new Color(0));
					if (g_hint) {
						setEchoChar('*');
					}
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
	public char[] getPassword() {
		// 首先检查内容是否为空
		if (empty) {
			return new char[0];
		} else {
			return super.getPassword();
		}
	}

	/**
	 * 创建默认样式的提示密文输入框
	 * 
	 * @param hint
	 *            提示内容
	 * @return 创建的输入框
	 */
	public static HintPassField createNormalField(String hint) {
		return new HintPassField(StyleConsts.btn_sel_back, hint);
	}

	/**
	 * 创建默认样式的提示密文输入框
	 * 
	 * @param hint
	 *            提示内容
	 * @param path
	 *            图标图片所在路径
	 * @return 创建的输入框
	 */
	public static HintPassField createNormalField(String hint, String path) {
		return new HintPassField(StyleConsts.btn_sel_back, hint, path);
	}

	/**
	 * 创建指定字体大小的默认样式提示密文输入框
	 * 
	 * @param hint
	 *            提示内容
	 * @param size
	 *            提示框内容的字体大小
	 * @return 创建的输入框
	 */
	public static HintPassField createNormalField(String hint, int size) {
		HintPassField field = new HintPassField(StyleConsts.btn_sel_back, hint);
		field.setFont(field.getFont().deriveFont(size));
		return field;
	}

	/**
	 * 设置文本内容显示明文还是密文
	 * 
	 * @param hint
	 *            若为true,显示密文(默认为密文)；若为false,显示明文；
	 */
	public void setHint(boolean hint){
		g_hint = hint;
	}
}
