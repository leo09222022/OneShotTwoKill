package login.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.gui.AdminMainGUI;
import main.gui.MainGUI;

public class LoginGUI extends JFrame{
	JTable table;
    JTextField Jinput;
    JLabel Jlabel;
    
    // 화면 구성 (생성자)
    public LoginGUI() {
        // 레이아웃 구성
        setLayout(new BorderLayout());
        JPanel p_center = new JPanel();     // 컨텐츠 영역
        JPanel p_center_top = new JPanel();  // 상단 타이틀 영역
        JPanel p_center_mid = new JPanel();  // 중앙 버튼 영역
        JPanel p_south = new JPanel();      // 하단
        
        // 배경색 설정
        p_center.setBackground(Color.WHITE);
        p_center_top.setBackground(Color.WHITE);
        p_center_mid.setBackground(Color.WHITE);
        p_south.setBackground(Color.WHITE);
        
        // 공통 컴포넌트 구성 : 버튼
        JButton btnLogin = new JButton("로그인");
        
        
        /* [S : 공통] 레이아웃영역  ====================================================== */
        // 하단 영역 : 레이아웃
        p_south.setBackground(Color.WHITE);
        
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
//        JLabel labelTitle = new JLabel("환영합니다");
//        labelTitle.setHorizontalAlignment(JLabel.CENTER);
//        labelTitle.setBorder(new EmptyBorder(100, 0, 50, 0)); // 간격넣기 (상, 좌, 하, 우)
//        labelTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
//        p_center_top.setLayout(new BorderLayout());
//        p_center_top.add(labelTitle, BorderLayout.CENTER); // 페이지 타이틀 넣기
        
     // 컨텐츠 영역 : 페이지 타이틀 및 정보
        JLabel labelTitle = new JLabel("관리자 로그인");
        labelTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        labelTitle.setAlignmentX(CENTER_ALIGNMENT);
        
        
        

        // 세로 정렬을 위해 BoxLayout 사용
        p_center_top.setLayout(new BoxLayout(p_center_top, BoxLayout.Y_AXIS));
        p_center_top.setBorder(new EmptyBorder(140, 0, 30, 0)); // 상하 여백 추가
        
        // 패널에 라벨 추가
        p_center_top.add(labelTitle);
        /* [E] 컨텐츠 영역 : p_center_top ========================================================== */
        /* [S] 컨텐츠 영역 : p_center_mid ========================================================== */
	     // 중앙 패널 설정 - 버튼 배치를 위한 설정
        p_center_mid.setLayout(new BoxLayout(p_center_mid, BoxLayout.Y_AXIS));	// 중앙패널을 세로로 배치하기 위한 코드
        
        // 위쪽 여백 추가
        p_center_mid.add(Box.createVerticalStrut(20));
        
        // 비밀번호 라벨과 텍스트 필드를 함께 담을 패널 생성
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passwordPanel.setBackground(Color.WHITE);
        

	     // 비밀번호 라벨 생성
	     JLabel passwordLabel = new JLabel("비밀번호: ");
	     passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
	        
        Jinput = new JTextField(10); // 10개 문자 너비
        Jinput.setPreferredSize(new Dimension(150, 25));

		// 텍스트 필드를 p_center_mid에 먼저 추가 (버튼 전에 추가하여 위에 위치하게 함)
		passwordPanel.add(passwordLabel);
		passwordPanel.add(Jinput);
	
		// 패널을 중앙 패널에 추가
		p_center_mid.add(passwordPanel);
		
		// 오류 메시지 라벨 생성 (처음에는 보이지 않음)
		JLabel errorLabel = new JLabel("비밀번호가 다릅니다");
		errorLabel.setForeground(Color.RED);
		errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		errorLabel.setVisible(false); // 처음에는 보이지 않게 설정
		
		// 오류 메시지 라벨 추가
		p_center_mid.add(errorLabel);
		
		// 텍스트 필드와 버튼 사이에 적은 여백만 추가 (이 값을 줄이세요)
		p_center_mid.add(Box.createVerticalStrut(5)); // 이 값을 더 작게 (예: 5로) 조정해 보세요
		
	     // 매출 관리 버튼 설정
	     btnLogin.setPreferredSize(new Dimension(200, 40));   // 버튼 사이즈 설정
	     btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT); // 가운데 정렬
	     btnLogin.setFont(new Font("SansSerif", Font.BOLD, 14));
	     btnLogin.setBackground(new Color(30, 135, 61));
	     btnLogin.setForeground(Color.WHITE);
	     btnLogin.setFocusPainted(false);
	     btnLogin.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
	     btnLogin.setOpaque(true);
	     btnLogin.setContentAreaFilled(true);
	     btnLogin.setBorderPainted(false);
	
	     // 버튼을 텍스트 필드 다음에 추가
	     p_center_mid.add(btnLogin);
	     
	     // 로그인 버튼 이벤트 
	     btnLogin.addActionListener(e -> {
	    	 if(Jinput.getText().equals("1234")) {
	    		 dispose(); // 현재 창 닫기
	    		 new AdminMainGUI();
	    	 } else {
	    		 Jinput.setText("");
	    		 errorLabel.setVisible(true); // 오류 메시지 표시
	    	 }
	     });
	     
	     // 버튼에 약간의 여백 추가
	     p_center_mid.setBorder(new EmptyBorder(0, 0, 250, 0)); // 하단 여백 추가
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
        setLocationRelativeTo(null); // 화면 중앙에 표시
        setResizable(false); // 리사이즈 제어
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
