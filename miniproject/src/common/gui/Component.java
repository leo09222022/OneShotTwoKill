package common.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
//import java.awt.GridLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.util.ArrayList;
//import java.util.Vector;

// import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
// import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


// 공통 레이아웃
public class Component extends JFrame{
	JTable table;
	JTextField Jinput;
	JLabel Jlabel;
	
	// 화면 구성 (생성자)
	public Component() {
		// 레이아웃 구성
		setLayout(new BorderLayout());
		JPanel p_top = new JPanel();		// 상단
		JPanel p_center = new JPanel();		// 컨텐츠 영역
		JPanel p_center_top = new JPanel();
		JPanel p_center_mid = new JPanel();
		JPanel p_center_btn = new JPanel();
		JPanel p_south = new JPanel();		// 하단
		
		// 공통 컴포넌트 구성 : 버튼
		JButton btnTypeG = new JButton("회색버튼");
		JButton btnTypeW = new JButton("흰색버튼");
		JButton btnBack = new JButton("< 뒤로가기"); 	
		JButton btnExit = new JButton("관리자화면 종료");
		
		
		
		/* [S : 공통] 레이아웃영역  ====================================================== */
		// 상+하단 영역 : 레이아웃
		p_top.setBackground(Color.WHITE);
		p_south.setBackground(Color.WHITE);
		p_center_mid.setBackground(Color.WHITE); // 배경화면 설정
		p_center_btn.setBackground(Color.WHITE); // 배경화면 설정 
		
		// 상단 영역 : 공통 버튼( 뒤로가기 )
		p_top.setLayout(new FlowLayout(FlowLayout.LEFT));
		p_top.add(btnBack);
		add(p_top,BorderLayout.NORTH);
		btnBack.setBorderPainted(false);
		btnBack.setBackground(Color.WHITE);
		btnBack.setForeground(Color.BLACK);
		btnBack.setFocusPainted(false);
	        
		// 하단 영역 : 공통 버튼( 관리자 화면 종료 )
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
        
        
        // 컨텐츠 영역 : 페이지 타이틀
        JLabel labelTitle = new JLabel("페이지 타이틀영역");
		labelTitle.setAlignmentX(Component.LEFT_ALIGNMENT);	// 왼쪽정렬
		labelTitle.setBorder(new EmptyBorder(10, 0, 10, 20)); // 간격넣기
		labelTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
		p_center_top_tit.add(labelTitle); // 페이지 타이틀 넣기
		p_center_top_tit.setBackground(Color.WHITE); // 배경화면 설정
        
		
		// 컨텐츠 영역 : 인풋 + 버튼
		JTextField textField = new JTextField();
		textField.setPreferredSize(new Dimension(285, 28)); // JTextField 사이즈 설정
        JButton btnTypeSearch = new JButton("검색");
        p_center_top_search.add(textField);
        p_center_top_search.add(btnTypeSearch);
        p_center_top_search.setBackground(Color.WHITE); // 배경화면 설정
        
        
		// 컨텐츠 영역 : p_center_top 관련 패널에 넣기
        p_center_top.setLayout(new BorderLayout());
        p_center_top.add(p_center_top_tit,BorderLayout.NORTH);
        p_center_top.add(p_center_top_search,BorderLayout.SOUTH);
        /* [E] 컨텐츠 영역 : p_center_top ========================================================== */
        
        
        
        
        
        /* [S] 컨텐츠 영역 : p_center_mid ========================================================== */
        // 컨텐츠 영역 : p_center_mid 관련 패널 생성
        // 없음
        
        // 컨텐츠 영역 : 라벨 + 인풋
        JTextField[] textFields = new JTextField[4];
        JLabel[] labels = new JLabel[4];
        
        // 라벨 텍스트 배열
        String[] labelTexts = {"상품명", "가격", "적정재고", "재고수량"};
        for (int i = 0; i < labelTexts.length; i++) {
            // 라벨영역
        	labels[i] = new JLabel(labelTexts[i]);
        	labels[i].setPreferredSize(new Dimension(350, 28));	// 크기 설정
            labels[i].setMaximumSize(new Dimension(350, 28));	// 최대 크기 설정
            labels[i].setAlignmentX(Component.LEFT_ALIGNMENT);	// 왼쪽정렬
            
        	// 인풋영역
        	textFields[i] = new JTextField();
            textFields[i].setPreferredSize(new Dimension(350, 28));	// 크기 설정
            textFields[i].setMaximumSize(new Dimension(350, 28));	// 최대 크기 설정
            textFields[i].setAlignmentX(Component.LEFT_ALIGNMENT);	// 왼쪽정렬
        }
		
        p_center_mid.setLayout(new FlowLayout(FlowLayout.LEFT));
		for (int i = 0; i < 4; i++) {
		    p_center_mid.add(labels[i]);
		    p_center_mid.add(textFields[i]);
		}
		
		
		// 컨텐츠 영역 : 라벨 + 체크박스
		// 체크박스 관련 패널 생성
		JPanel panel_check_group = new JPanel(); // 패널 생성
        panel_check_group.setLayout(new FlowLayout(FlowLayout.LEFT)); // 체크박스 정렬을 위해 FlowLayout 사용
        panel_check_group.setBackground(Color.WHITE); // 배경화면 설정
        
		// 체크박스 배열
        String[] checkTexts = {"음료", "과자", "카테고리1", "카테고리2", "기타"};
        JCheckBox []jcb;
        jcb = new JCheckBox[checkTexts.length];
        
        JLabel label = new JLabel("체크박스");
        label.setPreferredSize(new Dimension(350, 28));	// 크기 설정
        label.setMaximumSize(new Dimension(350, 28));	// 최대 크기 설정
        label.setAlignmentX(Component.LEFT_ALIGNMENT);	// 왼쪽정렬
        
        p_center_mid.add(label); // 체크박스 타이틀 넣기
        for (int i = 0; i < checkTexts.length; i++) {
        	jcb[i] = new JCheckBox(checkTexts[i]);
        	jcb[i].setBackground(Color.WHITE); // 배경화면 설정
        	panel_check_group.add(jcb[i]);
        }
       
        
        // 컨텐츠 영역 : p_center_mid 관련 패널에 넣기
        p_center_mid.add(panel_check_group);
        // panel_check_group.setLayout(new BoxLayout(panel_check_group, BoxLayout.Y_AXIS)); // [필요시 사용] 1행씩 한줄 왼쪽정렬
        /* [E] 컨텐츠 영역 : p_center_mid ========================================================== */
		
		
	
		
        
        /* [공통] 버튼영역  ====================================================== */
		// 컨텐츠 영역 : 버튼
        p_center_btn.setLayout(new FlowLayout(FlowLayout.CENTER));
        p_center_btn.add(btnTypeG);
        p_center_btn.add(btnTypeW);
		btnTypeG.setPreferredSize(new Dimension(120, 28)); // 버튼 사이즈 설정
		btnTypeW.setPreferredSize(new Dimension(120, 28)); // 버튼 사이즈 설정
        // add(p_center_btn,BorderLayout.SOUTH); // 현재 관지라 화면 종료 버튼으로 인해 해당버튼 안보임

		
		
		/* [공통] 최상위 부모 패널닫기  ====================================================== */
		// 최종 컨텐츠 닫기 : p_center
        p_center.setLayout(new BorderLayout());
        p_center.add(p_center_top,BorderLayout.NORTH);
        p_center.add(p_center_mid,BorderLayout.CENTER);
        p_center.add(p_center_btn,BorderLayout.SOUTH); // 현재 관지라 화면 종료 버튼으로 인해 해당버튼 안보임
		add(p_center,BorderLayout.CENTER);	
		
	
		
		/* [유지] 기본세팅 : 항시 소스 맨 밑에 배치 ====================================================== */
		setTitle("무인편의점 키오스크");
		setSize(375, 660);
	    setVisible(true);
	    setResizable(false); // 리사이즈 제어
	    setLocationRelativeTo(null); // 화면 정중앙 배치
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	
	
	// test
	public static void main(String[] args) {
		new Component();
	}
}
