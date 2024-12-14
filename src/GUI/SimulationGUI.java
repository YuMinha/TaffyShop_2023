package GUI;

import RunSim.TaffyShop;

import javax.swing.*;
import java.awt.*;

public class SimulationGUI {
    private JFrame countFr;
    private JPanel ConsolePa;
    private JLabel timeLa;
    private JTextArea statusTextArea;
    private JPanel counterPa;
    private JPanel queuePa;
    private JPanel simulaPa;
    private JButton nextButton;
    private JButton spMulti_1;
    private JButton spMulti_5;
    private JButton spMulti_10;
    private JButton spMulti_50;

    private StaticsGUI staticsGUI;

    public SimulationGUI(int countNum) {
        staticsGUI = new StaticsGUI();

        countFr = new JFrame();
        Container cp = countFr.getContentPane();
        cp.setBackground(new Color(234, 146, 148));
        countFr.setSize(900, 520);
        countFr.setLocationRelativeTo(null);
        countFr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cp.setLayout(null);

        //오른쪽 패널
        ConsolePa = new JPanel();
        ConsolePa.setLayout(new BorderLayout());
        ConsolePa.setBounds(675, 0, 210, 520);

        //시간 표시
        timeLa = new JLabel("Time : 0");
        timeLa.setFont(new Font("Arial", Font.BOLD, 16));
        timeLa.setHorizontalAlignment(JLabel.CENTER);
        ConsolePa.add(timeLa, BorderLayout.NORTH);

        //콘솔 텍스트
        statusTextArea = new JTextArea();
        statusTextArea.setEditable(false);
        JScrollPane stScrollPane = new JScrollPane(statusTextArea);
        ConsolePa.add(stScrollPane, BorderLayout.CENTER);

        cp.add(ConsolePa);
        //시뮬레이션 패널
        simulaPa = new JPanel();
        simulaPa.setLayout(null);
        simulaPa.setBounds(40, 0, 635, 520);
        simulaPa.setBackground(new Color(234, 146, 148));

        //창구
        counterPa = new JPanel();
        counterPa.setLayout(new BoxLayout(counterPa, BoxLayout.X_AXIS));
        counterPa.setBounds(40, 20, 575, 120);
        //각 창구에 아이콘 설정
        for (int i = 0; i < countNum; i++) {
            JLabel customerLabel = new JLabel();
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("customerOut.png"));
            Image Icon = icon.getImage();
            Image changeSize = Icon.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            ImageIcon customer = new ImageIcon(changeSize);
            customerLabel.setIcon(customer);
            counterPa.add(customerLabel);
        }

        simulaPa.add(counterPa);

        //대기 패널
        queuePa = new JPanel();
        queuePa.setLayout(new FlowLayout(FlowLayout.LEADING));
        queuePa.setBounds(40, 160, 575, 300);
        simulaPa.add(queuePa);


        //통계 창을 볼 수 있는 버튼
        nextButton = new JButton("NEXT");
        nextButton.setFont(new Font("", Font.BOLD, 16));
        nextButton.setHorizontalAlignment(JButton.CENTER);
        nextButton.setBounds(0, 20, 80, 30);
        nextButton.setEnabled(false);
        nextButton.setVisible(false);//시뮬레이션이 끝나면 보이도록 비활성화해두었음

        //배속 기능
        spMulti_1 = new JButton("x1");
        spMulti_1.setFont(new Font("", Font.BOLD, 14));
        spMulti_1.setHorizontalAlignment(JButton.CENTER);
        spMulti_1.setBounds(10, 310, 60, 30);
        spMulti_1.addActionListener(e -> {
            TaffyShop.setSpeedMultiplier(100);//TaffyShop의 단위 시간과 배속을 나누어 시간이 흐르도록 지정
        });
        spMulti_5 = new JButton("x5");
        spMulti_5.setFont(new Font("", Font.BOLD, 14));
        spMulti_5.setHorizontalAlignment(SwingConstants.CENTER);
        spMulti_5.setBounds(10, 350, 60, 30);
        spMulti_5.addActionListener(e -> {
            TaffyShop.setSpeedMultiplier(500);
        });
        spMulti_10 = new JButton("x10");
        spMulti_10.setFont(new Font("", Font.BOLD, 14));
        spMulti_10.setHorizontalAlignment(JButton.CENTER);
        spMulti_10.setBounds(10, 390, 60, 30);
        spMulti_10.addActionListener(e -> {
            TaffyShop.setSpeedMultiplier(1000);
        });
        spMulti_50 = new JButton("x50");
        spMulti_50.setFont(new Font("", Font.BOLD, 14));
        spMulti_50.setHorizontalAlignment(JButton.CENTER);
        spMulti_50.setBounds(10, 430, 60, 30);
        spMulti_50.addActionListener(e -> {
            TaffyShop.setSpeedMultiplier(5000);
        });


