package product.gui;

import javax.swing.*;
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
        
        setLayout(new BorderLayout());
        JPanel p_top = new JPanel();
        JPanel p_center = new JPanel();
        JPanel p_south = new JPanel();
        JLabel lblExit = new JLabel("관리자 화면 종료");

        p_top.setBackground(Color.WHITE);
        p_south.setBackground(Color.WHITE);

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
        p_south.setBackground(Color.WHITE); // 배경색 설정
        p_south.add(lblExit);
        add(p_south, BorderLayout.SOUTH);

        setTitle("상품 수정");
        setSize(375, 660);

        p_center.setLayout(null);
        add(p_center, BorderLayout.CENTER);

        JLabel lblTitle = new JLabel("[상품 수정]");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitle.setBounds(20, 50, 300, 30);
        p_center.add(lblTitle);

        JLabel lblProductName = new JLabel("상품명");
        lblProductName.setBounds(20, 100, 100, 20);
        p_center.add(lblProductName);
        txtProductName = new JTextField(product.getProductName());
        txtProductName.setBounds(20, 125, 320, 30);
        p_center.add(txtProductName);

        JLabel lblSalePrice = new JLabel("판매가");
        lblSalePrice.setBounds(20, 165, 100, 20);
        p_center.add(lblSalePrice);
        txtSalePrice = new JTextField(String.valueOf(product.getSalePrice()));
        txtSalePrice.setBounds(20, 190, 320, 30);
        p_center.add(txtSalePrice);

        JLabel lblOptimalStock = new JLabel("적정재고");
        lblOptimalStock.setBounds(20, 230, 100, 20);
        p_center.add(lblOptimalStock);
        txtOptimalStock = new JTextField(String.valueOf(product.getOptimalStock()));
        txtOptimalStock.setBounds(20, 255, 320, 30);
        p_center.add(txtOptimalStock);

        JLabel lblStock = new JLabel("재고수량");
        lblStock.setBounds(20, 295, 100, 20);
        p_center.add(lblStock);
        txtStock = new JTextField(String.valueOf(product.getStock()));
        txtStock.setBounds(20, 320, 320, 30);
        p_center.add(txtStock);

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

        p_center.add(chkBeverage);
        p_center.add(chkSnack);
        p_center.add(chkCategory1);
        p_center.add(chkCategory2);
        p_center.add(chkEtc);

        setCategoryCheckBoxes();

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnPanel.setBounds(20, 470, 320, 40);

        btnEdit = new JButton("수정");
        btnCancel = new JButton("취소");
        btnEdit.setPreferredSize(new Dimension(150, 40));
        btnCancel.setPreferredSize(new Dimension(150, 40));

        btnCancel.addActionListener(e -> returnToUpdateGUI());
        btnEdit.addActionListener(e -> updateProduct());

        btnPanel.add(btnEdit);
        btnPanel.add(btnCancel);
        p_center.add(btnPanel);

        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

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

    private void returnToUpdateGUI() {
        this.setVisible(false);
        new ProductUpdateGUI();
    }
}