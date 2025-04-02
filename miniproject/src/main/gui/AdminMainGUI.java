package main.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

// 공통 레이아웃
public class AdminMainGUI extends JFrame {
    JTable table;
    JTextField Jinput;
    JLabel Jlabel;
    
    // 화면 구성 (생성자)
    public AdminMainGUI() {
        // 레이아웃 구성
        setLayout(new BorderLayout());
        JPanel p_center = new JPanel();     // 컨텐츠 영역
        JPanel p_center_top = new JPanel();  // 상단 타이틀 영역
        JPanel p_center_mid = new JPanel();  // 중앙 버튼 영역
        JPanel p_south = new JPanel();      // 하단
        
        // 공통 컴포넌트 구성 : 버튼
        JButton btnProductManage = new JButton("상품 관리");
        JButton btnSalesManage = new JButton("매출 관리");
        JLabel btnExit = new JLabel("관리자 화면 종료");
        
        /* [S : 공통] 레이아웃영역  ====================================================== */
        // 하단 영역 : 레이아웃
        p_south.setBackground(Color.WHITE);
        
        // 하단 영역 : 공통 레이블(관리자 화면 종료)
        p_south.setLayout(new FlowLayout(FlowLayout.CENTER));
        p_south.add(btnExit);
        add(p_south, BorderLayout.SOUTH);
        /* [E : 공통] 레이아웃영역  ====================================================== */
        
        /* [S] 컨텐츠 영역 : p_center_top ========================================================== */
        // 컨텐츠 영역 : 페이지 타이틀
        JLabel labelTitle = new JLabel("환영합니다");
        labelTitle.setHorizontalAlignment(JLabel.CENTER);
        labelTitle.setBorder(new EmptyBorder(100, 0, 50, 0)); // 간격넣기 (상, 좌, 하, 우)
        labelTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        p_center_top.setLayout(new BorderLayout());
        p_center_top.add(labelTitle, BorderLayout.CENTER); // 페이지 타이틀 넣기
        /* [E] 컨텐츠 영역 : p_center_top ========================================================== */
        
        /* [S] 컨텐츠 영역 : p_center_mid ========================================================== */
        // 중앙 패널 설정 - 버튼 배치를 위한 설정
        p_center_mid.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20)); // 버튼 사이 간격 설정
        
        // 버튼 스타일 설정
        btnProductManage.setPreferredSize(new Dimension(200, 40)); // 버튼 사이즈 설정
        btnSalesManage.setPreferredSize(new Dimension(200, 40));   // 버튼 사이즈 설정
        
        // 패널에 버튼 추가
        p_center_mid.add(btnProductManage);
        p_center_mid.add(btnSalesManage);
        
        // 버튼에 이벤트 추가
        btnProductManage.addActionListener(e -> {
        	this.setVisible(false);
        	new ProductManagementGUI();
        });
        
        // 버튼에 약간의 여백 추가
        p_center_mid.setBorder(new EmptyBorder(0, 0, 200, 0)); // 하단 여백 추가
        /* [E] 컨텐츠 영역 : p_center_mid ========================================================== */
        
        /* [공통] 최상위 부모 패널닫기  ====================================================== */
        // 최종 컨텐츠 닫기 : p_center
        p_center.setLayout(new BorderLayout());
        p_center.add(p_center_top, BorderLayout.NORTH);
        p_center.add(p_center_mid, BorderLayout.CENTER);
        add(p_center, BorderLayout.CENTER);    
        
        /* [유지] 기본세팅 : 항시 소스 맨 밑에 배치 ====================================================== */
        setTitle("무인편의점 키오스크");
        setSize(375, 660);
        setVisible(true);
        setResizable(false); // 리사이즈 제어
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    
}