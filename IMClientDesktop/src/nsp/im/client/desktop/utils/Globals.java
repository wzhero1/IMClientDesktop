package nsp.im.client.desktop.utils;

import nsp.im.client.model.Account;
import nsp.im.client.model.ModelRoot;

/**
 * 全局变量
 */
public class Globals {
	private static ModelRoot model;
	private static Account account;

	/**
	 * 设置连接
	 * 
	 * @param conn 连接
	 */
	public static void setModel(ModelRoot model) {
		Globals.model = model;
	}
	/**
	 * 设置账户
	 * 
	 * @param account 账户
	 */
	public static void setAccount(Account account) {
		Globals.account = account;
	}

	/**
	 * 获取连接
	 * 
	 * @return 连接
	 */
	public static ModelRoot getModel() {
		return model;
	}

	/**
	 * 获取账户
	 * 
	 * @return 账户
	 */
	public static Account getAccount() {
		return account;
	}
}
