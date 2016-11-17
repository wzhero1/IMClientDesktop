package nsp.im.client.desktop.base;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * ����ʾ�����������
 */
@SuppressWarnings("serial")
public class HintPassField extends RoundPassField {
	private boolean empty;
	private String hint;
	private boolean g_hint = true;//�����ı�������ʾ���Ļ������ĵĿ���

	/**
	 * ����һ������ʾ�����������
	 *
	 * @param hover
	 *            ��ͣ��ɫ
	 * @param hint
	 *            ��ʾ����
	 */
	public HintPassField(Color hover, String hint) {
		super(hover);
		this.hint = hint;
		judgmentText();
		super.setText(this.hint);
	}

	/**
	 * ����һ������ʾ�����������
	 *
	 * @param hover
	 *            ��ͣ��ɫ
	 * @param hint
	 *            ��ʾ����
	 * @param path
	 *            ��ʾ����
	 */
	public HintPassField(Color hover, String hint, String path) {
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
		// �����Ƿ�Ϊ�������ı���ɫ
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
	public char[] getPassword() {
		// ���ȼ�������Ƿ�Ϊ��
		if (empty) {
			return new char[0];
		} else {
			return super.getPassword();
		}
	}

	/**
	 * ����Ĭ����ʽ����ʾ���������
	 * 
	 * @param hint
	 *            ��ʾ����
	 * @return �����������
	 */
	public static HintPassField createNormalField(String hint) {
		return new HintPassField(StyleConsts.btn_sel_back, hint);
	}

	/**
	 * ����Ĭ����ʽ����ʾ���������
	 * 
	 * @param hint
	 *            ��ʾ����
	 * @param path
	 *            ͼ��ͼƬ����·��
	 * @return �����������
	 */
	public static HintPassField createNormalField(String hint, String path) {
		return new HintPassField(StyleConsts.btn_sel_back, hint, path);
	}

	/**
	 * ����ָ�������С��Ĭ����ʽ��ʾ���������
	 * 
	 * @param hint
	 *            ��ʾ����
	 * @param size
	 *            ��ʾ�����ݵ������С
	 * @return �����������
	 */
	public static HintPassField createNormalField(String hint, int size) {
		HintPassField field = new HintPassField(StyleConsts.btn_sel_back, hint);
		field.setFont(field.getFont().deriveFont(size));
		return field;
	}

	/**
	 * �����ı�������ʾ���Ļ�������
	 * 
	 * @param hint
	 *            ��Ϊtrue,��ʾ����(Ĭ��Ϊ����)����Ϊfalse,��ʾ���ģ�
	 */
	public void setHint(boolean hint){
		g_hint = hint;
	}
}
