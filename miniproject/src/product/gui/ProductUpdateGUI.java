package product.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import main.gui.MainGUI;
import main.gui.ProductManagementGUI;
import product.database.ProductDAO;
import product.database.ProductVO;

// [상품 수정] 클래스 
public class ProductUpdateGUI extends JFrame {
    private ProductDAO productDAO;
    private JPanel productPanel;
    private JTextField txtSearch;

    public ProductUpdateGUI() {
        productDAO = new ProductDAO();
        
        setLayout(new BorderLayout());
        JPanel p_top = new JPanel();
        JPanel p_center = new JPanel();
        JPanel p_south = new JPanel();
//        JLabel lblExit = new JLabel("메인으로 이동");
        JLabel lblTitle = new JLabel("[상품 수정]");
        
        // 메인으로 이동 버튼 기능 추가
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
        
        

        // 제목줄
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
//        lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
        lblTitle.setBorder(new EmptyBorder(20, 20, 10, 20));
        lblTitle.setBackground(Color.WHITE);
        lblTitle.setOpaque(true);
        lblTitle.setBounds(20, 50, 300, 30);
//        p_center.add(lblTitle, BorderLayout.NORTH);


        p_top.setBackground(Color.WHITE);
        p_south.setBackground(Color.WHITE);

        p_top.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton btnBack = new JButton("< 뒤로가기");
        btnBack.setBorderPainted(false);
        btnBack.setBackground(Color.WHITE);
        btnBack.setForeground(Color.BLACK);
        btnBack.setFocusPainted(false);
        // 뒤로가기 선택시, 이전 화면으로 돌아감 
        btnBack.addActionListener(e -> returnToManagementGUI());
        p_top.add(btnBack);
        add(p_top, BorderLayout.NORTH);

        p_south.setLayout(new FlowLayout(FlowLayout.CENTER));
//        p_south.add(lblExit);
        add(p_south, BorderLayout.SOUTH);

        setTitle("상품 수정");
		setSize(375, 660);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
        
        p_center.setLayout(new BorderLayout());
        p_center.add(lblTitle, BorderLayout.NORTH);
        
        add(p_center, BorderLayout.CENTER);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        searchPanel.setBackground(Color.WHITE);
        
        txtSearch = new JTextField(20);
        txtSearch.setPreferredSize(new Dimension(200, 30));
        
        JButton btnSearch = new JButton("검색");
        btnSearch.setPreferredSize(new Dimension(80, 30));
        btnSearch.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnSearch.setBackground(new Color(30, 135, 61));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFocusPainted(false);
        btnSearch.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        btnSearch.setOpaque(true);
        btnSearch.setBorderPainted(false);
        
        btnSearch.addActionListener(e -> searchProduct());
        
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        
        p_center.add(searchPanel, BorderLayout.CENTER);

        productPanel = new JPanel();
        productPanel.setBorder(new EmptyBorder(0, 10, 0, 10));
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
        productPanel.setBackground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(productPanel);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(320, 440));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        p_center.add(scrollPane, BorderLayout.SOUTH);

        // 현재 DB에 있는 모든 상품들을 나열하고 "수정", "X" 
        // 버튼도 나열된다. 
        loadProducts();

        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // 모든 상품 정보 나열 
    public void loadProducts() {
        productPanel.removeAll();
        List<ProductVO> products = productDAO.findAll();
        for (ProductVO product : products) {
            addProductRow(product);
        }
        productPanel.revalidate();
        productPanel.repaint();
    }

    // 수정, X 버튼에 이벤트를 등록한다. 
    private void addProductRow(ProductVO product) {
    	JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    	rowPanel.setBackground(Color.WHITE);

        JButton btnEdit = new JButton("수정");
        JButton btnDelete = new JButton("X");

//        JLabel lblProduct = new JLabel(product.getProductName() + " " + product.getStock() + "개 " + product.getSalePrice());
        JLabel lblProduct = new JLabel(product.getProductName() + " | 재고: " + product.getStock() + "개 | " + product.getSalePrice() + "원");
        lblProduct.setFont(new Font("SansSerif", Font.PLAIN, 13));
        
        // 버튼 스타일 추가
        btnEdit.setPreferredSize(new Dimension(36, 26));
        btnEdit.setFont(new Font("SansSerif", Font.BOLD, 10));
        btnEdit.setBackground(new Color(30, 135, 61));
        btnEdit.setForeground(Color.WHITE);
        btnEdit.setFocusPainted(false);
        btnEdit.setBorderPainted(false);
        btnEdit.setMargin(new Insets(2, 4, 2, 4)); 
        btnEdit.setOpaque(true);
        btnEdit.setContentAreaFilled(true);
        btnEdit.setBorderPainted(false);


        btnDelete.setPreferredSize(new Dimension(20, 20));
        btnDelete.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnDelete.setBackground(Color.WHITE);
        btnDelete.setForeground(new Color(30, 135, 61));
        btnDelete.setBorder(BorderFactory.createLineBorder(new Color(30, 135, 61), 2));
        btnDelete.setFocusPainted(false);
        btnDelete.setContentAreaFilled(true);
        btnDelete.setOpaque(true);
        
        
        btnEdit.addActionListener(e -> openEditScreen(product));
        btnDelete.addActionListener(e -> deleteProduct(product));

        rowPanel.add(btnEdit);
        rowPanel.add(lblProduct);
        
        //rowPanel.add(btnDelete);
        if (product.getStock() > 0) {
            rowPanel.add(btnDelete);
        }
        
        productPanel.add(rowPanel);
    }

    // 사용자가 "수정" 버튼을 눌렀을 경우, ProductEditGUI로 넘긴다. 
    private void openEditScreen(ProductVO product) {
        this.setVisible(false);
        new ProductEditGUI(product);
    }

    // X버튼을 눌렀을 때 상품의 재고를 0으로 수정한다. 
    private void deleteProduct(ProductVO product) {
        int option = JOptionPane.showConfirmDialog(this, 
            product.getProductName() + " 상품의 재고를 0으로 설정하시겠습니까?", 
            "재고 변경 확인", JOptionPane.YES_NO_OPTION);
        
        if (option == JOptionPane.YES_OPTION) {
            productDAO.updateStockToZero(product.getProductId());
            loadProducts();
        }
        
    }

    // 상품의 이름으로 상품들을 리스트에 가져온다. 
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

    // 이전 화면으로 돌아간다. 
    private void returnToManagementGUI() {
        this.setVisible(false);
        new ProductManagementGUI();
    }
}
