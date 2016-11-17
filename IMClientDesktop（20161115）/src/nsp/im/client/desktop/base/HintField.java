package nsp.im.client.desktop.base;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * ����ʾ�������
 */
@SuppressWarnings("serial")
public class HintField extends RoundField {
	private boolean empty;
	private String hint;

	/**
	 * ����һ������ʾ�������
	 * 
	 * @param hover
	 *            ��ͣ��ɫ
	 * @param hint
	 *            ��ʾ����
	 */
	public HintField(Color hover, String hint) {
		super(hover);
		this.hint = hint;
		judgmentText();
		super.setText(this.hint);
	}

	/**
	 * ����һ������ʾ�������
	 * 
	 * @param hover
	 *            ��ͣ��ɫ
	 * @param hint
	 *            ��ʾ����
	 * @param path
	 *            ��ʾ����
	 */
	public HintField(Color hover, String hint, String path) {
		super(hover, path);
		this.hint = hint;
		judgmentText();
		super.setText(this.hint);
	}

	private void judgmentText() {
		empty = true;
		// ����仯ʱ���ж������Ƿ�Ϊ��
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
		// �����Ƿ�Ϊ�������ı���ɫ
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
		// ���ȼ�������Ƿ�Ϊ��
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
		// ���ȼ�������Ƿ�Ϊ��
		if (empty) {
			return "";
		} else {
			return super.getText();
		}
	}

	/**
	 * ����Ĭ����ʽ����ʾ�����
	 * 
	 * @param hint
	 *            ��ʾ����
	 * @return ��������ʾ��
	 */
	public static HintField createNormalField(String hint) {
		return new HintField(StyleConsts.btn_sel_back, hint);
	}

	/**
	 * ������ͼ�����ʾ�����
	 * 
	 * @param hint
	 *            ��ʾ����
	 * @param path
	 *            ͼ��ͼƬ����·��
	 * @return ��������ʾ��
	 */
	public static HintField createNormalField(String hint, String path) {
		return new HintField(StyleConsts.btn_sel_back, hint, path);
	}

	/**
	 * ����ָ�������С��Ĭ����ʽ��ʾ�����
	 * 
	 * @param hint
	 *            ��ʾ����
	 * @param size
	 *            ��ʾ���ݵ������С
	 * @return ��������ʾ��
	 */
	public static HintField createNormalField(String hint, int size) {
		HintField field = new HintField(StyleConsts.btn_sel_back, hint);
		field.setFont(field.getFont().deriveFont(size));
		return field;
	}
}
