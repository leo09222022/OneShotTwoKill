package orders.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.gui.OrderReceiptGUI;
import main.gui.ProductManagementGUI;
import orders.database.OrderDAO;
import orders.database.OrderProductDAO;
import orders.database.OrderProductVO;
import orders.database.OrderVO;
import orders.database.OrderRecieptVO;
import orders.database.OrderRecieptDAO;
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
//                ProductVO product = 
                int quantity = Integer.parseInt(quantityField.getText());
                orderCart.put(productId, quantity);
                tableModel.addRow(new Object[]{"", "", "", "", quantity});
            }
        });

        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int orderId = orderDAO.getNextOrderId();
                ArrayList<OrderRecieptVO> list = new ArrayList<>();
                int totalPrice = orderDAO.calculateTotalPrice(orderCart);
                OrderVO newOrder = new OrderVO(orderId, new java.util.Date(), totalPrice, "발주");
                orderDAO.insertOrder(newOrder);
                for (String productId : orderCart.keySet()) {
                    int quantity = orderCart.get(productId);
                    OrderProductVO orderProduct = new OrderProductVO(orderId, productId, quantity);
                    orderProductDAO.insertOrderProduct(orderProduct);
                    OrderRecieptDAO dao = new OrderRecieptDAO();
                    OrderRecieptVO vo = dao.receiptsForOrders(orderId, productId, quantity);
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