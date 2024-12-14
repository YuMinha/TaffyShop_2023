package GUI;

import RunSim.TaffyShop;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class StaticsGUI {
    private JFrame fr;
    private JPanel customerStatics;
    private JLabel titleLa;
    private JTable staticsTable;
    private DefaultTableModel staticsTableModel;
    private JPanel totalStatics;
    private JLabel titleLa2;
    private JTable totalStaticsTable;
    private DefaultTableModel totalStaticsTableModel;
    private JPanel CallStatics;
    private JLabel titleLa3;
    private JTable callStaticsTable;
    private DefaultTableModel callStaticsTableModel;
    private JButton ToMenu;
    private JButton Exit;
    private JButton Save;
    private static int day = 1;

    public StaticsGUI() {
        fr = new JFrame();
        Container cp = fr.getContentPane();
        cp.setBackground(new Color(234, 146, 148));
        fr.setSize(900, 520);
        fr.setLocationRelativeTo(null);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cp.setLayout(null);

        customerStatics = new JPanel();
        customerStatics.setLayout(new BorderLayout());
        customerStatics.setBounds(30, 30, 420, 370);

        titleLa = new JLabel("고객 개인 통계");
        titleLa.setFont(new Font("", Font.BOLD, 15));
        titleLa.setHorizontalAlignment(SwingConstants.CENTER);
        customerStatics.add(titleLa, BorderLayout.NORTH);

        staticsTableModel = new DefaultTableModel();
        staticsTableModel.setColumnIdentifiers(new String[]{"고객 번호", "도착 시간", "시작 시간", "대기 시간", "서비스 시간"});
        staticsTable = new JTable(staticsTableModel);
        staticsTable.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(staticsTable);
        customerStatics.add(scrollPane, BorderLayout.CENTER);

        totalStatics = new JPanel();
        totalStatics.setLayout(new BorderLayout());
        totalStatics.setBounds(460, 30, 380, 150);

        titleLa2 = new JLabel("전체 통계");
        titleLa2.setFont(new Font("", Font.BOLD, 15));
        titleLa2.setHorizontalAlignment(SwingConstants.CENTER);
        totalStatics.add(titleLa2, BorderLayout.NORTH);

        totalStaticsTableModel = new DefaultTableModel();
        totalStaticsTableModel.setColumnIdentifiers(new String[]{"통계 항목", "값"});
        totalStaticsTable = new JTable(totalStaticsTableModel);
        totalStaticsTable.setEnabled(false);
        JScrollPane scroll = new JScrollPane(totalStaticsTable);
        totalStatics.add(scroll, BorderLayout.CENTER);

        CallStatics = new JPanel();
        CallStatics.setLayout(new BorderLayout());
        CallStatics.setBounds(460, 200, 380, 200);

        titleLa3 = new JLabel("전화 서비스 통계");
        titleLa3.setFont(new Font("", Font.BOLD, 15));
        titleLa3.setHorizontalAlignment(SwingConstants.CENTER);
        CallStatics.add(titleLa3, BorderLayout.NORTH);

        callStaticsTableModel = new DefaultTableModel();
        callStaticsTableModel.setColumnIdentifiers(new String[]{"고객 번호", "발신 시간", "수신 시간", "대기 시간", "서비스 시간"});
        callStaticsTable = new JTable(callStaticsTableModel);
        callStaticsTable.setEnabled(false);
        JScrollPane sc = new JScrollPane(callStaticsTable);
        CallStatics.add(sc, BorderLayout.CENTER);

        ToMenu = new JButton("메뉴");
        ToMenu.setHorizontalAlignment(SwingConstants.CENTER);
        ToMenu.setBounds(300, 420, 90, 50);
        ToMenu.addActionListener(e -> {
            saveAllTablesToCSV();
            TaffyShop.resetSimulation();
            updateDay(++day);
            fr.dispose();
            new startMenu();
        });
        cp.add(ToMenu);
        Exit = new JButton("종료");
        Exit.setHorizontalAlignment(SwingConstants.CENTER);
        Exit.setBounds(420, 420, 90, 50);
        Exit.addActionListener(e -> {
            saveAllTablesToCSV();
            System.exit(0);
        });

        cp.add(Exit);
        cp.add(customerStatics);
        cp.add(totalStatics);
        cp.add(CallStatics);
    }

    private static void updateDay(int newDay) {
        day = newDay;
    }

    public void StartGUI() {
        fr.setVisible(true);
    }

    public void appendToOutput(Object[] rowData) {
        staticsTableModel.addRow(rowData);
    }

    public void setTotalStatistics(Object[] data) {
        totalStaticsTableModel.addRow(data);
    }

    public void setCallStatistics(Object[] data) {
        callStaticsTableModel.addRow(data);
    }

    public void saveDataToCSV(String fileName, JTable table, DefaultTableModel model, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, append)))//Day가 1이 아니면 BufferedWriter에서 append를 true로 주어 이어쓰는 모드로 설정
        {
            // CSV 헤더(열 이름) 출력

            for (int i = 0; i < model.getColumnCount(); i++) {
                writer.write(model.getColumnName(i));
                if (i < model.getColumnCount() - 1) {
                    writer.write(",");
                }
            }
            writer.newLine();


            // 테이블 데이터 출력
            for (int row = 0; row < model.getRowCount(); row++) {
                for (int col = 0; col < model.getColumnCount(); col++) {
                    writer.write(model.getValueAt(row, col).toString());
                    if (col < model.getColumnCount() - 1) {
                        writer.write(",");
                    }
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 별도의 메서드를 사용하여 모든 테이블을 CSV 파일로 저장
    public void saveAllTablesToCSV() {
        if (day == 1) {//덮어쓰기
            saveDataToCSV("statics.csv", staticsTable, staticsTableModel, false);
            saveDataToCSV("totalStatics.csv", totalStaticsTable, totalStaticsTableModel, false);
            saveDataToCSV("callStatics.csv", callStaticsTable, callStaticsTableModel, false);
        } else {//이어쓰기
            saveDataToCSV("statics.csv", staticsTable, staticsTableModel, true);
            saveDataToCSV("totalStatics.csv", totalStaticsTable, totalStaticsTableModel, true);
            saveDataToCSV("callStatics.csv", callStaticsTable, callStaticsTableModel, true);
        }
    }
}
