package payment.gui;

import java.awt.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.time.format.DateTimeParseException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import common.gui.Component;
import main.gui.MainGUI;
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

public class PaymentGUI extends JFrame {
	PaymentDAO dao = new PaymentDAO();
    
	// 카드 승인번호 랜덤 출력 
	double random = Math.random();
	int cardVerInt = 10000000 + (int)(random * 90000000); // 8자리 출력
	String cardVer = String.valueOf(cardVerInt);
	
	// 구매 날짜 변환  
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	// 총합계 : 지역 변수로 사용시 내부에서 자유롭게 사용하기 어려움이 있어, 맴버변수로 변환 
    int totalPrice = 0;
	
    public PaymentGUI(Map<ProductVO, Integer> cartMap) {
    	// 레이아웃 구성
    	setLayout(new BorderLayout());
    	JPanel p_top = new JPanel();
    	JPanel itemListPanel = new JPanel();
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
    	itemListPanel.setLayout(new BoxLayout(itemListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(itemListPanel);
    	
        JPanel p_center_top_tit = new JPanel(); // 패널 생성
        p_center_top_tit.setLayout(new FlowLayout(FlowLayout.LEFT)); // 정렬은 되지만 한줄을 넘어가면 안보임

        
        // 컨텐츠 영역 : 페이지 타이틀
        JLabel labelTitle = new JLabel("카드 결제");
        labelTitle.setAlignmentX(Component.LEFT_ALIGNMENT);	// 왼쪽정렬
		labelTitle.setBorder(new EmptyBorder(0, 0, 10, 20)); // 간격넣기
		labelTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
		p_center_top_tit.add(labelTitle); // 페이지 타이틀 넣기
		p_center_top_tit.setBackground(Color.WHITE); // 배경화면 설정
		
		
		// 컨텐츠 영역 : 라벨 + 인풋
        JTextField[] textFields = new JTextField[3];
        JLabel[] labels = new JLabel[3];
        
        // 라벨 텍스트 배열
        String[] labelTexts = {"카드번호", "유효기간", "판매일자"}; // "판매금액",
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
            
            // JTextField에 오늘 날짜 입력
            if(i == 2) {
            	textFields[i].setText(LocalDate.now().format(formatter));
            	textFields[i].setEditable(false); // 입력방지
            }
        }
        
		
        p_center_mid.setLayout(new BoxLayout(p_center_mid, BoxLayout.Y_AXIS));
		for (int i = 0; i < 3; i++) {
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
        
        
        
        
        JLabel labelListTit = new JLabel("구매 상세내역 ------------------------ ");
        labelListTit.setFont(new Font("SansSerif", Font.BOLD, 14));
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setPreferredSize(new Dimension(330, 195));
        
        itemListPanel.setBackground(Color.WHITE);
        itemListPanel.add(labelListTit);

        
        List<SalesProductVO> spvList = new ArrayList<>();
        for (Map.Entry<ProductVO, Integer> entry : cartMap.entrySet()) {
            ProductVO product = entry.getKey();
            int quantity = entry.getValue();
            int price = product.getSalePrice() * quantity;
            totalPrice += price;

            JLabel label = new JLabel(product.getProductName() + " - " + quantity + "개 / " +
                    NumberFormat.getInstance().format(price) + "원");
            label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            itemListPanel.add(label);
            
         
            // 총 상품 한 건씩 넣기 
            SalesProductVO spv = new SalesProductVO(
                null,                      	// sales_id
                product.getProductId(),		// product_id
                quantity,					// sales_quantity
                product.getSalePrice(),		// sale_price_at
                product.getCostPrice()		// cost_price_at
            );
            spvList.add(spv);
        }
        
        
        JLabel totalLabel = new JLabel("총 금액: " + NumberFormat.getInstance().format(totalPrice) + "원", JLabel.CENTER);
        totalLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        totalLabel.setBorder(BorderFactory.createEmptyBorder(23, 0, 10, 0));

        
        
        // 결제 버튼 클릭 시 장바구니 내역 전달 
    	btnPrint.addActionListener(e -> {
            // S : textFields 입력값가져오기 
            for (int i = 0; i < textFields.length; i++) {
                String value = textFields[i].getText();
                System.out.println("TextField[" + i + "] 값: " + value);
            }
            String cardNum = textFields[0].getText();
            java.sql.Date expirationDate;       
            // E : textFields 입력값가져오기 
            
            // 카드번호 길이 유효성 검사 
            if (cardNum.length() != 16) {
                JOptionPane.showMessageDialog(this, "카드 번호를 확인해 주세요 (16자리 입력)");
                return;
            }
            
            // 카드 유효일자 유효성 검사 (과거 날짜 방지) 
            try {
            	String inputDate = textFields[1].getText().trim();
            	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            	LocalDate parsedDate = LocalDate.parse(inputDate, formatter);
	        	
            	// 과거 날짜 방지 
	            if(parsedDate.isBefore(LocalDate.now())) {
	            	JOptionPane.showMessageDialog(this, "카드 유효일자는 오늘 이후의 날짜로 입력해 주세요.");
	                return;	
	            }
	            
	            expirationDate = java.sql.Date.valueOf(parsedDate); // DB용 변환
	        	// expirationDate = java.sql.Date.valueOf(textFields[1].getText().trim()); 
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "카드 유효일자를 올바른 형식(yyyy-mm-dd)으로 입력해 주세요.");
                return;
            }
            
            
            // VO객체 생성
            SalesVO sv =  new SalesVO(
            		null,				// sales_id
            		null,				// sales_date
            		totalPrice, 		// sales_total
            		cardNum, 			// card_num
            		expirationDate, 	// expiration_date
            		cardVer);			// card_ver
           
            // 구매내역 인서트 &  salesId 받기 
            int salesId = dao.insertCardInfo(sv);

            // salesId 받기 실패시 (= 저장실패시)  
            if (salesId <= 0) {
                JOptionPane.showMessageDialog(this, "결제 저장 실패");
                return;
            }
            
            // 판매 ID를 판매상품 VO에 넣어주기
            for (SalesProductVO spv : spvList) {
                spv.setSalesId(salesId);
            }
            
            // 구매내역의 아이템별 정보 인서트 
            dao.insertCardProduct(spvList);
            
            // 모든 처리 완료 후 
            this.setVisible(false);
            new SalesListGUI(cartMap); // ← 넘겨주는 부분!
        });
        
        
        
        
        
        /* [공통] 최상위 부모 패널닫기  ====================================================== */
        p_center.setLayout(new BorderLayout());
        p_south.setLayout(new FlowLayout(FlowLayout.CENTER));

        
        p_center_mid.add(totalLabel);
        p_center.add(p_center_top,BorderLayout.NORTH);
        p_center.add(p_center_mid,BorderLayout.CENTER);
        p_center.add(scrollPane,BorderLayout.SOUTH);
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
