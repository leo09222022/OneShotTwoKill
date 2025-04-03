package sales.gui;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class SalesGUI extends JFrame {
    JTextField barcodeInput;
    JLabel productInfoLabel;

    public SalesGUI() {
        setTitle("무인편의점 키오스크 - 사용자 화면");
        setLayout(new BorderLayout());

        // [상단] 제목
        JLabel titleLabel = new JLabel("바코드 입력", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setBorder(new EmptyBorder(30, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // [중앙] 입력창과 안내
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        // 바코드 입력창
        barcodeInput = new JTextField();
        barcodeInput.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        centerPanel.add(barcodeInput);

        // 구분선
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(new JSeparator());
        centerPanel.add(Box.createVerticalStrut(20));

        // 안내문 + 상품 출력 영역
        productInfoLabel = new JLabel("상품 바코드를 입력해주세요", JLabel.CENTER);
        productInfoLabel.setPreferredSize(new Dimension(300, 100));
        productInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(productInfoLabel);

        // 구분선
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(new JSeparator());
        centerPanel.add(Box.createVerticalStrut(20));

        add(centerPanel, BorderLayout.CENTER);

        // [하단] 결제하기 버튼
        JButton payButton = new JButton("결제하기");
        payButton.setPreferredSize(new Dimension(100, 40));
        JPanel southPanel = new JPanel();
        southPanel.setBorder(new EmptyBorder(20, 0, 30, 0));
        southPanel.add(payButton);
        add(southPanel, BorderLayout.SOUTH);

        // 기본 세팅
        setSize(375, 660);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }


}