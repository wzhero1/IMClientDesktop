package nsp.im.client.desktop.base;

import java.awt.Color;
import java.awt.Font;

/**
 * 对界面上所用到的有关样式的常量进行统一管理
 */
public class StyleConsts {
	public static Color side_back = new Color(0x404040);
	// 深灰近黑，聊天页最左侧信息颜色，三项账号信息字体默认颜色；
	public static Color font_back = new Color(0xa0a0a0);// 浅灰；
	public static Color link_back = new Color(0x38b838);// 注册按钮中绿;//深蓝(0x4040ff);
	public static Color link_back1 = new Color(0x38e838);// 悬停进入注册按钮浅绿；
	public static Color link_back2 = new Color(0x388838);// 按下注册按钮深绿；

	public static Color listview_back = new Color(0xf0f0f0);// (0xececf0);浅灰近白(0xf0f0f0);?
	public static Color listview_hov = new Color(0xdcdcdc);// (0xd8d8dc);(0xdcdcdc)浅灰；?
	public static Color listview_sel = new Color(0xbcc0c8);// (0xb8bcc8);(0xbcc0c8)灰；?
	public static Color listview_lbl = new Color(0xe0e0e0);// (0xdcdce0); (0xe0e0e0)头像默认边框透明浅灰；?

	public static Color font_gray = new Color(0xa0a0a0);
	public static Color font_black = new Color(0x000000);

	public static Color main_back = new Color(0xf6f6f6);// (0xf4f4f8);(0xf6f6f6)浅灰近白(0xf8f8f8);0xff4040红；?
	public static Color text_back = new Color(0xfcfcfc);// (0xf8f8fc);(0xfcfcfc)浅灰几乎白；?

	public static Color btn_back = new Color(0x38c838);// 绿；
	public static Color btn_fore = new Color(0xffffff);// 白；
	public static Color btn_sel_back = new Color(0x3adc3a);// 绿浅(0x3cd03c);(0x3adc3a)；?
	public static Color btn_talk_back = new Color(158, 234, 106);// 很浅的绿色
//	public static Color btn_act_back = new Color(0x38c838);// 绿；
	public static Color btn_act_back = new Color(152, 225, 101);// 绿；
	public static Color border = new Color(0xe0e0e0);// 浅灰；

	public static Color close_hov = new Color(0xEE3B3B);// 红ff4040；
	public static Color close_sel = new Color(0xff0000);// 真红；

	public static Font default_font = new Font("Microsoft Yahei UI", Font.PLAIN, 12);
	// public static Font default_font = new Font("微软雅黑", Font.PLAIN, 12);
	 public static Font TNR_font = new Font("Times New Roman", Font.PLAIN, 16);
	// 新增颜色；
	public static Color bac_gre = new Color(0x37ff37);// 背景按钮绿；
	public static Color rempawd_gre = new Color(0x383838);// 记住密码灰；
	public static Color rempawd_gre1 = new Color(0x707070);// 记住密码灰；
	public static Color rempawd_gre2 = new Color(0x383838);// 记住密码灰；
	
	public static int expression_len = 20;

	// 标题字体；
	public static int corner_rad = 4;
	public static int corner_rad1 = 3;
}
