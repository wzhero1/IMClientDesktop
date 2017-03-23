package nsp.im.client.desktop.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.model.Group;
import nsp.im.client.model.Member;
import nsp.im.client.model.User;

/**
 * 包含关于图片处理的实用方法
 */
public class ImageUtil {
	/**
	 * 从字节数组获取图片
	 * 
	 * @param bytes
	 *            字节数组
	 * @return 转换成的图片，不成功则为null
	 * @throws IOException
	 */
	public static BufferedImage getImage(byte[] bytes) throws IOException {
		ByteArrayInputStream input = new ByteArrayInputStream(bytes);
		BufferedImage image = null;
		try {
			image = ImageIO.read(input);
		} catch (IOException e) {
			input.close();
		}
		return image;
	}

	/**
	 * 从资源路径获取图片
	 * 
	 * @param resName
	 *            资源路径
	 * @param w
	 *            图片长
	 * @param h
	 *            图片宽
	 * @return 转换成的图片
	 */
	public static BufferedImage getImage(String resName, int w, int h) {
		try {
			return scaleImage(ImageIO.read(new File(resName)), w, h);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 从资源路径获取图片，可选择是否将原图转为灰色
	 * 
	 * @param resName
	 *            资源路径
	 * @param w
	 *            图片长
	 * @param h
	 *            图片宽
	 * @param colorful
	 *            图片保持原彩色还是转换为灰色,false为灰色
	 * @return 转换成的图片
	 */
	public static BufferedImage getImage(String resName, int w, int h, boolean colorful) {// 20160513;
		try {
			return scaleImage(ImageIO.read(new File(resName)), w, h, colorful);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 从资源路径获取图片，可将原图转为指定不透明度的图片
	 * 
	 * @param resName
	 *            资源路径
	 * @param w
	 *            图片长
	 * @param h
	 *            图片宽
	 * @param opaque
	 *            图片不透明度，1.0f为原图，0.5f为半透明，0.0f为透明（完全不可见）
	 * @return 转换成的图片
	 */
	public static BufferedImage getImage(String resName, int w, int h, Float opaque) {// 20160517;
		try {
			return scaleImage(ImageIO.read(new File(resName)), w, h, opaque);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 从资源路径获取图片，可选择是否将原图转为灰色，且可将原图转为指定不透明度的图片
	 * 
	 * @param resName
	 *            资源路径
	 * @param w
	 *            图片长
	 * @param h
	 *            图片宽
	 * @param colorful
	 *            图片保持原彩色还是转换为灰色,false为灰色
	 * @param opaque
	 *            图片不透明度，1.0f为原图，0.5f为半透明，0.0f为透明（完全不可见）
	 * @return 转换成的图片
	 */
	public static BufferedImage getImage(String resName, int w, int h, boolean colorful, Float opaque) {// 20160517;
		try {
			return scaleImage(ImageIO.read(new File(resName)), w, h, colorful, opaque);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 从资源路径获取正方图片
	 * 
	 * @param resName
	 *            资源路径
	 * @param len
	 *            图片长
	 * @return 转换成的图片
	 */
	public static BufferedImage getImage(String resName, int len) {
		return getImage(resName, len, len);
	}

	/**
	 * 从资源路径获取正方图片，可选择是否将原图转为灰色
	 * 
	 * @param resName
	 *            资源路径
	 * @param len
	 *            图片长
	 * @param colorful
	 *            图片保持原彩色还是转换为灰色,false为灰色
	 * @return 转换成的图片
	 */
	public static BufferedImage getImage(String resName, int len, boolean colorful) {// 20160513;
		return getImage(resName, len, len, colorful);
	}

	/**
	 * 从资源路径获取正方图片，可将原图转为指定不透明度的图片
	 * 
	 * @param resName
	 *            资源路径
	 * @param len
	 *            图片长
	 * @param opaque
	 *            图片不透明度，1.0f为原图，0.5f为半透明，0.0f为透明（完全不可见）
	 * @return 转换成的图片
	 */
	public static BufferedImage getImage(String resName, int len, Float opaque) {// 20160517;
		return getImage(resName, len, len, opaque);
	}

	/**
	 * 从资源路径获取正方图片，可选择是否将原图转为灰色，且可将原图转为指定不透明度的图片
	 * 
	 * @param resName
	 *            资源路径
	 * @param len
	 *            图片长
	 * @param colorful
	 *            图片保持原彩色还是转换为灰色,false为灰色
	 * @param opaque
	 *            图片不透明度，1.0f为原图，0.5f为半透明，0.0f为透明（完全不可见）
	 * 
	 * @return 转换成的图片
	 */
	public static BufferedImage getImage(String resName, int len, boolean colorful, Float opaque) {// 20160517;
		return getImage(resName, len, len, colorful, opaque);
	}

	/**
	 * 根据字节数组获取图片，如果不成功则返回默认图片
	 * 
	 * @param bytes
	 *            字节数组
	 * @return 获取的图片
	 */
	public static BufferedImage getImageOrDefault(byte[] bytes) {
		BufferedImage image = null;
		if (bytes != null) {
			try {
				image = getImage(bytes);
			} catch (IOException e) {

			}
		}
		if (image == null) {
			try {
				image = ImageIO.read(new File("res/default_avatar.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (image == null) {
			throw new RuntimeException("Can't load image");
		}
		return image;
	}

	/**
	 * 对图片进行缩放
	 * 
	 * @param origin
	 *            原始图片
	 * @param width
	 *            缩放后宽
	 * @param height
	 *            缩放后高
	 * @return 缩放后图片
	 */
	public static BufferedImage scaleImage(BufferedImage origin, int width, int height) {
		BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = resized.createGraphics();
		GraphicsUtil.applyQualityRenderingHints(g);
		g.drawImage(origin, 0, 0, width, height, null);
		g.dispose();
		return resized;
	}

	/**
	 * 对图片进行缩放，可选择是否将原图转为灰色
	 * 
	 * @param origin
	 *            原始图片
	 * @param width
	 *            缩放后宽
	 * @param height
	 *            缩放后高
	 * @param colorful
	 *            图片保持原彩色还是转换为灰色,false为灰色
	 * @return 缩放后图片
	 */
	public static BufferedImage scaleImage(BufferedImage origin, int width, int height, boolean colorful) {// 20160513;
		if (colorful) {
			return scaleImage(origin, width, height);
		} else {
			BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = resized.createGraphics();
			GraphicsUtil.applyQualityRenderingHints(g);
			g.drawImage(origin, 0, 0, width, height, null);
			g.dispose();
			resized = colorToGray(resized);
			return resized;
		}
	}

	/**
	 * 对图片进行缩放，可将原图转为指定不透明度的图片
	 * 
	 * @param origin
	 *            原始图片
	 * @param width
	 *            缩放后宽
	 * @param height
	 *            缩放后高
	 * @param opaque
	 *            图片不透明度，1.0f为原图，0.5f为半透明，0.0f为透明（完全不可见）
	 * @return 缩放后图片
	 */
	public static BufferedImage scaleImage(BufferedImage origin, int width, int height, Float opaque) {// 20160517;
		if (opaque == 1.0f) {
			return scaleImage(origin, width, height);
		} else {
			BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = resized.createGraphics();
			GraphicsUtil.applyQualityRenderingHints(g);
			AlphaComposite alpha = AlphaComposite.SrcOver.derive(opaque);
			g.setComposite(alpha);// 指定AlphaComposite对象；
			g.drawImage(origin, 0, 0, width, height, null);
			g.dispose();
			return resized;
		}
	}

	/**
	 * 对图片进行缩放，可选择是否将原图转为灰色，且可将原图转为指定不透明度的图片
	 * 
	 * @param origin
	 *            原始图片
	 * @param width
	 *            缩放后宽
	 * @param height
	 *            缩放后高
	 * @param colorful
	 *            图片保持原彩色还是转换为灰色,false为灰色
	 * @param opaque
	 *            图片不透明度，1.0f为原图，0.5f为半透明，0.0f为透明（完全不可见）
	 * @return 缩放后图片
	 */
	public static BufferedImage scaleImage(BufferedImage origin, int width, int height, boolean colorful,
			Float opaque) {// 20160517;
		if (opaque == 1.0f) {
			return scaleImage(origin, width, height, colorful);
		} else if (colorful && opaque != 1.0f) {
			return scaleImage(origin, width, height, opaque);
		} else {
			BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = resized.createGraphics();
			GraphicsUtil.applyQualityRenderingHints(g);
			AlphaComposite alpha = AlphaComposite.SrcOver.derive(opaque);
			g.setComposite(alpha);// 指定AlphaComposite对象；
			g.drawImage(origin, 0, 0, width, height, null);
			g.dispose();
			resized = colorToGray(resized);
			return resized;
		}
	}

	/**
	 * 获取用户头像
	 * 
	 * @param user
	 *            用户
	 * @param len
	 *            图片长
	 * @return 用户头像
	 */
	public static BufferedImage getUserImage(User user, int len) {
		BufferedImage avatar = getImageOrDefault(user.getAvatar());
		BufferedImage resized = new BufferedImage(len, len, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = resized.createGraphics();
		GraphicsUtil.applyQualityRenderingHints(g);
		g.setClip(new RoundRectangle2D.Double(0, 0, len, len, StyleConsts.corner_rad, StyleConsts.corner_rad));
		g.drawImage(avatar, 0, 0, len, len, null);
		g.dispose();
		return resized;
	}

	/**
	 * 获取用户头像，可选择是否将原图转为灰色
	 * 
	 * @param user
	 *            用户
	 * @param len
	 *            图片长
	 * @param colorful
	 *            图片保持原彩色还是转换为灰色,false为灰色
	 * @return 用户头像
	 */
	public BufferedImage getUserImage(User user, int len, boolean colorful) {// 20160513;
		if (colorful) {
			return getUserImage(user, len);
		} else {
			BufferedImage avatar = getImageOrDefault(user.getAvatar());
			BufferedImage resized = new BufferedImage(len, len, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = resized.createGraphics();
			GraphicsUtil.applyQualityRenderingHints(g);
			g.setClip(new RoundRectangle2D.Double(0, 0, len, len, StyleConsts.corner_rad, StyleConsts.corner_rad));
			g.drawImage(avatar, 0, 0, len, len, null);
			g.dispose();
			resized = colorToGray(resized);
			return resized;
		}
	}

	/**
	 * 获取群组图片
	 * 
	 * @param group
	 *            群组
	 * @param len
	 *            图片长
	 * @return 群组图片
	 */
	public static BufferedImage getGroupImage(Group group, int len) {
		int thick = 1;
		int[][][] poses = new int[4][][];
		poses[0] = new int[][] { { thick, thick } };
		poses[1] = new int[][] { { thick, len / 4 + thick }, { len / 2 + thick, len / 4 + thick } };
		poses[2] = new int[][] { { len / 4 + thick, thick }, { thick, len / 2 + thick },
				{ len / 2 + thick, len / 2 + thick } };
		poses[3] = new int[][] { { thick, thick }, { len / 2 + thick, thick }, { thick, len / 2 + thick },
				{ len / 2 + thick, len / 2 + thick } };
		int[] lens = new int[] { len - thick * 2, len / 2 - thick * 2, len / 2 - thick * 2, len / 2 - thick * 2 };
		int num = group.getMembers().size();
		if (num > 4) {
			num = 4;
		}
		ArrayList<Member> members = new ArrayList<>(group.getMembers());
		BufferedImage groupImg = new BufferedImage(len, len, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = groupImg.createGraphics();
		GraphicsUtil.applyQualityRenderingHints(g);
		g.setClip(new RoundRectangle2D.Double(0, 0, len, len, StyleConsts.corner_rad, StyleConsts.corner_rad));
		for (int i = 0; i < num; i++) {
			Member member = members.get(i);
			BufferedImage avatar = getImageOrDefault(member.getAvatar());
			int x = poses[num - 1][i][0];
			int y = poses[num - 1][i][1];
			int l = lens[num - 1];
			g.drawImage(avatar, x, y, l, l, null);
		}
		g.dispose();
		return groupImg;
	}

	/**
	 * 获取带有数量提示的图片，例如用户头像右上方的未读消息数
	 * 
	 * @param origin
	 *            原始图片
	 * @param count
	 *            数量
	 * @return 带有数量提示的图片
	 */
	public static BufferedImage notifiedImage(BufferedImage origin, int count) {
		int notLen = 16;
		int h = origin.getHeight() + notLen / 2;
		int w = origin.getWidth() + notLen / 2;
		BufferedImage resized = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = resized.createGraphics();
		GraphicsUtil.applyQualityRenderingHints(g);
		g.setClip(new RoundRectangle2D.Double(notLen / 4, notLen / 4, w - notLen / 2, h - notLen / 2,
				StyleConsts.corner_rad, StyleConsts.corner_rad));
		g.drawImage(origin, notLen / 4, notLen / 4, w - notLen / 2, h - notLen / 2, null);
		g.setClip(null);
		g.setColor(Color.RED);
		if (count > 0) {
			g.fillOval(w - notLen, 0, notLen, notLen);
			String str = "" + count;
			int span = g.getFontMetrics().stringWidth(str.substring(1));
			g.fillOval(w - notLen - span, 0, notLen, notLen);
			g.fillRect(w - span - notLen / 2, 0, span, notLen);
			g.setFont(g.getFont().deriveFont(Font.BOLD));
			g.setColor(Color.WHITE);
			g.drawString(str, w - notLen / 2 - span / 2 - g.getFontMetrics().stringWidth(str) / 2,
					notLen / 2 + (g.getFontMetrics().getAscent() - g.getFontMetrics().getDescent()) / 2);
		}
		g.dispose();
		return resized;
	}

	/**
	 * 根据图片返回对应的字节数组
	 * 
	 * @param image
	 *            图片
	 * @return 字节数组
	 */
	public static byte[] getBinary(BufferedImage image) {
		ByteArrayOutputStream output = new ByteArrayOutputStream(4096);
		try {
			ImageIO.write(image, "jpg", output);
			return output.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage limitWidth(BufferedImage origin, int limit) {
		int h = origin.getHeight();
		int w = origin.getWidth();
		if (w > limit) {
			h = h * limit / w;
			w = limit;
		}
		return scaleImage(origin, w, h);
	}

	public static BufferedImage colorToGray(BufferedImage image) {// 20160513;
		ColorSpace colorSpace1 = ColorSpace.getInstance(ColorSpace.CS_GRAY);// 创建内置线性为灰度的颜色空间；
		ColorSpace colorSpace2 = ColorSpace.getInstance(ColorSpace.CS_LINEAR_RGB);// 创建内置线性为RGB的颜色空间；
		ColorConvertOp op = new ColorConvertOp(colorSpace1, colorSpace2, null);// 创建进行颜色转换的对象；
		image = op.filter(image, null);// 对缓冲图像进行颜色转换；
		return image;
	}
}
