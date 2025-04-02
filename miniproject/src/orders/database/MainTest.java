package orders.database;

import orders.database.*;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class MainTest extends JFrame {
    private DefaultTableModel tableModel;
    private JTable table;
    private JComboBox<String> productCombo;
    private JTextField quantityField;
    private HashMap<Integer, Integer> orderCart; // 제품 ID와 수량을 저장할 HashMap
    private OrderDAO orderDAO;

    public MainTest() {
        setTitle("발주 화면");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 1️⃣ 상단 패널 (상품 선택 & 수량 입력)
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("상품 선택:"));
        productCombo = new JComboBox<>(new String[]{"상품 101", "상품 102", "상품 103"}); // 상품 예시
        inputPanel.add(productCombo);

        inputPanel.add(new JLabel("수량:"));
        quantityField = new JTextField(5);
        inputPanel.add(quantityField);

        JButton addButton = new JButton("추가");
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.NORTH);

        // 2️⃣ 중앙 (JTable - 선택한 상품 목록)
        String[] columnNames = {"상품 ID", "수량"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // 3️⃣ 하단 (발주 버튼)
        JButton orderButton = new JButton("발주하기");
        add(orderButton, BorderLayout.SOUTH);

        // 4️⃣ HashMap 초기화 (장바구니 역할)
        orderCart = new HashMap<>();
        orderDAO = new OrderDAO();

        // ✅ "추가" 버튼 이벤트 처리
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedProduct = (String) productCombo.getSelectedItem();
                int productId = Integer.parseInt(selectedProduct.split(" ")[1]); // 상품 ID 추출
                int quantity = Integer.parseInt(quantityField.getText());

                orderCart.put(productId, quantity); // HashMap에 저장
                tableModel.addRow(new Object[]{productId, quantity}); // JTable에 추가
            }
        });

        // ✅ "발주하기" 버튼 이벤트 처리
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int orderId = orderDAO.insertOrder(); // 발주 테이블에 새 주문 추가
                for (Integer productId : orderCart.keySet()) {
                    int quantity = orderCart.get(productId);
                    orderDAO.insertOrderProduct(orderId, productId, quantity); // 발주상품 추가
                }
                JOptionPane.showMessageDialog(null, "발주가 완료되었습니다!");
                tableModel.setRowCount(0); // JTable 초기화
                orderCart.clear(); // HashMap 초기화
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainTest(); // Swing UI 실행
    }
}
