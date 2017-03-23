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
 * ��������ͼƬ�����ʵ�÷���
 */
public class ImageUtil {
	/**
	 * ���ֽ������ȡͼƬ
	 * 
	 * @param bytes
	 *            �ֽ�����
	 * @return ת���ɵ�ͼƬ�����ɹ���Ϊnull
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
	 * ����Դ·����ȡͼƬ
	 * 
	 * @param resName
	 *            ��Դ·��
	 * @param w
	 *            ͼƬ��
	 * @param h
	 *            ͼƬ��
	 * @return ת���ɵ�ͼƬ
	 */
	public static BufferedImage getImage(String resName, int w, int h) {
		try {
			return scaleImage(ImageIO.read(new File(resName)), w, h);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ����Դ·����ȡͼƬ����ѡ���Ƿ�ԭͼתΪ��ɫ
	 * 
	 * @param resName
	 *            ��Դ·��
	 * @param w
	 *            ͼƬ��
	 * @param h
	 *            ͼƬ��
	 * @param colorful
	 *            ͼƬ����ԭ��ɫ����ת��Ϊ��ɫ,falseΪ��ɫ
	 * @return ת���ɵ�ͼƬ
	 */
	public static BufferedImage getImage(String resName, int w, int h, boolean colorful) {// 20160513;
		try {
			return scaleImage(ImageIO.read(new File(resName)), w, h, colorful);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ����Դ·����ȡͼƬ���ɽ�ԭͼתΪָ����͸���ȵ�ͼƬ
	 * 
	 * @param resName
	 *            ��Դ·��
	 * @param w
	 *            ͼƬ��
	 * @param h
	 *            ͼƬ��
	 * @param opaque
	 *            ͼƬ��͸���ȣ�1.0fΪԭͼ��0.5fΪ��͸����0.0fΪ͸������ȫ���ɼ���
	 * @return ת���ɵ�ͼƬ
	 */
	public static BufferedImage getImage(String resName, int w, int h, Float opaque) {// 20160517;
		try {
			return scaleImage(ImageIO.read(new File(resName)), w, h, opaque);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ����Դ·����ȡͼƬ����ѡ���Ƿ�ԭͼתΪ��ɫ���ҿɽ�ԭͼתΪָ����͸���ȵ�ͼƬ
	 * 
	 * @param resName
	 *            ��Դ·��
	 * @param w
	 *            ͼƬ��
	 * @param h
	 *            ͼƬ��
	 * @param colorful
	 *            ͼƬ����ԭ��ɫ����ת��Ϊ��ɫ,falseΪ��ɫ
	 * @param opaque
	 *            ͼƬ��͸���ȣ�1.0fΪԭͼ��0.5fΪ��͸����0.0fΪ͸������ȫ���ɼ���
	 * @return ת���ɵ�ͼƬ
	 */
	public static BufferedImage getImage(String resName, int w, int h, boolean colorful, Float opaque) {// 20160517;
		try {
			return scaleImage(ImageIO.read(new File(resName)), w, h, colorful, opaque);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ����Դ·����ȡ����ͼƬ
	 * 
	 * @param resName
	 *            ��Դ·��
	 * @param len
	 *            ͼƬ��
	 * @return ת���ɵ�ͼƬ
	 */
	public static BufferedImage getImage(String resName, int len) {
		return getImage(resName, len, len);
	}

	/**
	 * ����Դ·����ȡ����ͼƬ����ѡ���Ƿ�ԭͼתΪ��ɫ
	 * 
	 * @param resName
	 *            ��Դ·��
	 * @param len
	 *            ͼƬ��
	 * @param colorful
	 *            ͼƬ����ԭ��ɫ����ת��Ϊ��ɫ,falseΪ��ɫ
	 * @return ת���ɵ�ͼƬ
	 */
	public static BufferedImage getImage(String resName, int len, boolean colorful) {// 20160513;
		return getImage(resName, len, len, colorful);
	}

	/**
	 * ����Դ·����ȡ����ͼƬ���ɽ�ԭͼתΪָ����͸���ȵ�ͼƬ
	 * 
	 * @param resName
	 *            ��Դ·��
	 * @param len
	 *            ͼƬ��
	 * @param opaque
	 *            ͼƬ��͸���ȣ�1.0fΪԭͼ��0.5fΪ��͸����0.0fΪ͸������ȫ���ɼ���
	 * @return ת���ɵ�ͼƬ
	 */
	public static BufferedImage getImage(String resName, int len, Float opaque) {// 20160517;
		return getImage(resName, len, len, opaque);
	}

	/**
	 * ����Դ·����ȡ����ͼƬ����ѡ���Ƿ�ԭͼתΪ��ɫ���ҿɽ�ԭͼתΪָ����͸���ȵ�ͼƬ
	 * 
	 * @param resName
	 *            ��Դ·��
	 * @param len
	 *            ͼƬ��
	 * @param colorful
	 *            ͼƬ����ԭ��ɫ����ת��Ϊ��ɫ,falseΪ��ɫ
	 * @param opaque
	 *            ͼƬ��͸���ȣ�1.0fΪԭͼ��0.5fΪ��͸����0.0fΪ͸������ȫ���ɼ���
	 * 
	 * @return ת���ɵ�ͼƬ
	 */
	public static BufferedImage getImage(String resName, int len, boolean colorful, Float opaque) {// 20160517;
		return getImage(resName, len, len, colorful, opaque);
	}

	/**
	 * �����ֽ������ȡͼƬ��������ɹ��򷵻�Ĭ��ͼƬ
	 * 
	 * @param bytes
	 *            �ֽ�����
	 * @return ��ȡ��ͼƬ
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
	 * ��ͼƬ��������
	 * 
	 * @param origin
	 *            ԭʼͼƬ
	 * @param width
	 *            ���ź��
	 * @param height
	 *            ���ź��
	 * @return ���ź�ͼƬ
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
	 * ��ͼƬ�������ţ���ѡ���Ƿ�ԭͼתΪ��ɫ
	 * 
	 * @param origin
	 *            ԭʼͼƬ
	 * @param width
	 *            ���ź��
	 * @param height
	 *            ���ź��
	 * @param colorful
	 *            ͼƬ����ԭ��ɫ����ת��Ϊ��ɫ,falseΪ��ɫ
	 * @return ���ź�ͼƬ
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
	 * ��ͼƬ�������ţ��ɽ�ԭͼתΪָ����͸���ȵ�ͼƬ
	 * 
	 * @param origin
	 *            ԭʼͼƬ
	 * @param width
	 *            ���ź��
	 * @param height
	 *            ���ź��
	 * @param opaque
	 *            ͼƬ��͸���ȣ�1.0fΪԭͼ��0.5fΪ��͸����0.0fΪ͸������ȫ���ɼ���
	 * @return ���ź�ͼƬ
	 */
	public static BufferedImage scaleImage(BufferedImage origin, int width, int height, Float opaque) {// 20160517;
		if (opaque == 1.0f) {
			return scaleImage(origin, width, height);
		} else {
			BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = resized.createGraphics();
			GraphicsUtil.applyQualityRenderingHints(g);
			AlphaComposite alpha = AlphaComposite.SrcOver.derive(opaque);
			g.setComposite(alpha);// ָ��AlphaComposite����
			g.drawImage(origin, 0, 0, width, height, null);
			g.dispose();
			return resized;
		}
	}

	/**
	 * ��ͼƬ�������ţ���ѡ���Ƿ�ԭͼתΪ��ɫ���ҿɽ�ԭͼתΪָ����͸���ȵ�ͼƬ
	 * 
	 * @param origin
	 *            ԭʼͼƬ
	 * @param width
	 *            ���ź��
	 * @param height
	 *            ���ź��
	 * @param colorful
	 *            ͼƬ����ԭ��ɫ����ת��Ϊ��ɫ,falseΪ��ɫ
	 * @param opaque
	 *            ͼƬ��͸���ȣ�1.0fΪԭͼ��0.5fΪ��͸����0.0fΪ͸������ȫ���ɼ���
	 * @return ���ź�ͼƬ
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
			g.setComposite(alpha);// ָ��AlphaComposite����
			g.drawImage(origin, 0, 0, width, height, null);
			g.dispose();
			resized = colorToGray(resized);
			return resized;
		}
	}

	/**
	 * ��ȡ�û�ͷ��
	 * 
	 * @param user
	 *            �û�
	 * @param len
	 *            ͼƬ��
	 * @return �û�ͷ��
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
	 * ��ȡ�û�ͷ�񣬿�ѡ���Ƿ�ԭͼתΪ��ɫ
	 * 
	 * @param user
	 *            �û�
	 * @param len
	 *            ͼƬ��
	 * @param colorful
	 *            ͼƬ����ԭ��ɫ����ת��Ϊ��ɫ,falseΪ��ɫ
	 * @return �û�ͷ��
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
	 * ��ȡȺ��ͼƬ
	 * 
	 * @param group
	 *            Ⱥ��
	 * @param len
	 *            ͼƬ��
	 * @return Ⱥ��ͼƬ
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
	 * ��ȡ����������ʾ��ͼƬ�������û�ͷ�����Ϸ���δ����Ϣ��
	 * 
	 * @param origin
	 *            ԭʼͼƬ
	 * @param count
	 *            ����
	 * @return ����������ʾ��ͼƬ
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
	 * ����ͼƬ���ض�Ӧ���ֽ�����
	 * 
	 * @param image
	 *            ͼƬ
	 * @return �ֽ�����
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
		ColorSpace colorSpace1 = ColorSpace.getInstance(ColorSpace.CS_GRAY);// ������������Ϊ�Ҷȵ���ɫ�ռ䣻
		ColorSpace colorSpace2 = ColorSpace.getInstance(ColorSpace.CS_LINEAR_RGB);// ������������ΪRGB����ɫ�ռ䣻
		ColorConvertOp op = new ColorConvertOp(colorSpace1, colorSpace2, null);// ����������ɫת���Ķ���
		image = op.filter(image, null);// �Ի���ͼ�������ɫת����
		return image;
	}
}
