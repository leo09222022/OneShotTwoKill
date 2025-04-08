package product.gui;

import javax.swing.*;

import main.gui.MainGUI;
import product.database.ProductDAO;
import product.database.ProductVO;
import java.awt.*;

public class ProductEditGUI extends JFrame {
    private JTextField txtProductName, txtSalePrice, txtOptimalStock, txtStock;
    private JCheckBox chkBeverage, chkSnack, chkCategory1, chkCategory2, chkEtc;
    private JButton btnEdit, btnCancel;
    private ProductVO product;

    public ProductEditGUI(ProductVO product) {
        this.product = product;

        // 레이아웃 구성
        setLayout(new BorderLayout());
        JPanel p_top = new JPanel();        // 상단
        JPanel p_center = new JPanel();     // 컨텐츠 영역
        JPanel p_south = new JPanel();      // 하단
//        JLabel lblExit = new JLabel("메인으로 이동");
        
        // 메인으로 이동 버튼 추가
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
        

        // 배경색 흰색으로 설정
        Color primaryColor = new Color(30, 135, 61);
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
        btnBack.addActionListener(e -> returnToUpdateGUI());
        p_top.add(btnBack);
        add(p_top, BorderLayout.NORTH);

        // 하단 영역 : 공통 레이블(관리자 화면 종료)
        p_south.setLayout(new FlowLayout(FlowLayout.CENTER));
//        p_south.add(lblExit);
        add(p_south, BorderLayout.SOUTH);

        setTitle("상품 수정");
        setSize(375, 660);

        // 메인 컨텐츠 패널 설정
        p_center.setLayout(null);
        add(p_center, BorderLayout.CENTER);

        // 제목
        JLabel lblTitle = new JLabel("[상품 수정]");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitle.setBounds(20, 50, 300, 30);
        p_center.add(lblTitle);

        // 상품명
        JLabel lblProductName = new JLabel("상품명");
        lblProductName.setBounds(20, 100, 100, 20);
        p_center.add(lblProductName);
        txtProductName = new JTextField(product.getProductName());
        txtProductName.setBounds(20, 125, 320, 30);
        p_center.add(txtProductName);

        // 판매가
        JLabel lblSalePrice = new JLabel("판매가");
        lblSalePrice.setBounds(20, 165, 100, 20);
        p_center.add(lblSalePrice);
        txtSalePrice = new JTextField(String.valueOf(product.getSalePrice()));
        txtSalePrice.setBounds(20, 190, 320, 30);
        p_center.add(txtSalePrice);

        // 적정재고
        JLabel lblOptimalStock = new JLabel("적정재고");
        lblOptimalStock.setBounds(20, 230, 100, 20);
        p_center.add(lblOptimalStock);
        txtOptimalStock = new JTextField(String.valueOf(product.getOptimalStock()));
        txtOptimalStock.setBounds(20, 255, 320, 30);
        p_center.add(txtOptimalStock);

        // 재고수량
        JLabel lblStock = new JLabel("재고수량");
        lblStock.setBounds(20, 295, 100, 20);
        p_center.add(lblStock);
        txtStock = new JTextField(String.valueOf(product.getStock()));
        txtStock.setBounds(20, 320, 320, 30);
        p_center.add(txtStock);

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

        // 카테고리 체크박스가 기존 상품들에 대해 이미 체크가 되어있도록 설정한다.
        setCategoryCheckBoxes();

        // 수정 및 취소 버튼 패널
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.setBounds(20, 470, 320, 40);

        btnEdit = new JButton("수정");
        btnCancel = new JButton("취소");

        // 수정 버튼 스타일
        btnEdit.setPreferredSize(new Dimension(150, 40));
        btnEdit.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnEdit.setBackground(primaryColor);
        btnEdit.setForeground(Color.WHITE);
        btnEdit.setFocusPainted(false);
        btnEdit.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnEdit.setOpaque(true);
        btnEdit.setContentAreaFilled(true);
        btnEdit.setBorderPainted(false);

        // 취소 버튼 스타일
        btnCancel.setPreferredSize(new Dimension(150, 40));
        btnCancel.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnCancel.setBackground(Color.WHITE);
        btnCancel.setForeground(primaryColor);
        btnCancel.setFocusPainted(false);
        btnCancel.setBorder(BorderFactory.createLineBorder(primaryColor, 2));
        btnCancel.setOpaque(true);
        btnCancel.setContentAreaFilled(true);

        btnCancel.addActionListener(e -> returnToUpdateGUI());
        btnEdit.addActionListener(e -> updateProduct());

        btnPanel.add(btnEdit);
        btnPanel.add(btnCancel);
        p_center.add(btnPanel);

        setTitle("상품수정");
        setSize(375, 660);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // 체크박스가 원래 카테고리와 맞는 옵션으로 체크된 상태로 구현한다.
    private void setCategoryCheckBoxes() {
        ProductDAO dao = new ProductDAO();
        String categoryName = dao.getCategoryNameById(product.getCategoryId());

        if ("음료".equals(categoryName)) chkBeverage.setSelected(true);
        else if ("스낵".equals(categoryName)) chkSnack.setSelected(true);
        else if ("유제품".equals(categoryName)) chkCategory1.setSelected(true);
        else if ("빙과류".equals(categoryName)) chkCategory2.setSelected(true);
        else if ("기타".equals(categoryName)) chkEtc.setSelected(true);
    }

    private void updateProduct() {
        ProductDAO dao = new ProductDAO();
        product.setProductName(txtProductName.getText());
        product.setSalePrice(Integer.parseInt(txtSalePrice.getText()));
        product.setOptimalStock(Integer.parseInt(txtOptimalStock.getText()));
        product.setStock(Integer.parseInt(txtStock.getText()));

        String categoryName = "";
        if (chkBeverage.isSelected()) categoryName = "음료";
        else if (chkSnack.isSelected()) categoryName = "스낵";
        else if (chkCategory1.isSelected()) categoryName = "유제품";
        else if (chkCategory2.isSelected()) categoryName = "빙과류";
        else if (chkEtc.isSelected()) categoryName = "기타";

        product.setCategoryId(dao.getCategoryIdByName(categoryName));
        dao.updateProduct(product);

        JOptionPane.showMessageDialog(this, "상품 정보가 수정되었습니다.", "수정 완료", JOptionPane.INFORMATION_MESSAGE);
        returnToUpdateGUI();
    }

    // 이전 화면으로 돌아간다.
    private void returnToUpdateGUI() {
        this.setVisible(false);
        new ProductUpdateGUI();
    }
}