        cp.add(spMulti_1);
        cp.add(spMulti_5);
        cp.add(spMulti_10);
        cp.add(spMulti_50);
        cp.add(nextButton);

        cp.add(simulaPa);
        countFr.setVisible(true);
    }

    public void EndSim() //시뮬레이션이 끝날때 호출하는 함수
    {
        nextButton.setVisible(true);
        nextButton.setEnabled(true);
        //통계로 넘어가는 버튼을 활성화
        nextButton.addActionListener(e -> {
            countFr.dispose();
            staticsGUI.StartGUI();
            //버튼을 누르면 현재 창은 꺼지고 통계 프레임이 시작됨
        });
    }

    public void updateCounterStatus(int counterId, boolean hasCustomer) {
        //카운터의 상태가 변할때마다 이미지를 변경해줌
        JLabel counterLabel = (JLabel) counterPa.getComponent(counterId - 1);
        if (hasCustomer) {//고객 서비스 중인 상태
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("customerIn.png"));
            Image Icon = icon.getImage();
            Image changeSize = Icon.getScaledInstance(140, 120, Image.SCALE_SMOOTH);
            ImageIcon customer = new ImageIcon(changeSize);
            counterLabel.setIcon(customer);
        } else {//쉬고 있는상태
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("customerOut.png"));
            Image Icon = icon.getImage();
            Image changeSize = Icon.getScaledInstance(140, 120, Image.SCALE_SMOOTH);
            ImageIcon customer = new ImageIcon(changeSize);
            counterLabel.setIcon(customer);
        }
        counterLabel.repaint();
        countFr.repaint();

    }

    public void updateCounterCall(int counterID, boolean status) {
        JLabel counterLabel = (JLabel) counterPa.getComponent(counterID - 1);
        if (status) {//전화 서비스 중인 상태
            ImageIcon icon = new ImageIcon( getClass().getClassLoader().getResource("CallingCounter.png"));
            Image Icon = icon.getImage();
            Image changeSize = Icon.getScaledInstance(140, 120, Image.SCALE_SMOOTH);
            ImageIcon customer = new ImageIcon(changeSize);
            counterLabel.setIcon(customer);
        } else {//쉬고있는 상태
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("customerOut.png"));
            Image Icon = icon.getImage();
            Image changeSize = Icon.getScaledInstance(140, 120, Image.SCALE_SMOOTH);
            ImageIcon customer = new ImageIcon(changeSize);
            counterLabel.setIcon(customer);
        }
        counterLabel.repaint();
        countFr.repaint();
    }

    public void updateQueueAdd() {//대기 큐에 아이콘을 추가함
        JLabel customerLabel = new JLabel();
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("customer.png"));
        Image Icon = icon.getImage();
        Image changeSize = Icon.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon customer = new ImageIcon(changeSize);
        customerLabel.setIcon(customer);
        queuePa.add(customerLabel);
    }

    public void updateQueueDelete() {//대기 큐에 아이콘 삭제
        Component[] customer = queuePa.getComponents();
        if (customer.length > 0) {
            queuePa.remove(customer[0]);
            countFr.revalidate();
            countFr.repaint();
        }
    }

    public void updateTime(int time) {
        timeLa.setText("Time: " + time);
    }

    public void updateText(String text) {
        statusTextArea.append(text + "\n");
    }

    public void PrintStatus(Object[] row) {
        staticsGUI.appendToOutput(row);
    }//Table에 내용을 추가

    public void PrintTotalStatus(Object[] row) {
        staticsGUI.setTotalStatistics(row);
    }

    public void PrintCallStatus(Object[] row) {
        staticsGUI.setCallStatistics(row);
    }

}