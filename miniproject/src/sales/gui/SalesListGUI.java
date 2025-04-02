package sales.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

// import common.gui.Component;

// 영수증 출력 화면
public class SalesListGUI extends JFrame{
	JTable table;
	JTextField Jinput;
	JLabel Jlabel;
	
	// 화면 구성 (생성자)
	public SalesListGUI() {
		// 레이아웃 구성
		setLayout(new BorderLayout());
		JPanel p_top = new JPanel();		// 상단
		JPanel p_center = new JPanel();		// 컨텐츠 영역
		JPanel p_center_top = new JPanel();
		JPanel p_center_mid = new JPanel();
		JPanel p_center_btn = new JPanel();
		JPanel p_south = new JPanel();		// 하단
		
		// 공통 컴포넌트 구성 : 버튼
		JButton btnTypeG = new JButton("영수증 출력");
		// JButton btnTypeW = new JButton("흰색버튼");
		// JButton btnBack = new JButton("< 뒤로가기"); 	
		// JButton btnExit = new JButton("관리자화면 종료");
		
		
		
		/* [S : 공통] 레이아웃영역  ====================================================== */
		// 상+하단 영역 : 레이아웃
//		p_top.setBackground(Color.WHITE);
		p_south.setBackground(Color.WHITE);
		
		// 상단 영역 : 공통 버튼( 뒤로가기 )
//		p_top.setLayout(new FlowLayout(FlowLayout.LEFT));
//		p_top.add(btnBack);
//		add(p_top,BorderLayout.NORTH);
//		btnBack.setBorderPainted(false);
//		btnBack.setBackground(Color.WHITE);
//		btnBack.setForeground(Color.BLACK);
//		btnBack.setFocusPainted(false);
	        
	
		// 상단 영역 : 멘트 영역
		p_top.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel labelMent = new JLabel("구매해 주셔서 감사합니다.");
		p_top.add(labelMent);
		add(p_top,BorderLayout.NORTH);
		
		
		// 하단 영역 : 공통 버튼( 관리자 화면 종료 )
//		p_south.setLayout(new FlowLayout(FlowLayout.CENTER));
//		p_south.add(btnExit);
//		add(p_south,BorderLayout.SOUTH);
//		btnExit.setBorderPainted(false);
//		btnExit.setBackground(Color.WHITE);
//		btnExit.setForeground(Color.BLACK);
//		btnExit.setFocusPainted(false);
		/* [E : 공통] 레이아웃영역  ====================================================== */
		
		
		
		
		
		/* [S] 컨텐츠 영역 : p_center_top ========================================================== */
		// 컨텐츠 영역 : p_center_top 관련 패널 생성
        JPanel p_center_top_tit = new JPanel(); // 패널 생성
        p_center_top_tit.setLayout(new FlowLayout(FlowLayout.CENTER)); // 정렬은 되지만 한줄을 넘어가면 안보임
        
        JPanel receipt_details_top = new JPanel(); // 패널 생성 
		receipt_details_top.setLayout(new GridLayout(2, 2, 5, 5)); // 3행 2열, 여백 5px

		JPanel receipt_details_btm = new JPanel(); // 패널 생성
     	receipt_details_btm.setLayout(new GridLayout(2, 2, 5, 5)); // 3행 2열, 여백 5px
     	
        
        // 컨텐츠 영역 : 페이지 타이틀 + 상단 공통 정보
        JLabel labelTitle = new JLabel("영수증");
		labelTitle.setAlignmentX(SalesListGUI.CENTER_ALIGNMENT);
		labelTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
		p_center_top_tit.add(labelTitle); // 페이지 타이틀 넣기
		
     	
        // 컴포넌트 생성
        JLabel titLabel = new JLabel("코스타 편의점");
        JLabel storeLabel = new JLabel("종각점 #12345");
        JLabel callLabel = new JLabel("012-3456-7890");
        JLabel addressLabel = new JLabel("서울특별시 종로구 우정국로 2길 21 대왕빌딩 7층");
        titLabel.setBorder(new EmptyBorder(5, 5, 0, 5)); 
        storeLabel.setBorder(new EmptyBorder(5, 76, 5, 5));
        callLabel.setBorder(new EmptyBorder(0, 5, 5, 5));
        addressLabel.setBorder(new EmptyBorder(5, 5, 5, 5)); 
        titLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        storeLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        callLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        addressLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        
        // 프레임에 추가
     	receipt_details_top.add(titLabel);
     	receipt_details_top.add(storeLabel);
     	receipt_details_top.add(callLabel);
     	receipt_details_btm.add(addressLabel);
        
        
		// 컨텐츠 영역 : p_center_top 관련 패널에 넣기
        p_center_top.setLayout(new BorderLayout());
        p_center_top.add(p_center_top_tit,BorderLayout.NORTH);
        p_center_top.add(receipt_details_top,BorderLayout.CENTER);
        p_center_top.add(receipt_details_btm,BorderLayout.SOUTH);
        /* [E] 컨텐츠 영역 : p_center_top ========================================================== */
        
        
        
        
        
        /* [S] 컨텐츠 영역 : p_center_mid ========================================================== */
        // 컨텐츠 영역 : p_center_mid 관련 패널 생성
        JPanel p_center_mid_top = new JPanel(); // 패널 생성
        p_center_mid_top.setLayout(new GridLayout(0, 1, 5, 5)); // 2행 1열, 여백 5px 
        
        JPanel receipt_mid_list = new JPanel(); // 패널 생성 
        receipt_mid_list.setLayout(new GridLayout(1, 3, 5, 5)); // 3행 1열, 여백 5px
        receipt_mid_list.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, Color.BLACK));
		
		JPanel receipt_mid_btm = new JPanel(); // 패널 생성 
		receipt_mid_btm.setLayout(new GridLayout(2, 2, 5, 5)); // 3행 2열, 여백 5px
		
		
		// 컴포넌트 생성
        JLabel titCardLabel = new JLabel("카드");
        JLabel saleDateLabel = new JLabel("[판매] 2025-03-19 (수) 18:11:47");
        saleDateLabel.setBorder(new EmptyBorder(5, 0, 5, 0));
        titCardLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        saleDateLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        JLabel productName = new JLabel("상품명");
        JLabel productCount = new JLabel("수량");
        JLabel productPrice = new JLabel("금액");
        productName.setBorder(new EmptyBorder(5, 35, 5, 35));
        productCount.setBorder(new EmptyBorder(5, 35, 5, 35));
        productPrice.setBorder(new EmptyBorder(5, 35, 5, 35));
        productName.setFont(new Font("SansSerif", Font.BOLD, 14));
        productCount.setFont(new Font("SansSerif", Font.BOLD, 14));
        productPrice.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        
        // 프레임에 추가
        p_center_mid_top.add(titCardLabel);
        p_center_mid_top.add(saleDateLabel);
        receipt_mid_list.add(productName);
        receipt_mid_list.add(productCount);
        receipt_mid_list.add(productPrice);
            
        
		// 컨텐츠 영역 : p_center_top 관련 패널에 넣기
     	p_center_mid.setLayout(new BorderLayout());
     	p_center_mid.add(p_center_mid_top,BorderLayout.NORTH);
     	p_center_mid.add(receipt_mid_list,BorderLayout.CENTER);
     	p_center_mid.add(receipt_mid_btm,BorderLayout.SOUTH);
		
		
		
		
		
		
		
		
		
        // 여기는 안씀
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
            labels[i].setAlignmentX(SalesListGUI.LEFT_ALIGNMENT);	// 왼쪽정렬
            
        	// 인풋영역
        	textFields[i] = new JTextField();
            textFields[i].setPreferredSize(new Dimension(350, 28));	// 크기 설정
            textFields[i].setMaximumSize(new Dimension(350, 28));	// 최대 크기 설정
            textFields[i].setAlignmentX(SalesListGUI.LEFT_ALIGNMENT);	// 왼쪽정렬
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
        
		// 체크박스 배열
        String[] checkTexts = {"음료", "과자", "카테고리1", "카테고리2", "기타"};
        JCheckBox []jcb;
        jcb = new JCheckBox[checkTexts.length];
        
        JLabel label = new JLabel("체크박스");
        label.setPreferredSize(new Dimension(350, 28));	// 크기 설정
        label.setMaximumSize(new Dimension(350, 28));	// 최대 크기 설정
        label.setAlignmentX(SalesListGUI.LEFT_ALIGNMENT);	// 왼쪽정렬
        
        p_center_mid.add(label); // 체크박스 타이틀 넣기
        for (int i = 0; i < checkTexts.length; i++) {
        	jcb[i] = new JCheckBox(checkTexts[i]);
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
        // p_center_btn.add(btnTypeW);
		btnTypeG.setPreferredSize(new Dimension(120, 28)); // 버튼 사이즈 설정
		// btnTypeW.setPreferredSize(new Dimension(120, 28)); // 버튼 사이즈 설정
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
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	
	
	// test
	public static void main(String[] args) {
		new SalesListGUI();
	}
}
