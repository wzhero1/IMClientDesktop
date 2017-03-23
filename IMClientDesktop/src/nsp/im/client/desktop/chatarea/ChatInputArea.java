package nsp.im.client.desktop.chatarea;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import nsp.im.client.desktop.base.ButtonStyleMouseAdapter;
import nsp.im.client.desktop.base.ExprTextPane;
import nsp.im.client.desktop.base.Expression;
import nsp.im.client.desktop.base.RoundButton;
import nsp.im.client.desktop.base.RoundDialog;
import nsp.im.client.desktop.base.ScrollDecorator;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.desktop.base.TextRoundPopup;
import nsp.im.client.desktop.chatarea.ExprPopup.ExprSelListener;
import nsp.im.client.desktop.utils.ImageUtil;
import nsp.im.client.model.Chat;
import nsp.im.client.model.ex.IMException;
import nsp.im.client.model.msg.MessageBody;

@SuppressWarnings("serial")
public class ChatInputArea extends JPanel {
	private JPanel toolBar;
	private ExprTextPane textArea;
	private ScrollDecorator scrollTextArea;
	private JButton sendBtn;
	private JPanel sendPanel;
	private JLabel fileBtn;
	private JLabel exprBtn;
	private JComboBox<String> algoBox;
	private Chat conversation;
	private ExprPopup exprPopup;

	public ChatInputArea() {
		layComponents();
		setListeners();
		setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
	}

	public void start() {
		// for (String s : server.getProtectorManager().listProtectors()) {
		// algoBox.addItem(s);
		// }
	}

	public void setConversation(Chat conversation) {
		this.conversation = conversation;
	}

	/**
	 * @���ߣ�lll
	 * @ʱ�䣺2016��4��1������4:15:30 @��Ҫ���ܣ�����������Ϣ����ز�����ƣ� @�޸����ݣ�
	 */
	private void layComponents() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;// HORIZONTAL;
		toolBar = new JPanel();
		add(toolBar, c);// *****��ӡ�1-toolBar����

