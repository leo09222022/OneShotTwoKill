package main.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import orders.database.OrderReceiptVO;
import totalordersproduct.gui.TotalOrdersProductGUI;

import java.text.NumberFormat;
import java.util.ArrayList;

public class OrderReceiptGUI extends JFrame {
    JLabel totalPriceLabel;

    public OrderReceiptGUI(ArrayList<OrderReceiptVO> orderList) {
        // 레이아웃 구성
        setLayout(new BorderLayout());
        JPanel p_top = new JPanel();
        JPanel p_center = new JPanel();
        JPanel p_center_top = new JPanel();
        JPanel p_center_mid = new JPanel();
        JPanel p_center_btm = new JPanel();
        JPanel p_south = new JPanel();

        // 공통 컴포넌트 : 버튼
        JButton btnBack = new JButton("< 뒤로가기");
        JButton btnTotalOrders = new JButton("확인");
        JLabel lblExit = new JLabel("메인으로 이동");

        /* [S : 공통] 레이아웃 ====================================================== */
        p_top.setBackground(Color.WHITE);
        p_south.setBackground(Color.WHITE);

        // 상단 영역 : 뒤로가기 버튼
        p_top.setLayout(new FlowLayout(FlowLayout.LEFT));
        p_top.add(btnBack);
        add(p_top, BorderLayout.NORTH);
        btnBack.setBorderPainted(false);
        btnBack.setBackground(Color.WHITE);
        btnBack.setForeground(Color.BLACK);
        btnBack.setFocusPainted(false);

        // 버튼 이벤트
        btnBack.addActionListener(e -> {
            this.setVisible(false);
            new ProductManagementGUI();
        });
        btnTotalOrders.addActionListener(e -> {
            this.setVisible(false);
            new TotalOrdersProductGUI();
        });

        // 하단 영역 : 공통 버튼( 메인 화면으로 이동)
        JButton btnExit = new JButton("메인으로 이동");
        p_south.setLayout(new FlowLayout(FlowLayout.CENTER));
        p_south.add(btnExit);
        add(p_south, BorderLayout.SOUTH);
        btnExit.setBorderPainted(false);
        btnExit.setBackground(Color.WHITE);
        btnExit.setForeground(Color.BLACK);
        btnExit.setFocusPainted(false);

        btnExit.addActionListener(e -> {
            dispose();
            new MainGUI();
        });

        /* [E : 공통] 레이아웃 ====================================================== */

        /* [S] 컨텐츠 영역 : 중앙 정렬 ========================================================== */
        p_center.setLayout(new BorderLayout());
        p_center.setBackground(Color.WHITE);

        // [S] p_center_top : 타이틀
        p_center_top.setLayout(new FlowLayout(FlowLayout.CENTER));
        p_center_top.setBackground(Color.WHITE);
        JLabel labelTitle = new JLabel("발주 영수증");
        labelTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        p_center_top.add(labelTitle);
        /* [E] p_center_top */

        // [S] p_center_mid : 발주 상품 리스트
        p_center_mid.setLayout(new BoxLayout(p_center_mid, BoxLayout.Y_AXIS));
        p_center_mid.setBackground(Color.WHITE);
        p_center_mid.setBorder(new EmptyBorder(10, 30, 10, 30));

        int totalPrice = 0;
        for (OrderReceiptVO vo : orderList) {
            int itemTotal = vo.getCostPerProduct();
            JLabel labelItem = new JLabel(vo.getProductName() + " - " + vo.getOrderQuantity() + "개 - " + NumberFormat.getInstance().format(itemTotal) + "원");
            labelItem.setFont(new Font("SansSerif", Font.PLAIN, 16));
            labelItem.setAlignmentX(Component.CENTER_ALIGNMENT);
            p_center_mid.add(labelItem);
            p_center_mid.add(Box.createVerticalStrut(5)); // 간격

            totalPrice += itemTotal;
        }
        /* [E] p_center_mid */

        // [S] p_center_btm : 총 가격 영역
        p_center_btm.setLayout(new FlowLayout(FlowLayout.CENTER));
        p_center_btm.setBackground(Color.WHITE);
        totalPriceLabel = new JLabel("총 가격: " + NumberFormat.getInstance().format(totalPrice) + " 원");
        totalPriceLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        p_center_btm.add(totalPriceLabel);
        /* [E] p_center_btm */

        // [공통] 중앙 패널 묶기
        p_center.add(p_center_top, BorderLayout.NORTH);
        p_center.add(p_center_mid, BorderLayout.CENTER);
        p_center.add(p_center_btm, BorderLayout.SOUTH);
        add(p_center, BorderLayout.CENTER);
        /* [E] 컨텐츠 영역 : 중앙 정렬 ========================================================== */

        /* [유지] 기본 세팅 ====================================================== */
        setTitle("무인편의점 키오스크");
        setSize(400, 600);
        setVisible(true);
        setLocationRelativeTo(null); // 화면 중앙 정렬
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
