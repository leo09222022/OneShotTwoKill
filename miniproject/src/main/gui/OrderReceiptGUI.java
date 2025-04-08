package main.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import orders.database.OrderReceiptVO;
import totalordersproduct.gui.TotalOrdersProductGUI;

import java.util.ArrayList;

public class OrderReceiptGUI extends JFrame {
    JLabel totalPriceLabel;

    public OrderReceiptGUI(ArrayList<OrderReceiptVO> orderList) {
        // 레이아웃 구성
        setLayout(new BorderLayout());
        JPanel p_top = new JPanel();
        JPanel p_center = new JPanel();
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
 		add(p_south,BorderLayout.SOUTH);
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
        JPanel receiptPanel = new JPanel();
        receiptPanel.setLayout(new BoxLayout(receiptPanel, BoxLayout.Y_AXIS));
        receiptPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // 타이틀 (발주 영수증)
        JLabel labelTitle = new JLabel("발주 영수증");
        labelTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        receiptPanel.add(labelTitle);
        receiptPanel.add(Box.createVerticalStrut(10)); // 간격 추가

        // 상품 목록 표시
        int totalPrice = 0;
        for (OrderReceiptVO vo : orderList) {
            JLabel labelItem = new JLabel(vo.getProductName() + " - " + vo.getOrderQuantity() + "개 - " + vo.getCostPerProduct() + "원");
            labelItem.setAlignmentX(Component.CENTER_ALIGNMENT);
            receiptPanel.add(labelItem);
            totalPrice += vo.getCostPerProduct(); // 총 가격 계산
        }

        receiptPanel.add(Box.createVerticalStrut(10)); // 간격 추가

        // 총 가격 표시
        totalPriceLabel = new JLabel("총 가격: " + totalPrice + " 원");
        totalPriceLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        totalPriceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        receiptPanel.add(totalPriceLabel);

        // 중앙 정렬 패널에 추가
        p_center.setLayout(new FlowLayout(FlowLayout.CENTER));
        p_center.add(receiptPanel);
        add(p_center, BorderLayout.CENTER);

        /* [유지] 기본 세팅 ====================================================== */
        setTitle("OSTK 편의점 - 발주영수증");
        setSize(400, 600);
        setVisible(true);
        setLocationRelativeTo(null); // 화면 중앙 정렬
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}