		c.gridy = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		textArea = new ExprTextPane();
		textArea.setFont(textArea.getFont().deriveFont(16f));
		//�������������Ҽ�ճ��ѡ��
        TextRoundPopup textMenu = new TextRoundPopup();
		textMenu.addItem(" ճ�� ", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.paste();
			}

		});
        textArea.setComponentPopupMenu(textMenu);

		scrollTextArea = new ScrollDecorator(textArea);
		scrollTextArea.setBorder(null);
		add(ScrollDecorator.makeTranslucentBarDecorator(scrollTextArea), c);// *****��ӡ�2-scrollTextArea����

		c.gridy = 2;
		c.weighty = 0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.EAST;
		sendPanel = new JPanel();
		sendPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		sendBtn = RoundButton.createNormalButton();
		sendBtn.setText("����(S)");
		sendBtn.setToolTipText("��Enter��������Ϣ����Ctrl+Enter������");// 20160518;
		sendBtn.setFont(new Font("����", Font.BOLD, 14));
		sendPanel.add(sendBtn);
		add(sendPanel, c);// *****��ӡ�3-sendPanel����

		exprBtn = new JLabel();
		ButtonStyleMouseAdapter.attach(exprBtn, new ButtonStyleMouseAdapter() {
			@Override
			public void onRestore() {
				exprBtn.setIcon(new ImageIcon(ImageUtil.getImage("res/chat/expr_g.png", 20)));// 30;
			}

			@Override
			public void onHover() {
				exprBtn.setIcon(new ImageIcon(ImageUtil.getImage("res/chat/expr_c.png", 20)));
			}

			@Override
			public void onAct() {
				exprBtn.setIcon(new ImageIcon(ImageUtil.getImage("res/chat/expr_c.png", 20)));
			}
		});
		exprBtn.setToolTipText("��ӱ���");
		fileBtn = new JLabel();
		ButtonStyleMouseAdapter.attach(fileBtn, new ButtonStyleMouseAdapter() {
			@Override
			public void onRestore() {
				fileBtn.setIcon(new ImageIcon(ImageUtil.getImage("res/chat/file_g.png", 20)));
			}

			@Override
			public void onHover() {
				fileBtn.setIcon(new ImageIcon(ImageUtil.getImage("res/chat/file_c.png", 20)));
			}

			@Override
			public void onAct() {
				fileBtn.setIcon(new ImageIcon(ImageUtil.getImage("res/chat/file_c.png", 20)));
			}
		});
		fileBtn.setToolTipText("�����ļ�");
		algoBox = new JComboBox<>();
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(exprBtn);
		toolBar.add(fileBtn);
		// toolBar.add(algoBox);
		algoBox.addItem("������ͼƬ");

		textArea.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent arg0) {
				setBack(StyleConsts.main_back);
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				setBack(StyleConsts.text_back);
			}
		});
		scrollTextArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textArea.requestFocusInWindow();
			}
		});

		exprPopup = new ExprPopup();
		setBack(StyleConsts.main_back);
	}

	private void setListeners() {
		sendBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sentText();
			}
		});

		exprBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getButton() != MouseEvent.BUTTON1)
					return;
				// int exprHeight = exprPopup.getHeight();// *****������ĸ߶ȣ�
				exprPopup.show(ChatInputArea.this, 0, -80);// (ChatInputArea.this,
															// 0, -exprHeight);
			}
		});
		fileBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int c = chooser.showOpenDialog(ChatInputArea.this);
				File file = chooser.getSelectedFile();
				if (file == null || c != JFileChooser.APPROVE_OPTION)
					return;
				MessageBody<?> msg = null;
				String name = file.getName();
				if (file.length() > 60 * 1024 * 1024) {
					RoundDialog.showMsg("�ļ�����", "�ļ�����60M");
					return;
				}
				// 20160518;
				if (name.toLowerCase().endsWith("jpg")
						|| name.toLowerCase().endsWith("jpeg")
						|| name.toLowerCase().endsWith("png")
						|| name.toLowerCase().endsWith("bmp")
						|| name.toLowerCase().endsWith("gif")) {
					conversation.sendImage(file).onFailure(IMException.class,
							(ex) -> {
								ex.printStackTrace();
								RoundDialog.showMsg("����ʧ��", "����ʧ��");
							});
				} else {
					conversation.sendFile(file).onFailure(IMException.class,
							(ex) -> {
								ex.printStackTrace();
								RoundDialog.showMsg("����ʧ��", "����ʧ��");
							});
				}
			}
		});
		algoBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String s = (String) algoBox.getSelectedItem();
				if (s.equals("������ͼƬ")) {
				} else {
					// protector =
					// server.getProtectorManager().loadProtector(s);
				}
			}
		});
		exprPopup.setExprSelListener(new ExprSelListener() {
			@Override
			public void onSelExpr(Expression e) {
				textArea.insertExpr(e);
			}
		});
		textArea.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "donothing");
		textArea.getActionMap().put("donothing", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		textArea.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					if (e.isShiftDown()) {// (e.isControlDown())
						textArea.replaceSelection("\n");
						return ;
					}
					sentText();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				super.keyReleased(e);
			}
		});
	}

	private void sentText() {
		String text = textArea.getText();
		if (text.length() == 0)
			return;
		if (text.length() > 1024) {
			RoundDialog.showMsg("���ֹ���", "����1000��");
			return;
		}
		conversation.sendText(text).onFailure(IMException.class, (e) -> {
			RoundDialog.showMsg("����ʧ��", "����ʧ��");
		});
		textArea.setText("");
	}

	private void setBack(Color color) {
		setBackground(color);
		textArea.setBackground(color);
		toolBar.setBackground(color);
		sendPanel.setBackground(color);
		scrollTextArea.setBackground(color);
	}

	public void requireChatFocus() {
		textArea.requestFocusInWindow();
	}
}