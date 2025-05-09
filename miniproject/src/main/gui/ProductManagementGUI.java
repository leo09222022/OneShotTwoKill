package main.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import orders.gui.OrderGUI;
import product.gui.ProductGUI;
import product.gui.ProductUpdateGUI;
import totalordersproduct.gui.TotalOrdersProductGUI;

// 상품 관리 화면
public class ProductManagementGUI extends JFrame {
    JTable table;
    JTextField Jinput;
    JLabel Jlabel;
    
    // 화면 구성 (생성자)
    public ProductManagementGUI() {
        // 레이아웃 구성
        setLayout(new BorderLayout());
        JPanel p_top = new JPanel();        // 상단
        JPanel p_center = new JPanel();     // 컨텐츠 영역
        JPanel p_center_top = new JPanel();  // 상단 타이틀 영역
        JPanel p_center_mid = new JPanel();  // 중앙 버튼 영역
        JPanel p_south = new JPanel();      // 하단
        
        // 배경색 설정
        p_top.setBackground(Color.WHITE);
        p_center.setBackground(Color.WHITE);
        p_center_top.setBackground(Color.WHITE);
        p_center_mid.setBackground(Color.WHITE);
        p_south.setBackground(Color.WHITE);
        
        
        
        // 공통 컴포넌트 구성 : 버튼
        JButton btnBack = new JButton("< 뒤로가기");
        JButton btnRegister = new JButton("상품 등록");
        JButton btnModify = new JButton("상품 수정");
        JButton btnTotalSales = new JButton("상품 발주");
        JButton btnTotalOrders = new JButton("발주 내역");
        JLabel lblExit = new JLabel("관리자 화면 종료");
        
        /* [S : 공통] 레이아웃영역  ====================================================== */
        // 상+하단 영역 : 레이아웃
        p_top.setBackground(Color.WHITE);
        p_south.setBackground(Color.WHITE);
        
        // 상단 영역 : 공통 버튼( 뒤로가기 )
        p_top.setLayout(new FlowLayout(FlowLayout.LEFT));
        p_top.add(btnBack);
        add(p_top, BorderLayout.NORTH);
        btnBack.setBorderPainted(false);
        btnBack.setBackground(Color.WHITE);
        btnBack.setForeground(Color.BLACK);
        btnBack.setFocusPainted(false);
        
        // 버튼에 이벤트 추가
        btnBack.addActionListener(e -> {
        	dispose(); // 현재 창 닫기
        	new AdminMainGUI();
        });
        btnTotalOrders.addActionListener(e -> {
        	dispose(); // 현재 창 닫기
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
        /* [E : 공통] 레이아웃영역  ====================================================== */
        
        /* [S] 컨텐츠 영역 : p_center_top ========================================================== */
        // 컨텐츠 영역 : 페이지 타이틀
//        JLabel labelTitle = new JLabel("상품 관리 화면");
//        labelTitle.setHorizontalAlignment(JLabel.CENTER);
//        labelTitle.setBorder(new EmptyBorder(50, 0, 50, 0)); // 간격넣기 (상, 좌, 하, 우)
//        labelTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
//        p_center_top.setLayout(new BorderLayout());
//        p_center_top.add(labelTitle, BorderLayout.CENTER); // 페이지 타이틀 넣기
        
        JLabel labelTitle = new JLabel("상품 관리 화면");
        labelTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        labelTitle.setAlignmentX(CENTER_ALIGNMENT);
        
        JLabel shopinfo1 = new JLabel("상품을 관리하고  ");
        shopinfo1.setFont(new Font("SansSerif", Font.PLAIN, 14));
        shopinfo1.setAlignmentX(CENTER_ALIGNMENT);

        JLabel shopinfo2 = new JLabel("발주 내역을 확인하세요.");
        shopinfo2.setFont(new Font("SansSerif", Font.PLAIN, 14));
        shopinfo2.setAlignmentX(CENTER_ALIGNMENT);

        // 세로 정렬을 위해 BoxLayout 사용
        p_center_top.setLayout(new BoxLayout(p_center_top, BoxLayout.Y_AXIS));
        p_center_top.setBorder(new EmptyBorder(80, 0, 20, 0)); // 상하 여백 추가

        // 패널에 라벨 추가
        p_center_top.add(labelTitle);
        p_center_top.add(shopinfo1);
        p_center_top.add(shopinfo2);
        
        
        
        /* [E] 컨텐츠 영역 : p_center_top ========================================================== */
        
        /* [S] 컨텐츠 영역 : p_center_mid ========================================================== */
        // 중앙 패널 설정 - 버튼 배치를 위한 설정
        p_center_mid.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20)); // 버튼 사이 간격 설정
        
        // 버튼 스타일 설정 - 테두리가 있는 흰색 버튼으로 설정
        btnRegister.setPreferredSize(new Dimension(200, 40)); // 버튼 사이즈 설정
        btnModify.setPreferredSize(new Dimension(200, 40));   // 버튼 사이즈 설정
        btnTotalSales.setPreferredSize(new Dimension(200, 40));   // 버튼 사이즈 설정
        btnTotalOrders.setPreferredSize(new Dimension(200, 40));  // 발주 내역 버튼 사이즈 설정
        
        // 모든 버튼에 테두리 설정
        btnRegister.setBorderPainted(true);
        btnRegister.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnRegister.setBackground(new Color(30, 135, 61));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFocusPainted(false);
        btnRegister.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnRegister.setOpaque(true);
        btnRegister.setContentAreaFilled(true);
        btnRegister.setBorderPainted(false);

        
        btnModify.setBorderPainted(true);
        btnModify.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnModify.setBackground(new Color(30, 135, 61));
        btnModify.setForeground(Color.WHITE);
        btnModify.setFocusPainted(false);
        btnModify.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnModify.setOpaque(true);
        btnModify.setContentAreaFilled(true);
        btnModify.setBorderPainted(false);

        
        btnTotalSales.setBorderPainted(true);
        btnTotalSales.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnTotalSales.setBackground(new Color(30, 135, 61));
        btnTotalSales.setForeground(Color.WHITE);
        btnTotalSales.setFocusPainted(false);
        btnTotalSales.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnTotalSales.setOpaque(true);
        btnTotalSales.setContentAreaFilled(true);
        btnTotalSales.setBorderPainted(false);

        
        btnTotalOrders.setBorderPainted(true);  // 발주 내역 버튼 테두리 설정
        btnTotalOrders.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnTotalOrders.setBackground(new Color(30, 135, 61));
        btnTotalOrders.setForeground(Color.WHITE);
        btnTotalOrders.setFocusPainted(false);
        btnTotalOrders.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnTotalOrders.setOpaque(true);
        btnTotalOrders.setContentAreaFilled(true);
        btnTotalOrders.setBorderPainted(false);

        
        // 패널에 버튼 추가
        p_center_mid.add(btnRegister);
        p_center_mid.add(btnModify);
        p_center_mid.add(btnTotalSales);
        p_center_mid.add(btnTotalOrders);  // 발주 내역 버튼 추가
        
        // [신규 상품 등록] 버튼을 누를 시 ProductGUI.java를 가동시킨다. 
        btnRegister.addActionListener(e -> {
            this.setVisible(false);
            new ProductGUI(); // 상품 등록 화면 열기
        });
        
        // [상품 수정] 버튼을 누를 시 ProductUpdateGUI.java를 가동시킨다. 
        btnModify.addActionListener(e -> {
        	this.setVisible(false);
        	new ProductUpdateGUI();
        });
        
        // [상품 발주] 버튼을 누를 시 OrderGUI.java를 가동시킨다. 
        btnTotalSales.addActionListener(e -> {
            this.setVisible(false);
            new OrderGUI(); // 상품 등록 화면 열기
        });

        // 버튼에 약간의 여백 추가
        p_center_mid.setBorder(new EmptyBorder(0, 0, 100, 0)); // 하단 여백 추가
        /* [E] 컨텐츠 영역 : p_center_mid ========================================================== */
        
        /* [공통] 최상위 부모 패널닫기  ====================================================== */
        // 최종 컨텐츠 닫기 : p_center
        p_center.setLayout(new BorderLayout());
        p_center.add(p_center_top, BorderLayout.NORTH);
        p_center.add(p_center_mid, BorderLayout.CENTER);
        add(p_center, BorderLayout.CENTER);    
        
		/* [유지] 기본세팅 : 항시 소스 맨 밑에 배치 ====================================================== */
		setTitle("OSTK 편의점 - 관리자");
		setSize(375, 660);
	    setVisible(true);
        setLocationRelativeTo(null); // 화면 중앙에 표시
	    setResizable(false); // 리사이즈 제어
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
   
}
