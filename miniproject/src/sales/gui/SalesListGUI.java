package sales.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import product.database.ProductVO;



// 영수증 출력 화면
public class SalesListGUI extends JFrame{
	JTable table;
	JTextField Jinput;
	JLabel Jlabel;
	
	// 총합계 : 지역 변수로 사용시 내부에서 자유롭게 사용하기 어려움이 있어, 맴버변수로 변환 
    int totalPrice = 0;
    
	
	// 화면 구성 (생성자)
	public SalesListGUI(Map<ProductVO, Integer> cartMap) {
		// 레이아웃 구성
		setLayout(new BorderLayout());
		JPanel p_top = new JPanel();		// 상단
		JPanel p_center = new JPanel();		// 컨텐츠 영역
		JPanel p_center_top = new JPanel();
		JPanel p_center_mid = new JPanel();
		JPanel p_center_btm = new JPanel();
		JPanel p_south = new JPanel();		// 하단
		JPanel p_south_btn = new JPanel();
		
		// 공통 컴포넌트 구성 : 버튼
		JButton btnTypeG = new JButton("영수증 출력");
		
		
		
		/* [S : 공통] 레이아웃영역  ====================================================== */
		// 상단 영역 : 멘트 영역
		p_top.setLayout(new FlowLayout(FlowLayout.CENTER));
		p_top.setBackground(Color.WHITE); // 배경화면 설정
		JLabel labelMent = new JLabel("구매해 주셔서 감사합니다.");
		p_top.add(labelMent);
		add(p_top,BorderLayout.NORTH);
		/* [E : 공통] 레이아웃영역  ====================================================== */
		
		
		
		
		
		/* [S] 컨텐츠 영역 : p_center_top ========================================================== */
		// 컨텐츠 영역 : p_center_top 관련 패널 생성
        JPanel p_center_top_tit = new JPanel(); // 패널 생성
        p_center_top_tit.setLayout(new FlowLayout(FlowLayout.CENTER)); // 정렬은 되지만 한줄을 넘어가면 안보임
        p_center_top_tit.setBackground(Color.WHITE); // 배경화면 설정
        
        JPanel receipt_details_top = new JPanel(); // 패널 생성 
		receipt_details_top.setLayout(new GridLayout(2, 2, 5, 5)); // 2행 2열, 여백 5px
		receipt_details_top.setBackground(Color.WHITE); // 배경화면 설정
		
		JPanel receipt_details_btm = new JPanel(); // 패널 생성
     	receipt_details_btm.setLayout(new GridLayout(2, 2, 5, 5)); // 2행 2열, 여백 5px
     	receipt_details_btm.setBackground(Color.WHITE); // 배경화면 설정
     	
        
        // 컨텐츠 영역 : 페이지 타이틀 + 상단 공통 정보
        JLabel labelTitle = new JLabel("영수증");
		labelTitle.setAlignmentX(SalesListGUI.CENTER_ALIGNMENT);
		labelTitle.setFont(new Font("SansSerif", Font.BOLD, 26));
		p_center_top_tit.add(labelTitle); // 페이지 타이틀 넣기
		
     	
        // 컴포넌트 생성
        JLabel titLabel = new JLabel("코스타 편의점");
        JLabel storeLabel = new JLabel("종각점 #12345");
        JLabel callLabel = new JLabel("012-3456-7890");
        JLabel addressLabel = new JLabel("서울특별시 종로구 우정국로 2길 21 대왕빌딩 7층");
        titLabel.setBorder(new EmptyBorder(5, 5, 0, 5)); 
        storeLabel.setBorder(new EmptyBorder(5, 65, 5, 5));
        callLabel.setBorder(new EmptyBorder(0, 5, 5, 5));
        addressLabel.setBorder(new EmptyBorder(5, 5, 5, 5)); 
        titLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        storeLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        callLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        addressLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        
        
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
        p_center_top.setBackground(Color.WHITE); // 배경화면 설정
        /* [E] 컨텐츠 영역 : p_center_top ========================================================== */
        
        
        
        
        
        /* [S] 컨텐츠 영역 : p_center_mid ========================================================== */
        // 컨텐츠 영역 : p_center_mid 관련 패널 생성
        JPanel p_center_mid_top = new JPanel(); // 패널 생성
        p_center_mid_top.setLayout(new GridLayout(0, 1, 5, 5)); // 2행 1열, 여백 5px 
        p_center_mid_top.setBackground(Color.WHITE); // 배경화면 설정
        
        JPanel receipt_mid_list = new JPanel(); // 패널 생성 
        receipt_mid_list.setLayout(new GridLayout(1, 3, 0, 0)); // (new FlowLayout(FlowLayout.CENTER));// 3행 1열, 여백 5px
        receipt_mid_list.setBackground(Color.WHITE); // 배경화면 설정
        // receipt_mid_list.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, Color.BLACK));
		
		JPanel receipt_mid_btm = new JPanel(); // 패널 생성 
		receipt_mid_btm.setLayout(new GridLayout(2, 2, 5, 5)); // 3행 2열, 여백 5px
		receipt_mid_btm.setBackground(Color.WHITE); // 배경화면 설정
		
		// 컴포넌트 생성
        JLabel titCardLabel = new JLabel("카드");
        // JLabel saleDateLabel = new JLabel("[판매] 2025-03-19 (수) 18:11:47");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd (E) HH:mm:ss");
        String now = LocalDateTime.now().format(formatter);
        JLabel saleDateLabel = new JLabel("[판매] " + now);
        titCardLabel.setAlignmentX(SalesListGUI.CENTER_ALIGNMENT);
        titCardLabel.setBorder(new EmptyBorder(5, 160, 5, 140));
        saleDateLabel.setBorder(new EmptyBorder(5, 5, 5, 10));
        titCardLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        saleDateLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        
        
        
        // 컬럼명 (헤더)
        String[] columnNames = {"상품명", "수량", "금액"};
        Object[][] data = new Object[cartMap.size()][3];
        int row = 0;
        for (Map.Entry<ProductVO, Integer> entry : cartMap.entrySet()) {
            ProductVO product = entry.getKey();
            int quantity = entry.getValue();
            int price = product.getSalePrice() * quantity;
            totalPrice += price;
            
            data[row][0] = product.getProductName();
            data[row][1] = quantity;
            data[row][2] = NumberFormat.getInstance().format(price) + "원";
            row++;
        }
        
        
        
        // JTable 생성
        table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true); // 테이블이 뷰포트 전체를 채우도록 설정

