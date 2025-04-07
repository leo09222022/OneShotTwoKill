package orders.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.gui.MainGUI;
import main.gui.OrderReceiptGUI;
import main.gui.ProductManagementGUI;
import orders.database.OrderDAO;
import orders.database.OrderProductDAO;
import orders.database.OrderProductVO;
import orders.database.OrderReceiptDAO;
import orders.database.OrderReceiptVO;
import orders.database.OrderVO;
import product.database.ProductDAO;
import product.database.ProductVO;

public class OrderGUI extends JFrame {
    private DefaultTableModel tableModel;
    private JTable table;
    private JComboBox<String> productCombo;
    private JTextField quantityField;
    private HashMap<String, Integer> orderCart;
    private OrderDAO orderDAO;
    private OrderProductDAO orderProductDAO;
    private ProductDAO productDAO;

    public OrderGUI() {
        setLayout(new BorderLayout());
        JPanel p_top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel p_center = new JPanel(new BorderLayout());
        JPanel p_south = new JPanel();

        JButton btnBack = new JButton("< 뒤로가기");
        p_top.add(btnBack);
        add(p_top, BorderLayout.NORTH);

        btnBack.addActionListener(e -> {
            this.setVisible(false);
            new ProductManagementGUI();
        });
        
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

        orderDAO = new OrderDAO();
        orderProductDAO = new OrderProductDAO();
        productDAO = new ProductDAO();

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.add(new JLabel("상품 선택:"));

        productCombo = new JComboBox<>();
        List<ProductVO> products = orderDAO.getAllProducts();
        for (ProductVO product : products) {
            productCombo.addItem(product.getProductId() + " " + product.getProductName());
        }
        inputPanel.add(productCombo);

        inputPanel.add(new JLabel("수량 입력:"));
        quantityField = new JTextField(5);
        inputPanel.add(quantityField);
        
        JButton addButton = new JButton("추가");
        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addButtonPanel.add(addButton);
        
        p_center.add(inputPanel, BorderLayout.NORTH);
        p_center.add(addButtonPanel, BorderLayout.CENTER);

        String[] columnNames = { "상품 이름", "재고", "적정 재고", "단가", "발주 수량" };
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        p_center.add(new JScrollPane(table), BorderLayout.SOUTH);

        add(p_center, BorderLayout.CENTER);

        JButton orderButton = new JButton("발주하기");
        p_south.add(orderButton);
        add(p_south, BorderLayout.SOUTH);

        orderCart = new HashMap<>();
        
        quantityField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addButton.doClick(); // 엔터를 누르면 "추가" 버튼 클릭 이벤트 발생
            }
        });
        
        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedProduct = (String) productCombo.getSelectedItem();
                String productId = selectedProduct.split(" ")[0];

                int quantity = Integer.parseInt(quantityField.getText());
                
                // db에서 해당 상품 정보 가져오기
                ProductVO product = orderDAO.getProductById(productId);
                if(product != null) {
                	String productName = product.getProductName();
                	int stock = product.getStock();
                	int optimalStock = product.getOptimalStock();
                	int cost = product.getCostPrice();
                	
                	// hashmap에 저장
                	orderCart.put(productId, quantity);
                	tableModel.addRow(new Object[]{productName, stock, optimalStock, cost, quantity});
                	
                	quantityField.setText("");
                }else {
                	JOptionPane.showMessageDialog(null, "상품 정보를 찾을 수 없습니다.");
                	
                }
                
            }
        });

        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int orderId = orderDAO.getNextOrderId();
                ArrayList<OrderReceiptVO> list = new ArrayList<>();
                int totalPrice = orderDAO.calculateTotalPrice(orderCart);
                OrderVO newOrder = new OrderVO(orderId, new java.util.Date(), totalPrice, "발주");
                orderDAO.insertOrder(newOrder);
                for (String productId : orderCart.keySet()) {
                    int quantity = orderCart.get(productId);
                    OrderProductVO orderProduct = new OrderProductVO(orderId, productId, quantity);
                    orderProductDAO.insertOrderProduct(orderProduct);
                    OrderReceiptDAO dao = new OrderReceiptDAO();
                    OrderReceiptVO vo = dao.receiptsForOrders(orderId, productId, quantity);
                    list.add(vo);
                }
                tableModel.setRowCount(0);
                orderCart.clear();
                new OrderReceiptGUI(list);
                dispose();
            }
        });

        setTitle("무인편의점 키오스크");
        setSize(400, 600);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new OrderGUI();
    }
}