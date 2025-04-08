// OrderGUI.java
package orders.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

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
     orderDAO = new OrderDAO();
     orderProductDAO = new OrderProductDAO();
     productDAO = new ProductDAO();

     setLayout(new BorderLayout());
     JPanel p_top = new JPanel(new FlowLayout(FlowLayout.LEFT));
     JPanel p_center = new JPanel(new BorderLayout());
     JPanel p_south = new JPanel();

     p_top.setBackground(Color.WHITE);
     p_center.setBackground(Color.WHITE);
     p_south.setBackground(Color.WHITE);

     // 🔹 상단 영역 (뒤로가기 버튼)
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

     // 🔹 하단 영역 (메인으로 이동 버튼 & 발주 버튼)
     JButton btnExit = new JButton("메인으로 이동");
     btnExit.setBorderPainted(false);
     btnExit.setBackground(Color.WHITE);
     btnExit.setForeground(Color.BLACK);
     btnExit.setFocusPainted(false);
     btnExit.addActionListener(e -> {
         dispose();
         new MainGUI();
     });

     JButton orderButton = new JButton("발주하기");
     orderButton.setPreferredSize(new Dimension(150, 40));
     orderButton.setFont(new Font("SansSerif", Font.BOLD, 14));
     orderButton.setBackground(new Color(30, 135, 61));
     orderButton.setForeground(Color.WHITE);
     orderButton.setFocusPainted(false);
     orderButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
     orderButton.setOpaque(true);
     orderButton.setContentAreaFilled(true);
     orderButton.setBorderPainted(false);
//     orderButton.setPreferredSize(new Dimension(100, 30));
//     orderButton.setBackground(new Color(30, 135, 61));
//     orderButton.setForeground(Color.WHITE);
//     orderButton.setFocusPainted(false);
//     orderButton.setBorderPainted(false);

//     p_south.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
//     p_south.add(btnExit);
//     p_south.add(orderButton);
     p_south.setLayout(new BoxLayout(p_south, BoxLayout.Y_AXIS));

     // 버튼 사이 간격 조절
     orderButton.setAlignmentX(Component.CENTER_ALIGNMENT);
     btnExit.setAlignmentX(Component.CENTER_ALIGNMENT);

     p_south.add(Box.createVerticalStrut(8));   // 🔹 발주하기 버튼 위 간격
     p_south.add(orderButton);
     p_south.add(Box.createVerticalStrut(4)); // 버튼 사이 간격
     p_south.add(btnExit);

     
     add(p_south, BorderLayout.SOUTH);

     // 🔹 중앙 입력 영역
     JPanel inputPanel = new JPanel();
     JLabel lblTitle = new JLabel("[상품 발주]");
     JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
     titlePanel.setBackground(Color.WHITE);
     titlePanel.add(lblTitle);
     titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 20));
     inputPanel.add(titlePanel);

     lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
//     lblTitle.setBorder(new EmptyBorder(0, 0, 10, 0));
//     lblTitle.setHorizontalAlignment(SwingConstants.LEFT); // JLabel 내부 텍스트도 왼쪽 정렬
//     lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT); // 패널 내에서 왼쪽 정렬

     inputPanel.setBackground(Color.WHITE);
     inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
     inputPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));

     // 🔹 상품 선택 라벨
     JPanel productLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
     productLabelPanel.setBackground(Color.WHITE);
     productLabelPanel.add(new JLabel("상품 선택:"));

     // 🔹 콤보박스 + 채우기 버튼 한 줄로 묶기
     JPanel comboWithButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
     comboWithButtonPanel.setBackground(Color.WHITE);

     productCombo = new JComboBox<>();
     productCombo.setPreferredSize(new Dimension(210, 25));
     List<ProductVO> products = orderDAO.getAllProducts();
     for (ProductVO product : products) {
         productCombo.addItem(product.getProductId() + " " + product.getProductName());
     }
     comboWithButtonPanel.add(productCombo);

     JButton fillStockButton = new JButton("채우기");
     fillStockButton.setPreferredSize(new Dimension(90, 25));
     fillStockButton.setFont(new Font("SansSerif", Font.BOLD, 12));
     fillStockButton.setBackground(new Color(30, 135, 61));
     fillStockButton.setForeground(Color.WHITE);
     fillStockButton.setFocusPainted(false);
     fillStockButton.setOpaque(true);
     fillStockButton.setContentAreaFilled(true);
     fillStockButton.setBorderPainted(false);
     comboWithButtonPanel.add(fillStockButton);

     // 🔹 수량 입력 라벨 (한 줄)
     JPanel quantityLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
     quantityLabelPanel.setBackground(Color.WHITE);
     quantityLabelPanel.add(new JLabel("수량 입력:"));

     // 🔹 수량 입력 필드 + 버튼 (한 줄)
     JPanel quantityInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
     quantityInputPanel.setBackground(Color.WHITE);
     quantityField = new JTextField();
     quantityField.setPreferredSize(new Dimension(210, 25));
     quantityInputPanel.add(quantityField);

     JButton addButton = new JButton("추가");
     addButton.setPreferredSize(new Dimension(90, 25));
     addButton.setFont(new Font("SansSerif", Font.BOLD, 12));
     addButton.setBackground(new Color(30, 135, 61));
     addButton.setForeground(Color.WHITE);
     addButton.setFocusPainted(false);
     addButton.setOpaque(true);
     addButton.setContentAreaFilled(true);
     addButton.setBorderPainted(false);
     quantityInputPanel.add(addButton);

     // 🔹 입력 패널에 하나씩 순서대로 추가
