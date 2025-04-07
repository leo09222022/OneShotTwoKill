package main.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
//import java.awt.GridLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.util.ArrayList;
//import java.util.Vector;
import java.awt.Font;

import javax.swing.BorderFactory;
// import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
// import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import login.gui.LoginGUI;
import sales.gui.SalesGUI;



// 공통 레이아웃
public class MainGUI extends JFrame{
	JTable table;
	JTextField Jinput;
	JLabel Jlabel;
	JLabel welcomeLabel = new JLabel("<html><center>환영합니다<br>OSTK 편의점 입니다</center></html>");
	
	// 화면 구성 (생성자)
	public MainGUI() {
		// 레이아웃 구성
		setLayout(new BorderLayout());
		JPanel p_north = new JPanel();		// 상단 영역
		JPanel p_center = new JPanel();		// 컨텐츠 영역
		JPanel p_center_top = new JPanel();
		JPanel p_center_mid = new JPanel();
		JPanel p_south = new JPanel();		// 하단
		
		// 시작 문구
		// 어서오세요 문구 출력
		welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
		welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
		welcomeLabel.setPreferredSize(new Dimension(300, 60));
		welcomeLabel.setForeground(new Color(30, 135, 61));
		welcomeLabel.setBorder(new EmptyBorder(0, 0, 10, 0)); // 버튼과 간격
		
		
		// 공통 컴포넌트 구성 : 버튼
		JButton btnTypeG = new JButton("시작하기");
		JButton btnExit = new JButton("관리자화면");
		
		// 버튼 스타일
		btnTypeG.setPreferredSize(new Dimension(120, 40));
		btnTypeG.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnTypeG.setBackground(new Color(30, 135, 61));
		btnTypeG.setForeground(Color.WHITE);
		btnTypeG.setFocusPainted(false);
		btnTypeG.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		btnTypeG.setOpaque(true);
		btnTypeG.setContentAreaFilled(true);
		btnTypeG.setBorderPainted(false);
		
		
		
		// 배경색 처리를 위한 상단
		
		/* [S : 공통] 레이아웃영역  ====================================================== */
		// 상+하단 영역 : 레이아웃
		p_north.setLayout(new FlowLayout(FlowLayout.CENTER));
		p_south.setBackground(Color.WHITE);
		add(p_north,BorderLayout.NORTH);
		
		// 상단 영역 : 공통 버튼( 뒤로가기 )
		p_north.setBackground(Color.WHITE);
	        
		// 하단 영역 : 공통 버튼( 관리자 화면으로 이동)
		p_south.setLayout(new FlowLayout(FlowLayout.CENTER));
		p_south.add(btnExit);
		add(p_south,BorderLayout.SOUTH);
		btnExit.setBorderPainted(false);
		btnExit.setBackground(Color.WHITE);
		btnExit.setForeground(Color.BLACK);
		btnExit.setFocusPainted(false);
		/* [E : 공통] 레이아웃영역  ====================================================== */
		
		
		
		
		
		/* [S] 컨텐츠 영역 : p_center_top ========================================================== */
		// 컨텐츠 영역 : p_center_top 관련 패널 생성
        JPanel p_center_top_tit = new JPanel(); // 패널 생성
        p_center_top_tit.setLayout(new FlowLayout(FlowLayout.LEFT)); // 정렬은 되지만 한줄을 넘어가면 안보임

        JPanel p_center_top_search = new JPanel(); // 패널 생성
        p_center_top_search.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5)); 
        
        /*
        // 컨텐츠 영역 : 페이지 타이틀
        JLabel labelTitle = new JLabel("페이지 타이틀영역");
		labelTitle.setAlignmentX(MainGUI.LEFT_ALIGNMENT);	// 왼쪽정렬
		labelTitle.setBorder(new EmptyBorder(10, 0, 10, 20)); // 간격넣기
		labelTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
		p_center_top_tit.add(labelTitle); // 페이지 타이틀 넣기
        */
		
        // 배경색 지정
        p_center_top_tit.setBackground(Color.WHITE);
        p_center_top_search.setBackground(Color.WHITE);

        
        
		// 컨텐츠 영역 : p_center_top 관련 패널에 넣기
        p_center_top.setLayout(new BorderLayout());
        p_center_top.add(p_center_top_tit,BorderLayout.NORTH);
        p_center_top.add(p_center_top_search,BorderLayout.SOUTH);
        btnTypeG.addActionListener(e -> {
        	this.setVisible(false);
        	new SalesGUI();
        });
        btnExit.addActionListener(e -> {
        	dispose(); // 현재 창 닫기
        	new LoginGUI();
        });
		/* [수정] 빨간 구역으로 회색버튼 이동 ====================================================== */
		// 중앙 패널 설정 - BorderLayout으로 변경하여 보다 정확한 위치 조정
		p_center_mid.setLayout(new BorderLayout());
		
		// 추가적인 패널을 생성하여 버튼의 세로 위치 조정
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.setBackground(Color.white);
		
		// 버튼 스타일 설정
		btnTypeG.setPreferredSize(new Dimension(200, 40)); // 버튼 사이즈 설정
		
		// 패널에 버튼 추가
		buttonPanel.add(welcomeLabel);
		buttonPanel.add(btnTypeG);
		
		// 패널에 여백 추가하여 빨간 구역 위치에 맞게 조정 (위쪽 여백)
		buttonPanel.setBorder(new EmptyBorder(200, 0, 0, 0)); // 빨간 구역 위치에 맞게 상단 여백 조정
		
		// 중앙 패널에 버튼 패널 추가
		p_center_mid.add(buttonPanel, BorderLayout.CENTER);
		
		/* [기존] 버튼영역 코드는 주석처리 또는 제거  ====================================================== */
        /* 
        // 컨텐츠 영역 : 버튼
        p_center_btn.setLayout(new FlowLayout(FlowLayout.CENTER));
        p_center_btn.add(btnTypeG);
		btnTypeG.setPreferredSize(new Dimension(120, 28)); // 버튼 사이즈 설정
        // add(p_center_btn,BorderLayout.SOUTH); // 현재 관지라 화면 종료 버튼으로 인해 해당버튼 안보임
		*/
		
		
		/* [공통] 최상위 부모 패널닫기  ====================================================== */
		// 최종 컨텐츠 닫기 : p_center
        p_center.setLayout(new BorderLayout());
        p_center.add(p_center_top,BorderLayout.NORTH);
        p_center.add(p_center_mid,BorderLayout.CENTER);
        // p_center.add(p_center_btn,BorderLayout.SOUTH); // 버튼을 중앙으로 옮겼으므로 이 줄은 주석 처리
		add(p_center,BorderLayout.CENTER);	
		
	
		
		/* [유지] 기본세팅 : 항시 소스 맨 밑에 배치 ====================================================== */
		setTitle("무인편의점 키오스크");
		setSize(375, 660);
	    setVisible(true);
        setLocationRelativeTo(null); // 화면 중앙에 표시
	    setResizable(false); // 리사이즈 제어
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


}