package nsp.im.client.desktop.utils;

import java.text.Collator;

/**
 * �Ժ�����ȡ����ĸ��ʵ�÷���
 */
public class NameGrouper {
	/**
	 * ��ȡ��Ӣ���ַ���������ĸ
	 * @param s
	 * @return
	 */
	public static char getGroup(String s) {
		String down = s.toLowerCase();
		if (s.length() == 0)
			return '.';
		char c = down.charAt(0);
		if (c >= 'a' && c <= 'z')
			return c;
		//��������
//		Collator clt = Collator.getInstance();
		Collator clt = Collator.getInstance(java.util.Locale.CHINA);//20160601;
		for (int i = 0; i < 26; i++) {
			//��26����ĸ��Ӧ���׸����ֽ��бȽϣ��Ӷ���ȡ����Ӧ����ĸ
			if (clt.compare(s, base[i]) >= 0 && clt.compare(s, base[i + 1]) < 0) {
				return (char)('a' + i);
			}
		}
		//���һ�����ֽ������⴦��
		if (s.charAt(0) == '��') {
			return 'z';
		}
		return '.';
	}
	
	/**
	 * 26����ĸ��Ӧ���׸�����
	 */
	private static String[] base = 
	{
		"��", "��", "��", "��", "��", "��", "��",
		"��", "��", "��", "��", "��", "��", "��",
		"Ŷ", "ž", "��", "Ȼ", "��", "��",
		"��", "��", "��", "��", "ѹ", "��", "��"
	};
}
