package product.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import main.gui.ProductManagementGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import product.database.ProductDAO;
import product.database.ProductVO;

public class ProductEditGUI extends JFrame {
    private JTextField txtSearch;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private ProductDAO productDAO;
    
    private ProductUpdateGUI parentGUI; // 이전 화면 저장

    // 생성자 변경
    public ProductEditGUI(ProductUpdateGUI parent, ProductVO product) {
    	this.parentGUI = parent;
    	productDAO = new ProductDAO();
    	
    	// 레이아웃 구성
        setLayout(new BorderLayout());
        JPanel p_top = new JPanel();        // 상단
        JPanel p_center = new JPanel();     // 컨텐츠 영역
        JPanel p_south = new JPanel();      // 하단
        JLabel lblExit = new JLabel("관리자 화면 종료");
        
        p_top.setBackground(Color.WHITE);
        p_south.setBackground(Color.WHITE);
     
    	setLayout(new BorderLayout());
    	setTitle("상품 수정");
    	setSize(400, 600);
     
    	JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	JButton btnBack = new JButton("< 뒤로가기");
    	btnBack.setBorderPainted(false);
    	btnBack.setBackground(Color.WHITE);
    	btnBack.setForeground(Color.BLACK);
    	btnBack.setFocusPainted(false);

    	btnBack.addActionListener(e -> {
            this.setVisible(false);
            new ProductUpdateGUI();
        });

     topPanel.add(btnBack);
     add(topPanel, BorderLayout.NORTH);
     
     productDAO = new ProductDAO();
     
     setLayout(new BorderLayout());
     setTitle("상품 수정");
     setSize(400, 600);
     
     btnBack.setBorderPainted(false);
     btnBack.setBackground(Color.WHITE);
     btnBack.setForeground(Color.BLACK);
     btnBack.setFocusPainted(false);
     
     topPanel.add(btnBack);
     add(topPanel, BorderLayout.NORTH);
     
     JPanel searchPanel = new JPanel(new FlowLayout());
     txtSearch = new JTextField(15);
     JButton btnSearch = new JButton("검색");
     btnSearch.addActionListener(e -> loadProducts(txtSearch.getText()));
     searchPanel.add(new JLabel("상품명"));
     searchPanel.add(txtSearch);
     searchPanel.add(btnSearch);
     add(searchPanel, BorderLayout.CENTER);
     
     tableModel = new DefaultTableModel(new String[]{"상품명", "재고", "가격", "수정", "삭제"}, 0);
     productTable = new JTable(tableModel);
     add(new JScrollPane(productTable), BorderLayout.SOUTH);

     setVisible(true);
     setResizable(false);
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void loadProducts(String query) {
        tableModel.setRowCount(0);
        List<ProductVO> products = productDAO.findAll();
        for (ProductVO product : products) {
            if (query.isEmpty() || product.getProductName().contains(query)) {
                JButton editButton = new JButton("수정");
                JButton deleteButton = new JButton("X");
                editButton.addActionListener(e -> openEditScreen(product));
                deleteButton.addActionListener(e -> deleteProduct(product));
                tableModel.addRow(new Object[]{product.getProductName(), product.getStock(), product.getSalePrice(), editButton, deleteButton});
            }
        }
    }

    private void openEditScreen(ProductVO product) {
        new ProductUpdateGUI();
    }

    private void deleteProduct(ProductVO product) {
        int option = JOptionPane.showConfirmDialog(this, 
            product.getProductName() + " 상품의 재고를 0으로 설정하시겠습니까?", 
            "재고 변경 확인", JOptionPane.YES_NO_OPTION);
        
        if (option == JOptionPane.YES_OPTION) {
            productDAO.updateStockToZero(product.getProductId()); // 재고 0으로 변경
            loadProducts(""); // 변경된 목록 다시 로드
        }
    }

}
