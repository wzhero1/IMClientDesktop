package nsp.im.client.desktop.base;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * �ĵ����ݱ仯�������������б仯��ᵽonChange����
 */
public class DocumentChangeAdapter implements DocumentListener {

	@Override
	public void changedUpdate(DocumentEvent e) {
		onChange();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		onChange();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		onChange();
	}
	
	/**
	 * �ĵ����ݱ仯ʱ����������
	 */
	public void onChange() {
		
	}

}
