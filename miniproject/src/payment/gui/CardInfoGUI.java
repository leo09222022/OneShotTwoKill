package payment.gui;

import java.awt.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.format.DateTimeParseException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import common.gui.Component;
import payment.database.PaymentDAO;
import product.database.ProductVO;
import sales.database.SalesProductVO;
import sales.database.SalesVO;
import sales.gui.SalesGUI;
import sales.gui.SalesListGUI;

/*
1. 받아야 할것
카드번호
유효기간
판매금액
판매일자
------------------------------------------------------
2. 결제진행 클릭시
카드 받은 내역 + 구매내역 DB로 옮기기
- 예외처리 >> 하나라도 없으면 진행 안됨
- 구매내역이 없는경우 결제 화면 못오게 막기
------------------------------------------------------
3. 맵으로 묶어서 가져온 결재 리스트
영수증 화면에 뿌리기
*/

// 카드 정보
public class CardInfoGUI extends JFrame {
	Map<String, String> cardInfoMap = new HashMap<>(); // 카드정보를 Map으로 저장 및 전달	
	PaymentDAO dao = new PaymentDAO();
    
	// 카드번호 랜덤 출력 
	long cardNumberLong = 1000000000000000L + (long)(Math.random() * 9000000000000000L); // 16자리 출력
	String cardNumFin = String.valueOf(cardNumberLong);
	
	// 카드 유효기간 랜덤 출력   
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	int randomYear = 1 + (int)(Math.random() * 5);
	LocalDate cardLimitDate = LocalDate.now().plusYears(randomYear); 	// 오늘 날짜 + 랜덤 년도
	String cardLimitDateFin = cardLimitDate.format(formatter); 			// 오늘 날짜를 포매팅
	
	
	
