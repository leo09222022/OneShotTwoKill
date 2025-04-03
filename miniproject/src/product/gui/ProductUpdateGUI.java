package product.gui;

import product.database.ProductDAO;
import product.database.ProductVO;

import javax.swing.*;
import main.gui.ProductManagementGUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

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
        JLabel lblExit = new JLabel("관리자 화면 종료");

        p_top.setBackground(Color.WHITE);
        p_south.setBackground(Color.WHITE);

        p_top.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton btnBack = new JButton("< 뒤로가기");
        btnBack.setBorderPainted(false);
        btnBack.setBackground(Color.WHITE);
        btnBack.setForeground(Color.BLACK);
        btnBack.setFocusPainted(false);
        btnBack.addActionListener(e -> returnToManagementGUI());
        p_top.add(btnBack);
        add(p_top, BorderLayout.NORTH);

        p_south.setLayout(new FlowLayout(FlowLayout.CENTER));
        p_south.add(lblExit);
        add(p_south, BorderLayout.SOUTH);

        setTitle("상품 수정");
        setSize(400, 600);
        
        p_center.setLayout(new BorderLayout());
        add(p_center, BorderLayout.CENTER);

        JPanel searchPanel = new JPanel(new FlowLayout());
        txtSearch = new JTextField(20);
        JButton btnSearch = new JButton("검색");
        btnSearch.addActionListener(e -> searchProduct());
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        p_center.add(searchPanel, BorderLayout.NORTH);

        productPanel = new JPanel();
        productPanel.setLayout(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(productPanel);
        p_center.add(scrollPane, BorderLayout.CENTER);

        loadProducts();

        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void loadProducts() {
        productPanel.removeAll();
        List<ProductVO> products = productDAO.getAllProducts();
        for (ProductVO product : products) {
            addProductRow(product);
        }
        productPanel.revalidate();
        productPanel.repaint();
    }

    private void addProductRow(ProductVO product) {
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnEdit = new JButton("수정");
        JButton btnDelete = new JButton("X");

        JLabel lblProduct = new JLabel(product.getProductName() + " " + product.getStock() + "개 " + product.getSalePrice());
        
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

    private void openEditScreen(ProductVO product) {
        this.setVisible(false);
        new ProductEditGUI(product);
    }

    private void deleteProduct(ProductVO product) {
        int option = JOptionPane.showConfirmDialog(this, 
            product.getProductName() + " 상품의 재고를 0으로 설정하시겠습니까?", 
            "재고 변경 확인", JOptionPane.YES_NO_OPTION);
        
        if (option == JOptionPane.YES_OPTION) {
            productDAO.updateStockToZero(product.getProductId());
            loadProducts();
        }
        
    }

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

    private void returnToManagementGUI() {
        this.setVisible(false);
        new ProductManagementGUI();
    }
}
