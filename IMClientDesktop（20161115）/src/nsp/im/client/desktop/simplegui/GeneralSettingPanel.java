package nsp.im.client.desktop.simplegui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

import nsp.im.client.desktop.base.RoundButton;
import nsp.im.client.desktop.base.RoundPanel;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.desktop.utils.Globals;


@SuppressWarnings("serial")
public class GeneralSettingPanel extends RoundPanel {

    private JTextField filePath;
    private static String localFilePath ;


    public GeneralSettingPanel() {
        setBackground(StyleConsts.main_back);
        layComponents();
    }

    private void layComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.weightx = 12;
        JLabel blankLabel = new JLabel();
        blankLabel.setText("");
        add(blankLabel, constraints);

        constraints.gridx = 1;
        constraints.weightx = 6;
        String text[] = {"�ļ�����"};
        JLabel fManageLabel = new JLabel();
        fManageLabel.setFont(fManageLabel.getFont().deriveFont(18f));
        fManageLabel.setText(text[0]);
        add(fManageLabel, constraints);

        constraints.gridx = 2;
        constraints.weightx = 1;
        filePath = new JTextField();
        filePath.setColumns(20);
        add(filePath, constraints);


        constraints.gridx = 3;
        constraints.weightx = 8;
        constraints.insets = new Insets(0,-40,0,0);
        constraints.anchor = GridBagConstraints.WEST;
        JButton choosePath = RoundButton.createNormalButton();
        choosePath.setText("����");
        choosePath.setToolTipText("��Enter��������Ϣ����Ctrl+Enter������");// 20160518;
        choosePath.setFont(new Font("����", Font.BOLD, 14));
        choosePath.setPreferredSize(new Dimension(70, 25));//���ð�ť��С
        add(choosePath, constraints);

        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.insets = new Insets(20,0,0,0);
        JButton clearLog = RoundButton.createNormalButton();
        clearLog.setText("��������¼");
        clearLog.setToolTipText("��Enter��������Ϣ����Ctrl+Enter������");// 20160518;
        clearLog.setFont(new Font("����", Font.BOLD, 14));
        add(clearLog, constraints);


        clearLog.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	Globals.getAccount().getChatList().clearAll();
            }
        });

        choosePath.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//����ֻ��ѡ���ļ���
                chooser.setMultiSelectionEnabled(false);//���ò��ܶ�ѡ
                chooser.setFileHidingEnabled(true);//�����Ƿ������ļ�
                int state = chooser.showOpenDialog(null);//����һ�� "Open File" �ļ�ѡ�����Ի���
                try{
                    if(state == chooser.CANCEL_OPTION)
                        return;
                    else if(state == chooser.APPROVE_OPTION){
                        File file = chooser.getSelectedFile();
                        localFilePath = file.getAbsolutePath();
                    }
                } catch (HeadlessException e1){
                    e1.printStackTrace();
                }
                filePath.setText(localFilePath);
            }
        });


    }

    public void start() {
    }
    public static String getLocalFilePath(){
        return localFilePath;
    }
}
