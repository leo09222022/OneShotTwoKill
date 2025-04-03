import javax.swing.*;
import java.awt.*;

public class OrderGUI extends JFrame {
    public OrderGUI() {
        setTitle("발주 화면");
        setSize(360, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        // 뒤로가기 버튼
        JButton backButton = new JButton("< 뒤로가기");
        backButton.setBounds(20, 20, 80, 30);
        add(backButton);

        // 관리자 화면 종료
        JButton exitButton = new JButton("관리자 화면 종료");
        exitButton.setBounds(117, 580, 126, 30);
        add(exitButton);

        // 상품 목록 패널
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new GridLayout(6, 4, 10, 10));
        productPanel.setBounds(20, 100, 320, 300);
        
        String[] headers = {"상품명", "현재재고", "적정재고", "발주수량"};
        for (String header : headers) {
            JLabel label = new JLabel(header, SwingConstants.CENTER);
            label.setFont(new Font("Inter", Font.BOLD, 16));
            productPanel.add(label);
        }

        // 샘플 데이터
        String[][] products = {
            {"콜라", "2개", "10개"},
            {"사이다", "3개", "8개"},
            {"커피", "5개", "15개"},
            {"주스", "4개", "12개"},
            {"우유", "1개", "5개"}
        };

        for (String[] product : products) {
            for (int i = 0; i < 3; i++) {
                JLabel label = new JLabel(product[i], SwingConstants.CENTER);
                productPanel.add(label);
            }
            JTextField orderField = new JTextField();
            orderField.setHorizontalAlignment(JTextField.CENTER);
            productPanel.add(orderField);
        }

        add(productPanel);
        setVisible(true);
    }

}
