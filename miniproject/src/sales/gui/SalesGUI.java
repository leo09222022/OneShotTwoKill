package sales.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.gui.MainGUI;
import payment.gui.PaymentGUI;
import product.database.ProductVO;
import sales.database.SalesDAO;

public class SalesGUI extends JFrame {
	JTextField barcodeInput;
	JPanel productListPanel;
	JLabel totalLabel;
	JLabel emptyLabel;
	Map<ProductVO, Integer> cartMap = new HashMap<>(); // 상품과 수량을 담는 장바구니 맵
	Map<ProductVO, CartItemPanel> panelMap = new HashMap<>(); // 수량증가용

	SalesDAO salesDAO = new SalesDAO();

	public SalesGUI() {
		setLayout(new BorderLayout());
		JPanel p_top = new JPanel();
		JPanel p_center = new JPanel();
		JPanel p_center_top = new JPanel();
		JPanel p_center_mid = new JPanel();
		JPanel p_center_bottom = new JPanel();
		JPanel p_south = new JPanel();

		// 상단: 뒤로가기 버튼
		JButton btnBack = new JButton("< 뒤로가기");
		p_top.setLayout(new FlowLayout(FlowLayout.LEFT));
		p_top.setBackground(Color.WHITE);
		btnBack.setBorderPainted(false);
		btnBack.setBackground(Color.WHITE);
		btnBack.setForeground(Color.BLACK);
		btnBack.setFocusPainted(false);
		p_top.add(btnBack);
		add(p_top, BorderLayout.NORTH);

		// 뒤로가기 버튼 액션
		btnBack.addActionListener(e -> {
			this.setVisible(false);
			new MainGUI();
		});

		// 하단: 총 금액 + 결제하기 버튼
		totalLabel = new JLabel("총 금액: 0원", JLabel.CENTER);
		totalLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
		totalLabel.setBorder(new EmptyBorder(10, 0, 5, 0));

		JButton payButton = new JButton("결제하기");
		payButton.setPreferredSize(new Dimension(120, 40));
		payButton.setFont(new Font("SansSerif", Font.BOLD, 14));
		payButton.setBackground(new Color(30, 135, 61));
		payButton.setForeground(Color.WHITE);
		payButton.setFocusPainted(false);
		payButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		payButton.setOpaque(true);
		payButton.setContentAreaFilled(true);
		payButton.setBorderPainted(false);

		// 결제 버튼 클릭 시 장바구니 출력 (샘플 처리)
		payButton.addActionListener(e -> {
			this.setVisible(false);
			new PaymentGUI(cartMap); // ← 넘겨주는 부분!
		});

		p_south.setLayout(new BoxLayout(p_south, BoxLayout.Y_AXIS));
		p_south.setBackground(Color.WHITE);
		p_south.setBorder(new EmptyBorder(0, 0, 50, 0));
		totalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		payButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		p_south.add(totalLabel);
		p_south.add(payButton);
		add(p_south, BorderLayout.SOUTH);

		// 타이틀
		JLabel labelTitle = new JLabel("바코드 입력");
		labelTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
		labelTitle.setBorder(new EmptyBorder(0, 0, 10, 0));
		p_center_top.setBackground(Color.WHITE);
		p_center_top.setLayout(new FlowLayout(FlowLayout.CENTER));
		p_center_top.add(labelTitle);

		// 바코드 입력창
		barcodeInput = new JTextField();
		barcodeInput.setPreferredSize(new Dimension(300, 30));
		p_center_mid.setBackground(Color.WHITE);
		p_center_mid.setLayout(new FlowLayout(FlowLayout.CENTER));
		p_center_mid.add(barcodeInput);

		// 상품 리스트 영역
		productListPanel = new JPanel();
		productListPanel.setLayout(new BoxLayout(productListPanel, BoxLayout.Y_AXIS));
		productListPanel.setBackground(Color.WHITE);

		// 리스트 헤더
		JPanel listHeader = new JPanel(new GridLayout(1, 4));
		listHeader.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		listHeader.setBackground(Color.LIGHT_GRAY);
		listHeader.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		listHeader.add(new JLabel("상품명"));
		listHeader.add(new JLabel("          수량"));
		listHeader.add(new JLabel("                가격"));
		listHeader.add(new JLabel(""));

		// 상품 없음 안내 문구
		emptyLabel = new JLabel("상품 바코드를 입력해주세요", JLabel.CENTER);
		emptyLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
		emptyLabel.setForeground(Color.GRAY);
		emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		productListPanel.add(emptyLabel);

		JScrollPane scrollPane = new JScrollPane(productListPanel);
		scrollPane.setBackground(Color.WHITE);
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.setPreferredSize(new Dimension(330, 300));

		// 상품 목록 타이틀 + 헤더 포함
		JLabel listTitleLabel = new JLabel("상품 목록");
		listTitleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
		listTitleLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
		listTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JPanel p_listWrap = new JPanel();
		p_listWrap.setLayout(new BoxLayout(p_listWrap, BoxLayout.Y_AXIS));
		p_listWrap.setBackground(Color.WHITE);
		p_listWrap.add(listTitleLabel);
		p_listWrap.add(listHeader);
		p_listWrap.add(scrollPane);

		p_center_bottom.setBackground(Color.WHITE);
		p_center_bottom.setBorder(new EmptyBorder(0, 20, 0, 20));
		p_center_bottom.setLayout(new BorderLayout());
		p_center_bottom.add(p_listWrap, BorderLayout.CENTER);

		// 중앙 패널 조합
		p_center.setLayout(new BorderLayout());
		p_center.add(p_center_top, BorderLayout.NORTH);
		p_center.add(p_center_mid, BorderLayout.CENTER);
		p_center.add(p_center_bottom, BorderLayout.SOUTH);
		add(p_center, BorderLayout.CENTER);

		// 바코드 입력 이벤트
		barcodeInput.addActionListener(e -> {
			String barcode = barcodeInput.getText().trim();
			if (!barcode.isEmpty()) {
				ProductVO product = salesDAO.findById(barcode);
				if (product != null) {
					addOrUpdateProduct(product);
				} else {
					JOptionPane.showMessageDialog(this, "상품을 찾을 수 없습니다.");
				}
				barcodeInput.setText("");
			}
		});

		// 프레임 설정
		setTitle("무인편의점 키오스크 - 사용자 화면");
		setSize(375, 660);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	// 상품 추가 또는 수량 증가 처리
	private void addOrUpdateProduct(ProductVO p) {
		if (cartMap.containsKey(p)) {
			cartMap.put(p, cartMap.get(p) + 1);
			CartItemPanel panel = panelMap.get(p);
			if (panel != null)
				panel.updateLabels(); // 기존 패널의 수량/금액만 갱신
		} else {
			cartMap.put(p, 1);
			CartItemPanel itemPanel = new CartItemPanel(p);
			panelMap.put(p, itemPanel); // 새 패널 저장
			int index = productListPanel.getComponentCount();
			if (index % 2 == 0) {
				itemPanel.setBackground(Color.WHITE);
			} else {
				itemPanel.setBackground(new Color(245, 245, 245));
			}
			productListPanel.add(itemPanel);
		}

		productListPanel.revalidate();
		productListPanel.repaint();
		updateTotalPrice();
		updateEmptyLabelVisibility();
	}

	// 총 금액 계산
	private void updateTotalPrice() {
		int total = 0;
		for (Map.Entry<ProductVO, Integer> entry : cartMap.entrySet()) {
			total += entry.getKey().getSalePrice() * entry.getValue();
		}
		NumberFormat nf = NumberFormat.getInstance();
		totalLabel.setText("총 금액: " + nf.format(total) + "원");
	}

	// 상품 없을 경우 안내 문구 표시
	private void updateEmptyLabelVisibility() {
		emptyLabel.setVisible(cartMap.isEmpty());
	}

	// 상품 개별 아이템 패널 정의
	class CartItemPanel extends JPanel {
		ProductVO product;
		JLabel quantityLabel, priceLabel;

		public CartItemPanel(ProductVO product) {
			this.product = product;
			setLayout(new FlowLayout(FlowLayout.LEFT));
			setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
			setAlignmentX(Component.LEFT_ALIGNMENT);

			JLabel nameLabel = new JLabel(product.getProductName());
			nameLabel.setPreferredSize(new Dimension(100, 20));

			quantityLabel = new JLabel(cartMap.get(product) + "개");
			priceLabel = new JLabel(NumberFormat.getInstance().format(getTotalPrice()) + "원");
			priceLabel.setPreferredSize(new Dimension(60, 20));

			JButton plusBtn = new JButton("➕");
			JButton minusBtn = new JButton("➖");
			JButton deleteBtn = new JButton("X");

			for (JButton btn : new JButton[] { plusBtn, minusBtn }) {
				btn.setPreferredSize(new Dimension(20, 20));
				btn.setFont(new Font("SansSerif", Font.BOLD, 10));
				btn.setMargin(new Insets(0, 0, 0, 0));
				btn.setFocusPainted(false);
				btn.setBackground(new Color(80, 80, 80));
				btn.setForeground(Color.WHITE);
				btn.setBorder(BorderFactory.createEmptyBorder());
			}

			deleteBtn.setPreferredSize(new Dimension(20, 20));
			deleteBtn.setFont(new Font("SansSerif", Font.BOLD, 10));
			deleteBtn.setMargin(new Insets(0, 0, 0, 0));
			deleteBtn.setFocusPainted(false);
			deleteBtn.setBackground(Color.WHITE);
			deleteBtn.setForeground(new Color(200, 200, 200));
			deleteBtn.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

			plusBtn.addActionListener(e -> {
				cartMap.put(product, cartMap.get(product) + 1);
				updateLabels();
			});

			minusBtn.addActionListener(e -> {
				int qty = cartMap.get(product);
				if (qty > 1) {
					cartMap.put(product, qty - 1);
					updateLabels();
				}
			});

			deleteBtn.addActionListener(e -> {
				cartMap.remove(product);
				panelMap.remove(product); // 여기 추가
				productListPanel.remove(this);
				productListPanel.revalidate();
				productListPanel.repaint();
				updateEmptyLabelVisibility();
				updateTotalPrice();
			});

			JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
			quantityPanel.setOpaque(false);
			quantityPanel.add(quantityLabel);
			quantityPanel.add(plusBtn);
			quantityPanel.add(minusBtn);
			quantityPanel.setPreferredSize(new Dimension(90, 25));

			add(nameLabel);
			add(quantityPanel);
			add(priceLabel);
			add(deleteBtn);
		}

		void updateLabels() {
			quantityLabel.setText(cartMap.get(product) + "개");
			priceLabel.setText(NumberFormat.getInstance().format(getTotalPrice()) + "원");
			updateTotalPrice();
		}

		int getTotalPrice() {
			return product.getSalePrice() * cartMap.get(product);
		}
	}

	public static void main(String[] args) {
		new SalesGUI();
	}
}
