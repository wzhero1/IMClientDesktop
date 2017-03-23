package nsp.im.client.desktop.utils;

import java.text.Collator;

/**
 * 对汉字提取首字母的实用方法
 */
public class NameGrouper {
	/**
	 * 提取中英文字符串的首字母
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
		//处理中文
//		Collator clt = Collator.getInstance();
		Collator clt = Collator.getInstance(java.util.Locale.CHINA);//20160601;
		for (int i = 0; i < 26; i++) {
			//和26个字母对应的首个汉字进行比较，从而提取出对应的字母
			if (clt.compare(s, base[i]) >= 0 && clt.compare(s, base[i + 1]) < 0) {
				return (char)('a' + i);
			}
		}
		//最后一个汉字进行特殊处理
		if (s.charAt(0) == '做') {
			return 'z';
		}
		return '.';
	}
	
	/**
	 * 26个字母对应的首个汉字
	 */
	private static String[] base = 
	{
		"啊", "芭", "擦", "搭", "蛾", "发", "噶",
		"哈", "击", "击", "喀", "垃", "妈", "拿",
		"哦", "啪", "期", "然", "撒", "塌",
		"挖", "挖", "挖", "嘻", "压", "匝", "做"
	};
}
