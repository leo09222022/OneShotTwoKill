package sales.gui;

import product.database.ProductVO;
import sales.database.SalesDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SalesGUI extends JFrame {
    JTextField barcodeInput;
    JPanel productListPanel;
    JLabel totalLabel;
    Map<String, CartItemPanel> cartMap = new HashMap<>();
    SalesDAO salesDAO = new SalesDAO();

    public SalesGUI() {
        setTitle("무인편의점 키오스크 - 사용자 화면");
        setLayout(new BorderLayout());

        // 상단 제목
        JLabel titleLabel = new JLabel("바코드 입력", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setBorder(new EmptyBorder(30, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // 중앙: 바코드 입력 + 리스트
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        // 바코드 입력창
        barcodeInput = new JTextField();
        barcodeInput.setPreferredSize(new Dimension(200, 30));
        centerPanel.add(barcodeInput, BorderLayout.NORTH);

        // 상품 리스트 패널 (스크롤 포함)
        productListPanel = new JPanel();
        productListPanel.setLayout(new BoxLayout(productListPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(productListPanel);
        scrollPane.setPreferredSize(new Dimension(300, 400));
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // 하단: 총금액 + 결제 버튼
        totalLabel = new JLabel("총 금액: 0원", JLabel.CENTER);
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        totalLabel.setBorder(new EmptyBorder(0, 0, 10, 0));

        JButton payButton = new JButton("결제하기");
        payButton.setPreferredSize(new Dimension(100, 40));

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        southPanel.setBorder(new EmptyBorder(20, 0, 30, 0));
        southPanel.add(totalLabel);
        southPanel.add(payButton);

        add(southPanel, BorderLayout.SOUTH);

        // 바코드 입력 후 Enter 시 상품 추가
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

        // 기본 프레임 설정
        setSize(375, 660);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    // 상품 추가 or 수량 증가
    private void addOrUpdateProduct(ProductVO p) {
        String barcode = p.getProductId();
        if (cartMap.containsKey(barcode)) {
            cartMap.get(barcode).increaseQuantity();
        } else {
            CartItemPanel itemPanel = new CartItemPanel(p);
            cartMap.put(barcode, itemPanel);
            productListPanel.add(itemPanel);
            productListPanel.revalidate();
            productListPanel.repaint();
        }
        updateTotalPrice();
    }

    // 총 금액 계산 후 라벨에 반영
    private void updateTotalPrice() {
        int total = 0;
        for (CartItemPanel item : cartMap.values()) {
            total += item.getTotalPrice();
        }
        totalLabel.setText("총 금액: " + total + "원");
    }

    // 장바구니 항목 패널 (ProductVO 기반)
    class CartItemPanel extends JPanel {
        ProductVO product;
        int quantity = 1;
        JLabel quantityLabel, priceLabel;

        public CartItemPanel(ProductVO product) {
            this.product = product;
            setLayout(new FlowLayout(FlowLayout.LEFT));
            setMaximumSize(new Dimension(330, 40));

            JLabel nameLabel = new JLabel(product.getProductName());
            quantityLabel = new JLabel(quantity + "개");
            priceLabel = new JLabel(getTotalPrice() + "원");

            JButton plusBtn = new JButton("+");
            plusBtn.setPreferredSize(new Dimension(20, 20));
            plusBtn.setFont(new Font("SansSerif", Font.PLAIN, 10));
            plusBtn.setMargin(new Insets(0, 0, 0, 0));

            JButton minusBtn = new JButton("-");
            minusBtn.setPreferredSize(new Dimension(20, 20));
            minusBtn.setFont(new Font("SansSerif", Font.PLAIN, 10));
            minusBtn.setMargin(new Insets(0, 0, 0, 0));

            JButton deleteBtn = new JButton("삭제");
            deleteBtn.setPreferredSize(new Dimension(40, 20));
            deleteBtn.setFont(new Font("SansSerif", Font.PLAIN, 10));
            deleteBtn.setMargin(new Insets(0, 0, 0, 0));

            plusBtn.addActionListener(e -> {
                quantity++;
                updateLabels();
            });

            minusBtn.addActionListener(e -> {
                if (quantity > 1) {
                    quantity--;
                    updateLabels();
                }
            });

            deleteBtn.addActionListener(e -> {
                productListPanel.remove(this);
                cartMap.remove(product.getProductId());
                productListPanel.revalidate();
                productListPanel.repaint();
                updateTotalPrice();
            });

            add(nameLabel);
            add(quantityLabel);
            add(plusBtn);
            add(minusBtn);
            add(priceLabel);
            add(deleteBtn);
        }

        void updateLabels() {
            quantityLabel.setText(quantity + "개");
            priceLabel.setText(getTotalPrice() + "원");
            updateTotalPrice();
        }

        void increaseQuantity() {
            quantity++;
            updateLabels();
        }

        int getTotalPrice() {
            return product.getSalePrice() * quantity;
        }
    }

    public static void main(String[] args) {
        new SalesGUI();
    }
}