    public CardInfoGUI(Map<ProductVO, Integer> cartMap) {
    	// 레이아웃 구성
    	setLayout(new BorderLayout());
    	JPanel p_top = new JPanel();
    	JPanel p_center = new JPanel();		// 컨텐츠 영역
    	JPanel p_center_top = new JPanel();
    	JPanel p_center_mid = new JPanel();
    	JPanel p_south = new JPanel();		// 하단

    	
    	
    	// 상단: 뒤로가기 버튼
		JButton btnBack = new JButton("< 뒤로가기");
		p_top.setLayout(new FlowLayout(FlowLayout.LEFT));
		p_top.setBackground(Color.WHITE);
		btnBack.setBorderPainted(false);
		btnBack.setBackground(Color.WHITE);
		btnBack.setForeground(Color.BLACK);
		btnBack.setFocusPainted(false);
		p_top.add(btnBack);
		add(p_top, BorderLayout.NORTH);

		// 뒤로가기 버튼 액션
		btnBack.addActionListener(e -> {
			this.setVisible(false);
			new SalesGUI();
		});
    	
    	
    	
    	/* [S] 레이아웃 & 컨텐츠 영역 ========================================================== */
    	// 상+하단 영역 : 레이아웃
		p_south.setBackground(Color.WHITE);
		p_center.setBackground(Color.WHITE); // 배경화면 설정
		p_center_mid.setBackground(Color.WHITE); // 배경화면 설정
		p_south.setBackground(Color.WHITE);
		p_center.setBorder(new EmptyBorder(10, 25, 10, 25)); // 간격넣기
		p_center_top.setBorder(new EmptyBorder(0, -5, 0, 0));
		
		
    	// 컨텐츠 영역 : 관련 패널 생성
        JPanel p_center_top_tit = new JPanel(); // 패널 생성
        p_center_top_tit.setLayout(new FlowLayout(FlowLayout.LEFT)); // 정렬은 되지만 한줄을 넘어가면 안보임

        
        // 컨텐츠 영역 : 페이지 타이틀
        JLabel labelTitle = new JLabel("결제 진행");
        labelTitle.setAlignmentX(Component.LEFT_ALIGNMENT);	// 왼쪽정렬
		labelTitle.setBorder(new EmptyBorder(0, 0, 10, 20)); // 간격넣기
		labelTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
		p_center_top_tit.add(labelTitle); // 페이지 타이틀 넣기
		p_center_top_tit.setBackground(Color.WHITE); // 배경화면 설정
		
		
		// 컨텐츠 영역 : 라벨 + 인풋
        JTextField[] textFields = new JTextField[2];
        JLabel[] labels = new JLabel[2];
        
        // 라벨 텍스트 배열
        String[] labelTexts = {"카드번호", "유효기간"};
        for (int i = 0; i < labelTexts.length; i++) {
            // 라벨영역
        	labels[i] = new JLabel(labelTexts[i]);
        	labels[i].setPreferredSize(new Dimension(350, 28));	// 크기 설정
            labels[i].setMaximumSize(new Dimension(350, 28));	// 최대 크기 설정
            labels[i].setAlignmentX(Component.LEFT_ALIGNMENT);	// 왼쪽정렬
            labels[i].setFont(new Font("SansSerif", Font.BOLD, 14));
            labels[i].setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            
        	// 인풋영역
        	textFields[i] = new JTextField();
            textFields[i].setPreferredSize(new Dimension(350, 28));	// 크기 설정
            textFields[i].setMaximumSize(new Dimension(350, 28));	// 최대 크기 설정
            textFields[i].setMinimumSize(new Dimension(350, 28));	// 최소 크기 설정
            textFields[i].setAlignmentX(Component.LEFT_ALIGNMENT);	// 왼쪽정렬
            
            // JTextField에 카드 번호 무작위 생성
            if(i == 0) {
            	textFields[i].setText(cardNumFin);
            	textFields[i].setEditable(false); // 입력방지
            }
            
            // JTextField에 유효기간 무작위 생성
            if(i == 1) {
            	textFields[i].setText(cardLimitDateFin);
            	textFields[i].setEditable(false); // 입력방지
            }
        }
        
		
        p_center_mid.setLayout(new BoxLayout(p_center_mid, BoxLayout.Y_AXIS));
		for (int i = 0; i < 2; i++) {
			p_center_mid.add(labels[i]);
		    p_center_mid.add(textFields[i]);
		}
    	
    	
		// 하단 영역 : 버튼( 영수증출력 )
		JButton btnPrint = new JButton("결제진행");
		btnPrint.setBorderPainted(false);
		btnPrint.setBackground(new Color(30, 135, 61));
		btnPrint.setForeground(Color.WHITE);
		btnPrint.setFocusPainted(false);
		btnPrint.setPreferredSize(new Dimension(120, 40));
    	btnPrint.setFont(new Font("SansSerif", Font.BOLD, 14));
    	btnPrint.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    	btnPrint.setAlignmentX(Component.CENTER_ALIGNMENT);
    	

		
		// 컨텐츠 영역 : p_center_top 관련 패널에 넣기
    	p_south.setBorder(new EmptyBorder(0, 0, 50, 5));
    	p_south.setLayout(new FlowLayout(FlowLayout.CENTER));
		p_south.add(btnPrint);
        
        p_center_top.setLayout(new BorderLayout());
        p_center_top.add(p_center_top_tit,BorderLayout.NORTH);
        /* [E] 레이아웃 & 컨텐츠 영역 ========================================================== */
        
        
        
        
        
        // 결제 버튼 클릭 시 카드정보 전달 
    	btnPrint.addActionListener(e -> {
            // 카드 입력값 수집 (생성자 영역에 있으면 빈값으로 넘어감)
        	String mapCardNum = textFields[0].getText().trim();	// 카드 번호
        	String mapCardDate = textFields[1].getText().trim();	// 카드 유효기간
            
            // 카드정보 전달
        	cardInfoMap.put("cardNum", mapCardNum);
        	cardInfoMap.put("expirationDate", mapCardDate);
            
            // 모든 처리 완료 후 
            this.setVisible(false);
            new PaymentGUI(cartMap, cardInfoMap); // ← 넘겨주는 부분!
        });
        
        
        
        
        
        /* [공통] 최상위 부모 패널닫기  ====================================================== */
        p_center.setLayout(new BorderLayout());
        p_south.setLayout(new FlowLayout(FlowLayout.CENTER));

        
        // p_center_mid.add(totalLabel);
        p_center.add(p_center_top,BorderLayout.NORTH);
        p_center.add(p_center_mid,BorderLayout.CENTER);
        add(p_center,BorderLayout.CENTER);
        add(p_south,BorderLayout.SOUTH);

        
                
		/* [유지] 기본세팅 : 항시 소스 맨 밑에 배치 ====================================================== */
		setTitle("OSTK 편의점");
		setSize(375, 660);
	    setVisible(true);
	    setResizable(false); // 리사이즈 제어
	    setLocationRelativeTo(null); // 화면 정중앙 배치
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}
