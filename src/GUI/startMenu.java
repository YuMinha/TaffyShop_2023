package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class startMenu {
    private JFrame fr;

    public  void createButton(JButton btn)//단순 꾸미기
    {
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        Color original =btn.getForeground();
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setForeground(Color.GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setForeground(original);
            }
        });
    }
    public startMenu()
    {
        fr=new JFrame();
        Container cp=fr.getContentPane();
        cp.setBackground(new Color(234, 146, 148, 255));
        fr.setSize(900,520);
        fr.setLocationRelativeTo(null);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cp.setLayout(null);

        JLabel imageL=new JLabel();
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("shop.png"));
        Image Icon= icon.getImage();
        Image changeSize=Icon.getScaledInstance(200,200,Image.SCALE_SMOOTH);
        ImageIcon BackIcon=new ImageIcon(changeSize);
        imageL.setIcon(BackIcon);
        imageL.setBounds(600,300,200,200);
        cp.add(imageL);

        JLabel laTitle = new JLabel("Taffy 상점 시뮬레이터");
        laTitle.setForeground(new Color(255, 255, 255));
        laTitle.setFont(new Font("Arial Rounded MT 굵게", Font.BOLD, 50));
        laTitle.setBounds(30,100,650,110);
        laTitle.setHorizontalAlignment(SwingConstants.CENTER);
        cp.add(laTitle);

        JLabel laTitle2 = new JLabel("Taffy 상점 시뮬레이터");
        laTitle2.setForeground(new Color(134, 78, 80));
        laTitle2.setFont(new Font("Arial Rounded MT 굵게", Font.BOLD, 50));
        laTitle2.setBounds(33,100,650,110);
        laTitle2.setHorizontalAlignment(SwingConstants.CENTER);
        cp.add(laTitle2);

        JButton btnStart = new JButton("> 시뮬레이션 시작");
        btnStart.addActionListener(new SwingAction());
        btnStart.setForeground(Color.WHITE);
        btnStart.setHorizontalAlignment(JButton.LEFT);
        btnStart.setFont(new Font("굴림 보통",Font.BOLD,20));
        btnStart.setBounds(90, 270,280,20);
        createButton(btnStart);
        cp.add(btnStart);

        JButton btnExit = new JButton("> 프로그램 종료");
        btnExit.addActionListener(new SwingAction());
        btnExit.setForeground(Color.WHITE);
        btnExit.setHorizontalAlignment(JButton.LEFT);
        btnExit.setFont(new Font("굴림 보통",Font.BOLD,20));
        btnExit.setBounds(90, 320,200,20);
        createButton(btnExit);
        cp.add(btnExit);

        fr.setVisible(true);
    }
    private class SwingAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand()=="> 시뮬레이션 시작")
            {
                fr.setVisible(false);
                selectCounter sC = new selectCounter();
            }
            else if(e.getActionCommand()=="> 프로그램 종료")
            {
                System.exit(0);
            }
        }
    }
}
