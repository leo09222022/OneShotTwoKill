package product.gui;

import product.database.ProductDAO;
import product.database.ProductVO;

import javax.swing.*;

import main.gui.ProductManagementGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductUpdateGUI extends JFrame {
    private ProductDAO productDAO;
    private JPanel productPanel;
    private JTextField txtSearch;
    
    public ProductUpdateGUI() {
        productDAO = new ProductDAO();
        
     // 레이아웃 구성
        setLayout(new BorderLayout());
        JPanel p_top = new JPanel();        // 상단
        JPanel p_center = new JPanel();     // 컨텐츠 영역
        JPanel p_south = new JPanel();      // 하단
        JLabel lblExit = new JLabel("관리자 화면 종료");
        
        p_top.setBackground(Color.WHITE);
        p_south.setBackground(Color.WHITE);
        
        
        setTitle("상품 수정");
        setSize(400, 600);
        setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        p_top.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton btnBack = new JButton("< 뒤로가기");
        btnBack.setBorderPainted(false);
        btnBack.setBackground(Color.WHITE);
        btnBack.setForeground(Color.BLACK);
        btnBack.setFocusPainted(false);
        // 뒤로가기를 누르면 ProductManagementGUI 화면으로 돌아간다. 
        btnBack.addActionListener(e -> {
            this.setVisible(false);
            new ProductManagementGUI();
        });
        p_top.add(btnBack);
        add(p_top, BorderLayout.NORTH);
        topPanel.add(btnBack);
        
        add(topPanel, BorderLayout.NORTH);
        
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());
        txtSearch = new JTextField(20);
        JButton btnSearch = new JButton("검색");
        btnSearch.addActionListener(e -> searchProduct());
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        
        add(searchPanel, BorderLayout.CENTER);
        
        productPanel = new JPanel();
        productPanel.setLayout(new GridLayout(0, 1));
        
        JScrollPane scrollPane = new JScrollPane(productPanel);
        add(scrollPane, BorderLayout.SOUTH);
        
        loadProducts();
        
        setVisible(true);
        setResizable(false); // 리사이즈 제어
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    // 모든 상품들을 출력한다. 
    private void loadProducts() {
        productPanel.removeAll();
        List<ProductVO> products = productDAO.getAllProducts();
        for (ProductVO product : products) {
        	// 상품들마다 "수정", "X" 버튼들이 옆에 생긴다. 
            addProductRow(product);
        }
        productPanel.revalidate();
        productPanel.repaint();
    }
    
    // 상품정보를 나열하며 옆에 "수정", "X" 버튼들을 나열한다. 
    // 각각 버튼들에게 액션을 부여한다. 
    private void addProductRow(ProductVO product) {
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnEdit = new JButton("수정");
        JButton btnDelete = new JButton("X");
        
        JLabel lblProduct = new JLabel(product.getProductName() + " " + product.getStock() + "개 " + product.getSalePrice());
        
        btnEdit.addActionListener(e -> openEditScreen(product));
        btnDelete.addActionListener(e -> deleteProduct(product));
        
        // 수정 버튼, 상품 정보들, X 버튼을 파넬에 담는다. 
        rowPanel.add(btnEdit);
        rowPanel.add(lblProduct);
        rowPanel.add(btnDelete);
        
        productPanel.add(rowPanel);
    }
    
    // [수정] 버튼을 누를시 해당 메소드를 작동시킨다. 
    private void openEditScreen(ProductVO product) {
        this.setVisible(false); // 현재 창 숨김
        new ProductEditGUI(this, product); // ProductUpdateGUI 인스턴스를 넘겨줌
    }

    // X를 선택시 재고를 0으로 업데이트 한다. 
    private void deleteProduct(ProductVO product) {
        int option = JOptionPane.showConfirmDialog(this, 
            product.getProductName() + " 상품의 재고를 0으로 설정하시겠습니까?", 
            "재고 변경 확인", JOptionPane.YES_NO_OPTION);
        
        if (option == JOptionPane.YES_OPTION) {
            productDAO.updateStockToZero(product.getProductId()); // 재고 0으로 변경
            loadProducts(); // 변경된 목록 다시 로드
        }
    }
    
    // [검색] 버튼을 누를 시 상품 이름과 유사한 상품들을 전부 출력해준다. 
    private void searchProduct() {
        String keyword = txtSearch.getText();
        List<ProductVO> products = productDAO.searchProductsByName(keyword);
        productPanel.removeAll();
        for (ProductVO product : products) {
            addProductRow(product);
        }
        productPanel.revalidate();
        productPanel.repaint();
    }
}
