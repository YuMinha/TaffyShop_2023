package GUI;

import RunSim.TaffyShop;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class selectCounter {
    private JFrame countFr;
    boolean iscall = false;

    public selectCounter() {
        countFr = new JFrame();
        Container cp = countFr.getContentPane();
        cp.setBackground(new Color(234, 146, 148));
        countFr.setSize(900, 520);
        countFr.setLocationRelativeTo(null);
        countFr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cp.setLayout(null);

        JLabel laTitle = new JLabel("Taffy 상점 시뮬레이터");
        laTitle.setForeground(new Color(255, 255, 255));
        laTitle.setFont(new Font("Arial Rounded MT 굵게", Font.BOLD, 20));
        laTitle.setBounds(10, 10, 300, 50);
        laTitle.setHorizontalAlignment(SwingConstants.CENTER);
        cp.add(laTitle);

        JLabel selectNum = new JLabel("    창구의 개수를 지정하세요");
        selectNum.setForeground(new Color(255, 137, 137));
        selectNum.setOpaque(true);
        selectNum.setBackground(Color.WHITE);
        selectNum.setHorizontalAlignment(SwingConstants.LEFT);
        selectNum.setFont(new Font("Arial Rounded MT 굵게", Font.BOLD, 30));
        selectNum.setBounds(0, 70, 900, 50);
        cp.add(selectNum);

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(2, 2));
        btnPanel.setBounds(0, 140, 900, 333);
        cp.add(btnPanel);

        JButton btn1 = new JButton("1개");
        btn1.setFont(new Font("굴림 보통", Font.BOLD, 20));
        btn1.setBackground(Color.WHITE);
        btn1.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        btn1.setFocusPainted(false);
        btn1.addActionListener(new SwingAction());
        btnPanel.add(btn1);

        JButton btn2 = new JButton("2개");
        btn2.setFont(new Font("굴림 보통", Font.BOLD, 20));
        btn2.setBackground(Color.WHITE);
        btn2.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        btn2.setFocusPainted(false);
        btn2.addActionListener(new SwingAction());
        btnPanel.add(btn2);

        JButton btn3 = new JButton("3개");
        btn3.setFont(new Font("굴림 보통", Font.BOLD, 20));
        btn3.setBackground(Color.WHITE);
        btn3.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        btn3.setFocusPainted(false);
        btn3.addActionListener(new SwingAction());
        btnPanel.add(btn3);

        JButton btn4 = new JButton("4개");
        btn4.setFont(new Font("굴림 보통", Font.BOLD, 20));
        btn4.setBackground(Color.WHITE);
        btn4.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        btn4.setFocusPainted(false);
        btn4.addActionListener(new SwingAction());
        btnPanel.add(btn4);

        JCheckBox chbox = new JCheckBox("전화 기능");
        chbox.setFont(new Font("굴림 보통", Font.BOLD, 20));
        chbox.setBounds(480, 70, 210, 50);
        chbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    iscall = true;
                } else {
                    iscall = false;
                }
            }
        });
        cp.add(chbox);
        countFr.setVisible(true);
    }

    private class SwingAction implements ActionListener {//창구의 개수 선택 액션

        @Override
        public void actionPerformed(ActionEvent e) {
            countFr.setVisible(false);
            int CounterNum = 0;
            if (e.getActionCommand() == "1개") {
                CounterNum = 1;
            } else if (e.getActionCommand() == "2개") {
                CounterNum = 2;
            } else if (e.getActionCommand() == "3개") {
                CounterNum = 3;
            } else if (e.getActionCommand() == "4개") {
                CounterNum = 4;
            }

            TaffyShop shop = new TaffyShop(CounterNum, iscall);
            shop.Start();

        }
    }
}
