package nsp.im.client.desktop.base;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * 文档内容变化监听器，将所有变化归结到onChange方法
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
	 * 文档内容变化时，触发方法
	 */
	public void onChange() {
		
	}

}
