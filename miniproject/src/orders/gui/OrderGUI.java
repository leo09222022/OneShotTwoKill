package orders.gui;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;

import main.gui.OrderReceiptGUI;
import orders.database.OrderDAO;
import orders.database.OrderProductDAO;
import orders.database.OrderProductVO;
import orders.database.OrderVO;
import product.database.ProductDAO;
import product.database.ProductVO;
import orders.database.OrderRecieptVO;
import orders.database.OrderRecieptDAO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderGUI extends JFrame {
	private DefaultTableModel tableModel;
	private JTable table;
	private JComboBox<String> productCombo;
	private JTextField quantityField;
	private HashMap<String, Integer> orderCart; // 제품 ID (String)와 수량을 저장할 HashMap
	private OrderDAO orderDAO;
	private OrderProductDAO orderProductDAO;
	private ProductDAO productDAO; // 추가: 상품 목록을 DB에서 가져오기 위한 DAO

	public OrderGUI() {
		setTitle("발주 화면");
		setSize(500, 400);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// DAO 초기화
		orderDAO = new OrderDAO();
		orderProductDAO = new OrderProductDAO();
		productDAO = new ProductDAO(); // 상품 정보를 가져오기 위한 DAO

		// 1️⃣ 상단 패널 (상품 선택 & 수량 입력)
		JPanel inputPanel = new JPanel();
		inputPanel.add(new JLabel("상품 선택:"));

		// 상품 목록을 DB에서 가져오기
		List<ProductVO> products = orderDAO.getAllProducts(); // productDAO 사용
		productCombo = new JComboBox<>();
		for (ProductVO product : products) {
			productCombo.addItem(product.getProductId() + " " + product.getProductName());
		}
		inputPanel.add(productCombo);

		inputPanel.add(new JLabel("수량:"));
		quantityField = new JTextField(5);
		inputPanel.add(quantityField);

		JButton addButton = new JButton("추가");
		inputPanel.add(addButton);

		add(inputPanel, BorderLayout.NORTH);

		// 2️⃣ 중앙 (JTable - 선택한 상품 목록)
		String[] columnNames = { "상품 ID", "수량" };
		tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel);
		add(new JScrollPane(table), BorderLayout.CENTER);

		// 3️⃣ 하단 (발주 버튼)
		JButton orderButton = new JButton("발주하기");
		add(orderButton, BorderLayout.SOUTH);

		// 4️⃣ HashMap 초기화 (장바구니 역할)
		orderCart = new HashMap<>();

		// ✅ "추가" 버튼 이벤트 처리
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedProduct = (String) productCombo.getSelectedItem();
				String productId = selectedProduct.split(" ")[0]; // 상품 ID 추출
				int quantity = Integer.parseInt(quantityField.getText());

				orderCart.put(productId, quantity); // HashMap에 저장
				tableModel.addRow(new Object[] { productId, quantity }); // JTable에 추가
			}
		});

		// ✅ "발주하기" 버튼 이벤트 처리
		orderButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 새로운 ORDER_ID 가져오기
				int orderId = orderDAO.getNextOrderId();
				ArrayList<OrderRecieptVO> list = new ArrayList<OrderRecieptVO>();
				// 총 가격 계산
				int totalPrice = orderDAO.calculateTotalPrice(orderCart);

				// 새로운 주문 생성 (ORDER_ID, ORDER_DATE, TOTAL_COST, REMARKS)
				OrderVO newOrder = new OrderVO(orderId, new java.util.Date(), totalPrice, "발주");
				orderDAO.insertOrder(newOrder); // 새 주문 추가

				// 발주 상품 추가
				for (String productId : orderCart.keySet()) {
					int quantity = orderCart.get(productId);
					OrderProductVO orderProduct = new OrderProductVO(orderId, productId, quantity);
					orderProductDAO.insertOrderProduct(orderProduct);

					// 상품명, 개수, 원가가격
					OrderRecieptDAO dao = new OrderRecieptDAO();
					OrderRecieptVO vo = dao.receiptsForOrders(orderId, productId, quantity);
//					System.out.println(vo.getOrderId()+" "+vo.getOrderQuantity()+" "+vo.getProductName()+" "+vo.getCostPerProduct());
					list.add(vo);
				}

				tableModel.setRowCount(0); // JTable 초기화
				orderCart.clear(); // HashMap 초기화
				new OrderReceiptGUI(list);
				dispose(); // 현재 창 닫기
			}
		});
		setVisible(true);
	}

	public static void main(String[] args) {
		new OrderGUI(); // Swing UI 실행
	}
}
