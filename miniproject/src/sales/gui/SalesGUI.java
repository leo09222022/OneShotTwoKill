package sales.gui;

import java.awt.*;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import product.database.ProductVO;
import sales.database.SalesDAO;

public class SalesGUI extends JFrame {
    JTextField barcodeInput;
    JPanel productListPanel;
    JLabel totalLabel;
    JLabel emptyLabel;
    Map<String, CartItemPanel> cartMap = new HashMap<>();
    SalesDAO salesDAO = new SalesDAO();

    public SalesGUI() {
        setLayout(new BorderLayout());
        JPanel p_top = new JPanel();
        JPanel p_center = new JPanel();
        JPanel p_center_top = new JPanel();
        JPanel p_center_mid = new JPanel();
        JPanel p_center_bottom = new JPanel();
        JPanel p_south = new JPanel();

        // 상단 : 뒤로가기 버튼
        JButton btnBack = new JButton("< 뒤로가기");
        p_top.setLayout(new FlowLayout(FlowLayout.LEFT));
        p_top.setBackground(Color.WHITE);
        btnBack.setBorderPainted(false);
        btnBack.setBackground(Color.WHITE);
        btnBack.setForeground(Color.BLACK);
        btnBack.setFocusPainted(false);
        p_top.add(btnBack);
        add(p_top, BorderLayout.NORTH);

        // 하단 : 총금액 + 결제하기
        totalLabel = new JLabel("총 금액: 0원", JLabel.CENTER);
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        totalLabel.setBorder(new EmptyBorder(10, 0, 5, 0));

        JButton payButton = new JButton("결제하기");
        payButton.setPreferredSize(new Dimension(120, 40));
        payButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        payButton.setBackground(new Color(0, 123, 255));
        payButton.setForeground(Color.WHITE);
        payButton.setFocusPainted(false);
        payButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

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

        emptyLabel = new JLabel("상품 바코드를 입력해주세요", JLabel.CENTER);
        emptyLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
        emptyLabel.setForeground(Color.GRAY);
        emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        productListPanel.add(emptyLabel);

        JScrollPane scrollPane = new JScrollPane(productListPanel);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setPreferredSize(new Dimension(330, 300));

        // 상품 목록 타이틀
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

        // 중앙 영역 합치기
        p_center.setLayout(new BorderLayout());
        p_center.add(p_center_top, BorderLayout.NORTH);
        p_center.add(p_center_mid, BorderLayout.CENTER);
        p_center.add(p_center_bottom, BorderLayout.SOUTH);
        add(p_center, BorderLayout.CENTER);

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

        setTitle("무인편의점 키오스크 - 사용자 화면");
        setSize(375, 660);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addOrUpdateProduct(ProductVO p) {
        String barcode = p.getProductId();
        if (cartMap.containsKey(barcode)) {
            cartMap.get(barcode).increaseQuantity();
        } else {
            CartItemPanel itemPanel = new CartItemPanel(p);
            int index = productListPanel.getComponentCount();
            if (index % 2 == 0) {
                itemPanel.setBackground(Color.WHITE);
            } else {
                itemPanel.setBackground(new Color(245, 245, 245));
            }
            cartMap.put(barcode, itemPanel);
            productListPanel.add(itemPanel);
            productListPanel.revalidate();
            productListPanel.repaint();
        }
        updateTotalPrice();
        updateEmptyLabelVisibility();
    }

    private void updateTotalPrice() {
        int total = 0;
        for (CartItemPanel item : cartMap.values()) {
            total += item.getTotalPrice();
        }
        NumberFormat nf = NumberFormat.getInstance();
        totalLabel.setText("총 금액: " + nf.format(total) + "원");
    }

    private void updateEmptyLabelVisibility() {
        emptyLabel.setVisible(cartMap.isEmpty());
    }

    class CartItemPanel extends JPanel {
        ProductVO product;
        int quantity = 1;
        JLabel quantityLabel, priceLabel;

        public CartItemPanel(ProductVO product) {
            this.product = product;
            setLayout(new FlowLayout(FlowLayout.LEFT));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel nameLabel = new JLabel(product.getProductName());
            nameLabel.setPreferredSize(new Dimension(100, 20));

            quantityLabel = new JLabel(quantity + "개");
            priceLabel = new JLabel(NumberFormat.getInstance().format(getTotalPrice()) + "원");
            priceLabel.setPreferredSize(new Dimension(60, 20));

            JButton plusBtn = new JButton("➕");
            JButton minusBtn = new JButton("➖");
            JButton deleteBtn = new JButton("X");

            for (JButton btn : new JButton[]{plusBtn, minusBtn}) {
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
            quantityLabel.setText(quantity + "개");
            priceLabel.setText(NumberFormat.getInstance().format(getTotalPrice()) + "원");
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