        // JTable 스타일링 (옵션)
        table.setRowHeight(28); // 행 높이 설정

        // 열 너비 설정
        table.getColumnModel().getColumn(0).setPreferredWidth(150); // 상품명
        table.getColumnModel().getColumn(1).setPreferredWidth(50);  // 수량
        table.getColumnModel().getColumn(2).setPreferredWidth(100); // 금액
        
        table.getTableHeader().setFont(new Font("SansSerif", Font.PLAIN, 16)); // 헤더 폰트 설정
        table.setFont(new Font("SansSerif", Font.PLAIN, 16)); // 데이터 폰트 설정
        table.setOpaque(false);  // 투명하게 설정
        table.setBackground(new Color(0, 0, 0, 0));  // 완전 투명
        table.setShowGrid(false); // 셀의 테두리 제거 (옵션)
        
        table.getTableHeader().setOpaque(false); // 헤더 배경 투명하게
        table.getTableHeader().setBackground(new Color(0, 0, 0, 0)); // 완전 투명
        table.getTableHeader().setBorder(null); // 테두리 제거
        table.setShowGrid(false); // 셀 테두리 제거
        table.setIntercellSpacing(new Dimension(0, 0)); // 셀 간격 제거
        
        // JTable을 JScrollPane에 추가 (스크롤 가능하게 함)
        JScrollPane scrollPane = new JScrollPane(table);        
        scrollPane.setBorder(null); // 테두리 제거 
        

        // 프레임에 추가
        p_center_mid_top.add(titCardLabel);
        p_center_mid_top.add(saleDateLabel);
        receipt_mid_list.add(scrollPane);
        
        
		// 컨텐츠 영역 : p_center_top 관련 패널에 넣기
     	p_center_mid.setLayout(new BorderLayout());
     	p_center_mid.add(p_center_mid_top,BorderLayout.NORTH);
     	p_center_mid.add(receipt_mid_list,BorderLayout.CENTER);
     	p_center_mid.setBackground(Color.WHITE); // 배경화면 설정
        /* [E] 컨텐츠 영역 : p_center_mid ========================================================== */
     	
     	
     	
     	
     	
     	/* [S] 컨텐츠 영역 : p_center_btm ========================================================== */
     	JPanel total_price = new JPanel(); // 패널 생성 
     	total_price.setLayout(new GridLayout(1, 2, 5, 5)); // 2행 2열, 여백 5px
		
     	JLabel totalText = new JLabel("합계");
        JLabel totalPriceText = new JLabel( NumberFormat.getInstance().format(totalPrice)); // 총합
        totalText.setBorder(new EmptyBorder(5, 0, 5, 0));
        totalPriceText.setBorder(new EmptyBorder(5, 35, 5, 35));
        totalText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        totalPriceText.setFont(new Font("SansSerif", Font.PLAIN, 20));
        
        total_price.add(totalText);
        total_price.add(totalPriceText);
        total_price.setBackground(Color.WHITE); // 배경화면 설정
        p_center_btm.setLayout(new BorderLayout());
        p_center_btm.add(total_price,BorderLayout.SOUTH);
        p_center_btm.setBackground(Color.WHITE); // 배경화면 설정
     	/* [E] 컨텐츠 영역 : p_center_btm ========================================================== */
	
		
        
     	
     	
        /* [공통] 버튼영역  ====================================================== */
		// 컨텐츠 영역 : 버튼
     	p_south_btn.setLayout(new FlowLayout(FlowLayout.CENTER));
        p_south_btn.add(btnTypeG);
        p_south_btn.setBackground(Color.WHITE); // 배경화면 설정
		btnTypeG.setPreferredSize(new Dimension(120, 28)); // 버튼 사이즈 설정

		
		
		/* [공통] 최상위 부모 패널닫기  ====================================================== */
		// 최종 컨텐츠 닫기 : p_center, p_center
        p_center.setLayout(new BorderLayout());
        p_center.add(p_center_top,BorderLayout.NORTH);
        p_center.add(p_center_mid,BorderLayout.CENTER);
        p_center.add(p_center_btm,BorderLayout.SOUTH);
        p_south.add(p_south_btn,BorderLayout.NORTH);
        p_south.setBackground(Color.WHITE); // 배경화면 설정
		add(p_center,BorderLayout.CENTER);	
		add(p_south,BorderLayout.SOUTH);
	
		
		/* [유지] 기본세팅 : 항시 소스 맨 밑에 배치 ====================================================== */
		setTitle("무인편의점 키오스크 : 영수증");
		setSize(375, 660);
	    setVisible(true);
	    setResizable(false); // 리사이즈 제어
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