//     inputPanel.add(lblTitle);
     inputPanel.add(productLabelPanel);
     inputPanel.add(comboWithButtonPanel);
     inputPanel.add(quantityLabelPanel);
     inputPanel.add(quantityInputPanel);
     p_center.add(inputPanel, BorderLayout.NORTH);

     // 🔹 테이블 영역
     String[] columnNames = {"삭제", "상품 ID", "상품 이름", "재고", "적정 재고", "단가", "발주 수량"};
     tableModel = new DefaultTableModel(columnNames, 0) {
         public boolean isCellEditable(int row, int column) {
             return column == 0;
         }
     };

     table = new JTable(tableModel);
     table.setRowHeight(28);
     table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));

     // 🔹 테이블 컬럼 설정 (기존 주석 유지)
     table.getColumnModel().getColumn(0).setPreferredWidth(50); // 삭제 버튼 컬럼 폭 조절 (0번 컬럼)
     table.getColumnModel().getColumn(1).setMinWidth(0); // 상품 ID컬럼 숨기기
     table.getColumnModel().getColumn(1).setMaxWidth(0);
     table.getColumnModel().getColumn(1).setWidth(0);
     table.getColumnModel().getColumn(3).setPreferredWidth(50); // 상품 재고 컬럼 폭 조절 (3번 컬럼)
     table.getColumnModel().getColumn(4).setPreferredWidth(60); // 상품 재고 컬럼 폭 조절 (4번 컬럼)
     table.getColumnModel().getColumn(6).setPreferredWidth(55); // 상품 재고 컬럼 폭 조절 (7번 컬럼)

     table.getColumnModel().getColumn(0).setCellRenderer(new ButtonRenderer());
     table.getColumnModel().getColumn(0).setCellEditor(new ButtonEditor(new JCheckBox()));
     JScrollPane scrollPane = new JScrollPane(table);
     p_center.add(scrollPane, BorderLayout.CENTER);

     add(p_center, BorderLayout.CENTER);

     // 🔹 기능 로직
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
             tableModel.addRow(new Object[]{"삭제", productId, productName, stock, optimalStock, cost, quantity});
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
         Date date = new java.util.Date();
         int orderId = orderDAO.getNextOrderId();
         ArrayList<OrderReceiptVO> list = new ArrayList<>();
         int totalPrice = orderDAO.calculateTotalPrice(orderCart);
         OrderVO newOrder = new OrderVO(orderId, date, totalPrice, "발주");
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
     setSize(375, 660);
     setVisible(true);
     setLocationRelativeTo(null);
     setResizable(false);
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 }

 public static void main(String[] args) {
     new OrderGUI();
 }

 // 🔹 삭제 버튼 렌더러
 class ButtonRenderer extends JButton implements TableCellRenderer {
     public ButtonRenderer() {
         setText("X");
     }

     public Component getTableCellRendererComponent(JTable table, Object value,
                                                    boolean isSelected, boolean hasFocus, int row, int column) {
         return this;
     }
 }

 // 🔹 삭제 버튼 편집기
 class ButtonEditor extends DefaultCellEditor {
     private JButton button;
     private int row;
     private boolean isPushed;

     public ButtonEditor(JCheckBox checkBox) {
         super(checkBox);
         button = new JButton("X");

         button.addActionListener(e -> {
             isPushed = true;
             fireEditingStopped();
         });
     }

     @Override
     public Component getTableCellEditorComponent(JTable table, Object value,
                                                  boolean isSelected, int row, int column) {
         this.row = row;
         isPushed = true;
         return button;
     }

     @Override
     public Object getCellEditorValue() {
         if (isPushed) {
             isPushed = false;
             SwingUtilities.invokeLater(() -> {
                 if (row >= 0 && row < tableModel.getRowCount()) {
                     String productId = (String) tableModel.getValueAt(row, 1);
                     orderCart.remove(productId);
                     tableModel.removeRow(row);
                 }
             });
         }
         return "X";
     }
 }
}