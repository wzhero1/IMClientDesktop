package nsp.im.client.desktop.base;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import nsp.im.client.desktop.utils.GridBagUtil;
import nsp.im.client.desktop.utils.ImageUtil;

public class TitleBar extends RoundPanel {
	private static final long serialVersionUID = 8765643515924087648L;
	private JLabel minBtn; // 最小化按钮
	private JLabel maxBtn; // 最大化按钮
	private JLabel clsBtn; // 关闭按钮
	private RoundPanel btnPanel; // 按钮组
	private RoundPanel title;
	private boolean dragging;
	private Point origin;
	private Window window;

	/**
	 * 构建一个绑定到对话框的标题栏
	 * 
	 * @param dialog
	 *            要绑定的对话框
	 */
	public TitleBar(JDialog dialog) {
		this(false, false);
		window = dialog;
	}

	/**
	 * 构建一个绑定到窗口的标题栏
	 * 
	 * @param frame
	 *            要绑定的窗口
	 * @param resizable
	 *            是否可以最大化
	 */
	public TitleBar(final JFrame frame, boolean resizable) {
		this(resizable, true);
		window = frame;
		minBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setExtendedState(JFrame.ICONIFIED);
			}

			@Override
			public void mouseReleased(MouseEvent e) {// 事件触发后将对应标签改为恢复状态；20160518;
				minBtn.setBackground(StyleConsts.main_back);
			}
		});
		maxBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (frame.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
					frame.setExtendedState(Frame.NORMAL);
					maxBtn.setIcon(new ImageIcon(ImageUtil.getImage("res/title/maxbtn.png", 24, 27)));
				} else {
					frame.setExtendedState(Frame.MAXIMIZED_BOTH);
					maxBtn.setIcon(new ImageIcon(ImageUtil.getImage("res/title/nmbtn.png", 24, 27)));
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {// 事件触发后将对应标签改为恢复状态；20160518;
				maxBtn.setBackground(StyleConsts.main_back);
			}
		});
	}

	/**
	 * 构建一个标题栏绑定到一个窗口，拥有最大化按钮
	 * 
	 * @param frame
	 *            要绑定的窗口
	 */
	public TitleBar(JFrame frame) {
		this(frame, true);
	}

	private TitleBar(boolean hasMax, boolean hasMin) {
		setBackground(StyleConsts.main_back);
		// 设置右侧按钮组
		minBtn = new JLabel(new ImageIcon(ImageUtil.getImage("res/title/minbtn.png", 24, 27)));
		minBtn.setOpaque(true);
		maxBtn = new JLabel(new ImageIcon(ImageUtil.getImage("res/title/maxbtn.png", 24, 27)));
		maxBtn.setOpaque(true);
		clsBtn = new JLabel();
		clsBtn.setOpaque(true);
		btnPanel = new RoundPanel();
		btnPanel.setBackground(StyleConsts.main_back);
		FlowLayout lo = new FlowLayout(FlowLayout.TRAILING);
		btnPanel.setLayout(lo);
		if (hasMin)
			btnPanel.add(minBtn);
		if (hasMax)
			btnPanel.add(maxBtn);
		btnPanel.add(clsBtn);

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		c.weighty = 1;
		GridBagUtil.setRectangle(c, 1, 0, 1, 1);
		add(btnPanel, c);

		// 设置标题容器
		title = new RoundPanel();
		title.setLayout(new BorderLayout());
		title.setOpaque(false);
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(8, 5, 5, 5);
		c.weightx = 1;
		GridBagUtil.setRectangle(c, 0, 0, 1, 1);
		add(title, c);

		// 设置各个按钮的鼠标交互样式
		ButtonStyleMouseAdapter.attach(minBtn, new ButtonStyleMouseAdapter() {
			@Override
			public void onRestore() {
				minBtn.setBackground(StyleConsts.main_back);
			}

			@Override
			public void onHover() {
				minBtn.setBackground(StyleConsts.listview_hov);
			}

			@Override
			public void onAct() {
				minBtn.setBackground(StyleConsts.listview_sel);
			}
		});
		ButtonStyleMouseAdapter.attach(maxBtn, new ButtonStyleMouseAdapter() {
			@Override
			public void onRestore() {
				maxBtn.setBackground(StyleConsts.main_back);
			}

			@Override
			public void onHover() {
				maxBtn.setBackground(StyleConsts.listview_hov);
			}

			@Override
			public void onAct() {
				maxBtn.setBackground(StyleConsts.listview_sel);
			}
		});

		ButtonStyleMouseAdapter.attach(clsBtn, new ButtonStyleMouseAdapter() {
			@Override
			public void onRestore() {
				clsBtn.setBackground(StyleConsts.main_back);
				clsBtn.setIcon(new ImageIcon(ImageUtil.getImage("res/title/clsbtn.png", 24, 27)));
			}

			@Override
			public void onHover() {
				clsBtn.setBackground(StyleConsts.close_hov);
				clsBtn.setIcon(new ImageIcon(ImageUtil.getImage("res/title/clsbtn_r.png", 24, 27)));
			}

			@Override
			public void onAct() {
				clsBtn.setBackground(StyleConsts.close_sel);
			}
		});

		clsBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
				//System.exit(0);
			}

			@Override
			public void mouseReleased(MouseEvent e) {// 事件触发后将对应标签改为恢复状态；20160518;
				clsBtn.setBackground(StyleConsts.main_back);
				clsBtn.setIcon(new ImageIcon(ImageUtil.getImage("res/title/clsbtn.png", 24, 27)));
			}
		});

		// 设置拖拽移动窗口行为，保持鼠标和窗口左上角的距离不变，从而移动到正确位置
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// 当最大化时，不应该移动窗口
				if (window instanceof JFrame) {
					JFrame frame = (JFrame) window;
					if ((frame.getExtendedState() & JFrame.MAXIMIZED_BOTH) != 0)
						return;
				}
				dragging = true;
				if (origin == null) {
					origin = new Point();
				}
				origin.x = e.getX();
				origin.y = e.getY();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				dragging = false;
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (dragging) {
					Point p = window.getLocation();
					window.setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
				}
			}
		});

		// 让标题容器的事件转发到标题栏，正确实现拖拽
		title.addMouseListener(new ForwardMouseAdapter());
		title.addMouseMotionListener(new ForwardMouseAdapter());

	}

	/**
	 * 设置标题内容
	 * 
	 * @param c
	 *            成为标题的组件
	 * @param centering
	 *            是否添加到居中位置
	 */
	public void setTitle(JComponent c, boolean centering) {
		title.removeAll();
		// 设置和按钮组对称的容器，使得c能够正确居中
		if (centering) {
			RoundPanel filler = new RoundPanel();
			filler.setOpaque(false);
			filler.setPreferredSize(btnPanel.getPreferredSize());
			title.add(filler, BorderLayout.WEST);
		}
		title.add(c, BorderLayout.CENTER);
		// 让标题内容的事件转发到标题容器，正确实现拖拽
		c.addMouseListener(new ForwardMouseAdapter());
		c.addMouseMotionListener(new ForwardMouseAdapter());
		validate();
		repaint();
	}
}
