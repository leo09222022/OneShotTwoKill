package orders.gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import main.gui.MainGUI;
import main.gui.OrderReceiptGUI;
import main.gui.ProductManagementGUI;
import orders.database.*;
import product.database.*;

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

        JButton btnExit = new JButton("메인으로 이동");
        p_south.setLayout(new FlowLayout(FlowLayout.CENTER));
        p_south.add(btnExit);
        add(p_south, BorderLayout.SOUTH);
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

        JPanel inputPanel = new JPanel(new GridLayout(2, 1, 3, 3));
     // 상품 선택 라인
        JPanel productLine = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        productLine.add(new JLabel("상품 선택:"));

        // 🔹 콤보박스 크기 조절
        productCombo = new JComboBox<>();
        productCombo.setPreferredSize(new Dimension(160, 25));
        List<ProductVO> products = orderDAO.getAllProducts();
        for (ProductVO product : products) {
            productCombo.addItem(product.getProductId() + " " + product.getProductName());
        }
        productLine.add(productCombo);

        // 🔹 재고채우기 버튼 폭 줄이기
        JButton fillStockButton = new JButton("채우기");
        fillStockButton.setPreferredSize(new Dimension(90, 25));
        productLine.add(fillStockButton);

        // 수량 입력 라인
        JPanel quantityLine = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        quantityLine.add(new JLabel("수량 입력:"));

        // 🔹 수량 필드 폭 줄이기
        quantityField = new JTextField();
        quantityField.setPreferredSize(new Dimension(50, 25));
        quantityLine.add(quantityField);

        // 🔹 추가 버튼 폭 줄이기
        JButton addButton = new JButton("추가");
        addButton.setPreferredSize(new Dimension(60, 25));
        quantityLine.add(addButton);


        p_center.add(inputPanel, BorderLayout.NORTH);
        
        inputPanel.add(productLine);
        inputPanel.add(quantityLine);

        String[] columnNames = {"삭제","상품 ID", "상품 이름", "재고", "적정 재고", "단가", "발주 수량"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }
        };

        table = new JTable(tableModel);
     // 삭제 버튼 컬럼 폭 조절 (0번 컬럼)
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(0).setMinWidth(50);
     // 상품 ID컬럼 숨기기
        table.getColumnModel().getColumn(1).setMinWidth(0);
        table.getColumnModel().getColumn(1).setMaxWidth(0);
        table.getColumnModel().getColumn(1).setWidth(0);
     // 상품 재고 컬럼 폭 조절 (3번 컬럼)
        table.getColumnModel().getColumn(3).setPreferredWidth(50);
        table.getColumnModel().getColumn(3).setMaxWidth(50);
        table.getColumnModel().getColumn(3).setMinWidth(50);
     // 상품 재고 컬럼 폭 조절 (4번 컬럼)
        table.getColumnModel().getColumn(4).setPreferredWidth(60);
        table.getColumnModel().getColumn(4).setMaxWidth(60);
        table.getColumnModel().getColumn(4).setMinWidth(60);
     // 상품 재고 컬럼 폭 조절 (7번 컬럼)   
        table.getColumnModel().getColumn(6).setPreferredWidth(55);
        table.getColumnModel().getColumn(6).setMaxWidth(55);
        table.getColumnModel().getColumn(6).setMinWidth(55);
        
        table.getColumnModel().getColumn(0).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(0).setCellEditor(new ButtonEditor(new JCheckBox()));
        p_center.add(new JScrollPane(table), BorderLayout.SOUTH);
        add(p_center, BorderLayout.CENTER);

        JButton orderButton = new JButton("발주하기");
        p_south.add(orderButton);

        orderCart = new HashMap<>();

        quantityField.addActionListener(e -> addButton.doClick());

        addButton.addActionListener(e -> {
            String selectedProduct = (String) productCombo.getSelectedItem();
            String productId = selectedProduct.split(" ")[0];
            int quantity = Integer.parseInt(quantityField.getText());

            if (orderCart.containsKey(productId)) {
                JOptionPane.showMessageDialog(null, "이미 발주 리스트에 있는 상품입니다");
                return;
            }

            
            ProductVO product = orderDAO.getProductById(productId);
            if (product != null) {
                String productName = product.getProductName();
                int stock = product.getStock();
                int optimalStock = product.getOptimalStock();
                int cost = product.getCostPrice();

                
                orderCart.put(productId, quantity);
                tableModel.addRow(new Object[]{"삭제", productId,productName, stock, optimalStock, cost, quantity});
                quantityField.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "상품 정보를 찾을 수 없습니다.");
            }
        });
        
        fillStockButton.addActionListener(e -> {
            List<ProductVO> allProducts = orderDAO.getAllProducts();
            int addedCount = 0;

            for (ProductVO product : allProducts) {
                String productId = product.getProductId();

                // 이미 추가된 상품은 스킵
                if (orderCart.containsKey(productId)) continue;

                int stock = product.getStock();
                int optimalStock = product.getOptimalStock();
                int cost = product.getCostPrice();
                String productName = product.getProductName();

                if (stock < optimalStock) {
                    int quantityToOrder = optimalStock - stock;
                    orderCart.put(productId, quantityToOrder);
                    tableModel.addRow(new Object[]{"삭제", productId, productName, stock, optimalStock, cost, quantityToOrder});
                    addedCount++;
                }
            }

            if (addedCount == 0) {
                JOptionPane.showMessageDialog(null, "추가할 상품이 없습니다");
            } else {
                JOptionPane.showMessageDialog(null, addedCount + "개의 상품을 자동으로 발주 리스트에 추가했습니다");
            }
        });


        orderButton.addActionListener(e -> {
        	    if (orderCart.isEmpty()) {
        	        JOptionPane.showMessageDialog(null, "발주 리스트가 비어 있습니다!");
        	        return;
        	    }

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

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setText("X");
        }
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private int row;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton("X");

            // 버튼 클릭 리스너
            button.addActionListener(e -> {
                isPushed = true;
                fireEditingStopped();  // 우선 편집 종료
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.row = row;  // 삭제할 row 기억해두기
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                isPushed = false;

                // JTable 편집 종료 후 안전하게 삭제하도록 예약
                SwingUtilities.invokeLater(() -> {
                    if (row >= 0 && row < tableModel.getRowCount()) {
                        String productId = (String) tableModel.getValueAt(row, 1);  // 상품 ID는 1번 컬럼
                        orderCart.remove(productId);  // 장바구니에서 제거
                        tableModel.removeRow(row);   // 테이블에서 제거
                    }
                });
            }
            return "X";
        }
    }



}
