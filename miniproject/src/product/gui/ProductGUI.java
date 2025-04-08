package product.gui;

import javax.swing.*;

import main.gui.MainGUI;
import main.gui.ProductManagementGUI;
import product.database.ProductDAO;
import product.database.ProductVO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class ProductGUI extends JFrame {
    private JTextField txtProductName, txtCostPrice, txtOptimalStock, txtSalePrice;
    private JCheckBox chkBeverage, chkSnack, chkCategory1, chkCategory2, chkEtc;
    private JButton btnRegister, btnCancel;

    public ProductGUI() {
        // 레이아웃 구성
        setLayout(new BorderLayout());
        JPanel p_top = new JPanel();        // 상단
        JPanel p_center = new JPanel();     // 컨텐츠 영역
        JPanel p_south = new JPanel();      // 하단
//        JLabel lblExit = new JLabel("메인으로 이동");
        
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
        
        // 배경색 흰색으로 수정
        p_top.setBackground(Color.WHITE);
        p_center.setBackground(Color.WHITE);
        p_south.setBackground(Color.WHITE);

        // 상단 영역 : 공통 버튼(뒤로가기)
        p_top.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton btnBack = new JButton("< 뒤로가기");
        btnBack.setBorderPainted(false);
        btnBack.setBackground(Color.WHITE);
        btnBack.setForeground(Color.BLACK);
        btnBack.setFocusPainted(false);
        btnBack.addActionListener(e -> {
            this.setVisible(false);
            new ProductManagementGUI();
        });
        p_top.add(btnBack);
        add(p_top, BorderLayout.NORTH);

        // 하단 영역 : 공통 레이블(관리자 화면 종료)
        p_south.setLayout(new FlowLayout(FlowLayout.CENTER));
//        p_south.add(lblExit);
        add(p_south, BorderLayout.SOUTH);

        setTitle("신규 상품 등록");
        setSize(375, 660);

        // 메인 컨텐츠 패널 설정
        p_center.setLayout(null);
        add(p_center, BorderLayout.CENTER);

        // 제목
        JLabel lblTitle = new JLabel("[신규 상품 등록]");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitle.setBounds(20, 50, 300, 30);
        p_center.add(lblTitle);

        // 상품명
        JLabel lblProductName = new JLabel("상품명");
        lblProductName.setBounds(20, 100, 100, 20);
        p_center.add(lblProductName);
        txtProductName = new JTextField();
        txtProductName.setBounds(20, 125, 320, 30);
        p_center.add(txtProductName);

        // 원가
        JLabel lblCostPrice = new JLabel("원가");
        lblCostPrice.setBounds(20, 165, 100, 20);
        p_center.add(lblCostPrice);
        txtCostPrice = new JTextField();
        txtCostPrice.setBounds(20, 190, 320, 30);
        p_center.add(txtCostPrice);

        // 적정재고
        JLabel lblOptimalStock = new JLabel("적정재고");
        lblOptimalStock.setBounds(20, 230, 100, 20);
        p_center.add(lblOptimalStock);
        txtOptimalStock = new JTextField();
        txtOptimalStock.setBounds(20, 255, 320, 30);
        p_center.add(txtOptimalStock);

        // 판매가
        JLabel lblSalePrice = new JLabel("판매가");
        lblSalePrice.setBounds(20, 295, 100, 20);
        p_center.add(lblSalePrice);
        txtSalePrice = new JTextField();
        txtSalePrice.setBounds(20, 320, 320, 30);
        p_center.add(txtSalePrice);

        // 카테고리
        JLabel lblCategory = new JLabel("카테고리");
        lblCategory.setBounds(20, 360, 100, 20);
        p_center.add(lblCategory);

        chkBeverage = new JCheckBox("음료");
        chkSnack = new JCheckBox("스낵");
        chkCategory1 = new JCheckBox("유제품");
        chkCategory2 = new JCheckBox("빙과류");
        chkEtc = new JCheckBox("기타");

        chkBeverage.setBounds(20, 385, 80, 30);
        chkSnack.setBounds(100, 385, 80, 30);
        chkCategory1.setBounds(180, 385, 100, 30);
        chkCategory2.setBounds(20, 415, 100, 30);
        chkEtc.setBounds(130, 415, 80, 30);
        
        // 체크박스 스타일링
//        Font checkFont = new Font("SansSerif", Font.PLAIN, 14);
        Color primaryColor = new Color(30, 135, 61); // 포인트 컬러

        for (JCheckBox cb : new JCheckBox[]{chkBeverage, chkSnack, chkCategory1, chkCategory2, chkEtc}) {
//            cb.setFont(checkFont);                  // 글씨 깔끔하게
            cb.setBackground(Color.WHITE);          // 배경 흰색
            cb.setForeground(Color.DARK_GRAY);      // 글씨 색 차분하게
            cb.setFocusPainted(false);              // 포커스 테두리 제거
            cb.setBorderPainted(false);             // 외곽선 제거
            cb.setOpaque(true);                     // 배경색 설정 적용되게 함
            cb.setIconTextGap(10);                  // 체크박스랑 텍스트 간격 조정
        }

        

        p_center.add(chkBeverage);
        p_center.add(chkSnack);
        p_center.add(chkCategory1);
        p_center.add(chkCategory2);
        p_center.add(chkEtc);

        // 등록 및 취소 버튼 패널
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.setBounds(20, 470, 320, 40);

        btnRegister = new JButton("등록");
        btnCancel = new JButton("취소");
        
        btnRegister.setPreferredSize(new Dimension(150, 40));
        btnRegister.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnRegister.setBackground(new Color(30, 135, 61));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFocusPainted(false);
        btnRegister.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnRegister.setOpaque(true);
        btnRegister.setContentAreaFilled(true);
        btnRegister.setBorderPainted(false);
        
        btnCancel.setPreferredSize(new Dimension(150, 40));
        btnCancel.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnCancel.setBackground(Color.WHITE); 
        btnCancel.setForeground(new Color(30, 135, 61)); 
        btnCancel.setFocusPainted(false);
        btnCancel.setBorder(BorderFactory.createLineBorder(new Color(30, 135, 61), 2));
        btnCancel.setOpaque(true);
        btnCancel.setContentAreaFilled(true);
        

        btnCancel.addActionListener(e -> {
            this.setVisible(false);
            new ProductManagementGUI();
        });

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerProduct();
            }
        });

        btnPanel.add(btnRegister);
        btnPanel.add(btnCancel);
        p_center.add(btnPanel);

    	setTitle("신규 상품 등록");
		setSize(375, 660);
	    setVisible(true);
        setLocationRelativeTo(null); // 화면 중앙에 표시
	    setResizable(false); // 리사이즈 제어
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
    }

    private void registerProduct() {
    	
    	String productName = txtProductName.getText().trim();
        String costPriceStr = txtCostPrice.getText().trim();
        String optimalStockStr = txtOptimalStock.getText().trim();
        String salePriceStr = txtSalePrice.getText().trim();

        // 공백 검증
        if (productName.isEmpty() || costPriceStr.isEmpty() || 
            optimalStockStr.isEmpty() || salePriceStr.isEmpty() || 
            !(chkBeverage.isSelected() || chkSnack.isSelected() || chkCategory1.isSelected() || 
              chkCategory2.isSelected() || chkEtc.isSelected())) {
            
            JOptionPane.showMessageDialog(this, "상품정보를 모두 올바르게 설정해주십시오.", "입력 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 파싱
        int costPrice = Integer.parseInt(costPriceStr);
        int optimalStock = Integer.parseInt(optimalStockStr);
        int salePrice = Integer.parseInt(salePriceStr);
        int stock = 0;
        
        String productId = generateRandomProductId();
        productName = txtProductName.getText();
        costPrice = Integer.parseInt(txtCostPrice.getText());
        optimalStock = Integer.parseInt(txtOptimalStock.getText());
        salePrice = Integer.parseInt(txtSalePrice.getText());

        // 카테고리 선택
        String categoryName = "";
        if (chkBeverage.isSelected()) categoryName = "음료";
        else if (chkSnack.isSelected()) categoryName = "스낵";
        else if (chkCategory1.isSelected()) categoryName = "유제품";
        else if (chkCategory2.isSelected()) categoryName = "빙과류";
        else if (chkEtc.isSelected()) categoryName = "기타";

        // 카테고리 ID 조회
        ProductDAO dao = new ProductDAO();
        int categoryId = dao.getCategoryIdByName(categoryName);

        // 데이터 객체 생성
        ProductVO product = new ProductVO(productId, categoryId, productName, optimalStock, stock, costPrice, salePrice, null, null);
        
        // DB 저장
        dao.insertProduct(product);

        // 성공 메시지
        JOptionPane.showMessageDialog(this, "상품등록이 완료되었습니다.", "등록 완료", JOptionPane.INFORMATION_MESSAGE);

        // 입력 필드 초기화
        txtProductName.setText("");
        txtCostPrice.setText("");
        txtOptimalStock.setText("");
        txtSalePrice.setText("");

        // 체크박스 초기화
        chkBeverage.setSelected(false);
        chkSnack.setSelected(false);
        chkCategory1.setSelected(false);
        chkCategory2.setSelected(false);
        chkEtc.setSelected(false);
    }

    // 상품 아이디 13자리 랜덤 생성자 
    private String generateRandomProductId() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 13; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
