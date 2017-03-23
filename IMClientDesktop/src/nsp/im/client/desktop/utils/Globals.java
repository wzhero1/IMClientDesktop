package nsp.im.client.desktop.utils;

import nsp.im.client.model.Account;
import nsp.im.client.model.ModelRoot;

/**
 * ȫ�ֱ���
 */
public class Globals {
	private static ModelRoot model;
	private static Account account;

	/**
	 * ��������
	 * 
	 * @param conn ����
	 */
	public static void setModel(ModelRoot model) {
		Globals.model = model;
	}
	/**
	 * �����˻�
	 * 
	 * @param account �˻�
	 */
	public static void setAccount(Account account) {
		Globals.account = account;
	}

	/**
	 * ��ȡ����
	 * 
	 * @return ����
	 */
	public static ModelRoot getModel() {
		return model;
	}

	/**
	 * ��ȡ�˻�
	 * 
	 * @return �˻�
	 */
	public static Account getAccount() {
		return account;
	}
}
